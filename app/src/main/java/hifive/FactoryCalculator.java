package hifive;

/**
 * FactoryCalculator class provides access to the CompositeCalculator
 * Utilised singleton pattern and factory pattern
 *
 * @version 1.0
 * @since 2024-10-01
 * @author Miles Li, Kylar Khant, Ngoc Thanh Lam Nguyen
 */public class FactoryCalculator
{
    private static FactoryCalculator factoryCalculator = null;
    private CompositeCalculator compCalculator;

    /**
     * Constructor or FactoryCalculator
     */
    private FactoryCalculator()
    {
        compCalculator = new CompositeCalculator();
        createCompCalculator();
    }

    /**
     * Adds scoring calculators to CompositeCalculator
     */
    private void createCompCalculator()
    {
        compCalculator.addCalculator(new FiveCalculator());
        compCalculator.addCalculator(new SumFiveCalculator());
        compCalculator.addCalculator(new DifferenceFiveCalculator());
        compCalculator.addCalculator(new NoFiveCalculator());
    }

    /**
     * Getter
     *
     * @return CompositeCalculator: the CompositeCalculator instance.
     */
    public CompositeCalculator getCompCalculator()
    {
        return compCalculator;
    }

    /**
     * Provides access to the single instance of FactoryCalculator
     * If no instance exists, it creates one.
     *
     * @return FactoryCalculator: the singleton instance of FactoryCalculator
     */
    public static FactoryCalculator getFactoryCalculator()
    {
        if (factoryCalculator == null)
        {
            factoryCalculator = new FactoryCalculator();
        }
        return factoryCalculator;
    }
}
