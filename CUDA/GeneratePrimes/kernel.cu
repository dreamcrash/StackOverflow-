/*** Original Question : https://stackoverflow.com/questions/13215614/

I am new to CUDA. I am trying to parallelize the following code. Right now it's sitting on kernel but is not using threads at all, thus slow. I tried to use this answer but to no avail so far.

The kernel is supposed to generate first n prime numbers, put them into device_primes array and this array is later accessed from host. The code is correct and works fine in serial version but I need to speed it up, perhaps with use of shared memory.

//CUDA kernel code
__global__ void generatePrimes(int* device_primes, int n) 
{
//int i = blockIdx.x * blockDim.x + threadIdx.x;
//int j = blockIdx.y * blockDim.y + threadIdx.y;

int counter = 0;
int c = 0;

for (int num = 2; counter < n; num++)
{       
    for (c = 2; c <= num - 1; c++)
    { 
        if (num % c == 0) //not prime
        {
            break;
        }
    }
    if (c == num) //prime
    {
        device_primes[counter] = num;
        counter++;
    }
}
}
My current, preliminary, and definitely wrong attempt to parallelize this looks like the following:

//CUDA kernel code
__global__ void generatePrimes(int* device_primes, int n) 
{
    int i = blockIdx.x * blockDim.x + threadIdx.x;
    int j = blockIdx.y * blockDim.y + threadIdx.y;
    int num = i + 2; 
    int c = j + 2;
    int counter = 0;

    if ((counter >= n) || (c > num - 1))
    {
        return;
    }
    if (num % c == 0) //not prime
    {
    
    }
    if (c == num) //prime
    {
       device_primes[counter] = num;
       counter++;
    }
    num++;
    c++;
}
But this code populates the array with data that does not make sense. In addition, many values are zeroes. Thanks in advance for any help, it's appreciated.

***/


__global__ void getPrimes(int *device_primes,int n)
{ 
    int c = 0;
    int thread_id = blockIdx.x * blockDim.x + threadIdx.x;
    int num = thread_id;

    if (thread_id == 0) device_primes[0] = 1;
    __syncthreads();

    while(device_primes[0] < n)
    {

        for (c = 2; c <= num - 1; c++)
        { 
            if (num % c == 0) //not prime
            {
                break;
            }
        }

        if (c == num) //prime
        {
            int pos = atomicAdd(&device_primes[0],1);
            device_primes[pos] = num;

        }

        num += blockDim.x * gridDim.x; // Next number for this thread       
    }
}
