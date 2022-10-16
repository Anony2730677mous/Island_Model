import Test_Examples.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Main_Frame // запуск основной программы
{
    public static void main(String[] args)  {
        /*
        Начальная фаза: запрос параметров острова и инициализация острова, вывод статистики по созданному острову.
         */
        int[] island_Dimensions = Input_Output_Data.print_Intro(); // задаем размеры острова
        Location_Example[][] island = new Location_Example[0][0];
        try {
            island = Location_Example.set_Location_on_Island(island_Dimensions[0], island_Dimensions[1]); // создаем остров в виде двумерного массива локаций по заданным размерам
            Input_Output_Data.print_Statistics(island); // выводим первоначальную статистику по созданному острову
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int time_Duration = Input_Output_Data.set_Time_Duration(); // пользователю предлагается установить длительность эксперимента с островом

        /*
        поведение животных
         */


    }
}
