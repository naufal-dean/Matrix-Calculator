package tubes;

import static tubes.Console.*;

/**
 * Class Main merupakan kelas utama yang akan dipanggil.
 */
public class Main {
    /**
     * @deprecated Tidak dibutuhkan dalam bentuk obyek.
     */
    @Deprecated
    private Main() {}
    
    /**
     * Prosedur pertama untuk menjalankan program.
     * @param args Argumen-argumen dari command line.
     */
    public static void main(String[] args) {
        useColor();
        ConsoleApp.start();
    }
}
