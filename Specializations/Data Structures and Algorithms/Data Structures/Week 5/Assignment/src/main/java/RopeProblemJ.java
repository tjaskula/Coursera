import java.io.*;
import java.util.*;

class RopeProblemJ {
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

    static class Rope {
        Vertex root;

        void process(int i, int j, int k) {
            VertexPair leftMid = split(root, i);
            VertexPair midRight = split(leftMid.right, j - i + 2);
            Vertex mid = midRight.left;
            root = merge(leftMid.left, midRight.right);
            VertexPair leftRight = split(root, k + 1);
            root = merge(leftRight.left, mid);
            root = merge(root, leftRight.right);
        }

        String result() {
            StringBuilder buf = new StringBuilder();
            print(root, buf, new Stack<>());
            return buf.toString();
        }

        Rope(String s) {
            root = create(s);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            return print(root,stringBuilder, new Stack<>()).toString();
        }
    }

    public static void main(String[] args) throws IOException {
        new RopeProblemJ().run();
    }

    public void run() throws IOException {
        FastScanner in = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        Rope rope = new Rope(in.next());
        for (int q = in.nextInt(); q > 0; q--) {
            int i = in.nextInt();
            int j = in.nextInt();
            int k = in.nextInt();
            rope.process(i+1 , j+1, k);
        }
        out.println(rope.result());
        out.close();
    }

    static StringBuilder print(Vertex node, StringBuilder buf, Stack<Vertex> stack) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }

        // traverse the tree
        while (stack.size() > 0) {

            // visit the top node
            node = stack.pop();
            buf.append(node.value);
            if (node.right != null) {
                node = node.right;

                // the next node to be visited is the leftmost
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
        }
        return buf;
    }

    static Vertex create(String string) {
        Vertex root = null;
        Vertex prev = null;
        int le = string.length();
        for (int i = 0; i < le; i++) {
            Vertex v = new Vertex(le - i, string.charAt(i), null, null, prev);
            if (prev == null) {
                root = v;
            } else {
                prev.right = v;
            }
            prev = v;
        }
        return root;
    }

    static class Vertex {
        int sum;
        // Sum of all the keys in the subtree - remember to update
        // it after each operation that changes the tree.
        char value;
        Vertex left;
        Vertex right;
        Vertex parent;

        Vertex(int sum, char value, Vertex left, Vertex right, Vertex parent) {
            this.sum = sum;
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        @Override
        public String toString() {
            return String.valueOf(value) + ":" + print(this,new StringBuilder(), new Stack<>());
        }
    }

    static  void update(Vertex v) {
        if (v == null) return;
        v.sum = 1 + (v.left != null ? v.left.sum : 0) + (v.right != null ? v.right.sum : 0);
        if (v.left != null) {
            v.left.parent = v;
        }
        if (v.right != null) {
            v.right.parent = v;
        }
    }

    static void smallRotation(Vertex v) {
        Vertex parent = v.parent;
        if (parent == null) {
            return;
        }
        Vertex grandparent = v.parent.parent;
        if (parent.left == v) {
            Vertex m = v.right;
            v.right = parent;
            parent.left = m;
        } else {
            Vertex m = v.left;
            v.left = parent;
            parent.right = m;
        }
        update(parent);
        update(v);
        v.parent = grandparent;
        if (grandparent != null) {
            if (grandparent.left == parent) {
                grandparent.left = v;
            } else {
                grandparent.right = v;
            }
        }
    }

    static void bigRotation(Vertex v) {
        if (v.parent.left == v && v.parent.parent.left == v.parent) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else if (v.parent.right == v && v.parent.parent.right == v.parent) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else {
            // Zig-zag
            smallRotation(v);
            smallRotation(v);
        }
    }

    // Makes splay of the given vertex and returns the new root.
    static Vertex splay(Vertex v) {
        if (v == null) return null;
        while (v.parent != null) {
            if (v.parent.parent == null) {
                smallRotation(v);
                break;
            }
            bigRotation(v);
        }
        return v;
    }

    static class VertexPair {
        Vertex left;
        Vertex right;

        VertexPair() {
        }

        VertexPair(Vertex left, Vertex right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "{" + left + "} + {" + right + "}";
        }
    }

    // Searches for the given key in the tree with the given root
    // and calls splay for the deepest visited node after that.
    // Returns pair of the result and the new root.
    // If found, result is a pointer to the node with the given key.
    // Otherwise, result is a pointer to the node with the smallest
    // bigger key (next value in the order).
    // If the key is bigger than all keys in the tree,
    // then result is null.
    static VertexPair find(Vertex root, int target) {
        if (root.sum < target) return null;
        Vertex v = root;
        Vertex last = v;
        Vertex found = null;

        while (v != null) {
            last = v;
            int leftSum = 0;
            if (v.left != null) {
                leftSum = v.left.sum;
            }
            if (target == leftSum + 1) {
                found = v;
                break;
            } else if (target > leftSum) {
                v = v.right;
                target -= leftSum + 1;
            } else {
                v = v.left;
            }
        }
        root = splay(last);
        return new VertexPair(found, root);
    }

    static VertexPair split(Vertex root, int key) {
        if(root == null) return new VertexPair(null,null);
        VertexPair result = new VertexPair();

        VertexPair findAndRoot = find(root, key);
        if(findAndRoot != null){
            root = findAndRoot.right;
            result.right = findAndRoot.left;
        }
        if (result.right == null) {
            result.left = root;
            return result;
        }
        result.right = splay(result.right);
        result.left = result.right.left;
        result.right.left = null;
        if (result.left != null) {
            result.left.parent = null;
        }
        update(result.left);
        update(result.right);
        return result;
    }

    static Vertex merge(Vertex left, Vertex right) {
        if (left == null) return right;
        if (right == null) return left;
        while (right.left != null) {
            right = right.left;
        }
        right = splay(right);
        right.left = left;
        update(right);
        return right;
    }
}