package lons;


/**
 * Write a description of class Basin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Basin
{
    private int volume;

    public Basin(){ }

    void increment()
    {
        volume++;
    }
    
    int getBasinSize() {
        return volume;
    }
}
