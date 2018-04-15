package lons;


/**
 * Write a description of class BinaryHammingNeighbourhood here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BinaryHammingNeighbourhood implements Neighbourhood<BinarySolution>
{
    @Override
    public BinarySolution[] neighbouringSolutions(BinarySolution s){
        BinarySolution[] neighbours = new BinarySolution[s.getNumberOfElements()];
        boolean[][] bitStringNeighbours = getBitStringNeighbours(s);
        for (int i=0; i<neighbours.length; i++) {
            neighbours[i] = ConcreteBinarySolution.constructBinarySolution(bitStringNeighbours[i]);
        }
        return neighbours;
    }
    
    private boolean[][] getBitStringNeighbours(BinarySolution s) {
        boolean[][] bitNeighbours = new boolean[s.getNumberOfElements()][s.getNumberOfElements()];
        for (int i=0; i<s.getNumberOfElements(); i++) {
            for (int j=0; j<s.getNumberOfElements(); j++) {
                bitNeighbours[i][j] = s.getDesignVariable(j);
                if (i==j)
                    bitNeighbours[i][j] = !bitNeighbours[i][j];
            }
        }
        return bitNeighbours; 
    }
    
    @Override
    public int[] indicesOfNeighbouringSolutions(BinarySolution s){
        int[] neighbouringIndices = new int[s.getNumberOfElements()];
        boolean[][] bitStringNeighbours = getBitStringNeighbours(s);
        for (int i=0; i<neighbouringIndices.length; i++) {
            neighbouringIndices[i] = BinaryProblem.getIndexOfBitString(bitStringNeighbours[i]);
        }
        return neighbouringIndices;
    }
    
}
