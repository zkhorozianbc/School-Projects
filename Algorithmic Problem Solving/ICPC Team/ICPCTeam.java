import java.io.*;
import java.util.*;

public class ICPCTeam {
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader("ICPCTeamTest.txt"));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int[] students = new int[n];
		String brih = "";
		
		StringBuilder bRep;
		for (int i = 0; i < n; i++) {
			brih = br.readLine();
			bRep = new StringBuilder();
			for (int j = 0; j < m; j++) {
				if (brih.charAt(j) == 'y') {
					bRep.append("1");
				} else {
					bRep.append("0");
				}
			}
			students[i] = Integer.parseInt(bRep.toString(),2);
		}


		int shrimps = ((1 << m) - 1);
		int count = 0;
		long startTime = System.nanoTime();
		for (int i = 0; i < n; i++) {
			for (int j = i+1; j < n; j++) {
				for (int k = j+1; k < n; k++) {
					System.out.println(i + " " + j + " " + k);
					if ((students[i] | students[j] | students[k]) == shrimps) {
						
						count += 1;
					}
				}
			}
		}

		long endTime = System.nanoTime();
		// System.out.println(endTime - startTime);
		System.out.println(count);
	}
}