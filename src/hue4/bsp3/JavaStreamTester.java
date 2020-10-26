/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hue4.bsp3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author holzm
 */
public class JavaStreamTester {

    public static List<String> strings = new ArrayList<>();
    public static List<Integer> numbers = new ArrayList<>();

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            strings.add(befuellenString());

        }

        for (int i = 0; i < 10; i++) {
            numbers.add((int) (Math.random() * 100));
        }

        System.out.println("Anzahl leerer Strings: " + getCountEmptyString(strings));
        System.out.println("----------------");

        System.out.println("Anzahl der Strings mit Länge 3: " + getCountLength3(strings));
        System.out.println("----------------");

        System.out.println("Liste ohne leere Strings:");
        for (int i = 0; i < strings.size(); i++) {

            System.out.println(deleteEmptyStrings(strings).get(i));
        }
        System.out.println("----------------");

        System.out.println("gemerchter String: " + getMergedString(strings, ";"));
        System.out.println("----------------");

        System.out.println("Liste der Quadrate:");
        for (int i = 0; i < numbers.size(); i++) {
            System.out.println(getSquares(numbers).get(i));
        }
        System.out.println("----------------");

        System.out.println("MAX:" + getMax(numbers));
        System.out.println("----------------");

        System.out.println("MIN:" + getMin(numbers));
        System.out.println("----------------");

        System.out.println("Summe:" + getSum(numbers));
        System.out.println("----------------");

        System.out.println("Durchschnitt:" + getAverage(numbers));
        System.out.println("----------------");

        for (int i = 0; i < numbers.size(); i++) {
            System.out.println(numbers.get(i));

        }

        for (int i = 0; i < strings.size(); i++) {
            System.out.println(strings.get(i));

        }
    }

    private static int getCountEmptyString(List<String> strings) {
        return (int) strings.stream().filter(string -> string.isEmpty()).count();
    }

    private static int getCountLength3(List<String> strings) {
        return (int) strings.stream().filter(string -> string.length() == 3).count();
    }

    private static List<String> deleteEmptyStrings(List<String> strings) {
        return strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
    }

    private static String getMergedString(List<String> strings, String separator) {
        return strings.stream().collect(Collectors.joining(separator));
        // return strings.stream().map(string -> string + separator).toString();
    }

    private static List<Integer> getSquares(List<Integer> numbers) {
        return numbers.stream().map(number -> number * number).collect(Collectors.toList());
    }

    private static int getMax(List<Integer> numbers) {
        return numbers.stream().max(Integer::compare).get();
    }

    private static int getMin(List<Integer> numbers) {
        return numbers.stream().min(Integer::compare).get();
    }

    private static int getSum(List<Integer> numbers) {
        return numbers.stream().reduce(0, (a, b) -> a + b);
    }

    private static int getAverage(List<Integer> numbers) {
        return (int) numbers.stream().mapToDouble(x -> x).average().getAsDouble();
    }

    public static String befuellenString() {

        String ausgabe = "----------------";

        String grossB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String kleinB = grossB.toLowerCase();

        String all = grossB + kleinB;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int j = 0;
            Random rand = new Random();

            sb.append(all.charAt(rand.nextInt(all.length())));
            j++;

            ausgabe = sb.toString();

        }
        return ausgabe;
    }
}
