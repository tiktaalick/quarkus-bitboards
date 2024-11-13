package org.mark;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class BitwiseCalculator {

    private static final String AND         = "&";
    private static final String DASH        = "-";
    private static final String EMPTY       = "";
    private static final String EXIT        = "exit";
    private static final String LEFT_SHIFT  = "<<";
    private static final String NOT         = "~";
    private static final String OR          = "|";
    private static final String PADDING     = "0";
    private static final String REPLACEMENT = "#";
    private static final String RIGHT_SHIFT = ">>";
    private static final String SPACE       = " ";
    private static final String XOR         = "^";

    public static void main(String[] args) {
        var operation = EMPTY;
        var consoleInput = new Scanner(System.in);

        System.out.println("Please enter bitwise operations:");

        while (!operation.contains(EXIT)) {
            operation = consoleInput.nextLine();

            if (!operation.contains(EXIT)) {
                var decimals = extractNumbers(operation);
                var calculation = calculate(operation, decimals);
                print(decimals, calculation);
            }
        }
    }

    private static Calculation calculate(String operation, Decimal decimals) {
        Calculation calculation;

        if (operation.contains(NOT)) {
            calculation = new Calculation(NOT, ~decimals.secondDecimal);
        } else if (operation.contains(AND)) {
            calculation = new Calculation(AND, decimals.firstDecimal & decimals.secondDecimal);
        } else if (operation.contains(OR)) {
            calculation = new Calculation(OR, decimals.firstDecimal | decimals.secondDecimal);
        } else if (operation.contains(XOR)) {
            calculation = new Calculation(XOR, decimals.firstDecimal ^ decimals.secondDecimal);
        } else if (operation.contains(LEFT_SHIFT)) {
            calculation = new Calculation(LEFT_SHIFT, decimals.firstDecimal << decimals.secondDecimal);
        } else if (operation.contains(RIGHT_SHIFT)) {
            calculation = new Calculation(RIGHT_SHIFT, decimals.firstDecimal >> decimals.secondDecimal);
        } else {
            System.out.println("Unsupported operation.");
            calculation = new Calculation(EMPTY, 0);
        }
        return calculation;
    }

    private static Binary createBinary(Decimal decimal, Calculation calculation) {
        var firstBinary = Long.toBinaryString(decimal.firstDecimal);
        var secondBinary = Long.toBinaryString(decimal.secondDecimal);
        var binaryResult = Long.toBinaryString(calculation.decimalResult());

        int maxLength = Stream
                .of(firstBinary, secondBinary, binaryResult)
                .map(String::length)
                .max(Integer::compareTo)
                .orElse(0);

        return new Binary(firstBinary, secondBinary, binaryResult, maxLength);
    }

    private static Decimal extractNumbers(String operation) {
        String[] decimalArray = split(operation);

        return new Decimal(parseLong(decimalArray[0]), parseLong(decimalArray[1]));
    }

    private static long parseLong(String decimal) {
        try {
            return Long.parseLong(decimal);
        }
        catch (NumberFormatException e) {
            if (decimal.isEmpty()) {
                System.out.println("Invalid operator.");
            } if (!isNumeric(decimal)) {
                System.out.println(decimal + " is not a valid number.");
            } else {
                System.out.println(decimal + " is too large.");
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please provide two numbers.");
        }

        return 0L;
    }

    private static void print(Decimal decimal, Calculation calculation) {
        var binary = createBinary(decimal, calculation);

        if (!Objects.equals(calculation.operator, NOT)) {
            System.out.println(StringUtils.leftPad(binary.firstBinary, binary.maxLength, PADDING) + SPACE + decimal.firstDecimal);
        }

        System.out.println(StringUtils.leftPad(binary.secondBinary, binary.maxLength, PADDING) + SPACE + decimal.secondDecimal);
        System.out.println(StringUtils.leftPad(DASH, binary.maxLength, DASH) + SPACE + calculation.operator());
        System.out.println(StringUtils.leftPad(binary.binaryResult, binary.maxLength, PADDING) + SPACE + calculation.decimalResult());
        System.out.println();
    }

    private static String[] split(String operation) {
        return operation
                .trim()
                .replace(AND, REPLACEMENT)
                .replace(OR, REPLACEMENT)
                .replace(XOR, REPLACEMENT)
                .replace(LEFT_SHIFT, REPLACEMENT)
                .replace(RIGHT_SHIFT, REPLACEMENT)
                .replace(NOT, 0 + REPLACEMENT)
                .replace(SPACE, EMPTY)
                .split(REPLACEMENT);
    }

    private record Binary(
            String firstBinary,
            String secondBinary,
            String binaryResult,
            int maxLength
    ) {

    }

    private record Calculation(
            String operator,
            long decimalResult) {

    }

    private record Decimal(
            long firstDecimal,
            long secondDecimal) {

    }
}
