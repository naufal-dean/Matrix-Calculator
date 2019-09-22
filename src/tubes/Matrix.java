package tubes;

import java.io.File;
import java.util.*;

public class Matrix {
    /**
     * Elemen dari matriks.
     */
    private double[][] content;
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
    private double scaledDet = 1;

    //** Konstruktor **//
    /**
     * F.S Membuat matrix baru dengan dimensi maxR*maxC.
     * @param maxR Panjang / indeks maksimum baris matriks.
     * @param maxC Panjang / indeks maksimum kolom matriks.
     */
    public Matrix(int maxR, int maxC) {
        this.maxR = maxR;
        this.maxC = maxC;
        this.content = new double[maxR+1][maxC+1];
    }

    /**
     * F.S Membuat Matrix baru dengan elemen pada sebuah array of array of double.
     * @param arr Konten isi matriks yang akan dibuat.
     */
    public Matrix(double[][] arr) {
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
    public double[][] getContent() {
        return this.content;
    }

    /**
     * F.S Mendapatkan elemen matriks dengan indeks baris r dan kolom c.
     * @param r Indeks baris.
     * @param c Indeks kolom.
     * @return Elemen matriks.
     */
    public double getElement(int r, int c) {
        return this.content[r][c];
    }

    /**
     * F.S Mendapatkan baris matrix dengan indeks baris.
     * @param r Indeks baris.
     * @return Satu baris elemen-elemen matriks dalam bentuk tipe data array of double.
     */
    public double[] getRow(int r) {
        return Arrays.copyOf(this.content[r], this.content[r].length);
    }

    /**
     * F.S Mendapatkan baris matrix dengan indeks kolom.
     * @param c Indeks kolom.
     * @return Satu kolom elemen-elemen matriks dalam bentuk tipe data array of double.
     */
    public double[] getColumn(int c) {
        double[] col = new double[this.maxR];
        for (int i = 0; i < this.maxR; i++) {
            col[i] = this.getElement(i+1, c);
        }
        return col;
    }

    //-- Selektor: Set --//
    /**
     * F.S mengganti nilai maxR pada matrix
     * @param maxR
     */
    public void setMaxRow(int maxR) {
        this.maxR = maxR;
    }

    public void setMaxColumn(int maxC) {
        this.maxC = maxC;
    }

    public void setContent(double[][] arr) {
        for (int r = 0; r < this.maxR; r++)
            for (int c = 0; c < this.maxC; c++)
                this.content[r+1][c+1] = arr[r][c];
    }

    public void setElement(int r, int c, double val) {
        this.content[r][c] = val;
    }

    public void setRow(int r, double[] row) {
        this.content[r] = Arrays.copyOf(row, row.length);
    }

    public void setColumn(int c, double[] col) {
        for (int i = 0; i < this.maxR; i++) {
            this.setElement(i+1, c, col[i]);
        }
    }

    //** Utility **//
    public Matrix copyMatrix() {
        Matrix M = new Matrix(this.subMatrixContent(1, 1));
        M.scaledDet = this.scaledDet;
        return M;
    }

    public double[][] subMatrixContent(int startR, int startC) {
        return subMatrixContent(startR, startC, this.maxR, this.maxC);
    }

    public double[][] subMatrixContent(int startR, int startC, int endR, int endC) {
        double[][] sub = new double[endR + 1 - startR][endC + 1 - startC];
        for (int i = 0; i < (endR + 1 - startR); i++)
            sub[i] = Arrays.copyOfRange(this.content[i + startR], startC, endC + 1);
        return sub;
    }

    public Matrix appendMatrix(Matrix mInput) {
        // Prekondisi: jumlah baris matriks ini == mInput
        double[][] m1 = this.subMatrixContent(1, 1);
        double[][] m2 = mInput.subMatrixContent(1, 1);
        double[][] tempVal = new double[m1.length][m1[0].length + m2[0].length];

        for (int i = 0; i < m1.length; i++) {
            tempVal[i] = Arrays.copyOf(m1[i], tempVal[i].length);
            for (int j = 0; j < m2[i].length; j++) {
                tempVal[i][m1[i].length + j] = m2[i][j];
            }
        }
        return new Matrix(tempVal);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 1; r <= this.maxR; r++)
            for (int c = 1; c <= this.maxC; c++) {
                double v = this.content[r][c];
                sb.append(String.format("%.2f", v == 0 ? 0 : v) + (c == this.maxC ? r < this.maxR ? "\n" : "" : " "));
            }
        return sb.toString();
    }

