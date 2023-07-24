package ru.lamoda.datamatrix.model;

import ru.lamoda.datamatrix.validation.ValidationResult;

import static ru.lamoda.datamatrix.model.TradeGroup.*;
import static ru.lamoda.datamatrix.validation.ValidationHelper.*;

/**
 * Идентификатор применения
 */
public enum AI implements AIAction {

    AI_01("01", true) {
        @Override
        public ValidationResult getValidationResult(TradeGroup type, String value) {
            return notBlank
                    .and(fixedSize(getSize(type)))
                    .and(onlyNumbers())
                    .test(value);
        }

        @Override
        public int getSize(TradeGroup type) {
            if (type == NICOTINE) {
                return getCode().length() + 12;
            }
            return getCode().length() + 14;
        }
    },

    AI_21("21", false) {
        @Override
        public ValidationResult getValidationResult(TradeGroup type, String value) {
            return notBlank
                    .and(fixedSize(getSize(type)))
                    .and(checkRegex(GS1_ISO_SUBSET_FOR_SERIAL))
                    .test(value);
        }

        @Override
        public int getSize(TradeGroup type) {
            switch (type) {
                case MILK:
                    return getCode().length() + 6;
                case BEER:
                case NICOTINE:
                    return getCode().length() + 7;
                case CAMERAS:
                    return getCode().length() + 20;
                default:
                    return getCode().length() + 13;
            }
        }
    },

    AI_91("91", false) {
        @Override
        public ValidationResult getValidationResult(TradeGroup type, String value) {
            return notBlank
                    .and(checkTradeGroup(type, ANTISEPTIC, BICYCLES, CAMERAS, CLOTHES, DS, LP, MP, PERFUMERY, SHOES, TIRES))
                    .and(fixedSize(getSize(type)))
                    .and(checkRegex(GS1_ISO_SUBSET_FOR_CONTROL_NUMBER))
                    .test(value);
        }

        @Override
        public int getSize(TradeGroup type) {
            switch (type) {
                case ANTISEPTIC:
                case BICYCLES:
                case CAMERAS:
                case CLOTHES:
                case DS:
                case LP:
                case MP:
                case PERFUMERY:
                case SHOES:
                case TIRES:
                    return getCode().length() + 4;
                default:
                    return 0;
            }
        }
    },

    AI_93("93", false) {
        @Override
        public ValidationResult getValidationResult(TradeGroup type, String value) {
            return notBlank
                    .and(checkTradeGroup(type, ANTISEPTIC, BEER, DS, WATER, MILK))
                    .and(fixedSize(getSize(type)))
                    .and(checkRegex(GS1_ISO_SUBSET_FOR_CONTROL_NUMBER))
                    .test(value);
        }

        @Override
        public int getSize(TradeGroup type) {
            switch (type) {
                case ANTISEPTIC:
                case BEER:
                case DS:
                case MILK:
                case WATER:
                    return getCode().length() + 4;
                default:
                    return 0;
            }
        }
    },

    AI_92("92", false) {
        @Override
        public ValidationResult getValidationResult(TradeGroup type, String value) {
            return notBlank
                    .and(checkTradeGroup(type, SHOES, ANTISEPTIC, BICYCLES, CAMERAS, CLOTHES, DS, LP, MP, PERFUMERY, TIRES))
                    .and(fixedSize(getSize(type)))
                    .and(checkRegex(GS1_ISO_SUBSET_FOR_CRYPTO_TAIL))
                    .test(value);
        }

        @Override
        public int getSize(TradeGroup type) {
            switch (type) {
                case SHOES:
                    return getCode().length() + 88;
                case ANTISEPTIC:
                case BICYCLES:
                case CAMERAS:
                case CLOTHES:
                case DS:
                case LP:
                case MP:
                case PERFUMERY:
                case TIRES:
                    return getCode().length() + 44;
                default:
                    return 0;
            }
        }
    },

    AI_240("240", false) {
        @Override
        public ValidationResult getValidationResult(TradeGroup type, String value) {
            return notBlank
                    .and(checkTradeGroup(type, SHOES))
                    .and(fixedSize(getSize(type)))
                    .and(onlyNumbers())
                    .test(value);
        }

        @Override
        public int getSize(TradeGroup type) {
            if (type == SHOES) {
                return getCode().length() + 4;
            }
            return 0;
        }
    },

    AI_3103("3103", false) {
        @Override
        public ValidationResult getValidationResult(TradeGroup type, String value) {
            return notBlank
                    .and(checkTradeGroup(type, MILK))
                    .and(fixedSize(getSize(type)))
                    .and(onlyNumbers())
                    .test(value);
        }

        @Override
        public int getSize(TradeGroup type) {
            if (type == MILK) {
                return getCode().length() + 6;
            }
            return 0;
        }
    },

    AI_8005("8005", false) {
        @Override
        public ValidationResult getValidationResult(TradeGroup type, String value) {
            return notBlank
                    .and(checkTradeGroup(type, NICOTINE))
                    .and(fixedSize(getSize(type)))
                    .and(onlyNumbers())
                    .test(value);
        }

        @Override
        public int getSize(TradeGroup type) {
            if (type == NICOTINE) {
                return getCode().length() + 6;
            }
            return 0;
        }
    },

    AI_335Y("335", false) {
        @Override
        public ValidationResult getValidationResult(TradeGroup type, String value) {
            return notBlank
                    .and(checkTradeGroup(type, BEER))
                    .and(fixedSize(getSize(type)))
                    .and(onlyNumbers())
                    .and(checkStartNumbers335Y())
                    .test(value);
        }

        @Override
        public int getSize(TradeGroup type) {
            if (type == BEER) {
                return getCode().length() + 7;
            }
            return 0;
        }
    };

    private final String code;
    private final boolean isFixedSize;

    AI(String code, boolean isFixedSize) {
        this.code = code;
        this.isFixedSize = isFixedSize;
    }

    public String getCode() {
        return code;
    }

    public boolean isFixedSize() {
        return isFixedSize;
    }

    public void validateThrow(TradeGroup type, String value) {
        this.getValidationResult(type, value)
                .throwIfInvalid();
    }
}
