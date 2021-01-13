#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>

/** 
Original Question : https://stackoverflow.com/questions/65678445/failed-to-parallelly-add-up-elements-in-the-array-with-several-strides-mpi

I am trying to parallelly add up the elements from the array. 
I got an example for the algorithm that I follow to add up the elements with different strides in the array:

input = [3,10,1,22,8,28,4,53,4,4,0,4,0,0,0,57]

First Stride (Add every N/2^1 to N/2^1 + N/2^(1+1): 
input = [ 3,10,1,22,8,28,4,53,4,4,0,57,0,0,0,57]

Second Stride (Add every N/2^2 to N/2^2 + +N/2^(2+1):
input = [3,10,1,22,8,50,4,53,4,57,0,57,0,57,0,57]

Third Stride (Add every N/2^3 to N/2^3 + N/2^(3+1):
input = [3,10,11,22,30,50,54,53,57,57,57,57,57,57,57,57]
I wrote the code to distribute the adding work equally to my processors. (To be noted that I am trying to avoid using MPI_Scan)

The processor each has a temp value which means the changed array value and MPI_Gather it back to root, then the root will change the whole input array and MPI_cast the input to each processor to do the adding work again before entering the next stride.

However, my result does not seem to work as I want. I would appreciate if anyone can tell me what I did wrong in my codes.

**/
void printArray(int *array, int size){
     int rank;
     MPI_Comm_rank(MPI_COMM_WORLD, &rank);
     if(rank == 0){
	for(int i = 0; i < size; i++)
            printf("%2d ", array[i]);
        printf("\n");
    }
}

int main(int argc, char **argv)
{
        int rank, size;
        MPI_Init(&argc, &argv);
        MPI_Comm_rank(MPI_COMM_WORLD, &rank);
        MPI_Comm_size(MPI_COMM_WORLD, &size);
	int input[16] = {3,10,1,22,8,28,4,53,4,4,0,4,0,0,0,57};	
	int totalarray = sizeof(input)/sizeof(input[0]);

	int size_per_process = totalarray/size;
	int begin = rank * size_per_process;
	int end =  (rank + 1) * size_per_process;
	int split_size = totalarray/2;
	while(split_size > 1){
	   int shift = (split_size/2) - 1;
	   int dest = ((begin == 0) ? split_size : (split_size/begin) * split_size) + shift; 
	   for(; dest < end; dest += split_size)
              input[dest] += input[dest - shift -1];
	   MPI_Allgather(MPI_IN_PLACE, size_per_process, MPI_INT, input, size_per_process, MPI_INT, MPI_COMM_WORLD);
	   split_size = split_size/2;
	}
        
	printArray(input, totalarray);
	MPI_Finalize();

        return 0;
}
