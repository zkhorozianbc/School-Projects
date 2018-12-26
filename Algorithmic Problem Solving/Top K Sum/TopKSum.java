import java.io.*;
import java.util.*;
import java.lang.Math;

public class TopKSum {
	static PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
	static long sum = 0;
	static int min = -1;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("TopKSumTest.txt"));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringBuilder out = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int currQSize = 0;
		int x = -1;
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) {
			x = Integer.parseInt(st.nextToken());
			if (currQSize < k) {
				pq.add(x);
				currQSize += 1;
				sum += x;
			} else {
				if (pq.peek() < x) {
					sum -= pq.poll();
					pq.add(x);
					sum += x;
				}
			}
			out.append(sum);
			if (i != n)
				out.append("\n");
		}
		System.out.print(out.toString());
	}
}