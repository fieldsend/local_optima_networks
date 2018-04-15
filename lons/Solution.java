package lons;


/**
 * Write a description of interface Solution here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface Solution
{
    int getIndex();
    int getNumberOfElements();
    default public int hashcode() {
        return getIndex();
    }
}
