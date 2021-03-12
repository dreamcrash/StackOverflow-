/** Original Question : https://stackoverflow.com/questions/66606118
I get segmentation fault core dumped. My function is supposed to return the prime number 
following the one put in parameters. or to return the number if it is a prime.

When I compile, I have no errors, I also compiled with -Wall, it worked. 
But when I run the program I get "segmentation fault, core dumped". I can't see the issue.

#include <stdio.h>

int     ft_next_prime(int nb)
{
        int     i = 2;
        int     notPrime = 0;

        while(i++ < 9 || notPrime != 1){
                if(nb == i)
                        i++;

                else if(nb % i == 0)
                        notPrime = 1;
        }

        return ((notPrime)? ft_next_prime(nb++) : nb);
}

int main()
{
        printf("%d", ft_next_prime(15));
}


**/

#include <stdio.h>

int ft_next_prime(int nb){
        int notPrime = 0;
        for(int i = 2; i < nb / 2 && !notPrime; i++)
            notPrime = nb % i == 0;
        return notPrime ? ft_next_prime(nb + 1) : nb;
}

int main()
{
        printf("%d", ft_next_prime(1217));
}
