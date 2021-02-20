/** Original Question : https://stackoverflow.com/questions/41088242

Suppose I have an array: int list[] = {1, 2, 4, 5, 6}; and I want to shift the array from the middle to the right and place 3 in the place of 4 so it would look like: {1, 2, 3, 4, 5, 6}.

How would I do it?

**/
#include <stdio.h>
#include <stdlib.h>



void print(int *a, int n){
     for(int i = 0; i < n; i++)
       printf("%d ", a[i]);
    printf("\n");
}

int add(int *array, int *size, int *occupancy, int pos, int new_value){
     if((*occupancy) < pos) // For simplicitly one can only add within 0 to occupancy
       return EXIT_FAILURE; 

     int tmp = array[pos];
     array[pos] = new_value;
     (*occupancy)++;

    if(occupancy == size)
    {
       *size = 2 * (*size);
       int *new_array = malloc(sizeof(int) * (*size));

       for(int i = 0; i <= pos; i++)
          new_array[i] = array[i];

       new_array[pos+1] = tmp;
       for(int i = pos+1; i < *occupancy; i++)
           new_array[i+1] = array[i];

        free(array);
        array = new_array;
   }    
   else
   {
       for(int i = pos+1; i < *occupancy; i++)
       {
           int tmp2 = array[i];
           array[i] = tmp;
           tmp = tmp2;
       } 
   }
   return EXIT_SUCCESS;
} 

int main(int argc, char *argv[])
{
    int size = (argc > 1) ? atoi(argv[1]) : 10;
    int occupancy =  size - 2;
    int *array = malloc(sizeof(int) * size);
    if(!array)
    {
      return (EXIT_FAILURE);
    } 
    for(int i = 0; i < occupancy; i++)
       array[i] = i;
     
    print(array, occupancy);

    int pos, value;
      
    // For simplicity I am assmung that the input is always correct 
    do{ 
       printf("Input the position and value to be inserted : format -> int int\n");
       scanf("%d %d", &pos, &value);
       if(pos == -1)
          break; 
       int status = add(array, &size, &occupancy, pos, value);
       if(status == EXIT_SUCCESS)
          print(array, occupancy);
       else
          printf("Invalid Position\n");
    }while(1);


    free(array);
    return (EXIT_SUCCESS);
}

