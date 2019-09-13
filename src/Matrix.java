package src;
import java.util.Scanner;

public class Matrix {
    private int row;
    private int col;
    private double[][] value;
    private Scanner input = new Scanner(System.in);

    // ** Constructor ** //
    public Matrix() {}

    public Matrix(int Row, int Col) {
        this.row = Row;
        this.col = Col;
        value = new double[Row][Col];
    }

    public Matrix(double[][] Val) {
        this.row = Val.length;
        this.col = Val[0].length;

        value = new double[this.row][this.col];
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                value[i][j] = Val[i][j];
            }
        }
    }

    // ** Selektor **//
    // -- Get -- //
    public int GetBaris() {
      return (this.row);
    }

    public int GetKolom() {
        return (this.col);
    }

    public double[][] GetValue() {
        return (this.value);
    }

    public double GetElmt(int Row, int Col) {
        return (this.value[Row][Col]);
    }

    // -- Set -- //
    public void SetBaris(int Row) {
        this.row = Row;
    }

    public void SetKolom(int Col) {
        this.col = Col;
    }

    public void SetValue(double[][] Val) {
        this.value = Val;
    }

    public void SetElmt(int Row, int Col, double El) {
        this.value[Row][Col] = El;
    }

    // ** Fungsi Baca dan Tulis **//
    public void BacaMatrix() {
        // Input row dan col
        System.out.print("Jumlah baris: ");
        this.row = input.nextInt();
        System.out.print("Jumlah kolom: ");
        this.col = input.nextInt();
        while ((this.row <= 0) || (this.col <= 0)) {
            System.out.println("Jumlah kolom dan baris harus positif.");

            System.out.print("Jumlah baris: ");
            this.row = input.nextInt();
            System.out.print("Jumlah kolom: ");
            this.col = input.nextInt();
        }
        // Inisialisasi value
        this.value = new double[this.row][this.col];
        // Input elemen
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.printf("Elemen[%d,%d]: ", i, j);
                this.value[i][j] = input.nextDouble();
            }
        }
    }

    public void TulisMatrix() {
        for (int i = 0; i < row; i++) {
            System.out.print("| ");
            for (int j = 0; j < col; j++) {
                System.out.printf("%f ", value[i][j]);
            }
            System.out.println("|");
        }
    }

    // ** Fungsi utility **//
    public Matrix CopyMatrix() {
        Matrix M = new Matrix(this.row, this.col);
        M.SetValue(this.value);
        return (M);
    }

    // ** Fungsi pada matriks **//
    public void Transpose(){
        // Kamus lokal
        double tempVal[][];
        int temp;
        // Algoritma
        // Transpose value
        tempVal = new double[col][row];
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                tempVal[j][i] = value[i][j];
            }
        }
        value = tempVal;
        // Swap row col
        temp = this.col;
        this.col = this.row;
        this.row = temp;
    }

    public void Gauss() {

    }
}
