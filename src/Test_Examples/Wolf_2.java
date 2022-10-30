package Test_Examples;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
//public class Wolf_2 // класс для тестирования отдельных методов и алгоритмов
//{
//    private static final AtomicInteger COUNTER = new AtomicInteger(0); // общий счетчик для созданных объектов класса
//    private final int id;
//    public Wolf_2() {
//
//       id = COUNTER.getAndIncrement(); // при создании объекта происходит увеличение счетчика созданных объектов
//    }
//    public int getId()
//    {return id;}
//
//    @Override
//    public String toString() {
//        return "Wolf_2{" +
//                "id = " + id +
//                '}';
//    }
//
//    public static ArrayList<Wolf_2> createNewWolfList(Supplier<Wolf_2> wolf_2Supplier, int count) // метод, создающий новый объект не больше количества, переданного в метод в качесте параметра и
//            //заносящий созданный объект в список
//{
//    ArrayList<Wolf_2> wolf_2ArrayList = new ArrayList<>();
//    for (int i = 0; i <count; i++)
//    {
//        wolf_2ArrayList.add(wolf_2Supplier.get());
//
//    }
//    return wolf_2ArrayList;
//}
//ArrayList<ArrayList<Wolf_2>> get_List_Around(int current_position_x, int current_position_y, int move_range)
//{
//    move_range = 2;
//    ArrayList<ArrayList<Wolf_2>> arrayList = new ArrayList<>();
//    ArrayList<Wolf_2> around;
//    for (int i = current_position_x - move_range; i <= current_position_x + move_range; i++)
//    {
//        for (int j = current_position_y - move_range; j <= current_position_y + move_range ; j++)
//        {
//            if((current_position_x >=0 && current_position_x <5) && (current_position_y >=0 && current_position_y < 5))
//            {
//
//            }
//        }
//    }
//    return arrayList;
//}
//}
//class Test_Wolf
//{
//      public static void main(String[] args)
//    {
//        ArrayList<Wolf_2> list = new ArrayList<>();
//        ArrayList<Wolf_2> list2 = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            list.add(i, new Wolf_2());
//        }
//        System.out.println(list);
//        System.out.println(list2);
//
//
//        for (int i = list.size()-1; i >= 0; i--)
//        {
//            if(Animals.ready_Or_Not())
//            {
//                list2.add(list.get(i));
//                list.remove(i);
//            }
//
//        }
//
//
//
//        System.out.println(list);
//        System.out.println(list2);
//
//    }
//}

class Solution {
    private static final int THREAD_POOL_SIZE = 10;
    private static final int COUNT = 20;
    private static final int TIMEOUT = 10;

    private static final Integer[] ARRAY = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final CopyOnWriteArrayList<Integer> LIST = new CopyOnWriteArrayList<>(List.of(ARRAY));

    public static void main(String[] args) throws InterruptedException {
        System.out.print("List before: ");
        printList(LIST);

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        for (int i = 1; i <= COUNT; i++) {
            final Integer element = i;
            Runnable task = () -> {
                boolean isAbsent = true;
                for (Integer integer : LIST)
                {
                    if (integer.equals(element))
                    {
                        isAbsent = false;
                        break;
                    }
                }
                if (isAbsent)
                {
                    LIST.addIfAbsent(element);
                }
            };
            executor.submit(task);
        }

        executor.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS);

        executor.shutdown();

        System.out.print("List  after: ");
        printList(LIST);
    }

    private static void printList(List<Integer> list) {
        String delimiter = "";
        for (Integer integer : list) {
            System.out.print(delimiter + integer);
            delimiter = ", ";
        }
        System.out.println();
    }
}
