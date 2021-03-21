/**
Original Question : https://stackoverflow.com/questions/16007640


**/

#include <assert.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <omp.h>

#define TASK_SIZE 100

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

void fillupRandomly (int *m, int size, unsigned int min, unsigned int max){
    for (int i = 0; i < size; i++)
    m[i] = rand_interval(min, max);
} 


void init(int *a, int size){
   for(int i = 0; i < size; i++)
       a[i] = 0;
}

void printArray(int *a, int size){
   for(int i = 0; i < size; i++)
       printf("%d ", a[i]);
   printf("\n");
}

int isSorted(int *a, int size){
   for(int i = 0; i < size - 1; i++)
      if(a[i] > a[i + 1])
        return 0;
   return 1;
}


int partition(int * a, int p, int r)
{
    int lt[r-p];
    int gt[r-p];
    int i;
    int j;
    int key = a[r];
    int lt_n = 0;
    int gt_n = 0;

    for(i = p; i < r; i++){
        if(a[i] < a[r]){
            lt[lt_n++] = a[i];
        }else{
            gt[gt_n++] = a[i];
        }   
    }   

    for(i = 0; i < lt_n; i++){
        a[p + i] = lt[i];
    }   

    a[p + lt_n] = key;

    for(j = 0; j < gt_n; j++){
        a[p + lt_n + j + 1] = gt[j];
    }   

    return p + lt_n;
}

void quicksort(int * a, int p, int r)
{
    int div;

    if(p < r){ 
        div = partition(a, p, r); 
	#pragma omp task shared(a) if(r - p > TASK_SIZE)
        quicksort(a, p, div - 1); 
	#pragma omp task shared(a) if(r - p > TASK_SIZE)
        quicksort(a, div + 1, r); 
    }
}

int main(int argc, char *argv[])
{
        srand(123456);
        int N  = (argc > 1) ? atoi(argv[1]) : 10;
        int print = (argc > 2) ? atoi(argv[2]) : 0;
        int numThreads = (argc > 3) ? atoi(argv[3]) : 2;
        int *X = malloc(N * sizeof(int));
        int *tmp = malloc(N * sizeof(int));

        omp_set_dynamic(0);              /** Explicitly disable dynamic teams **/
        omp_set_num_threads(numThreads); /** Use N threads for all parallel regions **/

         // Dealing with fail memory allocation
        if(!X || !tmp)
        { 
           if(X) free(X);
           if(tmp) free(tmp);
           return (EXIT_FAILURE);
        }

        fillupRandomly (X, N, 0, 5);

        double begin = omp_get_wtime();
        #pragma omp parallel
        {
            #pragma omp single
             quicksort(X, 0, N);
        }   
        double end = omp_get_wtime();
        printf("Time: %f (s) \n",end-begin);
    
        assert(1 == isSorted(X, N));

        if(print){
           printArray(X, N);
        }

        free(X);
        free(tmp);
        return (EXIT_SUCCESS);

    return 0;
}
