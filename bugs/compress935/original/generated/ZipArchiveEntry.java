package org.apache.commons.compress.archivers.zip;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.EntryStreamOffsets;
import org.apache.commons.compress.utils.ByteUtils;
import static jattack.Boom.*;
import jattack.annotation.*;
import org.csutil.checksum.WrappedChecksum;

public class ZipArchiveEntry extends java.util.zip.ZipEntry implements ArchiveEntry, EntryStreamOffsets {

    public static final int PLATFORM_UNIX = 3;

    public static final int PLATFORM_FAT = 0;

    public static final int CRC_UNKNOWN = -1;

    private static final int SHORT_MASK = 0xFFFF;

    private static final int SHORT_SHIFT = 16;

    public enum NameSource {

        NAME,
        NAME_WITH_EFS_FLAG,
        UNICODE_EXTRA_FIELD
    }

    public enum CommentSource {

        COMMENT,
        UNICODE_EXTRA_FIELD
    }

    private int method = ZipMethod.UNKNOWN_CODE;

    private long size = SIZE_UNKNOWN;

    private int internalAttributes;

    private int versionRequired;

    private int versionMadeBy;

    private int platform = PLATFORM_FAT;

    private int rawFlag;

    private long externalAttributes;

    private int alignment;

    private ZipExtraField[] extraFields;

    private UnparseableExtraFieldData unparseableExtra;

    private String name;

    private byte[] rawName;

    private GeneralPurposeBit gpb = new GeneralPurposeBit();

    private long localHeaderOffset = OFFSET_UNKNOWN;

    private long dataOffset = OFFSET_UNKNOWN;

    private boolean isStreamContiguous;

    private NameSource nameSource = NameSource.NAME;

    private CommentSource commentSource = CommentSource.COMMENT;

    private long diskNumberStart;

    static final ZipArchiveEntry[] EMPTY_ZIP_ARCHIVE_ENTRY_ARRAY = new ZipArchiveEntry[0];

    public ZipArchiveEntry(final String name) {
        super(name);
        setName(name);
    }

    public ZipArchiveEntry(final java.util.zip.ZipEntry entry) throws ZipException {
        super(entry);
        setName(entry.getName());
        final byte[] extra = entry.getExtra();
        if (extra != null) {
            setExtraFields(ExtraFieldUtils.parse(extra, true, ExtraFieldParsingMode.BEST_EFFORT));
        } else {
            setExtra();
        }
        setMethod(entry.getMethod());
        this.size = entry.getSize();
    }

    public ZipArchiveEntry(final ZipArchiveEntry entry) throws ZipException {
        this((java.util.zip.ZipEntry) entry);
        setInternalAttributes(entry.getInternalAttributes());
        setExternalAttributes(entry.getExternalAttributes());
        setExtraFields(getAllExtraFieldsNoCopy());
        setPlatform(entry.getPlatform());
        final GeneralPurposeBit other = entry.getGeneralPurposeBit();
        setGeneralPurposeBit(other == null ? null : (GeneralPurposeBit) other.clone());
    }

    protected ZipArchiveEntry() {
        this("");
    }

    public ZipArchiveEntry(final File inputFile, final String entryName) {
        this(inputFile.isDirectory() && !entryName.endsWith("/") ? entryName + "/" : entryName);
        if (inputFile.isFile()) {
            setSize(inputFile.length());
        }
        setTime(inputFile.lastModified());
    }

    public ZipArchiveEntry(final Path inputPath, final String entryName, final LinkOption... options) throws IOException {
        this(Files.isDirectory(inputPath, options) && !entryName.endsWith("/") ? entryName + "/" : entryName);
        if (Files.isRegularFile(inputPath, options)) {
            setSize(Files.size(inputPath));
        }
        setTime(Files.getLastModifiedTime(inputPath, options));
    }

    public void setTime(final FileTime fileTime) {
        setTime(fileTime.toMillis());
    }

    @Override
    public Object clone() {
        final ZipArchiveEntry e = (ZipArchiveEntry) super.clone();
        e.setInternalAttributes(getInternalAttributes());
        e.setExternalAttributes(getExternalAttributes());
        e.setExtraFields(getAllExtraFieldsNoCopy());
        return e;
    }

    @Override
    public int getMethod() {
        return method;
    }

    @Override
    public void setMethod(final int method) {
        if (method < 0) {
            throw new IllegalArgumentException("ZIP compression method can not be negative: " + method);
        }
        this.method = method;
    }

