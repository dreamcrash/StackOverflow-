/** Original question : https://stackoverflow.com/questions/64987301/functions-dependency-openmp/

I have 5 functions A,B,C,D,E.
To execute D I need B, C to be executed, to execute E I need A, D to be executed.
I have tried this

int main()
{
    #pragma omp parallel num_threads(5) 
    {
        long t1 = clock();
        int a = 0, b = 0, c = 0, d = 0, e = 0;
        int th = omp_get_thread_num();
        if (th == 0) {
            a += A();
            printf("A is finished after %d\n", clock() - t1);
        }
        if (th == 1) {
            b += B();
            printf("B is finished after %d\n", clock() - t1);
        }
        if (th == 2) {
            c += C();
            printf("C is finished after %d\n", clock() - t1);
        }
        if (th == 3 && (b == 1 && c == 1)) {
            d += D();
            printf("D is finished after %d\n", clock() - t1);
        }
        if (th == 4 && (a == 1 && d == 1)) {
            e += E();
            printf("E is finished after %d\n", clock() - t1);
        }

    }
    return 0;
}

but D, E haven't execute

All of these functions return 1 till now for debugging purpose


**/


int main()
    {
        #pragma omp parallel num_threads(5) 
        {
            long t1 = clock();
            int a = 0, b = 0, c = 0, d = 0, e = 0;
            int th = omp_get_thread_num();
            if (th == 0) {
                a += A();
                printf("A is finished after %d\n", clock() - t1);
            }
            if (th == 1) {
                b += B();
                printf("B is finished after %d\n", clock() - t1);
            }
            if (th == 2) {
                c += C();
                printf("C is finished after %d\n", clock() - t1);
            }
            // Threads will wait for each other here
            #pragma omp barrier 
            if (th == 3) {
                d += D();
                printf("D is finished after %d\n", clock() - t1);
            }
            // Threads will wait for each other here
            #pragma omp barrier 
            if (th == 4) {
                e += E();
                printf("E is finished after %d\n", clock() - t1);
            }
        }
        return 0;
    }
