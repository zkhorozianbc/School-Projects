import java.io.*; 
import java.util.*; 
import java.lang.Math.*;

public class CriticalElements {

	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("CriticalElementsTest.txt"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());

		int[] a = new int[n];
		ArrayList[] dp = new ArrayList[n];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) {
			a[i] = Integer.parseInt(st.nextToken());
			dp[i] = new ArrayList<Integer>();
		}
		
		solve(a,n);
	}


	static void solve(int[] a, int n) {

		int[] l = new int[n];
		int[] p = new int[n];
		for (int i = 0; i < n; i++) {
			p[i] = i;
		}

		for (int i = 1; i < n; i++) {
			for (int j = 0; j < i; j++) {
				if (a[i] > a[j] && l[i] < (l[j]+1)) {
					l[i] = l[j] + 1;
					p[i] = j;
				}
			}
		}

		int maxVal = -1;
		int maxIndex = -1;

		for (int i = 0; i < n; i++) {
			if (maxVal < l[i]) {
				maxVal = l[i];
				maxIndex = i;
			}
		}

		ArrayList<Integer> lis = new ArrayList<Integer>();
		lis.add(a[maxIndex]);
		while (maxIndex != p[maxIndex]) {
			maxIndex = p[maxIndex];
			lis.add(0,a[maxIndex]);
		}
		System.out.println(Arrays.toString(a));
		System.out.println(Arrays.toString(l));
		System.out.println(lis.toString());
	}
}