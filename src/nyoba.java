import java.util.Scanner;

//echelon form baru sistem persamaan yg matriksnya persegi??gatau dahh
class nyoba{
    static double[][] echelon(double[][] M){
        int  i,j, idx;
        double c;
        //double M1[][];
        //M1= new double[M.length][M[0].length];
        //M1=M;
        for(j=1; j<M/*[0]*/.length -1 ;j++){
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
                    M[i] = PlusTab(M[i],kaliC(M[idx],-1));
                }
            }
            M[idx]= kaliC(M[idx],1/M[idx][j]);
            //pindahin ke paling atas
            double[] temp = M[j];
            M[j] = M[idx];
            M[idx] = temp ;
        }
        M[j] = kaliC (M[j],1/M[j][j]);
        return M;
    }
   static double[][] REform(double[][] M){
        double[][] M1= new double[M.length][M[0].length];
        M1= echelon(M);
        /*for(int j= M[0].length -2; j>0; j--){
            for(int i = j-1; i>=0;i--){
               double c= M[i][j]/M[j][j];
               M[i]= MinTab(M[i], kaliC(M[j], c));
            }

        }*/
        for (int i = M1.length -2; i>=0;i--){
            for (int j = i; j>=1;j--){
                M1[j]=PlusTab(M1[j], kaliC(M1[i+1], -1*M1[j][i+1]));
            }
        }
        return M1;
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
        for (i=1; i< T.length;i++){
            A[i]= T[i] * c;
        }
        return A;
    }
    static double[] PlusTab(double[] T1,double[] T2){
        int i;
        double[] A= new double [T1.length] ;
        for (i=1; i< T1.length;i++){
            A[i] = T1[i] + T2[i];
        }
        return A;
    }
    static double det(double[][] M){
        double d =1;//inisialisasi
        int  i,j, idx;
        double c;
        //double[][] M1= new double[M.length][M.length+1];
        //M1 = M;
        for(j=1; j<M/*[0]*/.length - 1;j++){
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
                    d *= c; //dikali c detnya krena barsnya dibagi c
                    M[i] = PlusTab(M[i],kaliC(M[idx],-1));
                }
            }
            //pindahin ke paling atas
            if (j!=idx){
                d *= -1;//tukar dikali -1 detnya
                double[] temp = M[j];
                M[j] = M[idx];
                M[idx] = temp ;
            }
        }
        for (i=1; i<M.length;i++){
             d *= M[i][i];
        }
        return d;
    }
    public static void main(String[] args){
    double[][] M = {{0,0,0,0,0},{0,1,2,3,4},{0,2,2,1,3},/*{0,1,5,4,2}/*{1,2,4},{1,3,5}*/};
    //double[][] M = echelon(N);
    System.out.println(det(M));
    M = echelon(M);
        for (int i=1; i<M.length;i++){
            for(int j =1; j<M[0].length;j++){
                System.out.print(M[i][j]+" ");
            }
            System.out.println();
        }
        M = REform(M);
        for (int i=1; i<M.length;i++){
            for(int j =1; j<M[0].length;j++){
                System.out.print(M[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println(det(M));//sds bug gt jsdi M yg digunsinnys berubah ga ngerti gt ngatasinnya gimnaa
    }

  /*  Matrix M = this.copyMatrix();
    int  i,j, idx;
    //double c;
    double det=1;

    for(j=1; j<=M.getMaxRow() -1 ;j++){
        i = j;
        while((M.getElement(i,j) == 0) && (i<=M.getMaxRow())){
            i++;
        }//cari ampe yang ga 0 dibarisannya
        idx = i;
        i = i+1;
        for(;i<=M.getMaxRow();i++){
            //eliminasi yang lainnya dengan baris idx
            //c = M.getElement(i,j)/M.getElement(idx,j);
            if (M.getElement(i, j)!=0){
                M.addOBE(i, idx, -M.getElement(i, j)/M.getElement(idx, j));/*setRow(i,(RowOperation.kaliC(M.getRow(i), 1/c)));
                *///det *= M.getElement(idx, j)/M.getElement(i, j);
                //M.setRow(i,(RowOperation.PlusTab(M.getRow(i),RowOperation.kaliC(M.getRow(idx),-1))));*/
            }
        // }
        //pindahin ke paling atas
   /*     if(j!=idx){
            det *=-1;
            swapOBE(j,idx);
        }
    }
    for (i=1; i<=M.getMaxRow();i++){
        det *= M.getElement(i,i);
    }
    return det;
}*/
