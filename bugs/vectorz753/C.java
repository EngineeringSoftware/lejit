public class C {

    private int idx = 1;
    private double[] arr;

    public void m() {
        for (int i = 0; i < 1; ++i) {
            double x = arr[idx]; // should throw NullPointerException
        }
    }

    public static void main(String[] args) {
        C c = new C();
        for (int i = 0; i < 10_000; ++i) {
            try {
                c.m();
            } catch (NullPointerException ignored) {
            }
        }
    }
}
