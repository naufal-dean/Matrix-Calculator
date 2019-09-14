
package tubes;

//nopal
public class RowOperation {
    static double[] kaliC(double[] R,double c){
        double[] A= new double [R.length] ;
        int i;
        for (i=1; i< R.length;i++){
            A[i]= R[i] * c;
        }
        return A;
    }
    static double[] PlusTab(double[] R1,double[] R2){
        int i;
        double[] A= new double [R1.length] ;
        for (i=1; i< R1.length;i++){
            A[i] = R1[i] + R2[i];
        }
        return A;
    }
}
