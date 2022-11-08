package Test_Examples_2;



import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnimalBehavior // имитация поведения животных, таких как питание, размножение, передвижение и смерть
{
    private static String plant = "Plant";
    private static String caterpillar = "Caterpillar";
    private static synchronized boolean readyOrNot() // метод для получения случайного выбора да/нет для определения дальнейшей логики поведения животного
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
    public static void eatAnimalBehavior(List<LivingEntity> list) // метод, имитирующий процесс питания животных
    {
        int plantCount = AnimalBehavior.returnCountOfAnimal(countOfAnimals(list), plant); // начальное количество травы
        int caterpillarCount = AnimalBehavior.returnCountOfAnimal(countOfAnimals(list), caterpillar); // начальное количество гусениц

        /*
            Начинается общий перебор всех элементов списка
        */
        for (int i = list.size() - 1; i >= 0; i--)
        {

            /*
            вызывается уменьшение насыщенности
             */
            if (list.get(i) instanceof Animals.Herbivorous_Animal || list.get(i) instanceof Animals.Predatory_Animal) // если текущее животное является хищником или травоядным
            {
                if (!(list.get(i) instanceof Animals.Caterpillar)) // если текущее животное не является гусеницей
                {
                    ((Animals) list.get(i)).hungry(); // у животного уменьшается насыщение
                }
            }
            /*
            Травоядные животные кушают
             */
            if (list.get(i) instanceof Animals.Herbivorous_Animal)
            {
                if (!(list.get(i) instanceof Animals.Duck))
                {
                    if (!(list.get(i) instanceof Animals.Caterpillar))

                        if (plantCount > 0)
                        {
                            ((Animals.Herbivorous_Animal) list.get(i)).eat();// выбранное из основного списка животное поело траву
                            markedForDeathInCopyList(list, Animals.Plant.class.getSimpleName());
                            plantCount--;
                        }
                }
                else if (list.get(i) instanceof Animals.Duck) // в данной версии утки едят гусениц
                {

                    if (caterpillarCount > 0)
                    {
                        ((Animals.Herbivorous_Animal) list.get(i)).eat();
                        markedForDeathInCopyList(list, Animals.Caterpillar.class.getSimpleName());
                        caterpillarCount--;
                    }

                }
            }
            /*
            Хищные животные кушают
             */
            else if((list.get(i) instanceof Animals.Predatory_Animal))
            {
                String predatorType = list.get(i).getClass().getSimpleName(); // получаем тип животного-хищника
                List<String> listOfAllAnimalTypes = Factory.getAnimalTypeClass(); // список всех возможных типов животных в локации
                List<String> listOfVictims = AnimalInfo.getListOfVictims(predatorType); // список всех типов животных, которые ест данный хищник

                boolean isTrueVictim = true; // условие съедения жертвы хищником
                while (isTrueVictim) // пока жертва не съедена
                {
                    String victim = AnimalBehavior.getVictim(listOfAllAnimalTypes, listOfVictims); // получаем тип животного-жертвы
                    int countOfAliveVictims = AnimalBehavior.countOfAliveVictims(list, victim); // получаем общее количество потенциальных жертв данного вида животных
                    if(countOfAliveVictims > 0) // если количество потенциальных жертв данного типа животных больше 0
                    {
                        int probability = AnimalInfo.getProbability(predatorType, victim); // получаем вероятность, с которой хищник может съесть жертву
                        Random random = new Random(); // вызывается случайное число для имитации охоты хищника на жертву
                        boolean eat = random.nextInt(0, 100) <= probability;// условие вероятность поедания хищником жертвы больше, либо равна выпавшей вероятности
                        if(eat) // то хищник кушает жертву
                        {
                            markedForDeathInCopyList(list, victim);
                            ((Animals.Predatory_Animal) list.get(i)).eat(); // животное-хищник повышает насыщенность
                            isTrueVictim = false; // жертва съедена
                        }
                        else // иначе хищник остается голодным
                        {
                            break; // выход из цикла, если вероятность поедания жертвы оказалась меньше, чем нужно
                        }
                    }
                    else // если количество потенциальных жертв данного типа животных равно 0
                    {
                        if(listOfVictims.size() == 0) // если в списке еще есть типы жертв животных
                        {
                            listOfVictims.remove(victim); // удаляем тип потенциальной жертвы-животного из списка жертв, у которого нет доступных для съедения экземпляров
                        }
                        else
                        {
                            break; // если в списке потенциальных жертв никого нет, выходим из цикла. Хищник остается голодным
                        }
                    }
                }
}
}

}

    public static void multiPlayAnimalBehavior(List<LivingEntity> list, List<LivingEntity> newBorn) // метод, имитирующий процесс размножения животных
    {
    for (int i = 0; i < list.size(); i++)
    {
        String animalType;
        int animalMaxCount;
        int animalCurrentCount;
        int animalCurrentCountInNewBornList;
        if(!(list.get(i) instanceof Animals.Plant))
        {
            animalType = list.get(i).getClass().getSimpleName(); // получение типа животного для поиска пары
            animalMaxCount = AnimalInfo.getMaxAnimalCount(animalType); // максимальное число животных данного вида в локации
            animalCurrentCount = AnimalBehavior.returnCountOfAnimal(AnimalBehavior.countOfAnimals(list), animalType); // текущее число животных данного вида в основном листе
            animalCurrentCountInNewBornList = AnimalBehavior.returnCountOfAnimal(AnimalBehavior.countOfAnimals(newBorn), animalType); // текущее число животных данного вида в листе новорожденных
            if (!(list.get(i).isMultiplay())) // если животное еще не размножалось
                if (animalCurrentCount > 1) // если у животного есть пара
                {
                    for (int j = i + 1; j < list.size(); j++)
                    {
                        if(list.get(i).getClass().getSimpleName().equals(list.get(j).getClass().getSimpleName())) // если текущее и последующее животное-партнер одного вида
                        {
                            list.get(i).multiplay(true); // текущее выбранное животное размножается
                            list.get(j).multiplay(true); // текущее выбранное животное-партнер размножается
                            if ((animalCurrentCount + animalCurrentCountInNewBornList) < animalMaxCount) // условие создания нового экземпляра животного
                            {
                                Random random = new Random(); // вызывается случайное число для имитации рождения нового животного
                                int bornProbability = AnimalInfo.getProbabilityBornValue(animalType); // получаем вероятность рождения для данного вида животного
                                boolean born = random.nextInt(0, 100) <= bornProbability;// условие: вероятность рождения нового животного больше, либо равна выпавшей вероятности
                                if(born) // то появляется новый экземпляр данного вида
                                {
                                    newBorn.add(Factory.animalFactory(animalType)); // в список новорожденных добавляется новый экземпляр
                                    animalCurrentCountInNewBornList++; // счетчик новорожденных увеличивается
                                }
                            }
                            break;
                        }
                    }
                }
        }
    }
    }
    public static synchronized void resetMultiPlayState(List<LivingEntity> listCurrentLocation) // метод для сброса состояния в false у животного, размножавшегося на предыдущем этапе
    {
        for (int i = 0; i < listCurrentLocation.size(); i++)
        {
            if (listCurrentLocation.get(i) instanceof Animals.Herbivorous_Animal || listCurrentLocation.get(i) instanceof Animals.Predatory_Animal) // если текущее животное является хищником или травоядным
                if (!(listCurrentLocation.get(i) instanceof Animals.Caterpillar)) // если текущее животное не является гусеницей
                {
                    listCurrentLocation.get(i).multiplay(false); // в начале каждого дня животное не размножалось
                }

        }
    }
    public static synchronized void resetMoveState(List<LivingEntity> listCurrentLocation) // метод для сброса состояния в false у животного, ходившего на предыдущем этапе
    {
        for (int i = 0; i < listCurrentLocation.size(); i++)
        {
            if (listCurrentLocation.get(i) instanceof Animals.Herbivorous_Animal || listCurrentLocation.get(i) instanceof Animals.Predatory_Animal) // если текущее животное является хищником или травоядным
                if (!(listCurrentLocation.get(i) instanceof Animals.Caterpillar)) // если текущее животное не является гусеницей
                {
                    listCurrentLocation.get(i).setMove(false); // в начале каждого дня животное никуда не передвигалось
                }

        }
    }
    public static synchronized void moveAnimalBehavior(List<LivingEntity> listCurrentLocation, Location[][] island, int dimesionX, int dimensionY) // метод, имитирующий процесс передвижения животных
    {
        List<LivingEntity> copyToRelocate = new CopyOnWriteArrayList<>(); // список-копия, куда будут заноситься животные, которые перемещаются из текущей локации

        for (int k = listCurrentLocation.size() - 1; k >=0 ; k--)  // цикл по основному списку животных
        {

            if (listCurrentLocation.get(k) instanceof Animals.Herbivorous_Animal || listCurrentLocation.get(k) instanceof Animals.Predatory_Animal) // если текущее животное является хищником или травоядным
                if (!(listCurrentLocation.get(k) instanceof Animals.Caterpillar)) // если текущее животное не является гусеницей
                {
//                    boolean isMove = listCurrentLocation.get(k).isMove();
//                    System.out.println("Животное " + listCurrentLocation.get(k).getClass().getSimpleName() + " ходило? " + isMove);
                    if (!(listCurrentLocation.get(k).isMove()) && (readyOrNot())) // если животное решило передвигаться и если оно еще не передвигалось
                    {
                        String animalType = listCurrentLocation.get(k).getClass().getSimpleName(); // получаем тип животного
                        int moveValue = AnimalInfo.getMoveRangeValue(animalType); // получаем дальность передвижения животного
                        Location currentLocation = island[dimesionX][dimensionY]; // текущая локация для дальнейшей работы метода
                        List<Location> locationAround = Island.listOfLocationAround(island, currentLocation, moveValue); // получаем список, куда может переместиться выбранное животное
                        List<LivingEntity> locationToRelocate = Island.chooseOfLocationToRelocate(locationAround, animalType);// выбираем список для перемещения туда выбранного на текущей итерации животного
                        if(locationToRelocate != null)
                        {
                            listCurrentLocation.get(k).setMove(true); // на текущей итерации животному ставится флаг - передвижение выполнено, чтобы оно не передвигалось при обходе других списков
                            locationToRelocate.add(listCurrentLocation.get(k)); // перемещаем текущее животное в выбранный для перемещения список
                            copyToRelocate.add(listCurrentLocation.get(k)); // заносим выбранное животное в список на удаление из текущего итерируемого списка
                        }

                    }
                }
        }
        relocateFromList(listCurrentLocation, copyToRelocate); // после завершения цикла по выбранному листу из него удаляются животные, отмеченные флагом, как перешедшие
        Collections.sort(listCurrentLocation,new AnimalNameComparator().thenComparing(new AnimalCountComparator()));
    }

//    public static synchronized void removeFromList(List<LivingEntity> list) // метод, удаляющий из списка элементы, отмеченные как съеденые или умершие
//    {
//        Iterator<LivingEntity> iteratorList = list.iterator();
//
//        while (iteratorList.hasNext())
//        {
//            if(iteratorList.next().isDead())
//            {
//                iteratorList.remove();
//            }
//        }
//    }
    public static synchronized void removeFromList(List<LivingEntity> list)
    {
        for (int i = list.size() -1; i >=0 ; i--)
        {
            if(list.get(i).isDead())
            {
                list.remove(i);
            }
        }
    }
    public static void relocateFromList(List<LivingEntity> list, List<LivingEntity> copyList) // метод удаляет из основного списка животных, помеченных на перемещение в списке-копии
    {
        for (int i = copyList.size()-1; i >= 0; i--)
        {
            if(copyList.get(i) instanceof LivingEntity)
                if((copyList.get(i)).isMove())
                {
                    list.remove(i);
                }
        }
    }

    private static void markedForDeathInCopyList(List<LivingEntity> copyList, String animalType) // метод отмечает съеденное живое существо флагом для последующего его удаления
    {

        List<String> animalsTypes = Factory.getAnimalTypeClass(); // получаем список классов для сравнения
        if(animalsTypes.contains(animalType)) // условие сравнения класса животного, переданного в метод
        for (int i = 0; i < copyList.size(); i++)
        {
            if(copyList.get(i).getClass().getSimpleName().equals(animalType))  // условие сравнения текущего экземпляра из коллекци с переданным в метод типом животного
            {

                    if(!(copyList.get(i).isDead())) // если текущий флаг животного в положении false
                    {
                        copyList.get(i).setDead(true); // устанавливается флаг в true
                        break;
                   }
            }
        }
    }
    private static void markedForMoveInCopyList(List<LivingEntity> relocateList, String animalType) // метод отмечает перемещаюеся живое существо флагом для последующего его удаления из текущего списка
    {
        List<String> animalsTypes = Factory.getAnimalTypeClass(); // получаем список классов для сравнения
        if(animalsTypes.contains(animalType)) // условие сравнения класса животного, переданного в метод
            for (int i = 0; i < relocateList.size(); i++)
            {
                if(relocateList.get(i).getClass().getSimpleName().equals(animalType))  // условие сравнения текущего экземпляра из коллекци с переданным в метод типом животного
                {

                    if(!(relocateList.get(i).isMove())) // если текущий флаг животного в положении false
                    {
                        relocateList.get(i).setMove(true); // устанавливается флаг в true
                        break;
                    }
                }
            }
    }
    public static Map<String, Integer> countOfAnimals(List<LivingEntity> list) // метод, подсчитывающий количество животных данного вида в текущей локации, возвращает коллекцию типа Map
    {
        Map<String, Integer> mapOfAnimalsCount = new ConcurrentHashMap<>();

        String animalType;
        for (int i = 0; i < list.size(); i++)
        {
            int count = 0;
            animalType = list.get(i).getClass().getSimpleName();
            for (int j = 0; j < list.size(); j++)
            {
                if(animalType.equals(list.get(j).getClass().getSimpleName()))
                {
                    count++;
                }
            }
            mapOfAnimalsCount.put(animalType, count);
        }
        return mapOfAnimalsCount;
    }

    public static int returnCountOfAnimal(Map<String, Integer> mapOfAnimalCount, String animalType) // метод, возвращающий количество животных конкретного вида
    {
            int animalCount = 0;
            if(mapOfAnimalCount.containsKey(animalType))
            {

                animalCount = mapOfAnimalCount.get(animalType);
            }
            return animalCount;
        }

    private static String getVictim(List<String> allAnimals, List<String> victimAnimals) // метод, возвращающий из списков, входящий в аргументы метода, тот тип животного, которое попробует съесть хищник
    {
            String victim;
            allAnimals.retainAll(victimAnimals);
            Random random = new Random();
            int index = random.nextInt(0, allAnimals.size());
            return victim = allAnimals.get(index);
    }

    public static int countOfAliveVictims(List<LivingEntity> copyList, String victim) // метод, возвращающий количество несъеденных экземпляров данного типа животного
    {
    int countOfVictims = 0;
    for (int i = 0; i < copyList.size(); i++)
    {
        if(copyList.get(i).getClass().getSimpleName().equals(victim))
            if(!(copyList.get(i).isDead()))
            {
                countOfVictims++;
            }
    }
    return countOfVictims;
    }

    public static void hungryDeath(List<LivingEntity> list, List<LivingEntity> copyList) // метод, заносящий в список для удаления то животное, насыщение которого меньше, либо равно 0, как умершее от голода
    {
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i) instanceof Animals.Herbivorous_Animal || list.get(i) instanceof Animals.Predatory_Animal) // если текущее животное является хищником или травоядным
                if (!(list.get(i) instanceof Animals.Caterpillar)) // если текущее животное не является гусеницей
                {
                    double saturation = ((Animals) list.get(i)).getCurrent_Saturation(); // получаем текущее насыщение животного
                    if(saturation > 0) // если у животного насыщение больше 0
                    {
                        continue;
                    }
                    else if (saturation <= 0) // если у животного насыщение меньше, либо равно 0
                    {
                        markedForDeathInCopyList(copyList, list.get(i).getClass().getSimpleName()); // животное заносится в список для удаления как умершее от голода
                    }
                }
        }
    }

    public static void setPlant(List<LivingEntity> listCurrentLocation) // метод добавляет траву в текущую локацию
    {
        System.out.println("Добавление травы на локацию на следующий ход");
        List<LivingEntity> plantAdded = Factory.plantAddedList();
        int addedPlantCount = plantAdded.size();
        int plantOnCurrentLocation = AnimalBehavior.countOfAliveVictims(listCurrentLocation, plant);
        int maxPlantOnLocation = AnimalInfo.getMaxAnimalCount(plant);
        if((addedPlantCount + plantOnCurrentLocation) <= maxPlantOnLocation) // условие добавления на текущую локацию травы
        {

            listCurrentLocation.addAll(plantAdded);
        }
        else {
            for (int k = 0; k < (maxPlantOnLocation - plantOnCurrentLocation); k++)
            {
                listCurrentLocation.add(plantAdded.get(k));
            }
        }
    }
    public static void animalDayBehavior(List<LivingEntity> listCurrentLocation, Location[][] island, int i, int j)
    {
        List<LivingEntity> copyList = new CopyOnWriteArrayList<>();
        List<LivingEntity> newBorn = new CopyOnWriteArrayList<>();

        System.out.println("Список животных в начале дня " + listCurrentLocation);

                /*
                Проверка на голодную смерть
                 */
        System.out.println("Проверка на голодную смерть");
        AnimalBehavior.hungryDeath(listCurrentLocation, copyList);
        AnimalBehavior.removeFromList(listCurrentLocation);
        copyList.clear();
                /*
                Питание животных
                 */
        System.out.println("Животные кушают");
        AnimalBehavior.eatAnimalBehavior(listCurrentLocation);

        AnimalBehavior.removeFromList(listCurrentLocation);
        copyList.clear();
                /*
                Размножение животных
                 */
        System.out.println("Животные размножаются");
        AnimalBehavior.multiPlayAnimalBehavior(listCurrentLocation, newBorn);
                /*
                Передвижение животных
                 */
        System.out.println("Животные передвигаются");
        AnimalBehavior.moveAnimalBehavior(listCurrentLocation, island, i, j);

                /*
                Добавление травы на следующий ход
                 */

        setPlant(listCurrentLocation);
        Collections.sort(listCurrentLocation,new AnimalNameComparator().thenComparing(new AnimalCountComparator()));
        System.out.println("Список животных в конце дня " + listCurrentLocation);
        System.out.println("Локация с координатами x= " + i + " и y= " + j + " обработана");
    }



    public static void main(String[] args) {


        Location[][] island = Island.createIsland(3, 3); // создаем остров
        int dayCount = 3;
        for (int d = 0; d < dayCount; d++)
        {
            System.out.println();
            System.out.println("Начался " + d + " день жизни на острове");
            System.out.println();
            /*
            Цикл по всем локациям для сброса флага передвижений и размножений в начале каждого дня
             */

            System.out.println("сброс флага передвижений начался");
            for (int i = 0; i < island.length; i++)  // цикл по острову - массиву локаций, содержащих списки животных и растений
            {
                for (int j = 0; j < island[i].length; j++) // цикл по острову - массиву локаций, содержащих списки животных и растений
                {

                    List<LivingEntity> listCurrentLocation = island[i][j].getList();
                    AnimalBehavior.resetMoveState(listCurrentLocation);
                    AnimalBehavior.resetMultiPlayState(listCurrentLocation);

                }
            }
            System.out.println("сброс флага передвижений завершился");


            for (int i = 0; i < island.length; i++)  // цикл по острову - массиву локаций, содержащих списки животных и растений
            {
                for (int j = 0; j < island[i].length; j++) // цикл по острову - массиву локаций, содержащих списки животных и растений
                {

                    List<LivingEntity> listCurrentLocation = island[i][j].getList(); // на текущей итерации получаем из локации список с животными
//                    List<LivingEntity> copyList = new ArrayList<>();
//                    List<LivingEntity> newBorn = new ArrayList<>();
//
//                    System.out.println("Список животных в начале дня " + listCurrentLocation);
//
//                /*
//                Проверка на голодную смерть
//                 */
//                    System.out.println("Проверка на голодную смерть");
//                    AnimalBehavior.hungryDeath(listCurrentLocation, copyList);
//                    AnimalBehavior.removeFromList(listCurrentLocation);
//                    copyList.clear();
//                /*
//                Питание животных
//                 */
//                    System.out.println("Животные кушают");
//                    AnimalBehavior.eatAnimalBehavior(listCurrentLocation);
//
//                    AnimalBehavior.removeFromList(listCurrentLocation);
//                    copyList.clear();
//                /*
//                Размножение животных
//                 */
//                    System.out.println("Животные размножаются");
//                    AnimalBehavior.multiPlayAnimalBehavior(listCurrentLocation, newBorn);
//                /*
//                Передвижение животных
//                 */
//                    System.out.println("Животные передвигаются");
//                    AnimalBehavior.moveAnimalBehavior(listCurrentLocation, island, i, j);
//
//                /*
//                Добавление травы на следующий ход
//                 */
//
//                    setPlant(listCurrentLocation);
//                    Collections.sort(listCurrentLocation,new AnimalNameComparator().thenComparing(new AnimalCountComparator()));
//                    System.out.println("Список животных в конце дня " + listCurrentLocation);
//                    System.out.println("Локация с координатами x= " + i + " и y= " + j + " обработана");
                    AnimalBehavior.animalDayBehavior(listCurrentLocation, island, i, j);
                }

            }
            System.out.println();
            System.out.println("закончился " + d + " день жизни на острове");
            System.out.println();
        }
            System.out.println();
        }


}
