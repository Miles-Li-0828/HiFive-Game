package hifive;

import ch.aplu.jcardgame.Card;

import java.util.List;

public abstract class ArithmeticFiveCalculator implements ScoreCalculator
{

    @Override
    public int calculateScore(List<Card> privateCards)
    {
        Card card1 = privateCards.get(0);
        Card card2 = privateCards.get(1);

        Rank rank1 = (Rank) card1.getRank();
        Rank rank2 = (Rank) card2.getRank();

        // Use the operation method (to be implemented by subclasses) for the core logic
        if (checkGoalReached(applyOperation(rank1.getRankCardValue(), rank2.getRankCardValue())))
        {
            return calculateScoreWithSuits(card1, card2);
        }

        CardDecorator cd1 = getWildCardDecorator(rank1, card1);
        CardDecorator cd2 = getWildCardDecorator(rank2, card2);

        if (checkAlternativeValues(cd1, rank2.getRankCardValue())
                || checkAlternativeValues(cd2, rank1.getRankCardValue())
                || checkBothWildCardCombinations(cd1, cd2))
        {
            return calculateScoreWithSuits(card1, card2);
        }

        return 0;
    }

    // Abstract method to be implemented by subclasses for sum or difference
    protected abstract int applyOperation(int value1, int value2);

    // Method to check if the operation result matches the goal (can be sum or difference)
    private boolean checkGoalReached(int result) {
        return result == HiFive.FIVE_GOAL;
    }

    protected abstract int getPoints();

    // Helper methods remain common to both subclasses
    private int calculateScoreWithSuits(Card card1, Card card2)
    {
        Suit suit1 = (Suit) card1.getSuit();
        Suit suit2 = (Suit) card2.getSuit();
        return getPoints() + suit1.getBonusFactor() + suit2.getBonusFactor();
    }

    private CardDecorator getWildCardDecorator(Rank rank, Card card)
    {
        switch (rank)
        {
            case ACE:
                return new WildCardA(card);
            case JACK:
                return new WildCardJ(card);
            case QUEEN:
                return new WildCardQ(card);
            case KING:
                return new WildCardK(card);
            default:
                return null;
        }
    }

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
