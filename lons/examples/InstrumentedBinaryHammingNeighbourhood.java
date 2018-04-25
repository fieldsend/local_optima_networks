package lons.examples;

import lons.Neighbourhood;
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
    public void setMaxDistance(int maxDistance) {
        wrappedInstance.setMaxDistance(maxDistance);
    }
    
//     @Override
//     public int[] indicesOfNeighbouringSolutions(BinarySolution s){
//         neighbourhoodCalls++;
//         return wrappedInstance.indicesOfNeighbouringSolutions(s);
//     }

    @Override
    public BinarySolution[] neighbouringSolutionsUpToDistance(BinarySolution s){
        neighbourhoodCalls++;
        return wrappedInstance.neighbouringSolutionsUpToDistance(s);
    }
    
//     @Override
//     public int[] indicesOfNeighbouringSolutions(BinarySolution s, int maxDistance){
//         neighbourhoodCalls++;
//         return wrappedInstance.indicesOfNeighbouringSolutions(s, maxDistance);
//     }
}
