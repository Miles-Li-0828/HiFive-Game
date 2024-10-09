package hifive;

public class DifferenceFiveCalculator extends ArithmeticFiveCalculator {

    @Override
    protected int applyOperation(int value1, int value2) {
        return Math.abs(value1 - value2);
    }

    @Override
    protected int getPoints() {
        return HiFive.DIFFERENCE_FIVE_POINTS;
    }
}
