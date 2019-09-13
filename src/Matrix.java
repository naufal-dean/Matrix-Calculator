package tubes;

import java.util.Scanner;

public class Matrix {
    public float[][] content;
    public int maxR, maxC;
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
    public int getBaris() {
      return this.maxR;
    }
    public int getKolom() {
        return this.maxC;
    }
    public float[][] getContent() {
        return this.content;
    }
    public float getElmt(int r, int c) {
        return this.content[r][c];
    }
    //-- Selektor: Set --//
    public void setBaris(int maxR) {
        this.maxR = maxR;
    }
    public void setKolom(int maxC) {
        this.maxC = maxC;
    }
    public void setContent(float[][] arr) {
        for (int r = 1; r <= this.maxR; ++r)
            this.content[r] = Arrays.copyOf(arr[r], arr[r].length);
    }
    public void setElement(int r, int c, float val) {
        this.content[r][c] = val;
    }
    public float[] getARow(int r) {
        return Arrays.copyOf(this.content[r], this.content[r].length);
    }
    public void setARow(int r,float[] row) {
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
    public float[] getSistemPersamaanLinear() {
        // TODO: implement
        return new float[this.maxC+1];
    }
    public float getDeterminan() {
        // TODO: implement
        return 0;
    }
    public void transpose() {
        // Kamus lokal
        float tempVal[][];
        int temp;
        // Algoritma
        // Transpose value
        tempVal = new float[this.maxC][this.maxR];
        for (int i = 0; i < this.maxR; i++) {
            for (int j = 0; j < this.maxC; j++) {
                tempVal[j][i] = this.content[i][j];
            }
        }
        this.content = tempVal;
        // Swap maxR maxC
        temp = this.maxC;
        this.maxC = this.maxR;
        this.maxR = temp;
    }
    public Matrix getTransposeMatrix() {
        // TODO: implement
        return new float[this.maxC+1];
    }
    public void inverse() {
        // TODO: implement
        Matrix M = this.copyMatrix();
        int  i,j, idx;
        float c;
        float det=1;
        //double M1[][];
        //M1= new double[M.length][M[0].length];
        //M1=M;
        for(j=1; j<=M.getBaris() -1 ;j++){
            i = j;
            while((M.getElmt(i,j) == 0) && (i<=M.getBaris())){
                i++;
            }//cari ampe yang ga 0 dibarisannya
            idx = i;
            i = i+1;
            for(;i<=M.getBaris();i++){
                //eliminasi yang lainnya dengan baris idx
                c = M.getElmt(i,j)/M.getElmt(idx,j);
                if (c!=0){
                    M.setARow(i,(RowOperation.kaliC(M.getARow(i), 1/c)));
                    det *= c;
                    M.setARow(i,(RowOperation.PlusTab(M.getARow(i),RowOperation.kaliC(M.getARow(idx),-1))));
                }
            }
            //pindahin ke paling atas
            if(j!=idx){
                det *=-1;
                float[] temp = M.getARow(j);
                M.setARow(j, M.getARow(idx));
                M.setARow(idx,temp) ;
            }
        }
        for (i=1; i<=M.getBaris();i++){
             det *= M.getElmt(i,i);
        }

        return det;
    }
    public Matrix getInverseMatrix() {
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
    public void echelonForm() {
        // TODO: implement
    }
    public Matrix getEchelonForm() {
        // TODO: implement
        Matrix M = this.copyMatrix();
        int  i,j, idx;
        float c;
        //double M1[][];
        //M1= new double[M.length][M[0].length];
        //M1=M;
        for(j=1; j<=M.getBaris() -1 ;j++){
            i = j;
            while((M.getElmt(i,j) == 0) && (i<M.getBaris())){
                i++;
            }//cari ampe yang ga 0 dibarisannya
            idx = i;
            i = i+1;
            for(;i<=M.getBaris();i++){
                //eliminasi yang lainnya dengan baris idx
                c = M.getElmt(i,j)/M.getElmt(idx,j);
                if (c!=0){
                    M.setARow(i,(RowOperation.kaliC(M.getARow(i), 1/c)));
                    M.setARow(i,(RowOperation.PlusTab(M.getARow(i),RowOperation.kaliC(M.getARow(idx),-1))));
                }
            }
            M.setARow(idx, RowOperation.kaliC(M.getARow(idx),1/M.getElmt(idx,j)));
            //pindahin ke paling atas
            float[] temp = M.getARow(j);
            M.setARow(j, M.getARow(idx));
            M.setARow(idx,temp) ;
        }
        M.setARow(j,RowOperation.kaliC(M.getARow(j),1/M.getElmt(j,j)));
        return M; 
    }
    public void reducedEchelonForm() {
        // TODO: implement
    }
    public Matrix getReducedEchelonForm() {
        Matrix M = new Matrix(maxR,maxC);
        M = this.getEchelonForm();
        for (int i = M.getBaris() -1; i>=0;i--){
            for (int j = i; j>=1;j--){
                M.setARow(j,RowOperation.PlusTab(M.getARow(j), RowOperation.kaliC(M.getARow(i+1), -1*M.getElmt(j,i+1))));
            }
        }
        return M;
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Matrix))
            return false;
        Matrix m = (Matrix)o;
        if (m.maxR != this.maxR || m.maxC != this.maxC)
            return false;
        for (int r = 1; r <= this.maxR; ++r)
            for (int c = 1; c <= this.maxC; ++c)
                if (m.content[r][c] != this.content[r][c])
                    return false;
        return true;
    }
    // Tambahin lagi, klo bisa conventionnya sama, biar rapi..
}
