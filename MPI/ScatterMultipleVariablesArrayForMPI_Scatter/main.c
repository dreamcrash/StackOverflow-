/** Original Question : https://stackoverflow.com/questions/65650202/how-to-scatter-multiple-variables-in-an-array-for-mpi-scatter

How to scatter multiple variables in an array for MPI_Scatter

I am currently struggling to equally distribute an array with 8 integers to 2 integers per 4 processors. 
I used MPI_Bcast to let every processors to know there are total array of 8 and each of those will have 
2 integers array called "my_input".

...

However after scattering, I see the print function cannot print the 'rank' but all the integers from 
the 8 integers array. 
How should I program in order to equally distribute the number of arrays to other processors from root?

Here is my full code (it is just for testing a total of 8 integers, therefore scanf I will enter '8'):

**/


#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "mpi.h"

int main(int argc, char *argv[])
{
    MPI_Init(&argc, &argv);
    int rank, size;
    int *input;
    int totalarray;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    if (rank == 0){
        int number = (argc > 1) ? atoi(argv[1]) : 8;
        printf("Your number is %d\n",number);
        totalarray = pow(2, ceil(log(number)/log(2)));

        input = malloc(totalarray * sizeof(int));         
        int upper = 100, lower = 0;
        for(int i = 0; i < number ; i++)
           input[i] = (rand() % (upper - lower + 1)) + lower;
        for(int i = number; i < totalarray; i++)
           input[i] = 0;
        printf("the input is: ");
        for(int i =0; i < totalarray ; i++)
           printf(  "%d  ", input[i]);
    }
    
    MPI_Bcast(&totalarray, 1, MPI_INT, 0, MPI_COMM_WORLD);
    int size_per_process = totalarray / size;
    int *my_input = malloc(size_per_process * sizeof(int));
    MPI_Scatter (input, size_per_process, MPI_INT, my_input, size_per_process, MPI_INT, 0, MPI_COMM_WORLD );
    printf("\n my input is %d & %d and rank is  %d \n" , my_input[0], my_input[1] , rank);
    MPI_Finalize();
    return 0;
}
