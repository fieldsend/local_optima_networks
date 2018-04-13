package lons;


/**
 * Write a description of class InstrumentedConcreteBinarySolution here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InstrumentedConcreteBinarySolution implements BinarySolution
{
    private ConcreteBinarySolution wrappedInstance;
    static int neighbourCallCount;
    InstrumentedConcreteBinarySolution(ConcreteBinarySolution wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }
    
    @Override
    public boolean[] getDesignVector() {
        return wrappedInstance.getDesignVector();
    }
    
    @Override
    public boolean getDesignVariable(int index) {
        return wrappedInstance.getDesignVariable(index);
    }
    
    @Override
    public int getIndex() {
        return wrappedInstance.getIndex();
    }
    
    @Override
    public BinarySolution[] getNeighbours() {
        return wrappedInstance.getNeighbours();
    }
    
    @Override
    public int[] getNeighbourIndices() {
        return wrappedInstance.getNeighbourIndices();
    }
}