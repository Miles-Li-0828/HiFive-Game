package hifive;

/**
 * DifferenceFiveCalculator class to calculate the different
 *
 * @version 1.0
 * @since 2024-10-01
 * @author Miles Li, Skylar Khant, Ngoc Thanh Lam Nguyen
 */
public class DifferenceFiveCalculator extends ArithmeticFiveCalculator 
{

    /**
     * Calculate the difference between two card values
     *
     * @param value1: the first card's rank
     * @param value2: the second card's rank
     * @return int: the difference between the two card value
     */
    @Override
    protected int applyOperation(int value1, int value2) 
    {
        return Math.abs(value1 - value2);
    }

    /**
     * getter
     *
     * @return int: the point value
     */
    @Override
    protected int getPoints() 
    {
        return HiFive.DIFFERENCE_FIVE_POINTS;
    }
}
