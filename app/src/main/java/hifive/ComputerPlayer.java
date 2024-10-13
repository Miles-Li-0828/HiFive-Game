package hifive;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Deck;

import java.util.Properties;

/**
 * ComputerPlayer class to representing a computer-controlled player in the game
 *
 * @version 1.0
 * @since 2024-10-01
 * @author Miles Li, Kylar Khant, Ngoc Thanh Lam Nguyen
 */
public class ComputerPlayer extends Player
{
    /**
     * Constructor of Player
     *
     * @param id         : player id
     * @param deck       : deck of cards
     * @param properties: properties
     */
    public ComputerPlayer(int id, Deck deck, Properties properties, HiFive game)
    {
        super(id, deck, properties, game);
    }

    /**
     * Computer player play a card
     * The card is selected randomly from the player's hand
     * The selected card is then removed from the hand
     *
     * @return Card: selected card
     */
    @Override
    public Card play()
    {
        Card selected = null;
        selected = super.getGame().getRandomCard(super.getHand());
        selected.removeFromHand(true);
        return selected;
    }

    /**
     * Choose the card to be discarded
     *
     * @return Cards : the card that selected to discard
     */
    @Override
    public Card discardCard()
    {
        Card selected = null;

        if (super.getGame().isAuto())
        {
            selected = autoPlay();
        }
        else
        {
            setFinishAuto(true);
        }


        if (!super.getGame().isAuto() || super.isFinishAuto()) // The main logic of Random Computer Player
        {
            super.getGame().setStatusText("Player " + super.getId() + " thinking...");
            selected = play();
        }
        return selected;
    }
}
