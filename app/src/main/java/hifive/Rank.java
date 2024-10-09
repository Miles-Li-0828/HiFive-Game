package hifive;

import ch.aplu.jcardgame.Card;

public enum Rank
{
TWO(2, 2),
THREE(3, 3),
FOUR(4, 4),
FIVE(5, 5),
SIX(6, 6),
SEVEN(7, 7),
EIGHT(8, 8),
NINE(9, 9),
TEN(10, 10),
JACK(11, 11),
QUEEN(12, 12),
KING(13, 13),
ACE(1, 1);

    private int rankCardValue = 1;
    private int scoreValue = 0;

    Rank(int rankCardValue, int scoreValue)
    {
        this.rankCardValue = rankCardValue;
        this.scoreValue = scoreValue;
    }

    public int getRankCardValue()
    {
        return rankCardValue;
    }
    public int getScoreCardValue()
    {
        return scoreValue;
    }
    public String getRankCardLog()
    {
        return String.format("%d", rankCardValue);
    }

    public static Rank getRankFromString(String cardName)
    {
        String rankString = cardName.substring(0, cardName.length() - 1);
        Integer rankValue = Integer.parseInt(rankString);

        for (Rank rank : Rank.values())
        {
            if (rank.getRankCardValue() == rankValue)
            {
                return rank;
            }
        }

        return Rank.ACE;
    }
}
