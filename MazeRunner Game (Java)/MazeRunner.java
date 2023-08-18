import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class MazeRunner {
	public static int highscore = 0;
	public static int score = 0;
	public static int numberOfSteps = 0;
	public static boolean timeUp = false;
	public static long timeTaken = 0;

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Boolean exit = true;
		while (exit) {
			//Printing Menu
			System.out.println("\n-------- Maze Runner --------");
			System.out.println("\na. Play Game\nb. Instructions\nc. Credits\nd. High Score\ne. Exit\n");
			String menuinput = scan.nextLine();
			switch (menuinput) {
				case "a":
					boolean playAgain = false;
					do {
						playAgain = false;
						playGame();
						System.out.println("\nPress y to play again or press any other key to go to menu");
						char input1 = scan.next().charAt(0);
						if (input1=='y'){
							playAgain = true;
						}
					} while (playAgain);
					break;
				case "b":
					showInstructions();
					break;
				case "c":
					showCredits();
					break;
				case "d":
					showHighScore(highscore);
					break;
				case "e":
					exit = exitGame();
					break;
				default:
					System.out.println("\nInvaild Input! Enter Again");
			}

		}

		scan.close();

	}

	// Functions

	// 1. initializeMaze

	public static char[][] initializeMaze(int n) {

		char[][] maze = new char[n][n];
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				if (i == 0 || j == 0 || i == n - 1 || j == n - 1 || i == 2 || i == 4) {
					maze[i][j] = '#';
				} else {
					maze[i][j] = '.';
				}
			}
		}
		maze[2][4] = '.';
		maze[4][3] = '.';
		maze[n - 2][n - 2] = 'E';
		maze[1][1] = 'P';

		return maze;
	}

	// 2. PrintMaze

	public static void PrintMaze(char[][] maze) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				System.out.print(maze[i][j] + " ");
			}
			System.out.println();
		}
	}

	// 3. isValidMove
	public static boolean isValidMove(int newX, int newY) {
		char[][] maze = initializeMaze(7);
		if (maze[newX][newY] == '#') {
			return false;
		} else {
			return true;
		}
	}

	// 4. movePlayer
	public static char[][] movePlayer(String move, int[] pPosition, char[][] maze) {
		int[] oldpPosition = { pPosition[0], pPosition[1] };
		switch (move) {
			case "w":
				pPosition[0]--;
				break;
			case "s":
				pPosition[0]++;
				break;
			case "a":
				pPosition[1]--;
				break;
			case "d":
				pPosition[1]++;
				break;
			case "n":
				maze[0][0] = 'n';
				return maze;
			default:
				System.out.println("\nInvaild Move! Enter Again");
				return maze;
		}

		if (isValidMove(pPosition[0], pPosition[1])) {
			maze[oldpPosition[0]][oldpPosition[1]] = '.';
			maze[pPosition[0]][pPosition[1]] = 'P';
			numberOfSteps++;
		} else {
			pPosition[0] = oldpPosition[0];
			pPosition[1] = oldpPosition[1];
			System.out.println("\n!!Can't Go through a wall!!\n");

			return maze;
		}
		if (hasPlayerWon(pPosition[0], pPosition[1])) {
			System.out.println("\n!! Congratulations You Won !!\n");
			maze[0][0] = 'x';
		}
		return maze;
	}


	// 5. hasPlayerWon()

	public static boolean hasPlayerWon(int newX, int newY) {
		char[][] maze = initializeMaze(7);
		if (maze[newX][newY] == 'E') {
			return true;
		} else {
			return false;
		}
	}

	// 6. playGame
	public static void playGame() {
		System.out.println("\nNew Game Started\n");
		Scanner scan1 = new Scanner(System.in);
		char[][] maze = initializeMaze(7);
		int[] playerPosition = { 1, 1 };
		score = 0;numberOfSteps = 0;timeTaken = 0;timeUp = false;
		Boolean end = true;

		// Timer
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// This code will be executed when the timer runs out
				System.out.println("Time's up!\nYou Lose. Enter any key to see result.");
				timeUp = true;
			}
		}, 30000);
		long start = System.currentTimeMillis();
		while (end) {
			PrintMaze(maze);
			System.out.println("\nMove your player using (w/a/s/d) or press (n) to start new game.\n");
			String moveinput = scan1.nextLine();
			if (timeUp == false) {
				maze = movePlayer(moveinput, playerPosition, maze);	
			}
			if (maze[0][0] == 'x' || timeUp == true || maze[0][0] == 'n') {
				if (timeUp == false && maze[0][0] != 'n') {
				long endTime = System.currentTimeMillis();
				timeTaken = (endTime - start)/1000;}
				end = false;
				updateScore();
				displayResult();
				timer.cancel();
			}
		}
		

	}

	// 7. displayResult()
	public static void displayResult() {
		System.out.println("\nScore: " + score + "\nHigh Score: " + highscore + "\nSteps Taken: " + numberOfSteps + "\nTime Taken: " + timeTaken + " seconds");
	}

	// 9. updateScore()
	public static void updateScore() {
		if (timeTaken <= 5 && timeTaken > 1) {
			score = 300;
		} else if (timeTaken <= 15 && timeTaken > 10) {
			score = 150;	
		}else if (timeTaken <= 10 && timeTaken > 5) {
			score = 200;	
		}else if (timeTaken < 20 && timeTaken > 15) {
			score = 100;	
		}
		 else if (timeTaken > 20) {
			score = 50;
		}
		if (score > highscore) {
			highscore = score;
		}
	}

	// 9. startNewGame()
	public static char[][] startNewGame() {
		return initializeMaze(7);
	}

	// 10. showInstructions():
	public static void showInstructions() {
		System.out.println(
				"\n1.The maze will have walls (#) that are impassable obstacles, open paths (.) that you can move through, your starting position (P), and the exit point (E).\n\n2.Your moves will be considered valid if they do not hit any walls (#) or go outside the maze boundaries.\n\n3.The game will end only when you reach the exit (E).\n\n4.Move your player using (W/A/S/D).\n\n5. You have 30 seconds to complete or you lose.");
	}

	// 11. showCredits():
	public static void showCredits() {
		System.out.println("Name: 'Maze Runner'\nVersion: '1.0'\nLanguge: 'Java'\nDeveloper: 'Hamza Tarar'");
	}

	// 12. showHighScore()
	public static void showHighScore(int highscore) {
		System.out.println("High Score: " + highscore);
	}

	// 13. exitGame():

	public static boolean exitGame() {
		System.out.println("\nExiting Game, GoodBye!!");
		return false;
	}

	/// last
}
