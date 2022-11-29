package ru.lamoda.datamatrix.exception;

/**
 * Информирует о том, что датаматрикс код не соответствует правилам валидации
 */
public class DatamatrixCodeIncorrectException extends RuntimeException {

    public DatamatrixCodeIncorrectException(String msg) {
        super(msg);
    }
}
