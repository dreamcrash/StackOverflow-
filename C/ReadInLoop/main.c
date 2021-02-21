/** Original Question : https://stackoverflow.com/questions/65508024

Checking Loop in C - scan() is not running by the second run

void openMenu(int *op) {//edited
    do {
        printf("Your turn...\t\n");
        scanf(" %d", op);
        if (*op > 14 || *op < 1 ) {
            printf("Only enter a number between 1 and 14!\n");
        }
    } while (*op > 14 || *op < 1 );
}
I am trying to make a checking loop which controls if the value which 
was entered is between 1 and 14. If there are letters it has to repeat too, the enter process.

Perhaps it isn't working and everytime it goes in the second run the scanf didn't run.

I checked the thing to set a space in front of the %d, but it isn't working too... Did u have maybe a nice idea?

Working with Xcode on Mac 11.1

*/


#include <stdio.h>

int isNumber(char buffer[]){
    for(int i = 0; buffer[i] != '\0'; i++)
        if(!isdigit((unsigned char) buffer[i]))
           return 0;
    return 1;
}

int readInput(char buffer[]){
    int result = scanf("%99s", buffer); 
    while(getchar()!='\n');
    return result;
}

int isInRange(int *op, char buffer[]){
    *op = atoi(buffer);
    return *op <= 14 && *op >= 1;
}
           
           
void openMenu(int *op) {
    do {
        char buffer[100];
        if(readInput(buffer) && isNumber(buffer) && isInRange(op, buffer)) {
           break;
        }
        printf("Only enter a number between 1 and 14!\n");
    } while (1);
}   

int main()
{
    int op;
    printf("Enter a value between 0 and 14\n");
    openMenu(&op);
    printf("Number Read {%d}\n", op);
    return 0;
}
