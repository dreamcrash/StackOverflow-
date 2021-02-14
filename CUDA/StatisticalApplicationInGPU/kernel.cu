/** Original Question : https://stackoverflow.com/questions/13301309/

I'm working on a statistical application containing approximately 10 - 30 million floating point values in an array.

Several methods performing different, but independent, calculations on the array in nested loops, for example:

Dictionary<float, int> noOfNumbers = new Dictionary<float, int>();

for (float x = 0f; x < 100f; x += 0.0001f) {
    int noOfOccurrences = 0;

    foreach (float y in largeFloatingPointArray) {
        if (x == y) {
            noOfOccurrences++;
        }
    }

    noOfNumbers.Add(x, noOfOccurrences);
}
The current application is written in C#, runs on an Intel CPU and needs several hours to complete. I have no knowledge of GPU programming concepts and APIs, so my questions are:

Is it possible (and does it make sense) to utilize a GPU to speed up such calculations?
If yes: Does anyone know any tutorial or got any sample code (programming language doesn't matter)?

**/

__global__ void hash (float *largeFloatingPointArray,int largeFloatingPointArraySize, int *dictionary, int size, int num_blocks)
{
    int x = (threadIdx.x + blockIdx.x * blockDim.x); // Each thread of each block will
    float y;                                         // compute one (or more) floats
    int noOfOccurrences = 0;
    int a;
    
    while( x < size )            // While there is work to do each thread will:
    {
        dictionary[x] = 0;       // Initialize the position in each it will work
        noOfOccurrences = 0;    

        for(int j = 0 ;j < largeFloatingPointArraySize; j ++) // Search for floats
        {                                                     // that are equal 
                                                             // to it assign float
           y = largeFloatingPointArray[j];  // Take a candidate from the floats array 
           y *= 10000;                      // e.g if y = 0.0001f;
           a = y + 0.5;                     // a = 1 + 0.5 = 1;
           if (a == x) noOfOccurrences++;    
        }                                      
                                                    
        dictionary[x] += noOfOccurrences; // Update in the dictionary 
                                          // the number of times that the float appears 

    x += blockDim.x * gridDim.x;  // Update the position here the thread will work
    }
}
