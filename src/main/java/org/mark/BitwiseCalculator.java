package org.mark;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;
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
    private static final int          MAX_LONG_DIGITS        = 64;
    private static final String       MULTIPLY               = "*";
    private static final String       NEW_LINE               = "\n";
    private static final String       NOT                    = "~";
    private static final String       OR                     = "|";
    private static final String       PADDING                = "0";
    private static final String       SPACE                  = " ";
    private static final String       SUBTRACT               = "-";
    private static final String       DASH                   = SUBTRACT;
    private static final String       XOR                    = "^";
    private static final List<String> OPERATORS              = List.of(XOR,
            OR,
            AND,
            LEFT_SHIFT,
            LOGICAL_RIGHT_SHIFT,
            ARITHMETIC_RIGHT_SHIFT,
            NOT,
            SUBTRACT,
            ADD,
            DIVIDE,
            MULTIPLY);

    public static void main(String[] args) {
        String userInput;
        var consoleInput = new Scanner(System.in);

        System.out.println("Please enter bitwise operations:");

        while (true) {
            userInput = consoleInput.nextLine();

            if (!userInput.contains(EXIT)) {
                try {
                    print(calculate(createOperation(userInput)));
                }
                catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage() + NEW_LINE);
                }
            } else {
                break;
            }
        }
    }

    private static Result calculate(Operation operation) {
        long decimalResult;

        switch (operation.operator) {
            case NOT -> decimalResult = ~operation.secondDecimal;
            case AND -> decimalResult = operation.firstDecimal & operation.secondDecimal;
            case OR -> decimalResult = operation.firstDecimal | operation.secondDecimal;
            case XOR -> decimalResult = operation.firstDecimal ^ operation.secondDecimal;
            case LEFT_SHIFT -> decimalResult = operation.firstDecimal << operation.secondDecimal;
            case ARITHMETIC_RIGHT_SHIFT -> decimalResult = operation.firstDecimal >> operation.secondDecimal;
            case LOGICAL_RIGHT_SHIFT -> decimalResult = operation.firstDecimal >>> operation.secondDecimal;
            case ADD -> decimalResult = operation.firstDecimal + operation.secondDecimal;
            case SUBTRACT -> decimalResult = operation.firstDecimal - operation.secondDecimal;
            case MULTIPLY -> decimalResult = operation.firstDecimal * operation.secondDecimal;
            case DIVIDE -> decimalResult = operation.firstDecimal / operation.secondDecimal;
            default -> throw new IllegalArgumentException("Operator " + operation.operator + " is unsupported.");
        }

        return new Result(operation, decimalResult);
    }

    private static Binary createBinary(Result result) {
        var firstBinary = Long.toBinaryString(result.operation.firstDecimal);
        var secondBinary = Long.toBinaryString(result.operation.secondDecimal);
        var binaryResult = Long.toBinaryString(result.decimalResult);

        int maxLength = Stream.of(firstBinary, secondBinary, binaryResult)
                              .map(String::length)
                              .max(Integer::compareTo)
                              .orElse(0);

        return new Binary(firstBinary, secondBinary, binaryResult, maxLength);
    }

    private static Operation createOperation(String userInput) {
        var allNonOperands = userInput.chars()
                                      .mapToObj(Character::toString)
                                      .filter(character -> !character.equals(SPACE) && !isNumeric(character));

        var operator = OPERATORS.stream()
                                .filter(userInput::contains)
                                .findAny()
                                .orElse(allNonOperands.findAny()
                                                      .orElse(userInput));

        int operatorIndex = userInput.lastIndexOf(operator);

        String firstOperand = makeNumeric(isNumeric(operator)
                                          ? operator
                                          : userInput.substring(0, operatorIndex)
                                                     .trim());
        String secondOperand = makeNumeric(isNumeric(operator)
                                           ? EMPTY
                                           : userInput.substring(operatorIndex + operator.length())
                                                      .trim());

        return new Operation(parseLong(firstOperand), parseLong(secondOperand), isNumeric(operator) ? ADD : operator);
    }

    private static String makeNumeric(String operand) {
        String numericOperand;

        if (operand.equals(EMPTY)) {
            numericOperand = "0";
        } else {
            try {
                numericOperand = String.valueOf(parseLong(operand));
            }
            catch (IllegalArgumentException e) {
                var result = calculate(createOperation(operand));
                numericOperand = String.valueOf(result.decimalResult());
                print(result);
            }
        }

        return numericOperand;
    }

    private static long parseLong(String decimal) {
        try {
            return decimal.isEmpty() ? 0L : Long.parseLong(decimal);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException(decimal + (!isNumeric(decimal) ? " is not a valid number." : " is too large."));
        }
    }

    private static void print(Result result) {
        var binary = createBinary(result);
        var binaries = List.of(StringUtils.leftPad(binary.firstBinary, binary.maxLength, PADDING),
                SPACE + result.operation.firstDecimal + NEW_LINE,
                StringUtils.leftPad(binary.secondBinary, binary.maxLength, PADDING),
                SPACE + result.operation.secondDecimal + NEW_LINE,
                StringUtils.leftPad(DASH, binary.maxLength, DASH),
                SPACE + result.operation.operator() + NEW_LINE,
                StringUtils.leftPad(binary.binaryResult, binary.maxLength, PADDING),
                SPACE + result.decimalResult + NEW_LINE + NEW_LINE);

        IntStream.range(0, binaries.size())
                 .forEach(index -> printLine(index, binaries));
    }

    private static void printLine(int index, List<String> binaries) {
        if (index % 2 == 0) {
            System.out.print(StringUtils.leftPad(binaries.get(index), MAX_LONG_DIGITS));
        } else {
            System.out.print(binaries.get(index));
        }
    }

    private record Binary(
            String firstBinary,
            String secondBinary,
            String binaryResult,
            int maxLength) {

    }

    private record Operation(
            long firstDecimal,
            long secondDecimal,
            String operator) {

    }

    private record Result(
            Operation operation,
            long decimalResult) {

    }
}
