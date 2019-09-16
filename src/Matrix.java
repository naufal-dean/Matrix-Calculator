package tubes;

import java.util.*;

public class Matrix {
    private double[][] content;
    private int maxR, maxC;
    private Scanner input = new Scanner(System.in);

    //** Konstruktor **//
    public Matrix(int maxR, int maxC) {
        this.maxR = maxR;
        this.maxC = maxC;
        this.content = new double[maxR+1][maxC+1];
    }

    public Matrix(double[][] arr) {
        this(arr.length, arr.length > 0 ? arr[0].length : 0);
        setContent(arr);
    }

    //** Selektor **//
    //-- Selektor: Get --//
    public int getMaxRow() {
      return this.maxR;
    }

    public int getMaxColumn() {
        return this.maxC;
    }

    public double[][] getContent() {
        return this.content;
    }

    public double getElement(int r, int c) {
        return this.content[r][c];
    }

    public double[] getRow(int r) {
        return Arrays.copyOf(this.content[r], this.content[r].length);
    }

    public double[] getColumn(int c) {
        double[] col = new double[this.maxR];
        for (int i = 0; i < this.maxR; i++) {
            col[i] = this.getElement(i+1, c);
        }
        return col;
    }

    //-- Selektor: Set --//
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
    public void bacaMatrix() {
        // Input maxR dan maxC
        System.out.print("Jumlah baris: ");
        this.maxR = input.nextInt();
        System.out.print("Jumlah kolom: ");
        this.maxC = input.nextInt();
        while ((this.maxR <= 0) || (this.maxC <= 0)) {
            System.out.println("Jumlah kolom dan baris harus positif.");

            System.out.print("Jumlah baris: ");
            this.maxR = input.nextInt();
            System.out.print("Jumlah kolom: ");
            this.maxC = input.nextInt();
        }
        // Inisialisasi content
        this.content = new double[this.maxR+1][this.maxC+1];
        // Input elemen
        for (int i = 1; i <= this.maxR; i++) {
            for (int j = 1; j <= this.maxC; j++) {
                System.out.printf("Elemen[%d,%d]: ", i, j);
                this.content[i][j] = input.nextDouble();
            }
        }
    }

    public void tulisMatrix() {
        for (int i = 1; i <= this.maxR; i++) {
            System.out.print("| ");
            for (int j = 1; j <= this.maxC; j++) {
                System.out.printf("%f ", this.content[i][j]);
            }
            System.out.println("|");
        }
    }

    public Matrix copyMatrix() {
        return new Matrix(this.subMatrixContent(1, 1));
    }

    public double[][] subMatrixContent(int startR, int startC) {
        return subMatrixContent(startR, startC, this.maxR, this.maxC);
    }

    public double[][] subMatrixContent(int startR, int startC, int endR, int endC) {
        double[][] sub = new double[endR + 1 - startR][endC + 1 - startC];
        for (int i = 0; i < (endR + 1 - startR); i++) {
            sub[i] = Arrays.copyOfRange(this.content[i + startR], startC, endC + 1);
        }
        return sub;
    }

