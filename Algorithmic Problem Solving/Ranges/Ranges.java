import java.util.LinkedList;

public class Ranges {

  public static int countRanges(int[] s, int k) {
    int i = 0;
    int min = s[0];
    int max = s[0];
    LinkedList<Integer> ascending = new LinkedList();
    ascending.add(0);
    LinkedList<Integer> descending = new LinkedList();
    descending.add(0);
    System.out.println("[0...0]");
    int count = 1;
    for (int j = 1; j < s.length; j++) {
      int value = s[j];

      while (!ascending.isEmpty() && s[ascending.getLast()] > value) {
        ascending.removeLast();
      }
      ascending.add(j);

      while (!descending.isEmpty() && s[descending.getLast()] < value) {
        descending.removeLast();
      }
      descending.add(j);

      if (s[j] > max) {
        max = s[j];
        if (max - min >= k) {
          while(max - s[ascending.getFirst()] > k) {
            ascending.removeFirst();
          }
          i = ascending.getFirst();
          min = s[i];
          while (descending.getFirst() < i) {
            descending.removeFirst();
          }
        }
      } else if (s[j] < min) {
        min = s[j];
        if (max - min >= k) {
          while(s[descending.getFirst()] - min > k) {
            descending.removeFirst();
          }
          i = descending.getFirst();
          max = s[i];
          while (ascending.getFirst() < i) {
            ascending.removeFirst();
          }
        }
      }
      System.out.println("[" + i + "..." + j + "]");
      count += j - i + 1;  // New subarrays involving j
    }
    return count;
  }


  public static void main(String[] args) {
    final int[] s = new int[] {3, 4, 2, -2, 1};
    final int k = 3;
    System.out.println("count: " + countRanges(s, k));
  }
}