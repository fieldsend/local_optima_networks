package lons;


import java.util.HashMap;
import java.util.ArrayList;

/**
 * Write a description of class NiaveExactLocalOptimaNetwork here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NiaveExactLocalOptimaNetwork
{
    private double[] solutionFitness;
    private int[][] neighbourhoodIndices;
    private ArrayList<BinarySolution> optima = new ArrayList<>();
    private HashMap<BinarySolution,BinarySolution> mapFromSolutionToOptima = new HashMap<>();
    private HashMap<BinarySolution,Integer> optimaIndices = new HashMap<>();
    private double[][] edgeWeights;
    private BinarySolution[] designs;
    private HashMap<BinarySolution,Basin> basinSizes = new HashMap<>();
    int greedyHillclimbCalls;
    
    NiaveExactLocalOptimaNetwork(BinaryProblem problem) {
        designs = problem.getExhaustiveSetOfSolutions();
        neighbourhoodIndices = new int[designs.length][];
        solutionFitness = new double[designs.length];
        // first determine vertices
        // evaluate all designs
        System.out.println("Evaluate all designs");
        for (int i=0; i< designs.length; i++) {
            solutionFitness[i] = problem.getQuality(designs[i]);
        }
        // check neighbourhoods of designs, to determine vertices
        System.out.println("Determine vertices");
        for (int i=0; i< designs.length; i++) {
            neighbourhoodIndices[i] = designs[i].getNeighbourIndices();
            boolean isOptima = true;
            for (int index : neighbourhoodIndices[i]) {
                if (solutionFitness[i]<solutionFitness[index]){
                    isOptima = false;
                    break;
                }
            }
            if (isOptima) {
                optima.add(designs[i]);
            }
        }
        System.out.println("Get basin sizes");
        for (int i=0; i<optima.size(); i++) {
            optimaIndices.put(optima.get(i),i);
            basinSizes.put(optima.get(i), new Basin());
        }
        // now populate map from design to optima index
        for (int i=0; i< designs.length; i++) {
            BinarySolution basinOptima = greedyHillClimb(i);
            mapFromSolutionToOptima.put(designs[i],basinOptima);
        }
        // now compute basin sizes
        System.out.println(optima.size());
        System.out.println(basinSizes.size());
        System.out.println(mapFromSolutionToOptima.size());
        System.out.println(designs.length);
        for (int i=0; i< designs.length; i++) {
            basinSizes.get(mapFromSolutionToOptima.get(designs[i])).increment();
        }
        System.out.println("Specify edges");
        edgeWeights = new double[optima.size()][optima.size()];
        for (int i=0; i< designs.length; i++) {
            for (int j : designs[i].getNeighbourIndices()) {
                edgeWeights[optimaIndices.get(mapFromSolutionToOptima.get(designs[i]))][optimaIndices.get(mapFromSolutionToOptima.get(designs[j]))]++;
            }
        }
        System.out.println("Quality function calls (f()): " + NKModelProblem.numberOfFunctionCalls);
        NKModelProblem.numberOfFunctionCalls = 0; //reset
        System.out.println("Hillclimb calls: " + greedyHillclimbCalls);
    }
    
    private BinarySolution greedyHillClimb(int index) {
        greedyHillclimbCalls++;
        int bestIndex = -1;
        double bestValue = solutionFitness[index];
        for (int neighbourIndex : neighbourhoodIndices[index]) {
            if (solutionFitness[neighbourIndex]>bestValue) {
                bestIndex = neighbourIndex;
                bestValue = solutionFitness[neighbourIndex];
            }
        }
        if (bestIndex!=-1){
            return greedyHillClimb(bestIndex);
        }
        return designs[index];
    }
}
