package lons;


/**
 * Enumeration class EdgeType - write a description of the enum class here
 * 
 * @author Jonathan Fieldsend
 * @version (version number or date here)
 */
public enum EdgeType
{
    BASIN_TRANSITION, ESCAPE_EDGE;
    
    private int maxEscapeDistance = 1;
    
    void setEscape(int maxEscapeDistance) {
        this.maxEscapeDistance = maxEscapeDistance;
    }
}
