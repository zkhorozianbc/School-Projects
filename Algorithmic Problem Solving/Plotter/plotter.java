import java.io.*;
import java.util.*;

public class plotter {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		StringBuilder out = new StringBuilder();
		


		while (in.hasNextLine()) {
			Map<Character,Integer> counts;
			String s;
			int fsn = 101;
			Character fsc = 'a';
			int ssn = 101;
			Character ssc = 'a';
			int opa = -1;
			counts = new HashMap<Character,Integer>();
			s = in.nextLine();
			
			for (Character c : s.toCharArray()) {
				if (counts.containsKey(c)){
					opa = counts.get(c) + 1;
					counts.put(c,opa);
				} else {
					opa = 1;
					counts.put(c,1);
				}
				if (opa < fsn) {
					ssn = fsn;
					ssc = fsc;
					fsn = opa;
					fsc = c;
				}
				else if (opa < ssn) {
					ssn = opa;
					ssc = c;
				}
			}
			// System.out.println(fsn - ssn);
			int cop = s.length() - (fsn + ssn);
			System.out.println(cop);
			// int total = 0;
			// int k = 0;
			// for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
			//     Character key = entry.getKey();
			//     Integer value = entry.getValue();

			//     if (key.equals(fsc) == false && key.equals(ssc) == false){
			//     	total += value;
			//     	System.out.println(key);
			//     	System.out.println(value);
			//     }
			//     k += 1
			//     if (cop())

			// }
		 //    System.out.println(total);






		}


	}
}