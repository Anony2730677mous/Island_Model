package Test_Examples_2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class AnimalInfo // содержит различную информацию о животных разного вида
    {
        /*
        счетчик общего количества созданных экземпляров класса
         */
        static final AtomicInteger COUNTER_CATERPILLAR = new AtomicInteger(0);
        static final AtomicInteger COUNTER_RABBIT = new AtomicInteger(0);
        static final AtomicInteger COUNTER_PLANT = new AtomicInteger(0);
        static final AtomicInteger COUNTER_WOLF = new AtomicInteger(0);
        static final AtomicInteger COUNTER_BEAR = new AtomicInteger(0);
        static final AtomicInteger COUNTER_FOX = new AtomicInteger(0);
        static final AtomicInteger COUNTER_EAGLE = new AtomicInteger(0);
        static final AtomicInteger COUNTER_DEER = new AtomicInteger(0);
        static final AtomicInteger COUNTER_HOG = new AtomicInteger(0);
        static final AtomicInteger COUNTER_MOUSE = new AtomicInteger(0);
        static final AtomicInteger COUNTER_HORSE = new AtomicInteger(0);
        static final AtomicInteger COUNTER_BUFFALO = new AtomicInteger(0);
        static final AtomicInteger COUNTER_SHEEP = new AtomicInteger(0);
        static final AtomicInteger COUNTER_GOAT = new AtomicInteger(0);
        static final AtomicInteger COUNTER_DUCK = new AtomicInteger(0);
        static final AtomicInteger COUNTER_BOA = new AtomicInteger(0);


        /*
        Коллекции и методы для начальной иницализации и внутриигрового процесса
         */
        static Map<String, Integer> maxCountInfo = new ConcurrentHashMap<>();// коллекция с максимальными значениями животных в ячейке для каждого вида
        private final Map<String, Map<String, Integer>> probabilityTable = new ConcurrentHashMap<>(); // коллекция с таблицей вероятностей поедания одним животным другого
        private final static Map<String, Integer> probabilityBorn = new ConcurrentHashMap<>(); // коллекция с таблицей вероятностей рождения нового животного
        private static final Map<String, Integer> moveRange = new ConcurrentHashMap<>(); // коллекция с таблицей дальности передвижения животного каждого вида

        private static Map<String, Integer> createMoveRangeTable() // метод создает коллекцию, содержащую информацию о дальности передвижения животного каждого вида
        {
            moveRange.put("Boa", 1);
            moveRange.put("Horse", 4);
            moveRange.put("Deer", 4);
            moveRange.put("Rabbit", 2);
            moveRange.put("Mouse", 1);
            moveRange.put("Goat", 3);
            moveRange.put("Wolf", 3);
            moveRange.put("Eagle", 3);
            moveRange.put("Fox", 2);
            moveRange.put("Bear", 2);
            moveRange.put("Sheep", 3);
            moveRange.put("Hog", 2);
            moveRange.put("Buffalo", 3);
            moveRange.put("Duck", 4);
            return moveRange;
        }
        private static Map<String, Integer> createProbabilityBornTable() // метод создает коллекцию, содержащую информацию о вероятности рождения нового животного каждого вида
        {
            probabilityBorn.put("Boa", 40);
            probabilityBorn.put("Horse", 40);
            probabilityBorn.put("Deer", 40);
            probabilityBorn.put("Rabbit", 70);
            probabilityBorn.put("Mouse", 80);
            probabilityBorn.put("Goat", 60);
            probabilityBorn.put("Plant", 100);
            probabilityBorn.put("Wolf", 60);
            probabilityBorn.put("Eagle", 70);
            probabilityBorn.put("Fox", 60);
            probabilityBorn.put("Caterpillar", 90);
            probabilityBorn.put("Bear", 30);
            probabilityBorn.put("Sheep", 60);
            probabilityBorn.put("Hog", 50);
            probabilityBorn.put("Buffalo", 30);
            probabilityBorn.put("Duck", 80);
            return probabilityBorn;
        }

        public static int getMoveRangeValue(String typeOfAnimal) // метод возвращает дальность передвижения животного для данного вида
        {
            int moveRange = 0;
            for(String s: createMoveRangeTable().keySet())
            {
                if(s.equals(typeOfAnimal))
                {
                    moveRange = createMoveRangeTable().get(s);
                }
            }
            return moveRange;
        }

        public static int getProbabilityBornValue(String typeOfAnimal) // метод возвращает вероятность рождения животного для данного вида
        {
            int count = 0;
            for(String s: createProbabilityBornTable().keySet())
            {
                if(s.equals(typeOfAnimal))
                {
                    count = createProbabilityBornTable().get(s);
                }
            }
            return count;
        }

        private static Map<String, Integer> createMaxCountTable() // метод создает коллекцию, содержащую информацию о максимальном количестве экземпляров каждого животного
        {

            maxCountInfo.put("Boa", 30);
            maxCountInfo.put("Horse", 20);
            maxCountInfo.put("Deer", 20);
            maxCountInfo.put("Rabbit", 150);
            maxCountInfo.put("Mouse", 500);
            maxCountInfo.put("Goat", 140);
            maxCountInfo.put("Plant", 200);
            maxCountInfo.put("Wolf", 30);
            maxCountInfo.put("Eagle", 20);
            maxCountInfo.put("Fox", 30);
            maxCountInfo.put("Caterpillar", 1000);
            maxCountInfo.put("Bear", 5);
            maxCountInfo.put("Sheep", 140);
            maxCountInfo.put("Hog", 50);
            maxCountInfo.put("Buffalo", 10);
            maxCountInfo.put("Duck", 100);
            return maxCountInfo;
        }

        private Map<String, Map<String, Integer>> createTableOfProbability() // метод создает и возвращает коллекцию с таблицей вероятностей поедания одним животным другого
        {
            Map<String, Integer> inner_Map_Bear = new ConcurrentHashMap<>();
            inner_Map_Bear.put("Boa", 80);
            inner_Map_Bear.put("Horse", 40);
            inner_Map_Bear.put("Deer", 80);
            inner_Map_Bear.put("Rabbit", 80);
            inner_Map_Bear.put("Mouse", 90);
            inner_Map_Bear.put("Goat", 70);
            inner_Map_Bear.put("Sheep", 70);
            inner_Map_Bear.put("Hog", 50);
            inner_Map_Bear.put("Buffalo", 20);
            inner_Map_Bear.put("Duck", 10);
            probabilityTable.put("Bear", inner_Map_Bear);

            Map<String, Integer> inner_Map_Wolf = new ConcurrentHashMap<>();
            inner_Map_Wolf.put("Horse", 10);
            inner_Map_Wolf.put("Deer", 15);
            inner_Map_Wolf.put("Rabbit", 60);
            inner_Map_Wolf.put("Mouse", 80);
            inner_Map_Wolf.put("Goat", 60);
            inner_Map_Wolf.put("Sheep", 70);
            inner_Map_Wolf.put("Hog", 15);
            inner_Map_Wolf.put("Buffalo", 10);
            inner_Map_Wolf.put("Duck", 40);
            probabilityTable.put("Wolf", inner_Map_Wolf);

            Map<String, Integer> inner_Map_Boa = new ConcurrentHashMap<>();
            inner_Map_Boa.put("Fox", 10);
            inner_Map_Boa.put("Rabbit", 60);
            inner_Map_Boa.put("Mouse", 80);
            inner_Map_Boa.put("Duck", 40);
            probabilityTable.put("Boa", inner_Map_Boa);

            Map<String, Integer> inner_Map_Fox = new ConcurrentHashMap<>();
            inner_Map_Fox.put("Caterpillar", 40);
            inner_Map_Fox.put("Rabbit", 70);
            inner_Map_Fox.put("Mouse", 90);
            inner_Map_Fox.put("Duck", 60);
            probabilityTable.put("Fox", inner_Map_Fox);

            Map<String, Integer> inner_Map_Eagle = new ConcurrentHashMap<>();
            inner_Map_Eagle.put("Fox", 10);
            inner_Map_Eagle.put("Rabbit", 90);
            inner_Map_Eagle.put("Mouse", 90);
            inner_Map_Eagle.put("Duck", 80);
            probabilityTable.put("Eagle", inner_Map_Eagle);

            Map<String, Integer> inner_Map_Mouse = new ConcurrentHashMap<>();
            inner_Map_Mouse.put("Caterpillar", 90);
            probabilityTable.put("Mouse", inner_Map_Mouse);

            Map<String, Integer> inner_Map_Hog = new ConcurrentHashMap<>();
            inner_Map_Hog.put("Caterpillar", 90);
            inner_Map_Hog.put("Mouse", 50);
            probabilityTable.put("Hog", inner_Map_Hog);

            Map<String, Integer> inner_Map_Duck = new ConcurrentHashMap<>();
            inner_Map_Duck.put("Caterpillar", 90);
            probabilityTable.put("Duck", inner_Map_Duck);

            return probabilityTable;

        }

        public static int getMaxAnimalCount(String typeOfAnimal) // метод возвращает максимальное количество животных для данного вида
        {
        int maxCount = 0;
        for(String s: createMaxCountTable().keySet())
        {
            if(s.equals(typeOfAnimal))
            {
                maxCount = maxCountInfo.get(s);
            }
        }
        return maxCount;
        }

        public static int getProbability(String predator, String victim) // на основе таблицы вероятности поедания одним животным другого метод возвращает вероятность для конкретного типа животного
        {
        int probability = 0; // возвращаемое значение целочисленное
        Map<String, Map<String, Integer>> map = new AnimalInfo().createTableOfProbability(); // вызываем метод create_Table_of_probability для получения таблицы вероятностей поедания

        for(String s: map.keySet()) // в цикле по ключам таблицы ищем животное - хищник
        {
            if(s.equals(predator)) // если животное - хищник найдено
            {
                Map<String, Integer> typeOfAnimal = map.get(s); // получаем таблицу вероятностей для этого типа хищника
                for(String s1: typeOfAnimal.keySet()) // в таблице ищем вероятность для животного - жертвы
                {
                    if(s1.equals(victim)) // если животное - жертва найдено
                    {
                        probability = typeOfAnimal.get(s1); // получаем вероятность съедения жертвы хищником
                    }
                }
            }
        }
        return probability; // возвращаем найденную вероятность
    }

        public static List<String> getListOfVictims(String predator) // метод возвращает список животных-жертв для определенного хищника
        {
            List<String> listOfVictims = new CopyOnWriteArrayList<>(); // список, куда заносятся животные-жертвы
            Map<String, Map<String, Integer>> map = new AnimalInfo().createTableOfProbability(); // вызываем метод create_Table_of_probability для получения таблицы вероятностей поедания

            for(String s: map.keySet()) // в цикле по ключам таблицы ищем животное - хищник
            {
                if (s.equals(predator)) // если животное - хищник найдено
                {
                    listOfVictims = map.get(predator).keySet().stream().toList(); // набор значений из коллекции map возвращаем в виде списка строк
                }
            }
            return listOfVictims;
        }



}
