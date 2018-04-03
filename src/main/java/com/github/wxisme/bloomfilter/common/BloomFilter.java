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

package com.github.wxisme.bloomfilter.common;

import com.github.wxisme.bloomfilter.bitset.BaseBitSet;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

/**
 * This program refers to the java-bloomfilter,you can get its details form https://github.com/MagnusS/Java-BloomFilter
 * @param <E> Element type
 */
public class BloomFilter<E> implements Cloneable, Serializable {

    private BaseBitSet bitSet;

    public void bind(BaseBitSet bitset) {
        this.bitSet = bitset;
    }

    private int expectedElementNumber;
    private int elementNumber;
    private int hashNumber;
    private int size;
    private double bitNumber;


    public BloomFilter(double bitNumber, int expectedElementNumber, int hashNumber) {
        this.expectedElementNumber = expectedElementNumber;
        this.hashNumber = hashNumber;
        this.bitNumber = bitNumber;
        this.size = (int) Math.ceil(bitNumber * expectedElementNumber);
        elementNumber = 0;
    }


    public BloomFilter(int size, int expectedElementNumber) {
        this(size / (double) expectedElementNumber,
                expectedElementNumber, (int) Math.round((size / (double) expectedElementNumber) * Math.log(2)));
    }


    public BloomFilter(double misjudgmentProbability, int expectedElementNumber) {
        this(Math.ceil(-(Math.log(misjudgmentProbability) / Math.log(2))) / Math.log(2),
                expectedElementNumber, (int) Math.ceil(-(Math.log(misjudgmentProbability) / Math.log(2))));
    }


    public void add(E element) {
        try {
            add(element.toString().getBytes(CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public void add(byte[] bytes) {
        int[] hashes = MessageDigestUtils.createHashes(bytes, hashNumber);
        for (int hash : hashes)
            bitSet.set(Math.abs(hash % size), true);
        elementNumber++;
    }


    public void addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
        }
    }


    public boolean contains(E element) {
        boolean ret = false;
        try {
            ret = contains(element.toString().getBytes(CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }


    public boolean contains(byte[] bytes) {
        int[] hashes = MessageDigestUtils.createHashes(bytes, hashNumber);
        for (int hash : hashes) {
            if (!bitSet.get(Math.abs(hash % size))) {
                return false;
            }
        }
        return true;
    }

    public boolean containsAll(Collection<? extends E> c) {
        for (E element : c)
            if (!contains(element))
                return false;
        return true;
    }


    public boolean getBit(int bitIndex) {
        return bitSet.get(bitIndex);
    }


    public void setBit(int bitIndex, boolean value) {
        bitSet.set(bitIndex, value);
    }


    public BaseBitSet getBitSet() {
        return bitSet;
    }


    public long size() {
        return this.bitSet.size();
    }


    public int count() {
        return this.elementNumber;
    }

    public void clear() {
        bitSet.clear();
        elementNumber = 0;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final BloomFilter<E> other = (BloomFilter<E>) obj;
        if (this.expectedElementNumber != other.expectedElementNumber)
            return false;
        if (this.hashNumber != other.hashNumber)
            return false;
        if (this.size != other.size)
            return false;
        if (this.bitSet != other.bitSet && (this.bitSet == null || !this.bitSet.equals(other.bitSet)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.bitSet != null ? this.bitSet.hashCode() : 0);
        hash = 61 * hash + this.expectedElementNumber;
        hash = 61 * hash + this.size;
        hash = 61 * hash + this.hashNumber;
        return hash;
    }

    private static final String CHARSET = "UTF-8";

}