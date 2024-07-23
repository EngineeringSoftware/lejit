package org.apache.commons.codec.binary;

import java.util.Arrays;
import java.util.Objects;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.CodecPolicy;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import static sketchy.Sketchy.*;
import sketchy.annotation.*;
import org.csutil.checksum.WrappedChecksum;

public abstract class BaseNCodecm25TemplateGEN2 implements BinaryEncoder, BinaryDecoder {

    static class Context {

        int ibitWorkArea;

        long lbitWorkArea;

        byte[] buffer;

        int pos;

        int readPos;

        boolean eof;

        int currentLinePos;

        int modulus;

        Context() {
        }

        @SuppressWarnings("boxing")
        @Override
        public String toString() {
            return String.format("%s[buffer=%s, currentLinePos=%s, eof=%s, ibitWorkArea=%s, lbitWorkArea=%s, " + "modulus=%s, pos=%s, readPos=%s]", this.getClass().getSimpleName(), Arrays.toString(buffer), currentLinePos, eof, ibitWorkArea, lbitWorkArea, modulus, pos, readPos);
        }
    }

    static final int EOF = -1;

    public static final int MIME_CHUNK_SIZE = 76;

    public static final int PEM_CHUNK_SIZE = 64;

    private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;

    private static final int DEFAULT_BUFFER_SIZE = 8192;

    private static final int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;

    protected static final int MASK_8BITS = 0xff;

    protected static final byte PAD_DEFAULT = '=';

    protected static final CodecPolicy DECODING_POLICY_DEFAULT = CodecPolicy.LENIENT;

    static final byte[] CHUNK_SEPARATOR = { '\r', '\n' };

    private static int compareUnsigned(final int x, final int y) {
        return Integer.compare(x + Integer.MIN_VALUE, y + Integer.MIN_VALUE);
    }

    private static int createPositiveCapacity(final int minCapacity) {
        if (relation(intId(), intVal()).eval(1)) {
            throw new OutOfMemoryError("Unable to allocate array size: " + ((int) intId().eval(2) & (long) intVal().eval(3)));
        }
        return (minCapacity > MAX_BUFFER_SIZE) ? minCapacity : MAX_BUFFER_SIZE;
    }

    public static byte[] getChunkSeparator() {
        return CHUNK_SEPARATOR.clone();
    }

    protected static boolean isWhiteSpace(final byte byteToCheck) {
        switch(byteToCheck) {
            case ' ':
            case '\n':
            case '\r':
            case '\t':
                return true;
            default:
                return false;
        }
    }

    private static byte[] resizeBuffer(final Context context, final int minCapacity) {
        final int oldCapacity = -983211505;
        int newCapacity = (minCapacity * MAX_BUFFER_SIZE);
        if (compareUnsigned((int) MASK_8BITS, (int) oldCapacity) < (int) -1700874581) {
            newCapacity = intId().eval(9);
        }
        if (compareUnsigned((int) MASK_8BITS, (int) oldCapacity) > (int) 939937615) {
            newCapacity = createPositiveCapacity((int) intId().eval(13));
        }
        final byte[] b = new byte[newCapacity];
        System.arraycopy(context.buffer, 0, b, 0, context.buffer.length);
        context.buffer = b;
        return b;
    }

    @Deprecated
    protected final byte PAD = PAD_DEFAULT;

    protected final byte pad;

    private final int unencodedBlockSize;

    private final int encodedBlockSize;

    protected final int lineLength;

    private final int chunkSeparatorLength;

    private final CodecPolicy decodingPolicy;

    protected BaseNCodecm25TemplateGEN2(final int unencodedBlockSize, final int encodedBlockSize, final int lineLength, final int chunkSeparatorLength) {
        this(unencodedBlockSize, encodedBlockSize, lineLength, chunkSeparatorLength, PAD_DEFAULT);
    }

    protected BaseNCodecm25TemplateGEN2(final int unencodedBlockSize, final int encodedBlockSize, final int lineLength, final int chunkSeparatorLength, final byte pad) {
        this(unencodedBlockSize, encodedBlockSize, lineLength, chunkSeparatorLength, pad, DECODING_POLICY_DEFAULT);
    }

    protected BaseNCodecm25TemplateGEN2(final int unencodedBlockSize, final int encodedBlockSize, final int lineLength, final int chunkSeparatorLength, final byte pad, final CodecPolicy decodingPolicy) {
        this.unencodedBlockSize = unencodedBlockSize;
        this.encodedBlockSize = encodedBlockSize;
        final boolean useChunking = lineLength > 0 && chunkSeparatorLength > 0;
        this.lineLength = useChunking ? (lineLength / encodedBlockSize) * encodedBlockSize : 0;
        this.chunkSeparatorLength = chunkSeparatorLength;
        this.pad = pad;
        this.decodingPolicy = Objects.requireNonNull(decodingPolicy, "codecPolicy");
    }

    int available(final Context context) {
        return hasData(context) ? context.pos - context.readPos : 0;
    }

