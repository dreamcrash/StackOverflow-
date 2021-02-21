/** Original Question : https://stackoverflow.com/questions/65429478/

This function gets a sequence of numbers until the user inputs -1. 
It should return the sum of the numbers at the index(mod3)=0 minus the number in the other indexes.

For example:

9 2 4 7 8 1 3 -1 

the function sums at first all the numbers that are at the indexes that divisible by three. 
So sum1 supposed to be 5 because 4 is at the 3rd index and 1 is at the 6th index, 
all the other indexes sum up to sum2. Then it should subtract one from another : 
sum1 - sum2 and the output should be -24 (i.e., sum1 - sum2 = 5 - 29 = -24)

I wrote this code and I don't understand why it doesn't work properly the output that is returned for every sequence is 0.

**/

#include <stdio.h>


void main()
{
    printf("%d\n", sumOfSeq(1, 0, 0));
    system("pause");
}

int sumOfSeq(int curr, int sum1, int sum2)
{
    int n;
    scanf("%d", &n);
    if (n == -1)
        return sum2 - sum1;

    return (curr % 3 == 0) ? sumOfSeq(curr + 1, sum1 + n, sum2) : 
                             sumOfSeq(curr + 1, sum1, sum2 + n);
}
