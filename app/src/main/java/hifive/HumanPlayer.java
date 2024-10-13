package hifive;

import ch.aplu.jcardgame.*;

import java.util.Properties;

/**
 * HumanPlayer class represents a human player in the Hi Five card game
 * Provided specific functionality for human interaction
 *
 * @version 1.0
 * @since 2024-10-01
 * @author Miles Li, Kylar Khant, Ngoc Thanh Lam Nguyen
 */
public class HumanPlayer extends Player
{
    private Card selected = null;
    private Hand hand = super.getHand();

    /**
     * Constructor of Player
     *
     * @param id         : player id
     * @param deck       : deck of cards
     * @param properties : properties
     */
    public HumanPlayer(int id, Deck deck, Properties properties, HiFive game)
    {
        super(id, deck, properties, game);
    }

    /**
     * Human player to select a card to play by double-clicking on it
     * Waits for the player to make a selection and returns the chosen card
     *
     * @return Card: selected card
     */
    @Override
    public Card play()
    {
        hand.setTouchEnabled(true);
        // Set up human player for interaction
        CardListener cardListener = new CardAdapter()  // Human Player plays card
        {
            public void leftDoubleClicked(Card card)
            {
                selected = card;
                hand.setTouchEnabled(false);
                System.out.println("Player 0 is playing. Please double click on a card to discard");
            }
        };
        hand.addCardListener(cardListener);
        super.setHand(hand);

        super.getGame().setStatus("Player 0 is playing. Please double click on a card to discard");
        super.getGame().dealACardToHand(super.getHand());
        while (selected == null) HiFive.delay(super.getGame().getDelayTime());
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
        selected = null;

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
