import java.util.*;

public class PointsAndSegments {
    private static Random random = new Random();

    private static int[] fastCountSegments(int[] starts, int[] ends, int[] points) {
        int[] cnt = new int[points.length];

        Arrays.sort(starts);
        Arrays.sort(ends);

        for(int i=0;i<points.length;i++) {
            int index1 = 0;
            for(int j=0;j<starts.length;j++) {
                if(starts[index1] <= points[i])
                    index1++;
                else
                    break;
            }

            int index2 = 0;
            for(int j=0;j<ends.length;j++) {
                if(ends[index2] < points[i])
                    index2++;
                else
                    break;
            }

            cnt[i] = index1 - index2;
        }

        return cnt;
    }

    private static int[] naiveCountSegments(int[] starts, int[] ends, int[] points) {
        int[] cnt = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < starts.length; j++) {
                if (starts[j] <= points[i] && points[i] <= ends[j]) {
                    cnt[i]++;
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n, m;
        n = scanner.nextInt();
        m = scanner.nextInt();
        int[] starts = new int[n];
        int[] ends = new int[n];
        int[] points = new int[m];
        for (int i = 0; i < n; i++) {
            starts[i] = scanner.nextInt();
            ends[i] = scanner.nextInt();
        }
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }
        //use fastCountSegments
        int[] cnt = fastCountSegments(starts, ends, points);
        for (int x : cnt) {
            System.out.print(x + " ");
        }
    }
}