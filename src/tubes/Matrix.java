package tubes;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Class Matrix digunakan untuk melakukan operasi-operasi matriks dan menjalankan implementasi algoritma-algoritma dari berbagai macam metode.
 */
public class Matrix {
    /**
     * Elemen dari matriks.
     */
    private BigDecimal[][] content;
    /**
     * Panjang / indeks maksimum baris matriks.
     */
    private int maxR;
    /**
     * Panjang / indeks maksimum kolom matriks.
     */
    private int maxC;
    /**
     * Perbandingan determinan baru dengan determinan sebelumnya.
     */
    private BigDecimal scaledDet = BigDecimal.ONE;

    //** Konstruktor **//
    /**
     * F.S Membuat matrix baru dengan dimensi maxR*maxC.
     * @param maxR Panjang / indeks maksimum baris matriks.
     * @param maxC Panjang / indeks maksimum kolom matriks.
     */
    public Matrix(int maxR, int maxC) {
        this.maxR = maxR;
        this.maxC = maxC;
        this.content = new BigDecimal[maxR+1][maxC+1];
        for (int i = 0; i <= maxR; i++)
            for (int j = 0; j <= maxC; j++)
                this.content[i][j] = BigDecimal.ZERO;
    }

    /**
     * F.S Membuat Matrix baru dengan elemen pada sebuah array of array of BigDecimal.
     * @param arr Konten isi matriks yang akan dibuat.
     */
    public Matrix(BigDecimal[][] arr) {
        this(arr.length, arr.length > 0 ? arr[0].length : 0);
        setContent(arr);
    }

    //** Selektor **//
    //-- Selektor: Get --//
    /**
     * F.S Mengembalikan panjang / indeks maksimum baris matriks.
     * @return Panjang / indeks maksimum baris matriks.
     */
    public int getMaxRow() {
      return this.maxR;
    }

    /**
     * F.S Mengembalikan panjang / indeks maksimum kolom matriks.
     * @return Panjang / indeks maksimum kolom matriks.
     */
    public int getMaxColumn() {
        return this.maxC;
    }

    /**
     * F.S Mengembalikan isi elemen matriks.
     * @return Konten isi elemen matriks dalam bentuk tipe data array of array of double.
     */
    public BigDecimal[][] getContent() {
        return this.content;
    }

    /**
     * F.S Mendapatkan elemen matriks dengan indeks baris r dan kolom c.
     * @param r Indeks baris.
     * @param c Indeks kolom.
     * @return Elemen matriks.
     */
    public BigDecimal getElement(int r, int c) {
        return this.content[r][c];
    }

    /**
     * F.S Mendapatkan baris matrix dengan indeks baris.
     * @param r Indeks baris.
     * @return Satu baris elemen-elemen matriks dalam bentuk tipe data array of double.
     */
    public BigDecimal[] getRow(int r) {
        return Arrays.copyOf(this.content[r], this.content[r].length);
    }

    /**
     * F.S Mendapatkan baris matrix dengan indeks kolom.
     * @param c Indeks kolom.
     * @return Satu kolom elemen-elemen matriks dalam bentuk tipe data array of double.
     */
    public BigDecimal[] getColumn(int c) {
        BigDecimal[] col = new BigDecimal[this.maxR];
        for (int i = 0; i < this.maxR; i++)
            col[i] = this.getElement(i+1, c);
        return col;
    }

    //-- Selektor: Set --//
    /**
     * I.S maxR &gt;= 0.<br>
     * F.S mengganti nilai maxR pada matriks.
     * @param maxR Nilai maksimum baris matriks yang baru.
     */
    public void setMaxRow(int maxR) {
        this.maxR = maxR;
    }

    /**
     * I.S maxC &gt;= 0.<br>
     * F.S mengganti nilai maxC pada matriks.
     * @param maxC Nilai maksimum kolom matriks yang baru.
     */
    public void setMaxColumn(int maxC) {
        this.maxC = maxC;
    }

    /**
     * I.S Dimensi matrix dan array of array arr adalah sama.<br>
     * F.S Mengganti elemen pada matriks.
     * @param arr Konten matriks yang baru.
     */
    public void setContent(BigDecimal[][] arr) {
        for (int r = 0; r < this.maxR; r++)
            for (int c = 0; c < this.maxC; c++)
                this.content[r+1][c+1] = arr[r][c];
    }

