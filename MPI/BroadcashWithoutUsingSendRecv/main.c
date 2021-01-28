/** Original Question : https://stackoverflow.com/questions/65798629/how-to-broadcast-without-using-mpi-send-mpi-recv-in-mpi/

How to broadcast without using MPI_Send/ MPI_Recv in MPI?
Just a general question:

I wanted to ask if there is anyway to broadcast elements to only certain ranks in MPI without using the MPI_Send and MPI_Recv routines.

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
    int bcast_value = world_rank;  
    MPI_Bcast(&bcast_value, 1, MPI_INT, 0, MPI_COMM_WORLD);
    printf("MPI_Bcast 1 : MPI_COMM_WORLD ProcessID = %d, bcast_value = %d \n", world_rank, bcast_value);

    MPI_Comm new_comm;
    int color = (world_rank % 2  == 0) ? 1 : MPI_UNDEFINED;
    MPI_Comm_split(MPI_COMM_WORLD, color, world_rank, &new_comm); 
   
    if(world_rank % 2  == 0){
	int new_comm_rank, new_comm_size;
   	MPI_Comm_rank(new_comm, &new_comm_rank);
    	MPI_Comm_size(new_comm, &new_comm_size);
    	bcast_value = 1000;
       	MPI_Bcast(&bcast_value, 1, MPI_INT, 0, new_comm);
	
	printf("MPI_Bcast 2 : MPI_COMM_WORLD ProcessID = %d, new_comm = %d, bcast_value = %d \n", world_rank, new_comm_rank,  bcast_value);
        MPI_Comm_free(&new_comm);
   }
   MPI_Finalize(); 
   return 0;
 }
