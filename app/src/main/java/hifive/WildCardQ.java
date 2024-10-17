package hifive;

import ch.aplu.jcardgame.Card;

public class WildCardQ extends CardDecorator 
{
    public WildCardQ(Card card)
    {
        super(card);
    }

    protected void addAltValues()
    {
        this.getAltValues().add(6);
        this.getAltValues().add(7);
        this.getAltValues().add(8);
        this.getAltValues().add(9);
    }
}
