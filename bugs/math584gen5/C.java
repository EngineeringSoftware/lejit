public class C {

    static void m(int n) {
        int[] a = new int[n];
        for (int i = 0; i < 1; i++) {
            int x = a[i % -1]; // ArrayIndexOutOfBoundsException
        }
    }

    public static void main(String[] args) {
        int count = 0;
        for (int i = 0; i < 1000; ++i) {
            try {
                m(0);
            } catch (ArrayIndexOutOfBoundsException e) {
                count += 1;
            }
        }
        System.out.println(count); // expect 1000
    }
}
