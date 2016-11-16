import java.util.Scanner;

public class LCS3Java {
    private static int lcs3(int[] a, int[] b, int[] c) {
        int m = a.length;
        int n = b.length;
        int o = c.length;
        int[][][] values = new int[m+1][n+1][o+1];
        for (int i=0; i< m+1; i++){
            for (int j =0; j < n+1; j++){
                for (int k =0; k < o+1; k++){
                    if (i == 0 || j == 0 || k == 0){
                        values[i][j][k] = 0;
                        continue;
                    }
                    if (a[i-1] == b[j-1] && (a[i-1] == c[k-1])){
                        values[i][j][k] = 1 + values[i-1][j-1][k-1];
                    } else{
                        //max of all possible permutations
                        /*int first = values[i][j][k-1];
                        int second = values[i][j-1][k];
                        int third = values[i-1][j][k];
                        int fourth = values[i][j-1][k-1];
                        int fifth = values[i-1][j][k-1];
                        int sixth = values[i-1][j-1][k];
                        int max1 = Math.max(first, second);
                        int max2 = Math.max(third, fourth);
                        int max3 = Math.max(fifth, sixth);
                        int max4 = Math.max(max1, max2);*/
                        values[i][j][k] = Math.max(values[i - 1][j][k], Math.max(values[i][j - 1][k], values[i][j][k-1]));
                    }
                }
            }
        }
        return values[m][n][o];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int an = scanner.nextInt();
        int[] a = new int[an];
        for (int i = 0; i < an; i++) {
            a[i] = scanner.nextInt();
        }
        int bn = scanner.nextInt();
        int[] b = new int[bn];
        for (int i = 0; i < bn; i++) {
            b[i] = scanner.nextInt();
        }
        int cn = scanner.nextInt();
        int[] c = new int[cn];
        for (int i = 0; i < cn; i++) {
            c[i] = scanner.nextInt();
        }
        System.out.println(lcs3(a, b, c));
    }
}