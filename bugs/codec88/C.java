public class C {
    
    public static int m(int len) {
        int[] arr = new int[8];
        for (int i = 10000000, j = 0; (boolean) (i >= 1) && j < 100; i--, j++) {
            // should not enter inner loop.
            for (int k = 0; len < arr.length; ++k) {
                // throws ArithmeticException to break out.
                int x = 1 / 0;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int sum = 0;
        
        for (int i = 0; i < 100_000; ++i) {
            try {
                m(13);
            } catch (ArithmeticException e) {
                sum += 1;
            }
        }

        System.out.println(sum); // expected 0
    }
}
