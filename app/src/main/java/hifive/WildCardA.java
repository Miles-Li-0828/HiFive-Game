package hifive;

import ch.aplu.jcardgame.Card;

public class WildCardA extends CardDecorator {
    public WildCardA(Card card)
    {
        super(card);
    }

    protected void addAltValues()
    {
        this.getAltValues().add(11);
        this.getAltValues().add(12);
        this.getAltValues().add(13);
    }
}
