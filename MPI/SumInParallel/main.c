/** Original Question : https://stackoverflow.com/questions/65777074/sum-of-an-array-in-mpi


Sum of an array in MPI?


I'm trying to write an MPI program that calculates the sum of an integers array.

For this purpose I used MPI_Scatter to send chunks of the array to the other processes then MPI_Gather to get the sum of each chunk by the root process(process 0).

The problem is one of the processes receives two elements but the other one receives random numbers. I'm running my code with 3 processes.

Here is what I have:

**/




#include <stdio.h> 
#include <mpi.h>

int main(int argc,char *argv[]){
    MPI_Init(NULL,NULL); // Initialize the MPI environment
    int world_rank; 
    int world_size;
    MPI_Comm_rank(MPI_COMM_WORLD,&world_rank);
    MPI_Comm_size(MPI_COMM_WORLD,&world_size);
	
    int number1[2];           
    int number[4];
    if(world_rank == 0){
      number[0]=1;
      number[1]=3;
      number[2]=5;
      number[3]=9;               
    }

    //All processes
    MPI_Scatter(number, 2, MPI_INT, &number1, 2, MPI_INT, 0, MPI_COMM_WORLD);
    printf("I'm process %d , I received the array : ",world_rank);
	   
    int sub_sum = 0;
    for(int i=0 ; i<2 ; i++){
        printf("%d ",number1[i]);
        sub_sum = sub_sum + number1[i];
    }
    printf("\n");        
    int sum = 0;
    MPI_Reduce(&sub_sum, &sum, 1, MPI_INT, MPI_SUM,0,MPI_COMM_WORLD);            
    if(world_rank == 0)
      printf("\nthe sum of array is: %d\n",sum);
            

    MPI_Finalize();
    return 0;
 }
