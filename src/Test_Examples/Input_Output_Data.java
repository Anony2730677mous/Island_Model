package Test_Examples;

import java.util.ArrayList;
import java.util.Scanner;

public class Input_Output_Data // класс, запрашивающий у пользователя различные данные для моделирования острова и выводящий статистику на экран
{
    public static int[] print_Intro() // метод, предлагающий задать пользователю размеры острова в заданных пределах и возвращающий массив целочисленных значений
    {
        int[] array_dimension;
        System.out.println("Для проведения эксперимента \"Модель острова животных\", пожалуйста, задайте размеры острова");
        int x_dimension = set_X_Dimension();
        int y_dimension = set_Y_Dimension();
        System.out.println("Создан остров размерами: " + x_dimension + " на " + y_dimension + " локаций");
        return array_dimension = new int[]{x_dimension, y_dimension};
    }
    static private int set_X_Dimension() // метод, запрашивающий у пользователя размер длины острова, возвращает переменную типа int
    {
        int input_Data;
        int dimension = 0;
        System.out.println("Введите длину острова - целое положительное число больше 0, не включая 0, до 100 включительно");
        Scanner scanner = new Scanner(System.in);
       if(scanner.hasNext())
       {
           if(scanner.hasNextInt())
           {
               input_Data = scanner.nextInt();
               if(input_Data >0 && input_Data <=100)
               {
                   dimension = input_Data;
                   System.out.println("Длина острова равна: " + dimension);
               }
               else
               {
                   System.out.println("Неправильный тип данных, попробуйте ещё раз");
                   set_X_Dimension();
               }
           }
           else if (scanner.hasNextLine())
           {
               System.out.println("Неправильный тип данных, попробуйте ещё раз");
               set_X_Dimension();
           }
       }
        return dimension;
    }
    public static int set_Time_Duration() // метод, запрашивающий у пользователя продолжительность жизни на острове, возвращает переменную типа int
    {
        int input_Data;
        int time_Duration = 0;
        System.out.println("Введите продолжительность действия эксперимента - целое положительное число больше 0, не включая 0, до 10 включительно");
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNext())
        {
            if(scanner.hasNextInt())
            {
                input_Data = scanner.nextInt();
                if(input_Data >0 && input_Data <=10)
                {
                    time_Duration = input_Data;
                    System.out.println("Продолжительность эксперимента равна : " + time_Duration);
                    System.out.println("Эксперимент \"Остров\" начинается!");
                }
                else
                {
                    System.out.println("Неправильный тип данных, попробуйте ещё раз");
                    set_Time_Duration();
                }
            }
            else if (scanner.hasNextLine())
            {
                System.out.println("Неправильный тип данных, попробуйте ещё раз");
                set_Time_Duration();
            }
        }
        return time_Duration;
    }
    static private int set_Y_Dimension() // метод, запрашивающий у пользователя размер ширины острова, возвращает переменную типа int
    {
        int input_Data;
        int dimension = 0;
        System.out.println("Введите ширину острова - целое положительное число больше от 0, не включая 0, до 20 включительно");
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNext())
        {
            if(scanner.hasNextInt())
            {
                input_Data = scanner.nextInt();
                if(input_Data >0 && input_Data <=20)
                {
                    dimension = input_Data;
                    System.out.println("Ширина острова равна: " + dimension);
                }
                else
                {
                    System.out.println("Неправильный тип данных, попробуйте ещё раз");
                    set_Y_Dimension();
                }
            }
            else if (scanner.hasNextLine())
            {
                System.out.println("Неправильный тип данных, попробуйте ещё раз");
                set_Y_Dimension();
            }
        }
        return dimension;
    }
    public static void print_Statistics(Location_Example[][] island) // метод, подсчитывающий количество зверушек в каждой локации и выводящий общую статистику на экран
    {
        int total = 0; // общее количество зверей на острове
        int total_Predators = 0; // общее количество хищников на острове
        int total_Herbivore = 0; // общее количество травоядных на острове
        for (int i = 0; i < island.length; i++)
        {
            for (int j = 0; j < island[i].length; j++)
            {
                int loc_id = island[i][j].getLocation_id();
                ArrayList<ArrayList<? extends Animal>> loc_List_of_Animal = island[i][j].getMap();
                for (int k = 0; k < loc_List_of_Animal.size(); k++)
                {
                    String animal_Type;
                    int result = 0;
                    int predators = 0;
                    int herbivore = 0;
                    animal_Type = loc_List_of_Animal.get(k).get(0).getClass().getSimpleName(); // по первому элементу списка определяем класс животного
                    System.out.println("Количество зверушек вида " + animal_Type +   " на локации # " + loc_id + " равно " + loc_List_of_Animal.get(k).size());
                    result = result + loc_List_of_Animal.get(k).size();
                    if(Predators.class.isAssignableFrom(loc_List_of_Animal.get(k).get(0).getClass())) // для подсчета количества травоядных и хищников в локации и на острове используем рефлексию
                    {
                        predators = predators + loc_List_of_Animal.get(k).size();
                        System.out.println("В локации # " + loc_id + " всего хищников: " + predators);
                    }
                    else if(Herbivore.class.isAssignableFrom(loc_List_of_Animal.get(k).get(0).getClass())) // для подсчета количества травоядных и хищников в локации и на острове используем рефлексию
                    {
                        herbivore = herbivore + loc_List_of_Animal.get(k).size();
                        System.out.println("В локации # " + loc_id + " всего травоядных: " + herbivore);
                    }
                    total_Predators = total_Predators + predators; // общее количество хищников на острове
                    total_Herbivore = total_Herbivore + herbivore; // общее количество травоядных на острове


                    total = total + result; // общее количество зверей на острове
                }
            }
        }
        System.out.println("Общее поголовье хищников на острове: " + total_Predators);
        System.out.println("Общее поголовье травоядных на острове: " + total_Herbivore);
        System.out.println("Общее поголовье скота на острове: " + total);

        System.out.println("---------------------------");
    }
}
