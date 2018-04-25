package lons.examples;


/**
 * Write a description of class InstrumentedNKBinaryProblem here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InstrumentedNKModelProblem implements BinaryProblem
{
    private NKModelProblem wrappedInstance;
    static int evaluationCalls;
    
    InstrumentedNKModelProblem(NKModelProblem wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }
    
    public double getQuality(BinarySolution s) {
        evaluationCalls++;
        return wrappedInstance.getQuality(s);
    }
    
    public BinarySolution[] getExhaustiveSetOfSolutions() {
        return wrappedInstance.getExhaustiveSetOfSolutions();
    }
    
    public BinarySolution getRandomSolution() {
        return wrappedInstance.getRandomSolution();
    }
}
