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
        String invalidShortNicotine = "04603731175212JEIjNYDAAAAtFw";
        String invalidCamerasCode = "0102900000387843215v&XqVigrspEe95cj\"jF\u001D91FFD0\u001D";

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
                {invalidShortNicotine},
                {invalidCamerasCode},
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
//      Обувь
        String footwear = "0104680128320356215BWOM0ip7Ns3M91FFD092testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest";
//      Одежда
        String clothes = "0104669000322695215YCQ?cX3bNsD<91FFD092dGVzdMXgZt5JFLIfm/gTAGFQsN1zD3MhYdOBHhprah8=";
//      Молоко
        String milk = "01046690003220602156BelF93dGVz";
//      Духи
        String perfume = "0104669000322169215GqtjhXBO%LNp91FFD092dGVzdCR1+WbFwT1f8YoWctkvZKnLEpdiMjfsYVlNFAM=";
//      Вода
        String water = "01046690003222372151G'OclhkkPrJ93dGVz";
//      Шины
        String tires = "0104669000322152215W&r<lpN0IM),91FFD092dGVzdNsM825y2Sac/e1jg39IcBq9+/85k4UzsgB4ojM=";
//      Велосипеды
        String bicycles = "01046690003222992153WOb5arxriF491FFD092dGVzdIGXHttcXgubMZBcXhDYKIeiYiw4zv7JMDjjFQg=";
//      Мед.изделия
        String medicalProducts = "0104603225022459215cI;3t1LgW_gV\u001D91EE08\u001D92IbHShLeDs+NGo3FMCq1FB7R8a6Cun/Eyra0iLou67ek=";
//      Фотоаппараты
        String cameras = "0104669000322275215TXpf?A.tXlJ;ZJFn'Li\u001D91FFD0\u001D92dGVzdIPiTENrtHlgbiheL880JGjG6vjQS/UWNuxQCFs=";
//      Пиво
        String beer = "0104680128320615215pEB7Dc93dGVz3350000020";
//      Безалкогольное пиво
        String nonAlcoholicBeer = "0104653820923231215gSRo9L93dGVz";
//      БАД
        String dietarySupplement = "0104669000322336215GmpVhJ.c':lI93dGVz";
//      БАД*
        String dietarySupplement2 = "0104669000322343215TNbIpAVkQlYO91FFD092dGVzdO5In+HN3/axzPXOXDTfF9RGuqkxahFtHR7xFWg=";
        String dietarySupplement3 = "0104669000322312215E%hNC\"FDPJGZ\u001D91FFD0\u001D92dGVzdCjmBI8AAwc9lVRcbJObEgae5EddDoQ1iC7Q6F4=";
//      Антисептики
        String antiseptics = "0104607128160164215SGa:L0UJM=cE93vSFw";
//      Антисептики*
        String antiseptics2 = "0104607128160164215q3Ae0:jK.Gh391EE0692B5dj5RJZB7qarLvjwxl5fd+T9mM1QgVMd8edeRNUvQQ=";
//      Табак,АТП*, НСП*
        String tobacco = "01230000117319RzvofwbAAAAR8n8";
//      АТП
        String tobacco2 = "04653820923019f4WmAnbAAAAdGVz";
//      Табак,НСП
        String tobacco3 = "04653820923323,Uh4H>6AAAAdGVz";
        String nicotine = "04603731175212JEIjNYDAAAAtFwS";
//      Безалкогольное пиво
        String nonAlcoholicBeer2 = "0104653820923231215gSRo9L93dGVz";
//      Соковая продукция и безалкогольные напитки
        String juiceAndSoftDrinks = "0104653820923170215KIDz?kLcy=rp93dGVz";

        return new Object[][]{
                {shortShoesCode, SHOES, LP},
                {longShoesCode, SHOES, LP},
                {milkCode, MILK, WATER},
                {milkCodeWithWeight, MILK, SHOES},
                {waterCode, WATER, SHOES},
                {beerCode, BEER, LP},
                {beerCodeWithVolume, BEER, LP},
                {lpCode, LP, BEER},
                {footwear, SHOES, WATER},
                {clothes, CLOTHES, BEER},
                {milk, MILK, DS},
                {perfume, PERFUMERY, WATER},
                {water, WATER, BEER},
                {tires, TIRES, WATER},
                {bicycles, BICYCLES, SHOES},
                {medicalProducts, MP, MILK},
                {cameras, CAMERAS, LP},
                {beer, BEER, MP},
                {nonAlcoholicBeer, BEER, SHOES},
                {dietarySupplement, DS, BICYCLES},
                {dietarySupplement2, DS, WATER},
                {dietarySupplement3, DS, MILK},
                {antiseptics, ANTISEPTIC, NICOTINE},
                {antiseptics2, ANTISEPTIC, WATER},
                {tobacco, NICOTINE, BEER},
                {tobacco2, NICOTINE, WATER},
                {tobacco3, NICOTINE, ANTISEPTIC},
                {nicotine, NICOTINE, SHOES},
                {nonAlcoholicBeer2, BEER, WATER},
                {juiceAndSoftDrinks, WATER, MILK},
        };
    }
}