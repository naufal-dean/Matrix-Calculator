package tubes;

/**
 * Enum Method untuk mengidentifikasi jenis metode untuk menjalankan implementasi algoritma matriks.
 */
public enum Method {
    /**
     * Metode Eliminasi Gauss.
     */
    GAUSS,
    
    /**
     * Metode Eliminasi Gauss-Jordan.
     */
    GAUSS_JORDAN,
    
    /**
     * Metode matriks balikan.
     */
    INVERSE,
    
    /**
     * Kaidah Cramer.
     */
    CRAMER,
    
    /**
     * Metode ekspansi kofaktor.
     */
    COFACTOR_EXPANSION,
    
    /**
     * Metode pemanfaatan adjoin.
     */
    ADJOIN;
}