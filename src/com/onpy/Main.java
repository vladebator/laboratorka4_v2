package com.onpy;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.pow;

public class Main {


    public static int CheckCorrectFunction(String value) {
        int func = -1;
        try {
            func = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            func = -1;
        }

        return func;
    }

    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(System.in);
        int inCountTriangle = 1;//scan.nextInt();
        System.out.println("Введите количество треугольников: " + inCountTriangle);

        Triangle[] triangles = new Triangle[inCountTriangle];

        int CountTriangle = 0;
        int function = 0;

        for (int i = 0; i < triangles.length; i++) {

            triangles[i] = new Triangle();

            System.out.print("Введите cторону Х1 для треугольника №" + (i + 1) + ": ");
            triangles[i].x1 = scan.nextInt();
            System.out.print("Введите cторону Х2 для треугольника №" + (i + 1) + ": ");
            triangles[i].x2 = scan.nextInt();
            System.out.print("Введите cторону Х3 для треугольника №" + (i + 1) + ": ");
            triangles[i].x3 = scan.nextInt();

            triangles[i].numberTriangle = ++CountTriangle;
        }

        for (int i = 0; i < triangles.length; i++) {
            triangles[i].perimeter = triangles[i].x1 + triangles[i].x2 + triangles[i].x3;
            System.out.println("Периметр треугольника №" + triangles[i].numberTriangle + ": " + triangles[i].perimeter);

            triangles[i].alpha = Math.abs(Math.cos(((pow(triangles[i].x1, 2) + pow(triangles[i].x3, 2) - pow(triangles[i].x2, 2)) / 2 * triangles[i].x1 * triangles[i].x3)));
            triangles[i].betta = Math.abs(Math.cos(((pow(triangles[i].x1, 2) + pow(triangles[i].x2, 2) - pow(triangles[i].x3, 2)) / 2 * triangles[i].x1 * triangles[i].x2)));
            triangles[i].gamma = Math.abs(Math.cos(((pow(triangles[i].x2, 2) + pow(triangles[i].x3, 2) - pow(triangles[i].x1, 2)) / 2 * triangles[i].x3 * triangles[i].x2)));

            double radian = 57.295780;

            System.out.println("Косинус угла Alpha = " + triangles[i].alpha);
            System.out.println("Косинус угла Betta = " + triangles[i].betta);
            System.out.println("Косинус угла Gamma = " + triangles[i].gamma);

            triangles[i].square = 0.5 * triangles[i].x1 * triangles[i].x2 * Math.sin(triangles[i].alpha);
            System.out.println("Площадь треугольника №" + triangles[i].numberTriangle + " = " + triangles[i].square);

            if (triangles[i].x1 == triangles[i].x2 && triangles[i].x1 == triangles[i].x3 && triangles[i].x2 == triangles[i].x3) {
                System.out.println("Треугольник №" + triangles[i].numberTriangle + " является равнобедренным.");
                triangles[i].isosceles = 1;
            }
            else {
                System.out.println("Треугольник №" + triangles[i].numberTriangle + " не является равнобедренным.");
            }
        }

        double averageSquare = 0;
        double max = 0;
        double min = 0;
        int countNormalTriangle = 0;
        int countIsoscelesTriangle = 0;

        // И в этом цикле я вычисляю среднюю площадь для обычных треугольников
        for (int i = 0; i < triangles.length; i++) {
            if(triangles[i].isosceles == 0) {
                countNormalTriangle = countNormalTriangle + 1;
                averageSquare = +triangles[i].square;
            }
        }

        // В этом цикле находим минимальную площадь равнобедренных треугольников
        for (int i = 0; i < triangles.length; i++) {
            if (triangles[i].isosceles == 1) {
                countIsoscelesTriangle = countIsoscelesTriangle + 1;
                for (int j = 0; j != triangles.length; j++) {
                    if (!(triangles[i].square < max)) {
                        max = triangles[i].square;
                    }
                    if (triangles[i].square < min) {
                        min = triangles[i].square;
                    }
                }
            }
        }
        averageSquare = averageSquare / countNormalTriangle;
        // Выводим теперь эти данные на экран, среднюю площадь треугольников и мин.площ. равнобедренного треугольника.
        System.out.println("Средняя площадь треугольников = " + averageSquare);
        System.out.println("Минимальная площадь равнобедренного треугольника = " + min);

        do {
            System.out.println("\nСписок функций: ");
            System.out.println("1. Сохранение в файл");
            System.out.println("2. Загрузить из файла");
            System.out.println("3. Выход из программы");
            do {
                System.out.print("Введите номер функции: ");
                function = CheckCorrectFunction(scan.nextLine());
            } while (function == -1);
            
            switch (function) {
                case 1:
                    System.out.println("Введите путь к файлу:");
                    String wayToFile = scan.nextLine();
                    ArrayList<Triangle> triangleArrayList = new ArrayList<>();
                    triangleArrayList.addAll(Arrays.asList(triangles));
                    BinaryDataSaver.save(triangleArrayList, wayToFile);
                    System.out.println("Файл успешно сохранён!");
                    break;
                case 2:
                    System.out.println("Введите путь к файлу:");
                    String wayToFileLoad = scan.nextLine();
                    //triangles.loadFile(wayToFileLoad);
                    System.out.println("Файл успешно загружен!");
                    break;
                case 3:
                    return;
                default:
                    System.out.println("\nТакой функции нету");
            }
        } while (function != 0);
    }
}