import java.io.*;
import java.util.*;
import java.lang.Math;

public class SplitTheString {

	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("SplitTheStringTest.txt"));
		String s = br.readLine();
		String x = br.readLine();
		String y = br.readLine();
		// System.out.println(s);
		// System.out.println(x);
		// System.out.println(y);

		String opa;
		int a;
		int b;
		StringBuffer sb = new StringBuffer(s.length());
		for (int i = 0; i < s.length()-1; i++) {
			sb.append(s.charAt(i));
			a = x.indexOf(sb.toString());
			if (a != -1) {
				b = y.indexOf(s.substring(i+1));
				if (b != -1) {
					System.out.println((i+1) + " " + a + " " + b);
					return;
				}

			}

		}
		System.out.println("impossible");
	}
}