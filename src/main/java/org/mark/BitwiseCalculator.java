package org.mark;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class BitwiseCalculator {

    private static final String       ADD                    = "+";
    private static final String       AND                    = "&";
    private static final String       ARITHMETIC_RIGHT_SHIFT = ">>";
    private static final String       DIVIDE                 = "/";
    private static final String       EXIT                   = "exit";
    private static final String       LEFT_SHIFT             = "<<";
    private static final String       LOGICAL_RIGHT_SHIFT    = ">>>";
    private static final String       MULTIPLY               = "*";
    private static final String       NEW_LINE               = "\n";
    private static final String       NOT                    = "~";
    private static final String       OR                     = "|";
    private static final String       PADDING                = "0";
    private static final String       SPACE                  = " ";
    private static final String       SUBTRACT               = "-";
    private static final String       DASH                   = SUBTRACT;
    private static final String       XOR                    = "^";
    private static final List<String> OPERATORS              = List.of(AND, OR, XOR, LEFT_SHIFT,
            LOGICAL_RIGHT_SHIFT, ARITHMETIC_RIGHT_SHIFT, NOT, ADD, SUBTRACT, MULTIPLY, DIVIDE);

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
                    System.out.println(e.getMessage() + NEW_LINE);
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
            case ARITHMETIC_RIGHT_SHIFT -> decimalResult = decimals.firstDecimal >> decimals.secondDecimal;
            case LOGICAL_RIGHT_SHIFT -> decimalResult = decimals.firstDecimal >>> decimals.secondDecimal;
            case ADD -> decimalResult = decimals.firstDecimal + decimals.secondDecimal;
            case SUBTRACT -> decimalResult = decimals.firstDecimal - decimals.secondDecimal;
            case MULTIPLY -> decimalResult = decimals.firstDecimal * decimals.secondDecimal;
            case DIVIDE -> decimalResult = decimals.firstDecimal / decimals.secondDecimal;
            default -> throw new IllegalArgumentException("Operator " + decimals.operator + " is unsupported.");
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
        var allNonOperands = operation
                .chars()
                .mapToObj(Character::toString)
                .filter(character -> !character.equals(SPACE) && !StringUtils.isNumeric(character));

        var operator = OPERATORS.stream().filter(operation::contains).findAny().orElse(allNonOperands.findAny().orElse(operation));

        int index = operation.lastIndexOf(operator);

        String firstOperand = StringUtils.isNumeric(operator) ? operator : operation.substring(0, index).trim();
        String secondOperand = StringUtils.isNumeric(operator) ? EMPTY : operation.substring(index + operator.length()).trim();

        return new Decimal(parseLong(firstOperand), parseLong(secondOperand), StringUtils.isNumeric(operator) ? ADD : operator);
    }

    private static long parseLong(String decimal) {
        try {
            return decimal.isEmpty() ? 0L : Long.parseLong(decimal);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException(decimal + (!isNumeric(decimal) ? " is not a valid number." : " is too large."));
        }
    }

    private static void print(Decimal decimal, long decimalResult) {
        var binary = createBinary(decimal, decimalResult);

        if (!List.of(decimal.firstDecimal, decimal.secondDecimal).contains(0L)) {
            System.out.println(StringUtils.leftPad(binary.firstBinary, binary.maxLength, PADDING) + SPACE + decimal.firstDecimal);
            System.out.println(StringUtils.leftPad(binary.secondBinary, binary.maxLength, PADDING) + SPACE + decimal.secondDecimal);
            System.out.println(StringUtils.leftPad(DASH, binary.maxLength, DASH) + SPACE + decimal.operator());
        }
        System.out.println(StringUtils.leftPad(binary.binaryResult, binary.maxLength, PADDING) + SPACE + decimalResult + NEW_LINE);
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
