package lons.examples;

import lons.EdgeType;
import lons.Weight;
import lons.LONGenerator;
import java.util.HashMap;
import java.util.ArrayList;
import cec2013.CEC2013;
import cec2013.Func;
import cec2013.ClosedInterval;
import java.util.List;

/**
 * Class to run example experiments from paper -- requires use of cec2013 package, 
 * which is maintained by Michael Epitropakis and alsi available on GitHub https://github.com/mikeagn 
 * 
 * @author Jonathan Fieldsend
 * @version 11/07/2018
 */
public class Experiments
{
    public static void main(String[] args) {
        for (int i=12; i<20; i++)
            cecRuns(i);
    }
    
    public static void cecRuns(int functionNumber) {
        EdgeType edgeType = EdgeType.BASIN_TRANSITION;
        CEC2013 comp = new CEC2013();
        
        System.out.println("\n simulation: " + functionNumber);
        //int resolutionPerVariable = 100;
        int[] exactGreedyCalls = new int[20];
        int[] naiveGreedyCalls = new int[20];
        for (int i=10; i<=200; i+=10) {
            System.out.println("RESOLUTION: "+i);
            List< Func > testFunctions = comp.getFunctions();
            List< ClosedInterval.Double > bounds;
            Func a = testFunctions.get(functionNumber);
            DiscretisedCEC2013NichingProblem problem = new DiscretisedCEC2013NichingProblem(a, i);
            LONGenerator.exhaustiveLON(problem, new IntegerVectorNeighbourhood(), new HashMap<IntegerVectorSolution,Weight>(), new HashMap<IntegerVectorSolution,Double>(),new HashMap<IntegerVectorSolution,HashMap<IntegerVectorSolution,Weight>>(),edgeType);
            exactGreedyCalls[(i/10)-1] = LONGenerator.getGreedyHillclimbCalls();
            LONGenerator.naiveExhaustiveLON(problem, new IntegerVectorNeighbourhood(), new HashMap<IntegerVectorSolution,Weight>(), new HashMap<IntegerVectorSolution,Double>(),new HashMap<IntegerVectorSolution,HashMap<IntegerVectorSolution,Weight>>(),edgeType);
            naiveGreedyCalls[(i/10)-1] = LONGenerator.getGreedyHillclimbCalls();
        }
        System.out.println("\n exact/naive ");
        for (int g: exactGreedyCalls)
            System.out.print(g + " ");
        System.out.println("\n ;");
        for (int g: naiveGreedyCalls)
            System.out.print(g + " ");    
    }
    
    
    public static void convergenceRuns(int runs, int numberOfSamples,int n, int k, boolean basinTransition, int maxDist) {
        for (int i=0; i<runs; i++)
            sampleExample(i,n,k,numberOfSamples, basinTransition, maxDist);
        
    }
    
    public static void exactExample(int seed, int n, int k, boolean basinTransition, int maxDist) {
        EdgeType edgeType;
        if (basinTransition)
            edgeType = EdgeType.BASIN_TRANSITION;
        else
            edgeType = EdgeType.ESCAPE_EDGE;
        
        HashMap<BinarySolution,Weight> optimaBasins =  new HashMap<BinarySolution,Weight>();
        HashMap<BinarySolution,Double> optimaQuality =  new HashMap<BinarySolution,Double>();
        HashMap<BinarySolution,HashMap<BinarySolution,Weight>> mapOfAdjacencyListAndWeight = new HashMap<BinarySolution,HashMap<BinarySolution,Weight>>();  
        BinaryHammingNeighbourhood neighbourhood = new BinaryHammingNeighbourhood();
        neighbourhood.setMaxDistance(maxDist);    
        NKModelProblem.setSeed(seed);
        NKModelProblem nk = new NKModelProblem(n,k);
        LONGenerator.exhaustiveLON(nk, neighbourhood, optimaBasins, optimaQuality, mapOfAdjacencyListAndWeight,edgeType);
        System.out.println("\n number of optima" + optimaBasins.size());
        System.out.println("\nWriting files...");    
        LONGenerator.writeOutLON(optimaBasins,optimaQuality, mapOfAdjacencyListAndWeight);
        System.out.println("Completed");
    }
    
    
    public static void sampleExample(int seed, int n, int k, int numberOfSamples, boolean basinTransition, int maxDist) {
        EdgeType edgeType;
        if (basinTransition)
            edgeType = EdgeType.BASIN_TRANSITION;
        else
            edgeType = EdgeType.ESCAPE_EDGE;
        
        HashMap<BinarySolution,Weight> optimaBasins =  new HashMap<BinarySolution,Weight>();
        HashMap<BinarySolution,Double> optimaQuality =  new HashMap<BinarySolution,Double>();
        HashMap<BinarySolution,HashMap<BinarySolution,Weight>> mapOfAdjacencyListAndWeight = new HashMap<BinarySolution,HashMap<BinarySolution,Weight>>();  
        BinaryHammingNeighbourhood neighbourhood = new BinaryHammingNeighbourhood();
        neighbourhood.setMaxDistance(maxDist);    
        NKModelProblem.setSeed(0); // same mode for experiments
        NKModelProblem nk = new NKModelProblem(n,k);
        NKModelProblem.setSeed(seed);
        LONGenerator.sampledLON(nk, new InstrumentedBinaryHammingNeighbourhood(new BinaryHammingNeighbourhood()), new HashMap<BinarySolution,Weight>(), new HashMap<BinarySolution,Double>(),new HashMap<BinarySolution,HashMap<BinarySolution,Weight>>(),edgeType, numberOfSamples);
        System.out.println("\n ;");
    }
    
    
    public static void exactExampleRuns(int runs, int n, int k, boolean basinTransition) {
        int[] exactGreedyCalls = new int[100];
        int[] naiveGreedyCalls = new int[100];
        EdgeType edgeType;
        if (basinTransition)
            edgeType = EdgeType.BASIN_TRANSITION;
        else
            edgeType = EdgeType.ESCAPE_EDGE;
            
        for (int i=0; i<runs; i++) {
            System.out.println("\n simulation: " + i);
            NKModelProblem.setSeed(i);
            NKModelProblem nk = new NKModelProblem(n,k);
            NKModelProblem.setSeed(i);
            LONGenerator.exhaustiveLON(nk, new BinaryHammingNeighbourhood(), new HashMap<BinarySolution,Weight>(), new HashMap<BinarySolution,Double>(),new HashMap<BinarySolution,HashMap<BinarySolution,Weight>>(),edgeType);
            exactGreedyCalls[i] = LONGenerator.getGreedyHillclimbCalls();
            NKModelProblem.setSeed(i);
            LONGenerator.naiveExhaustiveLON(nk, new BinaryHammingNeighbourhood(), new HashMap<BinarySolution,Weight>(), new HashMap<BinarySolution,Double>(),new HashMap<BinarySolution,HashMap<BinarySolution,Weight>>(),edgeType);
            naiveGreedyCalls[i] = LONGenerator.getGreedyHillclimbCalls();
        }
        System.out.println("exact, then naive...");
        System.out.println("");
        for (int i=0; i<runs; i++) 
            System.out.print(exactGreedyCalls[i]+ " ");
        System.out.println(";");
        System.out.println("");
        
        for (int i=0; i<runs; i++) 
            System.out.print(naiveGreedyCalls[i]+ " ");
        System.out.println("");
        System.out.println(";");
    }

