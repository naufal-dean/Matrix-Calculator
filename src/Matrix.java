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
    public Matrix(Matrix m) {
        this(m.content);
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
    //-- Selektor: Set --//
    public void setMaxRow(int maxR) {
        this.maxR = maxR;
    }

    public void setMaxColumn(int maxC) {
        this.maxC = maxC;
    }
    public void setContent(float[][] arr) {
        for (int r = 1; r <= this.maxR; ++r)
            this.content[r] = Arrays.copyOf(arr[r], arr[r].length);
    }
    public void setElement(int r, int c, float val) {
        this.content[r][c] = val;
    }

    public float[] getRow(int r) {
        return Arrays.copyOf(this.content[r], this.content[r].length);
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
        this.content = new float[this.maxR][this.maxC];
        // Input elemen
        for (int i = 1; i <= this.maxR; i++) {
            for (int j = 1; j <= this.maxC; j++) {
                System.out.printf("Elemen[%d,%d]: ", i, j);
                this.content[i][j] = input.nextFloat();
            }
        }
    }
    public void tulisMatrix() {
        for (int i = 0; i < this.maxR; i++) {
            System.out.print("| ");
            for (int j = 0; j < this.maxC; j++) {
                System.out.printf("%f ", this.content[i][j]);
            }
            System.out.println("|");
        }
    }
    public Matrix copyMatrix() {
        return new Matrix(this.content);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 1; r <= this.maxR; r++)
            for (int c = 1; c <= this.maxC; c++)
                sb.append(this.content[r][c] + (c == this.maxC ? "\n" : " "));
        return sb.toString();
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
    public Matrix getTransposeMatrix() {
        // Kamus lokal
        float tempVal[][];
        int temp;
        // Algoritma
        // Transpose value
        tempVal = new float[this.maxC][this.maxR];
        for (int i = 1; i <= this.maxR; i++) {
            for (int j = 1; j <= this.maxC; j++) {
                tempVal[j][i] = this.content[i][j];
            }
        }
        // this.content = tempVal;
        // // Swap maxR maxC
        // temp = this.maxC;
        // this.maxC = this.maxR;
        // this.maxR = temp;
        return new Matrix(tempVal);
    }

    public Matrix getInverseMatrix(Method method) {
        // TODO: implement
        return new Matrix(this.maxR, this.maxC);
    }
    public void cofactor() {
        // TODO: implement
    }
    public Matrix getCofactorMatrix() {
        // TODO: implement
        return new Matrix(this.maxR, this.maxC);
    }
    public void adjoin() {
        // TODO: implement
    }
    public Matrix getAdjoinMatrix() {
        // TODO: implement
        return new Matrix(this.maxR, this.maxC);
    }

    //nopal
    public Matrix getEchelonForm() {
        // TODO: implement
        Matrix M = this.copyMatrix();
        int  i,j, idx;
        float c;
        //double M1[][];
        //M1= new double[M.length][M[0].length];
        //M1=M;
        for(j=1; j<=M.getMaxRow() -1 ;j++){
            i = j;
            while((M.getElement(i,j) == 0) && (i<M.getMaxRow())){
                i++;
            }//cari ampe yang ga 0 dibarisannya
            idx = i;
            i = i+1;
            for(;i<=M.getMaxRow();i++){
                //eliminasi yang lainnya dengan baris idx
                c = M.getElement(i,j)/M.getElement(idx,j);
                if (c!=0){
                    M.setRow(i,(RowOperation.kaliC(M.getRow(i), 1/c)));
                    M.setRow(i,(RowOperation.PlusTab(M.getRow(i),RowOperation.kaliC(M.getRow(idx),-1))));
                }
            }
            M.setRow(idx, RowOperation.kaliC(M.getRow(idx),1/M.getElement(idx,j)));
            //pindahin ke paling atas
            float[] temp = M.getRow(j);
            M.setRow(j, M.getRow(idx));
            M.setRow(idx,temp) ;
        }
        M.setRow(j,RowOperation.kaliC(M.getRow(j),1/M.getElement(j,j)));
        return M; 
    }

    //nopal
    public Matrix getReducedEchelonForm() {
        Matrix M = new Matrix(maxR,maxC);
        M = this.getEchelonForm();
        for (int i = M.getMaxRow() -1; i>=0;i--){
            for (int j = i; j>=1;j--){
                M.setRow(j,RowOperation.PlusTab(M.getRow(j), RowOperation.kaliC(M.getRow(i+1), -1*M.getElement(j,i+1))));
            }
        }
        return M;
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
