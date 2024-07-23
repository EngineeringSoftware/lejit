/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

/**
 * Tokenizes a string based on delimiters (separators)
 * and supporting quoting and ignored character concepts.
 * <p>
 * This class can split a String into many smaller strings. It aims
 * to do a similar job to {@link java.util.StringTokenizer StringTokenizer},
 * however it offers much more control and flexibility including implementing
 * the {@code ListIterator} interface. By default, it is set up
 * like {@code StringTokenizer}.
 * <p>
 * The input String is split into a number of <i>tokens</i>.
 * Each token is separated from the next String by a <i>delimiter</i>.
 * One or more delimiter characters must be specified.
 * <p>
 * Each token may be surrounded by quotes.
 * The <i>quote</i> matcher specifies the quote character(s).
 * A quote may be escaped within a quoted section by duplicating itself.
 * <p>
 * Between each token and the delimiter are potentially characters that need trimming.
 * The <i>trimmer</i> matcher specifies these characters.
 * One usage might be to trim whitespace characters.
 * <p>
 * At any point outside the quotes there might potentially be invalid characters.
 * The <i>ignored</i> matcher specifies these characters to be removed.
 * One usage might be to remove new line characters.
 * <p>
 * Empty tokens may be removed or returned as null.
 * <pre>
 * "a,b,c"         - Three tokens "a","b","c"   (comma delimiter)
 * " a, b , c "    - Three tokens "a","b","c"   (default CSV processing trims whitespace)
 * "a, ", b ,", c" - Three tokens "a, " , " b ", ", c" (quoted text untouched)
 * </pre>
 *
 * <table>
 *  <caption>StrTokenizer properties and options</caption>
 *  <tr>
 *   <th>Property</th><th>Type</th><th>Default</th>
 *  </tr>
 *  <tr>
 *   <td>delim</td><td>CharSetMatcher</td><td>{ \t\n\r\f}</td>
 *  </tr>
 *  <tr>
 *   <td>quote</td><td>NoneMatcher</td><td>{}</td>
 *  </tr>
 *  <tr>
 *   <td>ignore</td><td>NoneMatcher</td><td>{}</td>
 *  </tr>
 *  <tr>
 *   <td>emptyTokenAsNull</td><td>boolean</td><td>false</td>
 *  </tr>
 *  <tr>
 *   <td>ignoreEmptyTokens</td><td>boolean</td><td>true</td>
 *  </tr>
 * </table>
 *
 * @since 1.0
 * @deprecated Deprecated as of 1.3, use {@link StringTokenizer} instead. This class will be removed in 2.0.
 */
@Deprecated
public class StrTokenizer implements ListIterator<String>, Cloneable {

    /**
     * Comma separated values tokenizer internal variable.
     */
    private static final StrTokenizer CSV_TOKENIZER_PROTOTYPE;

    /**
     * Tab separated values tokenizer internal variable.
     */
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

    /**
     * Returns a clone of {@code CSV_TOKENIZER_PROTOTYPE}.
     *
     * @return a clone of {@code CSV_TOKENIZER_PROTOTYPE}.
     */
    private static StrTokenizer getCSVClone() {
        return (StrTokenizer) refId(org.apache.commons.text.StrTokenizer.class, "CSV_TOKENIZER_PROTOTYPE").eval().clone();
    }

    /**
     * Gets a new tokenizer instance which parses Comma Separated Value strings
     * initializing it with the given input.  The default for CSV processing
     * will be trim whitespace from both ends (which can be overridden with
     * the setTrimmer method).
     * <p>
     * You must call a "reset" method to set the string which you want to parse.
     * </p>
     * @return a new tokenizer instance which parses Comma Separated Value strings
     */
    public static StrTokenizer getCSVInstance() {
        return getCSVClone();
    }

    /**
     * Gets a new tokenizer instance which parses Comma Separated Value strings
     * initializing it with the given input.  The default for CSV processing
     * will be trim whitespace from both ends (which can be overridden with
     * the setTrimmer method).
     *
     * @param input  the text to parse
     * @return a new tokenizer instance which parses Comma Separated Value strings
     */
    public static StrTokenizer getCSVInstance(final char[] input) {
        final StrTokenizer tok = getCSVClone();
        refId(org.apache.commons.text.StrTokenizer.class, "tok").eval().reset(refId(char[].class, "input").eval());
        return refId(org.apache.commons.text.StrTokenizer.class, "tok").eval();
    }

    /**
     * Gets a new tokenizer instance which parses Comma Separated Value strings
     * initializing it with the given input.  The default for CSV processing
     * will be trim whitespace from both ends (which can be overridden with
     * the setTrimmer method).
     *
     * @param input  the text to parse
     * @return a new tokenizer instance which parses Comma Separated Value strings
     */
    public static StrTokenizer getCSVInstance(final String input) {
        final StrTokenizer tok = getCSVClone();
        refId(org.apache.commons.text.StrTokenizer.class, "tok").eval().reset(refId(java.lang.String.class, "input").eval());
        return refId(org.apache.commons.text.StrTokenizer.class, "tok").eval();
    }

