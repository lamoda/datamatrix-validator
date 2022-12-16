package ru.lamoda.datamatrix.model;

import ru.lamoda.datamatrix.validation.ValidationResult;

/**
 * Методы идентификатора применения
 *
 * @see AI
 */
public interface AIAction {

    /**
     * Возвращает результат валидации {@code ValidationResult} согласно правилам валидации
     * конкретного идентиифкатора применения {@code AI}
     *
     * @param type  товарная группа, необходима для специфичных товарной группе проверок, {@code TradeGroup}
     * @param value строка которая проверяется на соответствие правилам валидации, включает в себя код идентификатора применения
     * @return результат валидации {@code ValidationResult}
     * @see ValidationResult
     * @see TradeGroup
     */
    ValidationResult getValidationResult(TradeGroup type, String value);

    /**
     * Возвращает необходимую длину идентификатора применения {@code AI} для товарной группы {@code TradeGroup}
     *
     * @param type товарная группа для которой будет происходить валидация, {@code TradeGroup}
     * @return длина строки для идентификатора применения {@code AI} для товарной группы {@code TradeGroup}
     * @see TradeGroup
     */
    int getSize(TradeGroup type);
}
