package lons.examples;

import lons.*;
import cec2013.Func; // will need the cec2013 package to compile
import java.util.Random;

/**
 * DiscretisedCEC2013NichingProblem wraps and discretises the CEC2013 niching
 * competition test problems.
 * 
 * @author Jonathan Fieldsend
 * @version 11/07/2018
 */
public class DiscretisedCEC2013NichingProblem implements IntegerVectorProblem
{
    private static Random rng = new Random();
    private Func testFunction;
    private int resolutionPerVariable;
    private double[] stepSize;
    DiscretisedCEC2013NichingProblem(Func testFunction, int resolutionPerVariable) {
        this.testFunction = testFunction;
        this.resolutionPerVariable = resolutionPerVariable;
        stepSize = new double[testFunction.getDimension()];
        for (int i=0; i<stepSize.length; i++)
            stepSize[i] = (testFunction.getBounds().get(i).getUpper()-testFunction.getBounds().get(i).getLower())/(resolutionPerVariable-1.0);
    }
    
    
    @Override
    public double getQuality(IntegerVectorSolution s) {
        double[] realValuedDesign = new double[testFunction.getDimension()];
        for (int i=0; i<realValuedDesign.length; i++) {
            realValuedDesign[i] = testFunction.getBounds().get(i).getLower() + s.getDesignVariable(i)*stepSize[i];
            realValuedDesign[i] = realValuedDesign[i] > testFunction.getBounds().get(i).getUpper() ? testFunction.getBounds().get(i).getUpper() : realValuedDesign[i];
        }
            
        return testFunction.evaluate(realValuedDesign);
    }
    
    @Override
    public IntegerVectorSolution[] getExhaustiveSetOfSolutions(){
        int totalNumberOfUniqueDesigns = resolutionPerVariable;
        for (int i=1; i<testFunction.getDimension(); i++)
            totalNumberOfUniqueDesigns *= resolutionPerVariable;
        IntegerVectorSolution[] allSolutions = new IntegerVectorSolution[totalNumberOfUniqueDesigns];
        
        for (int i=0; i<totalNumberOfUniqueDesigns; i++) {
            allSolutions[i] = ConcreteIntegerVectorSolution.constructIntegerVectorSolution(convertIndexToVector(i, testFunction.getDimension(), resolutionPerVariable),resolutionPerVariable-1);
        }
        return allSolutions;
    }
    
    @Override
    public IntegerVectorSolution getRandomSolution() {
        int[] design = new int[testFunction.getDimension()];
        for (int i=0; i<design.length; i++)
            design[i] = (int) Math.floor(rng.nextDouble()*resolutionPerVariable);
            
        return ConcreteIntegerVectorSolution.constructIntegerVectorSolution(design,resolutionPerVariable-1); 
    }
    
    private static int[] convertIndexToVector(int index, int numVariables, int resolution) {
        int[] design = new int[numVariables];
        int multiplier = 1;
        
        for (int i=0; i<numVariables-1; i++) {
            multiplier *= resolution;
        }
        
        for (int j=0; j< numVariables; j++) {
            if (index >= multiplier) {
                design[j] = (int) Math.ceil((1.0+index-multiplier)/multiplier);
                index = index - design[j]*multiplier;
            } else {
                design[j] = 0;
            }
            multiplier /= resolution;  
        }
        return design;
    }
}
