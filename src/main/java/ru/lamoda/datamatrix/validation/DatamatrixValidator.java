package ru.lamoda.datamatrix.validation;

import ru.lamoda.datamatrix.exception.DatamatrixCodeIncorrectException;
import ru.lamoda.datamatrix.model.AI;
import ru.lamoda.datamatrix.model.TradeGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.lamoda.datamatrix.validation.ValidationHelper.*;

/**
 * Класс содержит методы валидации датаматрикс кодов
 */
public class DatamatrixValidator {

    /**
     * Метод проверки даматрикс кода на соответствие правилам валидации.
     *
     * @param datamatrixCode датаматрикс код, который необоходимо валидировать
     * @return возвращает true, если даматрикс код соответствует правилам валидации хотя бы одной товарной группы
     * @see TradeGroup
     */
    public static boolean validate(String datamatrixCode) {
        try {
            validateThrow(datamatrixCode, TradeGroup.values());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Метод проверки даматрикс кода на соответствие правилам валидации товарной группы {@code TradeGroup}
     *
     * @param datamatrixCode датаматрикс код, который необоходимо валидировать
     * @param type           товарная группа для которой будет происходить валидация, {@code TradeGroup}
     * @return возвращает true, если даматрикс код соответствует правилам валидации указанной товарной группы
     * @see TradeGroup
     */
    public static boolean validate(String datamatrixCode, TradeGroup type) {
        try {
            validateThrow(datamatrixCode, type);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Метод проверки даматрикс кода на соответствие правилам валидации товарной группы {@code TradeGroup}
     *
     * @param datamatrixCode датаматрикс код, который необоходимо валидировать
     * @param types          список товарных групп для которого будет происходить валидация, {@code TradeGroup}
     * @return возвращает true, если даматрикс код соответствует правилам валидации хотя бы одной товарной группы из списка
     * @see TradeGroup
     */
    public static boolean validate(String datamatrixCode, TradeGroup... types) {
        try {
            validateThrow(datamatrixCode, types);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void validateThrow(String datamatrixCode, TradeGroup... types) throws DatamatrixCodeIncorrectException {
        baseValidate(datamatrixCode);

        Arrays.stream(types)
                .filter(type -> validate(datamatrixCode, type))
                .findFirst()
                .orElseThrow(() -> new DatamatrixCodeIncorrectException("not found trade group."));
    }

    private static void validateThrow(String datamatrixCode, TradeGroup type) throws DatamatrixCodeIncorrectException {
        baseValidate(datamatrixCode);

        List<AI> currentOrderAis = splitToAis(datamatrixCode, type);

        Arrays.stream(type.getCombinations())
                .filter(combination -> combination.equals(currentOrderAis))
                .findFirst()
                .orElseThrow(() -> new DatamatrixCodeIncorrectException("not found valid combination for trade group."));
    }

    private static void baseValidate(String datamatrixCode) {
        notBlank
                .and(notStartWithGS())
                .and(containsGS())
                .and(notContainsDoubleGS())
                .test(datamatrixCode)
                .throwIfInvalid();
    }

    private static List<AI> splitToAis(String datamatrixCode, TradeGroup type) {
        String[] parseResult = datamatrixCode.split(String.valueOf(GS));
        List<AI> result = new ArrayList<>();

        for (String part : parseResult) {
            List<AI> ais = Arrays.stream(AI.values())
                    .filter(ai -> part.startsWith(ai.getCode()))
                    .collect(Collectors.toList());
            if (ais.size() == 0) {
                throw new DatamatrixCodeIncorrectException("unknown AI");
            }
            for (AI identifier : ais) {
                if (!identifier.isFixedSize()) {
                    identifier.validateThrow(type, part);
                    result.add(identifier);
                } else {
                    String begin = part.substring(0, identifier.getSize(type));
                    identifier.validateThrow(type, begin);
                    result.add(identifier);
                    result.addAll(splitToAis(part.substring(identifier.getSize(type)), type));
                }
            }
        }
        return result;
    }
}
