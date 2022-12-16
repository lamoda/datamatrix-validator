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
                    return getCode().length() + 7;
                default:
                    return getCode().length() + 13;
            }
        }
    },

    AI_91("91", false) {
        @Override
        public ValidationResult getValidationResult(TradeGroup type, String value) {
            return notBlank
                    .and(checkTradeGroup(type, SHOES, LP, PERFUMERY))
                    .and(fixedSize(getSize(type)))
                    .and(checkRegex(GS1_ISO_SUBSET_FOR_CONTROL_NUMBER))
                    .test(value);
        }

        @Override
        public int getSize(TradeGroup type) {
            switch (type) {
                case SHOES:
                case LP:
                case PERFUMERY:
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
                    .and(checkTradeGroup(type, BEER, WATER, MILK))
                    .and(fixedSize(getSize(type)))
                    .and(checkRegex(GS1_ISO_SUBSET_FOR_CONTROL_NUMBER))
                    .test(value);
        }

        @Override
        public int getSize(TradeGroup type) {
            switch (type) {
                case MILK:
                case WATER:
                case BEER:
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
                    .and(checkTradeGroup(type, PERFUMERY, SHOES, LP))
                    .and(fixedSize(getSize(type)))
                    .and(checkRegex(GS1_ISO_SUBSET_FOR_CRYPTO_TAIL))
                    .test(value);
        }

        @Override
        public int getSize(TradeGroup type) {
            switch (type) {
                case SHOES:
                    return getCode().length() + 88;
                case PERFUMERY:
                case LP:
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
