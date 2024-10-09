package hifive;

import ch.aplu.jcardgame.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class CardDecorator {
    private Card card;
    private List<Integer> altValues;
    public CardDecorator(Card card)
    {
        this.card = card;
        altValues = new ArrayList<>();
        addAltValues();
    }
    public List<Integer> getAltValues()
    {
        return altValues;
    }

    protected abstract void addAltValues();
}
