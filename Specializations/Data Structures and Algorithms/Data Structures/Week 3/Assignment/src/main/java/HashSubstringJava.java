import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;


public class HashSubstringJava {

    private static FastScanner in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        Data data = readInput();
        printOccurrences(new RobinKarp(data).run());
        out.close();
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

    private static List<Integer> getOccurrences(Data input) {
        String s = input.pattern, t = input.text;
        int m = s.length(), n = t.length();
        List<Integer> occurrences = new ArrayList<Integer>();
        for (int i = 0; i + m <= n; ++i) {
            boolean equal = true;
            for (int j = 0; j < m; ++j) {
                if (s.charAt(j) != t.charAt(i + j)) {
                    equal = false;
                    break;
                }
            }
            if (equal)
                occurrences.add(i);
        }
        return occurrences;
    }

    static class RobinKarp{
        String text = null;
        String pattern = null;
        private int prime =  (int) Math.pow(10, 9) +7;
        private int multiplier = 1;

        RobinKarp(Data data){
            this.text = data.text;
            this.pattern = data.pattern;
            Random random = new Random();
            multiplier = 1;
        }

        private long polyHash(String s, int index) {
            long hash = 0;
            for (int i = s.length() - 1; i >= index; --i)
                hash = (hash * multiplier + s.charAt(i)) % prime;
            return hash;
        }

        private long[] precomputeHashes(){
            int textLength = text.length();
            int patternLength = pattern.length();
            long[] h = new long[textLength - patternLength + 1];
            //init last value in array
            h[h.length-1] = polyHash(text, h.length-1);
            long y = 1;
            for (int i =0; i < patternLength; i++){
                y = (y * multiplier)%prime;
            }
            int start_length = h.length-2;
            for (int i = start_length; i >=0; i--){
                int t = text.charAt(i);
                int nt = text.charAt(i + patternLength);
                h[i] = (multiplier * h[i+1]) + t -(y * nt) %prime;
            }
            return h;
        }

        private List<Integer> run(){
            List<Integer> result = new ArrayList<>();
            long patternHash = polyHash(pattern, 0);
            long[] h = precomputeHashes();
            for (int i=0; i < (text.length() - pattern.length() +1); i++){
                if (h[i] != patternHash){
                    continue;
                }
                if (isEqual(text, pattern, i)){
                    result.add(i);
                }

            }
            return result;
        }

        private boolean isEqual(String t, String p, int index){
            int length = p.length();
            for(int i =0; i< length ;i++){
                if (t.charAt(index++) != p.charAt(i)){
                    return false;
                }
            }
            return true;
        }

    }

    static class Data {
        String pattern;
        String text;

        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
        }

    }



    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}