# Original Question : https://stackoverflow.com/questions/66703153/
#
# How can we update one global dictionary in MPI (specifically mpi4py) 
# across different processors. The issue that I am encountering now after broadcasting 
# is that different processors cannot see the changes (update) on the dictionary by the other processors.


from mpi4py import MPI
import numpy


comm = MPI.COMM_WORLD
size = comm.Get_size()
rank = comm.Get_rank()

if rank == 0:
    dictionary = {'a': 1, 'c': 3}
    for i in range(1, size, 1):
        data = comm.recv(source=i, tag=11)
        for key in data:
            if key in dictionary:
               dictionary[key] = dictionary[key] + data[key]
            else:
               dictionary[key] = data[key] 
    print(dictionary)
else:
    data = {'a': 1, 'b': 2, 'c': 1}
    comm.send(data, dest=0, tag=11)

