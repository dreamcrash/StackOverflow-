/** Original Question : https://stackoverflow.com/questions/65458735/

remove all occurrences of a given number from an array in C

I'm trying to solve this question :

You've got an array of integers of somewhat SIZE - i.e, arr[SIZE]. 
Write a function which remove all occurrences of a given number x from an array
I've wrote this function (below) which is suited for a case that the number is 
of 1 and only occurrence but not for multiple and I'm struggling to find a 
breakthrough for the last case (multiple occurrences) . 
My way of thinking was : in case of match - shift left one index all the 
element which are to the right side of the match and return the user a "new" size.

For the example below : { 3, 0, 5, 6, 6 }; and the number 5 the result 
will be (and indeed is) : { 3, 0, 6, 6 } , but for : { 3, 0, 5, 5, 6 }, 
I've got : { 3, 0, 5, 6 }. I know why do I get it but I don't know how to fix it. 
I tried using a flag which will give the indication of whether I have two 
adjacent match element and if it is loop again on this index

Can someone please guide me to a solve ?

**/

#include <stdio.h>
#include <stdlib.h>

int remove_occurences(int *arr, int size, int value_to_remove){
    int i = 0, total_elements = 0;
    for (; i < size; i++)
         if (arr[i] != value_to_remove)
            arr[total_elements++] = arr[i];
    return total_elements;
}

unsigned int rand_interval(unsigned int min, unsigned int max)
{
    // https://stackoverflow.com/questions/2509679/
    int r;
    const unsigned int range = 1 + max - min;
    const unsigned int buckets = RAND_MAX / range;
    const unsigned int limit = buckets * range;

    do
    {
        r = rand();
    } 
    while (r >= limit);

    return min + (r / buckets);
}

void fillupRandomly (int *m, int size, unsigned int min, unsigned int max){
    for (int i = 0; i < size; i++)
	m[i] = rand_interval(min, max);
} 

void print(int *array, int size){
    for (int i = 0; i < size; i++)
       printf("%d ", array[i]);
    printf("\n");
}

int main(int argc, char *argv[])
{
    int value_to_remove;
    int size = (argc > 1) ? atoi(argv[1]) : 10;
    int *array = malloc(size * sizeof(int));
    fillupRandomly(array, size, 0, 10);
    print(array, size);

    printf("Give a value to be removed\n");
    // For simplicity I am assuming that the input is 
    // always correct
    scanf("%d",&value_to_remove);

    int new_length = remove_occurences(array, size, value_to_remove);
    printf("New Size -> {%d}\n",new_length);
    print(array, new_length);
    free(array);
    return 0;
}
