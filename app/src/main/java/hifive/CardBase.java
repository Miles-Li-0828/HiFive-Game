package hifive;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Deck;

import java.util.ArrayList;
import java.util.List;

import static hifive.HiFive.random;

/**
 * CardBase class provides methods for working with a deck of cards
 *
 * @version 1.0
 * @since 2024-10-01
 * @author Miles Li, Skylar Khant, Ngoc Thanh Lam Nguyen
 */
public class CardBase extends Card
{

    /**
     * Constructs CardBase
     *
     * @param <T>:  enum type for suit
     * @param <R>:  enum type for rank
     * @param deck: the cards' deck
     * @param suit: the card's suit
     * @param rank: the card's rank
     */
    public <T extends Enum<T>, R extends Enum<R>> CardBase(Deck deck, T suit, R rank) {
        super(deck, suit, rank);
    }
}
