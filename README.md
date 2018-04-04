# bloomfilter

An empty Bloom filter is a bit array of m bits, all set to 0. There must also be hashNumber different hash functions defined, each of which maps or hashes some set element to one of the m array positions, generating a uniform random distribution. Typically, hashNumber is a constant, much smaller than m, which is proportional to the number of elements to be added; the precise choice of hashNumber and the constant of proportionality of m are determined by the intended false positive rate of the filter.
If you are not familiar with it, you can refer to [Wikipedia's detailed analysis](https://en.wikipedia.org/wiki/Bloom_filter).

## Overview

This project implements bloomfilter and can select and extend different BitSets or other storage methods. For example, BitSet supports Java and Redis. It can also be applied to scenarios where various data levels and different false positive rates are required.

## Quick Start

### Download & Install

The dependency currently uploaded to the maven central repository are still being reviewed. Soon you can omit this step and add dependency directly.

```
git clone https://github.com/wxisme/bloomfilter.git

cd bloomfilter

mvn clean install
```

### Add maven dependency

```xml

<dependency>
    <groupId>com.github.wxisme</groupId>
    <artifactId>bloomfilter</artifactId>
    <version>1.0.0</version>
</dependency>

```

### Test

```java
public class JavaBitSetTest {

    public static void main(String[] args) {
        //(falsePositiveProbability, expectedNumberOfElements)
        BloomFilter<String> filter = new BloomFilter<String>(0.0001, 10000);
        filter.bind(new JavaBitSet());

        filter.add("filter");
        System.out.println(filter.contains("filter"));
        System.out.println(filter.contains("bloom"));
        filter.add("bitset");
        filter.add("redis");
        System.out.println(filter.contains("bitset"));
        System.out.println(filter.contains("redis"));
        System.out.println(filter.contains("mysql"));
        System.out.println(filter.contains("linux"));
        System.out.println(filter.count());
        System.out.println(filter.isEmpty());
        filter.clear();
        System.out.println(filter.isEmpty());
        System.out.println(filter.contains("filter"));

    }

}
```
Expected test result is :</br>
```
true
false
true
true
false
false
3
false
true
false
```

### License

Bloomfilter is released under the [GNU Lesser General Public License v3.0](http://www.gnu.org/licenses/).</br>
You may copy this code directly into your project if you leave the LGPL-comment in place and reference the following hyperlink:</br>
https://github.com/MagnusS/Java-BloomFilter</br>
https://github.com/wxisme/bloomfilter</br>
