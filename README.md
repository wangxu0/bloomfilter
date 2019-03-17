# bloomfilter
[![Build Status](https://travis-ci.org/wxisme/bloomfilter.svg?branch=master)](https://travis-ci.org/wxisme/bloomfilter) ![GitHub release](https://img.shields.io/github/release/wxisme/bloomfilter.svg) ![GitHub license](https://img.shields.io/badge/license-LGPL--3.0-brightgreen.svg)

An empty Bloom filter is a bit array of m bits, all set to 0. There must also be hashNumber different hash functions defined, each of which maps or hashes some set element to one of the m array positions, generating a uniform random distribution. Typically, hashNumber is a constant, much smaller than m, which is proportional to the number of elements to be added; the precise choice of hashNumber and the constant of proportionality of m are determined by the intended false positive probability of the filter.</br>
If you are not familiar with it, you can refer to [Wikipedia's detailed analysis](https://en.wikipedia.org/wiki/Bloom_filter).</br>
If you use python, you can refer to the [bloomfilter written in python](https://github.com/wxisme/py-bloomfilter)

## Overview

This project implements bloomfilter and can select and extend different BitSets or other storage methods. For example, BitSet supports `Java` and `Redis`. It can also be applied to scenarios where various data levels and different false positive probability are required.</br>

Release Log:</br>
bloomfilter version 1.0.0 Released</br>

[Download](https://github.com/wxisme/bloomfilter/releases) source code and binary jar package on the [release page](https://github.com/wxisme/bloomfilter/releases).</br>


## Quick Start

### ~~Download & Install~~

This is not necessary now. The dependency uploaded to the maven central repository.</br>

```
git clone https://github.com/wxisme/bloomfilter.git

cd bloomfilter

mvn clean install
```

You can also download source code or binary jar package on the [release page](https://github.com/wxisme/bloomfilter/releases) directly.</br>

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

### Redis and other extensions


#### Redis

You can easily use bloomfilter on redis,here is a simple example:

```java
public class RedisBitSetTest {

    public static void main(String[] args) {

        //Don't forget auth password, you better use the configured redis client connection.
        //It should be noted that bloomfilter is not responsible for closing and returning redis connection resources.

        //(falsePositiveProbability, expectedNumberOfElements)
        BloomFilter<String> filter = new BloomFilter<String>(0.0001, 10000);
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("1234");
        filter.bind(new RedisBitSet(jedis, "bloomfilter:key:name"));

        //if you have a redis cluster
        //Set<HostAndPort> nodes = new HashSet<>();
        //nodes.add(new HostAndPort("127.0.0.1", 6379));

        //filter.bind(new RedisBitSet(new JedisCluster(nodes), "bloomfilter:key:name"));

        //you can also use jedispool
        //JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        //Jedis jedis = jedisPool.getResource();
        //filter.bind(new RedisBitSet(jedis, "bloomfilter:key:name"));

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
Don't forget auth password, you better use the configured redis client connection. It should be noted that bloomfilter is not responsible for closing and returning redis connection resources.</br>
If you are not familiar with redis, you can refer to the following hyperlink：</br>
https://redis.io</br>
https://github.com/xetorthio/jedis</br>

Test result is :</br>
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
#### Extensions

If you want to use your own data structure, you can directly implement the `BaseBitSet` interface.</br>
Example:</br>

```java
public class YourBitSet implements BaseBitSet {

    private int[] data;//boolean array

    public YourBitSet(int size) {
        data = new int[size];
    }

    @Override
    public void set(int bitIndex) {
        data[bitIndex] = 1;
    }

    @Override
    public void set(int bitIndex, boolean value) {
        if (value)
            data[bitIndex] = 1;
        else data[bitIndex] = 0;
    }

    @Override
    public boolean get(int bitIndex) {
        return data[bitIndex] == 1;
    }

    @Override
    public void clear(int bitIndex) {
        data[bitIndex] = 0;
    }

    @Override
    public void clear() {
        Arrays.fill(data, 0);
    }

    @Override
    public long size() {
        long size = 0;
        for (int d : data)
            if (d == 1)
                size++;
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() <= 0;
    }
}
```
Test:</br>
```java
public class YourBitSetTest {

    public static void main(String[] args) {
        //(falsePositiveProbability, expectedNumberOfElements)
        BloomFilter<String> filter = new BloomFilter<String>(0.0001, 10000);
        filter.bind(new YourBitSet(1000000));

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
Test result is :</br>
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

#### Welcome to submit your extension code or put any question issues on github.

### License

Bloomfilter is released under the [GNU Lesser General Public License v3.0](http://www.gnu.org/licenses/).</br>
You can just use maven to import dependency package or use your own compiled jar package.</br>
You may copy this code directly into your project if you leave the LGPL-comment in place and reference the following hyperlink:</br>
https://github.com/MagnusS/Java-BloomFilter</br>
https://github.com/wxisme/bloomfilter</br>
