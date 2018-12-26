import java.io.*;
import java.util.*;

public class ExpressionEval {
	static String eq;
	static long m = 1000000007;
	static Stack<Long> vals = new Stack<Long>();
	static Stack<Character> ops = new Stack<Character>();
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		eq = br.readLine().trim();
		shrimps();
		// FileReader in = new FileReader("ExpressionEvalTest.txt");
		// BufferedReader br = new BufferedReader(in);
		
		// while ((eq = br.readLine()) != null) {
		// 	eq = eq.trim();
		// 	shrimps();
		// }
		
	}

	static boolean isDigit(Character c) {
		return Character.isDigit(c);
	}
	static boolean isOp(Character c) {
		return (c.equals('+') || c.equals('-') || c.equals('*'));
	}


	static int respect(Character c) {
		if (c.equals('*'))
			return 1;
		return 0;
	}

	static long applyOp(long x, long y, Character op) {
		if (op.equals('*')) {
			return ((x % m) * (y % m)) % m;
		} else if (op.equals('+')) {
			if ((x + y) < 0) {
				return (m + (x + y)) % m;
			} else {
				return ((x % m) + (y % m)) % m;
			}
		} else {
			if ((x - y) < 0) {
				return (m + x - y) % m;
			} else {
				return ((x % m) - (y % m)) % m;
			}
		}
	}

	static void shrimps() {

		for (int i = 0; i < eq.length(); i++) {
			
			if (isDigit(eq.charAt(i))) {
				StringBuilder sb = new StringBuilder();
				while (i < eq.length() && isDigit(eq.charAt(i))) {
					sb.append(eq.charAt(i));
					i += 1;
				}
				i -= 1;
				
				long n = Long.parseLong(sb.toString());
				vals.push(n);
				
			} else {

				while (!ops.empty() && respect(ops.peek()) >= respect(eq.charAt(i))) {
					if (vals.empty()) {
						System.out.println("invalid");
						return;
					}
					long y = vals.pop();
					if (vals.empty()) {
						System.out.println("invalid");
						return;
					}
					long x = vals.pop();
					Character op = ops.pop();
					long a = applyOp(x,y,op);
					
					vals.push(a);		
				}
				ops.push(eq.charAt(i));
			}
		}
		while (!ops.empty()) {
			if (vals.empty()) {
				System.out.println("invalid");
				return;
			}
			long y = vals.pop();
			if (vals.empty()) {
				System.out.println("invalid");
				return;
			}
			long x = vals.pop();
			Character op = ops.pop();
			long a = applyOp(x,y,op);
			
			vals.push(a);
		}


		
		if (vals.empty()) {
			System.out.println("invalid");
		}
		long result = vals.pop();
		if (result < 0) {
			System.out.println((m + result) % m);
		} else {
			System.out.println(result % m);
		}
		
		return;
	}
}

