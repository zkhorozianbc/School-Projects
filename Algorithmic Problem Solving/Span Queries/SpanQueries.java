import java.io.*;
import java.util.*;
import java.lang.Math;

public class SpanQueries {
	// static PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
	// static PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(new Comparator<Integer>() {
 //  		public int compare(Integer x, Integer y) {
 //  			return y - x;
 //  		}});
	static List<Integer> tings = new ArrayList<Integer>();
	static int wagwan = 0;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader("SpanQueriesTest.txt"));
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder out = new StringBuilder();


		StringTokenizer st = new StringTokenizer(br.readLine());
		int num_queries = Integer.parseInt(st.nextToken());

		String op = "";
		int val = -1;
		for (int i = 0; i < num_queries; i++) {
			st = new StringTokenizer(br.readLine());
			op = st.nextToken();
			if (op.equals("a")) {
				val = Integer.parseInt(st.nextToken());
				a(val);
				
			} else if (op.equals("d")) {
				val = Integer.parseInt(st.nextToken());
				d(val);
			} else {
				out.append(s());
				if (i != num_queries)
					out.append("\n");
			}
		}

		System.out.print(out.toString());
	}



	static void a(int x) {
		int index = Collections.binarySearch(tings,x);
		if (index < 0) 
			index = -1*(index+1);
		tings.add(Math.max(0,index),x);
		wagwan += 1;
	}


	static void d(int x) {
		if (wagwan == 0) {
			return;
		}
		int index = Collections.binarySearch(tings,x);
		if (index < 0)
			return;
		tings.remove(index);
		wagwan -= 1;
	}
	static int s() {
		if (wagwan == 0) {
			return -1;
		}
		return Math.abs(tings.get(0) - tings.get(wagwan - 1));

	}
}