package hifive;

import ch.aplu.jcardgame.Card;

import java.util.List;

/**
 * NoFiveCalculator class to calculates the score for a player's cards without the five card
 *
 * @version 1.0
 * @since 2024-10-01
 * @author Miles Li, Kylar Khant, Ngoc Thanh Lam Nguyen
 */
public class NoFiveCalculator implements ScoreCalculator {
    /**
     * Calculates the score based on the cards' values
     *
     * @param privateCards: A list player's cards
     * @return int: The total score calculated
     */
    @Override
    public int calculateScore(List<Card> privateCards) {
        Card card1 = privateCards.get(0);
        Card card2 = privateCards.get(1);
        Rank rank1 = (Rank) card1.getRank();
        Rank rank2 = (Rank) card2.getRank();

        return rank1.getRankCardValue() + rank2.getRankCardValue();
    }
}
