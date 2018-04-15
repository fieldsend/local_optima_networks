package lons;


/**
 * Write a description of interface Solution here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Solution
{
    public abstract int getIndex();
    public abstract int getNumberOfElements();
    public int hashCode() {
        return getIndex();
    }
    public boolean equals(Object obj) {
        if (obj instanceof Solution) 
            if (((Solution) obj).getIndex()==this.getIndex())
                return true;
        
        return false;
    }
}
