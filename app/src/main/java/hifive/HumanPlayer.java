package hifive;

import ch.aplu.jcardgame.*;

import java.util.Properties;

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
