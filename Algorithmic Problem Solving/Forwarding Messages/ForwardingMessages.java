import java.io.*;
import java.util.*;

public class ForwardingMessages {
	static Map<Integer,List<Integer>> g;
	static Map<Integer,List<Integer>> gT;
	static int V,E;
	static int opa;
	static int[] leaders;
	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("ForwardingMessagesTest.txt"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		V = n;
		E = m;
		leaders = new int[n+1];
		g = new HashMap<Integer,List<Integer>>();
		int u,v;
		for (int i = 1;i <= V; i++)
			g.put(i,new ArrayList<Integer>());
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());
			g.get(u).add(v);
		}
		SCCs();

	}

	static void fillOrder(Stack<Integer> stack) {
		Set<Integer> visited = new HashSet<Integer>();

		for (Integer u: gT.keySet()) {
			if (!visited.contains(u)) {
				fillOrderUtil(u,stack,visited);
			
			}
		}
	}

	static void fillOrderUtil(int u, Stack<Integer> stack, Set<Integer> visited) {
		visited.add(u);
		
		for (Integer v: gT.get(u)) {
			if (!visited.contains(v))
				fillOrderUtil(v,stack,visited);
		}
		stack.push(u);
	}

	static void setTranspose() {
		gT = new HashMap<Integer,List<Integer>>();
		for (int i = 1;i <= V; i++)
			gT.put(i,new ArrayList<Integer>());
		for (Integer u: g.keySet()) {
			for (Integer v: g.get(u)) {
				gT.get(v).add(u);
			}
		}

	}

	static void secondRunUtil(int u, Set<Integer> visited) {
		visited.add(u);
		System.out.print(u + " ");
		leaders[u] = opa;
		for (Integer v: g.get(u)) {
			if (!visited.contains(v)) {
				secondRunUtil(v,visited);
			}
		}
	}

	static void secondRun(Stack<Integer> stack) {
		Set<Integer> visited = new HashSet<Integer>();
		int u;
		while (!stack.isEmpty()) {
			u = stack.pop();
			
			if (!visited.contains(u)) {
				opa = u;
				secondRunUtil(u,visited);
				System.out.println(visited.toString());
				System.out.println();
			}
		}
	}

	static void SCCs() {
		Stack<Integer> stack = new Stack<Integer>();
		setTranspose();
		fillOrder(stack);
		secondRun(stack);
		System.out.println(Arrays.toString(leaders));

	}
 


}
