package hifive;

import ch.aplu.jcardgame.Card;

import java.util.List;

public class FactoryCalculator
{
    private static FactoryCalculator factoryCalculator = null;
    private CompositeCalculator compCalculator;
    private FactoryCalculator()
    {
        compCalculator = new CompositeCalculator();
        createCompCalculator();
    }

    private void createCompCalculator()
    {
        compCalculator.addCalculator(new FiveCalculator());
        compCalculator.addCalculator(new SumFiveCalculator());
        compCalculator.addCalculator(new DifferenceFiveCalculator());
        compCalculator.addCalculator(new NoFiveCalculator());
    }

    public CompositeCalculator getCompCalculator()
    {
        return compCalculator;
    }

    public static FactoryCalculator getFactoryCalculator()
    {
        if (factoryCalculator == null)
        {
            factoryCalculator = new FactoryCalculator();
        }
        return factoryCalculator;
    }
}
