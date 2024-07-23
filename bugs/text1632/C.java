public class C {
    static class SM {
        void sMatch(char[] buffer, int pos) {
            boolean x = ' ' == buffer[pos];
        }
    }

    static SM sm = new SM();

    static void m() {
        char[] buffer = { '.' };
        read(buffer, 0);
        read(buffer, -1);
    }

    static void read(char[] buffer, int pos) {
        for (int j = 0; j < 10; ++j) {
            match(buffer, pos);
            sm.sMatch(buffer, pos);
        }
    }

    static void match(char[] buffer, int pos) {
        char x = buffer[pos];
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100_000; ++i) {
            try {
                m();
            } catch (IndexOutOfBoundsException e) {
            }
        }
    }
}
