import java.io.*;
import java.util.*;
import java.lang.Math;

public class AdjacentDifference {
	public static int[] a;
	public static int n;
	public static int ev;
	public static void main(String[] args) throws Exception {
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("AdjacentDifferenceTest.txt"));

		n = Integer.parseInt(br.readLine());

		a = new int[n];
		ev = 0;
		int fact = 1;
		for (int i = 0; i < n; i++) {
			a[i] = i+1;
			fact *= i+1;
		}

		for (int i = 0; i < n; i++) {
			p(a,0,n-1);
		}
		System.out.println(ev / fact);
	}

	static void p(int[] a, int l, int r) {
		int i;
		if (l == r) {
			System.out.println(Arrays.toString(a));
			int partial = 0;
			for (int j = 0; j < n-1; j++)
				partial += Math.abs(a[j] - a[j+1]);
			System.out.println(partial);
			ev += partial;

		} else {
			for (i = l; i <= r; i++) {
				swap(l, i);
          		p(a, l+1, r);
          		swap(l, i);
			}
		}

	}

	static void swap(int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}


}