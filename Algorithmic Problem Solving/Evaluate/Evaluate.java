import java.io.*;
import java.util.*;

public class Evaluate {
    public static void main(String[] args) throws Exception{ 
        Stack<Character> ops  = new Stack<Character>();
        Stack<Double> vals = new Stack<Double>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String eq = br.readLine().trim();
        int i = 0;
        while (i < eq.length()) {
            Character s = eq.charAt(i);
            if      (s.equals("("))               ;
            else if (s.equals("+"))    ops.push(s);
            else if (s.equals("-"))    ops.push(s);
            else if (s.equals("*"))    ops.push(s);
            else if (s.equals("/"))    ops.push(s);
            else if (s.equals("sqrt")) ops.push(s);
            else if (s.equals(")")) {
                Character op = ops.pop();
                double v = vals.pop();
                if      (op.equals("+"))    v = vals.pop() + v;
                else if (op.equals("-"))    v = vals.pop() - v;
                else if (op.equals("*"))    v = vals.pop() * v;
                else if (op.equals("/"))    v = vals.pop() / v;
                else if (op.equals("sqrt")) v = Math.sqrt(v);
                vals.push(v);
            }
            else vals.push(Double.parseDouble(String.valueOf(s)));
            i += 1;
        }
        System.out.println(vals.pop());
    }
}