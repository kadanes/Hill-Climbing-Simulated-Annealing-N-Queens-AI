import java.util.Random;

public class Runner {
	public static void main(String[] args) {
		
		Random rand = new Random();
		
		GameBoard gameBoard1 = new GameBoard();
		GameBoard gameBoard2 = new GameBoard();
		
		gameBoard2.setPositions(gameBoard1.copyPositions());
		gameBoard2.setHeuristic(gameBoard1.getHeuristic());


		System.out.println("Using Hill Climbing\n");
		
		gameBoard2.printBoard();

		
		int totalIterations = 0;
		
		gameBoard1.printBoard();
	
		while (gameBoard1.updateBoard()) {
			gameBoard1.printBoard();
			totalIterations += 1;
		}
		System.out.println("Reached optimum in "+totalIterations+" iterations.");


		System.out.println("Using Simulated Annealing\n");
		
		gameBoard2.printBoard();
		
		double temp = 1;
		double minTemp = 0.000001;
		double alpha = 0.9;
		int oldCost = gameBoard2.getHeuristic();
			
		totalIterations = 0;
		
		Coordinate[] positions = gameBoard2.copyPositions();
		
		
		while (temp > minTemp) {
			
			int iteration = 1;
			int maxIterations = 10000;
			
			while (iteration < maxIterations) {
				GameBoard newSol = new GameBoard(gameBoard2.generateNeighbour());
				int newCost = newSol.getHeuristic();
				
				double ap = Math.exp((oldCost-newCost)/temp);
					
				if (ap > rand.nextDouble()) {
					gameBoard2 = new GameBoard(newSol.copyPositions());
					oldCost = newCost;
					
				}
				iteration += 1;
				totalIterations += 1;
			}
			
			
			
			if (!gameBoard2.checkIfSame(positions)) {
			
				gameBoard2.printBoard();
				positions = gameBoard2.copyPositions();
			}
		
			temp = temp * alpha;
			
		}
		System.out.println("Reached optimum in "+totalIterations+" iterations.");

	}
}
