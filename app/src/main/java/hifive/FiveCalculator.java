package hifive;

import ch.aplu.jcardgame.Card;

import java.util.List;

import static hifive.HiFive.FIVE_GOAL;
import static hifive.HiFive.FIVE_POINTS;

/**
 * FiveCalculator class provides the logic to calculate a score
 * based on cards that have a rank value of five
 *
 * @version 1.0
 * @since 2024-10-01
 * @author Miles Li, Skylar Khant, Ngoc Thanh Lam Nguyen
 */
public class FiveCalculator implements ScoreCalculator
{

    /**
     * Calculates the score based on the player's cards
     * The score is only calculated if a card's rank value is equal to five
     *
     * @param privateCards: the list of private cards that player holds
     * @return int: maximum score calculated for any card with a rank of five; or 0 if no such cards are found
     */
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
