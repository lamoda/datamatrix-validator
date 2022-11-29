package ru.lamoda.datamatrix.validation;

import java.util.function.Predicate;

public class SimpleValidation<K> implements Validation<K> {

    private final Predicate<K> predicate;
    private final String onErrorMessage;

    public static <K> SimpleValidation<K> from(Predicate<K> predicate, String onErrorMessage) {
        return new SimpleValidation<>(predicate, onErrorMessage);
    }

    private SimpleValidation(Predicate<K> predicate, String onErrorMessage) {
        this.predicate = predicate;
        this.onErrorMessage = onErrorMessage;
    }

    @Override
    public ValidationResult test(K param) {
        return predicate.test(param) ? ValidationResult.ok() : ValidationResult.fail(onErrorMessage);
    }
}
