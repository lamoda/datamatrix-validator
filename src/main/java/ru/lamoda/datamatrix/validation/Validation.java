package ru.lamoda.datamatrix.validation;

@FunctionalInterface
public interface Validation<K> {

    ValidationResult test(K param);

    default Validation<K> and(Validation<K> other) {
        return (param) -> {
            ValidationResult firstResult = this.test(param);
            return !firstResult.isValid() ? firstResult : other.test(param);
        };
    }

    default Validation<K> or(Validation<K> other) {
        return (param) -> {
            ValidationResult firstResult = this.test(param);
            return firstResult.isValid() ? firstResult : other.test(param);
        };
    }
}
