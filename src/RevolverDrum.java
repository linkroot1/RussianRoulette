import java.util.Arrays;

public class RevolverDrum {
    private final int size;

    private final boolean[] chambers;

    private boolean conditionCurrentChambler;

    private int indexCurrentChambler;


    public RevolverDrum(int size) {

        if (size < 1) {
            throw new RuntimeException("Размер барабана должен быть больше 1");
        }

        this.size = size;
        chambers = new boolean[this.size];
        Arrays.fill(chambers, false);
        currentChamblerStartPos();
    }

    public int getSize() {
        return size;
    }

    //Вращение барабана на 1 ячейку
    public void nextChamber() {
        if (indexCurrentChambler + 1 < size) {
            indexCurrentChambler++;
            conditionCurrentChambler = chambers[indexCurrentChambler];
        } else {
            indexCurrentChambler = 0;
            conditionCurrentChambler = chambers[indexCurrentChambler];
        }
    }

    //Поместить патрон в текущую ячейку
    public void cartridgePush(boolean condition) {
        chambers[indexCurrentChambler] = condition;
        conditionCurrentChambler = chambers[indexCurrentChambler];
        nextChamber(); //После того как зарядили патрон, прокручиваем барабан дальше
    }

    //Извлечь патрон в из текущей ячейки с прокручиванием барабана на 1 ячейку или без прокручивания
    public void cartridgePop(boolean rotation) {
        chambers[indexCurrentChambler] = false;
        conditionCurrentChambler = chambers[indexCurrentChambler];

        if (rotation) {
            nextChamber();
        }
    }

    //Прокручивание на произвольное колличество ячеек
    public void rotate() {
        //Колличество итераций - случайное число в диапазоне от 0 до size
        int count = (int) ( Math.random() * size);

        for (int i = 0; i < count; i++) {
            nextChamber();
        }
    }

    //Установка барабана в исходное положение
    public void currentChamblerStartPos() {
        indexCurrentChambler = 0;
        conditionCurrentChambler = chambers[indexCurrentChambler];
    }

    //Заряжаем барабан
    public void chargeDrum() {
        for (int i = 0; i < size - 2; i++) {
            cartridgePush(false);
        }
        for (int i = size - 2; i < size; i++) {
            cartridgePush(true);
        }

        rotate(); //Вращаем, чтобы текущая камера оказалась в случайном месте
    }

    //Получить состояние текущей камеры барабана
    public boolean getCurrentChamblerCondition() {
        return conditionCurrentChambler;
    }
}
