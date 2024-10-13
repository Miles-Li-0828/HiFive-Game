package hifive;

import ch.aplu.jcardgame.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * CardDecorator class provides a way to extend the functionality of a Card object
 *
 * @version 1.0
 * @since 2024-10-01
 * @author Miles Li, Kylar Khant, Ngoc Thanh Lam Nguyen
 */
public abstract class CardDecorator {
    private Card card;
    private List<Integer> altValues;

    /**
     * Constructor of CardDecorator
     *
     * @param card: the card to be decorated
     */
    public CardDecorator(Card card)
    {
        this.card = card;
        altValues = new ArrayList<>();
        addAltValues();
    }

    /**
     * getter
     *
     * @return a list of alternative values for the card
     */
    public List<Integer> getAltValues()
    {
        return altValues;
    }

    /**
     * Abstract method to add specific alternative values
     */
    protected abstract void addAltValues();
}
