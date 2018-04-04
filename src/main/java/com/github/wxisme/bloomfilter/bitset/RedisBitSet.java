/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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

    private RedisBitSet() {
    }

    /**
     * Create a redis bitset.
     * @param jedisCluster jedis cluster client.
     * @param name the redis bit key name.
     */
    public RedisBitSet(JedisCluster jedisCluster, String name) {
        this.jedisCluster = jedisCluster;
        this.name = name;
        this.isCluster = true;
    }

    /**
     * Create a redis bitset.
     * @param jedis jedis client.
     * @param name the redis bit key name.
     */
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
