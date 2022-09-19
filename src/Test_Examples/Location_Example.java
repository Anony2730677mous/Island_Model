package Test_Examples;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Location_Example // класс для создания локации и наполнения массива локаций модели острова
{
     int location_id;
     int dimension_x;
     int dimension_y;

    ArrayList<ArrayList<? extends Animal>> map; // отдельная локация представляет собой список, содержащий списки животных различных видов

    public static Location_Example[][] set_Location_on_Island(int x, int y) throws InstantiationException, IllegalAccessException // метод, создающий и заносящий в массив острова новые локации, в которых находятся списки животных, и имеющие номер в качестве первого аргумента
    {
        Integer location_id = 0;
        Location_Example[][] array_of_location = new Location_Example[x][y]; // создаем массив локаций(остров) размером, указанным во входных параметрах метода
        for (int i = 0; i < x; i++) // цикл по ячейкам массива-острова
        {
            for (int j = 0; j < y; j++)
            {
                location_id = ++location_id; // задаем номер ячейки
                ArrayList<ArrayList<? extends Animal>> list; // создаем список, состоящий из списков животных
                list = ListCreation.create_Animal_List(ListCreation.get_Animal_Type_Class()); // наполняем список списков новосозданными животными
                array_of_location[i][j] = new Location_Example(location_id, list, i, j); // заполняем ячейку массива номером и списком списков животных, созданных для этой ячейки
                System.out.print(array_of_location[i][j]);
            }
            System.out.println("\n=====================================================");
        }
        return array_of_location;
    }
    public static Location_Example get_Location_Example(Location_Example[][] island, int x, int y) // метод, из массива локаций возвращающий локацию с координатами, заданными входящими параметрами
    {
        return island[x][y];
    }
    public static ArrayList<Location_Example> list_of_Location_Around(Location_Example location, Location_Example[][] location_array, int move_Range, int[] island) // метод, возвращающий список соседних локаций, куда может переместиться животное
    {
        int current_x = location.getDimension_x(); // получаем координату x текущей локации, переданной в качестве параметра
        int current_y = location.getDimension_y(); // получаем координату y текущей локации, переданной в качестве параметра
        Location_Example around; // временная переменная для занесения в список локаций
        ArrayList<Location_Example> list_to_return = new ArrayList<>(); // создаем список локаций, окружающих текущую локацию
        for (int i = current_x - move_Range; i <= current_x + move_Range; i++) // цикл по координатам х с учетом максимального расстояния передвижения животного
        {
            for (int j = current_y - move_Range; j <= current_y + move_Range ; j++) // цикл по координатам y с учетом максимального расстояния передвижения животного
            {
                if((i >= 0 && i < island[0]) && (j >=0 && j < island[1])) // если координаты циклов входят в диапазон координат острова
                {
                    around = location_array[i][j]; // временной локации присваиваем локацию из массива локаций(острова), переданного в параметры метода
                    if(!around.equals(location)) // чтобы исключить текущую локацию, сравниваем её с окружающими
                    {
                        list_to_return.add(around); // после сравнения добавляем в список локаций, окружающих текущую локацию
                    }
                }

            }
        }
        return list_to_return; // метод возвращает список локаций, куда может переместиться животное
    }
    public static ArrayList<? extends Animal> choose_Location_to_Relocate(ArrayList<Location_Example> list_of_Location, int max_Count_of_this_Type, String type_of_Animal) // метод возвращиющий список, куда перейдет животного с текущей локации
    {
        ArrayList<? extends Animal> list_to_Relocate; // метод возвращает список на выбранной локации
        ArrayList<ArrayList<? extends Animal>> list_for_Choice = new ArrayList<>(); // список списков животных для выбора одного из списков, куда перейдет животное
        for(Location_Example location_list: list_of_Location) // цикл по списку локаций, окружающих текущую локацию
        {
            ArrayList<ArrayList<? extends Animal>> list_1 = location_list.getMap(); // на текущей итерации из локации получаем список со списками животных
            for(int i = 0; i < list_1.size(); i++) // цикл по списку со списками животных
            {
                ArrayList<? extends Animal> list_2 = list_1.get(i); // на текущей итерации получаем список животного каждого вида
                for (int j = 0; j < list_2.size(); j++) // цикл по списку по каждому типу животного
                {
                    if(list_2.get(0).getClass().getSimpleName().equals(type_of_Animal)) // если тип животного из текущей итерации совпадает с типом, переданным в параметр
                    {
                        if(list_2.size() < max_Count_of_this_Type) // если численность животных в списке в локации меньше, чем максимальная численность этого вида для локации
                        {
                            list_for_Choice.add(list_2); // проходящий по требованиям список животных заносится в список списков животных, куда может переместится животное
                        }
                    }
                }
            }

        }
        Random random = new Random();
        list_to_Relocate = list_for_Choice.get(random.nextInt(0, list_for_Choice.size())); //случайным образом выбирается из списка списков список, который будет возвращен методом
        return list_to_Relocate;
    }
    public ArrayList<? extends Animal> get_List_of_Animal(Location_Example location, String type_of_Animal) // метод, возвращающий из переданной в параметр локации список животных того типа, который передан в параметр в виде строковой переменной
    {
        ArrayList<? extends Animal> list_to_return = new ArrayList<>();
        ArrayList<ArrayList<? extends Animal>> list_of_this_location = location.getMap();
        for(ArrayList<? extends Animal> lists: list_of_this_location)
        {
            if(lists.getClass().getSimpleName().equals(type_of_Animal))
            {
                list_to_return = lists;
            }
        }
        return list_to_return;
    }
    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public ArrayList<ArrayList<? extends Animal>> getMap() {
        return map;
    }

    public void setMap(ArrayList<ArrayList<? extends Animal>> map) {
        this.map = map;
    }

    public Location_Example(Integer location_id, ArrayList<ArrayList<? extends Animal>> map, int x, int y)
    {
        this.location_id = location_id;
        this.map = map;
        this.dimension_x = x;
        this.dimension_y = y;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location_Example)) return false;
        Location_Example that = (Location_Example) o;
        return location_id == that.location_id && dimension_x == that.dimension_x && dimension_y == that.dimension_y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(location_id, dimension_x, dimension_y);
    }

    public int getDimension_x() {
        return dimension_x;
    }

    public void setDimension_x(int dimension_x) {
        this.dimension_x = dimension_x;
    }

    public int getDimension_y() {
        return dimension_y;
    }

    public void setDimension_y(int dimension_y) {
        this.dimension_y = dimension_y;
    }

    @Override
    public String toString() {
        return "{" +
                "loc # " + location_id +
                '}' + " ";
    }
}
class Location_Create_Example // класс для проведения тестов
{
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Location_Example[][] locex =  Location_Example.set_Location_on_Island(2,4);

