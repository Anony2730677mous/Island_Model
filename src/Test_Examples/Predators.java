package Test_Examples;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Predators implements Animal // животные делятся на 2 подкласса: Хищники
{
    private double max_Saturation = 5;
    private double current_Saturation = 5;
    private double min_Saturation = 0;
    private int move_Range = 1;

    public Predators() {

    }

    public double getMax_Saturation() {
        return max_Saturation;
    }

    public void setMax_Saturation(double max_Saturation) {
        this.max_Saturation = max_Saturation;
    }

    public double getCurrent_Saturation() {
        return current_Saturation;
    }

    public void setCurrent_Saturation(double current_Saturation) {
        this.current_Saturation = current_Saturation;
    }

    public double getMin_Saturation() {
        return min_Saturation;
    }

    public void setMin_Saturation(double min_Saturation) {
        this.min_Saturation = min_Saturation;
    }

    public int getMove_Range() {
        return move_Range;
    }

    public void setMove_Range(int move_Range) {
        this.move_Range = move_Range;
    }

    @Override
    public void run() {

    }

    @Override
    public void eat() {

    }

    @Override
    public void multiply() {

    }

    @Override
    public void toDie(Location_Example locex) {

    }


    @Override
    public void choose_Direction(int j, int k, String type_of_Animal, Location_Example[][] island, int move_Range)
    {
      int[] array_of_dimension = new int[0];
      int max_Count_of_this_Type = 5;
      Location_Example current_Location = Location_Example.get_Location_Example(island, j, k); // определяем локацию, где находится животное
      ArrayList<Location_Example> list_of_Location_Around = Location_Example.list_of_Location_Around(current_Location, island,move_Range, array_of_dimension); // определяем список локаций, куда может переместиться животное
      ArrayList<? extends Animal> location_to_Relocate = Location_Example.choose_Location_to_Relocate(list_of_Location_Around, max_Count_of_this_Type, type_of_Animal); // выбирается локация для перемещения животного
      ArrayList<ArrayList<? extends Animal>> currentList = current_Location.getMap();

//        for (int i = current_Location.size()-1; i >= 0; i--)
//        {
//            if(Animals.ready_Or_Not())
//            {
//                list2.add(current_Location.get(i));
//                current_Location.remove(i);
//            }
//
//        }
    }

    @Override
    public void choose_Multiply() {

    }

    @Override
    public void behavior() {

    }
}
