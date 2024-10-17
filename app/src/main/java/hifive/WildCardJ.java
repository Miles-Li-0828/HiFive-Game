package hifive;

import ch.aplu.jcardgame.Card;

public class WildCardJ extends CardDecorator 
{
    public WildCardJ(Card card)
    {
        super(card);
    }

    protected void addAltValues()
    {
        this.getAltValues().add(1);
        this.getAltValues().add(2);
        this.getAltValues().add(3);
        this.getAltValues().add(4);
    }
}
