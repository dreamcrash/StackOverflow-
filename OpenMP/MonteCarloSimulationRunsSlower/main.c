/** Original Question : https://stackoverflow.com/questions/65560634/monte-carlo-simulation-runs-significantly-slower-than-sequential/

Monte Carlo simulation runs significantly slower than sequential

I'm new to the concept of concurrent and parallel programing in general. 
I'm trying to calculate Pi using Monte Carlo method in C. Here is my source code:

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>

int main(void)
{
    long points;
    long m = 0;
    double coordinates[2];
    double distance;
    printf("Enter the number of points: ");
    scanf("%ld", &points);

    srand((unsigned long) time(NULL));
    for(long i = 0; i < points; i++)
    {
        coordinates[0] = ((double) rand() / (RAND_MAX));
        coordinates[1] = ((double) rand() / (RAND_MAX));
        distance = sqrt(pow(coordinates[0], 2) + pow(coordinates[1], 2));
        if(distance <= 1)
            m++;
    }

    printf("Pi is roughly %lf\n", (double) 4*m / (double) points);
}


When I try to make this program parallel using openmp api it runs almost 4 times slower.

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include <omp.h>
#include <sys/sysinfo.h>

int main(void)
{

    long total_points;              // Total number of random points which is given by the user
    volatile long total_m = 0;      // Total number of random points which are inside of the circle
    int threads = get_nprocs();     // This is needed so each thred knows how amny random point it should generate
    printf("Enter the number of points: ");
    scanf("%ld", &total_points);
    omp_set_num_threads(threads);   

    #pragma omp parallel
    {
       double coordinates[2];          // Contains the x and y of each random point
       long m = 0;                     // Number of points that are in the circle for any particular thread
       long points = total_points / threads;   // Number of random points that each thread should generate
       double distance;                // Distance of the random point from the center of the circle, if greater than 1 then the point is outside of the circle
       srand((unsigned long) time(NULL));

        for(long i = 0; i < points; i++)
        {
           coordinates[0] = ((double) rand() / (RAND_MAX));    // Random x
           coordinates[1] = ((double) rand() / (RAND_MAX));    // Random y
           distance = sqrt(pow(coordinates[0], 2) + pow(coordinates[1], 2));   // Calculate the distance
          if(distance <= 1)
              m++;
       }

       #pragma omp critical
       {
           total_m += m;
       }
    }

    printf("Pi is roughly %lf\n", (double) 4*total_m / (double) total_points);
}
I tried looking up the reason but there was different answers to different algorithms.


**/


#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include <omp.h>

int main(void)
{
    long points = 1000000000; //....................................... INPUT AVOIDED
    long m = 0;
    unsigned long HAUSNUMERO = 1; //............................. AVOID SIN OF IREPRODUCIBILITY
    double DIV1byMAXbyMAX = 1. / RAND_MAX / RAND_MAX; //......... PRECOMPUTE A STATIC VALUE

    int threads = 4;
    omp_set_num_threads(threads);
    double start = omp_get_wtime();
    #pragma omp parallel
    {
        unsigned int aThreadSpecificSEED_x = HAUSNUMERO + 1 + omp_get_thread_num();
        unsigned int aThreadSpecificSEED_y = HAUSNUMERO - 1 + omp_get_thread_num();
        #pragma omp for reduction (+: m)
        for(long i = 0; i < points; i++)
        {   
            int x = rand_r( &aThreadSpecificSEED_x );
            int y = rand_r( &aThreadSpecificSEED_y );
            if( 1  >= ( x * x + y * y ) * DIV1byMAXbyMAX)
              m++;
         }
    }
    double end = omp_get_wtime();
    printf("%f\n",end-start);
    printf("Pi is roughly %lf\n", (double) 4*m / (double) points);
}
