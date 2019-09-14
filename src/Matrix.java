package tubes;

import java.util.*;

public class Matrix {
    private float[][] content;
    private int maxR, maxC;
    private Scanner input = new Scanner(System.in);

    //** Konstruktor **//
    public Matrix(int maxR, int maxC) {
        this.maxR = maxR;
        this.maxC = maxC;
        this.content = new float[maxR+1][maxC+1];
    }

    public Matrix(float[][] arr) {
        this(arr.length, arr[0].length);
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

    public float[][] getContent() {
        return this.content;
    }

    public float getElement(int r, int c) {
        return this.content[r][c];
    }

    public float[] getRow(int r) {
        return Arrays.copyOf(this.content[r], this.content[r].length);
    }

    //-- Selektor: Set --//
    public void setMaxRow(int maxR) {
        this.maxR = maxR;
    }

    public void setMaxColumn(int maxC) {
        this.maxC = maxC;
    }

    public void setContent(float[][] arr) {
        for (int r = 0; r < this.maxR; r++)
            for (int c = 0; c < this.maxC; c++)
                this.content[r+1][c+1] = arr[r][c];
    }

    public void setElement(int r, int c, float val) {
        this.content[r][c] = val;
    }

    public void setRow(int r, float[] row) {
        this.content[r] = Arrays.copyOf(row, row.length);
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
        this.content = new float[this.maxR+1][this.maxC+1];
        // Input elemen
        for (int i = 1; i <= this.maxR; i++) {
            for (int j = 1; j <= this.maxC; j++) {
                System.out.printf("Elemen[%d,%d]: ", i, j);
                this.content[i][j] = input.nextFloat();
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

    public float[][] subMatrixContent(int startR, int startC) {
        return subMatrixContent(startR, startC, this.maxR, this.maxC);
    }

    public float[][] subMatrixContent(int startR, int startC, int endR, int endC) {
        float[][] sub = new float[endR + 1 - startR][endC + 1 - startC];
        for (int i = 0; i < (endR + 1 - startR); i++) {
            sub[i] = Arrays.copyOfRange(this.content[i + startR], startC, endC + 1);
        }
        return sub;
    }

    public Matrix appendMatrix(Matrix mInput) {
        // Prekondisi: jumlah baris matriks ini == mInput
        float[][] m1 = this.subMatrixContent(1, 1);
        float[][] m2 = mInput.subMatrixContent(1, 1);
        float[][] tempVal = new float[m1.length][m1[0].length + m2[0].length];

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
            for (int c = 1; c <= this.maxC; c++)
                sb.append(String.format("%f", this.content[r][c]) + (c == this.maxC ? "\n" : " "));
        return sb.toString();
    }

    //** OBE **//
    private void swapOBE(int r1, int r2) {
        float[] temp;
        temp = this.content[r1];
        this.content[r1] = this.content[r2];
        this.content[r2] = temp;
    }

    private void scaleOBE(int r, float scale) {
        for (int c = 1; c <= maxC; c++) {
            this.content[r][c] *= scale;
        }
    }

    private void addOBE(int r1, int r2, float scale) {
        float temp;

        for (int c = 1; c <= maxC; c++) {
            temp = this.content[r1][c] + (this.content[r2][c] * scale);
            this.content[r1][c] = temp;
        }
    }

    //** Fungsi matriks **//
    public float[] getSistemPersamaanLinear(Method method) {
        // TODO: implement
        return new float[this.maxC+1];
    }

    //nopal (NOTE: tambahin lagi tiap method)
    public float getDeterminan(Method method) {
        Matrix M = this.copyMatrix();
        int  i,j, idx;
        float c;
        float det=1;
        //double M1[][];
        //M1= new double[M.length][M[0].length];
        //M1=M;
        for(j=1; j<=M.getMaxRow() -1 ;j++){
            i = j;
            while((M.getElement(i,j) == 0) && (i<=M.getMaxRow())){
                i++;
            }//cari ampe yang ga 0 dibarisannya
            idx = i;
            i = i+1;
            for(;i<=M.getMaxRow();i++){
                //eliminasi yang lainnya dengan baris idx
                c = M.getElement(i,j)/M.getElement(idx,j);
                if (c!=0){
                    M.setRow(i,(RowOperation.kaliC(M.getRow(i), 1/c)));
                    det *= c;
                    M.setRow(i,(RowOperation.PlusTab(M.getRow(i),RowOperation.kaliC(M.getRow(idx),-1))));
                }
            }
            //pindahin ke paling atas
            if(j!=idx){
                det *=-1;
                float[] temp = M.getRow(j);
                M.setRow(j, M.getRow(idx));
                M.setRow(idx,temp) ;
            }
        }
        for (i=1; i<=M.getMaxRow();i++){
             det *= M.getElement(i,i);
        }
    
        return det;
    }

    //dean
    public Matrix getIdentityMatrix() {
        // TODO: create exception
        // if (this.maxR != this.maxC)
        //     throw new Exception("Max row and max column are not the same!");

        Matrix m = new Matrix(this.maxR, this.maxC);
        for (int i = 1; i <= this.maxR; i++) {
            m.setElement(i, i, 1);
        }
        return m;
    }

    public Matrix getTransposeMatrix() {
        // Kamus lokal
        float[][] tempVal;
        int temp;
        // Algoritma
        tempVal = new float[this.maxC][this.maxR];
        for (int i = 0; i < this.maxR; i++) {
            for (int j = 0; j < this.maxC; j++) {
                tempVal[j][i] = this.subMatrixContent(1, 1)[i][j];
            }
        }
        return new Matrix(tempVal);
    }

    public Matrix getAdjoinMatrix() {
        // TODO: implement
        return new Matrix(this.maxR, this.maxC);
    }

    // public Matrix getInverseMatrix(Method method) {
    public Matrix getInverseMatrix() {
        // TODO: create exception
        // if (this.maxR != this.maxC)
        //     throw new Exception("Max row and max column are not the same!");

        Matrix m = this.appendMatrix(this.getIdentityMatrix());
        m = m.getReducedEchelonForm(m.getMaxColumn()/2);
        return new Matrix(m.subMatrixContent(1, ((m.getMaxColumn()/2)+1), m.getMaxRow(), m.getMaxColumn()));
    }

    public Matrix getEchelonForm(int colMax) {
        Matrix m = this.copyMatrix();

        if (m.getMaxRow() > colMax) {
            return m.getEchelonFormSpecial(m, 1, 1, colMax);
        } else {
            return m.getEchelonForm(m, 1, 1, colMax);
        }
    }

    private Matrix getEchelonFormSpecial(Matrix m, int rowStart, int colStart, int colMax) {
        m = m.getEchelonForm(m, 1, 1, colMax);
        for (int i = m.getMaxRow(); i > colMax; i--) {
            m.addOBE(i, colMax, -m.getElement(i, colMax));
        }
        return m;
    }

    private Matrix getEchelonForm(Matrix m, int rowStart, int colStart, int colMax) {
        // m.tulisMatrix();
        // System.out.printf("\n\n");
        if (rowStart == m.getMaxRow() || colStart == colMax) { // base
            m.scaleOBE(rowStart, (1/m.getElement(rowStart, colStart)));
            return m;
        } else { // recurrence
            m.scaleOBE(rowStart, (1/m.getElement(rowStart, colStart)));
            for (int i = rowStart + 1; i <= m.getMaxRow(); i++) {
                m.addOBE(i, rowStart, -m.getElement(i, colStart));
            }
            return m.getEchelonForm(m, rowStart + 1, colStart + 1, colMax);
        }
    }

    // //nopal
    // public Matrix getEchelonForm() {
    //     Matrix M = this.copyMatrix();
    //     int  i,j, idx;
    //     float c;
    //     //double M1[][];
    //     //M1= new double[M.length][M[0].length];
    //     //M1=M;
    //     for(j=1; j<=M.getMaxRow() -1 ;j++){
    //         i = j;
    //         while((M.getElement(i,j) == 0) && (i<M.getMaxRow())){
    //             i++;
    //         }//cari ampe yang ga 0 dibarisannya
    //         idx = i;
    //         i = i+1;
    //         for(;i<=M.getMaxRow();i++){
    //             //eliminasi yang lainnya dengan baris idx
    //             c = M.getElement(i,j)/M.getElement(idx,j);
    //             if (c!=0){
    //                 M.setRow(i,(RowOperation.kaliC(M.getRow(i), 1/c)));
    //                 M.setRow(i,(RowOperation.PlusTab(M.getRow(i),RowOperation.kaliC(M.getRow(idx),-1))));
    //             }
    //         }
    //         M.setRow(idx, RowOperation.kaliC(M.getRow(idx),1/M.getElement(idx,j)));
    //         //pindahin ke paling atas
    //         float[] temp = M.getRow(j);
    //         M.setRow(j, M.getRow(idx));
    //         M.setRow(idx,temp) ;
    //     }
    //     M.setRow(j,RowOperation.kaliC(M.getRow(j),1/M.getElement(j,j)));
    //     return M;
    // }

    //nopal
    public Matrix getReducedEchelonForm(int colMax) {
        int rowMax;

        Matrix m = new Matrix(this.maxR, this.maxC);
        m = this.getEchelonForm(colMax);
        rowMax = (m.getMaxRow() > colMax) ? (colMax) : (m.getMaxRow());
        for (int i = rowMax - 1; i >= 0; i--) {
            for (int j = i; j >= 1; j--){
                m.addOBE(j, (i + 1), -m.getElement(j, i + 1));
                // m.setRow(j,RowOperation.PlusTab(m.getRow(j), RowOperation.kaliC(m.getRow(i+1), -1*m.getElement(j,i+1))));
            }
        }
        return m;
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
}
