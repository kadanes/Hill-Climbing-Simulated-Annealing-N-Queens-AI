import java.util.Random;

public class GameBoard {
	private int rows = 8;
	private int cols = rows;
	private Coordinate[] positions = new  Coordinate[rows];  
	private int heuristic = 0;
	Random rand = new Random();
	
	public Coordinate[] getPositions() {
		return positions;
	}

	public void setPositions(Coordinate[] positions) {
		this.positions = positions;
	}


    public int getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}
    
	public GameBoard() {
		for(int i = 0; i < cols; i+=1 ) {
		    int row = rand.nextInt(cols)+1;
		    positions[i] = new Coordinate(i+1, row);
		}
		heuristic = calcHeuristic(positions);
	}
	
	public GameBoard (Coordinate[] positions) {
		this.positions = positions;
		rows = positions.length;
		cols = rows;
		heuristic = calcHeuristic(this.positions);
	}
	
	int calcHeuristic(Coordinate[] positions) {
		int calcHeuristic = 0;
		for (int col = 0; col < positions.length; col+=1 ) {
			Coordinate attackerPosition = positions[col];
			
			for(int row = col+1; row < positions.length; row+=1) {
				Coordinate underAttackPosition = positions[row];

				if (attackerPosition.row == underAttackPosition.row) {
					calcHeuristic += 1;
					continue;
				}
				
				float slope = (underAttackPosition.row - attackerPosition.row)/(underAttackPosition.col - attackerPosition.col);
				
				if (slope == -1) {
					calcHeuristic += 1;
					continue;
				}
				
				if (slope == +1) {
					calcHeuristic +=1;
					continue;
				}
			}
		}
		return calcHeuristic;
	}
	
	boolean updateBoard() {
		
		for (int col = 0; col < positions.length; col+=1) {
			int orignalRow = positions[col].row;
			
			for (int row = 1; row <= positions.length; row+=1) {
				if (row != orignalRow) {
					Coordinate[] copiedPositions = copyPositions();
					
					copiedPositions[col].row = row;
					
					int newHeuristic = calcHeuristic(copiedPositions);
					
					if (newHeuristic < heuristic) {
						heuristic = newHeuristic;
						positions[col].row = row;
						return true;
					}	
				}
			}
		}
		return false;
	}
	
	Coordinate[] generateNeighbour() {
		int col = rand.nextInt(cols);
		int nextRow = rand.nextInt(2);
		Coordinate[] positionsCopy = copyPositions();
		if (nextRow == 1) {
			if (positionsCopy[col].row < rows) {
				positionsCopy[col].row += 1;
			} else if (positionsCopy[col].row > 0) {
				positionsCopy[col].row -= 1;
			}
		} else {
			 if (positionsCopy[col].row > 1) {
				positionsCopy[col].row -= 1;
			} else if (positionsCopy[col].row < rows) {
				positionsCopy[col].row += 1;
			}
		}
		
		return positionsCopy;
	}
	
	Coordinate[] copyPositions() {
		Coordinate[] copiedPositions = new Coordinate[positions.length];
		
		for (int col = 0; col < positions.length; col+=1) {
			Coordinate coord =  new Coordinate(positions[col].col, positions[col].row); 
			copiedPositions[col] = coord;
		}
		return copiedPositions;
	}

	void printBoard() {
		System.out.println("Heuristic: "+heuristic);
		for (int row = 0; row < positions.length; row+=1) {
			for(int col = 0; col < positions.length; col+=1) {
				if (positions[col].row == row+1) {
					System.out.print(" ♕ ");
				} else {
					System.out.print(" ~ ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	boolean checkIfSame(Coordinate[] newPositions) {
		boolean isSame = true;
		for(int i = 0; i < newPositions.length; i+=1) {
			if (positions[i].row != newPositions[i].row) {
				return false;
			}
		}
		return isSame;
	}
}
