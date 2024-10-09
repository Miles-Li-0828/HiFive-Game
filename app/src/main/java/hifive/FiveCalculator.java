package hifive;

import ch.aplu.jcardgame.Card;

import java.util.List;

import static hifive.HiFive.FIVE_GOAL;
import static hifive.HiFive.FIVE_POINTS;

public class FiveCalculator implements ScoreCalculator
{
    @Override
    public int calculateScore(List<Card> privateCards)
    {
        int maxScore = 0;
        for (Card card : privateCards)
        {
            Rank rank = (Rank) card.getRank();
            Suit suit = (Suit) card.getSuit();
            if (rank.getRankCardValue() == FIVE_GOAL)
            {
                int score = FIVE_POINTS + suit.getBonusFactor();
                if (score > maxScore)
                {
                    maxScore = score;
                }
            }
        }

        return maxScore;
    }
}