    /**
     * Returns a clone of {@code TSV_TOKENIZER_PROTOTYPE}.
     *
     * @return a clone of {@code TSV_TOKENIZER_PROTOTYPE}.
     */
    private static StrTokenizer getTSVClone() {
        return (StrTokenizer) refId(org.apache.commons.text.StrTokenizer.class, "TSV_TOKENIZER_PROTOTYPE").eval().clone();
    }

    /**
     * Gets a new tokenizer instance which parses Tab Separated Value strings.
     * The default for CSV processing will be trim whitespace from both ends
     * (which can be overridden with the setTrimmer method).
     * <p>
     * You must call a "reset" method to set the string which you want to parse.
     * </p>
     * @return a new tokenizer instance which parses Tab Separated Value strings.
     */
    public static StrTokenizer getTSVInstance() {
        return getTSVClone();
    }

    /**
     * Gets a new tokenizer instance which parses Tab Separated Value strings.
     * The default for CSV processing will be trim whitespace from both ends
     * (which can be overridden with the setTrimmer method).
     * @param input  the string to parse
     * @return a new tokenizer instance which parses Tab Separated Value strings.
     */
    public static StrTokenizer getTSVInstance(final char[] input) {
        final StrTokenizer tok = getTSVClone();
        refId(org.apache.commons.text.StrTokenizer.class, "tok").eval().reset(refId(char[].class, "input").eval());
        return refId(org.apache.commons.text.StrTokenizer.class, "tok").eval();
    }

    /**
     * Gets a new tokenizer instance which parses Tab Separated Value strings.
     * The default for CSV processing will be trim whitespace from both ends
     * (which can be overridden with the setTrimmer method).
     * @param input  the string to parse
     * @return a new tokenizer instance which parses Tab Separated Value strings.
     */
    public static StrTokenizer getTSVInstance(final String input) {
        final StrTokenizer tok = getTSVClone();
        refId(org.apache.commons.text.StrTokenizer.class, "tok").eval().reset(refId(java.lang.String.class, "input").eval());
        return refId(org.apache.commons.text.StrTokenizer.class, "tok").eval();
    }

    /**
     * The text to work on.
     */
    private char[] chars;

    /**
     * The parsed tokens.
     */
    private String[] tokens;

    /**
     * The current iteration position.
     */
    private int tokenPos;

    /**
     * The delimiter matcher.
     */
    private StrMatcher delimMatcher = StrMatcher.splitMatcher();

    /**
     * The quote matcher.
     */
    private StrMatcher quoteMatcher = StrMatcher.noneMatcher();

    /**
     * The ignored matcher.
     */
    private StrMatcher ignoredMatcher = StrMatcher.noneMatcher();

    /**
     * The trimmer matcher.
     */
    private StrMatcher trimmerMatcher = StrMatcher.noneMatcher();

    /**
     * Whether to return empty tokens as null.
     */
    private boolean emptyAsNull;

    /**
     * Whether to ignore empty tokens.
     */
    private boolean ignoreEmptyTokens = true;

    /**
     * Constructs a tokenizer splitting on space, tab, newline and form feed
     * as per StringTokenizer, but with no text to tokenize.
     * <p>
     * This constructor is normally used with {@link #reset(String)}.
     * </p>
     */
    public StrTokenizer() {
        this.chars = null;
    }

    /**
     * Constructs a tokenizer splitting on space, tab, newline and form feed
     * as per StringTokenizer.
     *
     * @param input  the string which is to be parsed, not cloned
     */
    public StrTokenizer(final char[] input) {
        if (refId(char[].class, "input").eval() == null) {
            this.chars = null;
        } else {
            this.chars = refId(char[].class, "input").eval().clone();
        }
    }

    /**
     * Constructs a tokenizer splitting on the specified character.
     *
     * @param input  the string which is to be parsed, not cloned
     * @param delim the field delimiter character
     */
    public StrTokenizer(final char[] input, final char delim) {
        this(refId(char[].class, "input").eval());
        setDelimiterChar((char) charId("delim").eval());
    }

    /**
     * Constructs a tokenizer splitting on the specified delimiter character
     * and handling quotes using the specified quote character.
     *
     * @param input  the string which is to be parsed, not cloned
     * @param delim  the field delimiter character
     * @param quote  the field quoted string character
     */
    public StrTokenizer(final char[] input, final char delim, final char quote) {
        this(refId(char[].class, "input").eval(), (char) charId("delim").eval());
        setQuoteChar((char) charId("quote").eval());
    }

    /**
     * Constructs a tokenizer splitting on the specified string.
     *
     * @param input  the string which is to be parsed, not cloned
     * @param delim the field delimiter string
     */
    public StrTokenizer(final char[] input, final String delim) {
        this(refId(char[].class, "input").eval());
        setDelimiterString(refId(java.lang.String.class, "delim").eval());
    }

