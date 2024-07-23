package org.apache.commons.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import static jattack.Boom.*;
import jattack.annotation.*;
import org.csutil.checksum.WrappedChecksum;

@Deprecated
public class StrTokenizer implements ListIterator<String>, Cloneable {

    private static final StrTokenizer CSV_TOKENIZER_PROTOTYPE;

    private static final StrTokenizer TSV_TOKENIZER_PROTOTYPE;

    static {
        CSV_TOKENIZER_PROTOTYPE = new StrTokenizer();
        CSV_TOKENIZER_PROTOTYPE.setDelimiterMatcher(StrMatcher.commaMatcher());
        CSV_TOKENIZER_PROTOTYPE.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
        CSV_TOKENIZER_PROTOTYPE.setIgnoredMatcher(StrMatcher.noneMatcher());
        CSV_TOKENIZER_PROTOTYPE.setTrimmerMatcher(StrMatcher.trimMatcher());
        CSV_TOKENIZER_PROTOTYPE.setEmptyTokenAsNull(false);
        CSV_TOKENIZER_PROTOTYPE.setIgnoreEmptyTokens(false);
        TSV_TOKENIZER_PROTOTYPE = new StrTokenizer();
        TSV_TOKENIZER_PROTOTYPE.setDelimiterMatcher(StrMatcher.tabMatcher());
        TSV_TOKENIZER_PROTOTYPE.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
        TSV_TOKENIZER_PROTOTYPE.setIgnoredMatcher(StrMatcher.noneMatcher());
        TSV_TOKENIZER_PROTOTYPE.setTrimmerMatcher(StrMatcher.trimMatcher());
        TSV_TOKENIZER_PROTOTYPE.setEmptyTokenAsNull(false);
        TSV_TOKENIZER_PROTOTYPE.setIgnoreEmptyTokens(false);
    }

    private static StrTokenizer getCSVClone() {
        return (StrTokenizer) refId(org.apache.commons.text.StrTokenizer.class, "CSV_TOKENIZER_PROTOTYPE").eval(1).clone();
    }

    public static StrTokenizer getCSVInstance() {
        return getCSVClone();
    }

    public static StrTokenizer getCSVInstance(final char[] input) {
        final StrTokenizer tok = getCSVClone();
        refId(org.apache.commons.text.StrTokenizer.class, "tok").eval(3).reset(refId(char[].class, "input").eval(2));
        return refId(org.apache.commons.text.StrTokenizer.class, "tok").eval(4);
    }

    public static StrTokenizer getCSVInstance(final String input) {
        final StrTokenizer tok = getCSVClone();
        refId(org.apache.commons.text.StrTokenizer.class, "tok").eval(6).reset(refId(java.lang.String.class, "input").eval(5));
        return refId(org.apache.commons.text.StrTokenizer.class, "tok").eval(7);
    }

    private static StrTokenizer getTSVClone() {
        return (StrTokenizer) TSV_TOKENIZER_PROTOTYPE.clone();
    }

    public static StrTokenizer getTSVInstance() {
        return getTSVClone();
    }

    public static StrTokenizer getTSVInstance(final char[] input) {
        final StrTokenizer tok = getTSVClone();
        refId(org.apache.commons.text.StrTokenizer.class, "tok").eval(10).reset(refId(char[].class, "input").eval(9));
        return refId(org.apache.commons.text.StrTokenizer.class, "tok").eval(11);
    }

    public static StrTokenizer getTSVInstance(final String input) {
        final StrTokenizer tok = getTSVClone();
        tok.reset(input);
        return tok;
    }

    private char[] chars;

    private String[] tokens;

    private int tokenPos;

    private StrMatcher delimMatcher = StrMatcher.splitMatcher();

    private StrMatcher quoteMatcher = StrMatcher.noneMatcher();

    private StrMatcher ignoredMatcher = StrMatcher.noneMatcher();

    private StrMatcher trimmerMatcher = StrMatcher.noneMatcher();

    private boolean emptyAsNull;

    private boolean ignoreEmptyTokens = true;

    public StrTokenizer() {
        this.chars = null;
    }

    public StrTokenizer(final char[] input) {
        if (refId(char[].class, "input").eval(15) == null) {
            this.chars = null;
        } else {
            this.chars = refId(char[].class, "input").eval(16).clone();
        }
    }

    public StrTokenizer(final char[] input, final char delim) {
        this(refId(char[].class, "input").eval(17));
        setDelimiterChar((char) charId("delim").eval(18));
    }

