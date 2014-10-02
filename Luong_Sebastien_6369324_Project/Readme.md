Final project description: 

Description: 

The final project focuses on the
design of a virtual memory management
subsystem. Simply put, the idea is to build a
paging mechanism that simulates a multi-
tasking OS that uses limited physical memory
to run processes of variable size.
You will do this by creating three cooperating components:
1. Memory Manager. A module that maps process pages to possibly non-contiguous physical
memory frames.
2. Virtualization Manager. A module that transparently extends the process space to allow
running processes to exist partly in physical memory and partly on disk.
3. Swap Manager. A module that manages the swap space located on the backing store (i.e., disk),
thereby supporting the demand swapping of the virtualization manager.

We will discuss each of these below (omitted for brevity's sake)