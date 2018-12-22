import java.io.*;
import java.util.*;
import java.lang.Math;
import java.util.Random;

public class KnapsackHC {
	public static int GOAL_T;
	public static int GOAL_M;
	public static final int ADD_OP = 1;
	public static final int DELETE_OP = 2;
	public static final int SWAP_OP = 3;
	public static List<Ball> bag;
	public static int n;


	static class Ball {
		public String id;
		public int t,w,index;
		public Ball(String id, int t, int w, int index) {
			this.id = id;
			this.t = t;
			this.w = w;
			this.index = index;
		}
	}


	static class Operation {
		public int val,op;
		public Ball b1,b2;
		
		public Operation(int op, int val, Ball b1, Ball b2) {
			this.op = op;
			this.val = val;
			this.b1 = b1;
			this.b2 = b2;
		}
		public Operation(int op) {
			this(op,Integer.MAX_VALUE,null,null);
		}

		String toStringHa() {
			StringBuilder sb = new StringBuilder();
			switch(this.op) {
				case ADD_OP:
					sb.append("ADD: " + this.b1.id);
					break;
				case DELETE_OP:
					sb.append("DELETE: " + this.b1.id);
					break;
				case SWAP_OP:
					sb.append("SWAP: " + this.b1.id + "  " + this.b2.id);
					break;
			}

			return sb.toString();
		}

	}




	static class Sack {
		public int t,w;
		public List<Ball> balls;
		public Set<Integer> visited;
		public int bitRep;
		public int stateError;

		public int bestPreviousStateBitRep;
		public int bestPreviousStateError;
		public int numConsecutiveRepeats;

		public Operation minAddOp, minDeleteOp, minSwapOp;

		public Sack() {
			this.t = 0;
			this.w = 0;
			this.balls = new ArrayList<Ball>();
			this.visited = new HashSet<Integer>();
			this.bitRep = 0;
			this.stateError = this.getStateError();
			this.bestPreviousStateBitRep = 0;
			this.bestPreviousStateError = Integer.MAX_VALUE;
			this.restart();
		}


		///// random restart initialization

		void restart() {
			this.t = 0;
			this.w = 0;
			this.balls.clear();
			this.visited.clear();
			this.bitRep = 0;
			this.numConsecutiveRepeats = 0;


			Random random = new Random();
			
			for (int i = 0; i < n; i++) {
				if (random.nextBoolean()) {
					this.addBall(bag.get(i));
				}
			}
			this.visited.add(this.bitRep);
		}

		/////


		/////error functions

		int getStateError() {
			return Math.max(this.w-GOAL_M, 0) + Math.max(GOAL_T-this.t,0);
		}

		int errorAdd(Ball ball) {
			return Math.max((this.w + ball.w)-GOAL_M, 0) + Math.max(GOAL_T-(this.t+ball.t),0);
		}

		int errorDelete(Ball ball) {
			return Math.max((this.w - ball.w)-GOAL_M, 0) + Math.max(GOAL_T-(this.t - ball.t),0);
		}

		int errorSwap(Ball ballToRemove, Ball ballToAdd) {
			return  Math.max((this.w - ballToRemove.w + ballToAdd.w)-GOAL_M, 0) + Math.max(GOAL_T-(this.t - ballToRemove.t + ballToAdd.t),0);
		}

		/////


		///// state operations: add, delete, swap

		void addBall(Ball b) {
			this.balls.add(b);

			this.t += b.t;
			this.w += b.w;

			this.bitRep |= 1 << b.index;
		}

		void deleteBall(Ball b) {
			for (int i = 0; i < this.getSize(); i++) {
				if (this.balls.get(i).index == b.index)
					this.balls.remove(i);
			}

			this.t -= b.t;
			this.w -= b.w;

			this.bitRep &= ~(1 << b.index);
		}

		void swapBalls(Ball ballToRemove, Ball ballToAdd) {
			this.deleteBall(ballToRemove);
			this.addBall(ballToAdd);
		}

		/////


		///// compare and return operation with smallest error

		Operation getBestOp() {
			PriorityQueue<Operation> pq = new PriorityQueue<Operation>(new Comparator<Operation>() {
				public int compare(Operation op1, Operation op2) {
					return op1.val - op2.val;
				}
			});

			pq.add(this.minAddOp);
			pq.add(this.minDeleteOp);
			pq.add(this.minSwapOp);

			return pq.poll();
		}

		/////


		///// state transition function: executes operation to reach neigboring state with min error value 
		// or terminates if best found error is larger than current error

		boolean requiresRestart(Operation nextBestOp) {
			return nextBestOp.val == Integer.MAX_VALUE;

		}

