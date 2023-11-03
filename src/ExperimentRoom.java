public class ExperimentRoom {

    private final RevolverDrum revolverDrum;

    private final int experimentsCount;

    private int firstPlayerWinCount = 0;

    private int secondPlayerWinCountWithoutRot = 0;

    private int secondPlayerWinCountWithRot = 0;

    private final StrategiesForSecondPlayer strategy;


    public ExperimentRoom(RevolverDrum revolverDrum, int experimentsCount, StrategiesForSecondPlayer strategy) {
        this.revolverDrum = revolverDrum;
        this.experimentsCount = experimentsCount;
        this.strategy = strategy;
    }

    public void doExperiment() {

        if (strategy == StrategiesForSecondPlayer.StrategyWithRot) {

            for (int i = 0; i < experimentsCount; i++) {

                revolverDrum.chargeDrum(); //Заряжаем револьвер

                //Вращаем револьвер пока в текущей камере не окажется пустое место
                while (revolverDrum.getCurrentChamblerCondition()) {
                    revolverDrum.rotate();
                }

                //Первый игрок делает выстрел
                revolverDrum.cartridgePop(true);

                //Второй игрок прокручивает барабан
                revolverDrum.rotate();

                //Смотрим, будет ли выстрел если второй игрок нажмет на курок
                boolean shot = revolverDrum.getCurrentChamblerCondition();
                if (shot) {
                    firstPlayerWinCount++;
                } else {
                    secondPlayerWinCountWithRot++;
                }
            }

        } else if (strategy == StrategiesForSecondPlayer.StrategyWithoutRot) {

            for (int i = 0; i < experimentsCount; i++) {

                revolverDrum.chargeDrum();

                //Вращаем револьвер пока в текущей камере не окажется пустое место
                while (revolverDrum.getCurrentChamblerCondition()) {
                    revolverDrum.rotate();
                }

                //Первый игрок делает выстрел
                revolverDrum.cartridgePop(true);

                //Смотрим, будет ли выстрел если второй игрок нажмет на курок
                boolean shot = revolverDrum.getCurrentChamblerCondition();
                if (shot) {
                    firstPlayerWinCount++;
                } else {
                    secondPlayerWinCountWithoutRot++;
                }
            }

        } else {
            throw new RuntimeException("Эксперемента с такой стратегией еще не придумали(");
        }
    }

    public int getFirstPlayerWinCount() {
        return firstPlayerWinCount;
    }

    public int getSecondPlayerWinCountWithoutRot() {
        return secondPlayerWinCountWithoutRot;
    }

    public int getSecondPlayerWinCountWithRot() {
        return secondPlayerWinCountWithRot;
    }
}
