package hifive;

import ch.aplu.jcardgame.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement composite pattern to calculate score
 * It allows the combination of multiple ScoreCalculator objects
 *
 * @version 1.0
 * @since 2024-10-01
 * @author Miles Li, Skylar Khant, Ngoc Thanh Lam Nguyen
 */
public class CompositeCalculator implements ScoreCalculator
{
    private List<ScoreCalculator> calculators;

    /**
     * Constructor of CompositeCalculator
     */
    public CompositeCalculator()
    {
        this.calculators = new ArrayList<ScoreCalculator>();
    }

    /**
     * Calculates the maximum score
     *
     * @param privateCards: the list of cards used to calculate the score
     * @return int: the highest score
     */
    @Override
    public int calculateScore(List<Card> privateCards)
    {
        int maxScore = 0;
        for(ScoreCalculator cal: calculators)
        {
            maxScore = Math.max(maxScore, cal.calculateScore(privateCards));
            System.out.println(maxScore + "\t");
        }
        return maxScore;
    }

    /**
     * Calculates the highest score without NoFive calculator
     *
     * @param privateCards: the list of cards used to calculate the score
     * @return int: the highest score
     */
    public int calculateHighestScoreOnFive(List<Card> privateCards)
    {
        int bestScore = 0;
        for(int i = 0; i < calculators.size() - 1; i++)
        {
            bestScore = Math.max(bestScore, calculators.get(i).calculateScore(privateCards));
        }
        return bestScore;
    }

    /**
     * Adds a ScoreCalculator to the composite
     *
     * @param cal: the ScoreCalculator to be added
     */
    public void addCalculator(ScoreCalculator cal)
    {
        calculators.add(cal);
    }

    /**
     * Removes a ScoreCalculator from the composite
     *
     * @param cal: the ScoreCalculator to be removed
     */
    public void removeCalculator(ScoreCalculator cal)
    {
        calculators.remove(cal);
    }
}
