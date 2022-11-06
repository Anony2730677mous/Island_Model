package Test_Examples_2;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;


public class Island // создание острова
{

   public static Location[][] createIsland(int x, int y) // метод, возвращающий остров в виде массива листов, заполненных травой и животными
    {
        Location[][] island = new Location[x][y];
        for (int i = 0; i < x; i++)
        {
            for (int j = 0; j < y; j++)
            {
                island[i][j] = new Location(i, j);
            }

        }
        return island;
    }
    public static List<Location> listOfLocationAround(Location[][] island, Location location, int range)// метод, возвращающий список локаций, куда может переместится животное
    {
    int currentX = location.getDimensionX(); // получаем координату x текущей локации, переданной в качестве параметра
    int currentY = location.getDimensionY(); // получаем координату y текущей локации, переданной в качестве параметра
    Location around; // временная переменная для занесения в список локаций
    List<Location> listToReturn = new CopyOnWriteArrayList<>(); // создаем список локаций, окружающих текущую локацию
    for (int i = currentX - range; i <= currentX + range; i++) // цикл по координатам х с учетом максимального расстояния передвижения животного
    {
        for (int j = currentY - range; j <= currentY + range ; j++) // цикл по координатам y с учетом максимального расстояния передвижения животного
        {
            if((i >= 0 && i < island.length) && (j >=0 && j < island[0].length)) // если координаты циклов входят в диапазон координат острова
            {
                around = island[i][j]; // временной локации присваиваем локацию из массива локаций(острова), переданного в параметры метода
                if(!around.equals(location)) // чтобы исключить текущую локацию, сравниваем её с окружающими
                {
                    listToReturn.add(around); // после сравнения добавляем в список локаций, окружающих текущую локацию
                }
            }

        }
    }
    return listToReturn;
    }
    public static List<LivingEntity> chooseOfLocationToRelocate(List<Location> locationList, String animalType) // метод, возвращающий список, куда переместится животное
    {
    Random random = new Random(); // случайный выбор для перемещения животного
    int index = random.nextInt(0, locationList.size()); // получаем индекс списка для выбира, куда переместится животное
    List<LivingEntity> list = locationList.get(index).getList(); // по индексу получаем нужный список
    List<LivingEntity> listToRelocate = null; // тип возвращаемого значения - список
    int maxCountOfThisTypeAnimal = AnimalInfo.getMaxAnimalCount(animalType); // получаем макс. количество животных для списка
    int currentCountOfThisTypeAnimal = AnimalBehavior.returnCountOfAnimal(AnimalBehavior.countOfAnimals(list), animalType); // получаем текущее количество животных
    if(currentCountOfThisTypeAnimal < maxCountOfThisTypeAnimal) // если локация не переполнена
    {
        listToRelocate = list; // получаем список для релокации животного
    }
    if(list == null)
    {
        System.out.println("Список для перемещения отсутствует");
        throw new NullPointerException();

    }
    else
    return listToRelocate; // метод возвращает список для релокации
    }

}
class Location // создание отдельной локации для организации передвижения животных
{
    Location location;
    int dimensionX;
    int dimensionY;
    List<LivingEntity> list;
    Location(int x, int y)
    {
        this.dimensionX = x;
        this.dimensionY = y;
        list = Factory.listOfAllAnimals();
    }

    public Location getLocation() {
        return location;
    }

    public int getDimensionX() {
        return dimensionX;
    }

    public int getDimensionY() {
        return dimensionY;
    }

    public List<LivingEntity> getList() {
        return list;
    }
}
