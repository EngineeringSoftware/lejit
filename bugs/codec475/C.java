import java.nio.charset.StandardCharsets;

public class C {

    private static int m(String s) {
        byte[] arr = s.getBytes(StandardCharsets.ISO_8859_1);
        return arr[2];
    }

    public static void main(String[] args) {
        long sum = 0;
        for (int i = 0; i < 10_000_000; ++i) {
            sum += m("\u8020\000\000\020");
        }
        System.out.println(sum); // expected 0
    }
    
}
