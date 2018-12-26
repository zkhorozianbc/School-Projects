import java.io.*;
import java.util.*;

public class SendingEmail {
	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("SendingEmailTest.txt"));
		int num_test_cases = Integer.parseInt(br.readLine());
		int test_case_num = 1;
		StringBuilder sb = new StringBuilder();
		while (test_case_num <= num_test_cases) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());
			int source = Integer.parseInt(st.nextToken());
			int sink = Integer.parseInt(st.nextToken());
			int[][] g = new int[n+1][n+1];
			int V = n+1;
			ArrayList<HashMap<Integer,Integer>> adj = new ArrayList<HashMap<Integer,Integer>>();
			for (int i = 0; i < V; i++)
				adj.add(new HashMap<Integer,Integer>());
			int u,v,weight;
			for (int i = 0; i < m; i++) {
				st = new StringTokenizer(br.readLine());
				u = Integer.parseInt(st.nextToken());
				v = Integer.parseInt(st.nextToken());
				weight = Integer.parseInt(st.nextToken());
				adj.get(u).put(v,weight);
				adj.get(v).put(u,weight);
			}

			boolean[] visited = new boolean[V];
			int[] dist = new int[V];
			for (int i = 0; i < V; i++)
				dist[i] = Integer.MAX_VALUE;
			PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Comparator<Integer>() {
				public int compare(Integer a, Integer b) {
					return dist[a] - dist[b];
				}
			});
			boolean found = false;
			visited[source] = true;
			dist[source] = 0;
			pq.add(source);


			while (!pq.isEmpty()) {
				v = pq.poll();
				
				visited[v] = true;
			
				if (v == sink) {
					found = true;
					sb.append("Case #").append(test_case_num).append(": ").append(dist[v]).append("\n");
					break;
				}


				for (Integer i: adj.get(v).keySet()) {
					if (adj.get(v).get(i) > 0 && !visited[i]) {
						if ((dist[v] + adj.get(v).get(i)) < dist[i]) {
							dist[i] = dist[v] + adj.get(v).get(i);
						}
						pq.add(i);
					}
				}
			}
			if (!found)
				sb.append("Case #").append(test_case_num).append(": unreachable\n");
			test_case_num += 1;
		}
		sb.setLength(sb.length()-1);
		System.out.println(sb.toString());
	}
}