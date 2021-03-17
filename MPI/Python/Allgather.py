# Original Question : https://stackoverflow.com/questions/66670228

# I am trying to communicate between processes so that every processes are notified 
# when all other processes are ready. 
# The code snippet below does that. Is there a more elegant way to do this?

# def get_all_ready_status(ready_batch):
#      all_ready= all(ready_batch)
#      return [all_ready for _ in ready_batch]

# ready_batch= comm.gather(ready_agent, root=0)
#  if rank == 0:
#     all_ready_batch = get_all_ready_status(ready_batch)
#  all_ready_flag = comm.scatter(all_ready_batch , root=0)   


from mpi4py import MPI
import numpy


comm = MPI.COMM_WORLD
size = comm.Get_size()
rank = comm.Get_rank()

sendBuffer = numpy.ones(1, dtype=bool)
recvBuffer = numpy.zeros(size, dtype=bool)

print("Before Allgather => Process %s | sendBuffer %s | recvBuffer %s" % (rank, sendBuffer, recvBuffer))
comm.Allgather([sendBuffer,  MPI.BOOL],[recvBuffer, MPI.BOOL])
print("After Allgather  => Process %s | sendBuffer %s | recvBuffer %s" % (rank, sendBuffer, recvBuffer))
