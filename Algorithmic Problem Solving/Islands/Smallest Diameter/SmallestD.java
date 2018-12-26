import java.io.*;
import java.util.*;

public class SmallestD {
	public static Map<Integer,List<Integer>> t1 = new HashMap<Integer,List<Integer>>();
	public static int n1;
	public static int[] c1;
	public static Map<Integer,List<Integer>> t2 = new HashMap<Integer,List<Integer>>();
	public static int n2;
	public static int[] c2;
	public static Map<Integer,Integer> back1 = new HashMap<Integer,Integer>();
	public static Map<Integer,Integer>  back2 = new HashMap<Integer,Integer>();
	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(new File("SmallestDTest.txt"));
		n1 = populateTree(in,t1);
		n2 = populateTree(in,t2);
		c1 = new int[n1+1];
		c2 = new int[n2+1];

		bfs(1,t1,c1);
		int max1 = -1;
		int max1i = -1;
		for (int i = 0; i < c1.length;i++) {
			if (c1[i] > max1) {
				max1 = c1[i];
				max1i = i;
			}
		}



		c1 = new int[n1+1];
		bfs2(max1i,t1,c1,back1);

		max1 = -1;
		max1i = -1;
		for (int i = 0; i < c1.length;i++) {
			if (c1[i] > max1) {
				max1 = c1[i];
				max1i = i;
			}
		}
		int diameter1 = max1;
	
		int rad1;
		if (max1 % 2 == 0) {
			rad1 = max1 / 2;
		} else {
			rad1 = (max1 + 1) / 2;
		}
		int trav = max1i;
		for (int i = 0; i < rad1;i++) {
			trav = back1.get(trav);
		}
		int center1 = trav;
		
		






		bfs(1,t2,c2);
		int max2 = -1;
		int max2i = -1;
		for (int i = 0; i < c2.length; i++) {
			if (c2[i] > max2) {
				max2 = c2[i];
				max2i = i;
			}
		}




		c2 = new int[n2+1];
		bfs2(max2i,t2,c2,back2);
		max2 = -1;
		max2i = -1;
		for (int i = 0; i < c2.length;i++) {
			if (c2[i] > max2) {
				max2 = c2[i];
				max2i = i;
			}
		}
		trav = max2i;
		
		int diameter2 = max2;
		int rad2;
		if (max2 % 2 == 0) {
			rad2 = max2 / 2;
		} else {
			rad2 = (max2 + 1) / 2;
		}

		for (int i = 0; i < rad2;i++) {
			trav = back2.get(trav);
		}
		int center2 = trav;
		System.out.println(Math.max(Math.max(diameter1, diameter2),1 + rad1 + rad2));
		System.out.println(center1 + " " + center2);

	}
	

	public static int populateTree(Scanner in, Map<Integer,List<Integer>> adj) throws Exception {
		int n = in.nextInt();
		for (int v = 1; v <= n; v++) {
			adj.put(v,new ArrayList<Integer>());
		}

		for (int i = 0; i < n - 1; i++) {
			int v = in.nextInt();
			int w = in.nextInt();
			adj.get(v).add(w);
			adj.get(w).add(v);
			
		}
		return n;

	}

	public static void bfs2(int v, Map<Integer,List<Integer>> adj, int[] counts,Map<Integer,Integer> back) {
		Queue<Integer> q = new LinkedList<Integer>();
		int[] visited = new int[counts.length];
		visited[v] = 1;
		int u;
		q.add(v);
		while (q.peek() != null) {
			u = q.poll();
			
			for (Integer i: adj.get(u)) {
				if (visited[i] == 0) {
					// System.out.println(i);
					visited[i] = 1;
					counts[i] += counts[u] + 1;
					q.add(i);
				
					back.put(i,u);
				}

			}
		}

	}
	public static void bfs(int v, Map<Integer,List<Integer>> adj, int[] counts) {
		Queue<Integer> q = new LinkedList<Integer>();
		int[] visited = new int[counts.length];
		visited[v] = 1;
		int u;
		q.add(v);
		while (q.peek() != null) {
			u = q.poll();
			
			for (Integer i: adj.get(u)) {
				if (visited[i] == 0) {
					// System.out.println(i);
					visited[i] = 1;
					counts[i] += counts[u] + 1;
					q.add(i);
				

				}

			}
		}

	}


}