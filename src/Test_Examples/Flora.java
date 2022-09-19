package Test_Examples;

import java.util.Random;

public class Flora
{

    public Flora() {


    }

    @Override
    public String toString() {
        return "Flora";
    }
}
class Test
{
    public static void main(String[] args) {

        int lengthIsland = 5;
        int widthIsland = 5;
        Flora[][] array = new Flora[lengthIsland][widthIsland];
        for (int i = 0; i < widthIsland; i++) {
            for (int j = 0; j < lengthIsland; j++) {

                try {
                    Thread.sleep(100);
                    Random random = new Random();
                    boolean  isFlora = random.nextBoolean();
                    if(isFlora)
                    {
                        array[i][j] = new Flora();
                    }

                    System.out.print(array[i][j] + " ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            System.out.println();

        }

    }
}