package org.mark;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class BitwiseCalculator {

    private static final String       AND                = "&";
    private static final String       DASH               = "-";
    private static final String       EMPTY              = "";
    private static final String       EXIT               = "exit";
    private static final String       LEFT_SHIFT         = "<<";
    private static final String       NOT                = "~";
    private static final int          NUMBER_OF_OPERANDS = 2;
    private static final String       OR                 = "|";
    private static final String       PADDING            = "0";
    private static final String       REPLACEMENT        = "#";
    private static final String       RIGHT_SHIFT        = ">>";
    private static final String       SPACE              = " ";
    private static final String       XOR                = "^";
    private static final List<String> OPERATORS          = List.of(AND, OR, XOR, LEFT_SHIFT, RIGHT_SHIFT, NOT);

    public static void main(String[] args) {
        String operation;
        var consoleInput = new Scanner(System.in);

        System.out.println("Please enter bitwise operations:");

        while (true) {
            operation = consoleInput.nextLine();

            if (!operation.contains(EXIT)) {
                try {
                    var decimals = extractNumbers(operation);
                    var calculation = calculate(decimals);
                    print(decimals, calculation);
                }
                catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                break;
            }
        }
    }

    private static long calculate(Decimal decimals) {
        long decimalResult;

        switch (decimals.operator) {
            case NOT -> decimalResult = ~decimals.secondDecimal;
            case AND -> decimalResult = decimals.firstDecimal & decimals.secondDecimal;
            case OR -> decimalResult = decimals.firstDecimal | decimals.secondDecimal;
            case XOR -> decimalResult = decimals.firstDecimal ^ decimals.secondDecimal;
            case LEFT_SHIFT -> decimalResult = decimals.firstDecimal << decimals.secondDecimal;
            case RIGHT_SHIFT -> decimalResult = decimals.firstDecimal >> decimals.secondDecimal;
            default -> throw new IllegalArgumentException("Unsupported operation.");
        }

        return decimalResult;
    }

    private static Binary createBinary(Decimal decimal, long decimalResult) {
        var firstBinary = Long.toBinaryString(decimal.firstDecimal);
        var secondBinary = Long.toBinaryString(decimal.secondDecimal);
        var binaryResult = Long.toBinaryString(decimalResult);

        int maxLength = Stream
                .of(firstBinary, secondBinary, binaryResult)
                .map(String::length)
                .max(Integer::compareTo)
                .orElse(0);

        return new Binary(firstBinary, secondBinary, binaryResult, maxLength);
    }

    private static Decimal extractNumbers(String operation) {
        String operator = OPERATORS.stream().filter(operation::contains).findAny().orElse(EMPTY);

        String[] decimalArray = operation
                .replace(SPACE, EMPTY)
                .replace(operator, REPLACEMENT)
                .split(REPLACEMENT);

        if (decimalArray.length == NUMBER_OF_OPERANDS) {
            return new Decimal(parseLong(decimalArray[0]), parseLong(decimalArray[1]), operator);
        } else {
            throw new IllegalArgumentException("Please provide one valid operator.");
        }
    }

    private static long parseLong(String decimal) {
        try {
            return Long.parseLong(decimal);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException(decimal + (!isNumeric(decimal) ? " is not a valid number." : " is too large."));
        }
    }

    private static void print(Decimal decimal, long decimalResult) {
        var binary = createBinary(decimal, decimalResult);

        if (!Objects.equals(decimal.operator, NOT)) {
            System.out.println(StringUtils.leftPad(binary.firstBinary, binary.maxLength, PADDING) + SPACE + decimal.firstDecimal);
        }

        System.out.println(StringUtils.leftPad(binary.secondBinary, binary.maxLength, PADDING) + SPACE + decimal.secondDecimal);
        System.out.println(StringUtils.leftPad(DASH, binary.maxLength, DASH) + SPACE + decimal.operator());
        System.out.println(StringUtils.leftPad(binary.binaryResult, binary.maxLength, PADDING) + SPACE + decimalResult);
        System.out.println();
    }

    private record Binary(
            String firstBinary,
            String secondBinary,
            String binaryResult,
            int maxLength
    ) {

    }

    private record Decimal(
            long firstDecimal,
            long secondDecimal,
            String operator) {

    }
}
