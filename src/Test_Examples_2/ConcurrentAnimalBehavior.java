package Test_Examples_2;


import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentAnimalBehavior
{

    public volatile boolean firstLock = true;
    public volatile boolean secondLock = true;
    List<LivingEntity> listCurrentLocation;
    final Lock lock = new ReentrantLock();
    int x = 3;
    int y = 3;
    Location[][] island = Island.createIsland(x, y);
    final int day = 5;
    CountDownLatch count = new CountDownLatch(day);
    ExecutorService executorService3 = Executors.newFixedThreadPool(x * y);


    static String plant = "Plant";
    static String caterpillar = "Caterpillar";

    public void allMethods()
    {
        ExecutorService executorServiceStateReset;
        for (int k = 0; k < day; k++)
        {
            resetAnimalState();
            animalConcurrentBehavior();
            printStatistics();
            System.out.println("на острове закончился " + (k + 1) + " день");
            count.countDown();

        }
    }

    public void resetAnimalState()
    {
        synchronized (lock)
        {
            try {
                while (!firstLock && !secondLock)
                {
                    lock.wait();
                }
                System.out.println("Метод resetAnimalState");

                firstLock = false;
                lock.notifyAll();

            } catch (InterruptedException e) {
                System.out.println(e.getCause());
            }
        }
    }

    public void animalConcurrentBehavior() {
        synchronized (lock) {
            try {

                while (firstLock) {
                    lock.wait();
                }
                System.out.println("Метод animalConcurrentBehavior");

                secondLock = false;
                lock.notifyAll();

            } catch (InterruptedException e) {
                System.out.println(e.getCause());
            }
        }
    }

    public void printStatistics()
    {
        synchronized (lock)
        {
            try {
                while (secondLock)
                {
                    lock.wait();
                }
                System.out.println("Метод printStatistics");

                     AnimalStatistics as = new AnimalStatistics(island, lock);
                     as.runStats();

                firstLock = true;
                secondLock = true;
                lock.notifyAll();
            } catch (InterruptedException e) {
                System.out.println(e.getCause());
            }
        }
    }

    public static void main(String[] args) {

//        int x = 3;
//        int y = 3;
//        int d = 5;
//
//        Location[][] island = Island.createIsland(x, y);


        ConcurrentAnimalBehavior cab = new ConcurrentAnimalBehavior();
//        cab.firstLock = true;
//        cab.secondLock = true;
//        cab.lock = new ReentrantLock();
//        cab.count = count1;
//        ExecutorService executorService1 = Executors.newFixedThreadPool(x * y);
//        ExecutorService executorService2 = Executors.newFixedThreadPool(x * y);
//        ExecutorService executorService3 = Executors.newFixedThreadPool(1);


        cab.allMethods();





        System.out.println("Работа цикла закончена");



    }

    class StateReset implements Runnable
    {
        Location[][] island;

        int x;
        int y;
        ExecutorService executorServiceStateReset;
        Lock lock;

        public StateReset(Location[][] island, int x, int y, ExecutorService executorServiceStateReset, Lock lock)
        {
            this.island = island;
            this.x = x;
            this.y = y;
            this.executorServiceStateReset = executorServiceStateReset;
            this.lock = lock;
        }

        @Override
        public void run()
        {
            try {
                lock.lock();
                for (int j = 0; j < x; j++)
                {
                    for (int k = 0; k < y; k++)
                    {
                        List<LivingEntity> list = island[j][k].getList();
                        executorServiceStateReset.execute(new AnimalStateReset(list));

                    }

                }
                executorServiceStateReset.shutdown();
            } catch (Exception e) {
                System.out.println(e.getCause());
            } finally {
                lock.unlock();
            }


        }
    }

    class AnimalLife implements Runnable {
        Location[][] island;

        int x;
        int y;
        ExecutorService executorServiceAnimalLife;
        Lock lock;

        public AnimalLife(Location[][] island, int x, int y, ExecutorService executorServiceAnimalLife, Lock lock) {
            this.island = island;
            this.x = x;
            this.y = y;
            this.executorServiceAnimalLife = executorServiceAnimalLife;
            this.lock = lock;
        }

        @Override
        public void run() {
            try {

                lock.lock();
                for (int j = 0; j < x; j++) {
                    for (int k = 0; k < y; k++) {
                        List<LivingEntity> list = island[j][k].getList();
                        executorServiceAnimalLife.execute(new AnimalConcurrentBehavior(list, island, j, k));


                    }

                }
                executorServiceAnimalLife.shutdown();
            } catch (Exception e) {
                System.out.println(e.getCause());
            } finally {
                lock.unlock();
            }


        }
    }

    class AnimalStateReset implements Runnable {
        List<LivingEntity> listCurrentLocation;


        public AnimalStateReset(List<LivingEntity> listCurrentLocation) {
            this.listCurrentLocation = listCurrentLocation;

        }

        @Override
        public void run() {

            AnimalBehavior.resetMultiPlayState(listCurrentLocation);
            AnimalBehavior.resetMoveState(listCurrentLocation);


        }
    }

    class AnimalConcurrentBehavior implements Runnable {
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
        public void run() {

            List<LivingEntity> copyList = new CopyOnWriteArrayList<>();
            List<LivingEntity> newBorn = new CopyOnWriteArrayList<>();
            List<LivingEntity> plantAdded;
            //System.out.println("Список животных в начале дня " + listCurrentLocation);

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
            AnimalBehavior.multiPlayAnimalBehavior(listCurrentLocation, newBorn);
            listCurrentLocation.addAll(newBorn);
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
            if ((addedPlantCount + plantOnCurrentLocation) <= maxPlantOnLocation) // условие добавления на текущую локацию травы
            {

                listCurrentLocation.addAll(plantAdded);
            } else {
                for (int k = 0; k < (maxPlantOnLocation - plantOnCurrentLocation); k++) {
                    listCurrentLocation.add(plantAdded.get(k));
                }
            }
            Collections.sort(listCurrentLocation, new AnimalNameComparator().thenComparing(new AnimalCountComparator()));
            //System.out.println("Список животных в конце дня " + listCurrentLocation);
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