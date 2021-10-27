/**
https://stackoverflow.com/questions/20413995
**/

#define S_SIZE 10
#include <stdio.h>
#include <time.h>
#include <omp.h>
int main ()
{
  int A [] = {84, 30, 95, 94, 36, 73, 52, 23, 2, 13};
  int S [S_SIZE] = {0};

  #pragma omp parallel for reduction(+:S[:S_SIZE])
  for (int n=0 ; n<S_SIZE ; ++n ){
    for (int m=0; m<=n; ++m){
      S[n] += A[m];
    }
  }
  int expected_output [] = {84, 114, 209, 303, 339, 412, 464, 487, 489, 502};   
  for(int i = 0; i < S_SIZE; i++){
      if(S[i] == expected_output[i])
        printf("%d\n", S[i]);
     else
       printf("ERROR! it should have been %d instead of %d\n", expected_output[i], S[i]);
  }
  
  return 0;
}
