package tubes;

import java.util.Scanner;

public class Matrix {
    public float[][] content;
    public int maxR, maxC;
    private Scanner input = new Scanner(System.in);

    //** Konstruktor **//
    public Matrix() {}

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
    public int getBaris() {
      return (this.maxR);
    }
    public int getKolom() {
        return (this.maxC);
    }
    public float[][] getContent() {
        return (this.content);
    }
    public float getElmt(int r, int c) {
        return (this.content[r][c]);
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
            for (int c = 1; c <= this.maxC; ++c)
                this.content[r][c] = arr[r][c];
    }
    public void setElmt(int r, int c, float val) {
      this.content[r][c] = val;
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
        for (int i = 0; i < this.maxR; i++) {
            for (int j = 0; j < this.maxC; j++) {
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
        Matrix m = new Matrix(this.content);
        return (m);
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
    public void transpose(){
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
    public float[] getSistemPersamaanLinear() {
        // TODO: implement
        return new float[this.maxC+1];
    }
    public float getDeterminan() {
        // TODO: implement
        return 0;
    }
    public Matrix getInverseMatrix() {
        // TODO: implement
        return new Matrix(this.maxR, this.maxC);
    }
    public Matrix getCofactorMatrix() {
        // TODO: implement
        return new Matrix(this.maxR, this.maxC);
    }
    public Matrix getAdjoinMatrix() {
        // TODO: implement
        return new Matrix(this.maxR, this.maxC);
    }
    public Matrix getEchelonForm() {
        // TODO: implement
        return new Matrix(this.maxR, this.maxC);
    }
    public Matrix getReducedEchelonForm() {
        // TODO: implement
        return new Matrix(this.maxR, this.maxC);
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
