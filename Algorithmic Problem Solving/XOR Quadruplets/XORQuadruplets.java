import java.io.*;
import java.util.*;
import java.lang.Math;
public class XORQuadruplets {
	private static int n;
	private static int[] a;
	private static int[] b;
	private static HashMap<Long,Long>  x;
	private static HashMap<Long,Long>  y;
	private static long counter;


	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("XORQuadrupletsTest.txt"));
		n = Integer.parseInt(br.readLine());
		counter = 0L;
		a = new int[n];
		b = new int[n];
		
		x = new HashMap<Long,Long>();
		y = new HashMap<Long,Long>();
		
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		fillArray(a, st);
		st = new StringTokenizer(br.readLine());
		fillArray(b,st);


		Long g,h;
		for (int i = 0; i < n; i++) {
			for (int j = i+1; j < n; j++) {
				g = Long.valueOf(a[i]^a[j]);
				h = Long.valueOf(b[i]^b[j]);
				x.put(g,x.getOrDefault(g,0L)+1L);
				y.put(h,y.getOrDefault(h,0L)+1L);
			}
		}
	

		for (Map.Entry<Long, Long> entry : y.entrySet())
		{
		    counter += (x.getOrDefault(entry.getKey(),0L)*entry.getValue());
		}
		System.out.println(counter);

	}

	static void fillArray(int[] arr, StringTokenizer st) {
		for (int j = 0; j < arr.length; j++)
			arr[j] = Integer.parseInt(st.nextToken());
	}
}