/** Original Question : https://stackoverflow.com/questions/67367721

I am trying to divide columns of a 2D matrix among N process with MPI. For template I used the example on MPI_Scatter - sending columns of 2D array.

**/

#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>

    char** board_initialize(int n, int m){
        char* bd = (char*)malloc(sizeof(char) * n * m);
        char** b = (char**)malloc(sizeof(char*) * n);
        for (int k = 0; k < n; k++)
            b[k] = &bd[k * m];

        for (int k = 0; k < n; k++)
            for (int l = 0; l < m; l++)
                b[k][l] = rand() < 0.25 * RAND_MAX;

        return b;
    }

    void board_print(char** b, int n, int m)
    {
        for (int k = 0; k < n; k++){
            for (int l = 0; l < m; l++)
                printf("%d", b[k][l]);
            printf("\n");
        }
        printf("\n");
    }


   int main(int argc, char* argv[])
    {   
        int N = 10;

        char * boardptr = NULL;                 // ptr to board
        char ** board;                          // board, 2D matrix, contignous memory allocation!

        int procs, myid;            
        int mycols;
        char ** myboard;                        // part of board that belongs to a process

        MPI_Init(&argc, &argv);                 // initiailzation
        
        MPI_Comm_rank(MPI_COMM_WORLD, &myid);   // process ID
        MPI_Comm_size(MPI_COMM_WORLD, &procs);  // number of processes

        // initialize global board
        if (myid == 0)
        {
            srand(1573949136);
            board = board_initialize(N, N);
            boardptr = *board;
            board_print(board, N, N);
        }
        // divide work
        mycols = N / procs;


        // initialize my structures
        myboard = board_initialize(N,mycols);
	MPI_Datatype acol, acoltype, bcol, bcoltype;

	if (myid == 0) {
    		MPI_Type_vector(N,    
               	1,                  
              	N,         
               	MPI_CHAR,       
               	&acol);       
            	MPI_Type_commit(&acol);
        	MPI_Type_create_resized(acol, 0, 1*sizeof(char), &acoltype);
	}
	MPI_Type_vector(N,    
               1,                  
               mycols,         
               MPI_CHAR,       
               &bcol);       

	MPI_Type_commit(&bcol);
	MPI_Type_create_resized(bcol, 0, 1*sizeof(char), &bcoltype);
	MPI_Type_commit(&bcoltype);
	

	MPI_Scatter (boardptr, mycols, acoltype, *myboard, mycols, bcoltype, 0, MPI_COMM_WORLD);
        board_print(myboard, N, mycols);
        
        MPI_Finalize();         // finalize MPI

        return 0;
    }
