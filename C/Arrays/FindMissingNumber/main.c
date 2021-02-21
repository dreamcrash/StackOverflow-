/** Original Question : https://stackoverflow.com/questions/65530494

Is there an O(n) algorithm to find the first missing number in an array?

Given an array of n integers, not necessarily sorted, is there an O(n) 
algorithm to find the least integer that is greater than the 
minimum integer in the array but that is not in the array?

**/

#include <stdio.h>
#include <stdio.h>
#include <stdlib.h>

int find_min(int *array, int size){
    int min = array[0];
    for(int i = 0; i < size; i++)
        min = (array[i] < min) ? array[i] : min;
    return min;
}

void fill_array(int *array, int size, int missing_value){
     for(int i = 0; i < size; i++)
        array[i] = missing_value;
}

void mark_existing_values(int *s, int size, int *d, int min){
    for(int i = 0; i < size; i++){
        int elem = s[i];
        if(elem - min < size)
           d[elem - min] = elem;
    }
}

int find_first_missing_value(int *array, int size, int min){
     int missing_value = min - 1;
     for(int i = 0; i < size; i++){
         if(array[i] == missing_value){
            return i + min;
         }
     }
    return missing_value;
}


int main(){
    int array_size =  6;
    int array_example [] = {5, 3, 0, 1, 2, 6};
    int min = find_min(array_example, array_size);
    int *missing_values = malloc(array_size * sizeof(int));
    fill_array(missing_values, array_size, min - 1);
    mark_existing_values(array_example, array_size, missing_values, min);
    int value = find_first_missing_value(missing_values, array_size, min);
    printf("First missing value {%d}\n", value);
    free(missing_values);
    return 0;
}

