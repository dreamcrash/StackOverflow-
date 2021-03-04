/** Original Question : https://stackoverflow.com/questions/66261968

How can we calculate, for every element in an array, the number of elements 
to the right that are greater than that element?

Given an array A with n values, let X of A be an array that holds in index i the number of 
elements which are bigger than A[i] and are to its right side in the original array A.

For example, if A was: [10,12,8,17,3,24,19], then X(A) is: [4,3,3,2,2,0,0]

How can I solve this in O(n log(n)) Time and O(n) Space complexity?

I can solve this easily in O(n^2) Time and O(1) Space by using a loop and, for every element, 
counting how many elements are bigger than it on the right side, but I wasn't successful with those requirements.

I was thinking about using quick sort with can be done in O(n log(n)) at worst, 
but I don't see how the sorted array could help here.

Note: Regarding quick sort the algorithm needs some tweak to insure O(n log(n)) at worst and not only on average.

**/
#include<stdio.h>
#include<stdlib.h>
 
/**
The AVL code was adapted from https://www.geeksforgeeks.org/avl-tree-set-1-insertion/

**/ 

struct Node{
    int key;
    struct Node *left;
    struct Node *right;
    int height;
    int size;
};
 
int height(struct Node *N){
    return (N == NULL) ? 0 : N->height;
}

int sizeRightTree(struct Node *N){
    return (N == NULL || N -> right == NULL) ? 0 : N->right->height;
}
 
int max(int a, int b){
    return (a > b) ? a : b;
}
 
struct Node* newNode(int key){
    struct Node* node = (struct Node*) malloc(sizeof(struct Node));
    node->key   = key;
    node->left   = NULL;
    node->right  = NULL;
    node->height = 1;
    node->size = 0;
    return(node);
}
 
struct Node *rightRotate(struct Node *y) {
    struct Node *x = y->left;
    struct Node *T2 = x->right;
 
    x->right = y;
    y->left = T2;
 
    y->height = max(height(y->left), height(y->right))+1;
    x->height = max(height(x->left), height(x->right))+1;
    y->size = sizeRightTree(y);
    x->size = sizeRightTree(x);
    return x;
}
 
struct Node *leftRotate(struct Node *x){
    struct Node *y = x->right;
    struct Node *T2 = y->left;
 
    y->left = x;
    x->right = T2;
 
    x->height = max(height(x->left), height(x->right))+1;
    y->height = max(height(y->left), height(y->right))+1;
    y->size = sizeRightTree(y);
    x->size = sizeRightTree(x); 

    return y;
}
 
int getBalance(struct Node *N){
    return (N == NULL) ? 0 : height(N->left) - height(N->right);
}
 
struct Node* insert(struct Node* node, int key, int *size){
    if (node == NULL)
        return(newNode(key));
    if (key < node->key){
        *size = *size + node->size + 1;
        node->left  = insert(node->left, key, size);
    } 
    else if (key > node->key){
	node->size++;
	node->right = insert(node->right, key, size);
    }
    else 
        return node;
 
    node->height = 1 + max(height(node->left), height(node->right));
    int balance = getBalance(node);
 
    if (balance > 1 && key < node->left->key)
        return rightRotate(node);
    if (balance < -1 && key > node->right->key)
        return leftRotate(node);
    if (balance > 1 && key > node->left->key){
        node->left =  leftRotate(node->left);
        return rightRotate(node);
    }
    if (balance < -1 && key < node->right->key){
        node->right = rightRotate(node->right);
        return leftRotate(node);
    } 
    return node;
}

int main()
{
  int arraySize = 7;
  struct Node *root = NULL;
  int A[7] = {10,12,8,17,3,24,19};
  int X[7] ={0};
  for(int i = arraySize - 1; i >= 0; i--)
     root = insert(root, A[i], &X[i]);

  for(int i = 0; i < arraySize; i++)
     printf("%d ", X[i]);
  printf("\n");
  return 0;
}
