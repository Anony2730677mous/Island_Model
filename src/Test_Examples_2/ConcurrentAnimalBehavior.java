package Test_Examples_2;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentAnimalBehavior {
    public static void main(String[] args) {

        int x = 5;
        int y = 3;
        Location[][] island = Island.createIsland(x, y);

        ExecutorService executorService = Executors.newFixedThreadPool(x*y);

        for (int i = 0; i < x; i++)
        {
            for (int j = 0; j < y; j++)
            {

                executorService.execute(new PrintList(island[i][j]));

            }
        }
        executorService.shutdown();


    }
}
class PrintList implements Runnable
{
    Location location;
    public PrintList(Location location)
    {
        this.location = location;
    }
    @Override
    public void run() {
        System.out.println(location.getList());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}