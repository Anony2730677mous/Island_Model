package Test_Examples_2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Factory // создание животных и наполнение списка созданными экземплярами животных каждого вида в случайном количестве
{
    private static String plant = "Plant";
    private static String packageName = "Test_Examples_2.Animals";
    private static String predators = "Predatory_Animal";
    private static String herbivorous = "Herbivorous_Animal";
    private static String nothing = "null";
    private static String notSuchClassException = "Такого класса не найдено: ";
    public static List<LivingEntity> plantAddedList() // метод, создающий список с травой для добавления её в общий список животных каждый новый день
    {
        List<LivingEntity> plantAddedList = new ArrayList<>();
        int plantCount = Factory.animalFactoryInitialCount(plant);
        for (int i = 0; i < plantCount; i++) {
            plantAddedList.add(i, Factory.animalFactory(plant));
        }
        return plantAddedList;
    }
    public static List<LivingEntity> listOfAllAnimals() // метод, возвращающий общий список, наполненный экземплярами животных и травы
    {
        List<LivingEntity> listOfAllAnimals = new ArrayList<>();
        List<String> animalsList = Factory.getAnimalTypeClass();
        for (String animal: animalsList)
        {

            int animalСount = Factory.animalFactoryInitialCount(animal);
            for (int i = 0; i < animalСount; i++)
            {

                listOfAllAnimals.add(i, Factory.animalFactory(animal));
            }

        }

        return listOfAllAnimals;
    }

    public static List<String> getAnimalTypeClass() // метод, создающий и возвращающий лист строковых переменных из названий классов
    {

        List<String> array_string = new ArrayList<>(); // возвращаемый тип значения метода - лист String
        try {
            Class[] array_of_Animal_Class = Class.forName(packageName).getDeclaredClasses(); //с помощью рефлексии собираем все классы класса Animal_Example в массив классов

            for (int i = 0; i < array_of_Animal_Class.length; i++) {
                if(!(array_of_Animal_Class[i].getSimpleName().equals(predators)))
                if(!(array_of_Animal_Class[i].getSimpleName().equals(herbivorous)))
                if(!(array_of_Animal_Class[i].getSimpleName().equals(nothing)))
                {

                    array_string.add( array_of_Animal_Class[i].getSimpleName()); // преобразуем название класса в строковую переменную и заносим ее в лист строк
                }

            }
        } catch (ClassNotFoundException e)
        {
            System.out.println(notSuchClassException + e.getCause());
        }
        return array_string;
    }

    private static int animalFactoryInitialCount(String animalClass) // метод, создающий и возвращающий количество объектов определенного типа для начальной инициализации
    {
        int animal_Max_Count = AnimalInfo.getMaxAnimalCount(animalClass); // для первоначального наполнения острова вызывается метод, возввращающий максимальное число эивотных данного вида
        int animal_count_to_return;
        Random random = new Random();
        animal_count_to_return = random.nextInt(1, animal_Max_Count);
        return animal_count_to_return;

    }

    public static LivingEntity animalFactory(String animalClass)  // метод, создающий и возвращающий объект определенного типа
    {

        LivingEntity animals;
        switch (animalClass)
        {
            case "Boa":
                animals =  new Animals().new Boa();
                break;
            case "Bear":
                animals = new Animals().new Bear();
                break;
            case "Goat":
                animals = new Animals().new Goat();
                break;
            case "Sheep":
                animals = new Animals().new Sheep();
                break;
            case "Eagle":
                animals = new Animals().new Eagle();
                break;
            case "Fox":
                animals = new Animals().new Fox();
                break;
            case "Wolf":
                animals = new Animals().new Wolf();
                break;
            case "Rabbit":
                animals = new Animals().new Rabbit();
                break;
            case "Mouse":
                animals = new Animals().new Mouse();
                break;
            case "Horse":
                animals = new Animals().new Horse();
                break;
            case "Buffalo":
                animals = new Animals().new Buffalo();
                break;
            case "Hog":
                animals = new Animals().new Hog();
                break;
            case "Caterpillar":
                animals = new Animals().new Caterpillar();
                break;
            case "Deer":
                animals = new Animals().new Deer();
                break;
            case "Duck":
                animals = new Animals().new Duck();
                break;
            case "Plant":
                Animals newPlant = new Animals();
                Animals.Plant plant = newPlant.new Plant();
                animals = plant;
                break;
            default:
                throw new IllegalStateException(notSuchClassException + animalClass);
        }
return animals;
    }
}