    /**
     * I.S r dan c merupakan indeks valid pada matriks.<br>
     * F.S Mengganti nilai elemen matrix  berindeks baris r dan berindeks kolom c dengan nilai val.
     * @param r Indeks baris matriks.
     * @param c Indeks kolom matriks.
     * @param val Nilai elemen matriks.
     */
    public void setElement(int r, int c, BigDecimal val) {
        this.content[r][c] = val;
    }

    /**
     * I.S r merupkan indeks valid matriks dan row mempunyai nilai panjang yang sama dengan maxC.<br>
     * F.S mengganti nilai elemen matriks berindeks baris r dengan row.
     * @param r Indeks baris matriks.
     * @param row Konten baris matriks.
     */
    public void setRow(int r, BigDecimal[] row) {
        this.content[r] = Arrays.copyOf(row, row.length);
    }

    /**
     * I.S c merupkan indeks valid matrix dan kolom mempunyai nilai panjang.
     * @param c Indeks kolom matriks.
     * @param col Konten kolom matriks.
     */
    public void setColumn(int c, BigDecimal[] col) {
        for (int i = 0; i < this.maxR; i++) {
            this.setElement(i+1, c, col[i]);
        }
    }

    //** Utility **//
    /**
     * F.S menduplikasi keseluruhan atribut matriks.
     * @return Matriks baru hasil duplikasi.
     */
    public Matrix copyMatrix() {
        Matrix M = new Matrix(this.subMatrixContent(1, 1));
        M.scaledDet = this.scaledDet;
        return M;
    }

    /**
     * I.S 0 &lt;= startR &lt;= maxR dan 0 &lt;= startC &lt;= maxC.<br>
     * F.S membuat submatrix yang dimulai dari indeks baris startR dan indeks.
     * @param startR Indeks mulai baris matriks.
     * @param startC Indeks mulai kolom matriks.
     * @return Konten matriks.
     */
    public BigDecimal[][] subMatrixContent(int startR, int startC) {
        return subMatrixContent(startR, startC, this.maxR, this.maxC);
    }

    /**
     * I.S 0 &lt;= startR &lt;= maxR dan 0 &lt;= startC &lt;= maxC.<br>
     * F.S membuat submatrix yang dimulai dari indeks baris startR dan indeks kolom startC hingga indeks baris endR dan indeks kolom endC.
     * @param startR Indeks mulai baris matriks.
     * @param startC Indeks mulai kolom matriks.
     * @param endR Indeks akhir baris matriks.
     * @param endC Indeks akhir kolom matriks.
     * @return Konten matriks.
     */
    public BigDecimal[][] subMatrixContent(int startR, int startC, int endR, int endC) {
        BigDecimal[][] sub = new BigDecimal[endR + 1 - startR][endC + 1 - startC];
        for (int i = 0; i < (endR + 1 - startR); i++)
            sub[i] = Arrays.copyOfRange(this.content[i + startR], startC, endC + 1);
        return sub;
    }

    /**
     * I.S Jumlah baris matriks ini == mInput.<br>
     * F.S Menambahkan kolom pada suatu matriks.
     * @param mInput Matriks yang ditempelkan pada matriks ini.
     * @return Matriks baru hasil penempelan.
     */
    public Matrix appendMatrix(Matrix mInput) {
        BigDecimal[][] m1 = this.subMatrixContent(1, 1);
        BigDecimal[][] m2 = mInput.subMatrixContent(1, 1);
        BigDecimal[][] tempVal = new BigDecimal[m1.length][m1[0].length + m2[0].length];

        for (int i = 0; i < m1.length; i++) {
            tempVal[i] = Arrays.copyOf(m1[i], tempVal[i].length);
            for (int j = 0; j < m2[i].length; j++)
                tempVal[i][m1[i].length + j] = m2[i][j];
        }
        return new Matrix(tempVal);
    }

