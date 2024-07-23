public class C {
    public static int m(float[] array) {
        // should throw NegativeArraySizeException
        float[] subarray = new float[array.length - 1];
        return subarray.length;
    }

    public static void main(String[] args) {
        int sum = 0;
        for (int i = 0; i < 10000; ++i) {
            try {
                sum += m(new float[]{});
            } catch (NegativeArraySizeException ignored) {
            }
        }
        System.out.println(sum); // expected 0
    }
}
