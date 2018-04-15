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
}
