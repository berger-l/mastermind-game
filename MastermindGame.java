//Mastermind Game - guess the correct four, auto-generated colors in less than 12 guesses and win the game!
import java.util.*;
public class MastermindGame {
	private static Scanner scanner = new Scanner(System.in);
	private static Random rand = new Random();
	private static String[] codeColors;
	private static  Hashtable<String, Integer> colorChoices = new Hashtable<>(){{
		put("black", 30);
		put("red", 31);
		put("green", 32);
		put("yellow", 33);
		put("blue", 34);
		put("magenta", 35);
		put("cyan", 36);
		put("white", 37);
	}};
	final static int MAXGUESSES = 12;
	private static boolean gameOver = false;
	public static void main(String[] args) {
		playGame();
	}
	enum Colors {
        BLACK,
        WHITE,
        BLANK;
		
		@Override
		 public String toString() {
		   return name().toLowerCase();
		 }
    }
	//Computer chooses the random four color code.
	public static void chooseCode() {
		Object[] colorArray = colorChoices.keySet().toArray();
		codeColors = new String[] {
				(String)colorArray[rand.nextInt(colorArray.length)],
				(String)colorArray[rand.nextInt(colorArray.length)],
				(String)colorArray[rand.nextInt(colorArray.length)],
				(String)colorArray[rand.nextInt(colorArray.length)]};
	}
	//Computer asks for user guess, sends it to be checked, and then checks to see if game is over.
	public static void playGame() {
		int guesses = 0;
		chooseCode();
		while (guesses < MAXGUESSES && !gameOver) {
			System.out.println("Select four of the following colors:");
			printColors(colorChoices.keySet().toArray());
			String colors = scanner.nextLine();
			String[] colorGuesses = colors.split("[\\s,]+");
			if (colorGuesses.length != 4 || !colorChoices.keySet().containsAll(Arrays.asList(colorGuesses))) {
				System.out.println("Invalid guess! Please try again.");
				System.out.println("------------------------------");
				continue;
			}
			printColors(colorGuesses);
			checkGuess(colorGuesses);
			guesses++;
			System.out.println("------------------------------");
		}
		if (!gameOver) {
			System.out.println("Out of guesses! The secret code was:");
			printColors(codeColors);
			System.out.println("------------------------------");
		}
		System.out.println();
		System.out.println("Would you like to play again??");
		System.out.println("Press 'y' to play again, 'n' to quit");
		String response = scanner.nextLine().strip();
		if (response.equals("y")) {  
			gameOver = false;
			System.out.println();
			playGame();
		}
		else {
			System.out.println("Game Over! You have successfully exited the game.");
			System.exit(0);
		}
	}
	//Computer checks user guess against code.
	public static void checkGuess(String[] colorGuesses) {
		if (Arrays.equals(codeColors, colorGuesses)) {
			System.out.println("Congrats! you guessed the secret code!!!");
			gameOver = true;
			return;
		}
		ArrayList<Colors> checked = new ArrayList<Colors>();
		String[] copyCode = Arrays.copyOf(codeColors, codeColors.length);
		//check for blacks;
		for (int i = 0; i < copyCode.length; i++) {
			if (copyCode[i].equals(colorGuesses[i])) {
				copyCode[i] = null;
				colorGuesses[i] = null;
				checked.add(Colors.BLACK);
			}
		}
		//check for whites;
		for (int i = 0; i < copyCode.length; i++) {
			if (copyCode[i] != null) {
				for (int j = 0; j < colorGuesses.length; j++) {
					if (copyCode[i] != null && copyCode[i].equals(colorGuesses[j])) {
						copyCode[i] = null;
						colorGuesses[j] = null;
						checked.add(Colors.WHITE);
					}
				}
				if (copyCode[i] != null) {
					checked.add(Colors.BLANK);
				}
			}
		}
		Collections.sort(checked);
		System.out.println(checked);
	}
	public static void printColors(Object[] colors) {
		for (var color: colors) {
			if(colorChoices.containsKey(color)) {
				System.out.printf("\u001b[%dm%s\u001b[0m ", colorChoices.get(((String) color).toLowerCase()), color );
			}
		}
		System.out.println();
	}
}