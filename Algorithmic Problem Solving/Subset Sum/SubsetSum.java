import java.io.*; 
import java.util.*; 

public class SubsetSum {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader br = new BufferedReader(new FileReader("SubsetSumTest.txt"));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int n = Integer.parseInt(st.nextToken());
		int u = Integer.parseInt(st.nextToken());
		int[] a = new int[n+1];
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++)
			a[i] = Integer.parseInt(st.nextToken());
		solveUtil(n,u,a);
	}

	static void solveUtil(int n, int u, int[] a) {
		long[][] dp = new long[n+1][u+1];
		dp[0][0] = 1;

		for (int i = 1; i <= n; i++) {
			for (int s = 0; s <= u; s++) {
				dp[i][s] = dp[i-1][s];
				if (s - a[i] >= 0)
					dp[i][s] += (dp[i-1][s-a[i]] % 1000000007);
			}
		}
		for (int i = 0; i <= u; i++)
			System.out.println(i + ": " + (dp[n][i] % 1000000007));
	}
}