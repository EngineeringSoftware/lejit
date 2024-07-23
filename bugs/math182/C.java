public class C {

    public static void m() {
        int X = 536_870_908;
        int[] a = new int[X + 1]; // Object[] also works
        a[X] = 1;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1_000; ++i) {
            m();
        }
    }
}