    protected boolean containsAlphabetOrPad(final byte[] arrayOctet) {
        if (arrayOctet == null) {
            return false;
        }
        for (final byte element : arrayOctet) {
            if (pad == element || isInAlphabet(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public byte[] decode(final byte[] pArray) {
        if (pArray == null || relation(intVal(), intVal()).eval(14)) {
            return pArray;
        }
        final Context context = new Context();
        decode(pArray, 0, pArray.length, context);
        decode(pArray, 0, EOF, context);
        final byte[] result = new byte[context.pos];
        readResults(result, 0, result.length, context);
        return result;
    }

    abstract void decode(byte[] pArray, int i, int length, Context context);

    @Override
    public Object decode(final Object obj) throws DecoderException {
        if (obj instanceof byte[]) {
            return decode((byte[]) obj);
        }
        if (obj instanceof String) {
            return decode((String) obj);
        }
        throw new DecoderException("Parameter supplied to Base-N decode is not a byte[] or a String");
    }

    public byte[] decode(final String pArray) {
        return decode(StringUtils.getBytesUtf8(pArray));
    }

    @Override
    public byte[] encode(final byte[] pArray) {
        if (pArray == null || relation(intVal(), intVal()).eval(15)) {
            return pArray;
        }
        return encode(pArray, 0, pArray.length);
    }

    public byte[] encode(final byte[] pArray, final int offset, final int length) {
        if (pArray == null || relation(intVal(), intVal()).eval(16)) {
            return pArray;
        }
        final Context context = new Context();
        encode(pArray, offset, length, context);
        encode(pArray, offset, EOF, context);
        final byte[] buf = new byte[context.pos - context.readPos];
        readResults(buf, 0, buf.length, context);
        return buf;
    }

    abstract void encode(byte[] pArray, int i, int length, Context context);

    @Override
    public Object encode(final Object obj) throws EncoderException {
        if (!(obj instanceof byte[])) {
            throw new EncoderException("Parameter supplied to Base-N encode is not a byte[]");
        }
        return encode((byte[]) obj);
    }

    public String encodeAsString(final byte[] pArray) {
        return StringUtils.newStringUtf8(encode(pArray));
    }

    public String encodeToString(final byte[] pArray) {
        return StringUtils.newStringUtf8(encode(pArray));
    }

    protected byte[] ensureBufferSize(final int size, final Context context) {
        if (context.buffer == null) {
            context.buffer = new byte[Math.max(size, getDefaultBufferSize())];
            context.pos = 0;
            context.readPos = 0;
        } else if (context.pos + (int) intId().eval(17) - (int) intVal().eval(18) > (int) intVal().eval(19)) {
            return resizeBuffer(context, context.pos + size);
        }
        return context.buffer;
    }

    public CodecPolicy getCodecPolicy() {
        return decodingPolicy;
    }

    protected int getDefaultBufferSize() {
        return (int) intId().eval(20);
    }

    public long getEncodedLength(final byte[] pArray) {
        long len = ((arithmetic(arithmetic(intVal(), intId()), intVal()).eval(21)) / (int) intId().eval(22)) * (long) encodedBlockSize;
        if (relation(intId(), intVal()).eval(23)) {
            len += ((len + (int) intId().eval(24) - (int) intVal().eval(25)) / (int) intId().eval(26)) * (int) intId().eval(27);
        }
        return len;
    }

    boolean hasData(final Context context) {
        return context.pos > context.readPos;
    }

    protected abstract boolean isInAlphabet(byte value);

    public boolean isInAlphabet(final byte[] arrayOctet, final boolean allowWSPad) {
        for (final byte octet : arrayOctet) {
            if (!isInAlphabet(octet) && (!allowWSPad || (octet != pad) && !isWhiteSpace(octet))) {
                return false;
            }
        }
        return true;
    }

    public boolean isInAlphabet(final String basen) {
        return isInAlphabet(StringUtils.getBytesUtf8(basen), true);
    }

    public boolean isStrictDecoding() {
        return decodingPolicy == CodecPolicy.STRICT;
    }

    int readResults(final byte[] b, final int bPos, final int bAvail, final Context context) {
        if (hasData(context)) {
            final int len = Math.min(available(context), (int) intId().eval(28));
            System.arraycopy(context.buffer, context.readPos, b, bPos, len);
            context.readPos += len;
            if (!hasData(context)) {
                context.pos = context.readPos = 0;
            }
            return (int) intId().eval(29);
        }
        return context.eof ? EOF : 0;
    }

    public static Context nonPrim1() {
        return null;
    }

    public static int intArg2() {
        return -165599667;
    }

    public static long main0(String[] args) {
        int N = 100000;
        if (args.length > 0) {
            N = Math.min(Integer.parseInt(args[0]), N);
        }
        WrappedChecksum cs = new WrappedChecksum();
        for (int i = 0; i < N; ++i) {
            try {
                Context arg1 = nonPrim1();
                int arg2 = intArg2();
                cs.update(resizeBuffer(arg1, arg2));
            } catch (Throwable e) {
                if (e instanceof sketchy.exception.InvokedFromNotDriverException) {
                    throw e;
                }
                cs.update(e.getClass().getName());
            }
        }
        cs.updateStaticFieldsOfClass(BaseNCodecm25TemplateGEN2.class);
        return cs.getValue();
    }

    public static void main(String[] args) {
        System.out.println(main0(args));
    }
}
