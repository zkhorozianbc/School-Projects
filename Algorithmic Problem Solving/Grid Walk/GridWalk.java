import java.io.*;
import java.util.*;

public class GridWalk {

	public static class Node {
		public int x,y,d;

		public Node(int x, int y, int d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}
	}

	public static int n;
	public static int m;
	public static int[][] g;
	public static HashMap<Integer,Node> opa = new HashMap<Integer,Node>();
	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("GridWalkTest.txt"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		g = new int[n][m];

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < m; j++) {
				g[i][j] = Integer.parseInt(st.nextToken());
				opa.put(cantor(i,j), new Node(i,j,g[i][j]));
				System.out.println(i + " " + j + ": " + cantor(i,j));
			}

		}

		djikstra(g);
	}



	static void djikstra(int[][] g) {
		int[][] dists = new int[n][m];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++)
				dists[i][j] = Integer.MAX_VALUE;
		}

		int[] dx = {-1, 0, 1, 0}; 
    	int[] dy = {0, 1, 0, -1};

    	PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Comparator<Integer>() {
    		public int compare(Integer a, Integer b) {
    			return opa.get(a).d - opa.get(b).d;
    		}
    	});
    	opa.get(cantor(0,0)).d = 0;
    	pq.add(cantor(0,0));
    	dists[0][0] = g[0][0];
    	Node trav;
    	while (!pq.isEmpty()) {
    		trav = opa.get(pq.poll());
    		System.out.println( "ha " + trav.x + " " + trav.y + ": " + trav.d);
    		for (int i = 0; i < 3; i++) {
    			int x = trav.x + dx[i];
    			int y = trav.y + dy[i];
    			if (!isValid(x,y))
    				continue;
    			int hash = cantor(x,y);

    			if (dists[x][y] > dists[trav.x][trav.y] + g[x][y]) {
    				if (dists[x][y] != Integer.MAX_VALUE) {
    					// System.out.println( "ha " + opa.get(hash).x + " " + opa.get(hash).y + ": " + opa.get(hash).d);
    					pq.remove(hash);
    				}
    				dists[x][y] = dists[trav.x][trav.y] + g[x][y];
    				trav.d = dists[x][y];
    				pq.add(hash);
    				// System.out.println(opa.get(hash).x + " " + opa.get(hash).y + ": " + opa.get(hash).d);


    			}
    		}
    	}


		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++)
				System.out.print(dists[i][j] + " ");
			System.out.println();
		}
	}


	static boolean isValid(int x, int y) {
		return (x >= 0) && (x < n) && (y >= 0) && (y < m) && (g[x][y] != 0);
	}


	static int cantor(int a, int b) {
		return (a + b) * (a + b + 1) / 2 + a;
	}




}