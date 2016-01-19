# Introduction
Sorting big files that do not fit in memory drives us to use exeternal sorting methods that some common and widely used utilities - such as [Unix sort](http://man7.org/linux/man-pages/man1/sort.1.html) - have.

>" External sorting is a term for a class of sorting algorithms that can handle massive amounts of data." (Source: [Wikipedia](https://en.wikipedia.org/wiki/External_sorting))

So, basically, we are using a "divide and conquer" strategy, sorting small chunks of the input and storing them into the disk. After that, we can read the first N bytes of each chunk and merge them into the output file. A high level diagram of this strategy is available [here](https://www.cl.cam.ac.uk/teaching/0910/IbAsExBrfg/figures/merges-small.png). Example:
```
chunk-1: [a, b, c, k, z]
chunk-2: [d, e, f]
```
Having these two ordered chunks, we should now select, comparing them, what is the order to write them into the output file. In this case, viewing the algorithm in high-level, we should have something like this:
```
1. a < d? Write `a` and advance in `chunk-1`
2. b < d? Write `b` and advance in `chunk-1`
3. c < d? Write `c` and advance in `chunk-1`
4. k < d? Write `d` and advance in `chunk-2`
5. k < e? Write `e` and advance in `chunk-2`
6. k < f? Write `f` and advance in `chunk-2`
7. Write `k`and advance in `chunk-1`
8. Write `z`and advance in `chunk-1``
9. Close the output file
```

# About the solution
The solution to this problem is written in Java8 and does not use any external library. The program has a ``Main``class that takes two arguments: the first one is the _input_ file and the second one is the file where the _output_ should be written. The _architeture_ of the solution is the following:

+ ``ExternalSort``=> just a wrapper for the two steps (divide and conquer) of the algorithm;
+ ``FileSplitter``=> splits a given _input_ file, sorts its contents in N chunks and writes them into disk;
+ ``SortedFileBuffer``=> utility data structure that has the list of _chunks_ that are opened, a relationship between these buffers and the last line that was read from them and a set of buffers that were completely read;
+ ``FileMerger``=> Using the above class, selects the entry to write to the _output_ buffer, from the list of open and sorted buffers, based on alphabetic comparation.

# Test
There is a file ``test.txt``in the root directory of the project. It's a blacklist from somewhere on the Internet. It has some lines that are not sorted, for test and demonstration purposes.

**NOTE**: this implementation should **not** be used in production. There are, certainly, a some optimizations and corrections to do. This is just a proof-of-concept.