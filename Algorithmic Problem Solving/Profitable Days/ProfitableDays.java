import java.io.*;
import java.util.*;

public class ProfitableDays {
	static Queue<Integer> pq = new PriorityQueue<Integer>(new Comparator<Integer>() {
  		public int compare(Integer x, Integer y) {
  			return y - x;
  		}});
	static int[] days;
	public static void main(String[] args) throws Exception { 
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		BufferedReader br = new BufferedReader(new FileReader("ProfitableDaysTest.txt"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());

		days = new int[n];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++){
			days[i] = Integer.parseInt(st.nextToken());
		}

		for (int i = 1; i < k+1; i++) {
			pq.add(days[i]);
		}

		StringBuilder sb = new StringBuilder();
		if (days[0] < pq.peek()) {
			sb.append("y");
		} else {	
			sb.append("n");
		}
		
		for (int i = 1; i < n; i++) {
			pq.remove(days[i]);
			if ((i+k) < n) {
				pq.add(days[i+k]);
			}
			if (pq.peek() == null) {
				sb.append("n");
				break;
			}

			if (days[i] < pq.peek()) {
				sb.append("y");
			} else {	
				sb.append("n");
			}
			



		}
		System.out.println(sb.toString());


	}
}