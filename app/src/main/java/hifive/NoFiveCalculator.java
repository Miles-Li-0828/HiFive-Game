package hifive;

import ch.aplu.jcardgame.Card;

import java.util.List;

public class NoFiveCalculator implements ScoreCalculator {
    @Override
    public int calculateScore(List<Card> privateCards) {
        Card card1 = privateCards.get(0);
        Card card2 = privateCards.get(1);
        Rank rank1 = (Rank) card1.getRank();
        Rank rank2 = (Rank) card2.getRank();

        return rank1.getRankCardValue() + rank2.getRankCardValue();
    }
}
