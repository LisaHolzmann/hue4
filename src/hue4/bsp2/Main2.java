/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hue4.bsp2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author holzm
 */
public class Main2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Scanner scan = new Scanner(System.in, "Windows-1252");
        int n = 0;
        List<Integer> list = new LinkedList<Integer>();
        final int length = 100;

        while (n == 0) {
            System.out.println("Geben Sie bitte eine Zahl n ein:");

            n = Integer.parseInt(scan.nextLine());

            for (int i = 0; i <= n; i++) {
                list.add(i);
            };
        }

        final int teilbereiche = list.size() / length;
        List<List<Integer>> unterteileList = chopped(list, length);

        ExecutorService exc = Executors.newCachedThreadPool();

        List<Runnable> tasks = new LinkedList<>();

        for (List<Integer> unterteilteList : unterteileList) {
            tasks.add(new Runnable(unterteilteList));
        }

        //speichert ergebnisse in liste
        List<Future<Integer>> results = exc.invokeAll(tasks);
        exc.shutdown();

        //speichert ergebnisse in List<Integer>
        List<Integer> numbers = new LinkedList<Integer>();
        for (Future<Integer> re : results) {
            numbers.add(re.get());
        }

        int result = numbers.stream()
                .reduce(0, (a, b) -> a + b);
        System.out.println(result);

    }

    //erstellt Sublist
    static <T> List<List<T>> chopped(List<T> list, int length) {
        List<List<T>> parts = new ArrayList<List<T>>();

        for (int i = 0; i < list.size(); i += length) {
            parts.add(new ArrayList<T>(
                    list.subList(i, Math.min(list.size(), i + length)))
            );
        }
        return parts;
    }

}
