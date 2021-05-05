/** Original Question : https://stackoverflow.com/questions/67378446

Dynamich load balancing master-worker

**/
#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>

int main(int argc,char *argv[]){
    MPI_Init(NULL,NULL); // Initialize the MPI environment
    int rank; 
    int size;
    MPI_Status status;
    MPI_Comm_rank(MPI_COMM_WORLD,&rank);
    MPI_Comm_size(MPI_COMM_WORLD,&size);

    int work_is_done = -1;
    if(rank == 0){
     int max_index = 10; 
     int index_simulator = 0;
     // Send statically the first iterations
     for(int i = 1; i < size; i++){
     	MPI_Send(&index_simulator, 1, MPI_INT, i, i, MPI_COMM_WORLD); 
        index_simulator++;
     }  
     int processes_finishing_work = 0;
      
     do
     {
       int process_that_wants_work = 0;
       MPI_Recv(&process_that_wants_work, 1, MPI_INT, MPI_ANY_SOURCE, 1, MPI_COMM_WORLD, &status);
       if(index_simulator < max_index){
          MPI_Send(&index_simulator, 1, MPI_INT, process_that_wants_work, 1, MPI_COMM_WORLD);  
          index_simulator++;
       }
       else{
           MPI_Send(&work_is_done, 1, MPI_INT, process_that_wants_work, 1, MPI_COMM_WORLD);
           processes_finishing_work++;
       }
     } while(processes_finishing_work < size - 1);
      printf("Process {%d} -> I AM OUT\n", rank);
    }
    else{
      int index_to_work = 0;
      MPI_Recv(&index_to_work, 1, MPI_INT, 0, rank, MPI_COMM_WORLD, &status);    
      printf("Process {%d} -> {%d}\n", rank, index_to_work);
    
     do{
	MPI_Send(&rank, 1, MPI_INT, 0, 1, MPI_COMM_WORLD);
        MPI_Recv(&index_to_work, 1, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);
        printf("Process {%d} -> {%d}\n", rank, index_to_work);
      
      }while(index_to_work != work_is_done);
      printf("Process {%d} -> I AM OUT\n", rank);
    }

    MPI_Finalize();
    return 0;
 }
