package hifive;

import ch.aplu.jcardgame.Card;

public class WildCardK extends CardDecorator {
    public WildCardK(Card card)
    {
        super(card);
    }

    protected void addAltValues()
    {
        this.getAltValues().add(1);
        this.getAltValues().add(3);
        this.getAltValues().add(7);
        this.getAltValues().add(9);
        this.getAltValues().add(11);
    }
}
