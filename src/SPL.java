package tubes;




import static tubes.Console.*;

public class SPL {
    public double[][] content;
    // i: untuk Xi
    // j: j == 0 -> no var, j == 1 -> s1, j == 2 -> s2, dst
    // cth: X1 = 4 - s1 + 2s2
    // content[1][0] = 4;
    // content[1][1] = -1;
    // content[1][2] = 2;

    public SPL(Matrix m) {
        // outln("BEFORE");
        // out(m);
        // outln("AFTER");
        initREF(m.getReducedEchelonForm(m.getMaxColumn()-1));
    }

    public SPL(double[] sol) {
        content = new double[sol.length][1];
        for (int i = 1; i < sol.length; i++)
            content[i][0] = sol[i];
    }

    // private void initREF(Matrix m) {
    //     int r = m.getMaxRow(), c = m.getMaxColumn()-1;
    //     for (int i = c+1; i <= r; i++)
    //         if (m.getElement(i, m.getMaxColumn()) != 0)
    //             throw new RuntimeException("Matrix is inconsistent!");
    //     r = c;
    //     content = new double[c+1][c+1];
    //     while (r >= 1) {
    //         int j = 1;
    //         boolean found = false;
    //         while (j <= c) {
    //             if (m.getElement(r, j) != 0) {
    //                 found = true;
    //                 break;
    //             }
    //         }
    //         content[j][0] = m.getElement(r, c+1);
    //         if (!found) {
    //             // outln(r + "," + j + "," + (c+1));
    //             if (m.getElement(r, c+1) != 0)
    //                 throw new RuntimeException("Matrix is inconsistent!");
    //             content[j][j] = 1;
    //         } else
    //             for (int j2 = j+1; j2 <= c; j2++) {
    //                 double v2 = m.getElement(r, j2);
    //                 if (v2 != 0)
    //                     content[j][j2] = -v2;
    //             }
    //         r--;
    //     }
    // }

    public void initREF(Matrix in) {
        int r = in.getMaxRow(), c = in.getMaxColumn()-1;
        for (int i = c+1; i <= r; i++)
            if (in.getElement(i, in.getMaxColumn()) != 0)
                throw new RuntimeException("Matrix is inconsistent!");
        content = new double[c+1][c+1];
        Matrix m = new Matrix(c, c+1);
        for (int i = 1; i <= in.getMaxRow(); i++)
            for (int j = 1; j <= c+1; j++)
                m.setElement(i, j, in.getElement(i, j));
        int j = 1;
        for (int i = 1; i <= c; i++) {
            if (j > c)
                break;
            if (m.getElement(i, j) == 0) {
                // outln(i + "," + j + " = " + 0);
                content[j][j] = 1;
                j++;
                i--;
                continue;
            }
            content[j][0] = m.getElement(i, c+1);
            // outln(i + "," + j + " = " + m.getElement(i, j));
            for (int j2 = j+1; j2 <= c; j2++)
                if (m.getElement(i, j2) != 0) {
                    content[i][j2] = -m.getElement(i, j2);
                    outln(i + "," + j2 + " = " + m.getElement(i, j2));
                }
            j++;
        }
    }

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
            sb.append("\n");
        }
        return sb.toString();
    }
}