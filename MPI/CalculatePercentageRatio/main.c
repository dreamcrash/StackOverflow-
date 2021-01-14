/** Original Question : https://stackoverflow.com/questions/65597324/how-to-calculate-percentage-ratio/

I am new to mpi and I am trying to write a mini C program that calculates 
the percentage ratio of numbers that the user inputs.

The percentage ratio is calculated by that expression

`δi = ((xi – xmin ) / (xmax – xmin )) * 100`. 
The numbers that the user inputs are stored in an array of fixed size data[100] and 
are scattered to all processes (this program is supposed to work only with four processes). 
The problem I am facing is that the division doesn't work although all the processes have the data. 
For example if the user inputs the numbers {1, 2, 3, 4} the expected percentage 
ratio according to the mathematical expression is {0, 33.3, 66.6, 100} but instead 
I am getting {0,0,100,100}. This is what I have.
*/


#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>

int main(int argc, char** argv){
    int my_rank;
    int total_processes;
    int root = 0;
    int *data = NULL;
    int *loc_data;

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
    MPI_Comm_size(MPI_COMM_WORLD, &total_processes);

    int input_size = 0;
    if (my_rank == 0){
        input_size = 8;
	data = malloc(input_size * sizeof(int));      
        for(int i = 0; i < input_size; i++)
	   data[i] = i + 1;
    }
    MPI_Bcast(&input_size, 1, MPI_INT, root, MPI_COMM_WORLD);
    int loc_num = input_size/total_processes;
    loc_data = malloc(loc_num * sizeof(int));
    MPI_Scatter(data, loc_num, MPI_INT, loc_data, loc_num, MPI_INT, root, MPI_COMM_WORLD);
    
    int local_max = loc_data[0];
    int local_min = loc_data[0];
    for(int i = 0; i < loc_num; i++){
       local_max = (local_max > loc_data[i]) ?  local_max : loc_data[i];
       local_min = (local_min < loc_data[i]) ?  local_min : loc_data[i];  
    }
    int global_max = local_max;
    int global_min = local_min;

    MPI_Allreduce(&local_max, &global_max, 1, MPI_INT, MPI_MAX, MPI_COMM_WORLD);
    MPI_Allreduce(&local_min, &global_min, 1, MPI_INT, MPI_MIN, MPI_COMM_WORLD);


    float *loc_delta = malloc(loc_num * sizeof(float));
    float y = global_max - global_min;
    for(int j = 0; j< loc_num; j++){
        loc_delta[j] = (((loc_data[j] - global_min) / y) * 100.0); 
    }
    
    float *final_delta = (my_rank == 0) ? malloc(total_processes * loc_num * sizeof(float)) : NULL;
    MPI_Gather(loc_delta, loc_num, MPI_FLOAT, final_delta, loc_num, MPI_FLOAT, root, MPI_COMM_WORLD);
    if(my_rank == 0){
        printf("max number: %d\n", global_max);
        printf("min number: %d\n", global_min);
        for(int i = 0; i<input_size; i++)
            printf("delta[%d]: %.2f | ", i+1, final_delta[i]);
        printf("\n");
    }
    free(data);
    free(loc_data);
    free(loc_delta);
    free(final_delta);
    MPI_Finalize();

    return 0;
}
