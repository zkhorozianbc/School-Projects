import java.io.*;
import java.util.*;

public class PalindromicNumbers {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader br = new BufferedReader(new FileReader("PalindromicNumbersTest.txt"));

		StringBuilder out = new StringBuilder();
		int q = Integer.parseInt(br.readLine());
		StringTokenizer st;
		int x,k;
		for (int i = 0; i < q; i++) {
			st = new StringTokenizer(br.readLine());
			x = st.nextToken();
			k = Integer.parseInt(st.nextToken());
			if (isPalindrome(x)) {
				k -= 1;
			}
			int[] a = new int[x.length()];
			for (int i = 0; i < x.length(); i++)
				a[i] = x.charAt(i) - '0';
			int nextPalindrome;
			while (k > 0) {
				nextPalindrome = nextSmallest(a);
				k -= 1;
			}
			out.append(nextPalindrome + "\n");
			
		}
		out.setLength(out.length()-1);
		System.out.println(out.toString());
	}

	static boolean isPalindrome(String x) {
		return x.equals(new StringBuilder(x).reverse().toString());
	}

	static String nextSmallest(int[] a, int k) {
		

	}
}