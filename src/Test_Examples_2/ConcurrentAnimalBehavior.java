package Test_Examples_2;


import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentAnimalBehavior
{

    public volatile boolean firstLock;
    public volatile boolean secondLock;


    Lock lock;
    static CountDownLatch count;
    static CountDownLatch countFirst;
    static CountDownLatch countSecond;
//    static String plant = "Plant";
//    static String caterpillar = "Caterpillar";
//    private Location[][] island;

    public void allMethods(Location[][] island, int day, Lock lock)
    {

        count = new CountDownLatch(3);
        countFirst = countSecond = count;
        count.countDown();
        ExecutorService resetExecutors = Executors.newFixedThreadPool((island.length) * (island[0].length));
        ExecutorService behaviorExecutors = Executors.newFixedThreadPool((island.length) * (island[0].length));
        for (int i = 0; i < day; i++)
        {

                System.out.println("На острове начался " + (i+1) + " день.");

                resetAnimalState(island, resetExecutors);

                animalConcurrentBehavior(island, behaviorExecutors);

                printStatistics(island, lock);

                System.out.println("На острове закончился " + (i + 1) + " день.");
                System.out.println();

        }
        resetExecutors.shutdown();
        behaviorExecutors.shutdown();

    }

    public  void resetAnimalState(Location[][] island, ExecutorService resetExecutors) // сброс состояний isMove и isMultiplay в локациях
    {


        System.out.println("Метод resetAnimalState");

                for (int i = 0; i < island.length; i++)
                {
                    for (int j = 0; j < island[i].length; j++)
                    {
                        List<LivingEntity> list = island[i][j].getList();

                        resetExecutors.submit(new AnimalStateReset(list));

                    }
                }

    }

    public  void animalConcurrentBehavior(Location[][] island, ExecutorService behaviorExecutors)
    {


        for (int i = 0; i < island.length; i++)
                {
                    for (int j = 0; j < island[i].length; j++)
                    {
                        List<LivingEntity> list = island[i][j].getList();

                        behaviorExecutors.submit(new AnimalConcurrentBehavior(list, island, i, j));

                    }
                }

    }

    public  void printStatistics(Location[][] island, Lock lock)
    {


        System.out.println("Метод printStatistics ");
                AnimalStatistics as = new AnimalStatistics(island, lock);
                as.runStats();

    }




    class AnimalStateReset implements Runnable
    {
        List<LivingEntity> listCurrentLocation;

        public AnimalStateReset(List<LivingEntity> listCurrentLocation)
        {
            this.listCurrentLocation = listCurrentLocation;
        }

        @Override
        public void run()
        {
            AnimalBehavior.resetMultiPlayState(listCurrentLocation);
            AnimalBehavior.resetMoveState(listCurrentLocation);

        }
    }

    class AnimalConcurrentBehavior implements Runnable
    {
        List<LivingEntity> listCurrentLocation;

        Location[][] island;

        int i;
        int j;

        public AnimalConcurrentBehavior(List<LivingEntity> listCurrentLocation, Location[][] island, int i, int j)
        {
            this.listCurrentLocation = listCurrentLocation;
            this.island = island;
            this.i = i;
            this.j = j;

        }

        @Override
        public void run() {

            List<LivingEntity> copyList = new CopyOnWriteArrayList<>();
            List<LivingEntity> newBorn = new CopyOnWriteArrayList<>();


                /*
                Проверка на голодную смерть
                 */
            //System.out.println("Проверка на голодную смерть");
            AnimalBehavior.hungryDeath(listCurrentLocation, copyList);
            AnimalBehavior.removeFromList(listCurrentLocation);
            copyList.clear();
                /*
                Питание животных
                 */
            //System.out.println("Животные кушают");
            AnimalBehavior.eatAnimalBehavior(listCurrentLocation);

            AnimalBehavior.removeFromList(listCurrentLocation);
            copyList.clear();
                /*
                Размножение животных
                 */
            //System.out.println("Животные размножаются");
            AnimalBehavior.multiPlayAnimalBehavior(listCurrentLocation, newBorn);
            listCurrentLocation.addAll(newBorn);
                /*
                Передвижение животных
                 */
            //System.out.println("Животные передвигаются");
            AnimalBehavior.moveAnimalBehavior(listCurrentLocation, island, i, j);

                /*
                Добавление травы на следующий ход
                 */
            //System.out.println("Добавление травы на локацию на следующий ход");
            AnimalBehavior.setPlant(listCurrentLocation);
            Collections.sort(listCurrentLocation, new AnimalNameComparator().thenComparing(new AnimalCountComparator()));

            System.out.println("Локация с координатами x= " + i + " и y= " + j + " обработана");
        }


    }

    static class AnimalStatistics
    {
        List<LivingEntity> listCurrentLocation;
        Location[][] island;
        private Lock lock;

        public AnimalStatistics(Location[][] island, Lock lock)
        {

            this.island = island;
            this.lock = lock;
        }


        public void runStats()
        {
            try {

                lock.lock();
                int total = 0; // общее количество зверей на острове
                int totalPredators = 0; // общее количество хищников
                int totalHerbivore = 0; // общее количество травоядных
                int totalPlant = 0;

                for (int i = 0; i < island.length; i++) {
                    for (int j = 0; j < island[i].length; j++) {
                        listCurrentLocation = island[i][j].getList();
                        int result;
                        int predators = 0;
                        int herbivore = 0;
                        int plant = 0;
                        for (int k = 0; k < listCurrentLocation.size(); k++) {

                            if (Animals.Herbivorous_Animal.class.isAssignableFrom(listCurrentLocation.get(k).getClass())) {
                                herbivore = herbivore + 1;
                            } else if (Animals.Predatory_Animal.class.isAssignableFrom(listCurrentLocation.get(k).getClass())) {
                                predators = predators + 1;
                            } else if (Animals.Plant.class.isAssignableFrom(listCurrentLocation.get(k).getClass())) {
                                plant = plant + 1;
                            }
                        }
                        totalHerbivore = totalHerbivore + herbivore;
                        totalPredators = totalPredators + predators;
                        totalPlant = totalPlant + plant;
                        result = herbivore + predators;
                        total = total + result;
                    }
                }
                System.out.println("Общее количество скота на острове = " + total + " , из них количество хищников = " + totalPredators + " , количество травоядных = " + totalHerbivore + " , количество травы = " + totalPlant);
            } catch (Exception e) {
                System.out.println(e.getCause());
            } finally {
                lock.unlock();
            }
        }

    }


}
class TestCAB
{
    public static void main(String[] args)
    {
        int x = 3;
        int y = 3;
       final int time = 4;
        Location[][] island = Island.createIsland(x, y);

        ConcurrentAnimalBehavior cab = new ConcurrentAnimalBehavior();
        cab.count = new CountDownLatch(time);
        cab.firstLock = true;
        cab.secondLock = true;
        cab.lock = new ReentrantLock();
        ConcurrentAnimalBehavior.AnimalStatistics cabas = new ConcurrentAnimalBehavior.AnimalStatistics(island, cab.lock);
        cabas.runStats();

        cab.allMethods(island, time, cab.lock);

        cabas.runStats();

    }
    }
