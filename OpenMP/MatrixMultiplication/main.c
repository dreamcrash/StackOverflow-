/**
Original Question : https://stackoverflow.com/questions/13575689

OpenMP matrix multiplication nested loops

This is a matrix multiplication code with one i loop parallelized and another with j loop parallelized. 
With both the versions the value of C array is correct (I have tested with small matrix sizes). 
There is also no performance improvement from one over other.

Can anyone please tell me what is the difference in these 2 versions? 
Will the array C be accurate in both the versions regardless of the size of the matrix? Thanks in advance

**/

#include <stdlib.h>
#include <stdio.h>
#include <omp.h>

void fillupRandomly (int *m, int dimension){
    for (int i = 0; i < dimension; i++)
        for (int j = 0; j < dimension; j++)
            m[dimension*i+j] = (((float)rand())/RAND_MAX*99+1);
} 

void matMultiply (int *A, int *B, int *C, int dimension) {
    #pragma omp parallel for collapse(2)
    for(int i = 0; i < dimension; i++)
     for(int j = 0; j < dimension; j++) 
       for(int k = 0; k < dimension; k++)
           C[dimension*i+j] += A[dimension*i+k] *  B[dimension*k+j];        
  }

void printMatrix(int *C, int N){
    printf("----- Matrix ------\n"); 
    for(int i = 0; i < N; i++){
       for(int j = 0; j < N; j++){
          printf("%d ",C[N*i+j]);
       }
       printf("\n");
    }
    printf("------------------\n");
}

int main(int argc, char *argv[]) {
        srand(123456);
   	int N  = (argc > 1) ? atoi(argv[1]) : 10;
	int print = (argc > 2) ? atoi(argv[2]) : 0;
        int numThreads = (argc > 3) ? atoi(argv[3]) : 2;
	int *A = malloc(N * N * sizeof(int));
	int *B = malloc(N * N * sizeof(int));
	int *C = malloc(N * N * sizeof(int));

        omp_set_dynamic(0);              /** Explicitly disable dynamic teams **/
        omp_set_num_threads(numThreads); /** Use N threads for all parallel regions **/

         // Dealing with fail memory allocation
    	if(!A || !B || !C)
    	{
        	if(A)   free(A);
        	if(B)   free(B);
        	if(C)   free(C);
        	return (EXIT_FAILURE);
    	}
	fillupRandomly (A, N);
        fillupRandomly (B, N);

	double begin = omp_get_wtime();
	matMultiply(A, B, C, N);
 	double end = omp_get_wtime();
   	printf("Time: %f\n",end-begin);

	if(print){
	  printMatrix(C, N);
	}

	free(A);
	free(B);
	free(C);
	return (EXIT_SUCCESS);
}


