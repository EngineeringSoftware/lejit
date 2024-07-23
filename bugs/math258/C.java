public class C {

    private double q0;
    private double q1;
    private double q2;
    private double q3;

    public C(double a0, double a1, double a2, double a3) {
        this.q0 = a3;
        this.q1 = a1;
        this.q2 = 0;
        this.q3 = 0;
    }

    public static double m(double d) {
        C c = new C(0, 1.0, 0, d % d);
        return c.q1;
    }

    public static void main(String[] args) {
        double sum = 0;
        for (int i = 0; i < 100_000; ++i) {
            // m(1.0) is expected to be 1.0, but after a few thousand
            // iterations, m(1.0) returns 0.0.
            sum += m(1.0);
        }
        System.out.println(sum); // Expect 100000.0
    }
}
