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

    CountDownLatch count;
    static String plant = "Plant";
    static String caterpillar = "Caterpillar";

    public void allMethods()
    {
        resetAnimalState();
        animalConcurrentBehavior();
        printStatistics();
        count.countDown();
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

                firstLock = true;
                secondLock = true;
                lock.notifyAll();
            } catch (InterruptedException e) {
                System.out.println(e.getCause());
            }
        }
    }



    class StateReset implements Runnable
    {
        Location[][] island;

        int x;
        int y;

        @Override
        public void run() {
            System.out.println("StateReset");
        }
    }

    class AnimalLife implements Runnable {
        Location[][] island;

        int x;
        int y;

        @Override
        public void run() {
            System.out.println("AnimalLife");
        }
    }

    class AnimalStateReset implements Runnable {
        List<LivingEntity> listCurrentLocation;
        Location[][] island;
        @Override
        public void run()
        {
            System.out.println("AnimalStateReset");
            for (int i = 0; i < island.length; i++)
            {
                for (int j = 0; j < island[i].length; j++)
                {
                    listCurrentLocation = island[i][j].getList();
                    AnimalBehavior.resetMultiPlayState(listCurrentLocation);
                    AnimalBehavior.resetMoveState(listCurrentLocation);
                }
            }
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
            AnimalBehavior.setPlant(listCurrentLocation);
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
class TestCAB
{
    public static void main(String[] args)
    {
        int x = 3;
        int y = 3;
        int time = 4;
        Location[][] island = Island.createIsland(x, y);

        ConcurrentAnimalBehavior cab = new ConcurrentAnimalBehavior();
        cab.count = new CountDownLatch(time);
        cab.firstLock = true;
        cab.secondLock = true;
        cab.lock = new ReentrantLock();
        for (int i = 0; i < time; i++)
        {

            cab.allMethods();
        }

    }
}