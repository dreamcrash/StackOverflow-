/** Original Question : https://stackoverflow.com/questions/65324897/

Preventing threads from unnecessary exit and keep the pool alive

Suppose I call a program with OMP_NUM_THREADS=16.

The first function calls #pragma omp parallel for num_threads(16).

The second function calls #pragma omp parallel for num_threads(2).

The third function calls #pragma omp parallel for num_threads(16).

Debugging with gdb shows me that on the second call 14 threads exit. And on the third call, 14 new threads are spawned.

Is it possible to prevent 14 threads from exiting on the second call? Thank you.

The proof listings are below.

$ cat a.cpp
#include <omp.h>

void func(int thr) {
    int count = 0;

    #pragma omp parallel for num_threads(thr)
    for(int i = 0; i < 10000000; ++i) {
        count += i;
    }        
}    

int main() {
    func(16);

    func(2);

    func(16);

    return 0;
} 
**/


#include <omp.h>
#include<stdio.h>
#include <stdlib.h>

int func(int total_threads) {
    int count = 0;
    int thread_id = omp_get_thread_num();
    if (thread_id < total_threads)
    {
       for(int i = thread_id; i < 10000000; i += total_threads) 
           count += i;
    }
    return count;        
}    

int main() {
    int max_threads_to_be_used = 4;
    int* count_array = malloc(max_threads_to_be_used * sizeof(int));
    #pragma omp parallel num_threads(max_threads_to_be_used)
    {
        int count = func(16);
        count += func(2);
        count += func(16);
        count_array[omp_get_thread_num()] = count;
    }
    int count = 0;
    for(int i = 0; i < max_threads_to_be_used; i++) 
        count += count_array[i];
    printf("Count = %d\n", count);
    return 0;
} 
