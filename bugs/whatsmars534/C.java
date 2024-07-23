import java.lang.reflect.Field;

/**
 * To reproduce the bug, use JDK >= 18
 *
 * # Compilation up to level 1
 * java --add-opens java.base/java.lang=ALL-UNNAMED -XX:TieredStopAtLevel=1 C.java
 * # Output
 * 100000
 *
 * # Compilation up to level 4 (default)
 * java --add-opens java.base/java.lang=ALL-UNNAMED C.java
 * # Output (non-deterministic)
 * 35619
 */
public class C {

    public static void main(String[] args) throws Exception {
        int count = 0;
        for (int i = 0; i < 100_000; ++i) {
            try {
                Field f = Class.class.getDeclaredField("componentType");
                f.setAccessible(true);
                Object val = f.get(Runnable.class);
            } catch (IllegalArgumentException e) {
                count += 1;
            }
        }
        System.out.println(count);
    }
}
