package hifive;

import ch.aplu.jcardgame.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite pattern to calculate score
 *
 * @version 1.0
 * @since 2024-10-01
 * @author Miles Li, Kylar Khant, Ngoc Thanh Lam Nguyen
 */
public class CompositeCalculator implements ScoreCalculator
{
    private List<ScoreCalculator> calculators;
    public CompositeCalculator()
    {
        this.calculators = new ArrayList<ScoreCalculator>();
    }
    @Override
    public int calculateScore(List<Card> privateCards)
    {
        int maxScore = 0;
        for(ScoreCalculator cal: calculators)
        {
            maxScore = Math.max(maxScore, cal.calculateScore(privateCards));
        }
        return maxScore;
    }

    public void addCalculator(ScoreCalculator cal)
    {
        calculators.add(cal);
    }
    public void removeCalculator(ScoreCalculator cal)
    {
        calculators.remove(cal);
    }
}
