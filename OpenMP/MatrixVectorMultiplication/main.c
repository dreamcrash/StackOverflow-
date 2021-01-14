/**

Original Question : https://stackoverflow.com/questions/65630629/multiplication-matrix-vector-using-openmp/

Any idea how to parallel this function using OpenMP?

/*______ prod_matrix_vector __________ */

void dot(double matrix[], double vector[],int n, double result[]){
      
      for(int i=0,k=0;i<n*n,k<n;i=i+n,k++)
            for(int j=i,l=0;j<i+n,l<n;j++,l++)
                result[k]+=matrix[j]*vector[l];
}
**/


#include <assert.h>
#include <stdlib.h>
#include <stdio.h>
void dot(double matrix[],double vector[],int n,double result[]){
      #pragma omp parallel for     
      for(int k=0; k<n; k++){ 
    	 for(int j=k*n, l=0; l<n; j++,l++)             
            result[k] += matrix[j] * vector[l];
      }
}

void test(double result_openmp[], double matrix[], double vector[],int n, double result[]){
      
      for(int i=0, k=0; k<n; i+=n,k++)
            for(int j=i, l=0;l<n; j++,l++)
                result[k] += matrix[j] * vector[l];

      for(int i = 0; i < n; i++)
	 assert(result_openmp[i] == result[i]);

      printf("Success\n");
}

int main(int argc, char *argv[]) {
   	int N = 10000;
	double *matrix = malloc(N * N * sizeof(double));
	double *vector = malloc(N * sizeof(double));
	double *result = malloc(N * sizeof(double));
	double *result_openmp = malloc(N * sizeof(double));

	for(int i = 0; i < N * N; i++)
           matrix[i] = i + i;	
	for(int i = 0; i < N; i++){
	   vector[i] = i;
	   result[i] = result_openmp[i] = 0;
	}
	
        dot(matrix, vector, N, result_openmp);
	test(result_openmp, matrix, vector, N, result);
        
	free(matrix);
	free(vector);
	free(result);
	free(result_openmp);
}


