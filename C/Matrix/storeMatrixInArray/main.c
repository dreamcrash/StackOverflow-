/** Original Question : https://stackoverflow.com/questions/65198500/

How do I store a matrix in an array in C?

So I would like to store a couple of matrices in an array. 
I understand you could make a three dimensional array. 
What I want to do is to be able to store the matrices 
I get from AndMatrix method in an array and then use them when I need to. 
My code is below. arrayOfMatrices variable is a 3 dimensional array 
I have initialized already. 
Can someone explain also how I would access these matrices in the array. My code is below:

**/




#include <stdio.h>
#include <stdlib.h>

int** AndMatrix(int row, int column){
     int** result = calloc(row, sizeof(int*));
     for (int i = 0; i < row; i++) 
            result[i] = calloc(column, sizeof(int)); 
     return result;
}


int main() {
    
    int ***arrayOfMatrices = malloc(sizeof(int**) * 100);
    int row_matrix1 = 10;
    int col_matrix1 = 10;
    arrayOfMatrices[0] = AndMatrix(row_matrix1, col_matrix1);
    int **first_matrix = arrayOfMatrices[0];
    
    // Fill up the matrix with some values
    for(int i = 0; i < row_matrix1; i++)
     for(int j = 0; j < col_matrix1; j++)
         first_matrix[i][j] = i * j;
 
    for(int i = 0; i < row_matrix1; i++){
     for(int j = 0; j < col_matrix1; j++)
         printf("%d ", first_matrix[i][j]);
     printf("\n");
    }
    
    // free the memory accordingly.
    
    return 0;
} 
