package fcc.sportsstore.utils;

import java.util.ArrayList;
import java.util.List;

public class ValidateUtil {

    private String removeUnusedSpace(String input) {
        return input.trim().replaceAll("\\s+", " ");
    }

    private String toStringForm(String title,
                               String rawInput,
                               int minLength,
                               int maxLength,
                               boolean hasAlpha,
                               boolean hasNum,
                               boolean hasSpace,
                               boolean hasSpecialChars) {
        if (rawInput == null) {
            throw new IllegalArgumentException(String.format("%s must be not null.", title));
        }

        String input = removeUnusedSpace(rawInput);

        int length = input.length();
        if (length < minLength) {
            throw new IllegalArgumentException(String.format("%s must be at least %d characters.", title, minLength));
        } else if (length > maxLength) {
            throw new IllegalArgumentException(String.format("%s must be equals or less than %d characters.", title, minLength));
        }

        StringBuilder regex = new StringBuilder();
        List<String> allowTags = new ArrayList<>();

        if (hasAlpha) {
            regex.append("a-zA-Z");
            allowTags.add("alpha");
        }

        if (hasNum) {
            regex.append("0-9");
            allowTags.add("number");
        }

        if (hasSpace) {
            regex.append(" ");
            allowTags.add("space");
        }

        if (hasSpecialChars) {
            regex.append("_\\-.,/:;@#$%&*!?");
            allowTags.add("special chars");
        }

        if (!input.matches(String.format("^[%s]+$", regex))) {
            throw new IllegalArgumentException(String.format("%s just contains %s", title,
                    String.join(", ", allowTags)));
        }

        return input;
    }

    private Integer toIntegerForm(String title,
                                 String rawInput,
                                 int minRange,
                                 int maxRange) {
        if (rawInput == null) {
            throw new IllegalArgumentException(String.format("%s must be not null.", title));
        }

        Integer input = null;
        try {
            input = Integer.parseInt(rawInput);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("%s must be a integer.", title));
        }

        if (input < minRange) {
            throw new IllegalArgumentException(String.format("%s must be at least %d.", title, minRange));
        } else if (input > maxRange) {
            throw new IllegalArgumentException(String.format("%s must be equals or less than %d.", title, maxRange));
        }

        return input;
    }

    private Double toDoubleForm(String title,
                                 String rawInput,
                                 double minRange,
                                 double maxRange) {
        if (rawInput == null) {
            throw new IllegalArgumentException(String.format("%s must be not null.", title));
        }

        Double input = null;
        try {
            input = Double.parseDouble(rawInput);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("%s must be a number.", title));
        }

        if (input < minRange) {
            throw new IllegalArgumentException(String.format("%s must be at least %.1f.", title, minRange));
        } else if (input > maxRange) {
            throw new IllegalArgumentException(String.format("%s must be equals or less than %.1f.", title, maxRange));
        }

        return input;
    }

    public String toId(String input) {
        return toStringForm("ID", input, 1, 100, true, true, false, true);
    }

    public String toUsername(String input) {
        return toStringForm("Username", input, 6, 20, true, true, false, false);
    }

    public String toFullName(String input) {
        return toStringForm("Full name", input, 3, 35, true, false, true, false);
    }
    
    public String toPassword(String input) {
        return toStringForm("Password", input, 6, 35, true, true, false, true);
    }

    public String toCollectionName(String input) {
        return toStringForm("Collection name", input, 3, 35, true, true, true, false);
    }

    public String toProductPropertyField(String input) {
        return toStringForm("Property field", input, 1, 20, true, true, true, false);
    }

    public String toProductPropertyData(String input) {
        return toStringForm("Property data", input, 1, 20, true, true, true, true);
    }

    public String toProductTitle(String input) {
        return toStringForm("Title", input, 3, 35, true, true, true, true);
    }

    public String toProductDescription(String input) {
        return toStringForm("Description", input, 3, 500, true, true, true, true);
    }

    public Double toPrice(String input) {
        return toDoubleForm("Price", input, 0f, Double.MAX_VALUE);
    }

    public String toProductTypeName(String input) {
        return toStringForm("Type name", input, 3, 35, true, false, true, false);
    }

    public String toVoucherCode(String input) {
        return toStringForm("Voucher code", input, 3, 35, true, false, true, false);
    }

    public Integer toVoucherMaxUsedCount(String input) {
        return toIntegerForm("Limit used count", input, -1, Integer.MAX_VALUE);
    }

    public Double toVoucherDiscountValue(String input) {
        return toDoubleForm("Discount value", input, 0f, Double.MAX_VALUE);
    }

    public Double toVoucherMaxDiscountValue(String input) {
        return toDoubleForm("Max discount value", input, -1, Double.MAX_VALUE);
    }

    public String toAddressNote(String input) {
        return toStringForm("Address note", input, 2, 20, true, false, true, false);
    }

    public String toPhoneNumber(String input) {
        return toStringForm("Phone number", input, 9, 11, false, true, false, false);
    }

    public String toAddressDetail(String input) {
        return toStringForm("Address detail", input, 5, 200, true, true, true, true);
    }

    public String toEmail(String input) {
        return toStringForm("Email", input, 6, 20, true, true, false, true);
    }

    public String toLongCode(String input) {
        return toStringForm("Code", input, 100, 255, true, true, false, true);
    }
}
