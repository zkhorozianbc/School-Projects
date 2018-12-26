import java.io.*; 
import java.util.*; 
import java.lang.Math.*;

public class InsertionSort {
	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("InsertionSortTest.txt"));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int n = Integer.parseInt(st.nextToken());
		int[] x = new int[n+1];
		int[] y = new int[n+1];
		st = new StringTokenizer(br.readLine());

		for (int i = 1; i <= n; i++) x[i] = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) y[i] = Integer.parseInt(st.nextToken());
		solveUtil(n,x,y);
	}

	static void solveUtil(int n, int[] x, int[] y) {
		int[][] dp = new int[n+1][n+1];

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
				dp[i][j] = Math.max(dp[i][j], dp[i-1][j-1] + (x[i] == y[j] ? 1 : 0));
			}
		}
		System.out.println(n - dp[n][n]);
	}
}