import java.io.*;
import java.util.*;
import java.lang.Math;
public class AutoCompletion {

	static class Node {
		char c;
		Map<Character,Node> children;
		Map<Character,Boolean> thickness;
		boolean end;
		int count;
		int weight;
		public Node(char c, int w) {
			this.c = c;
			children = new HashMap<Character,Node>();
			thickness = new HashMap<Character,Boolean>();
			count = 1;
			weight = w;
		}
	}
	static Queue<Node> pq = new PriorityQueue<Node>(new Comparator<Node>() {
				public int compare(Node x, Node y) {
					return y.weight - x.weight;
					
			}});
	// static Comparator<Node> firstRank =  new Comparator<Node>() {
 //        public int compare(Node x, Node y) {
 //            return  y.weight - x.weight;
 //        }
 //    };
	static <K, V> Map<K, V> filterByValue(Map<K, V> map, Predicate<V> predicate) {
	    return map.entrySet()
	            .stream()
	            .filter(entry -> predicate.test(entry.getValue()))
	            .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}
	public static Node root;
	public static Map<Character,Integer> weights;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("AutoCompletionTest.txt"));
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder out = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		weights = new HashMap<Character,Integer>();
		char empty = '$';
		root = new Node(empty,-1);
		String word;
		int w;
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			word = st.nextToken();
			w = Integer.parseInt(st.nextToken());
			insert(word,w);
		}
		st = new StringTokenizer(br.readLine());
		int q = Integer.parseInt(st.nextToken());
		String keyStrokes;
		for (int i = 0; i < q; i++) {
			st = new StringTokenizer(br.readLine());
			keyStrokes = st.nextToken();
			System.out.println(search(keyStrokes));
		}
	}

	public static String search(String keyStrokes) {
		StringBuilder sb = new StringBuilder();
		char c;
		Node trav = root;
		boolean flag = false;
		for (int i = 0; i < keyStrokes.length(); i++) {
			c = keyStrokes.charAt(i);

			if (flag && (i < keyStrokes.length() - 1)) {
				sb.append(c);
				continue;
			}
			
			if (!c.equals('#')) {
				sb.append(c);
				if (!trav.containsKey(c)) {
					flag = true;
					continue;
				}
				trav = trav.children.get(c);
			}

			Queue<Node> q = new LinkedList<Node>();
			filterByValue(trav.children.get(c), value -> value.weight == trav.weight);












		}

	}

	public static void insert(String word, int w) {
		Node trav = root;
		char c;
		
		int l = word.length();
		for (int i = 0; i < l; i++) {
			c = word.charAt(i);
			Node next;
			if (!trav.children.containsKey(c)) {
				next = new Node(c, w);
				trav.children.put(c,next);
			} else {
				next = trav.children.get(c);
				next.weight = w > next.weight ? w: next.weight;
			}
			trav = next;
		}
		trav.children.put('!',new Node('!',w));
	}






}
		