    //** Operasi pada matriks **//
    public Matrix multiplyOPR(Matrix m) {
        if (this.maxC != m.maxR)
            throw new MatrixException(MatrixErrorIdentifier.MULTIPLY_ERROR);
        double temp = 0;
        Matrix res = new Matrix(this.maxR, m.maxC);
        for (int i = 1; i <= this.maxR; i++) {
            for (int j = 1; j <= m.maxC; j++) {
                temp = 0;
                for (int k = 1; k <= this.maxC; k++) {
                    temp += this.getElement(i, k)*m.getElement(k, j);
                }
                res.setElement(i, j, temp);
            }
        }
        return res;
    }

    public Matrix scalarMultiplyOPR(double x) {
        Matrix res = copyMatrix();
        for (int i = 1; i <= this.maxR; i++)
            for (int j = 1; j <= this.maxC; j++)
                res.content[i][j] *= x;
        return res;
    }

    public Matrix addOPR(Matrix m) {
        if (this.maxR != m.maxR || this.maxC != m.maxC)
            throw new MatrixException(MatrixErrorIdentifier.DIFFERENT_SIZE_ERROR);
        Matrix res = copyMatrix();
        for (int i = 1; i <= maxR; i++)
            for (int j = 1; j <= maxC; j++)
                res.setElement(i, j, res.getElement(i,j) + m.getElement(i,j));
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Matrix))
            return false;
        Matrix m = (Matrix)o;
        if (m.getMaxRow() != this.maxR || m.getMaxColumn() != this.maxC)
            return false;
        for (int r = 1; r <= this.maxR; ++r)
            for (int c = 1; c <= this.maxC; ++c)
                if (!Utils.doubleEquals(m.getElement(r, c), this.content[r][c]))
                    return false;
        return true;
    }

    //** OBE **//
    private void swapOBE(int r1, int r2) {
        double[] temp;
        temp = this.content[r1];
        this.content[r1] = this.content[r2];
        this.content[r2] = temp;
    }

    private void scaleOBE(int r, double scale) {
        for (int c = 1; c <= maxC; c++) {
            this.content[r][c] *= scale;
        }
    }

    private void addOBE(int r1, int r2, double scale) {
        for (int c = 1; c <= maxC; c++)
            this.content[r1][c] += (this.content[r2][c] * scale);
    }


    //** Fungsi matriks **//
    public double getDeterminan(Method method) {
        if (this.maxR != this.maxC)
            throw new MatrixException(MatrixErrorIdentifier.NOT_SQUARE_ERROR);
        switch (method) {
            case GAUSS: {
                double newDet = 1;
                Matrix M = this.getEchelonForm(this.maxC);
                for (int i = 1; i <= M.maxC; i++)
                    newDet *= M.getElement(i, i);
                return (newDet/M.scaledDet);
            }
            case GAUSS_JORDAN: {
                double newDet = 1;
                Matrix M = this.getReducedEchelonForm(this.maxC);
                for (int i = 1; i <= M.maxC; i++)
                    newDet *= M.getElement(i, i);
                return (newDet/M.scaledDet);
            }
            case INVERSE: {
                return 1/this.getInverseMatrix(Method.CRAMER).getDeterminan(Method.COFACTOR_EXPANSION);
            }
            case COFACTOR_EXPANSION: {
                if (this.maxR == 1)
                    return this.content[1][1];
                float res = 0;
                for (int j = 1; j <= this.maxC; j++)
                    res += this.content[1][j]*this.getEntryMatrix(1, j).getDeterminan(Method.COFACTOR_EXPANSION)*(j%2 == 0 ? -1 : 1);
                return res;
            }
            default:
                throw new RuntimeException("Method " + method + " is not valid!");
        }
    }

    public static Matrix getIdentityMatrix(int size) {
        Matrix m = new Matrix(size, size);
        for (int i = 1; i <= size; i++)
            m.setElement(i, i, 1);
        return m;
    }

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
                m.setElement(i, j, Double.parseDouble(strs[j-1]));
        }
        return m;
    }
    
    public Matrix getTransposeMatrix() {
        double[][] tempVal = new double[this.maxC][this.maxR];
        double[][] subC = this.subMatrixContent(1, 1);

        for (int i = 0; i < this.maxR; i++) {
            for (int j = 0; j < this.maxC; j++) {
                tempVal[j][i] = subC[i][j];
            }
        }
        return new Matrix(tempVal);
    }

    public Matrix getEntryMatrix(int r, int c) {
        double[][] tempVal = new double[this.maxR-1][this.maxC-1];
        double[][] subC = this.subMatrixContent(1, 1);

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

    public Matrix getCofactorMatrix() {
        double[][] tempVal = new double[this.maxR][this.maxC];

        for (int i = 0; i < this.maxR; i++) {
            for (int j = 0; j < this.maxC; j++) {
                tempVal[i][j] = ((i + j) % 2 == 0)  ? ((this.getEntryMatrix(i+1, j+1)).getDeterminan(Method.GAUSS))
                                                    : (-(this.getEntryMatrix(i+1, j+1)).getDeterminan(Method.GAUSS));
            }
        }
        return new Matrix(tempVal);
    }

    public Matrix getAdjointMatrix() {
        return (this.getCofactorMatrix()).getTransposeMatrix();
    }

    public Matrix getInverseMatrix(Method method) {
        if (this.maxR != this.maxC)
            throw new MatrixException(MatrixErrorIdentifier.DIFFERENT_SIZE_ERROR);
        if (this.getDeterminan(Method.GAUSS) == 0)
            throw new MatrixException(MatrixErrorIdentifier.DETERMINANT_ZERO_ERROR);
        switch (method) {
            case GAUSS_JORDAN: {
                Matrix m = this.appendMatrix(Matrix.getIdentityMatrix(this.maxC));
                m = m.getReducedEchelonForm(m.getMaxColumn()/2);
                return new Matrix(m.subMatrixContent(1, ((m.getMaxColumn()/2)+1), m.getMaxRow(), m.getMaxColumn()));
            }
            case ADJOIN: {
                return (this.getAdjointMatrix()).scalarMultiplyOPR(1/this.getDeterminan(Method.GAUSS));
            }
            default:
                throw new RuntimeException("Method " + method + " is not supported for getting inverse matrix.");
        }
    }

    public Matrix getEchelonForm() {
        return this.getEchelonForm(this.maxC-1);
    }

    public Matrix getEchelonForm(int colMax) {
        return this.getEchelonForm(1, 1, colMax);
    }

    private Matrix getEchelonForm(int rowStart, int colStart, int colMax) {
        Matrix m = this.scaledPartialPivoting(rowStart, colStart, colMax);
        if (rowStart == m.getMaxRow() || colStart == colMax) {
            if (m.getElement(rowStart, colStart)!=0){
                m.scaledDet *= (1/m.getElement(rowStart, colStart));
                m.scaleOBE(rowStart, (1/m.getElement(rowStart, colStart)));
            }

            if (colStart == colMax && rowStart < m.getMaxRow()) {
                for (int i = m.getMaxRow(); i > rowStart; i--) {
                    m.addOBE(i, rowStart, -m.getElement(i, colMax));
                }
            }
            return m;
        } else {
            if ((m.getElement(rowStart,colStart) == 0)) {
                return m.getEchelonForm(rowStart, colStart+1 ,colMax);
            }
            m.scaledDet *= (1/m.getElement(rowStart, colStart));
            m.scaleOBE(rowStart, (1/m.getElement(rowStart, colStart)));
            for (int i = rowStart + 1; i <= m.getMaxRow(); i++) {
                m.addOBE(i, rowStart, -m.getElement(i, colStart));
            }
            return m.getEchelonForm(rowStart + 1, colStart + 1, colMax);
        }
    }

    public Matrix getReducedEchelonForm() {
        return getReducedEchelonForm(this.maxC-1);
    }

    public Matrix getReducedEchelonForm(int colMax) {
        Matrix m = this.copyMatrix();

        m = this.getEchelonForm(colMax);
        return m.getReducedEchelonForm(1, 1, colMax);
    }

    public Matrix getReducedEchelonForm(int rowStart, int colStart, int colMax) {
        Matrix m = this.copyMatrix();

        if (rowStart == m.getMaxRow() || colStart == colMax) {
            for (int i = 1; i < rowStart; i++) {
                m.addOBE(i, rowStart, -m.getElement(i, colStart));
            }
            return m;
        } else {
            if ((m.getElement(rowStart,colStart) == 0)){
                return m.getReducedEchelonForm(rowStart, colStart+1 ,colMax);
            }
            for (int i = 1; i < rowStart; i++) {
                m.addOBE(i, rowStart, -m.getElement(i, colStart));
            }
            return m.getReducedEchelonForm(rowStart + 1, colStart + 1, colMax);
        }
    }

    private Matrix scaledPartialPivoting(int rowStart, int colStart, int colMax) {
        double rowMax;
        double scaledMax = -1;
        int scaledMaxIdx = rowStart;

        Matrix m = this.copyMatrix();

        for (int i = rowStart; i <= m.maxR; i++) {
            rowMax = -1;
            for (int j = colStart; j <= colMax; j++) {
                if (Math.abs(m.getElement(i, j)) > rowMax) {
                    rowMax = Math.abs(m.getElement(i, j));
                }
            }
            if (rowMax != 0) {
                if (Math.abs(m.getElement(i, colStart)/rowMax) > scaledMax) {
                    scaledMax = Math.abs(m.getElement(i, colStart)/rowMax);
                    scaledMaxIdx = i;
                }
            }
        }
        if (scaledMaxIdx != rowStart) {
            m.scaledDet *= (-1);
            m.swapOBE(scaledMaxIdx, rowStart);
        }
        return m;
    }

    //** Fungsi Sistem Persamaaan Linear **//
    public SPL getSistemPersamaanLinear(Method method) {
        // Prekondisi: matriks yang diproses adalah matriks augmented
        double[] sol = new double[this.maxR+1];
        Matrix coefM = new Matrix(this.subMatrixContent(1, 1, this.maxR, this.maxC-1));
        Matrix constM = new Matrix(this.subMatrixContent(1, this.maxC, this.maxR, this.maxC));

        switch (method) {
            case CRAMER: {
                if (!isAugmentedSize())
                    throw new MatrixException(MatrixErrorIdentifier.NOT_AUGMENTED_ERROR);
                Matrix cramM = new Matrix(this.subMatrixContent(1, 1, this.maxR, this.maxC-1));
                double detCoef = coefM.getDeterminan(Method.GAUSS);
                if (Utils.doubleEquals(detCoef, 0))
                    throw new MatrixException(MatrixErrorIdentifier.DETERMINANT_ZERO_ERROR);
                for (int i = 1; i <= this.maxR; i++) {
                    cramM.setColumn(i, this.getColumn(this.maxC));
                    sol[i] = cramM.getDeterminan(Method.GAUSS)/detCoef;
                    cramM.setColumn(i, coefM.getColumn(i));
                }
                return new SPL(sol);
            }
            case GAUSS: {
                return SPL.getEchelonFormMethod(this);
            }
            case GAUSS_JORDAN: {
                return SPL.getReducedEchelonFormMethod(this);
            }
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

    public boolean isAugmentedSize() {
        return this.maxC == this.maxR+1;
    }
}
