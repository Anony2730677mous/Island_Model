package Test_Examples;


public interface Animal // отдельные классы животных реализуют интерфейс Animal, включающий в себя различные методы поведения животных
{
    void run();
    void eat();
    void multiply();
    void toDie(Location_Example locex);
    void choose_Direction(int j, int k, String type_of_Animal, Location_Example[][] island, int move_Range);
    void choose_Multiply();
    void behavior();

}

class Herbivore implements Animal // животные делятся на 2 подкласса: Травоядные
{
    private double max_Saturation = 5;
    private double current_Saturation = 5;
    private double min_Saturation = 0;
    private int move_Range = 1;


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
    public void behavior() {

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
    public void choose_Direction(int j, int k, String type_of_Animal, Location_Example[][] island, int move_Range) {

    }

    @Override
    public void choose_Multiply() {

    }
}




