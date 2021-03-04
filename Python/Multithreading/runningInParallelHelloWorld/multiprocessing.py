from multiprocessing import Process
from time import sleep


def f1():
    print('Hello ')
    sleep(1)


def f2():
    print('World')
    sleep(1)


p1 = Process(target=f1)
p1.start()
p2 = Process(target=f2)
p2.start()
p1.join()
p2.join()
