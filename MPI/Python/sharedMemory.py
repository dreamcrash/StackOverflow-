# Original source https://groups.google.com/g/mpi4py/c/Fme1n9niNwQ/m/lk3VJ54WAQAJ

from mpi4py import MPI 
import numpy as np 

comm = MPI.COMM_WORLD 

size = 1000 
itemsize = MPI.DOUBLE.Get_size() 
if comm.Get_rank() == 0: 
   nbytes = size * itemsize 
else: 
   nbytes = 0 

win = MPI.Win.Allocate_shared(nbytes, itemsize, comm=comm) 

buf, itemsize = win.Shared_query(0) 
assert itemsize == MPI.DOUBLE.Get_size() 
buf = np.array(buf, dtype='B', copy=False) 
ary = np.ndarray(buffer=buf, dtype='d', shape=(size,)) 

if comm.rank == 1: 
  ary[:5] = np.arange(5) 
 
comm.Barrier() 
if comm.rank == 0: 
  print(ary[:10])

