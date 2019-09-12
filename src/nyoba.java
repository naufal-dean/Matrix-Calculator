import java.util.Scanner;

import sun.security.util.Length;
//echelon form baru sistem persamaan yg matriksnya persegi??gatau dahh
class nyoba{
    static double[][] echelon(double[][] M){
        int  i,j, idx;
        double c;
        for(j=0; j<M[0].length - 2;j++){
            i = j;
            while((M[i][j] == 0) && (i<M.length)){
                i++;
            }//cari ampe yang ga 0 dibarisannya
            idx = i;
            i = i+1;
            for(;i<M.length;i++){
                //eliminasi yang lainnya dengan baris idx
                c = M[i][j]/M[idx][j];
                if (c!=0){
                    M[i] = kaliC(M[i], 1/c);
                    M[i] = MinTab(M[i],M[idx]);
                }
            }
            M[idx]= kaliC(M[idx],1/M[idx][j]);
            //pindahin ke paling atas
            double[] temp = M[j];
            M[j] = M[idx];
            M[idx] = temp ;
        }
        M[j]=kaliC(M[j],1/M[j][j]);
        return M; 
    }
   static double[][] REform(double[][] M){
        M= echelon(M);
        /*for(int j= M[0].length -2; j>0; j--){
            for(int i = j-1; i>=0;i--){
               double c= M[i][j]/M[j][j];
               M[i]= MinTab(M[i], kaliC(M[j], c));
            }

        }*/
        for (int i = M.length -2; i>=0;i--){
            for (int j = i; j>=0;j--){
                M[j]=MinTab(M[j], kaliC(M[i+1], M[j][i+1]));
            }
        }
        return M;
    }
    /*
    gatau cara bikin prosedur yang bisa ngubahhhh input nya helpp
    static double[] ganti(double[] T1,double[] T2){
        int i;
        double temp;
        for (i=0; i< T1.length;i++){
            temp = T1[i];
            T1[i] = T2[i];
            T2[i] = temp;
        }
    }
    */
    static double[] kaliC(double[] T,double c){
        double[] A= new double [T.length] ;
        int i;
        for (i=0; i< T.length;i++){
            A[i]= T[i] * c;
        }
        return A;
    }
    static double[] MinTab(double[] T1,double[] T2){
        int i;
        double[] A= new double [T1.length] ;
        for (i=0; i< T1.length;i++){
            A[i] = T1[i] - T2[i];
        }
        return A;
    }
    public static void main(String[] args){
    double[][] M = {{1,2,3,4},{2,2,1,3},{1,5,4,2}/*{1,2,4},{1,3,5}*/};
        M = echelon(M);
        M= echelon(M);
        for (int i=0; i<M.length;i++){
            for(int j =0; j<M[0].length;j++){
                System.out.print(M[i][j]+" ");
            }
            System.out.println();
        }
        M = REform(M);
        for (int i=0; i<M.length;i++){
            for(int j =0; j<M[0].length;j++){
                System.out.print(M[i][j]+" ");
            }
            System.out.println();
        }
    }


}