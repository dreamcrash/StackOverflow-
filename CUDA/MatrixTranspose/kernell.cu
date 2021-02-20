/** Original Question : https://stackoverflow.com/questions/13782012

How to transpose a matrix in CUDA/cublas?

Say I have a matrix with a dimension of A*B on GPU, where B (number of columns) 
is the leading dimension assuming a C style. Is there any method in CUDA (or cublas) 
to transpose this matrix to FORTRAN style, where A (number of rows) becomes the leading dimension?

It is even better if it could be transposed during host->device transfer while keep the original data unchanged.



**/


__global__ void transposeNaive(float *odata, float* idata,
int width, int height, int nreps)
{
    int xIndex = blockIdx.x*TILE_DIM + threadIdx.x;
    int yIndex = blockIdx.y*TILE_DIM + threadIdx.y;
    int index_in = xIndex + width * yIndex;
    int index_out = yIndex + height * xIndex;

    for (int r=0; r < nreps; r++)
    {
        for (int i=0; i<TILE_DIM; i+=BLOCK_ROWS)
        {
          odata[index_out+i] = idata[index_in+i*width];
        }
    }
}

