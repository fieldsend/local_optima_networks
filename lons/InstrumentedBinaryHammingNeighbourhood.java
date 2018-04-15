package lons;


/**
 * Write a description of class InstrumentedBinaryHammingNeighbourhood here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InstrumentedBinaryHammingNeighbourhood implements Neighbourhood<BinarySolution>
{
    static int neighbourhoodCalls;
    BinaryHammingNeighbourhood wrappedInstance;
    InstrumentedBinaryHammingNeighbourhood(BinaryHammingNeighbourhood wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }
    @Override
    public BinarySolution[] neighbouringSolutions(BinarySolution s){
        neighbourhoodCalls++;
        return wrappedInstance.neighbouringSolutions(s);
    }
    
    @Override
    public int[] indicesOfNeighbouringSolutions(BinarySolution s){
        neighbourhoodCalls++;
        return wrappedInstance.indicesOfNeighbouringSolutions(s);
    }
}
