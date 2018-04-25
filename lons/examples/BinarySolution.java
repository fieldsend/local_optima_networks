package lons.examples;

import lons.Solution;
/**
 * Write a description of interface Solution here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class BinarySolution extends Solution
{
    public abstract boolean[] getDesignVector();
    public abstract boolean getDesignVariable(int index);
}
