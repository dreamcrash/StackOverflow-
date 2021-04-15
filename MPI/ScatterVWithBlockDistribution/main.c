/** Original Question : https://stackoverflow.com/questions/67096347

I have an array of numbers that I need to scatter to each node in an MPI program. 
The setup is that I have an array of numbers from 1 to 100 with all the even numbers 
except the number 2 removed. Due to the way I removed the even numbers, 
the number 2 is the last element in the array.

So my array contains 51 odd numbers, 3, 5, 7, ... 99, 2. My problem is that the 
final partition after a scatter does not contain the last three numbers in the array - 97, 99 and 2.


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

    int size = 50;
    int *numbers= NULL;
    if(world_rank == 0){
      numbers = malloc(sizeof(int) * size);
      for(int i = 0; i < size; i++)
         numbers[i] = (2*i + 1); 
    }
    int sendcounts [world_size];
    int	displs [world_size]; 
    int res = size % world_size;
    int size_per_process = size / world_size;
    int increment = 0;
    for(int processID = 0; processID < world_size; processID++){
       displs[processID] = increment;
       sendcounts[processID] = (processID + 1 <= res) ? size_per_process + 1 : size_per_process;
       increment += sendcounts[processID];
       if(world_rank == 0)
        printf("%d %d %d\n",processID, sendcounts[processID], displs[processID]); 
    }
    int process_size = sendcounts[world_rank];
    int local_numbers[process_size];
    MPI_Scatterv(numbers, sendcounts, displs, MPI_INT, local_numbers, process_size, MPI_INT, 0, MPI_COMM_WORLD);  
    if(world_rank == world_size - 1){
       for(int i = 0; i < size_per_process; i++)
	  printf("%d ", local_numbers[i]); 
       printf("\n"); 
    }

    MPI_Finalize();
    return 0;
 }
