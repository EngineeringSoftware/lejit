import java.util.ArrayList;
import java.util.List;

public class C {
    public static void encode(String str) {
        String[] res = str.split("\\s+");

        List<String> words = new ArrayList<String>(); 
        for (int i = 0; i < 1000; i++) {
            words.add("take up some space");
        }

        for (String word : words) {
            encode(word);
        }
    }

    public static void main(String[] args) {
        try {
            encode("any_string");
        } catch (Throwable e) {
            System.out.println(e);
        }
    }
}
