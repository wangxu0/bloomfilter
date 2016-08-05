package com.redisbloom.redis;

import redis.clients.jedis.JedisCluster;

/**
 * 自定义BitSet
 * @author wangxu
 *         blog:http://www.cnblogs.com/wxisme/
 *         email:wxu1994@163.com
 * @date 2016/8/4
 */
public class RedisBitSet {

    private JedisCluster jedisCluster;
    private String name;

    public RedisBitSet() {}

    public RedisBitSet(JedisCluster jedisCluster, String name) {
        this.jedisCluster = jedisCluster;
        this.name = name;
    }

    public void set(int bitIndex) {
        this.jedisCluster.setbit(this.name, bitIndex, true);
    }

    public void set(int bitIndex, boolean value) {
        this.jedisCluster.setbit(this.name, bitIndex, value);
    }

    public boolean get(int bitIndex) {
        return this.jedisCluster.getbit(this.name, bitIndex);
    }

    public void clear(int bitIndex) {
        this.jedisCluster.setbit(this.name, bitIndex, false);
    }

    public void clear() {
        this.jedisCluster.del(this.name);
    }

    public long size() {
        return this.jedisCluster.bitcount(this.name);
    }

    public boolean isEmpty() {
        return size()<=0;
    }
}
