import java.io.*;
import java.util.*;
import java.lang.Math;

public class CardArrangment {

	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("CardArrangementTest.txt"));

		int n = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] arr = new int[n+1];
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		String next;
		HashSet<Integer> seen = new HashSet<Integer>();
		for (int i = 1; i <= n; i++) {
			seen.add(i);
		}
		for (int i = 1; i <= n; i++) {
			next = st.nextToken();
			if (!next.equals("?")) {
				arr[i] = Integer.parseInt(next);
				seen.remove(arr[i]);
			}
		}
		pq.addAll(seen);

		int opa = pq.size();
		int count = 0;
		int x;
		int[] dp = new int[n+1];
		for (int i = n; i >= 1; i--) {
			if (arr[i] == 0)
				arr[i] = pq.poll();

		}
		for (int i = 1; i <= n; i++) {
			for (int j = i+1; j <= n; j++) {
				if (arr[i] > arr[j])
					count += 1;
			}
		}
		System.out.println(count);
		// System.out.println(Arrays.toString(arr));

	}
}