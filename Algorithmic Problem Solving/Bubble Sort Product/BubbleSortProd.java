import java.io.*;
import java.util.*;
import java.math.*;
public class BubbleSortProd {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int[] a = new int[n];

		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		BigInteger s = BigInteger.valueOf(0);
		boolean flag = false;
		for (int i = 0; i < n-1; i++) {
			for (int j = 0; j < n-i-1; j++) {
				if (a[j] > a[j+1]) {
					s = s.add(BigInteger.valueOf(a[j]*a[j+1]));
					int temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                    flag = true;
				}
			}
			if (flag == false) {
				break;
			}
		}

		System.out.println(s.toString());

	}
}