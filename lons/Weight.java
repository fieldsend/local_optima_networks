package lons;


/**
 * Write a description of class Basin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Weight
{
    private int weight;

    public Weight(){ }

    public Weight(int weight){ 
        this.weight = weight; 
    }
    
    void increment()
    {
        weight++;
    }
    
    int getWeight() {
        return weight;
    }
}
