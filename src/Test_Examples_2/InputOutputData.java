package Test_Examples_2;

import java.util.Scanner;

public class InputOutputData
{
    static String setDimensions = "Для проведения эксперимента \"Модель острова животных\", пожалуйста, задайте размеры острова";
    static String wrongDataType = "Неправильный тип данных, попробуйте ещё раз";
    static String setIslandLength = "Введите длину острова - целое положительное число больше 0, не включая 0, до 100 включительно";
    static String setIslandWidth = "Введите ширину острова - целое положительное число больше от 0, не включая 0, до 20 включительно";
    static String setTimeDuration = "Введите продолжительность действия эксперимента - целое положительное число больше 0, не включая 0, до 10 включительно";
    static String lengthIs = "Длина острова равна: ";
    static String widthIs = "Ширина острова равна: ";
    static String durationIs = "Продолжительность эксперимента равна : ";
    static String isBeginning = "Эксперимент \"Остров\" начинается!";
    static int zero = 0;
    static int hundred = 100;
    static int twenty = 20;
    static int ten = 10;
    public static int[] printIntro() // метод, предлагающий задать пользователю размеры острова в заданных пределах и возвращающий массив целочисленных значений
    {
        int[] array_dimension;
        System.out.println(setDimensions);
        int xDimension = setXDimension();
        int yDimension = setYDimension();
        System.out.println("Создан остров размерами: " + xDimension + " на " + yDimension + " локаций");
        return array_dimension = new int[]{xDimension, yDimension};
    }
    static private int setXDimension() // метод, запрашивающий у пользователя размер длины острова, возвращает переменную типа int
    {
        int input_Data;
        int dimension = zero;
        System.out.println(setIslandLength);
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNext())
        {
            if(scanner.hasNextInt())
            {
                input_Data = scanner.nextInt();
                if(input_Data > zero && input_Data <= hundred)
                {
                    dimension = input_Data;
                    System.out.println(lengthIs + dimension);
                }
                else
                {
                    System.out.println(wrongDataType);
                    setXDimension();
                }
            }
            else if (scanner.hasNextLine())
            {
                System.out.println(wrongDataType);
                setXDimension();
            }
        }
        return dimension;
    }
    public static int setTimeDuration() // метод, запрашивающий у пользователя продолжительность жизни на острове, возвращает переменную типа int
    {
        int inputData;
        int timeDuration = zero;
        System.out.println(setTimeDuration);
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNext())
        {
            if(scanner.hasNextInt())
            {
                inputData = scanner.nextInt();
                if(inputData > zero && inputData <= ten)
                {
                    timeDuration = inputData;
                    System.out.println(durationIs + timeDuration);
                    System.out.println(isBeginning);
                }
                else
                {
                    System.out.println(wrongDataType);
                    setTimeDuration();
                }
            }
            else if (scanner.hasNextLine())
            {
                System.out.println(wrongDataType);
                setTimeDuration();
            }
        }
        return timeDuration;
    }
    static private int setYDimension() // метод, запрашивающий у пользователя размер ширины острова, возвращает переменную типа int
    {
        int inputData;
        int dimension = zero;
        System.out.println(setIslandWidth);
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNext())
        {
            if(scanner.hasNextInt())
            {
                inputData = scanner.nextInt();
                if(inputData >zero && inputData <= twenty)
                {
                    dimension = inputData;
                    System.out.println(widthIs + dimension);
                }
                else
                {
                    System.out.println(wrongDataType);
                    setYDimension();
                }
            }
            else if (scanner.hasNextLine())
            {
                System.out.println(wrongDataType);
                setYDimension();
            }
        }
        return dimension;
    }
}
