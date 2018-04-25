package lons.examples;

import lons.Problem;

/**
 * Write a description of interface PermutationProblem here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface IntegerVectorProblem extends Problem<IntegerVectorSolution>
{
    static int getIndexOfIntegerVector(int[] x, int resolution) {
        int index = 0;
        int multiplier = 1;
        
        for (int i=0; i<x.length; i++) {
            index += x[i]*multiplier;
            multiplier *= resolution;
            if (multiplier==Integer.MAX_VALUE)
                break; // will be potential hash code clashes, but domain size is larger than max int
        }
        return index;  
    }
    
}
