package tubes;

import java.math.BigDecimal;
import java.util.*;

/**
 * Class SPL digunakan untuk membuat sebuah sistem persamaan linier dari sebuah matriks.
 */
public class SPL {
    /**
     * Konten dari sistem persamaan linier yang bisa mempunyai variabel bebas.
     */
    public BigDecimal[][] content;

    /**
     * Konstruktor SPL.
     */
    private SPL() {}

    /**
     * Konstruktor SPL dengan solusi yang sudah didefinisikan.
     * @param sol Solusi dalam bentuk array of double.
     */
    public SPL(BigDecimal[] sol) {
        content = new BigDecimal[sol.length][sol.length];
        for (int i = 1; i < sol.length; i++)
            content[i][0] = sol[i];
    }

    /**
     * Konstruktor SPL dengan menggunakan solusi SPL yang sudah ada.
     * @param sol Solusi dalam bentuk array of array of double.
     */
    public SPL(BigDecimal[][] sol) {
        this.content = new BigDecimal[sol.length+1][];
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
     * F.S Menghasilkan true jika o adalah obyek SPL yang sama dengan SPL ini.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SPL))
            return false;
            BigDecimal[][] content2 = ((SPL)o).content;
        if (content.length != content2.length)
            return false;
        for (int i = 1; i < content.length; i++) {
            if (content[i].length != content2[i].length)
                return false;
            for (int j = 0; j < content[i].length; j++)
                if (!BD.eqTest(content[i][j], content2[i][j]))
                  return false;
        }
        return true;
    }

    /**
     * F.S Melakukan substutusi variabel yang didapatkan dari SPL dari Gauss.
     */
    public void substituteEquations() {
        for (int i = this.content.length-2; i >= 1; i--) {
            for (int j = i+1; j < this.content[i].length; j++) {
                BigDecimal s_up = this.content[i][j];
                this.content[i][j] = BigDecimal.ZERO;
                this.content[i][0] = this.content[i][0].add(s_up.multiply(this.content[j][0]));
                for (int jj = j; jj < this.content[i].length; jj++)
                    this.content[i][jj] = this.content[i][jj].add(s_up.multiply(this.content[j][jj]));
            }
        }
    }

    /**
     * I.S Matriks in merupakan sebuah echelon form atau reduced echelon form.
     * F.S Mengubah matriks in menjadi bentuk SPL.
     * @param in Matriks echelon / reduced echelon form.
     */
    public void initREF(Matrix in) {
        int r = in.getMaxRow(), size = in.getMaxColumn()-1;
        for (int i = size+1; i <= r; i++)
            if (!BD.eq0(in.getElement(i, in.getMaxColumn())))
                throw new MatrixException(MatrixErrorIdentifier.INCONSISTENT_ERROR);
        content = new BigDecimal[size+1][size+1];
        content[0] = new BigDecimal[0];
        for (int i = 1; i <= size; i++)
            for (int j = 0; j <= size; j++)
                content[i][j] = BigDecimal.ZERO;
        Matrix m = new Matrix(size, size+1);
        for (int i = 1; i <= Math.min(r, size); i++)
            for (int j = 1; j <= size+1; j++)
                m.setElement(i, j, in.getElement(i, j));
        int emptyColCount = 0;
        for (int i = 1; i <= size; i++) {
            int j = 0;
            while (j+1 <= size+1 && BD.eq0(m.getElement(i, ++j)));
            if (j == size+1) {
                if (!BD.eq0(m.getElement(i, j)))
                    throw new MatrixException(MatrixErrorIdentifier.INCONSISTENT_ERROR);
                if (j > i+emptyColCount)
                    this.content[i+emptyColCount][i+emptyColCount] = BigDecimal.ONE;
                continue;
            }
            if (j > i+emptyColCount) {
                this.content[i+emptyColCount][i+emptyColCount] = BigDecimal.ONE;
                ++emptyColCount;
            }
            this.content[j][0] = m.getElement(i, size+1);
            for (int jj = j+1; jj <= size; jj++)
                this.content[j][jj] = m.getElement(i, jj).negate();
        }
    }

    /**
     * F.S Mengevaluasi nilai polinom hasil interpolasi di x.
     * @param x Nilai x yang ingin dievaluasi.
     * @return Hasil evaluasi.
     */
    public BigDecimal eval(double x) {
        BigDecimal res = BigDecimal.ZERO;
        for (int i = 1; i < content.length; i++)
            res = res.add(content[i][0].multiply(new BigDecimal(x).pow(i-1)));
        return res;
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
                BigDecimal v = content[i][j];
                if (BD.eq0(v) && j > 0)
                    continue;
                if (j > 0 && found) {
                    if (BD.lt(v, BigDecimal.ZERO)) {
                        v = v.negate();
                        sb.append(" - ");
                    } else
                        sb.append(" + ");
                }
                if (!BD.eq0(v))
                    found = true;
                if (!BD.eq0(v))
                    sb.append(BD.format(v));
                if (j > 0)
                    sb.append("s" + j);
            }
            if (!found)
                sb.append(BD.format(content[i][0]));
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
        StringBuilder sb = new StringBuilder("f(x) = ");
        boolean found = false;
        for (int i = content.length-1; i >= 1; i--) {
            BigDecimal v = content[i][0];
            if (BD.eq0(v))
                continue;
            if (found) {
                if (BD.lt(v, BigDecimal.ZERO)) {
                    v = v.negate();
                    sb.append(" - ");
                } else
                    sb.append(" + ");
            }
            found = true;
            boolean isOne = BD.eq1(v);
            if (isOne && i == 1 || !isOne)
                sb.append(BD.formatLong(v));
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
