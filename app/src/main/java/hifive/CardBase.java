package hifive;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Deck;

import java.util.ArrayList;
import java.util.List;

import static hifive.HiFive.random;


public class CardBase extends Card
{
    public <T extends Enum<T>, R extends Enum<R>> CardBase(Deck deck, T suit, R rank) {
        super(deck, suit, rank);
    }

    public static Card randomCard(ArrayList<Card> list)
    {
        int x = random.nextInt(list.size());
        return list.get(x);
    }

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
