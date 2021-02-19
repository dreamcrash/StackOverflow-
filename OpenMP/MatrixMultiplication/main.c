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

unsigned int rand_interval(unsigned int min, unsigned int max)
{
    // https://stackoverflow.com/questions/2509679/
    int r;
    const unsigned int range = 1 + max - min;
    const unsigned int buckets = RAND_MAX / range;
    const unsigned int limit = buckets * range;

    do
    {
        r = rand();
    } 
    while (r >= limit);

    return min + (r / buckets);
}

void fillupRandomly (int *m, unsigned int size, unsigned int min, unsigned int max){
    for (int i = 0; i < size; i++)
        for (int j = 0; j < size; j++)
            m[size*i + j] = rand_interval(min, max);
} 

void matMultiply (int *a, int *b, int *c, int size) {
    #pragma omp parallel for collapse(2)
    for(int i = 0; i < size; i++)
     for(int j = 0; j < size; j++) 
       for(int k = 0; k < size; k++)
           c[size*i+j] += a[size*i+k] *  b[size*k+j];        
  }

void printMatrix(int *c, int size){
    printf("----- Matrix ------\n"); 
    for(int i = 0; i < size; i++){
       for(int j = 0; j < size; j++){
          printf("%d ",c[size*i+j]);
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
	int *a = malloc(N * N * sizeof(int));
	int *b = malloc(N * N * sizeof(int));
	int *c = malloc(N * N * sizeof(int));

        omp_set_dynamic(0);              /** Explicitly disable dynamic teams **/
        omp_set_num_threads(numThreads); /** Use N threads for all parallel regions **/

         // Dealing with fail memory allocation
    	if(!a || !b || !c)
    	{
          if(a)   free(a);
          if(b)   free(b);
          if(c)   free(c);
          return (EXIT_FAILURE);
    	}
	fillupRandomly (a, N, 0, 100);
        fillupRandomly (b, N, 0, 100);

	double begin = omp_get_wtime();
	matMultiply(a, b, c, N);
 	double end = omp_get_wtime();
   	printf("Time: %f\n",end-begin);

	if(print)
        {
	  printMatrix(a, N);
	  printMatrix(b, N);
	  printMatrix(c, N);
	}

	free(a);
	free(b);
	free(c);
	return (EXIT_SUCCESS);
}


