# LeapfrogLCG
A java code to generate random numbers using LCG method and Leapfrog for MapReduce jobs

## Description

LCG (_Linear Congruential Generator_) is a method of generating random numbers. 
The method is based on multiplication.

```
X_n = a * X_{n-1} + c (mod m)
```

For more information checkout [Wikipedia](https://en.wikipedia.org/wiki/Linear_congruential_generator) page.

**LeapFrog**[1] is the parallel version of LCG using partitioning method. 
Ideally we are looking for the following sequence for processor `p` having 
`N` processors:

```X_P,X_{P+N},X_{P+2N},...```

To achieve this matter, we use the following LCG, 

```X_{P+N} = (a^N) * X_P + (a^N-1) * c/(a-1) (mod m)```

In order to get the full period length $m$ the following conditions are
necessary [2] and sufficient [3]:
\begin{itemize}
    \item $c\ne0$
    \item $m$ and $c$ are relatively prime
    \item $a-1$ is divisible by all prime factors of $m$
    \item $a-1$ is divisible by 4 if $m$ is divisible by 4
\end{itemize}


[1] Bowman, K. O., & Robinson, M. T. (1987). Studies of random number generators for 
parallel processing. In Proc. Second Conference on Hypercube Multiprocessors 
(pp. 445-453). SIAM, Philadelphia.

[2] Greenberger, M. (1961). Notes on a new pseudo-random number generator. 
Journal of the ACM (JACM), 8(2), 163-167.

[3] Hull, T. E., \& Dobell, A. R. (1962). Random number generators. SIAM review, 
4(3), 230-254.

