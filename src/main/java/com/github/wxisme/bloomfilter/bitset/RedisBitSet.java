package com.github.wxisme.bloomfilter.bitset;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

/**
 * Implement bloom filter on redis bitset.
 */
public class RedisBitSet implements BaseBitSet {

    private JedisCluster jedisCluster;
    private Jedis jedis;
    private String name;

    private boolean isCluster = true;

    public RedisBitSet() {
    }

    public RedisBitSet(JedisCluster jedisCluster, String name) {
        this.jedisCluster = jedisCluster;
        this.name = name;
        this.isCluster = true;
    }

    public RedisBitSet(Jedis jedis, String name) {
        this.jedis = jedis;
        this.name = name;
        this.isCluster = false;
    }


    public void set(int bitIndex) {
        if (this.isCluster) {
            this.jedisCluster.setbit(this.name, bitIndex, true);
        } else {
            this.jedis.setbit(this.name, bitIndex, true);
        }
    }

    public void set(int bitIndex, boolean value) {
        if (this.isCluster) {
            this.jedisCluster.setbit(this.name, bitIndex, value);
        } else {
            this.jedis.setbit(this.name, bitIndex, value);
        }
    }

    public boolean get(int bitIndex) {
        if (this.isCluster) {
            return this.jedisCluster.getbit(this.name, bitIndex);
        } else {
            return this.jedis.getbit(this.name, bitIndex);
        }
    }

    public void clear(int bitIndex) {
        if (this.isCluster) {
            this.jedisCluster.setbit(this.name, bitIndex, false);
        } else {
            this.jedis.setbit(this.name, bitIndex, false);
        }
    }

    public void clear() {
        if (this.isCluster) {
            this.jedisCluster.del(this.name);
        } else {
            this.jedis.del(this.name);
        }
    }

    public long size() {
        if (this.isCluster) {
            return this.jedisCluster.bitcount(this.name);
        } else {
            return this.jedis.bitcount(this.name);
        }
    }

    public boolean isEmpty() {
        return size() <= 0;
    }
}
