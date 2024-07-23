public class C {

    static void m() {
        int[] a = new int[1];
        for (int i = -1; i <= 0; ++i) {
            int x = a[i + Integer.MIN_VALUE]; // ArrayIndexOutOfBoundsException
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; ++i) {
            try {
                m();
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
    }
}
