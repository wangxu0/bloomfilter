package com.github.wxisme.bloomfilter.bitset;

/**
 * Base bit set interface. If you want to use your own data structure you can implement this interface.
 *
 * @author Xu Wang
 */
public interface BaseBitSet extends Cloneable, java.io.Serializable {

    public void set(int bitIndex);

    public void set(int bitIndex, boolean value);

    public boolean get(int bitIndex);

    public void clear(int bitIndex);

    public void clear();

    public long size();

    public boolean isEmpty();
}