    /**
     * Constructs a tokenizer splitting using the specified delimiter matcher.
     *
     * @param input  the string which is to be parsed, not cloned
     * @param delim  the field delimiter matcher
     */
    public StrTokenizer(final char[] input, final StrMatcher delim) {
        this(refId(char[].class, "input").eval());
        setDelimiterMatcher(refId(org.apache.commons.text.StrMatcher.class, "delim").eval());
    }

    /**
     * Constructs a tokenizer splitting using the specified delimiter matcher
     * and handling quotes using the specified quote matcher.
     *
     * @param input  the string which is to be parsed, not cloned
     * @param delim  the field delimiter character
     * @param quote  the field quoted string character
     */
    public StrTokenizer(final char[] input, final StrMatcher delim, final StrMatcher quote) {
        this(refId(char[].class, "input").eval(), refId(org.apache.commons.text.StrMatcher.class, "delim").eval());
        setQuoteMatcher(refId(org.apache.commons.text.StrMatcher.class, "quote").eval());
    }

    /**
     * Constructs a tokenizer splitting on space, tab, newline and form feed
     * as per StringTokenizer.
     *
     * @param input  the string which is to be parsed
     */
    public StrTokenizer(final String input) {
        if (refId(java.lang.String.class, "input").eval() != null) {
            chars = refId(java.lang.String.class, "input").eval().toCharArray();
        } else {
            chars = null;
        }
    }

    /**
     * Constructs a tokenizer splitting on the specified delimiter character.
     *
     * @param input  the string which is to be parsed
     * @param delim  the field delimiter character
     */
    public StrTokenizer(final String input, final char delim) {
        this(refId(java.lang.String.class, "input").eval());
        setDelimiterChar((char) charId("delim").eval());
    }

    /**
     * Constructs a tokenizer splitting on the specified delimiter character
     * and handling quotes using the specified quote character.
     *
     * @param input  the string which is to be parsed
     * @param delim  the field delimiter character
     * @param quote  the field quoted string character
     */
    public StrTokenizer(final String input, final char delim, final char quote) {
        this(refId(java.lang.String.class, "input").eval(), (char) charId("delim").eval());
        setQuoteChar((char) charId("quote").eval());
    }

    /**
     * Constructs a tokenizer splitting on the specified delimiter string.
     *
     * @param input  the string which is to be parsed
     * @param delim  the field delimiter string
     */
    public StrTokenizer(final String input, final String delim) {
        this(refId(java.lang.String.class, "input").eval());
        setDelimiterString(refId(java.lang.String.class, "delim").eval());
    }

    /**
     * Constructs a tokenizer splitting using the specified delimiter matcher.
     *
     * @param input  the string which is to be parsed
     * @param delim  the field delimiter matcher
     */
    public StrTokenizer(final String input, final StrMatcher delim) {
        this(refId(java.lang.String.class, "input").eval());
        setDelimiterMatcher(refId(org.apache.commons.text.StrMatcher.class, "delim").eval());
    }

    /**
     * Constructs a tokenizer splitting using the specified delimiter matcher
     * and handling quotes using the specified quote matcher.
     *
     * @param input  the string which is to be parsed
     * @param delim  the field delimiter matcher
     * @param quote  the field quoted string matcher
     */
    public StrTokenizer(final String input, final StrMatcher delim, final StrMatcher quote) {
        this(refId(java.lang.String.class, "input").eval(), refId(org.apache.commons.text.StrMatcher.class, "delim").eval());
        setQuoteMatcher(refId(org.apache.commons.text.StrMatcher.class, "quote").eval());
    }

    /**
     * Unsupported ListIterator operation.
     * @param obj this parameter ignored.
     * @throws UnsupportedOperationException always
     */
    @Override
    public void add(final String obj) {
        throw new UnsupportedOperationException("add() is unsupported");
    }

    /**
     * Adds a token to a list, paying attention to the parameters we've set.
     *
     * @param list  the list to add to
     * @param tok  the token to add
     */
    private void addToken(final List<String> list, String tok) {
        if (refId(java.lang.String.class, "tok").eval() == null || refId(java.lang.String.class, "tok").eval().isEmpty()) {
            if (isIgnoreEmptyTokens()) {
                return;
            }
            if (isEmptyTokenAsNull()) {
                tok = null;
            }
        }
        list.add(refId(java.lang.String.class, "tok").eval());
    }

    /**
     * Checks if tokenization has been done, and if not then do it.
     */
    private void checkTokenized() {
        if (refId(java.lang.String[].class, "tokens").eval() == null) {
            if (refId(char[].class, "chars").eval() == null) {
                // still call tokenize as subclass may do some work
                final List<String> split = tokenize(null, (int) asInt(0).eval(), (int) asInt(0).eval());
                tokens = split.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
            } else {
                final List<String> split = tokenize(refId(char[].class, "chars").eval(), (int) asInt(0).eval(), chars.length);
                tokens = split.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
            }
        }
    }

    /**
     * Creates a new instance of this Tokenizer. The new instance is reset so
     * that it will be at the start of the token list.
     * If a {@link CloneNotSupportedException} is caught, return {@code null}.
     *
     * @return a new instance of this Tokenizer which has been reset.
     */
    @Override
    public Object clone() {
        try {
            return cloneReset();
        } catch (final CloneNotSupportedException ex) {
            return null;
        }
    }

