package lons;


/**
 * Write a description of class Main here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Main
{
    public static void main(String[] args) {
        int n = 24;
        int k = 1;
        int[] exactGreedyCalls = new int[100];
        int[] niaveGreedyCalls = new int[100];
        for (int i=0; i<100; i++) {
            NKModelProblem.setSeed(i);
            NKModelProblem nk = new NKModelProblem(n,k);
            ExactLocalOptimaNetwork a = new ExactLocalOptimaNetwork(nk); 
            exactGreedyCalls[i] = a.greedyHillclimbCalls;
            NiaveExactLocalOptimaNetwork b = new NiaveExactLocalOptimaNetwork(nk); 
            niaveGreedyCalls[i] = b.greedyHillclimbCalls;
        }
        System.out.println("exact...");
        for (int i=0; i<100; i++) 
            System.out.print(exactGreedyCalls[i]+ " ");
        System.out.println("");
        System.out.println("");
        System.out.println("exact niave...");
        for (int i=0; i<100; i++) 
            System.out.print(niaveGreedyCalls[i]+ " ");
        System.out.println("");
        System.out.println("");
            
    }
}
