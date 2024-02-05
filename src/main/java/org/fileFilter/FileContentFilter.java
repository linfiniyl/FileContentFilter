package org.fileFilter;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

public class FileContentFilter {
    public static void main(String[] args) {
        int i = 0;
        String path = "";
        String prefix = "";
        boolean additionMode = false;
        boolean statistic = false;
        boolean fullStatistic = false;
        Queue<BigInteger> integers = new LinkedList<>();
        Queue<BigDecimal> floats = new LinkedList<>();
        Queue<String> strings = new LinkedList<>();
        Queue<String> fileList = new LinkedList<>();

        BigInteger maxInt = new BigInteger("0"), minInt = new BigInteger("0");
        BigDecimal avgInt = new BigDecimal("0");
        BigInteger sumInt = new BigInteger("0"), countInt = new BigInteger("0");
        BigDecimal maxFlt = new BigDecimal("0"), minFlt = new BigDecimal("0"), avgFlt = new BigDecimal("0");
        BigDecimal sumFlt = new BigDecimal("0");
        BigInteger countFlt = new BigInteger("0");
        BigInteger maxStr = new BigInteger("0"), minStr = new BigInteger("0"), countStr = new BigInteger("0");

        while(i < args.length) {
            switch (args[i]) {
                case "-o" -> {
                    if (args.length > i + 1 && args[i + 1] != null) {
                        path = args[i + 1];
                        Path pathCheck = Paths.get(path);
                        if (!Files.exists(pathCheck)) {
                            System.out.println("This " + path + " directory does not exist." +
                                    " The default directory will be used");
                            path = "";
                        }
                        i += 2;
                    } else {
                        i += 1;
                        System.out.println(
                                "The parameters are not set correctly. " +
                                        "Please try again based on the call pattern " + "\n" +
                                        "java -jar FileContentFilter.jar [-o path]" +
                                        " [-p prefix] [-a] [-f|-s] file_name [file_name_1 file_name_2 ..]"
                        );
                    }
                }
                case "-p" -> {
                    if (args.length > i + 1 && args[i + 1] != null) {
                        prefix = args[i + 1];
                        if (prefix.matches(".*[><:|\"?*\\\\]+.*")) {
                            System.out.println(
                                    "Incorrect file prefix. " +
                                            "Do not use illegal characters \"\\<>?:|/*\". " +
                                            "Default files will be used. ");
                            prefix = "";
                        }
                        i += 2;
                    } else {
                        i += 1;
                        System.out.println(
                                "The parameters are not set correctly. " +
                                        "Please try again based on the call pattern " + "\n" +
                                        "java -jar FileContentFilter.jar [-o path]" +
                                        " [-p prefix] [-a] [-f|-s] file_name [file_name_1 file_name_2 ..]"
                        );
                    }
                }
                case "-a" -> {
                    additionMode = true;
                    i += 1;
                }
                case "-s" -> {
                    statistic = true;
                    i += 1;
                }
                case "-f" -> {
                    fullStatistic = true;
                    i += 1;
                }
                default -> {
                    for (int j = i; j < args.length; j++) {
                        fileList.offer(args[i]);
                        i += 1;
                    }
                }
            }
        }
        if (fileList.size() == 0){
            System.out.println("No files are entered. " +
                    "Please try again based on the call pattern " + "\n" +
                    "java -jar FileContentFilter.jar [-o path]" +
                    " [-p prefix] [-a] [-f|-s] file_name [file_name_1 file_name_2 ..]"
            );
        }

        String str;
        String file;

       while((file = fileList.poll()) != null){
            try (BufferedReader buffer =
                         new BufferedReader(new FileReader(file))) {
                while (buffer.ready()) {
                    str = buffer.readLine();
                    if (str.matches("-?\\b(?<!\\.)\\d+(?!\\.)\\b")) {
                        integers.offer(new BigInteger(str));
                    } else if (str.matches("-?([0-9]*[.])[0-9]+([Ee]-?([0-9]+))?")) {
                        floats.offer(new BigDecimal(str));
                    } else {
                        strings.offer(str);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error with reading from file: " + file);
            }
        }
        if (statistic || fullStatistic){
            if (integers.size() > 0) {
                countInt = new BigInteger(String.valueOf(integers.size()));
            }
            if (floats.size() > 0) {
                countFlt = new BigInteger(String.valueOf(floats.size()));
            }
            if (strings.size() > 0){
                countStr = new BigInteger(String.valueOf(strings.size()));
            }
        }
        if (fullStatistic){
            if (integers.size() > 0) {
                minInt = integers.stream().min(BigInteger::compareTo).get();
                maxInt = integers.stream().max(BigInteger::compareTo).get();
                sumInt = integers.stream().reduce(BigInteger.ZERO, BigInteger::add);
                avgInt = new BigDecimal(sumInt).divide(new BigDecimal(countInt), 3, RoundingMode.HALF_UP);
            }
            if (floats.size() > 0) {
                minFlt = floats.stream().min(BigDecimal::compareTo).get();
                maxFlt = floats.stream().max(BigDecimal::compareTo).get();
                sumFlt = floats.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
                avgFlt = sumFlt.divide(new BigDecimal(countFlt), 3, RoundingMode.HALF_UP);
            }
            if (strings.size() > 0){
                minStr = new BigInteger(String.valueOf(strings.stream().max(String::compareTo).get().length()));
                maxStr = new BigInteger(String.valueOf(strings.stream().min(String::compareTo).get().length()));
            }
        }

        if (integers.size() > 0) {
            try (BufferedWriter bufferInt = new BufferedWriter(
                    new FileWriter(path + prefix + "integers.txt", additionMode))) {
                BigInteger intWrite;
                while((intWrite = integers.poll()) != null){
                    bufferInt.write(intWrite + "\n");
                }
                System.out.println("Integers written to file: " + countInt);
                if (fullStatistic){
                    System.out.println("Maximum integer: " + maxInt);
                    System.out.println("Minimum integer: " + minInt);
                    System.out.println("Sum of the integers: " + sumInt);
                    System.out.println("Average value of the integers: " + avgInt);
                    System.out.println();
                }
            } catch (IOException e) {
                System.out.println("Error writing to file: " + path + prefix + "integers.txt");
            }
        } else {
            System.out.println("Integers are not found.");
        }
        if (floats.size() > 0) {
            try (BufferedWriter bufferFlt = new BufferedWriter(
                    new FileWriter(path + prefix + "floats.txt", additionMode))) {
                BigDecimal fltWrite;
                while((fltWrite = floats.poll()) != null){
                    bufferFlt.write(fltWrite + "\n");
                }
                System.out.println("Float numbers written to file: " + countFlt);
                if (fullStatistic){
                    System.out.println("Maximum float number: " + maxFlt);
                    System.out.println("Minimum float number: " + minFlt);
                    System.out.println("Sum of the float numbers: " + sumFlt);
                    System.out.println("Average value of the float numbers: " + avgFlt);
                    System.out.println();
                }
            } catch (IOException e) {
                System.out.println("Error writing to file: " + path + prefix + "floats.txt");
            }
        } else {
            System.out.println("Float numbers are not found.");
        }
        if (strings.size() > 0){
            try (BufferedWriter bufferStr = new BufferedWriter(
                    new FileWriter(path + prefix + "strings.txt", additionMode))) {
                String strWrite;
                while((strWrite = strings.poll()) != null){
                    bufferStr.write(strWrite + "\n");
                }
                System.out.println("Strings written to file: " + countStr);
                if (fullStatistic){
                    System.out.println("Maximum string length: " + maxStr);
                    System.out.println("Minimum string length: " + minStr);
                    System.out.println();
                }
            } catch (IOException e) {
                System.out.println("Error writing to file: " + path + prefix + "strings.txt");
            }
        } else {
            System.out.println("Strings are not found.");
        }
        System.out.println("Completed");
    }
}




/*
integers.txt, floats.txt, strings.txt - Default files.

-o задавать путь к эти файлам

-p префикс к файлам

-a режим добавления в существующие файлы. По умолчанию режим перезаписывания

Если данных для конкретного файла нет, то и создавать его не нужно.

-s статистика кол-ва записанных элементов в файлы. -f полная статистика min, max, average, sum. Для строк кол-во, min, max.
 */