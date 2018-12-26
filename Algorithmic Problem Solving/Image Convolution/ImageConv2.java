import java.io.*;
import java.util.*;

public class ImageConv2 {
	static String[] template;
	static int[][] shpirt;
	static int n;
	static int m;
	static int a;
	static int b;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader("ImageConvTest.txt"));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		String[] source = new String[n];
		
		String row;
		for (int i = 0; i < n; i++) {
			source[i] = br.readLine();
		}

		st = new StringTokenizer(br.readLine());
		a = Integer.parseInt(st.nextToken());
		b = Integer.parseInt(st.nextToken());

		template = new String[a];
		for (int i = 0; i < a; i++) {
			template[i] = br.readLine();
		}


		StringBuilder sb;
		String[] opa = new String[a];
		int count = 0;
		long startTime = System.nanoTime();
 		for (int k = 0; k + a <= n; k++) {
 			for (int j = 0; j + b <= m; j++) {
 				opa = new String[a];
 				for (int i = k; i < k + a;i++) {
 					
 					opa[i-k] = source[i].substring(j,b+j);
 				}
 				if (match(opa))
 					count += 1;

 			}
		}
		long endTime = System.nanoTime();
		System.out.println(endTime - startTime);
		System.out.println(count);

	}


	static boolean match(String[] m) {
		for (int i = 0; i < a; i++) {
			for (int j = 0; j < b; j++) {
				if (template[i].charAt(j) != '?') {
					if (template[i].charAt(j) != m[i].charAt(j))
						return false;
				}
			}
		}
		return true;

	}













}