    public StrTokenizer(final char[] input, final char delim, final char quote) {
        this(refId(char[].class, "input").eval(19), (char) charId("delim").eval(20));
        setQuoteChar((char) charId("quote").eval(21));
    }

    public StrTokenizer(final char[] input, final String delim) {
        this(refId(char[].class, "input").eval(22));
        setDelimiterString(refId(java.lang.String.class, "delim").eval(23));
    }

    public StrTokenizer(final char[] input, final StrMatcher delim) {
        this(refId(char[].class, "input").eval(24));
        setDelimiterMatcher(refId(org.apache.commons.text.StrMatcher.class, "delim").eval(25));
    }

    public StrTokenizer(final char[] input, final StrMatcher delim, final StrMatcher quote) {
        this(refId(char[].class, "input").eval(26), refId(org.apache.commons.text.StrMatcher.class, "delim").eval(27));
        setQuoteMatcher(refId(org.apache.commons.text.StrMatcher.class, "quote").eval(28));
    }

    public StrTokenizer(final String input) {
        if (refId(java.lang.String.class, "input").eval(29) != null) {
            chars = refId(java.lang.String.class, "input").eval(30).toCharArray();
        } else {
            chars = null;
        }
    }

    public StrTokenizer(final String input, final char delim) {
        this(refId(java.lang.String.class, "input").eval(31));
        setDelimiterChar((char) charId("delim").eval(32));
    }

    public StrTokenizer(final String input, final char delim, final char quote) {
        this(refId(java.lang.String.class, "input").eval(33), (char) charId("delim").eval(34));
        setQuoteChar((char) charId("quote").eval(35));
    }

    public StrTokenizer(final String input, final String delim) {
        this(refId(java.lang.String.class, "input").eval(36));
        setDelimiterString(refId(java.lang.String.class, "delim").eval(37));
    }

    public StrTokenizer(final String input, final StrMatcher delim) {
        this(refId(java.lang.String.class, "input").eval(38));
        setDelimiterMatcher(refId(org.apache.commons.text.StrMatcher.class, "delim").eval(39));
    }

    public StrTokenizer(final String input, final StrMatcher delim, final StrMatcher quote) {
        this(refId(java.lang.String.class, "input").eval(40), refId(org.apache.commons.text.StrMatcher.class, "delim").eval(41));
        setQuoteMatcher(refId(org.apache.commons.text.StrMatcher.class, "quote").eval(42));
    }

    @Override
    public void add(final String obj) {
        throw new UnsupportedOperationException("add() is unsupported");
    }

    private void addToken(final List<String> list, String tok) {
        if (tok == null || tok.isEmpty()) {
            if (isIgnoreEmptyTokens()) {
                return;
            }
            if (isEmptyTokenAsNull()) {
                tok = null;
            }
        }
        list.add(tok);
    }

    private void checkTokenized() {
        if (tokens == null) {
            if (chars == null) {
                final List<String> split = tokenize(null, (int) asInt(0).eval(50), (int) asInt(0).eval(51));
                tokens = split.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
            } else {
                final List<String> split = tokenize(chars, (int) 0, chars.length);
                tokens = split.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
            }
        }
    }

    @Override
    public Object clone() {
        try {
            return cloneReset();
        } catch (final CloneNotSupportedException ex) {
            return null;
        }
    }

    Object cloneReset() throws CloneNotSupportedException {
        final StrTokenizer cloned = (StrTokenizer) super.clone();
        if (cloned.chars != null) {
            cloned.chars = cloned.chars.clone();
        }
        cloned.reset();
        return cloned;
    }

    public String getContent() {
        if (refId(char[].class, "chars").eval(54) == null) {
            return null;
        }
        return new String(refId(char[].class, "chars").eval(55));
    }

    public StrMatcher getDelimiterMatcher() {
        return this.delimMatcher;
    }

    public StrMatcher getIgnoredMatcher() {
        return ignoredMatcher;
    }

    public StrMatcher getQuoteMatcher() {
        return quoteMatcher;
    }

    public String[] getTokenArray() {
        checkTokenized();
        return refId(java.lang.String[].class, "tokens").eval(58).clone();
    }

    public List<String> getTokenList() {
        checkTokenized();
        final List<String> list = new ArrayList<>(tokens.length);
        Collections.addAll(list, refId(java.lang.String[].class, "tokens").eval(59));
        return list;
    }

    public StrMatcher getTrimmerMatcher() {
        return trimmerMatcher;
    }

    @Override
    public boolean hasNext() {
        checkTokenized();
        return (int) intId("tokenPos").eval(61) < tokens.length;
    }

    @Override
    public boolean hasPrevious() {
        checkTokenized();
        return (boolean) relation(intId("tokenPos"), asInt(0)).eval(62);
    }

