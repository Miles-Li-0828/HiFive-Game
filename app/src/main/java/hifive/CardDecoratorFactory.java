package hifive;

import ch.aplu.jcardgame.Card;

/**
 * CardDecorator class provides a way to extend the functionality of a Card object
 *
 * @version 1.0
 * @since 2024-10-01
 * @author Miles Li, Skylar Khant, Ngoc Thanh Lam Nguyen
 */
public class CardDecoratorFactory 
{
    /**
     * A decorator for wildcard
     *
     * @param rank: the rank of the card
     * @param card: the card to be decorated
     * @return CardDecorator: for the wildcard; or null otherwise
     */
    public CardDecorator getWildCardDecorator(Rank rank, Card card)
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
}
