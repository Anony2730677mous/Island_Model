package Test_Examples_2;


import java.util.Comparator;
import java.util.Objects;

abstract class LivingEntity // для создания абстрактных типов животных
{
    abstract void multiplay(boolean multiplay);
    abstract boolean isMultiplay();
    abstract boolean isDead();
    abstract void setDead(boolean dead);
    abstract boolean isMove();


    abstract void setMove(boolean move);
    int count;
    String name;

    public String getName() {
        return name;
    }
}
abstract class Animal extends LivingEntity // животные
{
    @Override
    boolean isMove() {
        return false;
    }

    @Override
    void setMove(boolean move) {

    }

    String name;
    abstract void eat();
    abstract void hungry();

    @Override
    void multiplay(boolean multiplay) {

    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    boolean isMultiplay() {
        return false;
    }

    @Override
    boolean isDead() {
        return false;
    }

    @Override
    void setDead(boolean dead) {

    }
}
public class Animals extends Animal// для создания конкретных типов животных
{
    String name;
    @Override
    public String getName() {
        return name;
    }

    @Override
    boolean isMove() {
        return false;
    }

    @Override
    void setMove(boolean move) {

    }

    @Override
    void multiplay(boolean multiplay) {

    }

    @Override
    boolean isMultiplay() {
        return false;
    }

    @Override
    boolean isDead() {
        return false;
    }

    @Override
    void setDead(boolean dead) {

    }
    double current_Saturation;
    public double getCurrent_Saturation() {
        return current_Saturation;
    }
    @Override
    public void eat() {}

    @Override
    public void hungry() {}

    class Plant extends LivingEntity // трава
    {
        @Override
        boolean isMove() {
            return false;
        }

        @Override
        void setMove(boolean move) {

        }

        String name;
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Plant plant = (Plant) o;
            return count == plant.count;
        }

        @Override
        public int hashCode() {
            return Objects.hash(count);
        }

        @Override
        void multiplay(boolean multiplay) {

        }
        @Override
        boolean isMultiplay() {
            return false;
        }

        @Override
        public boolean isDead() {
            return this.isDead;
        }
        @Override
        public void setDead(boolean dead) {
            this.isDead = dead;
        }

        @Override
        public String toString() {
            return "Plant{" +
                    " # " + count +
                    '}';
        }
        boolean isDead;
        int count;
        Plant()
        {
            name = "Plant";
            this.isDead = false;
            count = AnimalInfo.COUNTER_PLANT.incrementAndGet();
        }

        public String getName() {
            return name;
        }
    }
    abstract class Predatory_Animal extends Animals // хищные животные
    {
        String name;
        @Override
        public String getName() {
            return name;
        }
        int count;
        @Override
        void multiplay(boolean multiplay) {
            super.multiplay(multiplay);
        }

        @Override
        boolean isMultiplay() {
            return super.isMultiplay();
        }

        double current_Saturation;
        public double getCurrent_Saturation() {
            return current_Saturation;
        }
        @Override
        public abstract void eat();

        @Override
        boolean isDead() {
            return super.isDead();
        }

        @Override
        void setDead(boolean dead) {
            super.setDead(dead);
        }
    }
    abstract class Herbivorous_Animal extends Animals // травоядные животные
    {

        String name;
        @Override
        public String getName() {
            return name;
        }
        int count;
        @Override
        void multiplay(boolean multiplay) {
            super.multiplay(multiplay);
        }

        @Override
        boolean isMultiplay() {
            return super.isMultiplay();
        }

        double current_Saturation;
        double max_Saturation;
        double day_down_Saturation;


        @Override
        boolean isDead() {
            return super.isDead();
        }

        @Override
        void setDead(boolean dead) {
            super.setDead(dead);
        }

        public double getCurrent_Saturation() {
            return current_Saturation;
        }

