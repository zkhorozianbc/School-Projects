import java.io.*;
import java.util.*;

public class LastSmallerElement {
	public static Deque<Integer> lsi;
	public static Queue<Integer> q;
	public static Map<Integer,Integer> counts;
	public static void main(String[] args) throws Exception {
		FileReader in = new FileReader("LastSmallerElementTest.txt");
		BufferedReader br = new BufferedReader(in);
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String firstLine = br.readLine();
		StringTokenizer st;
		st = new StringTokenizer(firstLine);
		int num_queries = Integer.parseInt(st.nextToken());


		String op;
		int x;
	
		lsi = new LinkedList<Integer>();
		q = new LinkedList<Integer>();
		counts = new HashMap<Integer,Integer>();
	
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < num_queries; i++) {
			st = new StringTokenizer(br.readLine());
			op = st.nextToken();
			if (op.equals("a")) {
				x = Integer.parseInt(st.nextToken());
				sb.append(a(x) + "\n");
				
			} else {
				sb.append(p() + "\n");
			}

		}
		System.out.println(sb.toString());
	}


	public static int p() {
		if (q.peek() == null){
			return -1;
		}

		int head = q.remove();
		if (lsi.peekFirst() == head && counts.get(head) == 1) {
			lsi.removeFirst();
          	counts.put(head,0);
		} else {
			counts.put(head,counts.get(head)-1);
		}
		return head;
	}

	public static int a(int x) {
		q.add(x);
		
		int opa = -1;
		while (lsi.isEmpty() == false &&  lsi.peekLast() >= x) {
			lsi.removeLast();
			
		}
		if (lsi.isEmpty() == false) {
			opa = lsi.peekLast();
		}


		
		lsi.add(x);
      	if (counts.containsKey(x)) {
        	counts.put(x,counts.get(x)+1);
        } else {
        	counts.put(x,1);
        }
		
		return opa;
	}
}