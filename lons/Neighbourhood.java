package lons;


/**
 * Write a description of interface Neighbourhood here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface Neighbourhood<K extends Solution>
{
    K[] neighbouringSolutions(K s);
    int[] indicesOfNeighbouringSolutions(K s);
}
