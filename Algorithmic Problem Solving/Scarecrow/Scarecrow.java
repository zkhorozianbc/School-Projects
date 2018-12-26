import java.io.*;
import java.util.*;
public class Scarecrow {
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader("ScarecrowTest.txt"));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder out = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		int t = 0;
		while (t < T) {
			int n = Integer.parseInt(br.readLine());
			char[] field = br.readLine().toCharArray();
			int count = 0;
			int i = 0;
			while (i < n) {
				if (field[i] == '.') {
					count += 1;
					i += 3;
				} else {
					i += 1;
				}
			}
			out.append("Case " + ++t + ": " + count + "\n");
		}
		out.setLength(out.length()-1);
		System.out.println(out.toString());
	}
}