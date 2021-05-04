package io.kaitai.struct;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * An implementation of OutputStream backed by a {@link ByteBuffer}.
 * It can be either a {@link MappedByteBuffer} backed by {@link FileChannel},
 * or a regular wrapper over a given byte array).
 */
public class ByteBufferKaitaiOutputStream extends KaitaiOutputStream {

    private FileChannel fc;
    private ByteBuffer bb;

    /**
     * Initializes a stream, writing to a local file with specified fileName.
     * Internally, FileChannel + MappedByteBuffer will be used.
     * @param fileName file to write
     * @throws IOException if file can't be written
     */
    public ByteBufferKaitaiOutputStream(String fileName) throws IOException {
        this(Paths.get(fileName));
    }

    /**
     * Initializes a stream, writing to a local file with specified fileName.
     * Internally, FileChannel + MappedByteBuffer will be used.
     * @param file file to write
     * @throws IOException if file can't be written
     */
    public ByteBufferKaitaiOutputStream(File file) throws IOException {
        this(file.getAbsolutePath());
    }

    /**
     * Initializes a stream, writing to a local file with specified fileName.
     * Internally, FileChannel + MappedByteBuffer will be used.
     * @param path file to write
     * @throws IOException if file can't be written
     */
    public ByteBufferKaitaiOutputStream(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        fc = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.TRUNCATE_EXISTING);
        bb = ByteBuffer.allocateDirect(8 * 1024 * 1024);
    }

    /**
     * Initializes a stream that will write data to a given byte array.
     * Internally, ByteBuffer wrapping given array will be used.
     * @param arr byte array to write
     */
    public ByteBufferKaitaiOutputStream(byte[] arr) {
        fc = null;
        bb = ByteBuffer.wrap(arr);
    }

    /**
     * Initializes a stream that will write data to a given ByteBuffer.
     * @param buffer ByteBuffer to write
     */
    public ByteBufferKaitaiOutputStream(ByteBuffer buffer) {
        fc = null;
        bb = buffer;
    }

    /**
     * Closes the stream safely. If there was an open file associated with it, closes that file.
     * For streams that were writing to in-memory array, does nothing.
     * <p>
     * @implNote Unfortunately, there is no simple way to close memory-mapped ByteBuffer in
     * Java and unmap underlying file. As {@link MappedByteBuffer} documentation suggests,
     * "mapped byte buffer and the file mapping that it represents remain valid until the
     * buffer itself is garbage-collected". Thus, the best we can do is to delete all
     * references to it, which breaks all subsequent <code>write..</code> methods with
     * {@link NullPointerException}. Afterwards, a call to {@link System#gc()} will
     * typically release the mmap, if garbage collection will be triggered.
     * </p>
     * <p>
     * There is a <a href="https://bugs.java.com/bugdatabase/view_bug.do?bug_id=4724038">
     * JDK-4724038 request for adding unmap method</a> filed at Java bugtracker since 2002,
     * but as of 2018, it is still unresolved.
     * </p>
     * <p>
     * A couple of unsafe approaches (such as using JNI, or using reflection to invoke JVM
     * internal APIs) have been suggested and used with some success, but these are either
     * unportable or dangerous (may crash JVM), so we're not using them in this general
     * purpose code.
     * </p>
     * <p>
     * For more examples and suggestions, see:
     * https://stackoverflow.com/questions/2972986/how-to-unmap-a-file-from-memory-mapped-using-filechannel-in-java
     * </p>
     * @throws IOException if FileChannel can't be closed
     */
    @Override
    public void close() throws IOException {
        if (fc != null) {
            fc.close();
            fc = null;
        }
        bb = null;
    }

    @Override
    public void saveAndClose() throws IOException {
        if (fc != null) {
            bb.flip();
            fc.write(bb);

            fc.close();
            fc = null;
        }
        bb = null;
    }

    //region Stream positioning

    @Override
    public boolean isEof() {
        return !(bb.hasRemaining() || bitsLeft > 0);
    }

    @Override
    public void seek(int newPos) {
        bb.position(newPos);
    }

    @Override
    public void seek(long newPos) {
        if (newPos > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Java ByteBuffer can't be seeked past Integer.MAX_VALUE");
        }
        bb.position((int) newPos);
    }

    @Override
    public int pos() {
        return bb.position();
    }

    @Override
    public long size() {
        return bb.limit();
    }

    //endregion

    //region Integer numbers

    //region Signed

    /**
     * Write one signed 1-byte value.
     */
    @Override
    public void writeS1(byte value) {
        bb.put(value);
    }

    //region Big-endian

    @Override
    public void writeS2be(short value) {
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.putShort(value);
    }

    @Override
    public void writeS4be(int value) {
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.putInt(value);
    }

    @Override
    public void writeS8be(long value) {
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.putLong(value);
    }

    //endregion

    //region Little-endian

    @Override
    public void writeS2le(short value) {
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putShort(value);
    }

    @Override
    public void writeS4le(int value) {
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putInt(value);
    }

    @Override
    public void writeS8le(long value) {
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putLong(value);
    }

    //endregion

    //endregion

    //region Unsigned

    @Override
    public void writeU1(byte value) {
        bb.put(value);
    }

    //region Big-endian

    @Override
    public void writeU2be(short value) {
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.putShort(value);
    }

    @Override
    public void writeU4be(int value) {
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.putInt(value);
    }

    //endregion

    //region Little-endian

    @Override
    public void writeU2le(short value) {
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putShort(value);
    }

    @Override
    public void writeU4le(int value) {
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putInt(value);
    }

    //endregion

    //endregion

    //endregion

    //region Floating point numbers

    //region Big-endian

    @Override
    public void writeF4be(float value) {
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.putFloat(value);
    }

    @Override
    public void writeF8be(double value) {
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.putDouble(value);
    }

    //endregion

    //region Little-endian

    @Override
    public void writeF4le(float value) {
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putFloat(value);
    }

    @Override
    public void writeF8le(double value) {
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putDouble(value);
    }

    //endregion

    //endregion

    //region Byte arrays

    /**
     * Writes designated number of bytes to the stream.
     * @param n number of bytes to write
     */
    @Override
    public void writeBytes(byte[] array, long n) {
        byte[] buf = Arrays.copyOf(array, toByteArrayLength(n));
        bb.put(buf);
    }

    /**
     * Writes all the remaining bytes in a stream as byte array.
     */
    @Override
    public void writeBytesFull(byte[] array) {
        bb.put(array);
    }

    @Override
    public void writeBytesTerm(byte[] array, int term, boolean includeTerm, boolean consumeTerm, boolean eosError) {
        ByteBuffer buf = ByteBuffer.wrap(array);
        while (true) {
            if (!buf.hasRemaining()) {
                if (eosError) {
                    throw new RuntimeException("End of stream reached, but no terminator " + term + " found");
                } else {
                    return;
                }
            }
            byte b = buf.get();
            if (b == term) {
                if (includeTerm) {
                    writeS1(b);
                }
                if (!consumeTerm) {
                    bb.position(bb.position() - 1);
                }
                return;
            }
            bb.put(b);
        }
    }

    //endregion


    public ByteBuffer getBb() {
        return bb;
    }
}
