package Test_Examples_2;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultiThreadingAnimalBehavior
{

    int day;

    private volatile boolean firstLock;
    private volatile boolean secondLock;
    final Lock lock = new ReentrantLock();
    CountDownLatch count = new CountDownLatch(3);

    public void runAllMethods(int day, Location[][] island)
    {
        ExecutorService resetExecutors = Executors.newFixedThreadPool((island.length) * (island[0].length));
        ExecutorService behaviorExecutors = Executors.newFixedThreadPool((island.length) * (island[0].length));

        for (int k = 0; k < day; k++)
        {
            firstLock = true;
            secondLock = true;
            resetAnimalState1(island);
            animalConcurrentBehavior1(island);
            printStatistics1(island);
            System.out.println("на острове закончился " + (k + 1) + " день");


        }
        resetExecutors.shutdown();
        behaviorExecutors.shutdown();
    }
    public void resetAnimalState1(Location[][] island)
    {
        synchronized (lock)
        {
            try {
                while (!firstLock && !secondLock)
                {
                    lock.wait();
                }

                System.out.println("1");
                Thread thread = new Thread(new AnimalStateReset(island));
                thread.start();
                thread.join();
                firstLock = false;
                lock.notifyAll();

            } catch (InterruptedException e) {
                e.getCause();
            }
        }
    }
class AnimalStateReset implements Runnable
{
    Location[][] island;

    public AnimalStateReset(Location[][] island) {
        this.island = island;
    }

    @Override
    public void run(){

        for (int i = 0; i < island.length; i++)
        {
            for (int j = 0; j < island[i].length; j++)
            {
                List<LivingEntity> list = island[i][j].getList();

                AnimalBehavior.resetMultiPlayState(list);
                AnimalBehavior.resetMoveState(list);

            }
        }

    }
}
    class AnimalBehaviorMT implements Runnable
    {
        List<LivingEntity> listCurrentLocation;

        Location[][] island;

        int i;
        int j;

        public AnimalBehaviorMT(List<LivingEntity> listCurrentLocation, Location[][] island, int i, int j) {
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
    public void animalConcurrentBehavior1(Location[][] island)
    {
        synchronized (lock) {
            try {

                while (firstLock) {
                    lock.wait();
                }
                for (int i = 0; i < island.length; i++)
                {
                    for (int j = 0; j < island[0].length; j++) {

                        System.out.println("2");
                        List<LivingEntity> listCurrentLocation = island[i][j].getList();
                        Thread thread = new Thread(new AnimalBehaviorMT(listCurrentLocation, island, i, j));
                        thread.start();
                        //thread.join();
                    }
                }
                secondLock = false;
                lock.notifyAll();

            } catch (InterruptedException e) {
                e.getCause();
            }
        }
    }

    public void printStatistics1(Location[][] island)
    {
        synchronized (lock)
        {
            try {
                while (secondLock)
                {
                    lock.wait();
                }
                System.out.println("3");

                ConcurrentAnimalBehavior.AnimalStatistics as = new ConcurrentAnimalBehavior.AnimalStatistics(island, lock);
                as.runStats();

                firstLock = true;
                secondLock = true;
                lock.notifyAll();
            } catch (InterruptedException e) {
                e.getCause();
            }
        }
    }

}
class TestMT
{
    public static void main(String[] args) {
        Location[][] island = Island.createIsland(3, 3);
        int time = 4;
        MultiThreadingAnimalBehavior mtab = new MultiThreadingAnimalBehavior();
        mtab.runAllMethods(time, island);
    }
}
