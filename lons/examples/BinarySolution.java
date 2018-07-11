package lons.examples;

import lons.Solution;
/**
 * Class defines additional  methods that BinarySolutions must provide beyond those
 * defined in Solution
 * 
 * @author Jonathan Fieldsend
 * @version 11/07/2018
 */
public abstract class BinarySolution extends Solution
{
    public abstract boolean[] getDesignVector();
    public abstract boolean getDesignVariable(int index);
}
