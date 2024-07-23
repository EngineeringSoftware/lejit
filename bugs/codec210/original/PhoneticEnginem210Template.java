package org.apache.commons.codec.language.bm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.codec.language.bm.Languages.LanguageSet;
import org.apache.commons.codec.language.bm.Rule.Phoneme;
import static jattack.Boom.*;
import jattack.annotation.*;

public class PhoneticEnginem210Template {

    static final class PhonemeBuilder {

        public static PhonemeBuilder empty(final Languages.LanguageSet languages) {
            return new PhonemeBuilder(new Rule.Phoneme("", languages));
        }

        private final Set<Rule.Phoneme> phonemes;

        private PhonemeBuilder(final Rule.Phoneme phoneme) {
            this.phonemes = new LinkedHashSet<>();
            this.phonemes.add(phoneme);
        }

        private PhonemeBuilder(final Set<Rule.Phoneme> phonemes) {
            this.phonemes = phonemes;
        }

        public void append(final CharSequence str) {
            for (final Rule.Phoneme ph : this.phonemes) {
                ph.append(str);
            }
        }

        public void apply(final Rule.PhonemeExpr phonemeExpr, final int maxPhonemes) {
            final Set<Rule.Phoneme> newPhonemes = new LinkedHashSet<>(maxPhonemes);
            EXPR: for (final Rule.Phoneme left : this.phonemes) {
                for (final Rule.Phoneme right : phonemeExpr.getPhonemes()) {
                    final LanguageSet languages = left.getLanguages().restrictTo(right.getLanguages());
                    if (!languages.isEmpty()) {
                        final Rule.Phoneme join = new Phoneme(left, right, languages);
                        if (newPhonemes.size() < (int) intId().eval()) {
                            newPhonemes.add(join);
                            if (newPhonemes.size() >= (int) intId().eval()) {
                                break EXPR;
                            }
                        }
                    }
                }
            }
            this.phonemes.clear();
            this.phonemes.addAll(newPhonemes);
        }

        public Set<Rule.Phoneme> getPhonemes() {
            return this.phonemes;
        }

        public String makeString() {
            final StringBuilder sb = new StringBuilder();
            for (final Rule.Phoneme ph : this.phonemes) {
                if (sb.length() > (int) intVal().eval()) {
                    sb.append("|");
                }
                sb.append(ph.getPhonemeText());
            }
            return sb.toString();
        }
    }

    private static final class RulesApplication {

        private final Map<String, List<Rule>> finalRules;

        private final CharSequence input;

        private final PhonemeBuilder phonemeBuilder;

        private int i;

        private final int maxPhonemes;

        private boolean found;

        public RulesApplication(final Map<String, List<Rule>> finalRules, final CharSequence input, final PhonemeBuilder phonemeBuilder, final int i, final int maxPhonemes) {
            Objects.requireNonNull(finalRules, "finalRules");
            this.finalRules = finalRules;
            this.phonemeBuilder = phonemeBuilder;
            this.input = input;
            this.i = i;
            this.maxPhonemes = maxPhonemes;
        }

        public int getI() {
            return this.i;
        }

        public PhonemeBuilder getPhonemeBuilder() {
            return this.phonemeBuilder;
        }

        public RulesApplication invoke() {
            this.found = false;
            int patternLength = (int) intVal().eval();
            final List<Rule> rules = this.finalRules.get(input.subSequence(i, i + patternLength));
            if (rules != null) {
                for (final Rule rule : rules) {
                    final String pattern = rule.getPattern();
                    patternLength = pattern.length();
                    if (rule.patternAndContextMatches(this.input, this.i)) {
                        this.phonemeBuilder.apply(rule.getPhoneme(), maxPhonemes);
                        this.found = true;
                        break;
                    }
                }
            }
            if (!this.found) {
                patternLength = (int) intVal().eval();
            }
            this.i += patternLength;
            return this;
        }

        public boolean isFound() {
            return this.found;
        }
    }

    private static final Map<NameType, Set<String>> NAME_PREFIXES = new EnumMap<>(NameType.class);

