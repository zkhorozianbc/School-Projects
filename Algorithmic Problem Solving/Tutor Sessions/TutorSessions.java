import java.io.*;
import java.util.*;

public class TutorSessions {
	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("TutorSessionsTest.txt"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		Long k = Long.parseLong(st.nextToken());

		PriorityQueue<Long> pq = new PriorityQueue<Long>();
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			pq.add(Long.parseLong(st.nextToken()) + 1L);
		}
		Long count = (long)0;
		Long top = (long)-1;
		Long trav = (long)0;

		while (!pq.isEmpty()) {
			while (!pq.isEmpty() && top < trav) {
				top = pq.poll();
			}
			trav = top + k;
			count += 1;
		}
		System.out.println(count);


	}
}