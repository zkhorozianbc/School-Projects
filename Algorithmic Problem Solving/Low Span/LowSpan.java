import java.io.*;
import java.util.*;
import java.lang.Math;

public class LowSpan {
	static Deque<Integer> dq = new LinkedList<Integer>();
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("LowSpanTest.txt"));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringBuilder out = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		
		int[] arr = new int[n];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++)
			arr[i] = Integer.parseInt(st.nextToken());



		int count = 0;
		int start = 0;
		int min = arr[0];
		int max = arr[0];

		for (int i = 0; i < n; i++) {
			if (max < arr[i]) {
				max = arr[i];
			}
			if (min > arr[i]) {
				min = arr[i];
			}

			if (Math.abs(arr[i] - min) >= k) {
				min = arr[i];
				max = arr[i];
				int j = i - 1;
				while (j >= 0 && j >= start) {
					if (min > arr[j]) {
						min = arr[j];
					}
					if (Math.abs(arr[i] - max) <= k) {
						break;
					}
					j -= 1;
				}
				start = j + 1;
			}
			if (Math.abs(max - arr[i]) >= k) {
				min = arr[i];
				max = arr[i];
				int j = i - 1;
				while (j >= 0 && j >= start) {
					if (max < arr[j]) {
						max = arr[j];
					}
					if (Math.abs(arr[i] - max) <= k) {
						break;
					}
					j -= 1;
				}
				start = j + 1;
			}
			System.out.println(" n " + " start " + " min " + " max " + " pairs " );
			System.out.println(" " + "-" + "    " + "-" + "    " + "-" + "     " + "-" + "     " + "-");
			System.out.println(" " + arr[i] + "    " + start + "    " + min + "     " + max + "     " + (i - start));
			System.out.println();
			count += Math.abs(i - start);
		}
		System.out.println(count);

		// int count = 0;
		// int up = -1;
		// int down = -1;
		// int span = -1;
		// for (int i = 0; i < n-1; i++) {
		// 	up = arr[i];
		// 	down = arr[i];
		// 	dq = new LinkedList<Integer>();
		// 	dq.add(arr[i]);
		// 	for (int j = i+1; j < n; j++) {
		// 		dq.add(arr[j]);
		// 		if (arr[j] > up) {
		// 			up = arr[j];
		// 		}
		// 		if (arr[j] < down) {
		// 			down = arr[j];
		// 		}

		// 		span = Math.abs(up - down);
		// 		Iterator<Integer> itr = dq.iterator();
		// 		while (itr.hasNext()) {
		// 			System.out.print(itr.next() + " ");
		// 		}
		// 		System.out.println();
		// 		// System.out.println("min: " + down + " max: " + up + " " + "span: " + span);
		// 		if (span <= k)
		// 			count += 1;
		// 	}

		// }
		// System.out.println(count);
	}
}
