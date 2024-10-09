package hifive;

import ch.aplu.jcardgame.Card;

import java.util.List;

public interface ScoreCalculator {
    int calculateScore(List<Card> privateCards);
}
