import java.util.*;
public class Battleship 
{
	//An emulation of the Battleship game using java.
	//William Akinsanya (2937067) & Alessandro Baccin (2937425).
	
	/*REMAINING ISSUES
	 *Save functionality, add option to automate placements.
	 * Add prompts for invalid entries.
	 */
	
	//Method to print game menu.
	public static int printMenu()
	{
		Scanner kb = new Scanner(System.in);
		System.out.println("Battleship - by William Akinsanya & Alessandro Baccin." +"\n"
				+ "Game Menu" +"\n"
				+ "1. Rules & Information (1)" +"\n"
				+ "2. New Game (2)" +"\n"
				+ "3. Save Game (3)" +"\n"
				+ "4. Quit Game (4)");

		int UserChoice = kb.nextInt();
		return UserChoice;
	}
	//Created method to print rules & information.
	public static void printRules()
	{
		System.out.println("Rules & Information" +"\n"
				+ "1. Battleship is a turn based guessing game consisting of two players." +"\n"
				+ "2. At the start of the game, each player will deploy a fleet of five unique water-crafts in a 10x10 grid."+"\n"
				+ "3. During a turn, a missile (specified by a grid input) will be launched into an opponents grid."+"\n"
				+ "4. Each water-craft has a specific hit-point value, determining the number of hits they can withstand."+"\n"
				+ "5. The game ends when a player destroys all opposing sea vehicles."+"\n"
				+ "6. Grid inputs are case-sensitive, only uppercase letters are accepted.");
		System.out.println("");
	}
	//Method allowing users to place their sea vehicles.
	public static void setShips(Board b, Player p)
	{
		Scanner kb = new Scanner(System.in);
		String x, x1;
		int y,y1;
		for(int index = 1; index < 6; index++)
		{	
			b.printBoard();
			boolean Flag = false;
			while(!Flag) {
				System.out.println(p.getName() + ", it is time to place your " + b.getShip(index - 1) + "\n"
						+ "Enter the row letter (uppercase only) for the start point of your " + b.getShip(index - 1));
				x = kb.nextLine();
				System.out.println("Enter the column number for the start point of your " + b.getShip(index - 1));
				y = kb.nextInt();
				kb.nextLine();
				System.out.println("Ok, now enter the row letter (uppercase only) for the end point of your " + b.getShip(index - 1));
				x1 = kb.nextLine();
				System.out.println("Complete this placement by providing the column number for the end point of your " + b.getShip(index - 1));
				y1 = kb.nextInt();
				kb.nextLine();
				Flag = b.placeShip(x, y, x1, y1, index, b.getBoard());
			}
		}
		b.printBoard();
	}

	public static void printResult(Player p1, Player p2, boolean decider)
	{
		if(decider)
		{
			System.out.println(p2.getName() + ", your entire fleet has been sunk." +"\n"
					+ p1.getName() + " has won the game.");
		}
		else
		{
			System.out.println(p1.getName() + ", your entire fleet has been sunk" + "\n"
					+ p2.getName() + " has won the game.");
		}
	}
	
	public static void main(String[] args) 
	{
		Scanner kb = new Scanner(System.in);
		boolean Game = true;
		
		//Encapsulating loop for program control.
		while(Game)
		{
			Player p1 = new Player();
			Player p2 = new Player();
			Board b1 = new Board();
			Board b2 = new Board();
			int UserChoice = printMenu();
			//User(s) opts to quit the game.
			if(UserChoice == 4)
			{
				System.out.println("We hoped you enjoyed playing Battleship."+"\n"+"William Akinsanya & Alessandro Baccin");
				Game = false;
			}
			
			else if(UserChoice == 1)
			{
				printRules();
			}

			else if(UserChoice == 3) //Save game option.
			{

			}

			//User(s) decide to play the game.
			else if(UserChoice == 2)
			{
				//Standard objects are created.
				boolean inGame = true;
				int y;
				String x;
				
				System.out.println("Welcome to Battleship" +"\n" + "Player 1, enter your name.");
				p1.setName(kb.nextLine());
				System.out.println("Player 2, enter your name.");
				p2.setName(kb.nextLine());
				
				//Placement phase version - Will A.
				setShips(b1, p1);
				System.out.println("");
				setShips(b2, p2);
				
				//This loops marks the beginning of a game.
				while(inGame)
				{
					//Striking phase (in progress).
					//Player 1 turn.
					b1.printBoard();
					System.out.println(p1.getName() + ", it is your turn to fire a shot." + "\n" 
					+ "Enter the letter corresponding to your chosen row (uppercase only).");
					x = kb.nextLine();
					System.out.println("Enter the number corresponding to your chosen column");
					y = kb.nextInt();
					kb.nextLine();
					
					//Only if a user has hit a water-craft - is further investigation required.
					boolean fleetStatus = true;
					//boolean[] fleet = b2.getAllFleet();
					if(b1.checkStrike(x, y, b2.getBoard()))
					{
						b1.trueStrike(b2.getBoard(), b2.getAllFleet());
						for(int i = 4; i > -1; i--)
						{
							if(!(b2.getFleet(i)))
							{
								System.out.println("You sunk a "+b2.getShip(i));
								
							}
						}
						
						for(int j = 0; j < 5; j++)
						{
							if(!(b2.getFleet(j)))
							{
								fleetStatus = false; 
							}
							else
							{
								fleetStatus = true;
								break;
							}
						}
						
						if(!fleetStatus)
						{
							boolean decider = true;
							printResult(p1, p2, decider);
							inGame = false;
							Game = false;
						}
					}
			
					
					//Player 2 turn........................................................................................................................
					b2.printBoard();
					System.out.println(p2.getName() + ", it is your turn." + "\n" 
					+ "Enter the letter corresponding to your chosen row (uppercase only).");
					x = kb.nextLine();
					System.out.println("Enter the number corresponding to your chosen column");
					y = kb.nextInt();
					kb.nextLine();
					
					boolean fleetStatus1 = true;
					if(b2.checkStrike(x, y, b1.getBoard()))
					{
						b2.trueStrike(b1.getBoard(), b1.getAllFleet());
						for(int i = 4; i > -1; i--)
						{
							if(!(b1.getFleet(i)))
							{
								System.out.println("You sunk a "+b1.getShip(i));
							}
						}
						
						for(int k = 0; k < 5; k++)
						{
							if(!(b1.getFleet(k)))
							{
								fleetStatus1 = false; 
							}
							else
							{
								fleetStatus1 = true;
								break;
							}
						}
						
						if(!fleetStatus1)
						{
							boolean decider = false;
							printResult(p1, p2, decider);
							inGame = false;
							Game = false;
						}
					}
						
			}
		}

	}
 }
}