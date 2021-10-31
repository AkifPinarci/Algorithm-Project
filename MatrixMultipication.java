// Mehmet Akif Pinarci

class MatrixMultipication{

    public static void main(String[] args){
        // Input matrices given by the professor.
        int mat1[][] = { { 2, 0, -1, 6 },
                         { 3, 7, 8, 0 },
                         { -5, 1, 6, -2 },
                         { 8, 0, 2, 7 } };
 
        int mat2[][] = { { 0, 1, 6, 3 },
                         { -2, 8, 7, 1 },
                         { 2, 0, -1, 0 },
                         { 9, 1, 6, -2 } };

        // Printing statements that is used for showing the resultant matrices.
                         
        //System.out.println("classicalMatrixMultipication: ");
        //print2dArray(classicalMatrixMultipication(mat1, mat2));

        //System.out.println("naiveDAQ: ");
        //print2dArray(naiveDAQ(mat1, mat2));

        //System.out.println("strassenMM: ");
        //print2dArray(strassenMM(mat1, mat2)); 


        // Calculation which is used for calculating the average run time of the algorithms.
        long totalTime = 0;
        for(int i = 0; i < 10; i++){
            long startTime = System.currentTimeMillis();
            for(int k = 0; k < 1000001; k++){
                naiveDAQ(mat1, mat2);
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
            totalTime += (endTime - startTime);
        }
        System.out.println((float)totalTime/10);
    }



    public static int[][] classicalMatrixMultipication(int mat1[][], int mat2[][]){
        int[][] result= new int[mat1[0].length][mat1[0].length];
        int i, j, k;
        int n = mat1[0].length;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                result[i][j] = 0;
                for (k = 0; k < n; k++)
                    result[i][j] += mat1[i][k]
                                 * mat2[k][j];
            }
        }
        return result;
    }

    public static int[][] naiveDAQ(int mat1[][], int mat2[][]){
        // Base Case
        int n = mat1[0].length;
        int[][] result= new int[mat1[0].length][mat1[0].length];
		if (n == 1) {
			result[0][0] = mat1[0][0] * mat2[0][0];
			return result;
		} 
        else {
            //Step 1: Instantiate all divided matrix to be able to use in coming steps
			int[][] A11 = new int[n / 2][n / 2];
			int[][] A12 = new int[n / 2][n / 2];
			int[][] A21 = new int[n / 2][n / 2];
			int[][] A22 = new int[n / 2][n / 2];
			int[][] B11 = new int[n / 2][n / 2];
			int[][] B12 = new int[n / 2][n / 2];
			int[][] B21 = new int[n / 2][n / 2];
			int[][] B22 = new int[n / 2][n / 2];

            //Step 2: Filling all the partitioned arrays with accurate data
			matrixPartition(mat1, A11, 0, 0);
			matrixPartition(mat1, A12, 0, n / 2);
			matrixPartition(mat1, A21, n / 2, 0);
			matrixPartition(mat1, A22, n / 2, n / 2);
			matrixPartition(mat2, B11, 0, 0);
			matrixPartition(mat2, B12, 0, n / 2);
			matrixPartition(mat2, B21, n / 2, 0);
			matrixPartition(mat2, B22, n / 2, n / 2);
            
            // Step 3: Using Formulas as described in algorithm
			int[][] C11 = matrixAddition(naiveDAQ(A11, B11), naiveDAQ(A12, B21));
			int[][] C12 = matrixAddition(naiveDAQ(A11, B12), naiveDAQ(A12, B22));
			int[][] C21 = matrixAddition(naiveDAQ(A21, B11), naiveDAQ(A22, B21));
			int[][] C22 = matrixAddition(naiveDAQ(A21, B12), naiveDAQ(A22, B22));

            //Step 4: Merge all the sub matrixes into the resultant matrix
			mergeSubMatrix(C11, result, 0, 0);
			mergeSubMatrix(C12, result, 0, n / 2);
			mergeSubMatrix(C21, result, n / 2, 0);
			mergeSubMatrix(C22, result, n / 2, n / 2);
		}

		return result;
	}

    public static int[][] strassenMM(int mat1[][], int mat2[][]){
        int[][] result= new int[mat1[0].length][mat1[0].length];
        int n = mat1[0].length;
        // Base Case
        if (n == 1) {
			result[0][0] = mat1[0][0] * mat2[0][0];
			return result;
		}
        else{
            //Step 1: Instantiate all divided matrix to be able to use in coming steps
            int[][] A11 = new int[n / 2][n / 2];
			int[][] A12 = new int[n / 2][n / 2];
			int[][] A21 = new int[n / 2][n / 2];
			int[][] A22 = new int[n / 2][n / 2];
			int[][] B11 = new int[n / 2][n / 2];
			int[][] B12 = new int[n / 2][n / 2];
			int[][] B21 = new int[n / 2][n / 2];
			int[][] B22 = new int[n / 2][n / 2];

            //Step 2: Filling all the partitioned arrays with accurate data
            matrixPartition(mat1, A11, 0, 0);
			matrixPartition(mat1, A12, 0, n / 2);
			matrixPartition(mat1, A21, n / 2, 0);
			matrixPartition(mat1, A22, n / 2, n / 2);
			matrixPartition(mat2, B11, 0, 0);
			matrixPartition(mat2, B12, 0, n / 2);
			matrixPartition(mat2, B21, n / 2, 0);
			matrixPartition(mat2, B22, n / 2, n / 2);

            // Step 3: Using Formulas as described in algorithm
            // M1:=(A1+A3)×(B1+B2)
            int[][] M1 = strassenMM(matrixAddition(A11, A22), matrixAddition(B11, B22));
           
            // M2:=(A2+A4)×(B3+B4)
            int[][] M2 = strassenMM(matrixAddition(A21, A22), B11);
           
            // M3:=(A1−A4)×(B1+A4)
            int[][] M3 = strassenMM(A11, matrixSubtraction(B12, B22));
           
            // M4:=A1×(B2−B4)
            int[][] M4 = strassenMM(A22, matrixSubtraction(B21, B11));
           
            // M5:=(A3+A4)×(B1)
            int[][] M5 = strassenMM(matrixAddition(A11, A12), B22);
           
            // M6:=(A1+A2)×(B4)
            int[][] M6 = strassenMM(matrixSubtraction(A21, A11), matrixAddition(B11, B12));
           
            // M7:=A4×(B3−B1)
            int[][] M7 = strassenMM(matrixSubtraction(A12, A22), matrixAddition(B21, B22));
 
            // P:=M2+M3−M6−M7
            int[][] C11 = matrixAddition(matrixSubtraction(matrixAddition(M1, M4), M5), M7);
           
            // Q:=M4+M6
            int[][] C12 = matrixAddition(M3, M5);
           
            // R:=M5+M7
            int[][] C21 = matrixAddition(M2, M4);
           
            // S:=M1−M3−M4−M5
            int[][] C22 = matrixAddition(matrixSubtraction(matrixAddition(M1, M3), M2), M6);

            //Step 4: Merge all the sub matrixes into the resultant matrix
            mergeSubMatrix(C11, result, 0, 0);
			mergeSubMatrix(C12, result, 0, n / 2);
			mergeSubMatrix(C21, result, n / 2, 0);
			mergeSubMatrix(C22, result, n / 2, n / 2);

        }

        return result;
    }


    //Created all the helper methods that included in the algorithms.

    //Printing resultant arrays to see if they are correctly computed.
    public static void print2dArray(int input[][]){
        int n = input[0].length;
        for(int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                System.out.print(input[i][j]+" ");
            }
            System.out.println("");
        }
        System.out.println();
    }

    // Divided bigger sized matrices into the smaller sized matrices, used in DAC and Strassen's MM algorithms.
    public static void matrixPartition(int[][] mainMatrix, int[][] result, int a, int b){
        int y = b;
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result.length; j++) {
				result[i][j] = mainMatrix[a][y++];
			}
			y = b;
			a++;
        }
    }

    // Summed together to same sized matrices, used in DAC and Strassen's MM algorithms.
    public static int[][] matrixAddition(int[][] mat1, int[][] mat2){
        int n = mat1[0].length;
        int[][] result = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				result[i][j] = mat1[i][j] + mat2[i][j];
			}
		}
		return result;
    }

    // Summed together to same sized matrices, used in Strassen's MM algorithm.
    public static int[][] matrixSubtraction(int[][] mat1, int[][] mat2){
        int n = mat1[0].length;
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				result[i][j] = mat1[i][j] - mat2[i][j];
			}
		}
        return result;
    }

    // Merged to smaller size matrices into the bigger matrices, and at the and created the resultant matrix, used in the both DAC and Strassen's algorithms.
    public static void mergeSubMatrix(int[][] subMatrix, int[][] result, int a, int b) {
		int y = b;
		for (int i = 0; i < subMatrix.length; i++) {
			for (int j = 0; j < subMatrix.length; j++) {
				result[a][y++] = subMatrix[i][j];
			}
			y = b;
			a++;
		}
	}
}