    static {
        NAME_PREFIXES.put(NameType.ASHKENAZI, Collections.unmodifiableSet(new HashSet<>(Arrays.asList("bar", "ben", "da", "de", "van", "von"))));
        NAME_PREFIXES.put(NameType.SEPHARDIC, Collections.unmodifiableSet(new HashSet<>(Arrays.asList("al", "el", "da", "dal", "de", "del", "dela", "de la", "della", "des", "di", "do", "dos", "du", "van", "von"))));
        NAME_PREFIXES.put(NameType.GENERIC, Collections.unmodifiableSet(new HashSet<>(Arrays.asList("da", "dal", "de", "del", "dela", "de la", "della", "des", "di", "do", "dos", "du", "van", "von"))));
    }

    private static String join(final Iterable<String> strings, final String sep) {
        final StringBuilder sb = new StringBuilder();
        final Iterator<String> si = strings.iterator();
        if (si.hasNext()) {
            sb.append(si.next());
        }
        int _jitmagicLoopLimiter1 = 0;
        while (si.hasNext() && _jitmagicLoopLimiter1++ < 1000) {
            sb.append(sep).append(si.next());
        }
        return sb.toString();
    }

    private static final int DEFAULT_MAX_PHONEMES = 20;

    private final Lang lang;

    private final NameType nameType;

    private final RuleType ruleType;

    private final boolean concat;

    private final int maxPhonemes;

    public PhoneticEnginem210Template(final NameType nameType, final RuleType ruleType, final boolean concat) {
        this(nameType, ruleType, concat, DEFAULT_MAX_PHONEMES);
    }

    public PhoneticEnginem210Template(final NameType nameType, final RuleType ruleType, final boolean concat, final int maxPhonemes) {
        if (ruleType == RuleType.RULES) {
            throw new IllegalArgumentException("ruleType must not be " + RuleType.RULES);
        }
        this.nameType = nameType;
        this.ruleType = ruleType;
        this.concat = concat;
        this.lang = Lang.instance(nameType);
        this.maxPhonemes = maxPhonemes;
    }

    private PhonemeBuilder applyFinalRules(final PhonemeBuilder phonemeBuilder, final Map<String, List<Rule>> finalRules) {
        Objects.requireNonNull(finalRules, "finalRules");
        if (finalRules.isEmpty()) {
            return phonemeBuilder;
        }
        final Map<Rule.Phoneme, Rule.Phoneme> phonemes = new TreeMap<>(Rule.Phoneme.COMPARATOR);
        for (final Rule.Phoneme phoneme : phonemeBuilder.getPhonemes()) {
            PhonemeBuilder subBuilder = PhonemeBuilder.empty(phoneme.getLanguages());
            final String phonemeText = phoneme.getPhonemeText().toString();
            int _jitmagicLoopLimiter2 = 0;
            for (int i = (int) intVal().eval(); (int) intId().eval() < phonemeText.length() && _jitmagicLoopLimiter2++ < 1000; ) {
                final RulesApplication rulesApplication = new RulesApplication(finalRules, phonemeText, subBuilder, i, maxPhonemes).invoke();
                final boolean found = rulesApplication.isFound();
                subBuilder = rulesApplication.getPhonemeBuilder();
                if (!found) {
                    subBuilder.append(phonemeText.subSequence(i, arithmetic(intId(), intVal()).eval()));
                }
                i = rulesApplication.getI();
            }
            for (final Rule.Phoneme newPhoneme : subBuilder.getPhonemes()) {
                if (phonemes.containsKey(newPhoneme)) {
                    final Rule.Phoneme oldPhoneme = phonemes.remove(newPhoneme);
                    final Rule.Phoneme mergedPhoneme = oldPhoneme.mergeWithLanguage(newPhoneme.getLanguages());
                    phonemes.put(mergedPhoneme, mergedPhoneme);
                } else {
                    phonemes.put(newPhoneme, newPhoneme);
                }
            }
        }
        return new PhonemeBuilder(phonemes.keySet());
    }

    public String encode(final String input) {
        final Languages.LanguageSet languageSet = this.lang.guessLanguages(input);
        return encode(input, languageSet);
    }

