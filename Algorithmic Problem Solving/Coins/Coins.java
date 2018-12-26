import java.io.*; 
import java.util.*; 
import java.lang.Math.*;

public class Coins {

	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("CoinsTest.txt"));
		
		int n = Integer.parseInt(br.readLine());
		int[] arr = new int[n];

		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++)
			arr[i] = Integer.parseInt(st.nextToken());

		int q = Integer.parseInt(br.readLine());
		int[] amounts = new int[q];
		int q_t,total;
		int max = -1;
		for (int i = 0; i < q; i++) {
			st = new StringTokenizer(br.readLine());
			q_t = Integer.parseInt(st.nextToken());
			total = Integer.parseInt(st.nextToken());
			amounts[i] = total;
			if (total > max)
				max = total;
		}
		count(arr,max,amounts);
	}

	static void count(int[] arr, int total, int[] amounts) {
		int n = arr.length;
		int[] dp = new int[total+1];

		dp[0] = 1;
		for (int i = 0; i < n; i++) {
			for (int j = arr[i]; j <= total; j++) {
				dp[j] = (dp[j] + (dp[j-arr[i]] % 1000000007)) % 1000000007;
			}
		}
		for (int i = 0; i < amounts.length; i++) {
			System.out.println(dp[amounts[i]]);
		}
		

	}
}