    public boolean isEmptyTokenAsNull() {
        return this.emptyAsNull;
    }

    public boolean isIgnoreEmptyTokens() {
        return (boolean) ignoreEmptyTokens;
    }

    private boolean isQuote(final char[] srcChars, final int pos, final int len, final int quoteStart, final int quoteLen) {
        int _jitmagicLoopLimiter1 = 0;
        for (int i = (int) asInt(0).eval(67); (boolean) relation(intId("i"), intId("quoteLen")).eval(66) && _jitmagicLoopLimiter1++ < 1000; i++) {
            if ((boolean) logic(relation(arithmetic(intId("pos"), intId("i"), ADD), intId("len")), relation(cast(Integer.class, charArrAccessExp(refId(char[].class, "srcChars"), arithmetic(intId("pos"), intId("i"), ADD))), cast(Integer.class, charArrAccessExp(refId(char[].class, "srcChars"), arithmetic(intId("quoteStart"), intId("i"), ADD))))).eval(64)) {
                return (boolean) asBool(false).eval(65);
            }
        }
        return (boolean) asBool(true).eval(68);
    }

    @Override
    public String next() {
        if (hasNext()) {
            return refArrAccessExp(java.lang.String.class, java.lang.String[].class, refId(java.lang.String[].class, "tokens")).eval(69);
        }
        throw new NoSuchElementException();
    }

    @Override
    public int nextIndex() {
        return (int) intId("tokenPos").eval(70);
    }

    public String nextToken() {
        if (hasNext()) {
            return refArrAccessExp(java.lang.String.class, java.lang.String[].class, refId(java.lang.String[].class, "tokens")).eval(71);
        }
        return null;
    }

    @Override
    public String previous() {
        if (hasPrevious()) {
            return refArrAccessExp(java.lang.String.class, java.lang.String[].class, refId(java.lang.String[].class, "tokens")).eval(72);
        }
        throw new NoSuchElementException();
    }

    @Override
    public int previousIndex() {
        return (int) arithmetic(intId("tokenPos"), asInt(1), SUB).eval(73);
    }

    public String previousToken() {
        if (hasPrevious()) {
            return refArrAccessExp(java.lang.String.class, java.lang.String[].class, refId(java.lang.String[].class, "tokens")).eval(74);
        }
        return null;
    }

    private int readNextToken(final char[] srcChars, int start, final int len, final StrBuilder workArea, final List<String> tokenList) {
        int _jitmagicLoopLimiter2 = 0;
        while ((boolean) (start <= len) && _jitmagicLoopLimiter2++ < 1000) {
            final int removeLen = Math.max(getIgnoredMatcher().isMatch(srcChars, (int) start, (int) start, (int) len), getTrimmerMatcher().isMatch(srcChars, (int) start, (int) start, (int) len));
            if ((boolean) (removeLen != 0) || getDelimiterMatcher().isMatch(srcChars, (int) start, (int) start, (int) len) > (int) 0 || getQuoteMatcher().isMatch(srcChars, (int) start, (int) start, (int) len) > (int) 0) {
                break;
            }
            start += (int) removeLen;
        }
        if ((boolean) (start <= len)) {
            addToken(tokenList, StringUtils.EMPTY);
            return (int) -1;
        }
        final int delimLen = getDelimiterMatcher().isMatch(refId(char[].class, "srcChars").eval(98), (int) intId("start").eval(99), (int) intId("start").eval(100), (int) intId("len").eval(101));
        if ((boolean) relation(intId("delimLen"), asInt(0)).eval(102)) {
            addToken(tokenList, StringUtils.EMPTY);
            return (int) arithmetic(intId("start"), intId("delimLen"), ADD).eval(103);
        }
        final int quoteLen = getQuoteMatcher().isMatch(refId(char[].class, "srcChars").eval(104), (int) intId("start").eval(105), (int) intId("start").eval(106), (int) intId("len").eval(107));
        if ((boolean) relation(intId("quoteLen"), asInt(0)).eval(108)) {
            return readWithQuotes(refId(char[].class, "srcChars").eval(109), (int) arithmetic(intId("start"), intId("quoteLen"), ADD).eval(110), (int) intId("len").eval(111), refId(org.apache.commons.text.StrBuilder.class, "workArea").eval(112), tokenList, (int) intId("start").eval(113), (int) intId("quoteLen").eval(114));
        }
        return readWithQuotes(refId(char[].class, "srcChars").eval(115), (int) intId("start").eval(116), (int) intId("len").eval(117), refId(org.apache.commons.text.StrBuilder.class, "workArea").eval(118), tokenList, (int) asInt(0).eval(119), (int) asInt(0).eval(120));
    }

