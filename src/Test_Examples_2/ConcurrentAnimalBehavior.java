package Test_Examples_2;


import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentAnimalBehavior {
    public static void main(String[] args) {

        int x = 5;
        int y = 3;
        int d = 5;
        Location[][] island = Island.createIsland(x, y);

        //ExecutorService executorService1 = Executors.newFixedThreadPool(x*y);
        ExecutorService executorService2 = Executors.newFixedThreadPool(x*y);

        for (int i = 0; i < d; i++)
        {
            System.out.println();
            System.out.println("Начинается день № " + i + " на острове");
            System.out.println("Сброс передвижений в начале дня ");
            System.out.println();
            for (int j = 0; j < x; j++)
            {
                for (int k = 0; k < y; k++)
                {

                    List<LivingEntity> list = island[j][k].getList();
                    AnimalBehavior.resetMoveState(list);
                }

            }
            for (int j = 0; j < x; j++)
            {
                for (int k = 0; k < y; k++)
                {
                    List<LivingEntity> list = island[j][k].getList();

                    executorService2.execute(new AnimalConcurrentBehavior(list, island, j, k));
                }

            }
            System.out.println();
            System.out.println("на острове завершился день № " + i);
            System.out.println();

        }
        executorService2.shutdown();
        //executorService1.shutdown();
        System.out.println("Симуляция жизни на острове завершена");

    }
}

class MoveReset implements Runnable
{
    List<LivingEntity> listCurrentLocation;

    public MoveReset(List<LivingEntity> listCurrentLocation) {
        this.listCurrentLocation = listCurrentLocation;
    }

    @Override
    public void run()
    {
        AnimalBehavior.resetMoveState(listCurrentLocation);
    }
}
class AnimalConcurrentBehavior implements Runnable
{
    List<LivingEntity> listCurrentLocation;

    Location[][] island;
    int i;
    int j;
    static String plant = "Plant";
    static String caterpillar = "Caterpillar";

    public AnimalConcurrentBehavior(List<LivingEntity> listCurrentLocation, Location[][] island, int i, int j) {
        this.listCurrentLocation = listCurrentLocation;
        this.island = island;
        this.i = i;
        this.j = j;
    }

    @Override
    public void run()
    {

        List<LivingEntity> copyList = new CopyOnWriteArrayList<>();
        List<LivingEntity> newBorn = new CopyOnWriteArrayList<>();
        List<LivingEntity> plantAdded;
        System.out.println("Список животных в начале дня " + listCurrentLocation);

                /*
                Проверка на голодную смерть
                 */
        System.out.println("Проверка на голодную смерть");
        AnimalBehavior.hungryDeath(listCurrentLocation, copyList);
        AnimalBehavior.removeFromList(listCurrentLocation);
        copyList.clear();
                /*
                Питание животных
                 */
        System.out.println("Животные кушают");
        AnimalBehavior.eatAnimalBehavior(listCurrentLocation);

        AnimalBehavior.removeFromList(listCurrentLocation);
        copyList.clear();
                /*
                Размножение животных
                 */
        System.out.println("Животные размножаются");
        AnimalBehavior.multiplayAnimalBehavior(listCurrentLocation, newBorn);
                /*
                Передвижение животных
                 */
        System.out.println("Животные передвигаются");
        AnimalBehavior.moveAnimalBehavior(listCurrentLocation, island, i, j);

                /*
                Добавление травы на следующий ход
                 */
        System.out.println("Добавление травы на локацию на следующий ход");
        plantAdded = Factory.plantAddedList();
        int addedPlantCount = plantAdded.size();
        int plantOnCurrentLocation = AnimalBehavior.countOfAliveVictims(listCurrentLocation, plant);
        int maxPlantOnLocation = AnimalInfo.getMaxAnimalCount(plant);
        if((addedPlantCount + plantOnCurrentLocation) <= maxPlantOnLocation) // условие добавления на текущую локацию травы
        {

            listCurrentLocation.addAll(plantAdded);
        }
        else {
            for (int k = 0; k < (maxPlantOnLocation - plantOnCurrentLocation); k++)
            {
                listCurrentLocation.add(plantAdded.get(k));
            }
        }
        Collections.sort(listCurrentLocation,new AnimalNameComparator().thenComparing(new AnimalCountComparator()));
        System.out.println("Список животных в конце дня " + listCurrentLocation);
        System.out.println("Локация с координатами x= " + i + " и y= " + j + " обработана");
    }
}