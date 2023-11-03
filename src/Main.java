import java.util.*;
/* Теоритические расчёты:
* После первой попытки выстрелить стало ясно, что первое гнездо пустует.
* Таким образом, остается четыре варианта расположения патронов (П):
* 1) xППххх
* 2) ххППхх
* 3) хххППх
* 4) ххххПП
* Из этих четырех вариантов лишь один является смертельным — вариант номер 1.
* То есть вероятность погибнуть при таком раскладе — 1 из 4 (1/4) == 0,25. (вероятность выжить 1 - 0,25 == 0,75)
*
* Если же мы выбираем прокрутить барабан, то колличество вариантов расположения увеличивается до 6:
* 1) xППххх
* 2) ххППхх
* 3) хххППх
* 4) ххххПП
* 5) ПхххxП
* 6) ППхххх
* Из этих шести вариантов два являются смертельными — вариант номер 5 и вариант номер 6.
* То есть вероятность погибнуть при таком раскладе — 2 из 6 (2/6) == 1 из 3 (1/3) == 0,3(3). (вероятность выжить 1 - 0,3(3) == 0,6(6))
*
* Вывод:
* 1/3 > 1/4, следовательно вероятность погибруть больше если прокрутить барабан,
* Если же сразу стрелять - больше вероятность выжить.
*
* Ответ: Лучше нажать на спусковой крючёк сразу.
*/

public class Main {

    public static void main(String[] args) {

        int experimentsCount = 0;
        int firstPlayerWinCount = 0, secondPlayerWinCountWithoutRot = 0, secondPlayerWinCountWithRot = 0;
        float firstPlayerWinPercent, secondPlayerWinPercentWithoutRot, secondPlayerWinPercentWithRot;

        //Ввод и валидация колличества эксперементов
        System.out.println("\nВведите колличество эксперементов");

        boolean successInput = false;
        while (!successInput) {
            Scanner scanner = new Scanner(System.in);
            try {
                experimentsCount = scanner.nextInt();
            } catch (NoSuchElementException | IllegalStateException  e) {
                System.out.println("\nОшибка ввода: колличество эксперементов должно быть целым числом. Попробуйте еще раз");
                continue;
            }

            if (experimentsCount <= 0) {
                System.out.println("\nОшибка ввода: колличество эксперементов должно быть целым положительным числом. Попробуйте еще раз");
            } else {
                successInput = true;
            }
        }

        //Подготовим барабан револьвера и комнаты для эксперементов
        RevolverDrum revolverDrum = new RevolverDrum(6);
        ExperimentRoom firstExperimentRoom = new ExperimentRoom(revolverDrum, experimentsCount / 2, StrategiesForSecondPlayer.StrategyWithRot);
        ExperimentRoom secondExperimentRoom = new ExperimentRoom(revolverDrum, experimentsCount / 2, StrategiesForSecondPlayer.StrategyWithoutRot);


        //Пусть первую половину эксперементов второй игрок выбирает стратегию прокрутить барабан
        firstExperimentRoom.doExperiment();

        //Соберем данные
        firstPlayerWinCount += firstExperimentRoom.getFirstPlayerWinCount();
        secondPlayerWinCountWithRot = firstExperimentRoom.getSecondPlayerWinCountWithRot();

        //Пусть вторую половину эксперементов второй игрок выбирает стратегию сразу стрелять
        secondExperimentRoom.doExperiment();

        //Соберем данные
        firstPlayerWinCount += secondExperimentRoom.getFirstPlayerWinCount();
        secondPlayerWinCountWithoutRot = secondExperimentRoom.getSecondPlayerWinCountWithoutRot();


        //Подсчет процентов успешных исходов для игроков
        int secondPlayerExperimentsCount = experimentsCount / 2;
        firstPlayerWinPercent = ((float) firstPlayerWinCount / experimentsCount) * 100;
        secondPlayerWinPercentWithRot = ((float) secondPlayerWinCountWithRot / secondPlayerExperimentsCount) * 100;
        secondPlayerWinPercentWithoutRot = ((float) secondPlayerWinCountWithoutRot / secondPlayerExperimentsCount) * 100;


        //Вывод
        System.out.println("\n кол-во экспериментов: " + experimentsCount);
        System.out.println("\n кол-во успешных исходов 1-го игрока: " + firstPlayerWinCount
                + ", процент успешных исходов 1-го игрока: " + firstPlayerWinPercent);
        System.out.println("\n кол-во успешных исходов 2-го игрока для случая \"крутить\": " + secondPlayerWinCountWithRot
                + ", процент успешных исходов 2-го игрока для случая \"крутить\": " + secondPlayerWinPercentWithRot);
        System.out.println("\n кол-во успешных исходов 2-го игрока для случая \"нажать\": " + secondPlayerWinCountWithoutRot
                + ", процент успешных исходов 2-го игрока для случая \"нажать\": " + secondPlayerWinPercentWithoutRot);


    }
}