    /**
     * Creates a new instance of this Tokenizer. The new instance is reset so that
     * it will be at the start of the token list.
     *
     * @return a new instance of this Tokenizer which has been reset.
     * @throws CloneNotSupportedException if there is a problem cloning
     */
    Object cloneReset() throws CloneNotSupportedException {
        // this method exists to enable 100% test coverage
        final StrTokenizer cloned = (StrTokenizer) super.clone();
        if (cloned.chars != null) {
            cloned.chars = cloned.chars.clone();
        }
        refId(org.apache.commons.text.StrTokenizer.class, "cloned").eval().reset();
        return refId(org.apache.commons.text.StrTokenizer.class, "cloned").eval();
    }

    /**
     * Gets the String content that the tokenizer is parsing.
     *
     * @return The string content being parsed
     */
    public String getContent() {
        if (refId(char[].class, "chars").eval() == null) {
            return null;
        }
        return new String(refId(char[].class, "chars").eval());
    }

    /**
     * Gets the field delimiter matcher.
     *
     * @return The delimiter matcher in use
     */
    public StrMatcher getDelimiterMatcher() {
        return this.delimMatcher;
    }

    /**
     * Gets the ignored character matcher.
     * <p>
     * These characters are ignored when parsing the String, unless they are
     * within a quoted region.
     * The default value is not to ignore anything.
     * </p>
     *
     * @return The ignored matcher in use
     */
    public StrMatcher getIgnoredMatcher() {
        return refId(org.apache.commons.text.StrMatcher.class, "ignoredMatcher").eval();
    }

    /**
     * Gets the quote matcher currently in use.
     * <p>
     * The quote character is used to wrap data between the tokens.
     * This enables delimiters to be entered as data.
     * The default value is '"' (double quote).
     * </p>
     *
     * @return The quote matcher in use
     */
    public StrMatcher getQuoteMatcher() {
        return refId(org.apache.commons.text.StrMatcher.class, "quoteMatcher").eval();
    }

    /**
     * Gets a copy of the full token list as an independent modifiable array.
     *
     * @return The tokens as a String array
     */
    public String[] getTokenArray() {
        checkTokenized();
        return refId(java.lang.String[].class, "tokens").eval().clone();
    }

    /**
     * Gets a copy of the full token list as an independent modifiable list.
     *
     * @return The tokens as a String array
     */
    public List<String> getTokenList() {
        checkTokenized();
        final List<String> list = new ArrayList<>(tokens.length);
        Collections.addAll(list, refId(java.lang.String[].class, "tokens").eval());
        return list;
    }

    /**
     * Gets the trimmer character matcher.
     * <p>
     * These characters are trimmed off on each side of the delimiter
     * until the token or quote is found.
     * The default value is not to trim anything.
     * </p>
     *
     * @return The trimmer matcher in use
     */
    public StrMatcher getTrimmerMatcher() {
        return refId(org.apache.commons.text.StrMatcher.class, "trimmerMatcher").eval();
    }

    /**
     * Checks whether there are any more tokens.
     *
     * @return true if there are more tokens
     */
    @Override
    public boolean hasNext() {
        checkTokenized();
        return (int) intId("tokenPos").eval() < tokens.length;
    }

    /**
     * Checks whether there are any previous tokens that can be iterated to.
     *
     * @return true if there are previous tokens
     */
    @Override
    public boolean hasPrevious() {
        checkTokenized();
        return (boolean) relation(intId("tokenPos"), asInt(0)).eval();
    }

    /**
     * Gets whether the tokenizer currently returns empty tokens as null.
     * The default for this property is false.
     *
     * @return true if empty tokens are returned as null
     */
    public boolean isEmptyTokenAsNull() {
        return this.emptyAsNull;
    }

    /**
     * Gets whether the tokenizer currently ignores empty tokens.
     * The default for this property is true.
     *
     * @return true if empty tokens are not returned
     */
    public boolean isIgnoreEmptyTokens() {
        return (boolean) boolId("ignoreEmptyTokens").eval();
    }

    /**
     * Checks if the characters at the index specified match the quote
     * already matched in readNextToken().
     *
     * @param srcChars  the character array being tokenized
     * @param pos  the position to check for a quote
     * @param len  the length of the character array being tokenized
     * @param quoteStart  the start position of the matched quote, 0 if no quoting
     * @param quoteLen  the length of the matched quote, 0 if no quoting
     * @return true if a quote is matched
     */
    private boolean isQuote(final char[] srcChars, final int pos, final int len, final int quoteStart, final int quoteLen) {
        int _jitmagicLoopLimiter1 = 0;
        for (int i = (int) asInt(0).eval(); (boolean) relation(intId("i"), intId("quoteLen")).eval() && _jitmagicLoopLimiter1++ < 1000; i++) {
            if ((boolean) logic(relation(arithmetic(intId("pos"), intId("i"), ADD), intId("len")), relation(cast(Integer.class, charArrAccessExp(refId(char[].class, "srcChars"), arithmetic(intId("pos"), intId("i"), ADD))), cast(Integer.class, charArrAccessExp(refId(char[].class, "srcChars"), arithmetic(intId("quoteStart"), intId("i"), ADD))))).eval()) {
                return (boolean) asBool(false).eval();
            }
        }
        return (boolean) asBool(true).eval();
    }

