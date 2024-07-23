public class C {

    static void m() {
        int[] a = new int[1];
        int j = 0;
        for (int i = 1; j <= 1; ++i, ++j) {
            a[i - 1] = 0;
        }
    }

    public static void main(String[] args) {
        int count = 0;
        int N = 100_000;
        for (int i = 0; i < N; ++i) {
            try {
                m();
            } catch (ArrayIndexOutOfBoundsException e) {
                count += 1;
            }
        }
        System.out.println(count);
    }
}