    /**
     * Representasi obyek dalam bentuk string.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 1; r <= this.maxR; r++)
            for (int c = 1; c <= this.maxC; c++) {
                BigDecimal v = this.content[r][c];
                sb.append(BD.format(BD.eq0(v) ? BigDecimal.ZERO : v) + (c == this.maxC ? r < this.maxR ? "\n" : "" : " "));
            }
        return sb.toString();
    }

    //** Operasi pada matriks **//
    /**
     * I.S maxC matriks ini sama dengan maxR matriks m.<br>
     * F.S menghasilkan matrix yang merupakan hasil perkalian matriks ini dengan matriks m.
     * @param m Matriks yang digunakan untuk perkalian.
     * @return Matriks baru hasil perkalian.
     */
    public Matrix multiplyOPR(Matrix m) {
        if (this.maxC != m.maxR)
            throw new MatrixException(MatrixErrorIdentifier.MULTIPLY_ERROR);
        BigDecimal temp = BigDecimal.ZERO;
        Matrix res = new Matrix(this.maxR, m.maxC);
        for (int i = 1; i <= this.maxR; i++) {
            for (int j = 1; j <= m.maxC; j++) {
                temp = BigDecimal.ZERO;
                for (int k = 1; k <= this.maxC; k++)
                    temp = temp.add(this.getElement(i, k).multiply(m.getElement(k, j)));
                res.setElement(i, j, temp);
            }
        }
        return res;
    }

    /**
     * F.S Menghasilkan matriks yang merupakan perkalian skalar matriks ini dengan x.
     * @param x Nilai bilangan riil untuk dikalikan ke matriks.
     * @return Matriks baru hasil perkalian skalar matriks.
     */
    public Matrix scalarMultiplyOPR(BigDecimal x) {
        Matrix res = copyMatrix();
        for (int i = 1; i <= this.maxR; i++)
            for (int j = 1; j <= this.maxC; j++)
                res.content[i][j] = res.content[i][j].multiply(x);
        return res;
    }

    /**
     * I.S Dimensi matriks ini sama dengan matriks matrix m.<br>
     * F.S menghasilkan matriks yang merupakan hasil penjumlahan matriks ini dengan matriks m.
     * @param m Matriks yang digunakan untuk dijumlahkan pada matriks ini.
     * @return Matriks baru hasil penjumlahan matriks.
     */
    public Matrix addOPR(Matrix m) {
        if (this.maxR != m.maxR || this.maxC != m.maxC)
            throw new MatrixException(MatrixErrorIdentifier.DIFFERENT_SIZE_ERROR);
        Matrix res = copyMatrix();
        for (int i = 1; i <= maxR; i++)
            for (int j = 1; j <= maxC; j++)
                res.setElement(i, j, res.getElement(i,j).add(m.getElement(i,j)));
        return res;
    }

    /**
     * F.S Menghasilkan true jika objek o sama dengan obyek matriks ini.
     * @param o Obyek yang akan dikomparasikan.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Matrix))
            return false;
        Matrix m = (Matrix)o;
        if (m.getMaxRow() != this.maxR || m.getMaxColumn() != this.maxC)
            return false;
        for (int r = 1; r <= this.maxR; ++r)
            for (int c = 1; c <= this.maxC; ++c)
                if (!BD.eqTest(m.getElement(r, c), this.content[r][c]))
                    return false;
        return true;
    }

    //** OBE **//
    /**
     * I.S r1 dan r2 merupakan indeks baris yang valid pada matrix ini.<br>
     * F.S Menukar baris r1 dan r2.
     * @param r1 Indeks baris matriks.
     * @param r2 Indeks baris matriks.
     */
    private void swapOBE(int r1, int r2) {
        BigDecimal[] temp = this.content[r1];
        this.content[r1] = this.content[r2];
        this.content[r2] = temp;
    }

    /**
     * I.S r merupakan indeks baris valid matriks.<br>
     * F.S Mengalikan baris r dengan bilangan skalar scale.
     * @param r Indeks baris matriks.
     * @param scale Skala dalam bentuk nilai riil.
     */
    private void scaleOBE(int r, BigDecimal scale) {
        for (int c = 1; c <= maxC; c++)
            this.content[r][c] = this.content[r][c].multiply(scale);
    }

