package lons;


/**
 * Defines methods required by all Neighbourhood implementors
 * 
 * @author Jonathan Fieldsend 
 * @version 1.0
 */
public interface Neighbourhood<K extends Solution>
{
    /**
     * Given the argument s, this method returns all its neighbours as defined by 
     * this Neighbourhood instance
     */
    K[] neighbouringSolutions(K s);
    
    /**
     * Given the argument s, this method returns all its neighbours as defined by 
     * this Neighbourhood instance up to a set distance currently stored in this 
     * Neighbourhood
     */
    K[] neighbouringSolutionsUpToDistance(K s);
    
    /**
     * Set the maximum distance to be considered in future calls to the 
     * neighbouringSolutionsUpToDistance method
     */
    void setMaxDistance(int maxDistance);
}
