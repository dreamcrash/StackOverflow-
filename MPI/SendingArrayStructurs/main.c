/** Original Question : https://stackoverflow.com/questions/66622459
I have a data structure looks like below

struct test{
  double a1;
  double a2;
}
and have a pointer test *info = nullptr; to beginning to the array of structs.

Question is how can I broadcast this array without converting it into two separate vector.

I tried to create mpi_type_struct didn't work.
I tried to pack my struct data didn't work. The cases I've seen so far are valid 
for only one struct but not array of structs.
**/

#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>
void defineStruct(MPI_Datatype *tstype);

typedef struct S{
  double a1;
  double a2;
} S;

void defineStruct(MPI_Datatype *tstype) {
    const int count = 2;
    int          blocklens[count] = {1, 1};
    MPI_Datatype types[count] = {MPI_DOUBLE, MPI_DOUBLE};
    MPI_Aint     disps[count] = {offsetof(S,a1), offsetof(S,a2)};

    MPI_Type_create_struct(count, blocklens, disps, types, tstype);
    MPI_Type_commit(tstype);
}

int main(int argc,char *argv[]){
    MPI_Init(NULL,NULL);
    int world_rank; 
    MPI_Comm_rank(MPI_COMM_WORLD,&world_rank);
    MPI_Datatype structtype;
    int total_size = 5;
    S *info = malloc(sizeof(S) * total_size);
    if(world_rank == 0){
    	for(int i = 0; i < total_size; i++){
       	   info[i].a1 = i * i;
           info[i].a2 = i * (i+1);
    	}
    }    

    defineStruct(&structtype);
    MPI_Bcast(info, total_size, structtype, 0, MPI_COMM_WORLD);  
          
    if(world_rank != 0){
      for(int i = 0; i < total_size; i++){
         printf("%lf %lf\n", info[i].a1, info[i].a2);
      }
    }
    free(info);
    MPI_Finalize();
    return 0;
 }
