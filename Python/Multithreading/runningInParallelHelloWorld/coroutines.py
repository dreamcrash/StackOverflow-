import asyncio


async def f1():
    print('Hello')
    await asyncio.sleep(1)


async def f2():
    print('World')
    await asyncio.sleep(1)

asyncio.run(f1())
asyncio.run(f2())