    private int readWithQuotes(final char[] srcChars, final int start, final int len, final StrBuilder workArea, final List<String> tokenList, final int quoteStart, final int quoteLen) {
        refId(org.apache.commons.text.StrBuilder.class, "workArea").eval(121).clear();
        int pos = (int) intId("start").eval(122);
        boolean quoting = (boolean) relation(intId("quoteLen"), asInt(0)).eval(123);
        int trimStart = (int) asInt(0).eval(124);
        int _jitmagicLoopLimiter3 = 0;
        while ((boolean) relation(intId("pos"), intId("len")).eval(180) && _jitmagicLoopLimiter3++ < 1000) {
            if ((boolean) boolId("quoting").eval(125)) {
                if (isQuote(refId(char[].class, "srcChars").eval(159), (int) intId("pos").eval(160), (int) intId("len").eval(161), (int) intId("quoteStart").eval(162), (int) intId("quoteLen").eval(163))) {
                    if (isQuote(refId(char[].class, "srcChars").eval(164), (int) arithmetic(intId("pos"), intId("quoteLen"), ADD).eval(165), (int) intId("len").eval(166), (int) intId("quoteStart").eval(167), (int) intId("quoteLen").eval(168))) {
                        refId(org.apache.commons.text.StrBuilder.class, "workArea").eval(172).append(refId(char[].class, "srcChars").eval(169), (int) intId("pos").eval(170), (int) intId("quoteLen").eval(171));
                        pos += (int) arithmetic(intId("quoteLen"), asInt(2), MUL).eval(173);
                        trimStart = refId(org.apache.commons.text.StrBuilder.class, "workArea").eval(174).size();
                        continue;
                    }
                    quoting = (boolean) asBool(false).eval(175);
                    pos += (int) intId("quoteLen").eval(176);
                    continue;
                }
            } else {
                final int delimLen = getDelimiterMatcher().isMatch(refId(char[].class, "srcChars").eval(126), (int) intId("pos").eval(127), (int) intId("start").eval(128), (int) intId("len").eval(129));
                if ((boolean) relation(intId("delimLen"), asInt(0)).eval(130)) {
                    addToken(tokenList, refId(org.apache.commons.text.StrBuilder.class, "workArea").eval(133).substring((int) asInt(0).eval(131), (int) intId("trimStart").eval(132)));
                    return (int) arithmetic(intId("pos"), intId("delimLen"), ADD).eval(134);
                }
                if ((boolean) relation(intId("quoteLen"), asInt(0)).eval(135) && isQuote(refId(char[].class, "srcChars").eval(136), (int) intId("pos").eval(137), (int) intId("len").eval(138), (int) intId("quoteStart").eval(139), (int) intId("quoteLen").eval(140))) {
                    quoting = (boolean) asBool(true).eval(141);
                    pos += (int) intId("quoteLen").eval(142);
                    continue;
                }
                final int ignoredLen = getIgnoredMatcher().isMatch(refId(char[].class, "srcChars").eval(143), (int) intId("pos").eval(144), (int) intId("start").eval(145), (int) intId("len").eval(146));
                if ((boolean) relation(intId("ignoredLen"), asInt(0)).eval(147)) {
                    pos += (int) intId("ignoredLen").eval(148);
                    continue;
                }
                final int trimmedLen = getTrimmerMatcher().isMatch(refId(char[].class, "srcChars").eval(149), (int) intId("pos").eval(150), (int) intId("start").eval(151), (int) intId("len").eval(152));
                if ((boolean) relation(intId("trimmedLen"), asInt(0)).eval(153)) {
                    refId(org.apache.commons.text.StrBuilder.class, "workArea").eval(157).append(refId(char[].class, "srcChars").eval(154), (int) intId("pos").eval(155), (int) intId("trimmedLen").eval(156));
                    pos += (int) intId("trimmedLen").eval(158);
                    continue;
                }
            }
            refId(org.apache.commons.text.StrBuilder.class, "workArea").eval(178).append((char) charArrAccessExp(refId(char[].class, "srcChars")).eval(177));
            trimStart = refId(org.apache.commons.text.StrBuilder.class, "workArea").eval(179).size();
        }
        addToken(tokenList, refId(org.apache.commons.text.StrBuilder.class, "workArea").eval(183).substring((int) asInt(0).eval(181), (int) intId("trimStart").eval(182)));
        return (int) asInt(-1).eval(184);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() is unsupported");
    }