    public static void samplingExampleRuns(int runs, int n, int k, int numberOfSamples, boolean basinTransition) {
        System.out.println("N: " + n + ", K: " +k + "samples: " + numberOfSamples+ "basin trans? " + basinTransition);
        int[] greedyCalls = new int[100];
        int[] naiveGreedyCalls = new int[100];
        int[] functionCalls = new int[100];
        int[] naiveFunctionCalls = new int[100];
        EdgeType edgeType;
        if (basinTransition)
            edgeType = EdgeType.BASIN_TRANSITION;
        else
            edgeType = EdgeType.ESCAPE_EDGE;
        //int numberOfSamples = 1000;
        for (int i=0; i<runs; i++) {
            System.out.println("Run: "+ i);
            NKModelProblem.setSeed(i);
            InstrumentedNKModelProblem nk = new InstrumentedNKModelProblem(new NKModelProblem(n,k));
            NKModelProblem.setSeed(i);
            InstrumentedBinaryHammingNeighbourhood.neighbourhoodCalls = 0;
            InstrumentedNKModelProblem.evaluationCalls = 0;
            LONGenerator.sampledLON(nk, new InstrumentedBinaryHammingNeighbourhood(new BinaryHammingNeighbourhood()), new HashMap<BinarySolution,Weight>(), new HashMap<BinarySolution,Double>(),new HashMap<BinarySolution,HashMap<BinarySolution,Weight>>(),edgeType, numberOfSamples);
            greedyCalls[i] = LONGenerator.getGreedyHillclimbCalls();
            //neighbourhoodCalls = InstrumentedBinaryHammingNeighbourhood.neighbourhoodCalls;
            functionCalls[i] = InstrumentedNKModelProblem.evaluationCalls;

            NKModelProblem.setSeed(i);
            InstrumentedBinaryHammingNeighbourhood.neighbourhoodCalls = 0;
            InstrumentedNKModelProblem.evaluationCalls = 0;
            LONGenerator.naiveSampledLON(nk, new InstrumentedBinaryHammingNeighbourhood(new BinaryHammingNeighbourhood()), new HashMap<BinarySolution,Weight>(), new HashMap<BinarySolution,Double>(),new HashMap<BinarySolution,HashMap<BinarySolution,Weight>>(),edgeType, numberOfSamples);
            naiveGreedyCalls[i] = LONGenerator.getGreedyHillclimbCalls();
            //naiveNeighbourhoodCalls = InstrumentedBinaryHammingNeighbourhood.neighbourhoodCalls;
            naiveFunctionCalls[i] = InstrumentedNKModelProblem.evaluationCalls;
        }
        System.out.println("\n sampling...greedy calls\n");
        for (int i=0; i<runs; i++) 
            System.out.print(greedyCalls[i]+ " ");
        System.out.println("\n");
        for (int i=0; i<runs; i++) 
            System.out.print(naiveGreedyCalls[i]+ " ");
            
        System.out.println("\n sampling...cost function queries\n");
        for (int i=0; i<runs; i++) 
            System.out.print(functionCalls[i]+ " ");
        System.out.println("\n");
        for (int i=0; i<runs; i++) 
            System.out.print(naiveFunctionCalls[i]+ " ");
            
    }
}
