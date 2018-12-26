import java.io.*;
import java.util.*;
import java.lang.Math;

public class StringCounting {

	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("StringCountingTest.txt"));

		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());

		int[] nah = new int[c];
		st = new StringTokenizer(br.readLine());
		String ha;
		while (st.hasMoreTokens()) {
			ha = st.nextToken();
			nah[(int)(ha.charAt(0) - 'a')] += 1;
		}
		System.out.println(Arrays.toString(nah));

		int[] dp = new int[n];
		dp[0] = c;
		for (int i = 1; i < n; i++) {
			int partial = 0;
			for (int j = 0; j < c; j++)
				partial += (nah[j] % 1000000007);
			dp[i] = (dp[i-1]*(c-partial)) % 1000000007;
		}
		System.out.println(Arrays.toString(dp));
		
		
	}


}