		boolean transition() {
			this.findMinAddOp();
			this.findMinDeleteOp();
			this.findMinSwapOp();

			Operation nextBestOp = this.getBestOp();

			if (this.requiresRestart(nextBestOp))
				return false;

		
			

			this.execute(nextBestOp);

			this.updateValley();
			return true;
			

		}

		void updateValley() {
			this.visited.add(this.bitRep);
			this.stateError = this.getStateError();
			if (this.stateError < this.bestPreviousStateError) {
				this.bestPreviousStateError = this.stateError;
				this.bestPreviousStateBitRep = this.bitRep;
			}
		}
		

		void execute(Operation nextBestOp) {
			switch(nextBestOp.op) {
				case ADD_OP:
					this.addBall(nextBestOp.b1);
					break;
				case DELETE_OP:
					this.deleteBall(nextBestOp.b1);
					break;
				case SWAP_OP:
					this.swapBalls(nextBestOp.b1,nextBestOp.b2);
					break;
			}
		}

		/////


		boolean contains(Ball b) {

			return ((this.bitRep & (1 << b.index)) >= 1);
		}

		boolean isPreviousState(int op, Ball b1, Ball b2) {
			boolean found = false;
			switch(op) {
				case ADD_OP:
					found = visited.contains(this.bitRep | (1 << b1.index));
					break;
				case DELETE_OP:
					found = visited.contains(this.bitRep & ~(1 << b1.index));
					break;
				case SWAP_OP:
					found = visited.contains((this.bitRep & ~(1 << b1.index)) | (1 << b2.index));
					break;
			}
			return found;
		}

		boolean isPreviousState(int op, Ball b1) {
			return this.isPreviousState(op,b1,null);
		}

		void findMinAddOp() {
			this.minAddOp = new Operation(ADD_OP);

			for (Ball b: bag) {
				if (!this.contains(b) && !this.isPreviousState(ADD_OP,b)) {

					int epsilon = this.errorAdd(b);
					if (epsilon < this.minAddOp.val) {
						this.minAddOp.b1 = b;
						this.minAddOp.val = epsilon;
					}
				}
			}

		}


		void findMinDeleteOp() {
			this.minDeleteOp = new Operation(DELETE_OP);
			for (Ball b: bag) {
				if (this.contains(b) && !this.isPreviousState(DELETE_OP,b)) {
					int epsilon = this.errorDelete(b);
					if (epsilon < this.minDeleteOp.val) {
						this.minDeleteOp.b1 = b;
						this.minDeleteOp.val = epsilon;
					}
				}
			}
		}


		void findMinSwapOp() {
			this.minSwapOp = new Operation(SWAP_OP);


			for (Ball b1: this.balls) {
				for (Ball b2: bag) {
					if (!this.contains(b2) && !this.isPreviousState(SWAP_OP,b1,b2)) {
						int epsilon = this.errorSwap(b1,b2);
						if (epsilon < this.minSwapOp.val) {
							this.minSwapOp.b1 = b1;
							this.minSwapOp.b2 = b2;
							this.minSwapOp.val = epsilon;
						}
					}
				}
			}
		}


		int getSize() {
			return this.balls.size();
		}

		boolean isGoalState() {
			return (this.t >= GOAL_T && this.w <= GOAL_M);
		}


		String toStringHa() {
			StringBuilder sb = new StringBuilder();
			
			sb.append("Output: ");
			for (Ball b: bag) {
				if (this.contains(b))
					sb.append(b.id + " ");
			}	
			sb.setLength(sb.length()-1);
			return sb.toString();
		}
	}


	static void climb() {
		Sack state = new Sack();
		

		int num_restarts = 10;
		int num_epochs = 100;

		for (int i = 0; i < num_restarts; i++) {
			for (int j = 0; j < num_epochs; j++) {
				if (!state.transition())
					break;
			
				if (state.isGoalState()) {
					System.out.println(state.toStringHa());
					return;
				}
				if (state.getSize() == n)
					break;

			}
		}
		System.out.println("No Solution");
	}


	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("KnapsackTest.txt"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		GOAL_T = Math.round(Float.parseFloat(st.nextToken()));
		GOAL_M = Math.round(Float.parseFloat(st.nextToken()));
		

		bag = new ArrayList<Ball>();
		
		String id;
		int t,w;
		String line;

		n = 0;
		while ((line=br.readLine())!=null && line.length()!=0) {
			st = new StringTokenizer(line);
			id = st.nextToken();
			t = Math.round(Float.parseFloat(st.nextToken()));
			w = Math.round(Float.parseFloat(st.nextToken()));
			bag.add(new Ball(id,t,w,n));
			n += 1;
			
		}

		climb();
		

		


	}
}