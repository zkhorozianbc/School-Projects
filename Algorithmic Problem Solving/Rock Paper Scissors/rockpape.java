import java.io.*;
import java.util.*;


public class rockpape {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Map<Integer,String> digits = new HashMap<Integer,String>();
		digits.put(1,"one");
		digits.put(2,"two");
		digits.put(3,"three");
		digits.put(4,"four");
		digits.put(5,"five");
		digits.put(6,"six");
		digits.put(7,"seven");
		digits.put(8,"eight");
		digits.put(9,"nine");
		digits.put(0,"zero");

		Map<Integer,String> tens = new HashMap<Integer,String>();
		tens.put(11,"eleven");
		tens.put(12,"twelve");
		tens.put(13,"thirteen");
		tens.put(14,"fourteen");
		tens.put(15,"fifteen");
		tens.put(16,"sixteen");
		tens.put(17,"seventeen");
		tens.put(18,"eighteen");
		tens.put(19,"nineteen");
		tens.put(10,"ten");

		Map<Integer,String> okr = new HashMap<Integer,String>();
		okr.put(2,"twenty");
		okr.put(3,"thirty");
		okr.put(4,"forty");
		okr.put(5,"fifty");
		okr.put(6,"sixty");
		okr.put(7,"seventy");
		okr.put(8,"eighty");
		okr.put(9,"ninety");


		String line;
		while ((line = br.readLine()) != null) {
			line = line.trim();
			int n = Integer.parseInt(line);
			if (n == -1) {
				break;
			}
			int thou = n / 1000;
			int hun = (n % 1000) / 100;
			int haaa = (n % 1000) % 100;
			int te = ((n % 1000) % 100) / 10;
			int on = ((n % 1000) % 100) % 10;
			String num = "";
			if (n == 0) {
				System.out.println("zero");
				continue;
			}
			if (thou != 0) {
				num += digits.get(thou) + " thousand";
			}
			if (hun != 0) {
				if (num.length() != 0) {
						num += " ";
				}
				num += digits.get(hun) + " hundred";
			}
			if ((hun != 0 || thou != 0) && (te != 0 || on != 0)) {
				num += " and";
			}
			if (te != 0) {
				if (te == 1) {
					if (num.length() != 0) {
						num += " ";
					}
					num += tens.get(haaa);
					System.out.println(num);
					continue;
				} else {
					if (num.length() != 0) {
						num += " ";
					}
					num += okr.get(te);

					if (on != 0) {
						num += "-" + digits.get(on);
						System.out.println(num);
						continue;
					}
				}
			} 
			if (on != 0) {
				if (num.length() != 0) {
						num += " ";
				}
				num += digits.get(on);
			}
			System.out.println(num);
		}
	}
}





// public class rockpape {
// 	public static void main(String[] args) throws Exception {
// 		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
// 		StringTokenizer gesturesST = new StringTokenizer(br.readLine());
// 		Map<String,Integer> score = new HashMap<String,Integer>();
// 		int gestureNum = 1;
// 		while (gesturesST.hasMoreTokens()) {
// 			score.put(gesturesST.nextToken(),gestureNum);
// 			gestureNum += 1;
// 		}
		
// 		String game;
// 		gestureNum -= 1;
// 		while ((game = br.readLine()) != null) {
// 			StringTokenizer g = new StringTokenizer(game);
// 			int renne = score.get(g.nextToken());
// 			int john = score.get(g.nextToken());
// 			int result = john - renne;
// 			if (renne == gestureNum && john == 1) {
// 				System.out.println("win");
// 			}
// 			else if (renne == 1 && john == gestureNum) {
// 				System.out.println("lose");
// 			}
// 			else if (result == 1) {
// 				System.out.println("win");
// 			}
// 			else if (result == -1) {
// 				System.out.println("lose");
// 			} else {
// 				System.out.println("draw");
// 			}
// 		}

// 	}
// }








// public class rockpape {
// 	public static void main(String[] args) throws Exception {
// 		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
// 		String line;
// 		StringTokenizer group;
// 		int groupSum;
// 		StringBuilder sb = new StringBuilder();
// 		while ((line = br.readLine()) != null) {
// 			group = new StringTokenizer(line);
// 			groupSum = 0;
// 			while (group.hasMoreTokens()) {
// 				groupSum += Integer.parseInt(group.nextToken());
// 			}
// 			sb.append(groupSum + "\n");
// 		}
// 		System.out.println(sb.toString());

// 	}
// }