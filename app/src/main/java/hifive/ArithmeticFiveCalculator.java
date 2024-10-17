package hifive;

import ch.aplu.jcardgame.Card;

import java.util.List;

/**
 * Abstract ArithmeticFiveCalculator class to calculate the score
 * for a hand of two cards based on their ranks and suits
 * Utilised decorator pattern
 *
 * @ Author: Miles Li; Skylar Khant; Lam Nguyen
 * @ Since: 2024-10-01
 */
public abstract class ArithmeticFiveCalculator implements ScoreCalculator
{

    /**
     * Calculates the score based on the rank values of two cards in the hand and their suits
     *
     * @param privateCards : the list of private cards in the player's hand
     * @return the calculated score if the goal is reached using the rank values or alternative
     *         values of wildcards, or 0 if no valid combination is found.
     */
    private CardDecoratorFactory cdf;

    public ArithmeticFiveCalculator()
    {
        cdf = new CardDecoratorFactory();
    }
    @Override
    public int calculateScore(List<Card> privateCards)
    {
        Card card1 = privateCards.get(0);
        Card card2 = privateCards.get(1);

        Rank rank1 = (Rank) card1.getRank();
        Rank rank2 = (Rank) card2.getRank();

        CardDecorator cd1 = cdf.getWildCardDecorator(rank1, card1);
        CardDecorator cd2 = cdf.getWildCardDecorator(rank2, card2);

        // Use the operation method (to be implemented by subclasses) for the core logic
//        if (cd1 == null && cd2 == null
//                && checkGoalReached(applyOperation(rank1.getRankCardValue(), rank2.getRankCardValue())))
//        {
//            return calculateScoreWithSuits(card1, card2);
//        }

        if (checkGoalReached(applyOperation(rank1.getRankCardValue(), rank2.getRankCardValue()))
                || checkAlternativeValues(cd1, rank2.getRankCardValue())
                || checkAlternativeValues(cd2, rank1.getRankCardValue())
                || checkBothWildCardCombinations(cd1, cd2))
        {
            return calculateScoreWithSuits(card1, card2);
        }

        return 0;
    }

    /**
     * Abstract method to be implemented by subclasses to apply the desired operation
     *
     * @param value1: first card's value
     * @param value2: second card's value
     * @return int: the result of the operation on the two values
     */
    // Abstract method to be implemented by subclasses for sum or difference
    protected abstract int applyOperation(int value1, int value2);

    /**
     * Checks if the result of the operation matches the goal value
     *
     * @param result: the result of the arithmetic operation
     * @return boolean: true if the result matches the goal; false otherwise
     */
    // Method to check if the operation result matches the goal (can be sum or difference)
    private boolean checkGoalReached(int result) 
    {
        return result == HiFive.FIVE_GOAL;
    }

    /**
     * Getter
     */
    protected abstract int getPoints();

    /**
     * Calculates the score by adding the points that match the rank values and
     * the bonus factors for the suits of the two cards.
     *
     * @param card1: the first card
     * @param card2: the second card
     * @return int: total score
     */
    // Helper methods remain common to both subclasses
    private int calculateScoreWithSuits(Card card1, Card card2)
    {
        Suit suit1 = (Suit) card1.getSuit();
        Suit suit2 = (Suit) card2.getSuit();
        return getPoints() + suit1.getBonusFactor() + suit2.getBonusFactor();
    }



    /**
     * Checks if using the alternative values got by the wildcard decorator
     * and the fixed value of the second card can achieve the score goal.
     *
     * @param cd: the wildcard decorator
     * @param fixedCardValue: the rank value of the second card
     * @return boolean: true if the score goal is reached using an alternative value from the wildcard;
     *                  false otherwise.
     */
    private boolean checkAlternativeValues(CardDecorator cd, int fixedCardValue)
    {
        if (cd != null)
        {
            for (int altValue : cd.getAltValues())
            {
                if (checkGoalReached(applyOperation(altValue, fixedCardValue)))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the combination of alternative values from two wildcard card can achieve the score goal
     *
     * @param cd1: the wildcard decorator for the first card
     * @param cd2 the wildcard decorator for the second card
     * @return boolean: true if any combination of alternative values from the two wildcards reaches the goal;
     *                  false otherwise
     */
    private boolean checkBothWildCardCombinations(CardDecorator cd1, CardDecorator cd2)
    {
        if (cd1 != null && cd2 != null)
        {
            for (int val1 : cd1.getAltValues())
            {
                for (int val2 : cd2.getAltValues())
                {
                    if (checkGoalReached(applyOperation(val1, val2)))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
