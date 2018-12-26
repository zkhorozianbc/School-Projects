import java.io.*;
import java.util.*;

public class SumQueries {
	public static Map<Integer,Integer> counts;
	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int q = in.nextInt();
		counts = new HashMap<Integer,Integer>();
		int x;
		for (int i = 0; i < n; i++) {
			x = in.nextInt();
			if (counts.containsKey(x) == false) {
				counts.put(x,1);
			} else {
				counts.put(x,counts.get(x)+1);
			}
		}

		int t;
		long numPairs;
		
		Set<Integer> seen = new HashSet<Integer>(); 
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < q; i++) {
			t = in.nextInt();
			numPairs = 0;
			seen.clear();
			for (Map.Entry<Integer,Integer> pair: counts.entrySet()) {
				int k = pair.getKey();
				int freq = pair.getValue();
				if (counts.containsKey(t-k) && seen.contains(k) == false) {
					if (t == 2*k) {
						if (freq == 1) {
							continue;
						}

						numPairs += nChooseTwo(freq,2);
						seen.add(t-k);
					} else {
		
						
						numPairs += (freq*counts.get(t-k));
						seen.add(t-k);
						
					}

				}
			}
			
			sb.append(numPairs + "\n");
		}
		System.out.println(sb.toString());


	}


	public static long nChooseTwo(int n, int k) { 
        int r = 1;
        if (k > (n - k)) {
            k = n - k; 
        }
      
        for (int i = 0; i < k; ++i) { 
        	r *= (n - i); 
        	r /= (i + 1); 
        } 
      
        return (long)r; 
    } 
}