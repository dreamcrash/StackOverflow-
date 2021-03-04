from time import sleep


def f1():
    print('Hello ')
    sleep(1)


def f2():
    print('World')
    sleep(1)


Thread(target=f1).start()
Thread(target=f2).start()
