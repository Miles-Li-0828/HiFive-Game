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
 * @author Miles Li, Kylar Khant, Ngoc Thanh Lam Nguyen
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

    /**
     * Selects a random card from the given list of cards
     *
     * @param list: the list of cards to select from
     * @return Card: a randomly selected card from the list
     */
    public static Card randomCard(ArrayList<Card> list)
    {
        int x = random.nextInt(list.size());
        return list.get(x);
    }

    /**
     * Retrieves a card from the given list of cards that matches the specified card name
     *
     * @param cards: the list of cards to search through
     * @param cardName: the name of the card to search for
     * @return Card: the card that matches; or null if no such card is found
     */
    public static Card getCardFromList(List<Card> cards, String cardName)
    {
        Rank cardRank = Rank.getRankFromString(cardName);
        Suit cardSuit = Suit.getSuitFromString(cardName);
        for (Card card : cards)
        {
            if (card.getSuit() == cardSuit
                    && card.getRank() == cardRank)
            {
                return card;
            }
        }

        return null;
    }
}
