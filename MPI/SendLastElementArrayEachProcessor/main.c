#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>

/**

Original Question : https://stackoverflow.com/questions/65672424/how-to-send-the-last-element-array-of-each-processor-in-mpi

I am struggled to write the code to perform like the following example similar to the 
Up Phase part in prefix scan and not want to use the function MPI_Scan:

WholeArray[16] = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]

Processor 0 got [0 , 1 , 2 , 3] , Processor 1 got [4 , 5 , 6 , 7] 

Processor 2 got [8 , 9 , 10 , 11] , Processor 3 got [12 , 13 , 14 , 15] 
To send and sum the last array with 2 strides:

(stride 1)

Processor 0 send Array[3] , Processor 1 receive from Processor 0 and add to Array[3]

Processor 2 send Array[3], Processor 3 receive from Processor 2 and add to Array[3] 

(stride 2)

Processor 1 sends Array[3], Processor 3 receive from Processor 1 and add to Array[3]
At last I would want to use MPI_Gather to let the result be:

WholeArray = [0 , 1 , 2 , 3 , 4 , 5 , 6 ,10 , 8 , 9 , 10 , 11 , 12 , 13 ,14 , 36]
I find it hard to write code to let the program do like the following 4nodes example:

(1st stride) - Processor 0 send to Processor 1 and Processor 1 receive from Processor 0
(1st stride) - Processor 2 send to Processor 3 and Processor 3 receive from Processor 2

(2nd stride) - Processor 1 send to Processor 3 and Processor 3 receive from Processor 1
Here is the code that I have written so far:

**/

int main(int argc, char **argv)
{
	int total_size = 16;
        int rank, mpisize;
        MPI_Init(&argc, &argv);
        MPI_Comm_rank(MPI_COMM_WORLD, &rank);
        MPI_Comm_size(MPI_COMM_WORLD, &mpisize);
	int *data = NULL;	

	if(rank == 0){
	   data = malloc(total_size * sizeof(int));
	   for(int i = 0; i < total_size; i++)
		data[i] = i;
	}
	int size_per_process = total_size / mpisize;
	int *localdata = malloc(size_per_process * sizeof(int));
	MPI_Scatter(data, size_per_process, MPI_INT, localdata, size_per_process, MPI_INT, 0, MPI_COMM_WORLD);

	if(rank == 0)
 	   for(int i = 0; i < total_size; i++) 
	      data[i] = 0;

	int key = 1;
	int temp = 0;
   	while(key <= mpisize/2){                      
 	  if((rank+1) % key == 0){
 	      if(rank/key % 2 == 0){	
		 temp = localdata[size_per_process-1];
            	 MPI_Send(&temp, 1, MPI_INT, rank+key, 0, MPI_COMM_WORLD);
	      }
              else {
            	MPI_Recv(&temp, 1, MPI_INT, rank-key, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
           	localdata[size_per_process-1]+= temp;
              }
      	  }
	  key = 2 * key;
	  MPI_Barrier(MPI_COMM_WORLD);
	}

	MPI_Gather(localdata, size_per_process, MPI_INT, data, size_per_process, MPI_INT, 0, MPI_COMM_WORLD);

	if(rank == 0){
	   for(int i = 0; i < total_size; i++)
               printf("%d ", data[i]);
	   printf("\n");
	}
	free(data);
	free(localdata);	

        MPI_Finalize();

        return 0;
}
