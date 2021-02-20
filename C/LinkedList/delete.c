/** Original Question : https://stackoverflow.com/questions/13656061

I am currently writing a program that has a function that needs to delete a node based on its value.

I have tried and tried to figure it out.

All I have so far is the function signature:

NODE* delete_node(NODE * ptr, int n, int *success_flag)
My linked list as the follow structure:

/* declaration of structure */
typedef struct node
{
  int data;
  struct node *next;
} NODE;
Heres some of the code I already have regarding another concerns:

#include <stdio.h>
#include <stdlib.h>

/* declaration of structure */
typedef struct node
{
  int data;
  struct node *next;
} NODE;

/* declaration of functions */
NODE* insert_node(NODE *ptr, NODE *new);
NODE* find_node(NODE *ptr, int n);
NODE* delete_node(NODE *ptr, int n, int *success_flag_ptr);
void print_backward_iteration(NODE *ptr);
void print_backward_recursion(NODE *ptr);

int main(int argc, char *argv[])
{
  int choice, x, flag_success;
  NODE *ptr, *new, *result;

  ptr = NULL;

  do
    {

      printf("\n1.\tInsert Integer into linked list\n");
      printf("2.\tFind integer in linked list\n");
      printf("3.\tDelete integer from linked list\n");
      printf("4.\tPrint out integers backward using the iterative strategy\n");
      printf("5.\tPrint out integers backward using the recursive strategy\n");
      printf("6.\tQuit\n");
      printf("\nEnter 1,2,3,4,5, or 6: ");
      scanf("%d", &choice);

      switch(choice)
    {
    case 1:

      printf("\nPlease enter an integer: ");
      scanf("%d", &x);
      new = (NODE *)malloc(sizeof(NODE));
      new->data = x;
      ptr = insert_node(ptr, new);
      printf("\nNode Inserted with value of %d.\n", ptr->data);
      break;

    case 2:

      printf("\nPlease enter an integer: ");
      scanf("%d", &x);
      result = find_node(ptr, x);

      if (result == NULL)
        {
          printf("\nValue could not be found.\n");
        }
      else
        {
          printf("\nValue %d was found.\n", x);
        }

      break;

    case 3:
      printf("\nPlease enter an integer: ");
      scanf("%d", &x);
      ptr = delete_node(ptr, x, &flag_success);

        if (result == NULL)
        {
          printf("\nValue could not be found.\n");
        }
      else
        {
          printf("\nValue %d was deleted.\n", x);
        }

      break;

    case 4:

      print_backward_iteration(ptr);
      break;

    case 5:

      printf("\n");
      print_backward_recursion(ptr);
      printf("\n");

      break;

    case 6:
      printf("\nThank you for using this program.\n");
    break;

    default:
      printf("\nInvalid Choice. Please try again.\n");
      break;

    }
    }
  while (choice != 6);

  printf("\n*** End of Program ***\n");
  return 0;
}

/* definition of function insert_node */
NODE* insert_node(NODE *ptr, NODE *new)
{

 new -> next = ptr;
 return new;

}

/* definition of function find_node */
NODE* find_node(NODE *ptr, int n)
{

  while (ptr != NULL)
    {
      if (ptr->data == n)  
    {
      return ptr;
    }
      else
    {
      ptr = ptr->next;
    }
    }
  return NULL;
  }

/* definition of function print_backward_iteration */
void print_backward_iteration(NODE *ptr)
{
  NODE *last, *current;

  last = NULL;

  printf("\n");

  while (ptr != last)
    {
      current = ptr;

      while (current != last)
    {
      current =  current -> next;
    }

      printf("%d  ", current -> data);
      last = current -> next;
    }

    printf("\n");

}

/* definition of function print_backward_recursion */
void print_backward_recursion(NODE *ptr)
{
  NODE *last, *current;

  last = NULL;

  while (ptr != last)
      {
    current = ptr;
    printf("%d  ", current -> data);
    print_backward_recursion(current -> next);
    last = current;
      }

}
**/


NODE* delete_node(NODE * ptr, int n, int *success_flag)
{
   Node *aux = NULL;

   if(ptr == NULL) // this means that theres is no 'n' in this linked list
   {
    *success_flag = 0;  // means no value found
    return NULL;
   }
   if(ptr-> n == n){     // if this is the value you want
      aux = ptr->next;   // aux will point to the remaining list
      free(ptr);         // free the actual node
      *success_flag = 1; // mark as success 
      return aux;        // return the pointer to the remaining list
    }
   else // lets see the remaining nodes 
      ptr->next = delete_node(ptr->next,n,success_flag); 

  return ptr;
 }

