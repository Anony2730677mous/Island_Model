package Test_Examples;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;



public class Animal_Factory // класс с методом для создания нового экземпляра класса, реализующего интерфейс Animal и наследующегося от классов Predators и Herbivore
{
    static final AtomicInteger COUNTER_WOLF = new AtomicInteger(0); // счетчик общего количества созданных экземпляров класса
    static final AtomicInteger COUNTER_RABBIT = new AtomicInteger(0); // счетчик общего количества созданных экземпляров класса


    public static Animal animal_Factory(String animalEnum)  // метод, создающий и возвращающий объект определенного типа
    {
        Animal animal_to_return;
        switch (animalEnum)
        {
            case "WOLF":
                animal_to_return = new Animals().new Wolf(5.0, 5.0, 0.0, 3);
                break;
            case "RABBIT":
                animal_to_return = new Animals().new Rabbit();
                break;
            default:
                throw new IllegalArgumentException("Wrong animal type:" + animalEnum);
        }
        return animal_to_return;

    }
    public static int animal_Factory_Initial_Count(String animalEnum) // метод, создающий и возвращающий количество объектов определенного типа для начальной инициализации
    {
        int animal_count_to_return;
        Random random = new Random();
        switch (animalEnum)
        {
            case "WOLF":
                animal_count_to_return = random.nextInt(1,30); // в текущей локации количество объектов типа Wolf должно быть не больше, чем 30
                break;
            case "RABBIT":

                animal_count_to_return = random.nextInt(1,150); // в текущей локации количество объектов типа Rabbit должно быть не больше, чем 150
                break;
            default:
                throw new IllegalArgumentException("Wrong animal type:" + animalEnum);
        }
        return animal_count_to_return;

    }
}
class ListCreation // класс для создания списка животных каждого вида для наполнения отдельной локации
{

    public static String[] get_Animal_Type_Class() // метод, создающий и возвращающий массив строковых переменных из названий классов
    {

        String[] array_string = new String[0]; // возвращаемый тип значения метода - массив String
        try {
            Class[] array_of_Animal_Class = Class.forName("Test_Examples.Animals").getDeclaredClasses(); //с помощью рефлексии собираем все классы класса Animal_Example в массив классов
            array_string = new String[array_of_Animal_Class.length]; // задаем размер исходящего массива равным размеру входящего массива
            for (int i = 0; i < array_of_Animal_Class.length; i++) {

                array_string[i] = array_of_Animal_Class[i].getSimpleName().toUpperCase(); // преобразуем название класса в строковую переменную и заносим ее в массив строк

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return array_string;
    }

    public static ArrayList<ArrayList<? extends Animal>> create_Animal_List(String[] s)  // метод, создающий и возвращающий список объектов определенного типа с начальным определенным количеством объектов этого типа
    {
        int initial_count;
        ArrayList<ArrayList<? extends Animal>> all_Animal_List = new ArrayList<>(); // список, куда будут заносится все списки со всеми типами животных

        for (int i = 0; i < s.length; i++) // цикл по всем типам животных
        {
            ArrayList<Animal> this_Animal_List = new ArrayList<>(); // список для занесения каждого экземпляра определенного типа животного
            initial_count = Animal_Factory.animal_Factory_Initial_Count(s[i]); // получаем количество животных для данной локации
            for (int j = 0; j < initial_count; j++) // цикл создания и занесения количества животных для данной локации в список животных
            {
                this_Animal_List.add(j, Animal_Factory.animal_Factory(s[i])); // занесение созданного животного текущего типа в список под своим номером
            }
            all_Animal_List.add(i, this_Animal_List); // занесение списка с текущим типом животного в общий список всех животных для данной локации
        }

        return  all_Animal_List;
    }

}


