/** Original Question : https://stackoverflow.com/questions/66916100/

What I want to achieve is to broadcast partial result to other threads and 
receive other threads' result at a different line of code, 
it can be expressed as the following pseudo code:

if have any incoming message:
    read the message and compare it with the local optimal
    if is optimal:
        update the local optimal

calculate local result
if local result is better than local optimal:
    update local optimal
    send the local optimal to others
The question is, MPI_Bcast/MPI_Ibcast do the send and receive in the same place, 
what I want is separate send and receive. I wonder if MPI has builtin support for my purpose, 
or if I can only achieve this by calling MPI_Send/MPI_Isend in a for loop?

**/

#include <stdio.h>
#include <mpi.h>

int main(int argc,char *argv[]){
    MPI_Init(NULL,NULL); // Initialize the MPI environment
    int world_rank; 
    int world_size;
    MPI_Comm_rank(MPI_COMM_WORLD,&world_rank);
    MPI_Comm_size(MPI_COMM_WORLD,&world_size);
    int my_local_optimal = world_rank;
    MPI_Allreduce(&my_local_optimal, &my_local_optimal, 1, MPI_INT, MPI_MAX, MPI_COMM_WORLD);
    printf("Step 1 : Process %d -> max local %d \n", world_rank, my_local_optimal);

    my_local_optimal += world_rank * world_size;

    MPI_Allreduce(&my_local_optimal, &my_local_optimal, 1, MPI_INT, MPI_MAX, MPI_COMM_WORLD);
    printf("Step 2 : Process %d -> max local %d \n", world_rank, my_local_optimal);


    MPI_Finalize();
    return 0;
 }
