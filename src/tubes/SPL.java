package tubes;

import java.util.*;

/**
 * Class SPL digunakan untuk membuat sebuah sistem persamaan linier dari sebuah matriks.
 */
public class SPL {
    /**
     * Konten dari sistem persamaan linier yang bisa mempunyai variabel bebas.
     */
    public double[][] content;

    /**
     * Konstruktor SPL.
     */
    public SPL() {}

    /**
     * Konstruktor SPL dengan solusi yang sudah didefinisikan.
     * @param sol Solusi dalam bentuk array of double.
     */
    public SPL(double[] sol) {
        content = new double[sol.length][sol.length];
        for (int i = 1; i < sol.length; i++)
            content[i][0] = sol[i];
    }
    
    /**
     * Konstruktor SPL dengan menggunakan solusi SPL yang sudah ada.
     * @param sol Solusi dalam bentuk array of array of double.
     */
    public SPL(double[][] sol) {
        this.content = new double[sol.length+1][];
        for (int i = 0; i < sol.length; i++)
            this.content[i+1] = Arrays.copyOf(sol[i], sol[i].length);
    }

    /**
     * F.S Membuat SPL dari sebuah matrix m dengan mengubah ke bentuk echelon form.
     * @param m Matriks sembarang.
     * @return Sistem persamaan linier hasil dari matriks m.
     */
    public static SPL getEchelonFormMethod(Matrix m) {
        SPL spl = new SPL();
        spl.initREF(m.getEchelonForm(m.getMaxColumn()-1));
        spl.substituteEquations();
        return spl;
    }

    /**
     * F.S Membuat SPL dari sebuah matrix m dengan mengubah ke bentuk reduced echelon form.
     * @param m Matriks sembarang.
     * @return Sistem persamaan linier hasil dari matriks m.
     */
    public static SPL getReducedEchelonFormMethod(Matrix m) {
        SPL spl = new SPL();
        spl.initREF(m.getReducedEchelonForm(m.getMaxColumn()-1));
        return spl;
    }

    /**
     * F.S Melakukan substutusi variabel yang didapatkan dari SPL dari Gauss.
     */
    public void substituteEquations() {
        for (int i = this.content.length-1; i >= 1; i--) {
            for (int j = i+1; j < this.content[i].length; j++) {
                double s_up = this.content[i][j];
                this.content[i][j] = 0;
                this.content[i][0] += s_up*this.content[j][0];
                for (int jj = j; jj < this.content[i].length; jj++)
                    this.content[i][jj] += s_up*this.content[j][jj];
            }
        }
    }

    /**
     * F.S Menghasilkan true jika o adalah obyek SPL yang sama dengan SPL ini.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SPL))
            return false;
        double[][] content2 = ((SPL)o).content;
        if (content.length != content2.length)
            return false;
        for (int i = 1; i < content.length; i++) {
            if (content[i].length != content2[i].length)
                return false;
            for (int j = 0; j < content[i].length; j++)
                if (!Utils.doubleEquals(content[i][j], content2[i][j]))
                  return false;
        }
        return true;
    }

    /**
     * F.S Mengubah marrix in menjadi bentuk SPL.
     * @param in Matriks echelon / reduced echelon form.
     */
    public void initREF(Matrix in) {
        int r = in.getMaxRow(), c = in.getMaxColumn()-1;
        for (int i = c+1; i <= r; i++)
            if (in.getElement(i, in.getMaxColumn()) != 0)
                throw new MatrixException(MatrixErrorIdentifier.INCONSISTENT_ERROR);
        content = new double[c+1][c+1];
        content[0] = new double[0];
        Matrix m = new Matrix(c, c+1);
        for (int i = 1; i <= Math.min(r, c); i++)
            for (int j = 1; j <= c+1; j++)
                m.setElement(i, j, in.getElement(i, j));
        int j = 1;
        for (int i = 1; i <= c; i++) {
            if (j > c) {
                if (m.getElement(i, j) != 0)
                    throw new MatrixException(MatrixErrorIdentifier.INCONSISTENT_ERROR);
                break;
            }
            if (m.getElement(i, j) == 0) {
                content[j][j] = 1;
                j++;
                i--;
                continue;
            }
            content[j][0] = m.getElement(i, c+1);
            for (int j2 = j+1; j2 <= c; j2++)
                if (m.getElement(i, j2) != 0)
                    content[i][j2] = -m.getElement(i, j2);
            j++;
        }
    }

    /**
     * F.S Merepresentasikan SPL ini dalam bentuk string.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < content.length; i++) {
            sb.append("x" + i + " = ");
            boolean found = false;
            for (int j = 0; j < content[i].length; j++) {
                double v = content[i][j];
                if (v == 0 && j > 0)
                    continue;
                if (j > 0 && found) {
                    if (v < 0) {
                        v = -v;
                        sb.append(" - ");
                    } else
                        sb.append(" + ");
                }
                if (v != 0)
                    found = true;
                if (v != 0)
                    sb.append(String.format("(%.2f)", v));
                if (j > 0)
                    sb.append("s" + j);
            }
            if (!found)
                sb.append(String.format("(%.2f)", content[i][0]));
            if (i < content.length-1)
                sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * F.S Mengubah bentuk SPL ini menjadi string persamaan berdasarkan variabel bebas.
     * @return Representasi persamaan dalam bentuk string.
     */
    public String toPersamaanString() {
        StringBuilder sb = new StringBuilder("y = ");
        boolean found = false;
        for (int i = content.length-1; i >= 1; i--) {
            double v = content[i][0];
            if (Utils.doubleEquals(v, 0))
                continue;
            if (found) {
                if (v < 0) {
                    v = -v;
                    sb.append(" - ");
                } else
                    sb.append(" + ");
            }
            found = true;
            boolean isOne = Utils.doubleEquals(v, 1);
            if (isOne && i == 1 || !isOne)
                sb.append(String.format("(%.10f)", v));
            if (i >= 2) {
                sb.append("x");
                if (i >= 3)
                    sb.append("^" + (i-1));
            }
        }
        if (!found)
            sb.append(0);
        return sb.toString();
    }
}
