/** Original Question : https://stackoverflow.com/questions/67025106/

**/

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "mpi.h"

int main( int argc, char *argv[] )
{
    MPI_Init(&argc, &argv);
    int myrank, size;
    MPI_Comm_rank(MPI_COMM_WORLD, &myrank) ;
    MPI_Comm_size(MPI_COMM_WORLD, &size);
    double mat[4]={1, 2, 3, 4};
    int r=4;
    double snd_buf[r];
    double recv_buf[10];
  
    MPI_Status status;
    MPI_Datatype type;
    MPI_Type_contiguous( r, MPI_DOUBLE, &type );
    MPI_Type_commit(&type);
             
    if(myrank==0)
       MPI_Send (&mat[0], 1 , type, 1, 100, MPI_COMM_WORLD);
    else if(myrank==1)
    {
       MPI_Recv(&recv_buf[6], 1, type, 0, 100, MPI_COMM_WORLD, &status);
       for(int i=6;i<10;i++)
          printf("%lf ",recv_buf[i]);
      printf("\n");
   }

   MPI_Type_free(&type);        
   MPI_Finalize();
   return 0;
}
