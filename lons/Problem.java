package lons;


/**
 * Write a description of interface Problem here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface Problem<K extends Solution>
{
    double getQuality(K s);
    K[] getExhaustiveSetOfSolutions();
    K getRandomSolution();
}
