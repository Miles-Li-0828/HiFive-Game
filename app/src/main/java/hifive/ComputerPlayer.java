package hifive;

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.GameGrid;

import java.util.List;
import java.util.Properties;

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

    @Override
    public Card play()
    {
        Card selected = null;
        selected = super.getGame().getRandomCard(super.getHand());
        selected.removeFromHand(true);
        return selected;
    }

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
