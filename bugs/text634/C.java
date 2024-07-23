public class C {
    static int[] a = new int[1];

    static void m() {
        int j = 0;
        for (int i = 0; (boolean) (j >= 0) && j++ < 2; i--) {
            a[i] = 0;
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
