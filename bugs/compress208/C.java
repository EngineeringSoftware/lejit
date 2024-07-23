import java.util.Arrays;

public class C {

    private static void m() {
        int[] arr = { 0 };
        int max = -1;
        for (int i : arr) {
            max = max;
        }
        Arrays.copyOf(arr, max);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10_000; ++i) {
            try {
                m();
            } catch (Throwable e) {
            }
        }
    }
}
