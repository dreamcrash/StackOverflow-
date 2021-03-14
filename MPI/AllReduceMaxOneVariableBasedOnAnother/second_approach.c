/** Original Question : https://stackoverflow.com/questions/65874060/an-efficient-way-to-perform-an-all-reduction-in-mpi-based-on-the-value-of-anothe/


As an example, lets say I have

int a = ...;
int b = ...;
int c;
where a is the result of some complex local calculation and b is some metric for the quality of a.

I'd like to send the best value of a to every process and store it in c where best is defined by having the largest value of b.

I guess I'm just wondering if there is a more efficient way of doing this than doing an allgather on a and b and then searching through the resulting arrays.

The actual code involves sending and comparing several hundred values on upto several hundred/thousand processes, so any efficiency gains would be welcome.
**/
#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>
void defineStruct(MPI_Datatype *tstype);

typedef struct MyStruct {
    double a, b;
} S;

void max_struct(void *in, void *inout, int *len, MPI_Datatype *type){
    S *invals    = in;
    S *inoutvals = inout;

    for (int i=0; i<*len; i++) {
        inoutvals[i].b  = (inoutvals[i].b > invals[i].b) ? inoutvals[i].b  : invals[i].b;
    }
}

void defineStruct(MPI_Datatype *tstype) {
    const int count = 2;
    int          blocklens[count] = {1, 1};
    MPI_Datatype types[count] = {MPI_DOUBLE, MPI_DOUBLE};
    MPI_Aint     disps[count] = {offsetof(S,a), offsetof(S,b)};

    MPI_Type_create_struct(count, blocklens, disps, types, tstype);
    MPI_Type_commit(tstype);
}

int main(int argc,char *argv[]){
    MPI_Init(NULL,NULL); // Initialize the MPI environment
    int world_rank; 
    int world_size;
    MPI_Comm_rank(MPI_COMM_WORLD,&world_rank);
    MPI_Comm_size(MPI_COMM_WORLD,&world_size);
    MPI_Datatype structtype;
    MPI_Op       maxstruct;
    S  local, global;

    defineStruct(&structtype);
    MPI_Op_create(max_struct, 1, &maxstruct);

    local.a = world_rank;
    local.b = world_size - world_rank;

    MPI_Allreduce(&local, &global, 1, structtype, maxstruct, MPI_COMM_WORLD);  
          
    if(world_rank == 0){
      printf("%f %f\n", global.b, global.a);
    }

    MPI_Finalize();
    return 0;
 }
