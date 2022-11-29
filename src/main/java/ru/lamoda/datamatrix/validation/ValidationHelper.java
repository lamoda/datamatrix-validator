package ru.lamoda.datamatrix.validation;

import ru.lamoda.datamatrix.model.TradeGroup;

import java.util.Arrays;
import java.util.regex.Pattern;

import static java.lang.String.format;

public class ValidationHelper {

    public static final char GS = '\u001d';
    public static final String GS1_ISO_SUBSET_FOR_CRYPTO_TAIL = "^[a-zA-Z0-9_.,()/=!\"*<>'+;:?\\-{}~`%&]*$";
    public static final String GS1_ISO_SUBSET_FOR_SERIAL = "^[a-zA-Z0-9_.,()/=!\"*<>'+;:?\\-{}~`%&]*$";
    public static final String GS1_ISO_SUBSET_FOR_CONTROL_NUMBER = "^[a-zA-Z0-9_.,()/=!\"*<>'+;:?\\-{}~`%&]*$";
    public static final String START_NUMBERS_335Y = "335[0-5].*$";
    public static final String NUMBERS_ONLY = "[0-9]+";

    public static final Validation<String> notBlank = SimpleValidation.from(v -> !isBlank(v), "must not be blank.");

    public static Validation<String> fixedSize(int size) {
        return SimpleValidation.from((s) -> s.length() == size, format("must have %s chars.", size));
    }

    public static Validation<String> notStartWithGS() {
        return SimpleValidation.from((s) -> !s.startsWith(String.valueOf(GS)), "must not start with <GS>");
    }

    public static Validation<String> notContainsDoubleGS() {
        return SimpleValidation.from((s) -> !s.contains(GS + String.valueOf(GS)), "must not contains double <GS>");
    }

    public static Validation<String> containsGS() {
        return SimpleValidation.from((s) -> contains(s, GS), "must contain <GS>");
    }

    public static Validation<String> onlyNumbers() {
        return SimpleValidation.from((s) -> Pattern.matches(NUMBERS_ONLY, s), "must contain only numbers");
    }

    public static Validation<String> checkStartNumbers335Y() {
        return SimpleValidation.from((s) -> Pattern.matches(START_NUMBERS_335Y, s), "incorrect start numbers");
    }

    public static Validation<String> checkRegex(String regex) {
        return SimpleValidation.from((s) -> Pattern.matches(regex, s), format("not valid for regex %s", regex));
    }

    public static Validation<String> checkTradeGroup(TradeGroup type, TradeGroup... expectedTypes) {
        return SimpleValidation.from((s) -> Arrays.asList(expectedTypes).contains(type), String.format("incorrect trade group %s", type));
    }

    private static boolean contains(String s, char search) {
        if (s.length() == 0) {
            return false;
        } else {
            return s.charAt(0) == search || contains(s.substring(1), search);
        }
    }

    private static boolean isBlank(String v) {
        int strLen;
        if (v == null || (strLen = v.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(v.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
