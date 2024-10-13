package hifive;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Deck;
import ch.aplu.jgamegrid.GameGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * BasicComputerPlayer class
 *
 * @version 1.0
 * @since 2024-10-01
 * @author Miles Li, Kylar Khant, Ngoc Thanh Lam Nguyen
 */
public class BasicComputerPlayer extends ComputerPlayer
{

    /**
     * Constructor of Player
     *
     * @param id         : player id
     * @param deck       : deck of cards
     * @param properties : properties
     */
    public BasicComputerPlayer(int id, Deck deck, Properties properties, HiFive game)
    {
        super(id, deck, properties, game);
    }

    /**
     * BasicComputerPlayer play a card
     * If value cards are available, the player chooses the card with the lowest suit and highest rank.
     * Otherwise, the player selects a picture card based on the lowest rank and suit.
     * The selected card is removed from the hand
     *
     * @return Card: selected card
     */
    @Override
    public Card play()
    {
        super.getGame().dealACardToHand(super.getHand());
        HiFive.delay(super.getGame().getThinkingTime());

        List<Card> handCards = super.getHand().getCardList();

        // Find the value cards and picture cards
        List<Card> valueCards = new ArrayList<>();
        List<Card> pictureCards = new ArrayList<>();

        for (Card card : handCards)
        {
            if (card.getRankId() <= 10 && card.getRankId() >= 2)
            {
                valueCards.add(card);
            }
            else
            {
                pictureCards.add(card);
            }
        }

        // Dealing with the value cards first
        Card selected = null;
        if (!valueCards.isEmpty())
        {
            selected = cardWithLowestSuitAndHighestRank(valueCards);
        }
        else
        {
            selected = cardWithLowestRankAndSuit(pictureCards);
        }

        selected.removeFromHand(true);
        super.getGame().refresh();
        return selected;
    }

    /**
     * Select the card with the lowest suit and the highest rank from the value cards
     *
     * @param cards: list of value cards
     * @return Card: the card with the lowest suit and the highest rank
     */
    private Card cardWithLowestSuitAndHighestRank(List<Card> cards)
    {
        cards.sort((c1, c2) ->
        {
            if (c1.getSuitId() != c2.getSuitId())
            {
                return c1.getSuitId() - c2.getSuitId();
            }
            else
            {
                return c1.getRankId() - c2.getRankId();
            }
        });

        return cards.get(0);
    }

    /**
     * Select the card with the lowest rank and suit from the picture cards
     *
     * @param cards: list of picture cards
     * @return Card: the card with the lowest rank and suit
     */
    private Card cardWithLowestRankAndSuit(List<Card> cards)
    {
        cards.sort((c1, c2) ->
        {
            if (c1.getRankId() != c2.getRankId())
            {
                return c1.getRankId() - c2.getRankId();
            }
            else
            {
                return c1.getSuitId() - c2.getSuitId();
            }
        });

        return cards.get(0);
    }
}
