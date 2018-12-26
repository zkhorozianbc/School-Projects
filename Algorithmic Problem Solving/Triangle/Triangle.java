import java.io.*;
import java.util.*;

public class Triangle {
	static List<Edge> edges;
	static HashMap<Integer,Integer> degrees;

	static class Node {
		public int label;
		public List<Node> neighbors;
		public Node(int label) {
			this.label = label;
			this.neighbors = new ArrayList<Node>();
		}
	}

	static class Edge {
		public Node x;
		public Node y;
		public Edge(Node x, Node y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("TriangleTest.txt"));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		edges = new ArrayList<Edge>();
		degrees = new HashMap<Integer,Integer>();
	
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			if (v < w) {
				g.get(v).add(w);
			} else {
				g.get(w).add(v);
			}
			
			degrees.put(v,degrees.getOrDefault(v,0)+1);
			degrees.put(w,degrees.getOrDefault(w,0)+1);
			edges.add(new Edge(v,w));
		}


	}




}