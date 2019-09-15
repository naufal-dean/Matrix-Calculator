package tubes;

public class SPL {
    public double[][] content;
    // i: untuk Xi
    // j: j == 0 -> no var, j == 1 -> s1, j == 2 -> s2, dst
    // cth: X1 = 4 - s1 + 2s2
    // content[1][0] = 4;
    // content[1][1] = -1;
    // content[1][2] = 2;

    public SPL(Matrix m) {
        int col = m.getMaxColumn();
        init(new Matrix(m.subMatrixContent(1, 1, Math.min(m.getMaxRow(), col-1), col)).getReducedEchelonForm(col-1));
    }

    public SPL(double[] sol) {
        content = new double[sol.length][1];
        for (int i = 1; i < sol.length; i++)
            content[i][0] = sol[i];
    }

    private void init(Matrix m) {
        int r = m.getMaxRow(), c = m.getMaxColumn()-1;
        content = new double[c+1][c+1];
        for (int j = c; j >= 1; j--) {
            double v = content[j][0] = m.getElement(r, j);
            if (v == 0) {
                if (m.getElement(r, c+1) != 0)
                    throw new RuntimeException("Matrix is inconsistent!");
                content[j][1] = 1;
            } else
                for (int j2 = j+1; j2 <= c; j2++) {
                    double v2 = m.getElement(r, j2);
                    if (v2 != 0)
                        content[j][c-j2+1] = v2;
                }
            r--;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < content.length; i++) {
            sb.append("x" + i + " = ");
            for (int j = 0; j < content[i].length; j++) {
                double v = content[i][j];
                if (j > 0) {
                    if (v < 0) {
                        v = -v;
                        sb.append(" - ");
                    } else
                        sb.append(" + ");
                }
                sb.append(v);
                if (j > 0)
                    sb.append("s" + j);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}