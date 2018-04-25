package lons.examples;


/**
 * Write a description of class ConcreteBinarySolution here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ConcreteBinarySolution extends BinarySolution
{
    private boolean[] design;
    private int index = -1;
    private ConcreteBinarySolution(boolean[] design) {
        this.design = design;
    }
    
    @Override
    public int getNumberOfElements(){
        return design.length;
    }
    
    @Override
    public boolean[] getDesignVector() {
        return design;
    }
    
    @Override
    public boolean getDesignVariable(int index) {
        return design[index];
    }
    
    @Override
    public int getIndex() {
        if (index==-1) {
            index = BinaryProblem.getIndexOfBitString(design);
        }   
        return index;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConcreteBinarySolution) 
            if (((ConcreteBinarySolution) obj).getIndex()==this.getIndex())
                if (this.sameState((ConcreteBinarySolution) obj))
                    return true;
        
        return false;
    }
    
    private boolean sameState(ConcreteBinarySolution solution) {
        for (int i=0; i<design.length; i++)
            if (design[i]!=solution.design[i])
                return false;
        return true;
    }
    
    public static BinarySolution constructBinarySolution(boolean[] design) {
        return new ConcreteBinarySolution(design);
    }
    
}
