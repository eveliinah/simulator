package simulaattorinrunko5FX.src.eduni.distributions;

/** A random number generator based on the pareto distribution.
  * automatically generated by <code>MetaGenerator</code> 
  * @version 1.0, Thu Oct 03 10:59:16 BST 2002
  * @author F.Mallet from C.Simatos's original
  */

public class Pareto extends Generator implements ContinuousGenerator {
    private double shape;
    private double scale;
    /**
     * the seed is aumatically provided by the <code>SeedGenerator</code>
     */
    public Pareto(double scale, double shape) {
        super();
        set(shape, scale);
    }

    /**
     * The constructor with which a specific seed is set for the random
     * number generator
     * @param seed The initial seed for the generator, two instances with
     *             the same seed will generate the same sequence of numbers
     */
    public Pareto(double scale, double shape, long seed) {
        super(seed);
        set(shape, scale);
    }

    private void set(double shape, double scale) {
        if (shape<=0 || scale<=0)
            throw new ParameterException("Pareto: Shape and scale parameters must be greater than 0.");
        this.shape = shape;
        this.scale = scale;
    }

    /**
     * Generate a new random number.
     * @return The next random number in the sequence
     */
    public double sample() { 
         return distrib.pareto(shape, scale);
    }
}
