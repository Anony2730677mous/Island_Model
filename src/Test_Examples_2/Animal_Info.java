package Test_Examples_2;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Animal_Info // содержит различную информацию о животных разного вида
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
        static Map<String, Integer> max_Count_Info = new HashMap<>();// коллекция с максимальными значениями животных в ячейке для каждого вида
        private Map<String, Map<String, Integer>> probability_Table = new HashMap<>(); // коллекция с таблицей вероятностей поедания одним животным другого
        private static Map<String, Integer> probabilityBorn = new HashMap<>(); // коллекция с таблицей вероятностей рождения нового животного
        private static Map<String, Integer> moveRange = new HashMap<>(); // коллекция с таблицей дальности передвижения животного каждого вида

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

        public static int getMoveRangeValue(String type_of_Animal) // метод возвращает дальность передвижения животного для данного вида
        {
            int moveRange = 0;
            for(String s: createMoveRangeTable().keySet())
            {
                if(s.equals(type_of_Animal))
                {
                    moveRange = createMoveRangeTable().get(s);
                }
            }
            return moveRange;
        }

        public static int getProbabilityBornValue(String type_of_Animal) // метод возвращает вероятность рождения животного для данного вида
        {
            int count = 0;
            for(String s: createProbabilityBornTable().keySet())
            {
                if(s.equals(type_of_Animal))
                {
                    count = createProbabilityBornTable().get(s);
                }
            }
            return count;
        }

        private static Map<String, Integer> create_max_Count_Table() // метод создает коллекцию, содержащую информацию о максимальном количестве экземпляров каждого животного
        {

            max_Count_Info.put("Boa", 30);
            max_Count_Info.put("Horse", 20);
            max_Count_Info.put("Deer", 20);
            max_Count_Info.put("Rabbit", 150);
            max_Count_Info.put("Mouse", 500);
            max_Count_Info.put("Goat", 140);
            max_Count_Info.put("Plant", 200);
            max_Count_Info.put("Wolf", 30);
            max_Count_Info.put("Eagle", 20);
            max_Count_Info.put("Fox", 30);
            max_Count_Info.put("Caterpillar", 1000);
            max_Count_Info.put("Bear", 5);
            max_Count_Info.put("Sheep", 140);
            max_Count_Info.put("Hog", 50);
            max_Count_Info.put("Buffalo", 10);
            max_Count_Info.put("Duck", 100);
            return max_Count_Info;
        }

        private Map<String, Map<String, Integer>> create_Table_of_probability() // метод создает и возвращает коллекцию с таблицей вероятностей поедания одним животным другого
        {
            Map<String, Integer> inner_Map_Bear = new HashMap<>();
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
            probability_Table.put("Bear", inner_Map_Bear);

            Map<String, Integer> inner_Map_Wolf = new HashMap<>();
            inner_Map_Wolf.put("Horse", 10);
            inner_Map_Wolf.put("Deer", 15);
            inner_Map_Wolf.put("Rabbit", 60);
            inner_Map_Wolf.put("Mouse", 80);
            inner_Map_Wolf.put("Goat", 60);
            inner_Map_Wolf.put("Sheep", 70);
            inner_Map_Wolf.put("Hog", 15);
            inner_Map_Wolf.put("Buffalo", 10);
            inner_Map_Wolf.put("Duck", 40);
            probability_Table.put("Wolf", inner_Map_Wolf);

            Map<String, Integer> inner_Map_Boa = new HashMap<>();
            inner_Map_Boa.put("Fox", 10);
            inner_Map_Boa.put("Rabbit", 60);
            inner_Map_Boa.put("Mouse", 80);
            inner_Map_Boa.put("Duck", 40);
            probability_Table.put("Boa", inner_Map_Boa);

            Map<String, Integer> inner_Map_Fox = new HashMap<>();
            inner_Map_Fox.put("Caterpillar", 40);
            inner_Map_Fox.put("Rabbit", 70);
            inner_Map_Fox.put("Mouse", 90);
            inner_Map_Fox.put("Duck", 60);
            probability_Table.put("Fox", inner_Map_Fox);

            Map<String, Integer> inner_Map_Eagle = new HashMap<>();
            inner_Map_Eagle.put("Fox", 10);
            inner_Map_Eagle.put("Rabbit", 90);
            inner_Map_Eagle.put("Mouse", 90);
            inner_Map_Eagle.put("Duck", 80);
            probability_Table.put("Eagle", inner_Map_Eagle);

            Map<String, Integer> inner_Map_Mouse = new HashMap<>();
            inner_Map_Mouse.put("Caterpillar", 90);
            probability_Table.put("Mouse", inner_Map_Mouse);

            Map<String, Integer> inner_Map_Hog = new HashMap<>();
            inner_Map_Hog.put("Caterpillar", 90);
            inner_Map_Hog.put("Mouse", 50);
            probability_Table.put("Hog", inner_Map_Hog);

            Map<String, Integer> inner_Map_Duck = new HashMap<>();
            inner_Map_Duck.put("Caterpillar", 90);
            probability_Table.put("Duck", inner_Map_Duck);

            return probability_Table;

        }

        public static int get_max_Animal_Count(String type_of_Animal) // метод возвращает максимальное количество животных для данного вида
        {
        int max_count = 0;
        for(String s: create_max_Count_Table().keySet())
        {
            if(s.equals(type_of_Animal))
            {
                max_count = max_Count_Info.get(s);
            }
        }
        return max_count;
        }

        public static int get_probability(String predator, String victim) // на основе таблицы вероятности поедания одним животным другого метод возвращает вероятность для конкретного типа животного
        {
        int probability = 0; // возвращаемое значение целочисленное
        Map<String, Map<String, Integer>> map = new Animal_Info().create_Table_of_probability(); // вызываем метод create_Table_of_probability для получения таблицы вероятностей поедания

        for(String s: map.keySet()) // в цикле по ключам таблицы ищем животное - хищник
        {
            if(s.equals(predator)) // если животное - хищник найдено
            {
                Map<String, Integer> type_of_Animal = map.get(s); // получаем таблицу вероятностей для этого типа хищника
                for(String s1: type_of_Animal.keySet()) // в таблице ищем вероятность для животного - жертвы
                {
                    if(s1.equals(victim)) // если животное - жертва найдено
                    {
                        probability = type_of_Animal.get(s1); // получаем вероятность съедения жертвы хищником
                    }
                }
            }
        }
        return probability; // возвращаем найденную вероятность
    }

        public static List<String> getListOfVictims(String predator) // метод возвращает список животных-жертв для определенного хищника
        {
            List<String> listOfVictims = new ArrayList<>(); // список, куда заносятся животные-жертвы
            Map<String, Map<String, Integer>> map = new Animal_Info().create_Table_of_probability(); // вызываем метод create_Table_of_probability для получения таблицы вероятностей поедания

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
class Test_6
{
    public static void main(String[] args) {


        System.out.println(Animal_Info.get_probability("Eagle", "Eagle"));
    }
}