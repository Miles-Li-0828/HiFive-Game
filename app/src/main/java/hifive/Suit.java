package hifive;

public enum Suit implements Comparable<Suit>
{
    CLUBS("C", 5), DIAMONDS("D", 10),
    HEARTS("H", 15), SPADES("S", 20);
    private String suitShortHand = "";
    private final int bonusFactor;
    private Suit other;

    Suit(String shortHand, int bonusFactor)
    {
        this.suitShortHand = shortHand;
        this.bonusFactor = bonusFactor;
    }

    public String getSuitShortHand()
    {
        return suitShortHand;
    }

    public int getBonusFactor()
    {
        return bonusFactor;
    }

    public static Suit getSuitFromString(String cardName)
    {
//        String rankString = cardName.substring(0, cardName.length() - 1);
        String suitString = cardName.substring(cardName.length() - 1, cardName.length());

        for (Suit suit : Suit.values())
        {
            if (suit.getSuitShortHand().equals(suitString))
            {
                return suit;
            }
        }
        return Suit.CLUBS;
    }

}
