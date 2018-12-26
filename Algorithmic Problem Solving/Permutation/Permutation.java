import java.io.*;
import java.util.*;
public class Permutation {
	static Map<Integer,Integer> left;
	static Map<Integer,Integer> right;
	static List<Integer> a;
	static List<Integer> b;
	static int n;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader("PermutationTest.txt"));
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder out = new StringBuilder();
		n = Integer.parseInt(br.readLine());
		// int opa = (1 << n)-1;
		a = new ArrayList<Integer>(Collections.nCopies(n+1, 0));
		b = new ArrayList<Integer>();
		left = new HashMap<Integer,Integer>();
		right = new HashMap<Integer,Integer>();
		StringTokenizer st;
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			int l = Integer.parseInt(st.nextToken());
			int r = Integer.parseInt(st.nextToken());
			left.put(i,l);
			right.put(i,r);
			b.add(i);
		}
		boolean flag = false;
		Collections.sort(b, new Comparator<Integer>() {
		    public int compare(Integer a1, Integer a2) {
		        return right.get(a1) - right.get(a2);
		    }
		});
		for (Integer x: b)
			System.out.print(x + " ");
		System.out.println();
		
		for (Integer x: b) {
			
			for (int i = left.get(x); i <= right.get(x); i++) {
				if (a.get(i) == 0) {
					a.set(i,x);
					
				}
			}
		}
		for (int i = 1; i <= n; i++) {
			System.out.print(a.indexOf(i) + " ");
		} 
		System.out.println();
		


		// for (int i = 1; i <= n; i++) {
		// 	if (!push(i)) {
		// 		out.append("no solution");
		// 		flag = true;
		// 		break;
		// 	}
		// }
		// if (!flag) {
		// 	for (int i = 1; i <= n; i++) {
		// 		out.append(a.indexOf(i) + " ");
		// 	}
		// }
		// System.out.println(out.toString());

	}


	// static boolean push(int k) {
	// 	for (int j = left.get(k); j <= right.get(k); j++) {
	// 		if (a.get(j) == 0) {
	// 			a.set(j,k);
	// 			return true;
	// 		}
			
	// 	}
	// 	for (int j = left.get(k); j <= right.get(k); j++) {
	// 		for (int r = left.get(j); r <= right.get(j); r++) {
	// 			if (a.get(r) == 0) {
	// 				a.set(r,a.get(j));
	// 				a.set(j,k);
	// 				return true;
	// 			}
				
	// 		}
	// 	}
	// 	return false;
	// }

	static void printA() {
		for (int i = 1; i <= n; i++)
			System.out.print(a.get(i) + " ");
		System.out.println();
	}

}