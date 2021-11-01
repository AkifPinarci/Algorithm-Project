import java.util.Scanner;
import java.util.Random;
class MatrixProduct extends Thread {
    private int[][] A;
    private int[][] B;
    private int[][] C;
    private int rig,col;
    private int dim;

    public MatrixProduct(int[][] A,int[][] B,int[][] C,int rig, int col,int dim_com)  {
        this.A=A;    
        this.B=B;
        this.C=C;
        this.rig=rig;    
        this.col=col; 
        this.dim=dim_com;     
    }

    public void run()
    {
        for(int i=0;i<dim;i++){
            C[rig][col]+=A[rig][i]*B[i][col];        
        }            
    }   
    public static class MatrixMultiplication {
        public static void main(String[] args){      
           Scanner In = new    Scanner(System.in); 
 
           System.out.print("Row of Matrix A: ");     
           int rA=In.nextInt();
           System.out.print("Column of Matrix A: ");
           int cA=In.nextInt();
           System.out.print("Row of Matrix B: ");     
           int rB=In.nextInt();
           System.out.print("Column of Matrix B: ");
           int cB=In.nextInt();
           System.out.println();
            int[][] A=new int[rA][cA];
            int[][] B=new int[rB][cB];
            int[][] C=new int[rA][cB];
            MatrixProduct[][] thrd= new MatrixProduct[rA][cB];
    
            Random r=new Random();
            int n = rA;
            for(int i=0;i<n;i++)
            {
                for(int j=0;j<n;j++)
                {
                    A[i][j]=r.nextInt(20);
                    B[i][j]=r.nextInt(20);
                }
            }
            long startTime = System.currentTimeMillis();
            for(int i=0;i<rA;i++)
            {
            for(int j=0;j<cB;j++){
                    thrd[i][j]=new MatrixProduct(A,B,C,i,j,cA);
                    thrd[i][j].start();
                }
            }
    
            for(int i=0;i<rA;i++)
            {
                for(int j=0;j<cB;j++)
                {
                    try{
                        thrd[i][j].join();
                    }
                catch(InterruptedException e){}
                }
            } 
            long endTime = System.currentTimeMillis();
            System.out.println(endTime-startTime);              
        }      
    }       
}