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
    K[] neighbouringSolutionsUpToDistance(K s);
    void setMaxDistance(int maxDistance);
//     int[] indicesOfNeighbouringSolutions(K s);
//     int[] indicesOfNeighbouringSolutions(K s, int maxDistance);
}
