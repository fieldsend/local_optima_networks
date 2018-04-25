package lons.examples;

import lons.Solution;

/**
 * Abstract class PermutationSolution - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class IntegerVectorSolution extends Solution
{
    public abstract int[] getDesignVector();
    public abstract int getDesignVariable(int index);
    public abstract int getMaxValue();
}
