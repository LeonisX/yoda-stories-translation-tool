/*
 * Copyright 2015-2017 Kaitai Project: MIT license
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.kaitai.struct;

/**
 * Common base class for all structured generated by Kaitai Struct.
 * Stores stream object that this object was parsed from in {@link #io}.
 */
public abstract class KaitaiStruct {
    /**
     * Stream object that this KaitaiStruct-based structure was parsed from.
     */
    protected transient int pos;
    protected transient KaitaiInputStream io;
    protected transient KaitaiStruct parent;

    public KaitaiStruct(KaitaiInputStream io) {
        this.io = io;
        if (io != null) {
            this.pos = io.pos();
        } else {
            this.pos = 0;
        }
    }

    public KaitaiInputStream getIo() { return io; }
    public KaitaiStruct getParent() { return parent; }

    public abstract void write(KaitaiOutputStream os);
}
