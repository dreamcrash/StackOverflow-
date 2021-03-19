/** Original Question : https://stackoverflow.com/questions/66714286/

I was thinking of the basic parallel for construct inside the while, 
but my counter variable is expected to be around 5000. 
So I would like that to be in parallel. 
But I need w_new to copy back to w so I can calculate the next iteration of w_new. 
So I don't think tasks or sections would work as I would need one w for the other. Thank you for the help.

**/


#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <omp.h>
#define n 1000

int main(int argc, char** argv)
{
   const int total_threads      = (argc > 1) ? atoi(argv[1]) : 1;
   float w[n][n],w_new[n][n]={0},max=100;
   int counter = 0;
   omp_set_dynamic(0);     // Explicitly disable dynamic teams
   omp_set_num_threads(total_threads); 

   double begin = omp_get_wtime(); 
   #pragma omp parallel
   {
      #pragma omp for
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
             w[i][j]=75;

      #pragma omp for
      for (int i = 0; i < n; i++){
        w[0][i]=0;
        w[n-1][i]=100;
        w[i][0]=100;
        w[i][n-1]=100;

        w_new[0][i]=0;
        w_new[n-1][i]=100;
        w_new[i][0]=100;
        w_new[i][n-1]=100;
     }

     w[0][n-1]=100;
     w_new[0][n-1]=100;

     
    while(max > 0.0001){
       // We need this barrier to ensure that we don't have a
       // race condition between the master updating max to zero
       // and the other threads reading max > 0.0001 
       #pragma omp barrier
       #pragma omp master
       #pragma omp atomic write
        max = 0;
       #pragma omp barrier 
       
        #pragma omp for reduction(max:max)
        for (int i = 1; i < n-1; i++){
           for (int j = 1; j < n-1; j++){
              w_new[i][j]=(float)((w[i+1][j]+w[i-1][j]+w[i][j+1]+w[i][j-1])/4);
              if (max < fabs(w_new[i][j]-w[i][j]))
                  max = fabs(w_new[i][j]-w[i][j]);
           }
        }      
          
        #pragma omp for nowait
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
               w[i][j]=w_new[i][j];
          
        #pragma omp master
        counter++;
     } 
   }
   double end = omp_get_wtime();
   printf("Time: %.8f\n",end-begin);
   printf("Counter is: %d\n", counter);
   return 0;
}
