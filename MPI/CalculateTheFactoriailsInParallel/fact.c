/***
	ORIGINAL QUESTION: 
https://stackoverflow.com/questions/65585379/how-to-send-an-integer-array-using-mpi-and-calculate-factorial-of-them/

Consider that process 0 have an array of integer numbers where we should compute the factorial 
of all the elements in the array. Modify the program in order to send the array to process 1 
that computes all the factorials and return the results to process 0.
**/



#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <mpi.h>
int main (int argc, char ** argv){
    int rank, size,tag=100;
    MPI_Init (&argc, &argv);  /* starts MPI */
    MPI_Comm_rank (MPI_COMM_WORLD, &rank);    /* get current process id */
  
    if (rank == 0)
    {
        int a[10]={1,2,3,4,5,6,7,8,9,10}; // the array with the values to calculate the factorial
        int fact[10] = {0}; // the array to store the results
        MPI_Send(a, 10, MPI_INT,1,tag, MPI_COMM_WORLD); // the values 
        MPI_Recv(fact, 10, MPI_INT,1,tag, MPI_COMM_WORLD,MPI_STATUSES_IGNORE); // wait for the result 
        for(int i = 0; i < 10; i++) // print the results;
           printf("Process %d,Result=%d\n",rank, fact[i]);
    } 
    else if (rank == 1) 
    { 
       int a[10] = {0};
       int fact[10] = {0};
       MPI_Recv(a, 10, MPI_INT,0,tag, MPI_COMM_WORLD,MPI_STATUSES_IGNORE);
       for(int i = 0; i < 10; i++){
           int f = 1;
           for (int k = 1; k <= a[i]; ++k) // Calculate the factorials 
                f *= k; 
           fact[i] = f;
       }
       MPI_Send(fact,10, MPI_INT,0,tag, MPI_COMM_WORLD); // send the factorials to process 0
    }
    MPI_Comm_size (MPI_COMM_WORLD, &size);    /* get number of processes */
    MPI_Finalize();
    return 0;
}