    /**
     * Gets the next token.
     *
     * @return The next String token
     * @throws NoSuchElementException if there are no more elements
     */
    @Override
    public String next() {
        if (hasNext()) {
            return refArrAccessExp(java.lang.String.class, java.lang.String[].class, refId(java.lang.String[].class, "tokens")).eval();
        }
        throw new NoSuchElementException();
    }

    /**
     * Gets the index of the next token to return.
     *
     * @return The next token index
     */
    @Override
    public int nextIndex() {
        return (int) intId("tokenPos").eval();
    }

    /**
     * Gets the next token from the String.
     * Equivalent to {@link #next()} except it returns null rather than
     * throwing {@link NoSuchElementException} when no tokens remain.
     *
     * @return The next sequential token, or null when no more tokens are found
     */
    public String nextToken() {
        if (hasNext()) {
            return refArrAccessExp(java.lang.String.class, java.lang.String[].class, refId(java.lang.String[].class, "tokens")).eval();
        }
        return null;
    }

    /**
     * Gets the token previous to the last returned token.
     *
     * @return The previous token
     */
    @Override
    public String previous() {
        if (hasPrevious()) {
            return refArrAccessExp(java.lang.String.class, java.lang.String[].class, refId(java.lang.String[].class, "tokens")).eval();
        }
        throw new NoSuchElementException();
    }

    /**
     * Gets the index of the previous token.
     *
     * @return The previous token index
     */
    @Override
    public int previousIndex() {
        return (int) arithmetic(intId("tokenPos"), asInt(1), SUB).eval();
    }

    /**
     * Gets the previous token from the String.
     *
     * @return The previous sequential token, or null when no more tokens are found
     */
    @jattack.annotation.Entry
    public String previousToken() {
        if (hasPrevious()) {
            return refArrAccessExp(java.lang.String.class, java.lang.String[].class, refId(java.lang.String[].class, "tokens")).eval();
        }
        return null;
    }

    /**
     * Reads character by character through the String to get the next token.
     *
     * @param srcChars  the character array being tokenized
     * @param start  the first character of field
     * @param len  the length of the character array being tokenized
     * @param workArea  a temporary work area
     * @param tokenList  the list of parsed tokens
     * @return The starting position of the next field (the character
     *  immediately after the delimiter), or -1 if end of string found
     */
    private int readNextToken(final char[] srcChars, int start, final int len, final StrBuilder workArea, final List<String> tokenList) {
        int _jitmagicLoopLimiter2 = 0;
        // skip all leading whitespace, unless it is the
        // field delimiter or the quote character
        while ((boolean) relation(intId("start"), intId("len")).eval() && _jitmagicLoopLimiter2++ < 1000) {
            final int removeLen = Math.max(getIgnoredMatcher().isMatch(refId(char[].class, "srcChars").eval(), (int) intId("start").eval(), (int) intId("start").eval(), (int) intId("len").eval()), getTrimmerMatcher().isMatch(refId(char[].class, "srcChars").eval(), (int) intId("start").eval(), (int) intId("start").eval(), (int) intId("len").eval()));
            if ((boolean) relation(intId("removeLen"), asInt(0)).eval() || getDelimiterMatcher().isMatch(refId(char[].class, "srcChars").eval(), (int) intId("start").eval(), (int) intId("start").eval(), (int) intId("len").eval()) > (int) asInt(0).eval() || getQuoteMatcher().isMatch(refId(char[].class, "srcChars").eval(), (int) intId("start").eval(), (int) intId("start").eval(), (int) intId("len").eval()) > (int) asInt(0).eval()) {
                break;
            }
            start += (int) intId("removeLen").eval();
        }
        // handle reaching end
        if ((boolean) relation(intId("start"), intId("len")).eval()) {
            addToken(tokenList, StringUtils.EMPTY);
            return (int) asInt(-1).eval();
        }
        // handle empty token
        final int delimLen = getDelimiterMatcher().isMatch(refId(char[].class, "srcChars").eval(), (int) intId("start").eval(), (int) intId("start").eval(), (int) intId("len").eval());
        if ((boolean) relation(intId("delimLen"), asInt(0)).eval()) {
            addToken(tokenList, StringUtils.EMPTY);
            return (int) arithmetic(intId("start"), intId("delimLen"), ADD).eval();
        }
        // handle found token
        final int quoteLen = getQuoteMatcher().isMatch(refId(char[].class, "srcChars").eval(), (int) intId("start").eval(), (int) intId("start").eval(), (int) intId("len").eval());
        if ((boolean) relation(intId("quoteLen"), asInt(0)).eval()) {
            return readWithQuotes(refId(char[].class, "srcChars").eval(), (int) arithmetic(intId("start"), intId("quoteLen"), ADD).eval(), (int) intId("len").eval(), refId(org.apache.commons.text.StrBuilder.class, "workArea").eval(), tokenList, (int) intId("start").eval(), (int) intId("quoteLen").eval());
        }
        return readWithQuotes(refId(char[].class, "srcChars").eval(), (int) intId("start").eval(), (int) intId("len").eval(), refId(org.apache.commons.text.StrBuilder.class, "workArea").eval(), tokenList, (int) asInt(0).eval(), (int) asInt(0).eval());
    }

