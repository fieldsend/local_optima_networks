package lons;


import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

/**
 * Write a description of class LONGenerator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LONGenerator
{
    static int greedyHillclimbCalls;
    
    
    public static <K extends Solution> void naiveExhaustiveLON(Problem<K> problem, Neighbourhood<K> neighbourhood, HashMap<K,Weight> optimaBasins,HashMap<K,HashMap<K,Weight>> mapOfAdjacencyListAndWeight) {
        exhaustiveLON(problem,neighbourhood,optimaBasins,mapOfAdjacencyListAndWeight,false);
    }
    
    public static <K extends Solution> void exhaustiveLON(Problem<K> problem, Neighbourhood<K> neighbourhood, HashMap<K,Weight> optimaBasins,HashMap<K,HashMap<K,Weight>> mapOfAdjacencyListAndWeight) {
        exhaustiveLON(problem,neighbourhood,optimaBasins,mapOfAdjacencyListAndWeight,true);
    }
    
    public static <K extends Solution> void naiveSampledLON(Problem<K> problem, Neighbourhood<K> neighbourhood, HashMap<K,Weight> optimaBasins,HashMap<K,HashMap<K,Weight>> mapOfAdjacencyListAndWeight, int numberOfSamples) {
        System.out.print("\n cleaning...");
        clean();
        ArrayList<K> optima = new ArrayList<>(); // list of optima
        for (int i=0; i<numberOfSamples; i++) {
            K randomDesign = problem.getRandomSolution();
            ArrayList<K> visitedOnPath = new ArrayList<>();
            K optimum = greedyHillclimb(randomDesign,problem, neighbourhood);
            if (optimaBasins.containsKey(optimum)) {
                optimaBasins.get(optimum).increment();
            } else {
                optimaBasins.put(optimum,new Weight(1));
            }
        }
    }
        
    
    public static <K extends Solution> void sampledLON(Problem<K> problem, Neighbourhood<K> neighbourhood, HashMap<K,Weight> optimaBasins,HashMap<K,HashMap<K,Weight>> mapOfAdjacencyListAndWeight, int numberOfSamples) {
        System.out.print("\n cleaning...");
        clean();
        HashSet<K> optima = new HashSet<>(); // list of optima
        HashMap<K,K> mapFromSolutionToOptima = new HashMap<>(numberOfSamples); // map from each solution queried on a path to its optima
        HashMap<K,Double> fitnesses  = new HashMap<>(numberOfSamples); // map from queries designs to their fitness 
        for (int i=0; i<numberOfSamples; i++) {
            K randomDesign = problem.getRandomSolution();
            //System.out.print(randomDesign.hashcode()+" ");
            ArrayList<K> visitedOnPath = new ArrayList<>();
            K optimum = greedyHillclimb(randomDesign,problem, neighbourhood, fitnesses, mapFromSolutionToOptima, visitedOnPath);
            optima.add(optimum);
            for (K design : visitedOnPath) 
                if (!mapFromSolutionToOptima.containsKey(design))
                    mapFromSolutionToOptima.put(design,optimum);
            
            if (optimaBasins.containsKey(optimum)) {
                optimaBasins.get(optimum).increment();
            } else {
                optimaBasins.put(optimum,new Weight(1));
            }
        }
        System.out.println(optima.size());
        System.out.println(fitnesses.size());
        System.out.println(mapFromSolutionToOptima.size());
    }
    //helper methods
    
    
    private static void clean() {
        greedyHillclimbCalls=0;
    }
    
    /*
     * Fills optimaBasins hashmap with the optima solutions and the corresponding basin sizes, and 
     * mapOfAdjacencyList with the corresponding list of linked (outward) optima and weights (in the 
     * Edge instances). 
     */
    private static <K extends Solution> void exhaustiveLON(Problem<K> problem, Neighbourhood<K> neighbourhood , HashMap<K,Weight> optimaBasins,HashMap<K,HashMap<K,Weight>> mapOfAdjacencyListAndWeight, boolean efficient) {
        System.out.print("\n cleaning...");
        clean();
        ArrayList<K> optima = new ArrayList<>(); // list of optima
        K[] designs = problem.getExhaustiveSetOfSolutions(); // all designs
        HashMap<K,K> mapFromSolutionToOptima = new HashMap<>(designs.length); // map from each solution to its optima
        System.out.print("getting fitnesses...");
        double[] solutionFitness = getAllQualities(designs,problem); // fitnesses of all solutions, assumed maximisation
        System.out.print("getting neighbourhood indices...");
        int[][] neighbourhoodIndices = getNeighboursAndSetOptima(neighbourhood,designs,solutionFitness,optima);
        System.out.print("getting basins...");
        fillBasins(designs,optima,optimaBasins,mapFromSolutionToOptima,solutionFitness,neighbourhoodIndices,efficient);
        System.out.print("setting edges...");
        setEdges(neighbourhood,designs,optima,mapOfAdjacencyListAndWeight,mapFromSolutionToOptima);
    }
    
    
    private static <K extends Solution> K greedyHillclimb(K designToSearchFrom, Problem<K> problem, Neighbourhood<K> neighbourhood) {
        greedyHillclimbCalls++;
        K nextOnPath = designToSearchFrom;
        double currentFitness = problem.getQuality(designToSearchFrom);
        K[] neighbours = neighbourhood.neighbouringSolutions(designToSearchFrom);
        for (K neighbour : neighbours) {
            double fitness = problem.getQuality(neighbour);
            if (fitness > currentFitness) {
                currentFitness = fitness;
                nextOnPath = neighbour;
            }
        }
        if (nextOnPath==designToSearchFrom)
            return designToSearchFrom;
        return greedyHillclimb(nextOnPath, problem, neighbourhood);    
    }
    
    private static <K extends Solution> K greedyHillclimb(K designToSearchFrom, Problem<K> problem, Neighbourhood<K> neighbourhood, HashMap<K,Double> fitnesses, HashMap<K,K> mapFromSolutionToOptima, ArrayList<K> visitedOnPath) {
        if (mapFromSolutionToOptima.containsKey(designToSearchFrom)){
            return mapFromSolutionToOptima.get(designToSearchFrom);
        } else {
            greedyHillclimbCalls++;
            visitedOnPath.add(designToSearchFrom);
            K nextOnPath = designToSearchFrom;
            Double currentFitness = fitnesses.get(designToSearchFrom);
            if (currentFitness==null) {
                currentFitness = problem.getQuality(designToSearchFrom);
                fitnesses.put(designToSearchFrom,currentFitness);
            }
            K[] neighbours = neighbourhood.neighbouringSolutions(designToSearchFrom);
            for (K neighbour : neighbours) {
                Double fitness = fitnesses.get(neighbour);
                if (fitness==null) {
                    fitness = problem.getQuality(neighbour);
                    fitnesses.put(neighbour,fitness);
                } 
                if (fitness.doubleValue() > currentFitness.doubleValue()) {
                    currentFitness = fitness;
                    nextOnPath = neighbour;
                }
            }
            if (nextOnPath==designToSearchFrom)
                return designToSearchFrom;
            return greedyHillclimb(nextOnPath, problem, neighbourhood, fitnesses, mapFromSolutionToOptima,visitedOnPath);    
        }
    }
    
    private static <K extends Solution> void setEdges(Neighbourhood<K> neighbourhood, K[] designs, ArrayList<K> optima, HashMap<K,HashMap<K,Weight>> mapOfAdjacencyListAndWeight, HashMap<K,K> mapFromSolutionToOptima) {
        for (K optimum : optima)
            mapOfAdjacencyListAndWeight.put(optimum,new HashMap<K,Weight>()); //initialise maps to adjacent optima, and their edge weights
        
        for (int i=0; i< designs.length; i++) { // for each design
            K currentOptima = mapFromSolutionToOptima.get(designs[i]);
            for (int j : neighbourhood.indicesOfNeighbouringSolutions(designs[i])) { //get design neighbours
                K adjacentOptima = mapFromSolutionToOptima.get(designs[j]);
                HashMap<K,Weight> linkedOptima = mapOfAdjacencyListAndWeight.get(currentOptima); //get map of adjacent optima
                // now add neighbouring optima if not present, or update the weight
                if (linkedOptima.containsKey(adjacentOptima))
                    linkedOptima.get(adjacentOptima).increment();
                else
                    linkedOptima.put(adjacentOptima,new Weight(1));
            }
        }
    }
    
    private static <K extends Solution> void fillBasins(K[] designs, ArrayList<K> optima, HashMap<K,Weight> optimaBasins,HashMap<K,K> mapFromSolutionToOptima, double[] solutionFitness, int[][] neighbourhoodIndices, boolean efficient) {
        // connect each optima to an empty initial basin
        for (int i=0; i<optima.size(); i++) {
            optimaBasins.put(optima.get(i), new Weight());
        }
        if (efficient == false) {
            // now compute basin sizes, by hillclimbing from every design
            for (int i=0; i< designs.length; i++) {
                K basinOptima = naiveGreedyHillClimb(designs,i,solutionFitness,neighbourhoodIndices); // find optima of ith design
                mapFromSolutionToOptima.put(designs[i],basinOptima); // store optima for ith design
                optimaBasins.get(basinOptima).increment(); // increment basin size of optima
            }
        } else { // now compute basin sizes, by hillclimbing from every design, an utilising previous visits
            for (int i=0; i< designs.length; i++) {
                ArrayList<K> visitedOnPath = new ArrayList<>();
                K basinOptima = efficientGreedyHillClimb(mapFromSolutionToOptima,visitedOnPath,designs,i,solutionFitness,neighbourhoodIndices); // find optima of ith design
                for (K s : visitedOnPath) {
                    mapFromSolutionToOptima.put(s,basinOptima);
                    optimaBasins.get(basinOptima).increment();
                }
            }
        }
    }
    
    
    private static <K extends Solution> K efficientGreedyHillClimb(HashMap<K,K> mapFromSolutionToOptima, ArrayList<K> visitedOnPath,K[] designs, int index, double[] solutionFitness, int[][] neighbourhoodIndices) {
        if (mapFromSolutionToOptima.containsKey(designs[index]))
            return mapFromSolutionToOptima.get(designs[index]);
        greedyHillclimbCalls++;
        int bestIndex = -1;
        double bestValue = solutionFitness[index];
        for (int neighbourIndex : neighbourhoodIndices[index]) {
            if (solutionFitness[neighbourIndex]>bestValue) {
                bestIndex = neighbourIndex;
                bestValue = solutionFitness[neighbourIndex];
            }
        }
        visitedOnPath.add(designs[index]);
        if (bestIndex!=-1){
            return efficientGreedyHillClimb(mapFromSolutionToOptima,visitedOnPath,designs,bestIndex,solutionFitness,neighbourhoodIndices);
        }
        return designs[index];
    }
    
    private static <K extends Solution> K naiveGreedyHillClimb(K[] designs, int index, double[] solutionFitness, int[][] neighbourhoodIndices) {
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
            return naiveGreedyHillClimb(designs,bestIndex,solutionFitness,neighbourhoodIndices);
        }
        return designs[index];
    }
    
    
    private static <K extends Solution> int[][] getNeighboursAndSetOptima(Neighbourhood<K> neighbourhood,K[] designs,double[] solutionFitness,ArrayList<K> optima){ 
        int[][] neighbourhoodIndices = new int[designs.length][];
        for (int i=0; i< designs.length; i++) {
            neighbourhoodIndices[i] = neighbourhood.indicesOfNeighbouringSolutions(designs[i]);
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
        return neighbourhoodIndices;
    }
    
    private static <K extends Solution> double[] getAllQualities(K[] designs, Problem<K> problem) {
        double[] solutionFitnesses = new double[designs.length]; // fitnesses of all solutions, assumed maximisation
        for (int i=0; i< designs.length; i++) 
            solutionFitnesses[i] = problem.getQuality(designs[i]);
        return solutionFitnesses;    
    }
    
}
