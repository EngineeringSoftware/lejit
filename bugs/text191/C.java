public class C {

    static int i;

    public static void m() {
        char[] arr = new char[2];
        while (arr[i - 1312433786] != 0) {
            i--;
        }
        return;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100_000; ++i) {
            try {
                C.m();
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
    }
}
