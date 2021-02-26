/**
Original Question : https://stackoverflow.com/questions/65907260

Check if binary search tree contains more evens numbers than odds

I want to implement a function to check if a BST (of int values) 
contains more odds it should return 2, if it contains more evens return 1 and 0 otherwise - recursively.

I was trying to create help function with inputs of even and odd counter but the function returns wrong output.

My code: 

int evenOdd(Tnode* root) {
    if (root == NULL)
        return 0 ;
    return eVenOddHelp(root , 0 , 0) ;
}

int eVenOddHelp(Tnode* root , int even , int odd) {
    if (root == NULL) {
        if (even > odd)
            return 1 ;
        else if (odd > even)
            return 2 ;
        return 0 ;
    }
    else {
        if (root->data % 2 == 0) {
            eVenOddHelp(root->left , even + 1 , odd) ;
            eVenOddHelp(root->right , even + 1 , odd) ;
        }
        else {
            eVenOddHelp(root->left , even , odd + 1) ;
            eVenOddHelp(root->right , even  , odd + 1) ;
        }
    }
}


**/


int evenOdd(Tnode* root) {
    int result = eVenOddHelp(root);
    if(result > 1) return 1;       // More evens
    else if(result == 0) return 0; // the same 
    else return 2;                 // More odds
}

int eVenOddHelp(Tnode* root) {
    if (root != NULL) {
       int isEven = (root->data % 2 == 0) ? 1 : -1;
       return eVenOddHelp(root->left) + eVenOddHelp(root->right) + isEven;
    }
    return 0;
}