        for (int i = 0; i < locex.length; i++) // цикл по длине острова
        {
            for (int j = 0; j < locex[i].length; j++)  // цикл по ширине острова
            {
                int loc_id = locex[i][j].getLocation_id();
                ArrayList<ArrayList<? extends Animal>> loc_List_of_Animal = locex[i][j].getMap(); // из каждой ячейки острова получаем список списков животных
                for (int k = 0; k < loc_List_of_Animal.size(); k++) // цикл по списку животных
                {
                    ArrayList<? extends Animal> list = loc_List_of_Animal.get(k); // получаем список животных определенного вида на текущей итерации
                    String animal_Class = list.get(0).getClass().getSimpleName();
                    Method animal_Behavior;
//                    Iterator<? extends Animal> iterator = list.iterator();
//                    while (iterator.hasNext())
                    for (int l = 0; l < list.size(); l++)
                    {

                        if (Predators.class.isAssignableFrom(list.get(0).getClass())) // для вызова метода поведения хищника в локации и на острове используем рефлексию
                        {
                            System.out.println("Животное вида " + list.get(i).getClass().getSimpleName() + " с номером " + list.get(l) +  " из локации № " + loc_id);
                            list.get(l).eat();
                            list.get(l).run();
                            list.get(l).multiply();
                            list.get(l).choose_Multiply();


                        } else if (Herbivore.class.isAssignableFrom(list.get(0).getClass())) // для вызова метода поведения травоядного в локации и на острове используем рефлексию
                        {
                            System.out.println("Животное вида " + list.get(i).getClass().getSimpleName() + " с номером " + list.get(l) +  " из локации № " + loc_id);
                            list.get(l).eat();
                            list.get(l).run();
                            list.get(l).multiply();
                            list.get(l).choose_Multiply();
                        }
                    }
                }
            }
        }
    }
}