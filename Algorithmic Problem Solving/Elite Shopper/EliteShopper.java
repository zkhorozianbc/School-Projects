import java.io.*;
import java.util.*;
public class EliteShopper {
	public static class SegmentTree {
		public static int INF = (int)1e9;
		public int[] val;

		// Build the tree for arr[l..r]
		public SegmentTree(ArrayList<Integer> arr, int l, int r) {
			val = new int[4 * (arr.size() + 1)];
			build(0, l, r, arr);
		}

		public void build(int k, int nL, int nR, ArrayList<Integer> arr) {
			if (nL == nR) {
		 	 val[k] = arr.get(nL);
		 	 return;
			}
			int mid = (nL + nR) / 2;
			build(2*k+1, nL, mid, arr);
			build(2*k+2, mid + 1, nR, arr);
			val[k] = Math.min(val[2*k+1], val[2*k+2]);
		}

		// Add delta to arr[pos]
		public void add(int k, int nL, int nR, int pos, int delta) {
			if (pos < nL || pos > nR) 
				return; // pos is outside the node's range
			if (nL == nR) { // on a leaf that manages a single element
			  	val[k] += delta;
			  	return;
			}
			int mid = (nL + nR) / 2;
			add(2*k+1, nL, mid, pos, delta);
			add(2*k+2, mid + 1, nR, pos, delta);
			val[k] = Math.min(val[2*k+1], val[2*k+2]);
		}

		// Get the minimum in arr[L..R]
		public int get(int k, int nL, int nR, int L, int R) {
			// query range is outside the node's range
			if (R < nL || L > nR) return INF;
			// node's range is entirely covered by the query range
			if (L <= nL && nR <= R) return val[k];
			int mid = (nL + nR) / 2;
			int ansLeft = get(2*k+1, nL, mid, L, R);
			int ansRight = get(2*k+2, mid + 1, nR, L, R);
			return Math.min(ansLeft, ansRight);
		}
}
	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("EliteShopperTest.txt"));

		int n = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		ArrayList<Integer> shops = new ArrayList<Integer>(n);
		for (int i = 0; i < n; i++)
			shops.add(Integer.parseInt(st.nextToken()));
		SegmentTree tree = new SegmentTree(shops,0, n);
		int q = Integer.parseInt(br.readLine());
		for (int i = 0; i < q; i++) {
			st = new StringTokenizer(br.readLine());
			st.nextToken();
			System.out.println(tree.get(0,0,n-1,Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken())));
		}
	}	

}