/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hue4.bsp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author holzm
 */
public class Main1 {

    List<String> list = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in, "Windows-1252");
        System.out.println("Geben Sie einen Teiler ein: ");
        int teiler = Integer.parseInt(scanner.nextLine());
        System.out.println("In wie viele Unterteile soll die Liste aufgeteilt werden?");
        int unterteile = Integer.parseInt(scanner.nextLine());

        Main1 main = new Main1();
        main.readFile();

        System.out.println(main.list.size());
        System.out.println(main.toIntList().size());
        System.out.println("--------------------------");

        List<Integer> list = main.toIntList();
        Predicate<Integer> teilbar = i -> i % teiler == 0;

        int length = list.size() / unterteile;
        List<List<Integer>> unterteileList = chopped(list, length);

        ExecutorService death;
        death = Executors.newFixedThreadPool(unterteile);
        for (List<Integer> unterteilteListe : unterteileList) {
            death.execute(() -> {
                for (int i = 0; i < unterteilteListe.size(); i++) {
                    if (teilbar.test(unterteilteListe.get(i))) {
                        System.out.println(unterteilteListe.get(i));
                    }
                }
            });
        }

        death.awaitTermination(20, TimeUnit.SECONDS);
    }

    public void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("numbers.csv")));
            String line = br.readLine();
            while (line != null) {
                list.addAll(Arrays.asList(line.split(":")));
                line = br.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isNumber(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }

    }

    public List<Integer> toIntList() {
        List<Integer> intList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (isNumber(list.get(i)) == true) {
                intList.add(Integer.parseInt(list.get(i)));
            }
        }
        return intList;
    }

    static <T> List<List<T>> chopped(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<List<T>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<T>(
                    list.subList(i, Math.min(N, i + L)))
            );
        }
        return parts;
    }
}