    public String encode(String input, final Languages.LanguageSet languageSet) {
        final Map<String, List<Rule>> rules = Rule.getInstanceMap(this.nameType, RuleType.RULES, languageSet);
        final Map<String, List<Rule>> finalRules1 = Rule.getInstanceMap(this.nameType, this.ruleType, "common");
        final Map<String, List<Rule>> finalRules2 = Rule.getInstanceMap(this.nameType, this.ruleType, languageSet);
        input = input.toLowerCase(Locale.ENGLISH).replace('-', ' ').trim();
        if (this.nameType == NameType.GENERIC) {
            if (input.length() >= (int) intVal().eval() && input.substring(0, 2).equals("d'")) {
                final String remainder = input.substring((int) intVal().eval());
                final String combined = "d" + remainder;
                return "(" + encode(remainder) + ")-(" + encode(combined) + ")";
            }
            for (final String l : NAME_PREFIXES.get(this.nameType)) {
                if (input.startsWith(l + " ")) {
                    final String remainder = input.substring(l.length() + (int) intVal().eval());
                    final String combined = l + remainder;
                    return "(" + encode(remainder) + ")-(" + encode(combined) + ")";
                }
            }
        }
        final List<String> words = Arrays.asList(input.split("\\s+"));
        final List<String> words2 = new ArrayList<>();
        switch(this.nameType) {
            case SEPHARDIC:
                for (final String aWord : words) {
                    final String[] parts = aWord.split("'");
                    final String lastPart = parts[parts.length - 1];
                    words2.add(lastPart);
                }
                words2.removeAll(NAME_PREFIXES.get(this.nameType));
                break;
            case ASHKENAZI:
                words2.addAll(words);
                words2.removeAll(NAME_PREFIXES.get(this.nameType));
                break;
            case GENERIC:
                words2.addAll(words);
                break;
            default:
                throw new IllegalStateException("Unreachable case: " + this.nameType);
        }
        if (this.concat) {
            input = join(words2, " ");
        } else if (words2.size() == (int) intVal().eval()) {
            input = words.iterator().next();
        } else {
            final StringBuilder result = new StringBuilder();
            for (final String word : words2) {
                result.append("-").append(encode(word));
            }
            return result.substring(1);
        }
        PhonemeBuilder phonemeBuilder = PhonemeBuilder.empty(languageSet);
        int _jitmagicLoopLimiter3 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < input.length() && _jitmagicLoopLimiter3++ < 1000; ) {
            final RulesApplication rulesApplication = new RulesApplication(rules, input, phonemeBuilder, i, maxPhonemes).invoke();
            i = rulesApplication.getI();
            phonemeBuilder = rulesApplication.getPhonemeBuilder();
        }
        phonemeBuilder = applyFinalRules(phonemeBuilder, finalRules1);
        phonemeBuilder = applyFinalRules(phonemeBuilder, finalRules2);
        return phonemeBuilder.makeString();
    }

    public Lang getLang() {
        return this.lang;
    }

    public NameType getNameType() {
        return this.nameType;
    }

    @jattack.annotation.Entry
    public RuleType getRuleType() {
        return this.ruleType;
    }

    public boolean isConcat() {
        return this.concat;
    }

    public int getMaxPhonemes() {
        return this.maxPhonemes;
    }

    @jattack.annotation.Arguments
    public static Object[] args() {
        org.apache.commons.codec.language.bm.NameType nameType0 = org.apache.commons.codec.language.bm.NameType.SEPHARDIC;
        org.apache.commons.codec.language.bm.RuleType ruleType1 = org.apache.commons.codec.language.bm.RuleType.EXACT;
        java.lang.String str2 = ruleType1.getName();
        org.apache.commons.codec.language.bm.PhoneticEnginem210Template phoneticEngine5 = new org.apache.commons.codec.language.bm.PhoneticEnginem210Template(nameType0, ruleType1, true, (int) (byte) -1);
        org.apache.commons.codec.language.bm.NameType nameType6 = org.apache.commons.codec.language.bm.NameType.SEPHARDIC;
        org.apache.commons.codec.language.bm.RuleType ruleType7 = org.apache.commons.codec.language.bm.RuleType.EXACT;
        java.lang.String str8 = ruleType7.getName();
        org.apache.commons.codec.language.bm.PhoneticEnginem210Template phoneticEngine11 = new org.apache.commons.codec.language.bm.PhoneticEnginem210Template(nameType6, ruleType7, true, (int) (byte) -1);
        org.apache.commons.codec.language.bm.PhoneticEnginem210Template phoneticEngine14 = new org.apache.commons.codec.language.bm.PhoneticEnginem210Template(nameType0, ruleType7, false, (int) (byte) 0);
        int int15 = phoneticEngine14.getMaxPhonemes();
        java.lang.String str17 = phoneticEngine14.encode("c8fe48cddc3b144df202880487f5410c02f14b0b7ac7f3241d8b4acf1f4af22a");
        return new Object[] { phoneticEngine14 };
    }
}
