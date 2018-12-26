import java.io.*;
import java.util.*;
import java.lang.Math;
public class KnapsackID {
	public static int GOAL_T;
	public static int GOAL_M;
	public static List<Ball> bag;
	public static int max_depth;
	public static int n;
	public static HashMap<String,Integer> nextIndices;
	static class Ball {
		public String id;
		public int t,w;
		public Ball(String id, int t, int w) {
			this.id = id;
			this.t = t;
			this.w = w;
		}
	}

	static class Sack {
		public int t,w;
		public List<Ball> balls;
		public Sack() {
			this.t = 0;
			this.w = 0;
			this.balls = new ArrayList<Ball>();
		}

		int getSize() {
			return this.balls.size();
		}

		void removeLast() {
			if (balls.size() == 0)
				return;
			this.t -= this.balls.get(this.getSize()-1).t;
			this.w -= this.balls.get(this.getSize()-1).w;
			balls.remove(this.getSize()-1);
		}

		void addBall(Ball b) {
			this.balls.add(b);
			this.t += b.t;
			this.w += b.w;
		}

		boolean isGoals() {
			return (this.t >= GOAL_T && this.w <= GOAL_M);
		}

		String toStringHa() {
			StringBuilder sb = new StringBuilder();
			// sb.append("T: " + this.t + " W: " + this.w + "\n");
			// sb.append("objects: ");
			for (Ball b: this.balls) {
				sb.append(b.id + " ");
			}
			sb.setLength(sb.length()-1);
			return sb.toString();
		}
	}


	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("KnapsackTest.txt"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		GOAL_T = Math.round(Float.parseFloat(st.nextToken()));
		GOAL_M = Math.round(Float.parseFloat(st.nextToken()));
		

		bag = new ArrayList<Ball>();
		nextIndices = new HashMap<String,Integer>();
		String id;
		int t,w;
		String line;

		n = 0;
		while ((line=br.readLine())!=null && line.length()!=0) {
			st = new StringTokenizer(line);
			id = st.nextToken();
			t = Math.round(Float.parseFloat(st.nextToken()));
			w = Math.round(Float.parseFloat(st.nextToken()));
			bag.add(new Ball(id,t,w));
			n += 1;
			nextIndices.put(id,n);
		}
		max_depth = n;
		IDDFS();


	}


	static boolean dfs(Ball next_ball, Sack state, int depth) {
		if (depth == 0) 
			return false;

		state.addBall(next_ball);

		if (state.isGoals()) {
			return true;
		}

		Ball b;
		for (int i = nextIndices.get(next_ball.id); i < n; i++) {
			b = bag.get(i);
			if (state.w + b.w <= GOAL_M) {
				if (dfs(b,state,depth-1))
					return true;
			}
		}

		state.removeLast();
		return false;
		
	}


	static void IDDFS() {
		

		Sack state;		
		for (int depth = 1; depth <= max_depth;depth++) {

			for (int i = 0; i < bag.size(); i++) {
				state = new Sack();
				if (dfs(bag.get(i),state,depth)) {
					System.out.println(state.toStringHa());
					return;
				}
			}
		}
		System.out.println("No Solution");



	}
}