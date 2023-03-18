package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainStream {
    public static void main(String[] args) {
        int[] numbers = new int[]{2, 1, 1, 4, 1, 5};
        System.out.println("minValue = " + minValue(numbers));

        List<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(3);
        list.add(1);
        System.out.println(oddOrEven(list));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .sorted()
                .distinct()
                .reduce(0, (left, right) ->
                        left * 10 + right);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int oddSum = integers.stream().reduce(0, (left, right) -> left + right) % 2;
        return integers.stream().filter(integer ->
                        ((integer % 2 != 0 && oddSum != 0) || (integer % 2 == 0 && oddSum == 0)))
                .toList();

    }
}
