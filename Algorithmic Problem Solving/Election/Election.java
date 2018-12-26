import java.io.*;
import java.util.*;

public class Election {
	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("ElectionTest.txt"));

		int n = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		Integer[] candidates = new Integer[n-1];
		int john = Integer.parseInt(st.nextToken());
		for (int i = 0; i < n-1; i++)
			candidates[i] = Integer.parseInt(st.nextToken());
		

		Arrays.sort(candidates,Collections.reverseOrder());
		int max = candidates[0];
		int count = 0;
		int numMax = 0;
		while (john + count <= max) {
			while (numMax < (n-1) && candidates[numMax] == max && (john + count + numMax) <= max) 
				numMax += 1;
			count += numMax;
			max -= 1;
		}
		System.out.println(count);




	}
}

