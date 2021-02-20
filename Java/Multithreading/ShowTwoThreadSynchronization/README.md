Original question : https://stackoverflow.com/questions/65166368/

I've been looking about multi threading tutorials and those specific about synchronization, 
but I haven't been able to implement something that I need.

It's a showcase of a synchronization happening in my program.

Basically, I have one class that inherits some functions from other class, 
those functions needs to be synchronized in order for both of threads doesn't modify an object at the same time (no data corruption).

I previously implemented the code without the synchronize keyword, 
so I could manage to see the data corruption occurring