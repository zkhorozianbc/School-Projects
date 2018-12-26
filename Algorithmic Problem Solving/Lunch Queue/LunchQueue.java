import java.io.*;
import java.util.*;

public class LunchQueue {
	static Deque<String> q = new LinkedList<String>();
	public static void main(String[] args) throws Exception{
		// FileReader f = new FileReader("LunchQueueTest.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) {
			q.add(st.nextToken());
		}
		StringBuilder sb = new StringBuilder();
		int num_queries = Integer.parseInt(br.readLine());
		String op;
		String student = "";

		for (int i = 0; i < num_queries; i++) {
			st = new StringTokenizer(br.readLine());
			op = st.nextToken();
			if (op.equals("s")) {
				sb.append(q.pop() + "\n");
			} 
			else if (op.equals("j")){
				student = st.nextToken();
				q.add(student);

			} else {
				student = st.nextToken();
				q.remove(student);
			}
		}
		System.out.println(sb.toString());
	}
}