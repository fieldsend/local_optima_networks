package lons;


import java.util.HashMap;
import java.util.ArrayList;


/**
 * Write a description of class Main here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Main
{
    public static void main(String[] args) {
        int n = 16;
        int k = 8;
        int[] exactGreedyCalls = new int[100];
        int[] naiveGreedyCalls = new int[100];
        for (int i=0; i<100; i++) {
            System.out.println("\n simulation: " + i);
            NKModelProblem.setSeed(i);
            NKModelProblem nk = new NKModelProblem(n,k);
            LONGenerator.exhaustiveLON(nk, new BinaryHammingNeighbourhood(), new HashMap<BinarySolution,Weight>(),new HashMap<BinarySolution,HashMap<BinarySolution,Weight>>());
            exactGreedyCalls[i] = LONGenerator.greedyHillclimbCalls;
            LONGenerator.naiveExhaustiveLON(nk, new BinaryHammingNeighbourhood(), new HashMap<BinarySolution,Weight>(),new HashMap<BinarySolution,HashMap<BinarySolution,Weight>>());
            naiveGreedyCalls[i] = LONGenerator.greedyHillclimbCalls;
        }
        System.out.println("exact...");
        for (int i=0; i<100; i++) 
            System.out.print(exactGreedyCalls[i]+ " ");
        System.out.println("");
        System.out.println("");
        System.out.println("exact niave...");
        for (int i=0; i<100; i++) 
            System.out.print(naiveGreedyCalls[i]+ " ");
        System.out.println("");
        System.out.println("");
    }
    
    public static void samplingExample(int numberOfSamples) {
        int n = 18;
        int k = 10;
        int greedyCalls = 0;
        int naiveGreedyCalls = 0;
        int neighbourhoodCalls = 0;
        int naiveNeighbourhoodCalls = 0;
        int functionCalls = 0;
        int naiveFunctionCalls = 0;
        
        //int numberOfSamples = 1000;
        
        NKModelProblem.setSeed(0);
        InstrumentedNKModelProblem nk = new InstrumentedNKModelProblem(new NKModelProblem(n,k));
        InstrumentedBinaryHammingNeighbourhood.neighbourhoodCalls = 0;
        InstrumentedNKModelProblem.evaluationCalls = 0;
        LONGenerator.sampledLON(nk, new InstrumentedBinaryHammingNeighbourhood(new BinaryHammingNeighbourhood()), new HashMap<BinarySolution,Weight>(),new HashMap<BinarySolution,HashMap<BinarySolution,Weight>>(), numberOfSamples);
        greedyCalls = LONGenerator.greedyHillclimbCalls;
        neighbourhoodCalls = InstrumentedBinaryHammingNeighbourhood.neighbourhoodCalls;
        functionCalls = InstrumentedNKModelProblem.evaluationCalls;
        
        NKModelProblem.setSeed(0);
        nk = new InstrumentedNKModelProblem(new NKModelProblem(n,k));
        InstrumentedBinaryHammingNeighbourhood.neighbourhoodCalls = 0;
        InstrumentedNKModelProblem.evaluationCalls = 0;
        LONGenerator.naiveSampledLON(nk, new InstrumentedBinaryHammingNeighbourhood(new BinaryHammingNeighbourhood()), new HashMap<BinarySolution,Weight>(),new HashMap<BinarySolution,HashMap<BinarySolution,Weight>>(), numberOfSamples);
        naiveGreedyCalls = LONGenerator.greedyHillclimbCalls;
        naiveNeighbourhoodCalls = InstrumentedBinaryHammingNeighbourhood.neighbourhoodCalls;
        naiveFunctionCalls = InstrumentedNKModelProblem.evaluationCalls;
        
        System.out.println("\n sampling...");
        System.out.println("greedy: " + greedyCalls);
        System.out.println("neighbour: " + neighbourhoodCalls);
        System.out.println("f evals: " + functionCalls);
        System.out.println("");
        System.out.println("sampling naive...");
        System.out.println("greedy: " + naiveGreedyCalls);
        System.out.println("neighbour: " + naiveNeighbourhoodCalls);
        System.out.println("f evals: "+ naiveFunctionCalls);
        System.out.println("");
    }
}
