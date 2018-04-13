package lons;


/**
 * Write a description of interface Solution here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface BinarySolution
{
    boolean[] getDesignVector();
    boolean getDesignVariable(int index);
    int getIndex();
    BinarySolution[] getNeighbours();
    int[] getNeighbourIndices();
    
    default public int hashcode() {
        return getIndex();
    }
}
