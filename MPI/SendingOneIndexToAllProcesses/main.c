/** Original Question : https://stackoverflow.com/questions/65763043/wrong-output-with-processes-sending-one-array-index-to-all-other-processes-using

I am just trying to get my head around MPI and can't seem to understand, 
why the following programs output is different from what I expect.

int rank, size;
  
MPI_Comm_rank(MPI_COMM_WORLD, &rank);
MPI_Comm_size(MPI_COMM_WORLD, &size);

int *sendbuf, *recvbuf;
sendbuf = (int *) malloc(sizeof(int) * size);
recvbuf = (int *) malloc(sizeof(int) * size);

for(int i = 0; i < size; i++) {
  sendbuf[i] = rank;
}
for(int i = 0; i < size; i++) {
  printf("sendbuf[%d] = %d, rank: %d\n", i, sendbuf[i], rank);
}

MPI_Scatter(sendbuf, 1, MPI_INT, 
  recvbuf, 1, MPI_INT, rank, MPI_COMM_WORLD);

for(int i = 0; i < size; i++) {
  printf("recvbuf[%d] = %d, rank: %d\n", i, recvbuf[i], rank);
}
As far as I understood, MPI_Scatter sends sendcount values from an array to all processses. 
In my example I gave each process an array filled with the own rank number. 
Then each process sends one of the indexes in its array to all other processes. 
With two processes the first procss has an sendbuf array of:

sendbuf[0] = 0
sendbuf[1] = 0
And the second process (rank 1) has an array of size MPI_Comm_size filled with 1. The expected output should be:

recvbuf[0] = 0, rank: 0
recvbuf[1] = 1, rank: 0
recvbuf[0] = 0, rank: 1
revcbuf[1] = 1, rank: 1
But instead I get the following output (for two processes):

sendbuf[0] = 0, rank: 0
sendbuf[1] = 0, rank: 0
sendbuf[0] = 1, rank: 1
sendbuf[1] = 1, rank: 1
recvbuf[0] = 0, rank: 0
recvbuf[1] = 32690, rank: 0
recvbuf[0] = 1, rank: 1
recvbuf[1] = 32530, rank: 1
Any help pointing out my mistake is well appreciated.


**/

#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>


int main(int argc, char **argv){
        int rank, size;
        MPI_Init(&argc, &argv);
        MPI_Comm_rank(MPI_COMM_WORLD, &rank);
        MPI_Comm_size(MPI_COMM_WORLD, &size);

        int *sendbuf = malloc(sizeof(int) * size);
        int *recvbuf = malloc(sizeof(int) * size);

        for(int i = 0; i < size; i++)
            sendbuf[i] = rank;      
        MPI_Allgather(sendbuf, 1, MPI_INT, recvbuf, 1, MPI_INT, MPI_COMM_WORLD);

        for(int i = 0; i < size; i++)
           printf("recvbuf[%d] = %d, rank: %d\n", i, recvbuf[i], rank);
        MPI_Finalize();
        return 0;
}