    public int getInternalAttributes() {
        return internalAttributes;
    }

    public void setInternalAttributes(final int value) {
        internalAttributes = value;
    }

    public long getExternalAttributes() {
        return externalAttributes;
    }

    public void setExternalAttributes(final long value) {
        externalAttributes = value;
    }

    public void setUnixMode(final int mode) {
        setExternalAttributes((mode << SHORT_SHIFT) | ((mode & 0200) == 0 ? 1 : 0) | (isDirectory() ? 0x10 : 0));
        platform = PLATFORM_UNIX;
    }

    public int getUnixMode() {
        return platform != PLATFORM_UNIX ? 0 : (int) ((getExternalAttributes() >> SHORT_SHIFT) & SHORT_MASK);
    }

    public boolean isUnixSymlink() {
        return (getUnixMode() & UnixStat.FILE_TYPE_FLAG) == UnixStat.LINK_FLAG;
    }

    public int getPlatform() {
        return platform;
    }

    protected void setPlatform(final int platform) {
        this.platform = platform;
    }

    protected int getAlignment() {
        return this.alignment;
    }

    public void setAlignment(final int alignment) {
        if ((alignment & (alignment - 1)) != 0 || alignment > 0xffff) {
            throw new IllegalArgumentException("Invalid value for alignment, must be power of two and no bigger than " + 0xffff + " but is " + alignment);
        }
        this.alignment = alignment;
    }

    public void setExtraFields(final ZipExtraField[] fields) {
        unparseableExtra = null;
        final List<ZipExtraField> newFields = new ArrayList<>();
        if (fields != null) {
            for (final ZipExtraField field : fields) {
                if (field instanceof UnparseableExtraFieldData) {
                    unparseableExtra = (UnparseableExtraFieldData) field;
                } else {
                    newFields.add(field);
                }
            }
        }
        extraFields = newFields.toArray(ExtraFieldUtils.EMPTY_ZIP_EXTRA_FIELD_ARRAY);
        setExtra();
    }

    public ZipExtraField[] getExtraFields() {
        return getParseableExtraFields();
    }

    public ZipExtraField[] getExtraFields(final boolean includeUnparseable) {
        return includeUnparseable ? getAllExtraFields() : getParseableExtraFields();
    }

    public ZipExtraField[] getExtraFields(final ExtraFieldParsingBehavior parsingBehavior) throws ZipException {
        if (parsingBehavior == ExtraFieldParsingMode.BEST_EFFORT) {
            return getExtraFields(true);
        }
        if (parsingBehavior == ExtraFieldParsingMode.ONLY_PARSEABLE_LENIENT) {
            return getExtraFields(false);
        }
        final byte[] local = getExtra();
        final List<ZipExtraField> localFields = new ArrayList<>(Arrays.asList(ExtraFieldUtils.parse(local, true, parsingBehavior)));
        final byte[] central = getCentralDirectoryExtra();
        final List<ZipExtraField> centralFields = new ArrayList<>(Arrays.asList(ExtraFieldUtils.parse(central, false, parsingBehavior)));
        final List<ZipExtraField> merged = new ArrayList<>();
        for (final ZipExtraField l : localFields) {
            ZipExtraField c = null;
            if (l instanceof UnparseableExtraFieldData) {
                c = findUnparseable(centralFields);
            } else {
                c = findMatching(l.getHeaderId(), centralFields);
            }
            if (c != null) {
                final byte[] cd = c.getCentralDirectoryData();
                if (cd != null && cd.length > 0) {
                    l.parseFromCentralDirectoryData(cd, 0, cd.length);
                }
                centralFields.remove(c);
            }
            merged.add(l);
        }
        merged.addAll(centralFields);
        return merged.toArray(ExtraFieldUtils.EMPTY_ZIP_EXTRA_FIELD_ARRAY);
    }

    private ZipExtraField[] getParseableExtraFieldsNoCopy() {
        if (extraFields == null) {
            return ExtraFieldUtils.EMPTY_ZIP_EXTRA_FIELD_ARRAY;
        }
        return extraFields;
    }

    private ZipExtraField[] getParseableExtraFields() {
        final ZipExtraField[] parseableExtraFields = getParseableExtraFieldsNoCopy();
        return (parseableExtraFields == extraFields) ? copyOf(parseableExtraFields, parseableExtraFields.length) : parseableExtraFields;
    }