    //dean
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
                sb.append(String.format("%f", v == 0 ? 0 : v) + (c == this.maxC ? "\n" : " "));
            }
        return sb.toString();
    }

    //** Operasi pada matriks **//
    public Matrix multiplyOPR(Matrix m) {
        if (this.maxC != m.maxR)
            throw new RuntimeException("Column of the left matrix is not equal to row of the right matrix!");
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
            throw new RuntimeException("Different matrix size!");
        Matrix res = copyMatrix();
        for (int i = 1; i <= maxR; i++)
            for (int j = 1; j <= maxC; j++)
                res.setElement(i, j, res.getElement(i,j) + m.getElement(i,j));
        return res;
    }

    //camcam
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Matrix))
            return false;
        Matrix m = (Matrix)o;
        if (m.getMaxRow() != this.maxR || m.getMaxColumn() != this.maxC)
            return false;
        for (int r = 1; r <= this.maxR; ++r)
            for (int c = 1; c <= this.maxC; ++c)
                if (m.getElement(r, c) != this.content[r][c])
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

    private void addOBE(int r1, int r2) {
        addOBE(r1, r2, 1);
    }

    private void addOBE(int r1, int r2, double scale) {
        for (int c = 1; c <= maxC; c++)
            this.content[r1][c] += (this.content[r2][c] * scale);
    }


    //** Fungsi matriks **//
    //nopal (NOTE: tambahin lagi tiap method)
    public double getDeterminan(Method method) {
        if (this.maxR != this.maxC)
            throw new RuntimeException("Max row and max column are not the same! No determinant.");
        switch (method) {
            case CRAMER: {
                if (this.maxR == 1)
                    return this.content[1][1];
                float res = 0;
                for (int j = 1; j <= this.maxC; j++)
                    res += this.content[1][j]*this.getEntryMatrix(1, j).getDeterminan(Method.CRAMER)*(j%2 == 0 ? -1 : 1);
                return res;
            }
            case GAUSS: {
                Matrix M = this.copyMatrix();
                int  i,j, idx;
                //double c;
                double det=1;
                for(j=1; j<=M.getMaxRow() -1 ;j++){
                    i = j;
                    while((M.getElement(i,j) == 0) && (i<M.getMaxRow())){
                        i++;
                    }//cari ampe yang ga 0 dibarisannya
                    idx = i;
                    i = i+1;
                    for(;i<=M.getMaxRow();i++){
                        //eliminasi yang lainnya dengan baris idx
                        //c = M.getElement(i,j)/M.getElement(idx,j);
                        if (M.getElement(i, j)!=0){
                            M.addOBE(i, idx, -M.getElement(i, j)/M.getElement(idx, j));/*setRow(i,(RowOperation.kaliC(M.getRow(i), 1/c)));*/
                            //det *= M.getElement(idx, j)/M.getElement(i, j);
                //M.setRow(i,(RowOperation.PlusTab(M.getRow(i),RowOperation.kaliC(M.getRow(idx),-1))));
                        }
                    }

                        //pindahin ke paling atas
                    if(j!=idx){
                    det *=-1;
                    M.swapOBE(j,idx);
                    }
                }
                for (i=1; i<=M.getMaxRow();i++){
                det *= M.getElement(i,i);
                }
                return det;

            }
            case GAUSS_JORDAN: {

                break;
            }
            case INVERSE: {

                break;
            }
        }
        throw new RuntimeException("Method " + method + " is not valid!");
    }

    //dean
    public static Matrix getIdentityMatrix(int size) {
        Matrix m = new Matrix(size, size);

        for (int i = 1; i <= size; i++) {
            m.setElement(i, i, 1);
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
            throw new RuntimeException("Max row and max column are not the same! No inverse matrix.");
        if (this.getDeterminan(Method.GAUSS) == 0)
            throw new RuntimeException("Matrix is singular! No inverse matrix.");
        switch (method) {
            case CRAMER: {
                return (this.getAdjointMatrix()).scalarMultiplyOPR(1/this.getDeterminan(Method.GAUSS));
            }
            case GAUSS_JORDAN: {
                Matrix m = this.appendMatrix(Matrix.getIdentityMatrix(this.maxC));
                m = m.getReducedEchelonForm(m.getMaxColumn()/2);
                return new Matrix(m.subMatrixContent(1, ((m.getMaxColumn()/2)+1), m.getMaxRow(), m.getMaxColumn()));
            }
            default: // GAUSS and INVERSE are not supported (?)
                throw new RuntimeException("Method " + method.toString().toLowerCase() + " is not supported for getting inverse matrix.");
        }
    }

    public Matrix getEchelonForm(int colMax) {
        Matrix m = this.copyMatrix();

        return m.getEchelonForm(1, 1, colMax);
    }

    private Matrix getEchelonForm(int rowStart, int colStart, int colMax) {
        // System.out.printf("%d %d %d\n", rowStart, colStart, colMax);
        // this.tulisMatrix();
        // System.out.printf("\n\n");
        Matrix m = this.scaledPartialPivoting(rowStart, colStart, colMax);
        if (rowStart == m.getMaxRow() || colStart == colMax) { // base
            //nambahin ini buat kasus baris matrix yang sama smua isinya
            if (m.getElement(rowStart, colStart)!=0){
                m.scaleOBE(rowStart, (1/m.getElement(rowStart, colStart)));
            }

            if (colStart == colMax && rowStart < m.getMaxRow()) {
                for (int i = m.getMaxRow(); i > rowStart; i--) {
                    m.addOBE(i, rowStart, -m.getElement(i, colMax));
                }
            }
            return m;
        } else { // recurrence
            if ((m.getElement(rowStart,colStart) == 0)){
                return m.getEchelonForm(rowStart, colStart+1 ,colMax);
            }
            //nambahinnya sampe sini ya {nopal}
            m.scaleOBE(rowStart, (1/m.getElement(rowStart, colStart)));
            for (int i = rowStart + 1; i <= m.getMaxRow(); i++) {
                m.addOBE(i, rowStart, -m.getElement(i, colStart));
            }
            return m.getEchelonForm(rowStart + 1, colStart + 1, colMax);
        }
    }

    public Matrix getReducedEchelonForm(int colMax) {
        Matrix m = this.copyMatrix();

        m = this.getEchelonForm(colMax);
        return m.getReducedEchelonForm(1, 1, colMax);
    }

    public Matrix getReducedEchelonForm(int rowStart, int colStart, int colMax) {
        Matrix m = this.copyMatrix();

        if (rowStart == m.getMaxRow() || colStart == colMax) { // base
            for (int i = 1; i < rowStart; i++) {
                m.addOBE(i, rowStart, -m.getElement(i, colStart));
            }
            return m;
        } else { // recurrence
            if ((m.getElement(rowStart,colStart) == 0)){
                return m.getReducedEchelonForm(rowStart, colStart+1 ,colMax);
            }
            for (int i = 1; i < rowStart; i++) {
                m.addOBE(i, rowStart, -m.getElement(rowStart, colStart));
            }
            return m.getReducedEchelonForm(rowStart + 1, colStart + 1, colMax);
        }
    }

    private Matrix scaledPartialPivoting(int rowStart, int colStart, int colMax) {
        double rowMax;
        double scaledMax = -1;
        int scaledMaxIdx = 0;

        Matrix m = this.copyMatrix();

        for (int i = rowStart; i <= m.maxR; i++) {
            rowMax = 0;
            for (int j = colStart; j <= colMax; j++) {
                if (Math.abs(m.getElement(i, j)) > rowMax) {
                    rowMax = Math.abs(m.getElement(i, j));
                }
            }
            if (rowMax!=0){
                if (Math.abs(m.getElement(i, colStart)/rowMax) > scaledMax) {
                    scaledMax = Math.abs(m.getElement(i, colStart)/rowMax);
                    scaledMaxIdx = i;
                }
            }
        }
        if (scaledMaxIdx != 0){
            m.swapOBE(scaledMaxIdx, rowStart);
        }
        return m;
    }

    //** Fungsi Sistem Persamaaan Linear **//
    public SPL getSistemPersamaanLinear(Method method) {
        // NOTE: mendingan outputnya double ae.. ntar baru ubah ke string pas dibutuhin di luar
        // daripada string, terus diubah jadi double pas butuh buat perhitungan
        // + ganti nama ae jadi getSolution :v
        // Prekondisi: matriks yang diproses adalah matriks augmented
        double[] sol = new double[this.maxR+1];
        Matrix coefM = new Matrix(this.subMatrixContent(1, 1, this.maxR, this.maxC-1));
        Matrix constM = new Matrix(this.subMatrixContent(1, this.maxC, this.maxR, this.maxC));

        switch (method) {
            case CRAMER: { // hanya untuk matriks augmented (n)*(n+1) -> (brarti harus validasi dulu dong(?)) + sama validasi klo det coefnya 0
                Matrix cramM = new Matrix(this.subMatrixContent(1, 1, this.maxR, this.maxC-1));

                for (int i = 1; i <= this.maxR; i++) {
                    cramM.setColumn(i, this.getColumn(this.maxC));
                    sol[i] = cramM.getDeterminan(Method.GAUSS)/coefM.getDeterminan(Method.GAUSS);
                    cramM.setColumn(i, coefM.getColumn(i));
                }
                return new SPL(sol); // bingung gimana ngubahnya, sementara gini ae
            }
            case GAUSS: {
                return new SPL(getEchelonForm(this.maxC-1));
            }
            case GAUSS_JORDAN: {
                return new SPL(getReducedEchelonForm(this.maxC-1));
            }
            case INVERSE: { // hanya untuk matriks augmented (n)*(n+1)
                //return new SPL(coefM.getInverseMatrix(Method.GAUSS_JORDAN).multiplyOPR(constM));
                Matrix solM = (coefM.getInverseMatrix(Method.GAUSS_JORDAN)).multiplyOPR(constM);

                for (int i = 1; i <= this.maxR; i++) {
                    sol[i] = solM.getElement(i, 1);
                }
                return new SPL(sol);
            }
        }
        throw new RuntimeException("Method " + method + " is not valid!");
    }
}
