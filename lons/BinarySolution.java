package lons;


/**
 * Write a description of interface Solution here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface BinarySolution extends Solution
{
    boolean[] getDesignVector();
    boolean getDesignVariable(int index);
}
