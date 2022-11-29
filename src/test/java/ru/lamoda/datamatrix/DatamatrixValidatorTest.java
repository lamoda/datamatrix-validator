package ru.lamoda.datamatrix;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.lamoda.datamatrix.model.TradeGroup;
import ru.lamoda.datamatrix.validation.DatamatrixValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.lamoda.datamatrix.model.TradeGroup.*;

class DatamatrixValidatorTest {

    @ParameterizedTest
    @MethodSource("badDatamatrixList")
    void validateBadDatamatrix(String datamatrixCode) {
        boolean result = DatamatrixValidator.validate(datamatrixCode);
        boolean resultByType = DatamatrixValidator.validate(datamatrixCode, TradeGroup.values());
        assertFalse(result);
        assertFalse(resultByType);
    }

    @ParameterizedTest
    @MethodSource("goodDatamatrixList")
    void validateGoodDatamatrix(String datamatrixCode, TradeGroup rightType, TradeGroup wrongType) {
        boolean result = DatamatrixValidator.validate(datamatrixCode);
        boolean rightResultByType = DatamatrixValidator.validate(datamatrixCode, rightType);
        boolean wrongResultByType = DatamatrixValidator.validate(datamatrixCode, wrongType);
        boolean resultByListTypes = DatamatrixValidator.validate(datamatrixCode, wrongType, rightType);
        assertTrue(result);
        assertTrue(rightResultByType);
        assertFalse(wrongResultByType);
        assertTrue(resultByListTypes);
    }

    public static Object[][] badDatamatrixList() {
        String withStartGS = "\u001D010290000032304921BxDJ)fD8.&jdm\u001D91003A\u001D925FoocZHR5PnetH3n6pYtr1Yh4MkNS5MxOakJjuE6teeozoexOH6Aixbnpj8l4XeSu1srkhk9FV2yy6kxX1Pmkg==";
        String withDoubleGS = "010290000032304921BxDJ)fD8.&jdm\u001D\u001D91003A\u001D925FoocZHR5PnetH3n6pYtr1Yh4MkNS5MxOakJjuE6teeozoexOH6Aixbnpj8l4XeSu1srkhk9FV2yy6kxX1Pmkg==";
        String noAI_01 = "020463007000812321ymxGZZnG838XW\u001D2406403\u001D91ffd0\u001D92mliP3gYsRdl6IorVEB8/4vYOHiPQsKeCeayAqCjwWDhl0cK4SYMZwD8SU3aKdhNtXf/pSBbSC9V6o8BLIoBW0Q==";
        String notFoundPatternCombination = "010463007000812321ymxGZZnG838XW\u001D010463007000812321ymxGZZnG838XW\u001D2406403\u001D91ffd0\u001D92mliP3gYsRdl6IorVEB8/4vYOHiPQsKeCeayAqCjwWDhl0cK4SYMZwD8SU3aKdhNtXf/pSBbSC9V6o8BLIoBW0Q==";
        String withDoubleAI = "010290000032304921BxDJ)fD8.&jdm\u001D91003A\u001D91003A\u001D925FoocZHR5PnetH3n6pYtr1Yh4MkNS5MxOakJjuE6teeozoexOH6Aixbnpj8l4XeSu1srkhk9FV2yy6kxX1Pmkg==";
        String beerCodeWithWrongVolumeNumber = "0104610053147452215d,OiLl\u001D93dGVz\u001D3356123456";
        String beerCodeWithWrongVolumeSize = "0104610053147452215d,OiLl\u001D93dGVz\u001D335112345";
        String withUnknownCode = "0104610053149777215d/wuU\u001D93dGVz\u001D88dGVz";
        String withDoubleCode = "0104653820923088215GoNfWgdcBeVZ\u001D93dGVz\u001D93dGVz";
        return new Object[][]{
                {null},
                {""},
                {" "},
                {"    "},
                {withStartGS},
                {withDoubleGS},
                {noAI_01},
                {notFoundPatternCombination},
                {withDoubleAI},
                {beerCodeWithWrongVolumeNumber},
                {beerCodeWithWrongVolumeSize},
                {withUnknownCode},
                {withDoubleCode},
        };
    }

    public static Object[][] goodDatamatrixList() {
        String shortShoesCode = "010290000032304921BxDJ)fD8.&jdm\u001D91003A\u001D925FoocZHR5PnetH3n6pYtr1Yh4MkNS5MxOakJjuE6teeozoexOH6Aixbnpj8l4XeSu1srkhk9FV2yy6kxX1Pmkg==";
        String longShoesCode = "010463007000812321ymxGZZnG838XW\u001D2406403\u001D91ffd0\u001D92mliP3gYsRdl6IorVEB8/4vYOHiPQsKeCeayAqCjwWDhl0cK4SYMZwD8SU3aKdhNtXf/pSBbSC9V6o8BLIoBW0Q==";
        String milkCode = "0104610053149777215d/wuU\u001D93dGVz";
        String milkCodeWithWeight = "0104610053149777215d/wuU\u001D93dGVz\u001D3103123456";
        String waterCode = "0104653820923088215GoNfWgdcBeVZ\u001D93dGVz";
        String beerCode = "0104610053147452215d,OiLl\u001D93dGVz";
        String beerCodeWithVolume = "0104610053147452215d,OiLl\u001D93dGVz\u001D3350123456";
        String lpCode = "0104669000322220215t*NH>t:ngCfH\u001D91FFD0\u001D92dGVzdBL8SAtwRqhp7H9rfyI59GoJ8ec6X9GhckDf8JE=";
        return new Object[][]{
                {shortShoesCode, SHOES, LP},
                {longShoesCode, SHOES, LP},
                {milkCode, MILK, WATER},
                {milkCodeWithWeight, MILK, SHOES},
                {waterCode, WATER, SHOES},
                {beerCode, BEER, LP},
                {beerCodeWithVolume, BEER, LP},
                {lpCode, LP, BEER},
        };
    }
}