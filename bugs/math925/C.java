public class C {

    public static long m(int x, int y) {
        return (long) y / x;
    }

    public static void main(String[] args) {
        int count = 0;
        for (int i = 0; i < 10000; ++i) {
            try {
                m(0, 1);
            } catch (ArithmeticException e) {
                count += 1;
            }
        }
        System.out.println(count); // expect 10000
    }
}