    public StrTokenizer reset() {
        tokenPos = (int) 0;
        tokens = null;
        return this;
    }

    public StrTokenizer reset(final char[] input) {
        reset();
        if (refId(char[].class, "input").eval(186) != null) {
            this.chars = refId(char[].class, "input").eval(187).clone();
        } else {
            this.chars = null;
        }
        return this;
    }

    public StrTokenizer reset(final String input) {
        reset();
        if (input != null) {
            this.chars = input.toCharArray();
        } else {
            this.chars = null;
        }
        return this;
    }

    @Override
    public void set(final String obj) {
        throw new UnsupportedOperationException("set() is unsupported");
    }

    public StrTokenizer setDelimiterChar(final char delim) {
        return setDelimiterMatcher(StrMatcher.charMatcher((char) charId("delim").eval(190)));
    }

    public StrTokenizer setDelimiterMatcher(final StrMatcher delim) {
        if (delim == null) {
            this.delimMatcher = StrMatcher.noneMatcher();
        } else {
            this.delimMatcher = delim;
        }
        return this;
    }

    public StrTokenizer setDelimiterString(final String delim) {
        return setDelimiterMatcher(StrMatcher.stringMatcher(refId(java.lang.String.class, "delim").eval(193)));
    }

    public StrTokenizer setEmptyTokenAsNull(final boolean emptyAsNull) {
        this.emptyAsNull = (boolean) emptyAsNull;
        return this;
    }

    public StrTokenizer setIgnoredChar(final char ignored) {
        return setIgnoredMatcher(StrMatcher.charMatcher((char) charId("ignored").eval(195)));
    }

    public StrTokenizer setIgnoredMatcher(final StrMatcher ignored) {
        if (ignored != null) {
            this.ignoredMatcher = ignored;
        }
        return this;
    }

    public StrTokenizer setIgnoreEmptyTokens(final boolean ignoreEmptyTokens) {
        this.ignoreEmptyTokens = (boolean) ignoreEmptyTokens;
        return this;
    }

    public StrTokenizer setQuoteChar(final char quote) {
        return setQuoteMatcher(StrMatcher.charMatcher((char) charId("quote").eval(199)));
    }

    public StrTokenizer setQuoteMatcher(final StrMatcher quote) {
        if (quote != null) {
            this.quoteMatcher = quote;
        }
        return this;
    }

    public StrTokenizer setTrimmerMatcher(final StrMatcher trimmer) {
        if (trimmer != null) {
            this.trimmerMatcher = trimmer;
        }
        return this;
    }

    public int size() {
        checkTokenized();
        return tokens.length;
    }

    protected List<String> tokenize(final char[] srcChars, final int offset, final int count) {
        if (srcChars == null || (boolean) (count < 0)) {
            return Collections.emptyList();
        }
        final StrBuilder buf = new StrBuilder();
        final List<String> tokenList = new ArrayList<>();
        int pos = (int) offset;
        int _jitmagicLoopLimiter4 = 0;
        while ((boolean) ((pos == 0) || (pos < count)) && _jitmagicLoopLimiter4++ < 1000) {
            pos = readNextToken(srcChars, (int) pos, (int) count, buf, tokenList);
            if ((boolean) (pos >= count)) {
                addToken(tokenList, StringUtils.EMPTY);
            }
        }
        return tokenList;
    }

    @Override
    public String toString() {
        if (refId(java.lang.String[].class, "tokens").eval(213) == null) {
            return "StrTokenizer[not tokenized yet]";
        }
        return "StrTokenizer" + getTokenList();
    }

    @jattack.annotation.Arguments
    public static Object[] _jitmagicArgs() throws Throwable {
        org.apache.commons.text.StrTokenizer strTokenizer1 = org.apache.commons.text.StrTokenizer.getTSVInstance("4a44  hi!\n10.0\n1\n1.0");
        return new Object[] { strTokenizer1 };
    }

    public static long main0(String[] args) {
        org.csutil.Helper.parseArgs(args);
        int N = Math.min(org.csutil.Helper.nItrs, 100000);
        WrappedChecksum cs = new WrappedChecksum(true);
        try {
            Object[] eArgs = _jitmagicArgs();
            cs.update(eArgs);
            StrTokenizer rcvr = (StrTokenizer) eArgs[0];
            for (int i = 0; i < N; ++i) {
                try {
                    cs.update(rcvr.previousToken());
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
        cs.updateStaticFieldsOfClass(StrTokenizer.class);
        return cs.getValue();
    }

    public static void main(String[] args) {
        org.csutil.Helper.write(main0(args));
    }
}
