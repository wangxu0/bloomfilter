# bloomfilter

An empty Bloom filter is a bit array of m bits, all set to 0. There must also be k different hash functions defined, each of which maps or hashes some set element to one of the m array positions, generating a uniform random distribution. Typically, k is a constant, much smaller than m, which is proportional to the number of elements to be added; the precise choice of k and the constant of proportionality of m are determined by the intended false positive rate of the filter.
If you are not familiar with it, you can refer to [Wikipedia's detailed analysis](https://en.wikipedia.org/wiki/Bloom_filter).

## Overview

This project implements bloomfilter and can select and extend different BitSets or other storage methods. For example, BitSet supports Java and Redis. It can also be applied to scenarios where various data levels and different error rates are required.

## Document