    /**
     * Reads a possibly quoted string token.
     *
     * @param srcChars  the character array being tokenized
     * @param start  the first character of field
     * @param len  the length of the character array being tokenized
     * @param workArea  a temporary work area
     * @param tokenList  the list of parsed tokens
     * @param quoteStart  the start position of the matched quote, 0 if no quoting
     * @param quoteLen  the length of the matched quote, 0 if no quoting
     * @return The starting position of the next field (the character
     *  immediately after the delimiter, or if end of string found,
     *  then the length of string
     */
    private int readWithQuotes(final char[] srcChars, final int start, final int len, final StrBuilder workArea, final List<String> tokenList, final int quoteStart, final int quoteLen) {
        // Loop until we've found the end of the quoted
        // string or the end of the input
        refId(org.apache.commons.text.StrBuilder.class, "workArea").eval().clear();
        int pos = (int) intId("start").eval();
        boolean quoting = (boolean) relation(intId("quoteLen"), asInt(0)).eval();
        int trimStart = (int) asInt(0).eval();
        int _jitmagicLoopLimiter3 = 0;
        while ((boolean) relation(intId("pos"), intId("len")).eval() && _jitmagicLoopLimiter3++ < 1000) {
            // quoting mode can occur several times throughout a string
            // we must switch between quoting and non-quoting until we
            // encounter a non-quoted delimiter, or end of string
            if ((boolean) boolId("quoting").eval()) {
                // In quoting mode
                // If we've found a quote character, see if it's
                // followed by a second quote.  If so, then we need
                // to actually put the quote character into the token
                // rather than end the token.
                if (isQuote(refId(char[].class, "srcChars").eval(), (int) intId("pos").eval(), (int) intId("len").eval(), (int) intId("quoteStart").eval(), (int) intId("quoteLen").eval())) {
                    if (isQuote(refId(char[].class, "srcChars").eval(), (int) arithmetic(intId("pos"), intId("quoteLen"), ADD).eval(), (int) intId("len").eval(), (int) intId("quoteStart").eval(), (int) intId("quoteLen").eval())) {
                        // matched pair of quotes, thus an escaped quote
                        refId(org.apache.commons.text.StrBuilder.class, "workArea").eval().append(refId(char[].class, "srcChars").eval(), (int) intId("pos").eval(), (int) intId("quoteLen").eval());
                        pos += (int) arithmetic(intId("quoteLen"), asInt(2), MUL).eval();
                        trimStart = refId(org.apache.commons.text.StrBuilder.class, "workArea").eval().size();
                        continue;
                    }
                    // end of quoting
                    quoting = (boolean) asBool(false).eval();
                    pos += (int) intId("quoteLen").eval();
                    continue;
                }
            } else {
                // Not in quoting mode
                // check for delimiter, and thus end of token
                final int delimLen = getDelimiterMatcher().isMatch(refId(char[].class, "srcChars").eval(), (int) intId("pos").eval(), (int) intId("start").eval(), (int) intId("len").eval());
                if ((boolean) relation(intId("delimLen"), asInt(0)).eval()) {
                    // return condition when end of token found
                    addToken(tokenList, refId(org.apache.commons.text.StrBuilder.class, "workArea").eval().substring((int) asInt(0).eval(), (int) intId("trimStart").eval()));
                    return (int) arithmetic(intId("pos"), intId("delimLen"), ADD).eval();
                }
                // check for quote, and thus back into quoting mode
                if ((boolean) relation(intId("quoteLen"), asInt(0)).eval() && isQuote(refId(char[].class, "srcChars").eval(), (int) intId("pos").eval(), (int) intId("len").eval(), (int) intId("quoteStart").eval(), (int) intId("quoteLen").eval())) {
                    quoting = (boolean) asBool(true).eval();
                    pos += (int) intId("quoteLen").eval();
                    continue;
                }
                // check for ignored (outside quotes), and ignore
                final int ignoredLen = getIgnoredMatcher().isMatch(refId(char[].class, "srcChars").eval(), (int) intId("pos").eval(), (int) intId("start").eval(), (int) intId("len").eval());
                if ((boolean) relation(intId("ignoredLen"), asInt(0)).eval()) {
                    pos += (int) intId("ignoredLen").eval();
                    continue;
                }
                // check for trimmed character
                // don't yet know if its at the end, so copy to workArea
                // use trimStart to keep track of trim at the end
                final int trimmedLen = getTrimmerMatcher().isMatch(refId(char[].class, "srcChars").eval(), (int) intId("pos").eval(), (int) intId("start").eval(), (int) intId("len").eval());
                if ((boolean) relation(intId("trimmedLen"), asInt(0)).eval()) {
                    refId(org.apache.commons.text.StrBuilder.class, "workArea").eval().append(refId(char[].class, "srcChars").eval(), (int) intId("pos").eval(), (int) intId("trimmedLen").eval());
                    pos += (int) intId("trimmedLen").eval();
                    continue;
                }
            }
            // copy regular character from inside quotes
            refId(org.apache.commons.text.StrBuilder.class, "workArea").eval().append((char) charArrAccessExp(refId(char[].class, "srcChars")).eval());
            trimStart = refId(org.apache.commons.text.StrBuilder.class, "workArea").eval().size();
        }
        // return condition when end of string found
        addToken(tokenList, refId(org.apache.commons.text.StrBuilder.class, "workArea").eval().substring((int) asInt(0).eval(), (int) intId("trimStart").eval()));
        return (int) asInt(-1).eval();
    }

