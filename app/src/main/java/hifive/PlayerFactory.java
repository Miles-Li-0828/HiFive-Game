package hifive;

import ch.aplu.jcardgame.Deck;

import java.util.Properties;

/**
 * PlayerFactory class to Player instances based on the specified mode
 * Utilised factory pattern
 *
 * @version 1.0
 * @since 2024-10-01
 * @author Miles Li, Skylar Khant, Ngoc Thanh Lam Nguyen
 */
public class PlayerFactory
{
    private Properties properties;

    /**
     * Constructor of PlayerFactory
     * @param properties: properties
     */
    public PlayerFactory(Properties properties)
    {
        this.properties = properties;
    }

    /**
     * Creates a Player instance based on the specified mode.
     *
     * @param mode: a string representing the type of player to create
     * @param id: player id
     * @param deck: deck of cards
     * @param game: the HiFive game
     * @return Player: a new Player instance corresponding to the specified mode;
     *                 or null if the mode is no such mode
     */
    public Player createPlayer(String mode, int id, Deck deck, HiFive game)
    {
        return switch (mode)
        {
            case "human" -> new HumanPlayer(id, deck, properties, game);
            case "random" -> new ComputerPlayer(id, deck, properties, game);
            case "clever" -> new CleverComputerPlayer(id, deck, properties, game);
            case "basic" -> new BasicComputerPlayer(id, deck, properties, game);
            default -> null;
        };
    }
}