    /**
     * I.S r1 dan r2 merupakan indeks baris yang valid pada matrix ini.<br>
     * F.S Menambahkan elemen-elemen di baris r1 dengan baris r2 dengan skala scale.
     * @param r1 Indeks baris matriks.
     * @param r2 Indeks baris matriks.
     * @param scale Skala dalam bentuk nilai riil.
     */
    private void addOBE(int r1, int r2, BigDecimal scale) {
        for (int c = 1; c <= maxC; c++)
            this.content[r1][c] = this.content[r1][c].add(this.content[r2][c].multiply(scale));
    }

    //** Fungsi matriks **//
    /**
     * I.S Matriks harus bujur sangkar.<br>
     * F.S Menghasilkan determinan matrix ini dengan metode method.
     * @param method Metode mencari determinan.
     * @return Nilai riil hasil determinan matriks.
     */
    public BigDecimal getDeterminan(Method method) {
        if (this.maxR != this.maxC)
            throw new MatrixException(MatrixErrorIdentifier.NOT_SQUARE_ERROR);
        switch (method) {
            case GAUSS: {
                BigDecimal newDet = BigDecimal.ONE;
                Matrix M = this.getEchelonForm(this.maxC);
                for (int i = 1; i <= M.maxC; i++)
                    newDet = newDet.multiply(M.getElement(i, i));
                return newDet.divide(M.scaledDet, BD.SCALE, RoundingMode.HALF_UP);
            }
            case GAUSS_JORDAN: {
                BigDecimal newDet = BigDecimal.ONE;
                Matrix M = this.getReducedEchelonForm(this.maxC);
                for (int i = 1; i <= M.maxC; i++)
                    newDet = newDet.multiply(M.getElement(i, i));
                return newDet.divide(M.scaledDet, BD.SCALE, RoundingMode.HALF_UP);
            }
            case INVERSE: {
                return BigDecimal.ONE.divide(this.getInverseMatrix(Method.GAUSS_JORDAN).getDeterminan(Method.COFACTOR_EXPANSION), BD.SCALE, RoundingMode.HALF_UP);
            }
            case COFACTOR_EXPANSION: {
                if (this.maxR == 1)
                    return this.content[1][1];
                BigDecimal res = BigDecimal.ZERO;
                for (int j = 1; j <= this.maxC; j++)
                    res = res.add(this.content[1][j].multiply(this.getEntryMatrix(1, j).getDeterminan(Method.COFACTOR_EXPANSION).multiply(j%2 == 0 ? BigDecimal.ONE.negate() : BigDecimal.ONE)));
                return res;
            }
            default:
                throw new RuntimeException("Method " + method + " is not valid!");
        }
    }

    /**
     * I.S size &gt;=0<br>
     * F.S Menghasilkan matrix identitas dengan dimensi size*size.
     * @param size Ukuran matriks.
     * @return Identitas matriks dengan ukuran size.
     */
    public static Matrix getIdentityMatrix(int size) {
        Matrix m = new Matrix(size, size);
        for (int i = 1; i <= size; i++)
            m.setElement(i, i, BigDecimal.ONE);
        return m;
    }

    /**
     * F.S Menghasilkan matrix hasil baca dari file
     * @param fileName Nama file.
     * @return Matriks hasil dari membaca file.
     * @throws Exception Saat akses file gagal atau input tidak valid.
     */
    public static Matrix readFile(String fileName) throws Exception {
        Scanner scan = new Scanner(new File(fileName));
        List<String> list = new ArrayList<>();
        while (scan.hasNextLine())
            list.add(scan.nextLine());
        scan.close();
        if (list.size() <= 0)
            return new Matrix(0, 0);
        Matrix m = new Matrix(list.size(), list.get(0).split(" ").length);
        for (int i = 1; i <= m.maxR; i++) {
            String[] strs = list.get(i-1).split(" ");
            for (int j = 1; j <= m.maxC; j++)
                m.setElement(i, j, new BigDecimal(strs[j-1]));
        }
        return m;
    }

    /**
     * F.S Menghasilkan matrix tranpos dari matriks ini.
     * @return Matriks transpos dari matriks ini.
     */
    public Matrix getTransposeMatrix() {
        BigDecimal[][] tempVal = new BigDecimal[this.maxC][this.maxR];
        BigDecimal[][] subC = this.subMatrixContent(1, 1);

        for (int i = 0; i < this.maxR; i++)
            for (int j = 0; j < this.maxC; j++)
                tempVal[j][i] = subC[i][j];
        return new Matrix(tempVal);
    }

