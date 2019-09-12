package com.nafkhanzam;

public class Matrix {
    private float[][] m;
    private int maxR, maxC;
    public Matrix(int maxR, int maxC) {
        this.maxR = maxR;
        this.m = new int[maxR+1][maxC+1];
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
        return null;
    }
    public float getDeterminan() {
        // TODO: implement
        return null;
    }
    public Matrix getInverseMatrix() {
        // TODO: implement
        return null;
    }
    public Matrix getCofactorMatrix() {
        // TODO: implement
        return null;
    }
    public Matrix getAdjoinMatrix() {
        // TODO: implement
        return null;
    }
    public Matrix getEchelonForm() {
        // TODO: implement
        return null;
    }
    public Matrix getReducedEchelonForm() {
        // TODO: implement
        return null;
    }
    // Tambahin lagi, klo bisa conventionnya sama, biar rapi..
}