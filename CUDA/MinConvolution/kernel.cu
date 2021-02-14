/** Original Question : https://stackoverflow.com/questions/13160617

I have two arrays, a and b, and I would like to compute the "min convolution" to produce result c. Simple pseudo code looks like the following:

for i = 0 to size(a)+size(b)
    c[i] = inf
    for j = 0 to size(a)
        if (i - j >= 0) and (i - j < size(b))
            c[i] = min(c[i], a[j] + b[i-j])
(edit: changed loops to start at 0 instead of 1)

If the min were instead a sum, we could use a Fast Fourier Transform (FFT), but in the min case, there is no such analog. Instead, I'd like to make this simple algorithm as fast as possible by using a GPU (CUDA). I'd be happy to find existing code that does this (or code that implements the sum case without FFTs, so that I could adapt it for my purposes), but my search so far hasn't turned up any good results. My use case will involve a's and b's that are of size between 1,000 and 100,000.

Questions:

Does code to do this efficiently already exist?
If I am going to implement this myself, structurally, how should the CUDA kernel look so as to maximize efficiency? I've tried a simple solution where each c[i] is computed by a separate thread, but this doesn't seem like the best way. Any tips in terms of how to set up thread block structure and memory access patterns?

**/


/** A faster version **/
__global__ void convAgB(double *a, double *b, double *c, int sa, int sb)
{
    int i = (threadIdx.x + blockIdx.x * blockDim.x);
    int idT = threadIdx.x;
    int out,j;

    __shared__ double c_local [512];

    c_local[idT] = c[i];

    out = (i > sa) ? sa : i + 1;
    j   = (i > sb) ? i - sb + 1 : 1;

    for(; j < out; j++)
    {    
       if(c_local[idT] > a[j] + b[i-j])
          c_local[idT] = a[j] + b[i-j]; 
    }   

    c[i] = c_local[idT];
} 

/** Older Version **/
__global__ void convAgB(double *a, double *b, double *c, int sa, int sb)
{
    int size = sa+sb;

    int idT = (threadIdx.x + blockIdx.x * blockDim.x);
    int out,j;


    for(int i = idT; i < size; i += blockDim.x * gridDim.x)
    {
        if(i > sa) out = sa;
        else out = i + 1;

        if(i > sb) j = i - sb + 1;
        else j = 1;


        for(; j < out; j++)
        {
                if(c[i] > a[j] + b[i-j])
                    c[i] = a[j] + b[i-j];
        }
    }
}