    /**
     * I.S Matriks merupakan matrix persegi.<br>
     * F.S Menghasilkan Entry Matrix matrix ini pada indeks baris r dan indeks kolom c
     * @param r Indeks baris matriks.
     * @param c Indeks kolom matriks.
     * @return Matriks entri matriks ini.
     */
    public Matrix getEntryMatrix(int r, int c) {
        BigDecimal[][] tempVal = new BigDecimal[this.maxR-1][this.maxC-1];
        BigDecimal[][] subC = this.subMatrixContent(1, 1);

        for (int i = 0; i < this.maxR; i++) {
            for (int j = 0; j < this.maxC; j++) {
                if (i < r-1 && j < c-1) {
                    tempVal[i][j] = subC[i][j];
                } else if (i > r-1 && j < c-1) {
                    tempVal[i-1][j] = subC[i][j];
                } else if (i < r-1 && j > c-1) {
                    tempVal[i][j-1] = subC[i][j];
                } else if (i > r-1 && j > c-1) {
                    tempVal[i-1][j-1] = subC[i][j];
                }
            }
        }
        return new Matrix(tempVal);
    }

    /**
     * I.S Matriks merupakan matriks persegi.<br>
     * F.S Menghasilkan matriks kofaktor pada matriks ini.
     * @return Matriks kofaktor matriks ini.
     */
    public Matrix getCofactorMatrix() {
        BigDecimal[][] tempVal = new BigDecimal[this.maxR][this.maxC];

        for (int i = 0; i < this.maxR; i++)
            for (int j = 0; j < this.maxC; j++)
                tempVal[i][j] = this.getEntryMatrix(i+1, j+1).getDeterminan(Method.GAUSS).multiply((i + j) % 2 == 0 ? BigDecimal.ONE : BigDecimal.ONE.negate());
        return new Matrix(tempVal);
    }

    /**
     * I.S Matriks merupakan matriks persegi.<br>
     * F.S Menghasilkan matriks adjoin pada matriks ini.
     * @return Matriks adjoin matriks ini.
     */
    public Matrix getAdjointMatrix() {
        return this.getCofactorMatrix().getTransposeMatrix();
    }

    /**
     * F.S Menghasilkan matriks balikan pada matriks ini dengan metode method.
     * @param method Metode mencari matriks balikan.
     * @return Matriks balikan matriks ini.
     */
    public Matrix getInverseMatrix(Method method) {
        if (this.maxR != this.maxC)
            throw new MatrixException(MatrixErrorIdentifier.DIFFERENT_SIZE_ERROR);
        if (BD.eq0(this.getDeterminan(Method.COFACTOR_EXPANSION)))
            throw new MatrixException(MatrixErrorIdentifier.DETERMINANT_ZERO_ERROR);
        switch (method) {
            case GAUSS_JORDAN: {
                Matrix m = this.appendMatrix(Matrix.getIdentityMatrix(this.maxC));
                m = m.getReducedEchelonForm(m.getMaxColumn()/2);
                return new Matrix(m.subMatrixContent(1, ((m.getMaxColumn()/2)+1), m.getMaxRow(), m.getMaxColumn()));
            }
            case ADJOIN:
                return (this.getAdjointMatrix()).scalarMultiplyOPR(BigDecimal.ONE.divide(this.getDeterminan(Method.GAUSS), BD.SCALE, RoundingMode.HALF_UP));
            default:
                throw new RuntimeException("Method " + method + " is not supported for getting inverse matrix.");
        }
    }

    /**
     * F.S Menghasilkan Echelon form dari matriks yang berdimensi maxR*(maxC-1).
     * @return Matriks echelon form matriks ini.
     */
    public Matrix getEchelonForm() {
        return this.getEchelonForm(this.maxC-1);
    }

