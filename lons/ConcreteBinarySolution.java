package lons;


/**
 * Write a description of class ConcreteBinarySolution here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ConcreteBinarySolution implements BinarySolution
{
    private boolean[] design;
    private int index = -1;
    private ConcreteBinarySolution(boolean[] design) {
        this.design = design;
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
    public BinarySolution[] getNeighbours() {
        BinarySolution[] neighbours = new BinarySolution[design.length];
        boolean[][] bitStringNeighbours = getBitStringNeighbours();
        for (int i=0; i<neighbours.length; i++) {
            neighbours[i] = new ConcreteBinarySolution(bitStringNeighbours[i]);
        }
        return neighbours;
    }
    
    @Override
    public int[] getNeighbourIndices() {
        int[] indices = new int[design.length];
        boolean[][] bitStringNeighbours = getBitStringNeighbours();
        for (int i=0; i<indices.length; i++) {
            indices[i] = BinaryProblem.getIndexOfBitString(bitStringNeighbours[i]);
        }
        return indices;
    }
    
    private boolean[][] getBitStringNeighbours() {
        boolean[][] neighbours = new boolean[design.length][design.length];
        for (int i=0; i<design.length; i++) {
            for (int j=0; j<design.length; j++) {
                neighbours[i][j] = design[j];
                if (i==j)
                    neighbours[i][j] = !neighbours[i][j];
            }
        }
        return neighbours; 
    }
    
    public static BinarySolution constructBinarySolution(boolean[] design) {
        return new ConcreteBinarySolution(design);
    }
    
}
