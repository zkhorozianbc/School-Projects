import java.io.*;
import java.util.*;
import java.lang.Math;
public class MaXXor {

	static class Node {
		int val;
		Node[] children = new Node[2];
		public Node() {
			val = 0;
			children[0] = null;
			children[1] = null;
		}
	}


	public static Node root;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("MaxXXorTest.txt"));
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		StringBuilder out = new StringBuilder();

		int[] a = new int[n-1];
		int x;
		int y;
		int w;
		for (int i = 0; i < n-1; i++) {
			st = new StringTokenizer(br.readLine());
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			w = Integer.parseInt(st.nextToken());
			a[Math.min(x,y) -1] = w;
		}
		System.out.println(getMax(a,a.length));
	}


	public static void insert(int prev) {
		Node trav = root;
		for (int i = 31; i >= 0; i--) {
            int val = (prev & (1<<i)) >=1 ? 1 : 0; 
            if (trav.children[val] == null) 
                trav.children[val] = new Node(); 
            trav = trav.children[val];
		}
		trav.val = prev;
	}
    public static int getMax(int a[], int n) {
        root = new Node(); 
        insert(0); 
        int result = Integer.MIN_VALUE; 
        int prev = 0; 
        for (int i=0; i<n; i++) { 
            prev = prev^a[i]; 
            insert(prev); 
            result = Math.max(result, search(prev)); 
        } 
        return result; 
    } 
	public static int search(int prev) {
		Node trav = root; 
        for (int i= 31; i>=0; i--) { 
            int val = (prev & (1<<i)) >= 1 ? 1 : 0; 
            if (trav.children[1-val] != null) 
                trav = trav.children[1-val];
            else if (trav.children[val] != null) 
                trav = trav.children[val]; 
        } 
        return prev^trav.val;
	}

}