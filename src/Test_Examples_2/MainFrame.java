package Test_Examples_2;


public class MainFrame
{
    public static void main(String[] args)
    {

        String end = "Эксперимент на острове завершился";
        int[] islandArray = InputOutputData.printIntro();
        int dimensionX = islandArray[0];
        int dimensionY = islandArray[1];
        int timeDuration = InputOutputData.setTimeDuration();
        Location[][] island = Island.createIsland(dimensionX,dimensionY);
        MultiThreadingAnimalBehavior mtab = new MultiThreadingAnimalBehavior();
        mtab.runAllMethods(timeDuration, island);
        System.out.println(end);

    }

}