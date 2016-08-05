package com.redisbloom.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * 操作Redis的工具类
 * @author wangxu
 *         blog:http://www.cnblogs.com/wxisme/
 *         email:wxu1994@163.com
 * @date 2016/8/4
 */
public class RedisUtils {

    /**
     * 根据传入的主机和端口参数来创建并返回JedisCluster对象
     * @param hostAndPorts
     * @return JedisCluster
     */
    public static JedisCluster getJedisCluster(HostAndPort ... hostAndPorts) {
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        for (HostAndPort hostAndPort : hostAndPorts) {
            jedisClusterNodes.add(hostAndPort);
        }
        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, 10000000);
        return jedisCluster;
    }

    /**
     * 获取一个Jedis对象
     * @param host
     * @return Jedis
     */
    public static Jedis getJedis(String host) {
        if (host != null && "".equals(host)) {
            Jedis j = new Jedis(host);
            return new Jedis(host);
        }
        return null;
    }

}