    /**
     * Unsupported ListIterator operation.
     *
     * @throws UnsupportedOperationException always
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() is unsupported");
    }

    /**
     * Resets this tokenizer, forgetting all parsing and iteration already completed.
     * <p>
     * This method allows the same tokenizer to be reused for the same String.
     *
     * @return this, to enable chaining
     */
    public StrTokenizer reset() {
        tokenPos = (int) asInt(0).eval();
        tokens = null;
        return this;
    }

    /**
     * Reset this tokenizer, giving it a new input string to parse.
     * In this manner you can re-use a tokenizer with the same settings
     * on multiple input lines.
     *
     * @param input  the new character array to tokenize, not cloned, null sets no text to parse
     * @return this, to enable chaining
     */
    public StrTokenizer reset(final char[] input) {
        reset();
        if (refId(char[].class, "input").eval() != null) {
            this.chars = refId(char[].class, "input").eval().clone();
        } else {
            this.chars = null;
        }
        return this;
    }

    /**
     * Reset this tokenizer, giving it a new input string to parse.
     * In this manner you can re-use a tokenizer with the same settings
     * on multiple input lines.
     *
     * @param input  the new string to tokenize, null sets no text to parse
     * @return this, to enable chaining
     */
    public StrTokenizer reset(final String input) {
        reset();
        if (refId(java.lang.String.class, "input").eval() != null) {
            this.chars = refId(java.lang.String.class, "input").eval().toCharArray();
        } else {
            this.chars = null;
        }
        return this;
    }

    /**
     * Unsupported ListIterator operation.
     * @param obj this parameter ignored.
     * @throws UnsupportedOperationException always
     */
    @Override
    public void set(final String obj) {
        throw new UnsupportedOperationException("set() is unsupported");
    }

    /**
     * Sets the field delimiter character.
     *
     * @param delim  the delimiter character to use
     * @return this, to enable chaining
     */
    public StrTokenizer setDelimiterChar(final char delim) {
        return setDelimiterMatcher(StrMatcher.charMatcher((char) charId("delim").eval()));
    }

    /**
     * Sets the field delimiter matcher.
     * <p>
     * The delimiter is used to separate one token from another.
     * </p>
     *
     * @param delim  the delimiter matcher to use
     * @return this, to enable chaining
     */
    public StrTokenizer setDelimiterMatcher(final StrMatcher delim) {
        if (refId(org.apache.commons.text.StrMatcher.class, "delim").eval() == null) {
            this.delimMatcher = StrMatcher.noneMatcher();
        } else {
            this.delimMatcher = refId(org.apache.commons.text.StrMatcher.class, "delim").eval();
        }
        return this;
    }

    /**
     * Sets the field delimiter string.
     *
     * @param delim  the delimiter string to use
     * @return this, to enable chaining
     */
    public StrTokenizer setDelimiterString(final String delim) {
        return setDelimiterMatcher(StrMatcher.stringMatcher(refId(java.lang.String.class, "delim").eval()));
    }

    /**
     * Sets whether the tokenizer should return empty tokens as null.
     * The default for this property is false.
     *
     * @param emptyAsNull  whether empty tokens are returned as null
     * @return this, to enable chaining
     */
    public StrTokenizer setEmptyTokenAsNull(final boolean emptyAsNull) {
        this.emptyAsNull = (boolean) boolId("emptyAsNull").eval();
        return this;
    }

    /**
     * Set the character to ignore.
     * <p>
     * This character is ignored when parsing the String, unless it is
     * within a quoted region.
     * </p>
     *
     * @param ignored  the ignored character to use
     * @return this, to enable chaining
     */
    public StrTokenizer setIgnoredChar(final char ignored) {
        return setIgnoredMatcher(StrMatcher.charMatcher((char) charId("ignored").eval()));
    }

    /**
     * Set the matcher for characters to ignore.
     * <p>
     * These characters are ignored when parsing the String, unless they are
     * within a quoted region.
     * </p>
     *
     * @param ignored  the ignored matcher to use, null ignored
     * @return this, to enable chaining
     */
    public StrTokenizer setIgnoredMatcher(final StrMatcher ignored) {
        if (refId(org.apache.commons.text.StrMatcher.class, "ignored").eval() != null) {
            this.ignoredMatcher = refId(org.apache.commons.text.StrMatcher.class, "ignored").eval();
        }
        return this;
    }

