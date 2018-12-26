import java.io.*;
import java.util.*;

public class AllClear {

	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("AllClearTest.txt"));

		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());

		long[] arr = new long[n];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++)
			arr[i] = Long.parseLong(st.nextToken());

		long[] delta = new long[n];
		long pSum = 0;
		long count = 0;
		for (int i = 0; i < n; i++) {
			pSum += delta[i];
			if (arr[i] > pSum) {
				count += (arr[i]-pSum);
				delta[i] += (arr[i]-pSum);
				if ((i+k) < n)
					delta[i+k] -= (arr[i]-pSum);
				
				pSum += (arr[i]-pSum);

			}
		}
		System.out.println(count);
	}	
}