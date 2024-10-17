package hifive;

import ch.aplu.jcardgame.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static hifive.HiFive.random;

/**
 * Utility functions related to Card
 *
 * @version 1.0
 * @since 2024-10-01
 * @author Miles Li, Skylar Khant, Ngoc Thanh Lam Nguyen
 */

public class CardUtils
{
    /**
     * Selects a random card from the given list of cards
     *
     * @param list: the list of cards to select from
     * @return Card: a randomly selected card from the list
     */
    public static Card randomCard(ArrayList<Card> list)
    {
        int x = random.nextInt(list.size());
        return list.get(x);
    }

    /**
     * Retrieves a card from the given list of cards that matches the specified card name
     *
     * @param cards: the list of cards to search through
     * @param cardName: the name of the card to search for
     * @return Card: the card that matches; or null if no such card is found
     */
    public static Card getCardFromList(List<Card> cards, String cardName)
    {
        Rank cardRank = Rank.getRankFromString(cardName);
        Suit cardSuit = Suit.getSuitFromString(cardName);
        for (Card card : cards)
        {
            if (card.getSuit() == cardSuit
                    && card.getRank() == cardRank)
            {
                return card;
            }
        }

        return null;
    }

    public static Card getCardWithLowestSuit(List<Card> cards)
    {
        // Ensure the list contains at least 1 card, throw an exception if not
        if (cards.isEmpty())
        {
            throw new IllegalArgumentException("The list must contain at least 1 card.");
        }

        // Start by assuming the first card has the lowest suit bonus
        Card lowestSuitCard = cards.get(0);
        int lowestBonusFactor = ((Suit) lowestSuitCard.getSuit()).getBonusFactor();

        // Iterate through the remaining cards to find the one with the lowest suit bonus
        for (int i = 1; i < cards.size(); i++)
        {
            Card currentCard = cards.get(i);
            int currentBonusFactor = ((Suit) currentCard.getSuit()).getBonusFactor();

            // If the current card's suit bonus is lower, update the lowest suit card
            if (currentBonusFactor < lowestBonusFactor)
            {
                lowestSuitCard = currentCard;
                lowestBonusFactor = currentBonusFactor;
            }
        }

        return lowestSuitCard;  // Return the card with the lowest suit bonus
    }

    public static Card getCardWithHighestRank(List<Card> cards)
    {
        if (cards.isEmpty())
        {
            throw new IllegalArgumentException("The list must contain at least 1 card.");
        }

        Card highestRankCard = null;
        int highestRankValue = Integer.MIN_VALUE;

        // Iterate through the list to find the highest rank card, skipping wildcards
        for (Card card : cards)
        {
            System.out.println(card + " highest\t");
            Rank rank = (Rank) card.getRank();
            CardDecorator cd = new CardDecoratorFactory().getWildCardDecorator(rank, card);
            // Check if the card is not a wildcard
            if (cd == null)
            {
                int rankValue = rank.getRankCardValue();
                System.out.println(" not WC\t");
                // If the current card's rank value is higher, update the highest rank card
                if (rankValue > highestRankValue) {
                    highestRankValue = rankValue;
                    highestRankCard = card;
                    System.out.println(highestRankCard + " highest rank card\t");
                }
            }
        }

        return highestRankCard;  // Return the card with the highest rank
    }


    // Method to create a HashMap of rank values and their counts
    public static HashMap<Integer, Integer> getRankValueCountMap(List<Card> cards)
    {
        HashMap<Integer, Integer> rankValueCountMap = new HashMap<>();
        CardDecoratorFactory cdf = new CardDecoratorFactory();

        for (Card card : cards)
        {
            List<Integer> rankValues = new ArrayList<>();
            Rank rank = (Rank) card.getRank();
            rankValues.add(rank.getRankCardValue());

            CardDecorator cd = cdf.getWildCardDecorator(rank, card);
            // If the card has multiple rank values (wild card), use those, otherwise use the single rank value
            if (cd != null)
            {
                rankValues.addAll(cd.getAltValues());  // Get multiple values for wildcards
            }

              // Single value for regular cards


            // Add each rank value to the map, and increment its count if it already exists
            for (int rankValue : rankValues)
            {
                rankValueCountMap.put(rankValue, rankValueCountMap.getOrDefault(rankValue, 0) + 1);
            }
        }

        return rankValueCountMap;  // Return the map with rank value counts
    }

    public static double calculateDiscardProbability(int value1, int value2) 
    {
        return (double) value1 / value2;
    }
}
