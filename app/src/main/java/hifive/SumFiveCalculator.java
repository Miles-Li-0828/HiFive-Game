package hifive;

/**
 * SumFiveCalculator class to calculate the sum
 *
 * @version 1.0
 * @since 2024-10-01
 * @author Miles Li, Kylar Khant, Ngoc Thanh Lam Nguyen
 */
public class SumFiveCalculator extends ArithmeticFiveCalculator {

    /**
     * Calculate the sum between two card values
     *
     * @param value1: the first card's rank
     * @param value2: the second card's rank
     * @return int: the sum between the two card value
     */
    @Override
    protected int applyOperation(int value1, int value2) {
        return value1 + value2;
    }

    /**
     * getter
     *
     * @return int: the point value
     */
    @Override
    protected int getPoints() {
        return HiFive.SUM_FIVE_POINTS;
    }
}
