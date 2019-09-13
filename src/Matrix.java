package com.nafkhanzam;

public class Matrix {
    private float[][] m;
    private int maxR, maxC;
    public Matrix(int maxR, int maxC) {
        this.maxR = maxR;
        this.m = new float[maxR+1][maxC+1];
    }
    public void set(int r, int c, float v) {
        m[r][c] = v;
    }
    public float get(int r, int c) {
        return m[r][c];
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 1; r <= maxR; r++)
            for (int c = 1; c <= maxC; c++)
                sb.append(get(r, c) + (c == maxC ? "\n" : " "));
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
    public boolean equals(Object o) {
        if (!(o instanceof Matrix))
            return false;
        Matrix m = (Matrix)o;
        if (m.maxR != maxR || m.maxC != maxC)
            return false;
        for (int r = 1; r <= maxR; ++r)
            for (int c = 1; c <= maxC; ++c)
                if (get(r, c) != m.get(r, c))
                    return false;
        return true;
    }
    // Tambahin lagi, klo bisa conventionnya sama, biar rapi..
}