    /**
     * I.S colMax merupakan kolom valid pada indeks kolom.<br>
     * F.S Menghasilkan Echelon form dari matriks yang berdimensi maxR*colMax.
     * @param colMax Indeks kolom maksimal yang diperhitungkan.
     * @return Matriks echelon form matriks ini.
     */
    public Matrix getEchelonForm(int colMax) {
        return this.getEchelonForm(1, 1, colMax);
    }

    /**
     * F.S Menghasilkan Echelon form dari matriks yang berdimensi maxR*colMax.
     * @param rowStart Indeks mulai baris matriks.
     * @param colStart Indeks mulai kolom matriks.
     * @param colMax Indeks kolom maksimal yang diperhitungkan.
     * @return Matriks echelon form matriks ini.
     */
    private Matrix getEchelonForm(int rowStart, int colStart, int colMax) {
        Matrix m = this.scaledPartialPivoting(rowStart, colStart, colMax);
        if (rowStart == m.getMaxRow() || colStart == colMax) {
            if (!BD.eq0(m.getElement(rowStart, colStart))){
                m.scaledDet = m.scaledDet.multiply(BigDecimal.ONE.divide(m.getElement(rowStart, colStart), BD.SCALE, RoundingMode.HALF_UP));
                m.scaleOBE(rowStart, BigDecimal.ONE.divide(m.getElement(rowStart, colStart), BD.SCALE, RoundingMode.HALF_UP));
            }
            if (colStart == colMax && rowStart < m.getMaxRow())
                for (int i = m.getMaxRow(); i > rowStart; i--)
                    m.addOBE(i, rowStart, m.getElement(i, colMax).negate());
            return m;
        }
        if (BD.eq0(m.getElement(rowStart,colStart)))
            return m.getEchelonForm(rowStart, colStart+1 ,colMax);
        m.scaledDet = m.scaledDet.multiply(BigDecimal.ONE.divide(m.getElement(rowStart, colStart), BD.SCALE, RoundingMode.HALF_UP));
        m.scaleOBE(rowStart, BigDecimal.ONE.divide(m.getElement(rowStart, colStart), BD.SCALE, RoundingMode.HALF_UP));
        for (int i = rowStart + 1; i <= m.getMaxRow(); i++)
            m.addOBE(i, rowStart, m.getElement(i, colStart).negate());
        return m.getEchelonForm(rowStart + 1, colStart + 1, colMax);
    }

    /**
     * I.S Matriks merupakan suatu augmented matriks.<br>
     * F.S Menghasilkan Reduced Echelon form dari matriks ini.
     * @return Matriks reduced echelon form dari matriks ini.
     */
    public Matrix getReducedEchelonForm() {
        return getReducedEchelonForm(this.maxC-1);
    }

    /**
     * F.S Menghasilkan Reduce Echelon form dari matriks yang berdimensi maxR*colMax
     * @param colMax Kolom maksimal matriks yang diperhitungkan.
     * @return Matriks reduced echelon form dari matriks ini.
     */
    public Matrix getReducedEchelonForm(int colMax) {
        Matrix m = this.copyMatrix();

        m = this.getEchelonForm(colMax);
        return m.getReducedEchelonForm(1, 1, colMax);
    }

    /**
     * F.S Menghasilkan Reduced Echelon form dari matriks yang berdimensi maxR*colMax.
     * @param rowStart Indeks mulai baris matriks.
     * @param colStart Indeks mulai kolom matriks.
     * @param colMax Indeks kolom maksimal yang diperhitungkan.
     * @return Matriks reduced echelon form matriks ini.
     */
    public Matrix getReducedEchelonForm(int rowStart, int colStart, int colMax) {
        Matrix m = this.copyMatrix();

        if (rowStart == m.getMaxRow() || colStart == colMax) {
            for (int i = 1; i < rowStart; i++)
                m.addOBE(i, rowStart, m.getElement(i, colStart).negate());
            return m;
        }
        if (BD.eq0(m.getElement(rowStart,colStart)))
            return m.getReducedEchelonForm(rowStart, colStart+1 ,colMax);
        for (int i = 1; i < rowStart; i++)
            m.addOBE(i, rowStart, m.getElement(i, colStart).negate());
        return m.getReducedEchelonForm(rowStart + 1, colStart + 1, colMax);
    }

