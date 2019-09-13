package com.nafkhanzam;

public class Matrix {
    //** Kamus **//
    public float[][] m;
    public int maxR, maxC;
    //** Konstruktor **//
    public Matrix(int maxR, int maxC) {
        this.maxR = maxR;
        this.maxC = maxC;
        this.m = new float[maxR+1][maxC+1];
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
        return (this.m);
    }
    public float getElmt(int r, int c) {
        return (this.m[r][c]);
    }
    //-- Selektor: Set --//
    public void setBaris(int maxR) {
        this.maxR = maxR;
    }
    public void setKolom(int maxC) {
        this.maxC = maxC;
    }
    public void setContent(float[][] arr) {
        for (int r = 1; r <= maxR; ++r)
            for (int c = 1; c <= maxC; ++c)
                m[r][c] = arr[r][c];
    }
    public void setElmt(int r, int c, float val) {
      this.m[r][c] = val;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 1; r <= maxR; r++)
            for (int c = 1; c <= maxC; c++)
                sb.append(getElmt(r, c) + (c == maxC ? "\n" : " "));
        return sb.toString();
    }
    public float[] getSistemPersamaanLinear() {
        // TODO: implement
        return new float[maxC+1];
    }
    public float getDeterminan() {
        // TODO: implement
        return 0;
    }
    public Matrix getInverseMatrix() {
        // TODO: implement
        return new Matrix(maxR, maxC);
    }
    public Matrix getCofactorMatrix() {
        // TODO: implement
        return new Matrix(maxR, maxC);
    }
    public Matrix getAdjoinMatrix() {
        // TODO: implement
        return new Matrix(maxR, maxC);
    }
    public Matrix getEchelonForm() {
        // TODO: implement
        return new Matrix(maxR, maxC);
    }
    public Matrix getReducedEchelonForm() {
        // TODO: implement
        return new Matrix(maxR, maxC);
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Matrix))
            return false;
        Matrix m = (Matrix)o;
        if (m.maxR != this.maxR || m.maxC != this.maxC)
            return false;
        for (int r = 1; r <= maxR; ++r)
            for (int c = 1; c <= maxC; ++c)
                if (m.content[r][c] != this.content[r][c])
                    return false;
        return true;
    }
    // Tambahin lagi, klo bisa conventionnya sama, biar rapi..
}
