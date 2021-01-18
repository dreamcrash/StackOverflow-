/** Original Question : https://stackoverflow.com/questions/65777074/sum-of-an-array-in-mpi


Sum of an array in MPI?


I'm trying to write an MPI program that calculates the sum of an integers array.

For this purpose I used MPI_Scatter to send chunks of the array to the other processes then MPI_Gather to get the sum of each chunk by the root process(process 0).

The problem is one of the processes receives two elements but the other one receives random numbers. I'm running my code with 3 processes.

Here is what I have:

**/
#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>

int main(int argc,char *argv[]){
    MPI_Init(NULL,NULL); // Initialize the MPI environment
    int world_rank; 
    int world_size;
    MPI_Comm_rank(MPI_COMM_WORLD,&world_rank);
    MPI_Comm_size(MPI_COMM_WORLD,&world_size);

    int size = (argc > 1) ? atoi(argv[1]) : 4;
    int size_per_process = (size % world_size == 0) ? size / world_size : 1 + size / world_size;
   
    int *number = NULL;
    if(world_rank == 0){
      number = malloc(sizeof(int) * size);
      for(int i = 0; i < size; i++)
         number[i] = i;
      int size_with_padding = size_per_process * world_size;
      for(int i = size; i < size_with_padding; i++)
         number[i] = 0;
    }
    int local_number[size_per_process];
    MPI_Scatter(number, size_per_process, MPI_INT, local_number, size_per_process, MPI_INT, 0, MPI_COMM_WORLD);  
    int sub_sum = 0;
    for(int i=0 ; i < size_per_process ; i++){
        sub_sum += local_number[i];
    }
    int sum = 0;
    MPI_Reduce(&sub_sum, &sum, 1, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);            
    if(world_rank == 0){
      int sumNaturalNumbers = (size * (size - 1)) / 2;
      assert(sum == sumNaturalNumbers);
      printf("The sum of array is: %d == %d\n", sum, sumNaturalNumbers);
    }

    MPI_Finalize();
    return 0;
 }
