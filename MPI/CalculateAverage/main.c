/** Original Question : https://stackoverflow.com/questions/65533005/how-to-calculate-average-across-multiple-processes-using-mpi-scatter-and-mpi-gat/

I am trying to make a mini C program to understand MPI, I want to have the user inputing 
how many numbers the program will receive, the numbers, and then scatter them equally on processes. 
Then each process will calculate it's local average and then with the 
gather the root process will calculate the whole average. The numbers are stored in 
an array with fixed size. The problem I have is that the root process 
doesn't calculate the average and I don't know why. I scatter and gather the numbers 
as I saw in other examples but I can't make it to work. This is what I have.

**/

#include <stdio.h>
#include "mpi.h"

int main(int argc, char** argv){
    int my_rank;
    int total_processes;
    int root = 0;
    int data[100];
    int data_loc[100];
    float final_res[100];

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
    MPI_Comm_size(MPI_COMM_WORLD, &total_processes);

    int input_size = 0;
    if (my_rank == 0){
       printf("Input how many numbers: ");
       scanf("%d", &input_size);
    
       printf("Input the elements of the array: ");
       for(int i=0; i<input_size; i++){
           scanf("%d", &data[i]);
       }
    }
 
    MPI_Bcast(&input_size, 1, MPI_INT, root, MPI_COMM_WORLD);

    int loc_num = input_size/total_processes;

    MPI_Scatter(&data, loc_num, MPI_INT, data_loc, loc_num, MPI_INT, root, MPI_COMM_WORLD);

    int loc_sum = 0;
    for(int i=0; i< loc_num; i++)
        loc_sum += data_loc[i];     
    float loc_avg = (float) loc_sum / (float) loc_num;
    MPI_Gather(&loc_avg, 1, MPI_FLOAT, final_res, 1, MPI_FLOAT, root, MPI_COMM_WORLD);

    if(my_rank==0){
      float fin = 0;
      for(int i=0; i<total_processes; i++)
         fin += final_res[i];
      float avg = fin / (float) total_processes;
      printf("Final average: %f \n", avg);
    }
    MPI_Finalize();
    return 0;
}

