/**
https://stackoverflow.com/questions/20413995
**/

#include <iostream>
#include <stdio.h>
#include <time.h>
#include <omp.h>
using namespace std;

int main ()
{
  int A [] = {84, 30, 95, 94, 36, 73, 52, 23, 2, 13};
  int S [10] = {0};

  #pragma omp parallel for reduction(+:S)
  for (int n=0 ; n<10 ; ++n ){
    for (int m=0; m<=n; ++m){
      S[n] += A[m];
    }
  }
  int expected_output [] = {84, 114, 209, 303, 339, 412, 464, 487, 489, 502};   
  for(int i = 0; i < 10; i++){
      if(S[i] == expected_output[i])
        printf("%d\n", S[i]);
     else
       printf("ERROR! it should have been %d instead of %d\n", expected_output[i], S[i]);
  }
  
  return 0;
}
