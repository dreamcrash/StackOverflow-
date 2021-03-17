/** Original Question : https://stackoverflow.com/questions/66664531
int v[10] = {2,9,1,3,5,7,1,2,0,0};
int maximo = 0;
int b = 0;
int i;

#pragma omp parallel for shared(v) private(i) reduction(max:maximo)
for(i = 0; i< 10; i++){
    if (v[i] > maximo)
        maximo = v[i];
    b = i + 100;
}
How can I get the value b gets during the iteration when maximo 
gets its max value (and therefore, its value after the for loop)?

**/


#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

struct MyMax { 
   int max; 
   int index; 
}; 

int main(void)
{
    #pragma omp declare reduction(maximo : struct MyMax : omp_out = omp_in.max > omp_out.max ? omp_in : omp_out)
    struct MyMax myMaxStruct;  
    myMaxStruct.max = 0;
    myMaxStruct.index = 0;

    int v[10] = {2,9,1,3,5,7,1,2,0,0};

    #pragma omp parallel for reduction(maximo:myMaxStruct)
    for(int i = 0; i< 10; i++){
       if (v[i] > myMaxStruct.max){
          myMaxStruct.max = v[i];
    	  myMaxStruct.index = i + 100;
      }
    }
    printf("Max %d : Index %d\n", myMaxStruct.max, myMaxStruct.index);
}