        @Override
        public void eat()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = max_Saturation;
            }
        }

        @Override
        public void hungry()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = current_Saturation - day_down_Saturation;
            }
            else
            {
                this.current_Saturation = max_Saturation - day_down_Saturation;
            }
        }
    }
    class Wolf extends Predatory_Animal
    {
        String name;
        public String getName() {
            return name;
        }
        private boolean isMove;
        @Override
        public boolean isMove() {
            return isMove;
        }
        @Override
        public void setMove(boolean move) {
            isMove = move;
        }

        private boolean multiplay;

        @Override
        void multiplay(boolean multiplay) {
            this.multiplay = multiplay;
        }

        @Override
        boolean isMultiplay() {
            return this.multiplay;
        }

        @Override
        public boolean isDead() {
            return this.isDead;
        }
        @Override
        public void setDead(boolean dead) {
            this.isDead = dead;
        }
        boolean isDead;
        @Override
        public void eat()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = max_Saturation;
            }
        }

        public double getCurrent_Saturation() {
            return current_Saturation;
        }

        @Override
        public void hungry()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = current_Saturation - day_down_Saturation;
            }
            else
            {
                this.current_Saturation = max_Saturation - day_down_Saturation;
            }
        }


        @Override
        public String toString() {
            return "Wolf{" +
                    " # " + count +
                    '}';
        }

        double weight = 50;
        double max_Saturation = 8;
        double current_Saturation;
        double day_down_Saturation = 2;
        public Wolf() {
            name = "Wolf";
            current_Saturation = max_Saturation;
            this.isDead = false;
            this.multiplay = false;
            this.isMove = false;
            count = AnimalInfo.COUNTER_WOLF.incrementAndGet();
        }
        int count;
    }
    class Boa extends Predatory_Animal
    {
        String name;
        public String getName() {
            return name;
        }
        private boolean isMove;
        @Override
        public boolean isMove() {
            return isMove;
        }
        @Override
        public void setMove(boolean move) {
            isMove = move;
        }

        private boolean multiplay;

        @Override
        void multiplay(boolean multiplay) {
            this.multiplay = multiplay;
        }

        @Override
        boolean isMultiplay() {
            return this.multiplay;
        }

        @Override
        public boolean isDead() {
            return this.isDead;
        }
        @Override
        public void setDead(boolean dead) {
            this.isDead = dead;
        }
        boolean isDead;
        @Override
        public void eat()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = max_Saturation;
            }
        }

        public double getCurrent_Saturation() {
            return current_Saturation;
        }

        @Override
        public void hungry()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = current_Saturation - day_down_Saturation;
            }
            else
            {
                this.current_Saturation = max_Saturation - day_down_Saturation;
            }
        }
        @Override
        public String toString() {
            return "Boa{" +
                    " # " + count +
                    '}';
        }

        double weight = 15;
        double max_Saturation = 3;
        double current_Saturation;
        double day_down_Saturation = 1;
        public Boa() {
            name = "Boa";
            current_Saturation = max_Saturation;
            this.isDead = false;
            this.multiplay = false;
            this.isMove = false;
            count = AnimalInfo.COUNTER_BOA.incrementAndGet();
        }
        int count;
    }
    class Fox extends Predatory_Animal
    {
        String name;
        public String getName() {
            return name;
        }
        private boolean isMove;
        @Override
        public boolean isMove() {
            return isMove;
        }
        @Override
        public void setMove(boolean move) {
            isMove = move;
        }

        private boolean multiplay;

        @Override
        void multiplay(boolean multiplay) {
            this.multiplay = multiplay;
        }

        @Override
        boolean isMultiplay() {
            return this.multiplay;
        }

        @Override
        public boolean isDead() {
            return this.isDead;
        }
        @Override
        public void setDead(boolean dead) {
            this.isDead = dead;
        }
        boolean isDead;
        @Override
        public void eat()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = max_Saturation;
            }
        }

        public double getCurrent_Saturation() {
            return current_Saturation;
        }

        @Override
        public void hungry()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = current_Saturation - day_down_Saturation;
            }
            else
            {
                this.current_Saturation = max_Saturation - day_down_Saturation;
            }
        }
        @Override
        public String toString() {
            return "Fox{" +
                    " # " + count +
                    '}';
        }

        double weight = 8;
        double max_Saturation = 2;
        double current_Saturation;
        double day_down_Saturation = 0.5;
        public Fox() {
            name = "Fox";
            current_Saturation = max_Saturation;
            this.isDead = false;
            this.multiplay = false;
            this.isMove = false;
            count = AnimalInfo.COUNTER_FOX.incrementAndGet();
        }
        int count;
    }
    class Bear extends Predatory_Animal
    {
        String name;
        public String getName() {
            return name;
        }
        private boolean isMove;
        @Override
        public boolean isMove() {
            return isMove;
        }
        @Override
        public void setMove(boolean move) {
            isMove = move;
        }

        private boolean multiplay;

        @Override
        void multiplay(boolean multiplay) {
            this.multiplay = multiplay;
        }

        @Override
        boolean isMultiplay() {
            return this.multiplay;
        }

        @Override
        public boolean isDead() {
            return this.isDead;
        }
        @Override
        public void setDead(boolean dead) {
            this.isDead = dead;
        }
        boolean isDead;
        @Override
        public void eat()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = max_Saturation;
            }
        }

        public double getCurrent_Saturation() {
            return current_Saturation;
        }

        @Override
        public void hungry()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = current_Saturation - day_down_Saturation;
            }
            else
            {
                this.current_Saturation = max_Saturation - day_down_Saturation;
            }
        }
        @Override
        public String toString() {
            return "Bear{" +
                    " # " + count +
                    '}';
        }

        double weight = 500;
        double max_Saturation = 80;
        double current_Saturation;
        double day_down_Saturation = 20;
        public Bear() {
            name = "Bear";
            current_Saturation = max_Saturation;
            this.isDead = false;
            this.multiplay = false;
            this.isMove = false;
            count = AnimalInfo.COUNTER_BEAR.incrementAndGet();
        }
        int count;
    }
    class Eagle extends Predatory_Animal
    {
        String name;
        public String getName() {
            return name;
        }
        private boolean isMove;
        @Override
        public boolean isMove() {
            return isMove;
        }
        @Override
        public void setMove(boolean move) {
            isMove = move;
        }

        private boolean multiplay;

        @Override
        void multiplay(boolean multiplay) {
            this.multiplay = multiplay;
        }

        @Override
        boolean isMultiplay() {
            return this.multiplay;
        }

        @Override
        public boolean isDead() {
            return this.isDead;
        }
        @Override
        public void setDead(boolean dead) {
            this.isDead = dead;
        }
        boolean isDead;
        @Override
        public void eat()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = max_Saturation;
            }
        }

        public double getCurrent_Saturation() {
            return current_Saturation;
        }

        @Override
        public void hungry()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = current_Saturation - day_down_Saturation;
            }
            else
            {
                this.current_Saturation = max_Saturation - day_down_Saturation;
            }
        }
        @Override
        public String toString() {
            return "Eagle{" +
                    " # " + count +
                    '}';
        }

        double weight = 6;
        double max_Saturation = 1;
        double current_Saturation;
        double day_down_Saturation = 0.2;
        public Eagle() {
            name = "Eagle";
            current_Saturation = max_Saturation;
            this.isDead = false;
            this.multiplay = false;
            this.isMove = false;
            count = AnimalInfo.COUNTER_EAGLE.incrementAndGet();
        }
        int count;
    }
    class Horse extends Herbivorous_Animal
    {
        String name;
        public String getName() {
            return name;
        }
        private boolean isMove;
        @Override
        public boolean isMove() {
            return isMove;
        }
        @Override
        public void setMove(boolean move) {
            isMove = move;
        }

        private boolean multiplay;

        @Override
        void multiplay(boolean multiplay) {
            this.multiplay = multiplay;
        }

        @Override
        boolean isMultiplay() {
            return this.multiplay;
        }

        public boolean isDead() {
            return isDead;
        }

        public void setDead(boolean dead) {
            isDead = dead;
        }
        public double getCurrent_Saturation() {
            return current_Saturation;
        }
        @Override
        public String toString() {
            return "Horse{" +
                    " # " + count +
                    '}';
        }

        @Override
        public void eat()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = max_Saturation;
            }
        }

        @Override
        public void hungry()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = current_Saturation - day_down_Saturation;
            }
            else
            {
                this.current_Saturation = max_Saturation - day_down_Saturation;
            }
        }

        double weight = 400;
        double max_Saturation = 60;
        double current_Saturation;
        double day_down_Saturation = 15;
        boolean isDead;
        public Horse() {
            name = "Horse";
            this.isDead = false;
            this.multiplay = false;
            this.isMove = false;
            current_Saturation = max_Saturation;
            count = AnimalInfo.COUNTER_HORSE.incrementAndGet();
        }
        int count;
    }
    class Deer extends Herbivorous_Animal
    {
        String name;
        public String getName() {
            return name;
        }
        private boolean isMove;
        @Override
        public boolean isMove() {
            return isMove;
        }
        @Override
        public void setMove(boolean move) {
            isMove = move;
        }

        private boolean multiplay;

        @Override
        void multiplay(boolean multiplay) {
            this.multiplay = multiplay;
        }

        @Override
        boolean isMultiplay() {
            return this.multiplay;
        }

        public boolean isDead() {
            return isDead;
        }

        public void setDead(boolean dead) {
            isDead = dead;
        }
        @Override
        public void eat()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = max_Saturation;
            }
        }

        public double getCurrent_Saturation() {
            return current_Saturation;
        }

        @Override
        public void hungry()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = current_Saturation - day_down_Saturation;
            }
            else
            {
                this.current_Saturation = max_Saturation - day_down_Saturation;
            }
        }
        @Override
        public String toString() {
            return "Deer{" +
                    " # " + count +
                    '}';
        }
        boolean isDead;
        double weight = 300;
        double current_Saturation;
        double day_down_Saturation = 12.5;
        double max_Saturation = 50;
        public Deer() {
            name = "Deer";
            this.isDead = false;
            this.multiplay = false;
            this.isMove = false;
            current_Saturation = max_Saturation;
            count = AnimalInfo.COUNTER_DEER.incrementAndGet();
        }
        int count;
    }
    class Rabbit extends Herbivorous_Animal
    {
        String name;
        public String getName() {
            return name;
        }
        private boolean isMove;
        @Override
        public boolean isMove() {
            return isMove;
        }
        @Override
        public void setMove(boolean move) {
            isMove = move;
        }

        private boolean multiplay;

        @Override
        void multiplay(boolean multiplay) {
            this.multiplay = multiplay;
        }

        @Override
        boolean isMultiplay() {
            return this.multiplay;
        }

        public boolean isDead() {
            return isDead;
        }

        public void setDead(boolean dead) {
            isDead = dead;
        }
        public double getCurrent_Saturation() {
            return current_Saturation;
        }
        @Override
        public void eat()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = max_Saturation;
            }
        }

        @Override
        public void hungry()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = current_Saturation - day_down_Saturation;
            }
            else
            {
                this.current_Saturation = max_Saturation - day_down_Saturation;
            }
        }
        @Override
        public String toString() {
            return "Rabbit{" +
                    " # " + count +
                    '}';
        }

        double weight = 2;
        double current_Saturation;
        double day_down_Saturation = 0.15;
        double max_Saturation = 0.45;
        boolean isDead;
        public Rabbit() {
            name = "Rabbit";
            this.isDead = false;
            this.multiplay = false;
            this.isMove = false;
            current_Saturation = max_Saturation;
            count = AnimalInfo.COUNTER_RABBIT.incrementAndGet();
        }
        int count;
    }
    class Mouse extends Herbivorous_Animal
    {
        String name;
        public String getName() {
            return name;
        }
        private boolean isMove;
        @Override
        public boolean isMove() {
            return isMove;
        }
        @Override
        public void setMove(boolean move) {
            isMove = move;
        }

        private boolean multiplay;

        @Override
        void multiplay(boolean multiplay) {
            this.multiplay = multiplay;
        }

        @Override
        boolean isMultiplay() {
            return this.multiplay;
        }

        public boolean isDead() {
            return isDead;
        }

        public void setDead(boolean dead) {
            isDead = dead;
        }
        public double getCurrent_Saturation() {
            return current_Saturation;
        }
        @Override
        public void eat()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = max_Saturation;
            }
        }

        @Override
        public void hungry()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = current_Saturation - day_down_Saturation;
            }
            else
            {
                this.current_Saturation = max_Saturation - day_down_Saturation;
            }
        }
        @Override
        public String toString() {
            return "Mouse{" +
                    " # " + count +
                    '}';
        }

        double weight = 0.05;
        double current_Saturation;
        double day_down_Saturation = 0.005;
        double max_Saturation = 0.01;
        boolean isDead;
        public Mouse() {
            name = "Mouse";
            this.isDead = false;
            this.multiplay = false;
            this.isMove = false;
            current_Saturation = max_Saturation;
            count = AnimalInfo.COUNTER_MOUSE.incrementAndGet();
        }
        int count;

    }
    class Goat extends Herbivorous_Animal
    {
        String name;
        public String getName() {
            return name;
        }
        private boolean isMove;
        @Override
        public boolean isMove() {
            return isMove;
        }
        @Override
        public void setMove(boolean move) {
            isMove = move;
        }

        private boolean multiplay;

        @Override
        void multiplay(boolean multiplay) {
            this.multiplay = multiplay;
        }

        @Override
        boolean isMultiplay() {
            return this.multiplay;
        }

        public boolean isDead() {
            return isDead;
        }

        public void setDead(boolean dead) {
            isDead = dead;
        }
        public double getCurrent_Saturation() {
            return current_Saturation;
        }
        @Override
        public void eat()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = max_Saturation;
            }
        }

        @Override
        public void hungry()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = current_Saturation - day_down_Saturation;
            }
            else
            {
                this.current_Saturation = max_Saturation - day_down_Saturation;
            }
        }
        @Override
        public String toString() {
            return "Goat{" +
                    " # " + count +
                    '}';
        }

        double weight = 60;
        double current_Saturation;
        double day_down_Saturation = 2.5;
        double max_Saturation = 10;
        boolean isDead;
        public Goat() {
            name = "Goat";
            this.isDead = false;
            this.multiplay = false;
            this.isMove = false;
            current_Saturation = max_Saturation;
            count = AnimalInfo.COUNTER_GOAT.incrementAndGet();
        }
        int count;

    }
    class Sheep extends Herbivorous_Animal
    {
        String name;
        public String getName() {
            return name;
        }
        private boolean isMove;
        @Override
        public boolean isMove() {
            return isMove;
        }
        @Override
        public void setMove(boolean move) {
            isMove = move;
        }
        private boolean multiplay;

        @Override
        void multiplay(boolean multiplay) {
            this.multiplay = multiplay;
        }

        @Override
        boolean isMultiplay() {
            return this.multiplay;
        }

        public boolean isDead() {
            return isDead;
        }

        public void setDead(boolean dead) {
            isDead = dead;
        }
        public double getCurrent_Saturation() {
            return current_Saturation;
        }
        @Override
        public void eat()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = max_Saturation;
            }
        }

        @Override
        public void hungry()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = current_Saturation - day_down_Saturation;
            }
            else
            {
                this.current_Saturation = max_Saturation - day_down_Saturation;
            }
        }
        @Override
        public String toString() {
            return "Sheep{" +
                    " # " + count +
                    '}';
        }

        double weight = 70;
        double current_Saturation;
        double day_down_Saturation = 5;
        double max_Saturation = 15;
        boolean isDead;
        public Sheep() {
            name = "Sheep";
            this.isDead = false;
            this.multiplay = false;
            this.isMove = false;
            current_Saturation = max_Saturation;
            count = AnimalInfo.COUNTER_SHEEP.incrementAndGet();
        }
        int count;
    }
    class Hog extends Herbivorous_Animal
    {
        String name;
        public String getName() {
            return name;
        }
        private boolean isMove;
        @Override
        public boolean isMove() {
            return isMove;
        }
        @Override
        public void setMove(boolean move) {
            isMove = move;
        }
        private boolean multiplay;

        @Override
        void multiplay(boolean multiplay) {
            this.multiplay = multiplay;
        }

        @Override
        boolean isMultiplay() {
            return this.multiplay;
        }

        public boolean isDead() {
            return isDead;
        }

        public void setDead(boolean dead) {
            isDead = dead;
        }
        public double getCurrent_Saturation() {
            return current_Saturation;
        }
        @Override
        public void eat()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = max_Saturation;
            }
        }

        @Override
        public void hungry()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = current_Saturation - day_down_Saturation;
            }
            else
            {
                this.current_Saturation = max_Saturation - day_down_Saturation;
            }
        }
        @Override
        public String toString() {
            return "Hog{" +
                    " # " + count +
                    '}';
        }

        double weight = 400;
        double current_Saturation;
        double day_down_Saturation = 12.5;
        double max_Saturation = 50;
        boolean isDead;

        public Hog() {
            name = "Hog";
            this.isDead = false;
            this.multiplay = false;
            this.isMove = false;
            current_Saturation = max_Saturation;
            count = AnimalInfo.COUNTER_HOG.incrementAndGet();
        }
        int count;
    }
    class Buffalo extends Herbivorous_Animal
    {
        String name;
        public String getName() {
            return name;
        }
        private boolean isMove;
        @Override
        public boolean isMove() {
            return isMove;
        }
        @Override
        public void setMove(boolean move) {
            isMove = move;
        }
        private boolean multiplay;

        @Override
        void multiplay(boolean multiplay) {
            this.multiplay = multiplay;
        }

        @Override
        boolean isMultiplay() {
            return this.multiplay;
        }

        public boolean isDead() {
            return isDead;
        }

        public void setDead(boolean dead) {
            isDead = dead;
        }

        public double getCurrent_Saturation() {
            return current_Saturation;
        }
        @Override
        public void eat()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = max_Saturation;
            }
        }

        @Override
        public void hungry()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = current_Saturation - day_down_Saturation;
            }
            else
            {
                this.current_Saturation = max_Saturation - day_down_Saturation;
            }
        }
        @Override
        public String toString() {
            return "Buffalo{" +
                    " # " + count +
                    '}';
        }

        public Buffalo() {
            name = "Buffalo";
            this.isDead = false;
            this.multiplay = false;
            this.isMove = false;
            current_Saturation = max_Saturation;
              count = AnimalInfo.COUNTER_BUFFALO.incrementAndGet();
        }
        int count;
        boolean isDead;
        double weight = 700;
        double current_Saturation;
        double day_down_Saturation = 25;
        double max_Saturation = 100;

    }
    class Duck extends Herbivorous_Animal
    {
        String name;
        public String getName() {
            return name;
        }

        private boolean isMove;
        @Override
        public boolean isMove() {
            return isMove;
        }
        @Override
        public void setMove(boolean move) {
            isMove = move;
        }
        private boolean multiplay;

        @Override
        void multiplay(boolean multiplay) {
            this.multiplay = multiplay;
        }

        @Override
        boolean isMultiplay() {
            return this.multiplay;
        }

        public boolean isDead() {
            return isDead;
        }

        public void setDead(boolean dead) {
            isDead = dead;
        }


        @Override
        public void eat()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = max_Saturation;
            }
        }

        @Override
        public void hungry()
        {
            if(this.current_Saturation != max_Saturation)
            {
                this.current_Saturation = current_Saturation - day_down_Saturation;
            }
            else
            {
                this.current_Saturation = max_Saturation - day_down_Saturation;
            }
        }
        @Override
        public String toString() {
            return "Duck{" +
                    " # " + count +
                    '}';
        }

        double weight = 1;
        double max_Saturation = 0.15;
        double current_Saturation;
        double day_down_Saturation = 0.05;
        boolean isDead;
        int count;
        public Duck()
        {
            name = "Duck";
            this.isDead = false;
            this.multiplay = false;
            this.isMove = false;
            current_Saturation = max_Saturation;
            count = AnimalInfo.COUNTER_DUCK.incrementAndGet();
        }

        public double getMax_Saturation() {
            return max_Saturation;
        }

        public double getCurrent_Saturation() {
            return current_Saturation;
        }

        public void setCurrent_Saturation(double current_Saturation) {
            this.current_Saturation = current_Saturation;
        }
    }
    class Caterpillar extends Herbivorous_Animal
    {
        String name;
        public String getName() {
            return name;
        }

        private boolean multiplay;

        @Override
        void multiplay(boolean multiplay) {
            this.multiplay = multiplay;
        }

        @Override
        boolean isMultiplay() {
            return this.multiplay;
        }

        public boolean isDead() {
            return this.isDead;
        }



        @Override
        public void setDead(boolean dead) {
            this.isDead = dead;
        }

        @Override
        public void eat()
        {

        }

        @Override
        public void hungry()
        {

        }
        double weight = 0.01;
        int count;
        boolean isDead;
        public Caterpillar() {
            name = "Caterpillar";
            this.isDead = false;
            this.multiplay = false;
            count = AnimalInfo.COUNTER_CATERPILLAR.incrementAndGet();
        }


        @Override
        public String toString() {
            return "Caterpillar{" +
                    " # " + count +
                    '}';
        }
    }
}
class AnimalNameComparator implements Comparator<LivingEntity> // класс для сравнения отдельных экземпляров по имени класса
{
    @Override
    public int compare(LivingEntity o1, LivingEntity o2)
    {
        int toStringCompare = o1.getName().compareTo(o2.getName());
        return  toStringCompare;
    }
}
class AnimalCountComparator implements Comparator<LivingEntity> // класс для сравнения отдельных экземпляров по порядковому номеру животного
{
    @Override
    public int compare(LivingEntity o1, LivingEntity o2)
    {
        return  Integer.compare(o1.count, o2.count);
    }
}