    private ZipExtraField[] getAllExtraFieldsNoCopy() {
        if (extraFields == null) {
            return getUnparseableOnly();
        }
        return unparseableExtra != null ? getMergedFields() : extraFields;
    }

    private ZipExtraField[] getMergedFields() {
        final ZipExtraField[] zipExtraFields = copyOf(extraFields, extraFields.length + 1);
        zipExtraFields[extraFields.length] = unparseableExtra;
        return zipExtraFields;
    }

    private ZipExtraField[] getUnparseableOnly() {
        return unparseableExtra == null ? ExtraFieldUtils.EMPTY_ZIP_EXTRA_FIELD_ARRAY : new ZipExtraField[] { unparseableExtra };
    }

    private ZipExtraField[] getAllExtraFields() {
        final ZipExtraField[] allExtraFieldsNoCopy = getAllExtraFieldsNoCopy();
        return (allExtraFieldsNoCopy == extraFields) ? copyOf(allExtraFieldsNoCopy, allExtraFieldsNoCopy.length) : allExtraFieldsNoCopy;
    }

    private ZipExtraField findUnparseable(final List<ZipExtraField> fs) {
        for (final ZipExtraField f : fs) {
            if (f instanceof UnparseableExtraFieldData) {
                return f;
            }
        }
        return null;
    }

    private ZipExtraField findMatching(final ZipShort headerId, final List<ZipExtraField> fs) {
        for (final ZipExtraField f : fs) {
            if (headerId.equals(f.getHeaderId())) {
                return f;
            }
        }
        return null;
    }

    public void addExtraField(final ZipExtraField ze) {
        if (ze instanceof UnparseableExtraFieldData) {
            unparseableExtra = (UnparseableExtraFieldData) ze;
        } else {
            if (extraFields == null) {
                extraFields = new ZipExtraField[] { ze };
            } else {
                if (getExtraField(ze.getHeaderId()) != null) {
                    removeExtraField(ze.getHeaderId());
                }
                final ZipExtraField[] zipExtraFields = copyOf(extraFields, extraFields.length + 1);
                zipExtraFields[zipExtraFields.length - 1] = ze;
                extraFields = zipExtraFields;
            }
        }
        setExtra();
    }

    public void addAsFirstExtraField(final ZipExtraField ze) {
        if (ze instanceof UnparseableExtraFieldData) {
            unparseableExtra = (UnparseableExtraFieldData) ze;
        } else {
            if (getExtraField(ze.getHeaderId()) != null) {
                removeExtraField(ze.getHeaderId());
            }
            final ZipExtraField[] copy = extraFields;
            final int newLen = extraFields != null ? extraFields.length + 1 : 1;
            extraFields = new ZipExtraField[newLen];
            extraFields[0] = ze;
            if (copy != null) {
                System.arraycopy(copy, 0, extraFields, 1, extraFields.length - 1);
            }
        }
        setExtra();
    }

    public void removeExtraField(final ZipShort type) {
        if (extraFields == null) {
            throw new java.util.NoSuchElementException();
        }
        final List<ZipExtraField> newResult = new ArrayList<>();
        for (final ZipExtraField extraField : extraFields) {
            if (!type.equals(extraField.getHeaderId())) {
                newResult.add(extraField);
            }
        }
        if (extraFields.length == newResult.size()) {
            throw new java.util.NoSuchElementException();
        }
        extraFields = newResult.toArray(ExtraFieldUtils.EMPTY_ZIP_EXTRA_FIELD_ARRAY);
        setExtra();
    }

    public void removeUnparseableExtraFieldData() {
        if (unparseableExtra == null) {
            throw new java.util.NoSuchElementException();
        }
        unparseableExtra = null;
        setExtra();
    }

    public ZipExtraField getExtraField(final ZipShort type) {
        if (extraFields != null) {
            for (final ZipExtraField extraField : extraFields) {
                if (type.equals(extraField.getHeaderId())) {
                    return extraField;
                }
            }
        }
        return null;
    }

    public UnparseableExtraFieldData getUnparseableExtraFieldData() {
        return unparseableExtra;
    }

    @Override
    public void setExtra(final byte[] extra) throws RuntimeException {
        try {
            final ZipExtraField[] local = ExtraFieldUtils.parse(extra, true, ExtraFieldParsingMode.BEST_EFFORT);
            mergeExtraFields(local, true);
        } catch (final ZipException e) {
            throw new RuntimeException("Error parsing extra fields for entry: " + getName() + " - " + e.getMessage(), e);
        }
    }

