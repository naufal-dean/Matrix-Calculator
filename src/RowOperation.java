
package tubes;

public class RowOperation {
    static float[] kaliC(float[] R,float c){
        float[] A= new float [R.length] ;
        int i;
        for (i=1; i< R.length;i++){
            A[i]= R[i] * c;
        }
        return A;
    }
    static float[] PlusTab(float[] R1,float[] R2){
        int i;
        float[] A= new float [R1.length] ;
        for (i=1; i< R1.length;i++){
            A[i] = R1[i] + R2[i];
        }
        return A;
    }
}
