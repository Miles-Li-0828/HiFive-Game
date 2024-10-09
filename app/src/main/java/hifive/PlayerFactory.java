package hifive;

import ch.aplu.jcardgame.Deck;

import java.util.Properties;

public class PlayerFactory
{
    private Properties properties;

    public PlayerFactory(Properties properties)
    {
        this.properties = properties;
    }

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

