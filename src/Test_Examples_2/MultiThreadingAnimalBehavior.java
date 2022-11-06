package Test_Examples_2;

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

        for (int k = 0; k < day; k++)
        {
            firstLock = true;
            secondLock = true;
            resetAnimalState1();
            animalConcurrentBehavior1();
            printStatistics1(island);
            System.out.println("на острове закончился " + (k + 1) + " день");


        }
    }
    public void resetAnimalState1()
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
                e.getCause();
            }
        }
    }

    public void animalConcurrentBehavior1()
    {
        synchronized (lock) {
            try {

                while (firstLock) {
                    lock.wait();
                }
                System.out.println("Метод animalConcurrentBehavior");

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
                System.out.println("Метод printStatistics");

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
