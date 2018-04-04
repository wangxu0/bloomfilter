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

import java.util.BitSet;

/**
 * Implement bloom filter on native java bitset.
 */
public class JavaBitSet implements BaseBitSet {

    private BitSet bitSet;

    public JavaBitSet() {
        this.bitSet = new BitSet();
    }

    public JavaBitSet(BitSet bitSet) {
        if (bitSet == null) {
            this.bitSet = new BitSet();
        } else {
            this.bitSet = bitSet;
        }
    }

    public void set(int bitIndex) {
        this.bitSet.set(bitIndex);
    }

    public void set(int bitIndex, boolean value) {
        this.bitSet.set(bitIndex, value);
    }

    public boolean get(int bitIndex) {
        return this.bitSet.get(bitIndex);
    }

    public void clear(int bitIndex) {
        this.bitSet.clear(bitIndex);
    }

    public void clear() {
        this.bitSet.clear();
    }

    public long size() {
        return this.bitSet.size();
    }

    public boolean isEmpty() {
        return this.isEmpty();
    }
}
