import java.io.*;
import java.util.*;
import java.lang.Math;

public class SubarrayGCD {

	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("SubarrayGCDTest.txt"));
		int n = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] opa = new int[n];
		for (int i = 0; i < n; i++) {
			opa[i] = Integer.parseInt(st.nextToken());
		}

		
		int count = 0;
		for (int i = 0; i <= n; i++) {
			for (int j = i+1; j <= n; j++) {
				count += gcd(Arrays.copyOfRange(opa,i,j));
			}
		}
		System.out.println(count);
	}

    public static int gcd(int[] arr) { 
    	System.out.println(Arrays.toString(arr));
        int opa = arr[0];
        for (int i = 1; i < arr.length; i++) {
            opa = gcdUtil(arr[i], opa); 
        }
  
        return opa; 
    } 

	public static int gcdUtil(int a, int b) { 
        if (a == 0) {
            return b; 
        }
        return gcdUtil(b % a, a); 
    } 
  


}