public class C {
    static byte[] m(byte[] arg1) {
        byte[] b = new byte[-1];
        System.arraycopy(arg1, 0, b, 0, arg1.length);
        return b;
    }

    public static void main(String[] args) {
        int count = 0;
        for (int i = 0; i < 100_000; ++i) {
            try {
                System.out.println(m(null));
            } catch (Throwable e) {
                if (e instanceof java.lang.NegativeArraySizeException) {
                    count++;
                }
            }
        }
        System.out.println(count); // Should be 100_000
    }
}
