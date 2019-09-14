// package tubes;
//
// public class SquareMatrix extends Matrix {
//     public SquareMatrix(int size) {
//         super(size, size);
//     }
//
//     public SquareMatrix(float[][] arr) {
//         // if (arr.length != arr[0].length)
//         //     throw new Exception("Max row and max column are not the same!");
//         this.maxR = this.maxC = arr.length;
//         this.content = new float[maxR+1][maxC+1];
//
//     }
//
//     //nopal (NOTE: tambahin lagi tiap method)
//     public float getDeterminan(Method method) {
//         Matrix M = this.copyMatrix();
//         int  i,j, idx;
//         float c;
//         float det=1;
//         //double M1[][];
//         //M1= new double[M.length][M[0].length];
//         //M1=M;
//         for(j=1; j<=M.getMaxRow() -1 ;j++){
//             i = j;
//             while((M.getElement(i,j) == 0) && (i<=M.getMaxRow())){
//                 i++;
//             }//cari ampe yang ga 0 dibarisannya
//             idx = i;
//             i = i+1;
//             for(;i<=M.getMaxRow();i++){
//                 //eliminasi yang lainnya dengan baris idx
//                 c = M.getElement(i,j)/M.getElement(idx,j);
//                 if (c!=0){
//                     M.setRow(i,(RowOperation.kaliC(M.getRow(i), 1/c)));
//                     det *= c;
//                     M.setRow(i,(RowOperation.PlusTab(M.getRow(i),RowOperation.kaliC(M.getRow(idx),-1))));
//                 }
//             }
//             //pindahin ke paling atas
//             if(j!=idx){
//                 det *=-1;
//                 float[] temp = M.getRow(j);
//                 M.setRow(j, M.getRow(idx));
//                 M.setRow(idx,temp) ;
//             }
//         }
//         for (i=1; i<=M.getMaxRow();i++){
//              det *= M.getElement(i,i);
//         }
//
//         return det;
//     }
//
//     public Matrix getInverseMatrix(Method method) {
//         // TODO: implement
//         return new Matrix(this.maxR, this.maxC);
//     }
//
//     public Matrix getCofactorMatrix() {
//         // TODO: implement
//         return new Matrix(this.maxR, this.maxC);
//     }
// }
