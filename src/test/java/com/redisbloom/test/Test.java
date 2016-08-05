package com.redisbloom.test;

import com.redisbloom.bloom.BloomFilter;
import com.redisbloom.redis.RedisUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.*;

/**
 * 测试
 * @author wangxu
 *         blog:http://www.cnblogs.com/wxisme/
 *         email:wxu1994@163.com
 * @date 2016/8/4
 */
public class Test {
    public final static int W = 10000;
    public static void main(String[] args) throws IOException {
        int dataCnt = 173070;
        int judgeCnt = dataCnt;
        double errorCnt = 0;

        JedisCluster jedisCluster = RedisUtils.getJedisCluster(
                new HostAndPort("192.168.2.241", 16389),
                new HostAndPort("192.168.2.242", 16389),
                new HostAndPort("192.168.2.243", 16389),
                new HostAndPort("192.168.2.245", 16389),
                new HostAndPort("192.168.2.246", 16389)
        );


        BloomFilter<String> bloomFilter = new BloomFilter<String>(0.000001, (int)(173070*1.5));
        bloomFilter.bind(jedisCluster, "redisbitname");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/root/data/data.log"), "UTF-8"));
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(new FileInputStream("/root/data/data2.log"), "UTF-8"));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/root/data/ret.log"), "UTF-8"));
        String line = null;
        while ((line = reader.readLine()) !=null) {
            bloomFilter.add(line);
        }
        long startTime = System.nanoTime();
        while ((line = reader2.readLine()) != null) {
            if (bloomFilter.contains(line)) {
                errorCnt ++;
            }
        }
        reader.close();
        reader2.close();
        long consumTime = System.nanoTime()-startTime;
        String result = "初始化：" + dataCnt*1 + "\n" + "插入数据：" + dataCnt + "\n" + "查询数据：" + judgeCnt + "\n"
                + "耗时：" + consumTime/judgeCnt + "ms" + "\n" + "内存：" + bloomFilter.getBitSet().size()/8/1024 + "KB" + "\n" + "失误：" + errorCnt;
        writer.write(result);
        writer.close();
        System.out.println("初始化：" + dataCnt*1);
        System.out.println("插入数据：" + dataCnt);
        System.out.println("查询数据：" + judgeCnt);
        System.out.println("耗时：" + consumTime/judgeCnt + "ns");
        System.out.println("内存：" + bloomFilter.getBitSet().size()/8/1024 + "KB");
        System.out.printf("失误率：%.2f%%\n", (errorCnt/judgeCnt)*100);
    }

}