    /**
     * Sets whether the tokenizer should ignore and not return empty tokens.
     * The default for this property is true.
     *
     * @param ignoreEmptyTokens  whether empty tokens are not returned
     * @return this, to enable chaining
     */
    public StrTokenizer setIgnoreEmptyTokens(final boolean ignoreEmptyTokens) {
        this.ignoreEmptyTokens = (boolean) boolId("ignoreEmptyTokens").eval();
        return this;
    }

    /**
     * Sets the quote character to use.
     * <p>
     * The quote character is used to wrap data between the tokens.
     * This enables delimiters to be entered as data.
     * </p>
     *
     * @param quote  the quote character to use
     * @return this, to enable chaining
     */
    public StrTokenizer setQuoteChar(final char quote) {
        return setQuoteMatcher(StrMatcher.charMatcher((char) charId("quote").eval()));
    }

    /**
     * Set the quote matcher to use.
     * <p>
     * The quote character is used to wrap data between the tokens.
     * This enables delimiters to be entered as data.
     * </p>
     *
     * @param quote  the quote matcher to use, null ignored
     * @return this, to enable chaining
     */
    public StrTokenizer setQuoteMatcher(final StrMatcher quote) {
        if (refId(org.apache.commons.text.StrMatcher.class, "quote").eval() != null) {
            this.quoteMatcher = refId(org.apache.commons.text.StrMatcher.class, "quote").eval();
        }
        return this;
    }

    /**
     * Sets the matcher for characters to trim.
     * <p>
     * These characters are trimmed off on each side of the delimiter
     * until the token or quote is found.
     * </p>
     *
     * @param trimmer  the trimmer matcher to use, null ignored
     * @return this, to enable chaining
     */
    public StrTokenizer setTrimmerMatcher(final StrMatcher trimmer) {
        if (refId(org.apache.commons.text.StrMatcher.class, "trimmer").eval() != null) {
            this.trimmerMatcher = refId(org.apache.commons.text.StrMatcher.class, "trimmer").eval();
        }
        return this;
    }

    /**
     * Gets the number of tokens found in the String.
     *
     * @return The number of matched tokens
     */
    public int size() {
        checkTokenized();
        return tokens.length;
    }

    /**
     * Internal method to performs the tokenization.
     * <p>
     * Most users of this class do not need to call this method. This method
     * will be called automatically by other (public) methods when required.
     * </p>
     * <p>
     * This method exists to allow subclasses to add code before or after the
     * tokenization. For example, a subclass could alter the character array,
     * offset or count to be parsed, or call the tokenizer multiple times on
     * multiple strings. It is also be possible to filter the results.
     * </p>
     * <p>
     * {@code StrTokenizer} will always pass a zero offset and a count
     * equal to the length of the array to this method, however a subclass
     * may pass other values, or even an entirely different array.
     * </p>
     *
     * @param srcChars  the character array being tokenized, may be null
     * @param offset  the start position within the character array, must be valid
     * @param count  the number of characters to tokenize, must be valid
     * @return The modifiable list of String tokens, unmodifiable if null array or zero count
     */
    protected List<String> tokenize(final char[] srcChars, final int offset, final int count) {
        if (refId(char[].class, "srcChars").eval() == null || (boolean) relation(intId("count"), asInt(0)).eval()) {
            return Collections.emptyList();
        }
        final StrBuilder buf = new StrBuilder();
        final List<String> tokenList = new ArrayList<>();
        int pos = (int) intId("offset").eval();
        int _jitmagicLoopLimiter4 = 0;
        // loop around the entire buffer
        while ((boolean) logic(relation(intId("pos"), asInt(0)), relation(intId("pos"), intId("count"))).eval() && _jitmagicLoopLimiter4++ < 1000) {
            // find next token
            pos = readNextToken(refId(char[].class, "srcChars").eval(), (int) intId("pos").eval(), (int) intId("count").eval(), refId(org.apache.commons.text.StrBuilder.class, "buf").eval(), tokenList);
            // handle case where end of string is a delimiter
            if ((boolean) relation(intId("pos"), intId("count")).eval()) {
                addToken(tokenList, StringUtils.EMPTY);
            }
        }
        return tokenList;
    }

    /**
     * Gets the String content that the tokenizer is parsing.
     *
     * @return The string content being parsed
     */
    @Override
    public String toString() {
        if (refId(java.lang.String[].class, "tokens").eval() == null) {
            return "StrTokenizer[not tokenized yet]";
        }
        return "StrTokenizer" + getTokenList();
    }

    @jattack.annotation.Arguments
    public static Object[] _jitmagicArgs() throws Throwable {
        org.apache.commons.text.StrTokenizer strTokenizer1 = org.apache.commons.text.StrTokenizer.getTSVInstance("4a44  hi!\n10.0\n1\n1.0");
        return new Object[] { strTokenizer1 };
    }
}
