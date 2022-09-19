package Test_Examples;

import java.util.Random;
/*
ПОКА ТОЛЬКО 2 ВИДА ЖИВОТНЫХ
 */
public class Animals // класс, содержащий различные классы конкретных видов животных
{

    static boolean ready_Or_Not() // метод для получения случайного выбора да/нет для определения дальнейшей логики поведения животного
    {
        int random = new Random().nextInt(2);
        if (random  == 1)
        {
            return true;
        } else
        {
            return false;
        }
    }
    class Wolf extends Predators {
        private double max_Saturation = 5;
        private double current_Saturation = 5;
        private double min_Saturation = 0;
        private int move_Range = 1;


        private int counterWolf;

        public Wolf(double max_Saturation, double current_Saturation, double min_Saturation, int move_Range)
        {
            super();
            this.max_Saturation = max_Saturation;
            this.current_Saturation = current_Saturation;
            this.min_Saturation = min_Saturation;
            this.move_Range = move_Range;
            counterWolf = Animal_Factory.COUNTER_WOLF.incrementAndGet();
        }

        public void setMax_Saturation(double max_Saturation) {
            this.max_Saturation = max_Saturation;
        }

        public void setCurrent_Saturation(double current_Saturation) {
            this.current_Saturation = current_Saturation;
        }

        public void setMin_Saturation(double min_Saturation) {
            this.min_Saturation = min_Saturation;
        }

        public void setMove_Range(int move_Range) {
            this.move_Range = move_Range;
        }

        public double getMax_Saturation() {
            return max_Saturation;
        }

        public double getCurrent_Saturation() {
            return current_Saturation;
        }

        public double getMin_Saturation() {
            return min_Saturation;
        }

        public int getMove_Range() {
            return move_Range;
        }

        @Override
        public String toString() {
            return "Wolf{" +
                    " # " + counterWolf +
                    '}';
        }

        public int getCounterWolf() {
            return counterWolf;
        }

        @Override
        public void behavior() {

            if (ready_Or_Not()) // принимаем решение о перемещении в новую локацию
            {
                //choose_Direction(j, k, type_of_Animal, island, move_Range); // выбираем локацию для перемещения
                run(); // перемещаемся в выбранную локацию
            }
            if (ready_Or_Not()) // принимаем решение о размножении
            {
                choose_Multiply(); // при наличии животного подобного класса высчитывается процент вероятности размножения и наличие места в списке животных в текущей локации
                multiply(); // происходит процесс размножения
            }
        }

        @Override
        public void run() {

                System.out.println("Волк бежит");

        }

        @Override
        public void eat() {
            System.out.println("Волк кушает");
        }

        @Override
        public void multiply() {
            System.out.println("Волк размножается");
        }


        @Override
        public void choose_Direction(int j, int k, String type_of_Animal, Location_Example[][] island, int move_Range) {
            System.out.println("Волк выбирает бежать на " + move_Range + " локацию");

        }

        @Override
        public void choose_Multiply() {
            System.out.println("Волк выбирает размножаться");
        }
    }
    class Rabbit extends Herbivore {
            public float max_Saturation = 7f;
            public float current_Saturation = 7f;
            public float min_Saturation = 0f;
            public int move_Range = 1;
            private int counterRabbit;

            public Rabbit(float max_Saturation, float current_Saturation, float min_Saturation, int move_Range, int counterRabbit) {
                this.max_Saturation = max_Saturation;
                this.current_Saturation = current_Saturation;
                this.min_Saturation = min_Saturation;
                this.move_Range = move_Range;
                this.counterRabbit = counterRabbit;
            }

            public Rabbit() {
                counterRabbit = Animal_Factory.COUNTER_RABBIT.incrementAndGet();
            }

            public int getCounterRabbit() {
                return counterRabbit;
            }

            @Override
            public String toString() {
                return "Rabbit{" +
                        " # " + counterRabbit +
                        '}';
            }



        @Override
            public void behavior() {

                if (ready_Or_Not()) // принимаем решение о перемещении в новую локацию
                {
                    //choose_Direction(j, k, type_of_Animal, island, move_Range); // выбираем локацию для перемещения
                    run(); // перемещаемся в выбранную локацию
                }
                if (ready_Or_Not()) // принимаем решение о размножении
                {
                    choose_Multiply(); // при наличии животного подобного класса высчитывается процент вероятности размножения и наличие места в списке животных в текущей локации
                    multiply(); // происходит процесс размножения
                }
            }

            @Override
            public void run() {
                System.out.println("Кролик бежит");
            }

            @Override
            public void eat() {
                System.out.println("Кролик кушает");
            }

            @Override
            public void multiply() {
                System.out.println("Кролик размножается");
            }



            @Override
            public void choose_Direction(int j, int k, String type_of_Animal, Location_Example[][] island, int move_Range)
            {
                int direction = 0;
                System.out.println("Кролик выбирает бежать ли на новую локацию");
                if(ready_Or_Not())
                {
                    System.out.println("Кролик бежит на новую локацию");

                }
                else
                {
                    System.out.println("Кролик остается на старой локацию");
                }

            }

            @Override
            public void choose_Multiply() {
                System.out.println("Кролик выбирает размножаться");
            }
        }

}

