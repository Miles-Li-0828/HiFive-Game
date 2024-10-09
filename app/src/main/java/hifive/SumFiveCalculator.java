package hifive;

public class SumFiveCalculator extends ArithmeticFiveCalculator {

    @Override
    protected int applyOperation(int value1, int value2) {
        return value1 + value2;
    }

    @Override
    protected int getPoints() {
        return HiFive.SUM_FIVE_POINTS;
    }
}
