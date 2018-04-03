/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.wxisme.bloomfilter.bitset;

/**
 * Base bit set interface. If you want to use your own data structure you can implement this interface.
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