    protected void setExtra() {
        super.setExtra(ExtraFieldUtils.mergeLocalFileDataData(getAllExtraFieldsNoCopy()));
    }

    public void setCentralDirectoryExtra(final byte[] b) {
        try {
            final ZipExtraField[] central = ExtraFieldUtils.parse(b, false, ExtraFieldParsingMode.BEST_EFFORT);
            mergeExtraFields(central, false);
        } catch (final ZipException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public byte[] getLocalFileDataExtra() {
        final byte[] extra = getExtra();
        return extra != null ? extra : ByteUtils.EMPTY_BYTE_ARRAY;
    }

    public byte[] getCentralDirectoryExtra() {
        return ExtraFieldUtils.mergeCentralDirectoryData(getAllExtraFieldsNoCopy());
    }

    @Override
    public String getName() {
        return name == null ? super.getName() : name;
    }

    @Override
    public boolean isDirectory() {
        final String n = getName();
        return n != null && n.endsWith("/");
    }

    protected void setName(String name) {
        if (name != null && getPlatform() == PLATFORM_FAT && !name.contains("/")) {
            name = name.replace('\\', '/');
        }
        this.name = name;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public void setSize(final long size) {
        if (size < 0) {
            throw new IllegalArgumentException("Invalid entry size");
        }
        this.size = size;
    }

    protected void setName(final String name, final byte[] rawName) {
        setName(name);
        this.rawName = rawName;
    }

    public byte[] getRawName() {
        if (rawName != null) {
            return Arrays.copyOf(rawName, rawName.length);
        }
        return null;
    }

    protected long getLocalHeaderOffset() {
        return this.localHeaderOffset;
    }

    protected void setLocalHeaderOffset(final long localHeaderOffset) {
        this.localHeaderOffset = localHeaderOffset;
    }

    @Override
    public long getDataOffset() {
        return dataOffset;
    }

    protected void setDataOffset(final long dataOffset) {
        this.dataOffset = dataOffset;
    }

    @Override
    public boolean isStreamContiguous() {
        return isStreamContiguous;
    }

    protected void setStreamContiguous(final boolean isStreamContiguous) {
        this.isStreamContiguous = isStreamContiguous;
    }

    @Override
    public int hashCode() {
        final String n = getName();
        return (n == null ? "" : n).hashCode();
    }

    public GeneralPurposeBit getGeneralPurposeBit() {
        return gpb;
    }

    public void setGeneralPurposeBit(final GeneralPurposeBit b) {
        gpb = b;
    }

    private void mergeExtraFields(final ZipExtraField[] f, final boolean local) {
        if (extraFields == null) {
            setExtraFields(f);
        } else {
            for (final ZipExtraField element : f) {
                final ZipExtraField existing;
                if (element instanceof UnparseableExtraFieldData) {
                    existing = unparseableExtra;
                } else {
                    existing = getExtraField(element.getHeaderId());
                }
                if (existing == null) {
                    addExtraField(element);
                } else {
                    final byte[] b = local ? element.getLocalFileDataData() : element.getCentralDirectoryData();
                    try {
                        if (local) {
                            existing.parseFromLocalFileData(b, 0, b.length);
                        } else {
                            existing.parseFromCentralDirectoryData(b, 0, b.length);
                        }
                    } catch (final ZipException ex) {
                        final UnrecognizedExtraField u = new UnrecognizedExtraField();
                        u.setHeaderId(existing.getHeaderId());
                        if (local) {
                            u.setLocalFileDataData(b);
                            u.setCentralDirectoryData(existing.getCentralDirectoryData());
                        } else {
                            u.setLocalFileDataData(existing.getLocalFileDataData());
                            u.setCentralDirectoryData(b);
                        }
                        removeExtraField(existing.getHeaderId());
                        addExtraField(u);
                    }
                }
            }
            setExtra();
        }
    }

    @Override
    public Date getLastModifiedDate() {
        return new Date(getTime());
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ZipArchiveEntry other = (ZipArchiveEntry) obj;
        final String myName = getName();
        final String otherName = other.getName();
        if (!Objects.equals(myName, otherName)) {
            return false;
        }
        String myComment = getComment();
        String otherComment = other.getComment();
        if (myComment == null) {
            myComment = "";
        }
        if (otherComment == null) {
            otherComment = "";
        }
        return getTime() == other.getTime() && myComment.equals(otherComment) && getInternalAttributes() == other.getInternalAttributes() && getPlatform() == other.getPlatform() && getExternalAttributes() == other.getExternalAttributes() && getMethod() == other.getMethod() && getSize() == other.getSize() && getCrc() == other.getCrc() && getCompressedSize() == other.getCompressedSize() && Arrays.equals(getCentralDirectoryExtra(), other.getCentralDirectoryExtra()) && Arrays.equals(getLocalFileDataExtra(), other.getLocalFileDataExtra()) && localHeaderOffset == other.localHeaderOffset && dataOffset == other.dataOffset && gpb.equals(other.gpb);
    }

    public void setVersionMadeBy(final int versionMadeBy) {
        this.versionMadeBy = versionMadeBy;
    }

    public void setVersionRequired(final int versionRequired) {
        this.versionRequired = versionRequired;
    }

    public int getVersionRequired() {
        return versionRequired;
    }

    public int getVersionMadeBy() {
        return versionMadeBy;
    }

    public int getRawFlag() {
        return rawFlag;
    }

    public void setRawFlag(final int rawFlag) {
        this.rawFlag = rawFlag;
    }

    public NameSource getNameSource() {
        return nameSource;
    }

    public void setNameSource(final NameSource nameSource) {
        this.nameSource = nameSource;
    }

    public CommentSource getCommentSource() {
        return commentSource;
    }

    public void setCommentSource(final CommentSource commentSource) {
        this.commentSource = commentSource;
    }

    public long getDiskNumberStart() {
        return diskNumberStart;
    }

    public void setDiskNumberStart(final long diskNumberStart) {
        this.diskNumberStart = diskNumberStart;
    }

    private ZipExtraField[] copyOf(final ZipExtraField[] src, final int length) {
        final ZipExtraField[] cpy = new ZipExtraField[length];
        System.arraycopy(src, 0, cpy, 0, Math.min(src.length, length));
        return cpy;
    }

    public enum ExtraFieldParsingMode implements ExtraFieldParsingBehavior {

        BEST_EFFORT(ExtraFieldUtils.UnparseableExtraField.READ) {

            @Override
            public ZipExtraField fill(final ZipExtraField field, final byte[] data, final int off, final int len, final boolean local) {
                return fillAndMakeUnrecognizedOnError(field, data, off, len, local);
            }
        }
        ,
        STRICT_FOR_KNOW_EXTRA_FIELDS(ExtraFieldUtils.UnparseableExtraField.READ),
        ONLY_PARSEABLE_LENIENT(ExtraFieldUtils.UnparseableExtraField.SKIP) {

            @Override
            public ZipExtraField fill(final ZipExtraField field, final byte[] data, final int off, final int len, final boolean local) {
                return fillAndMakeUnrecognizedOnError(field, data, off, len, local);
            }
        }
        ,
        ONLY_PARSEABLE_STRICT(ExtraFieldUtils.UnparseableExtraField.SKIP),
        DRACONIC(ExtraFieldUtils.UnparseableExtraField.THROW);

        private final ExtraFieldUtils.UnparseableExtraField onUnparseableData;

        ExtraFieldParsingMode(final ExtraFieldUtils.UnparseableExtraField onUnparseableData) {
            this.onUnparseableData = onUnparseableData;
        }

        @Override
        public ZipExtraField onUnparseableExtraField(final byte[] data, final int off, final int len, final boolean local, final int claimedLength) throws ZipException {
            return onUnparseableData.onUnparseableExtraField(data, off, len, local, claimedLength);
        }

        @Override
        public ZipExtraField createExtraField(final ZipShort headerId) throws ZipException, InstantiationException, IllegalAccessException {
            return ExtraFieldUtils.createExtraField(headerId);
        }

        @Override
        public ZipExtraField fill(final ZipExtraField field, final byte[] data, final int off, final int len, final boolean local) throws ZipException {
            return ExtraFieldUtils.fillExtraField(field, data, off, len, local);
        }

        private static ZipExtraField fillAndMakeUnrecognizedOnError(final ZipExtraField field, final byte[] data, final int off, final int len, final boolean local) {
            try {
                return ExtraFieldUtils.fillExtraField(field, data, off, len, local);
            } catch (final ZipException ex) {
                final UnrecognizedExtraField u = new UnrecognizedExtraField();
                u.setHeaderId(field.getHeaderId());
                if (local) {
                    u.setLocalFileDataData(Arrays.copyOfRange(data, off, off + len));
                } else {
                    u.setCentralDirectoryData(Arrays.copyOfRange(data, off, off + len));
                }
                return u;
            }
        }
    }

    @jattack.annotation.Arguments
    public static Object[] _jitmagicArgs() throws Throwable {
        org.apache.commons.compress.archivers.jar.JarArchiveEntry jarArchiveEntry1 = new org.apache.commons.compress.archivers.jar.JarArchiveEntry("UTF8");
        org.apache.commons.compress.archivers.zip.ResourceAlignmentExtraField resourceAlignmentExtraField4 = new org.apache.commons.compress.archivers.zip.ResourceAlignmentExtraField((int) (short) 10, false);
        boolean boolean5 = resourceAlignmentExtraField4.allowMethodChange();
        jarArchiveEntry1.addExtraField((org.apache.commons.compress.archivers.zip.ZipExtraField) resourceAlignmentExtraField4);
        java.lang.Object obj7 = jarArchiveEntry1.clone();
        jarArchiveEntry1.setMethod(84446);
        org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp x5455_ExtendedTimestamp10 = new org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp();
        org.apache.commons.compress.archivers.zip.ZipShort zipShort11 = x5455_ExtendedTimestamp10.getCentralDirectoryLength();
        java.io.InputStream inputStream12 = null;
        org.apache.commons.compress.archivers.tar.TarArchiveInputStream tarArchiveInputStream15 = new org.apache.commons.compress.archivers.tar.TarArchiveInputStream(inputStream12, (int) (short) 100, "00");
        org.apache.commons.compress.utils.CloseShieldFilterInputStream closeShieldFilterInputStream16 = new org.apache.commons.compress.utils.CloseShieldFilterInputStream((java.io.InputStream) tarArchiveInputStream15);
        org.apache.commons.compress.archivers.cpio.CpioArchiveInputStream cpioArchiveInputStream18 = new org.apache.commons.compress.archivers.cpio.CpioArchiveInputStream((java.io.InputStream) closeShieldFilterInputStream16, "bzip2");
        org.apache.commons.compress.compressors.zstandard.ZstdCompressorInputStream zstdCompressorInputStream19 = new org.apache.commons.compress.compressors.zstandard.ZstdCompressorInputStream((java.io.InputStream) cpioArchiveInputStream18);
        org.apache.commons.compress.archivers.zip.ZipArchiveInputStream zipArchiveInputStream22 = new org.apache.commons.compress.archivers.zip.ZipArchiveInputStream((java.io.InputStream) cpioArchiveInputStream18, "bzip2", false);
        org.apache.commons.compress.utils.CRC32VerifyingInputStream cRC32VerifyingInputStream25 = new org.apache.commons.compress.utils.CRC32VerifyingInputStream((java.io.InputStream) zipArchiveInputStream22, (long) 511, (long) (byte) 53);
        boolean boolean26 = zipShort11.equals((java.lang.Object) zipArchiveInputStream22);
        org.apache.commons.compress.archivers.zip.ZipExtraField zipExtraField27 = jarArchiveEntry1.getExtraField(zipShort11);
        int int28 = jarArchiveEntry1.getMethod();
        return new Object[] { jarArchiveEntry1 };
    }

    public static long main0(String[] args) {
        org.csutil.Helper.parseArgs(args);
        int N = Math.min(org.csutil.Helper.nItrs, 100000);
        WrappedChecksum cs = new WrappedChecksum(true);
        try {
            Object[] eArgs = _jitmagicArgs();
            cs.update(eArgs);
            ZipArchiveEntry rcvr = (ZipArchiveEntry) eArgs[0];
            for (int i = 0; i < N; ++i) {
                try {
                    cs.update(rcvr.getRawFlag());
                } catch (Throwable e) {
                    if (e instanceof jattack.exception.InvokedFromNotDriverException) {
                        throw (jattack.exception.InvokedFromNotDriverException) e;
                    }
                    cs.update(e.getClass().getName());
                }
            }
        } catch (Throwable e) {
            if (e instanceof jattack.exception.InvokedFromNotDriverException) {
                throw (jattack.exception.InvokedFromNotDriverException) e;
            }
            cs.update(e.getClass().getName());
        }
        cs.updateStaticFieldsOfClass(ZipArchiveEntry.class);
        return cs.getValue();
    }

    public static void main(String[] args) {
        org.csutil.Helper.write(main0(args));
    }
}
