import java.util.*;
import java.io.*;

public class tree_height {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }
        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public class TreeHeight {
        int n;
        int parent[];

        void read() throws IOException {
            FastScanner in = new FastScanner();
            n = in.nextInt();
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = in.nextInt();
            }
        }

        int computeHeight() {
            int maxHeight = 0;
            int[] heights = new int[parent.length];
            for (int vertex = 0; vertex < n; vertex++) {
                if (heights[vertex] != 0)       // We've been here before
                    continue;
                int height = 0;
                for (int i = vertex; i != -1; i = parent[i]) {
                    if (heights[i] != 0) {     // We've been here before
                        height += heights[i];
                        break;
                    }
                    height++;
                }
                maxHeight = Math.max(maxHeight, height);
                // Now we store the results in the array to save us time in the future.
                for (int i = vertex; i != -1; i = parent[i]) {
                    if (heights[i] != 0)
                        break;
                    heights[i] = height--;
                }
            }
            return maxHeight;
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new tree_height().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }
    public void run() throws IOException {
        TreeHeight tree = new TreeHeight();
        tree.read();
        System.out.println(tree.computeHeight());
    }
}