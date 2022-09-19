package Test_Examples_2;

import java.util.ArrayList;
import java.util.List;


public class Island // создание острова
{

   public static List<Living_Entity>[][] create_Island(int x, int y) // метод, возвращающий остров в виде массива листов, заполненных травой и животными
    {
        List<Living_Entity>[][] island = new ArrayList[x][y];
        for (int i = 0; i < x; i++)
        {
            for (int j = 0; j < y; j++)
            {
                island[i][j] = Factory.listOfAllAnimals();
            }

        }
        return island;
    }

}