    /**
     * I.S rowStart adalah indeks baris valid pada matriks serta colStart dan colMax adalah indeks kolom valid pada matriks.<br>
     * F.S Menghasilkan matriks yang sudah teroptimisasi ketelitian presisinya untuk dilakukan OBE.
     * @param rowStart Indeks mulai baris matriks.
     * @param colStart Indeks mulai kolom matriks.
     * @param colMax Indeks kolom maksimal yang diperhitungkan.
     * @return Matriks yang sudah teroptimisasi ketelitian presisinya untuk dilakukan OBE.
     */
    private Matrix scaledPartialPivoting(int rowStart, int colStart, int colMax) {
        BigDecimal rowMax;
        BigDecimal scaledMax = BigDecimal.ONE.negate();
        int scaledMaxIdx = rowStart;

        Matrix m = this.copyMatrix();

        for (int i = rowStart; i <= m.maxR; i++) {
            rowMax = BigDecimal.ONE.negate();
            for (int j = colStart; j <= colMax; j++)
                if (BD.gt(m.getElement(i, j).abs(), rowMax))
                    rowMax = m.getElement(i, j).abs();
            if (!BD.eq0(rowMax)) {
                BigDecimal absValue = m.getElement(i, colStart).divide(rowMax, BD.SCALE, RoundingMode.HALF_UP).abs();
                if (BD.gt(absValue, scaledMax)) {
                    scaledMax = absValue;
                    scaledMaxIdx = i;
                }
            }
        }
        if (scaledMaxIdx != rowStart) {
            m.scaledDet = m.scaledDet.negate();
            m.swapOBE(scaledMaxIdx, rowStart);
        }
        return m;
    }

    //** Fungsi Sistem Persamaaan Linear **//
    /**
     * I.S Matriks yang diproses adalah matriks augmented.<br>
     * F.S Menghasilkan bentuk SPL dari Matrix dengan metode method.
     * @param method Metode mencari sistem persamaan linear.
     * @return Sebuah sistem persamaan linear.
     */
    public SPL getSistemPersamaanLinear(Method method) {
        BigDecimal[] sol = new BigDecimal[this.maxR+1];
        Matrix coefM = new Matrix(this.subMatrixContent(1, 1, this.maxR, this.maxC-1));
        Matrix constM = new Matrix(this.subMatrixContent(1, this.maxC, this.maxR, this.maxC));

        switch (method) {
            case CRAMER: {
                if (!isAugmentedSize())
                    throw new MatrixException(MatrixErrorIdentifier.NOT_AUGMENTED_ERROR);
                Matrix cramM = new Matrix(this.subMatrixContent(1, 1, this.maxR, this.maxC-1));
                BigDecimal detCoef = coefM.getDeterminan(Method.GAUSS);
                if (BD.eq0(detCoef))
                    throw new MatrixException(MatrixErrorIdentifier.DETERMINANT_ZERO_ERROR);
                for (int i = 1; i <= this.maxR; i++) {
                    cramM.setColumn(i, this.getColumn(this.maxC));
                    sol[i] = cramM.getDeterminan(Method.GAUSS).divide(detCoef, BD.SCALE, RoundingMode.HALF_UP);
                    cramM.setColumn(i, coefM.getColumn(i));
                }
                return new SPL(sol);
            }
            case GAUSS:
                return SPL.getEchelonFormMethod(this);
            case GAUSS_JORDAN:
                return SPL.getReducedEchelonFormMethod(this);
            case INVERSE: {
                if (!isAugmentedSize())
                    throw new MatrixException(MatrixErrorIdentifier.NOT_AUGMENTED_ERROR);
                Matrix solM = (coefM.getInverseMatrix(Method.GAUSS_JORDAN)).multiplyOPR(constM);

                for (int i = 1; i <= this.maxR; i++) {
                    sol[i] = solM.getElement(i, 1);
                }
                return new SPL(sol);
            }
            default:
                throw new RuntimeException("Method " + method + " is not valid!");
        }
    }

    /**
     * Menghasilkan true apabila jumlah kolom matriks lebih satu jumlah dari jumlah baris matriks.
     * @return Jumlah kolom matriks lebih satu jumlah dari jumlah baris matriks.
     */
    public boolean isAugmentedSize() {
        return this.maxC == this.maxR+1;
    }
}
