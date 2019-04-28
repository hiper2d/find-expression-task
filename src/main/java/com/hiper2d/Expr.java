package com.hiper2d;

import java.util.Arrays;

public class Expr {

    private static final char[] OPTS = new char[] { '+', '-', '*', '/' };

    public static void main(String[] args) {
        Expr expr = new Expr();

        if (args.length != 4) {
            throw new IllegalArgumentException("Should be exactly 4 numeric arguments");
        }

        try {
            int[] input = parseArguments(args);
            expr.canBeEqualTo24(input);
        } catch (NumberFormatException ex) {
            System.out.println("Can't parse arguments " + Arrays.toString(args) + " to int numbers from 1 to 9");
        }
    }

    public boolean canBeEqualTo24(int[] numbers) {
        validateNumbers(numbers);

        for (int i1 = 0; i1 < 4; i1++) {
            for (int i2 = 0; i2 < 4; i2++) {
                if (i1 == i2) {
                    continue;
                }
                for (int i3 = 0; i3 < 4; i3++) {
                    if (i3 == i1 || i3 == i2) {
                        continue;
                    }
                    for (int i4 = 0; i4 < 4; i4++) {
                        if (i4 == i1 || i4 == i2 || i4 == i3) {
                            continue;
                        }
                        if (checkForSequence(new int[] { numbers[i1], numbers[i2], numbers[i3], numbers[i4] })) {
                            return true;
                        }
                    }
                }
            }
        }
        System.out.println("No results for " + Arrays.toString(numbers));
        return false;
    }

    private boolean checkForSequence(int[] numbers) {
        for (int o1 = 0; o1 < 4; o1++) {
            for (int o2 = 0; o2 < 4; o2++) {
                for (int o3 = 0; o3 < 4; o3++) {
                    if (checkForSequenceWithOperations(numbers, new char[] { OPTS[o1], OPTS[o2], OPTS[o3] })) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkForSequenceWithOperations(int[] numbers, char[] ops) {
        try {
            double[] doubles = Arrays.stream(numbers).asDoubleStream().toArray();

            if (calcInNormalOrder(doubles, ops) == 24d) {
                System.out.println("Found combination: " + Arrays.toString(numbers) + ", " + Arrays.toString(ops) + ", order: ((a + b) + c) + d");
                return true;
            }

            if (calcInNormalBackOrder(doubles, ops) == 24d) {
                System.out.println("Found combination: " + Arrays.toString(numbers) + ", " + Arrays.toString(ops) + ", order: (a + (b + c)) + d");
                return true;
            }

            if (calcInReversedOrder(doubles, ops) == 24d) {
                System.out.println("Found combination: " + Arrays.toString(numbers) + ", " + Arrays.toString(ops) + ", order: a + (b + (c + d))");
                return true;
            }

            if (calcInReversedBackOrder(doubles, ops) == 24d) {
                System.out.println("Found combination: " + Arrays.toString(numbers) + ", " + Arrays.toString(ops) + ", order: a + ((b + c) + d)");
                return true;
            }

            if (calcFromBothEnds(doubles, ops) == 24d) {
                System.out.println("Found combination: " + Arrays.toString(numbers) + ", " + Arrays.toString(ops) + ", order: (a + b) + (c + d)");
                return true;
            }

            return false;
        } catch (RuntimeException ex) {
            // Division by 0 exception.
            // No need to do anything, just return false since the combination is invalid anyway.
            return false;
        }
    }

    // ((a + b) + c) + d
    private double calcInNormalOrder(double[] numbers, char[] ops) {
        double r1 = calc(numbers[0], numbers[1], ops[0]);
        double r2 = calc(r1, numbers[2], ops[1]);
        return round(calc(r2, numbers[3], ops[2]));
    }

    // (a + (b + c)) + d
    private double calcInNormalBackOrder(double[] numbers, char[] ops) {
        double r1 = calc(numbers[1], numbers[2], ops[1]);
        double r2 = calc(numbers[0], r1, ops[0]);
        return round(calc(r2, numbers[3], ops[2]));
    }

    // a + (b + (c + d))
    private double calcInReversedOrder(double[] numbers, char[] ops) {
        double r1 = calc(numbers[2], numbers[3], ops[2]);
        double r2 = calc(numbers[1], r1, ops[1]);
        return round(calc(numbers[3], r2, ops[0]));
    }

    // a + ((b + c) + d)
    private double calcInReversedBackOrder(double[] numbers, char[] ops) {
        double r1 = calc(numbers[1], numbers[2], ops[1]);
        double r2 = calc(r1, numbers[3], ops[2]);
        return round(calc(numbers[0], r2, ops[0]));
    }

    // (a + b) + (c + d)
    private double calcFromBothEnds(double[] numbers, char[] ops) {
        double r1 = calc(numbers[0], numbers[1], ops[0]);
        double r2 = calc(numbers[2], numbers[3], ops[1]);
        return round(calc(r1, r2, ops[2]));
    }

    private double calc(double bigA, double bigB, char op) {
        switch (op) {
            case '+':
                return bigA + bigB;
            case '-':
                return bigA - bigB;
            case '*':
                return bigA * bigB;
            case '/':
                return bigA / bigB;
        }
        throw new UnsupportedOperationException("What the hell have you entered???");
    }

    private double round(double val) {
        return Math.round(val * 10) / 10.0; // I want to leave one decimal after a coma just in case
    }

    private static int[] parseArguments(String[] args) {
        int[] input = new int[4];
        for (int i = 0; i < 4; i++) {
            input[i] = Integer.parseInt(args[i]);
        }
        return input;
    }

    private void validateNumbers(int[] numbers) {
        for (int i = 0; i < 4; i++) {
            if (numbers[i] <= 0 || numbers[i] > 9) {
                throw new NumberFormatException("Arguments should be in 1-9 int range");
            }
        }
    }
}
