import java.io.*;
import java.util.*;
import java.util.stream.*;
public class Blocks {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// long n = Long.parseLong(br.readLine());
		long n = Long.parseLong("100");

		long m = 1000000007;
		// long[] dp = new long[n+1];
		// dp[0] = 1;
		// dp[1] = 1;
		// dp[2] = 2;
		// dp[3] = 7;
		HashMap<Long,Long> dp = new HashMap<Long,Long>();
		dp.put(0L,1L);
		dp.put(1L,1L);
		dp.put(2L,2L);
		dp.put(3L,7L);
		for (long i = 4; i <= n; i++) {
			// dp[i] = ((dp[i-1]%m) + (dp[i-2]%m) + (4*(dp[i-3]%m)%m) + (2*(dp[i-4]%m)%m)) %m;
			System.out.println((dp.get(i-1)%m) + (dp.get(i-2)%m) + "  " + (4L*dp.get(i-3)%m) + (2L*dp.get(i-4)%m));
			dp.put(i,((dp.get(i-1)%m) + (dp.get(i-2)%m) + (4L*dp.get(i-3)%m) + (2L*dp.get(i-4)%m)) %m);
			

		}
		System.out.println(dp.get(n));
		
	}

	   /* function that returns nth Fibonacci number */
    static int T(int n) 
    { 
    int F[][] = new int[][]{{1,1},{1,0}}; 
    if (n == 0) 
        return 0; 
    power(F, n-1); 
       
    return F[0][0]; 
    } 
       
    static void multiply(int F[][], int M[][]) 
    { 
    int x =  F[0][0]*M[0][0] + F[0][1]*M[1][0]; 
    int y =  F[0][0]*M[0][1] + F[0][1]*M[1][1]; 
    int z =  F[1][0]*M[0][0] + F[1][1]*M[1][0]; 
    int w =  F[1][0]*M[0][1] + F[1][1]*M[1][1]; 
      
    F[0][0] = x; 
    F[0][1] = y; 
    F[1][0] = z; 
    F[1][1] = w; 
    } 
       
    /* Optimized version of power() in method 4 */
    static void power(int F[][], int n) 
    { 
    if( n == 0 || n == 1) 
      return; 
    int M[][] = new int[][]{{1,1},{1,0}}; 
       
    power(F, n/2); 
    multiply(F, F); 
       
    if (n%2 != 0) 
       multiply(F, M); 
    } 
}