package ru.lamoda.datamatrix.model;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static ru.lamoda.datamatrix.model.AI.*;

/**
 * Товарная группа
 */
public enum TradeGroup {
    /**
     * Упакованная вода
     */
    WATER(AI_01, AI_21, AI_93),
    /**
     * Пиво и слабоалкогольные напитки
     */
    BEER(
            asList(AI_01, AI_21, AI_93),
            asList(AI_01, AI_21, AI_93, AI_335Y)
    ),
    /**
     * Молочная продукция
     */
    MILK(
            asList(AI_01, AI_21, AI_93),
            asList(AI_01, AI_21, AI_93, AI_3103)),
    /**
     * Обувь
     */
    SHOES(
            asList(AI_01, AI_21, AI_91, AI_92),
            asList(AI_01, AI_21, AI_240, AI_91, AI_92)
    ),
    /**
     * Товары лёгкой промышленности
     */
    LP(AI_01, AI_21, AI_91, AI_92),
    /**
     * Парфюмерия
     */
    PERFUMERY(AI_01, AI_21, AI_91, AI_92);

    /**
     * Список поддерживаемых форматов для каждой товарной группы
     */
    private final List<AI>[] combinations;

    TradeGroup(List<AI>... combinations) {
        this.combinations = combinations;
    }

    TradeGroup(AI... ais) {
        this.combinations = new List[]{Arrays.asList(ais)};
    }

    public List<AI>[] getCombinations() {
        return combinations;
    }
}
