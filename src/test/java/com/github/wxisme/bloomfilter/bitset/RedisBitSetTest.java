package com.github.wxisme.bloomfilter.bitset;

import com.github.wxisme.bloomfilter.common.BloomFilter;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

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

        /**
         Test results:
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
         */
    }
}
