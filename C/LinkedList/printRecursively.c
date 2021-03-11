/** Original Question : https://stackoverflow.com/questions/66531341

Print reversely the elements of a list using recursion

I want to print reversely the elements of a list using recursion, but I get an execution error !

Can someone help?

typedef struct node
{
    int val;
    struct node* next;
}node;
typedef struct list
{
    node* head;
}list;
void display(list L)
{
    if(L.head == NULL)    return;
    L.head= L.head->next;
    display(L);
    printf("%d\t",L.head->val);
}

**/



#include <stdio.h>
#include <stdio.h>
#include <stdlib.h>

typedef struct List
{
    int val;
    struct List* next;
}List;

void display(List *l){
    if(l != NULL){
       display(l->next);
       printf("%d\t",l->val);
    }
}

List *addElement(List *l, int val){
      if(l == NULL){
         List *node = malloc(sizeof(List));
         node->val = val;
         node->next = NULL;
         return node;
      }
      else 
         l->next = addElement(l->next, val);
      return l;
}


void freeList(List *l){
    if(l != NULL){
      freeList(l->next);
      free(l);
    }
}

int main(){
   List *l = NULL;
   l = addElement(l, 1);
   l = addElement(l, 2);
   l = addElement(l, 3);
   l = addElement(l, 4);
   l = addElement(l, 5); 
   display(l);
   freeList(l);
}
