/** Original Question : https://stackoverflow.com/questions/65509104/problems-counting-the-number-of-vowels-in-an-array
 
Problems counting the number of vowels in an array
**/

#include <stdio.h>
int vowel_count(char n[]){
    int hasil = 0;
    char vowel[] = "aiueoyAIUEOY";
    for (int i = 0; n[i] != '\0'; i++)
        for (int x = 0; vowel[x] != '\0'; x++)
            if (n[i] == vowel[x])
                hasil++;
    return hasil;
}

int main(void){
    int amount;
    char values[50], unknown[10];
    char vowel[] = "AIUEOYaiueoy";
    FILE* fp = fopen("zValues.txt", "r");
    fscanf(fp, "%d", &amount);
    fgets(unknown, 10, fp);
    for (int n = 0; n < amount; n++){
        fgets(values, 10, fp);
        printf("%d ", vowel_count(values)); 
    }
    
    fclose(fp);
}

