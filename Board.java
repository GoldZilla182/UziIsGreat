//This class is responsible for the management of 2D arrays.
public class Board 
{
	public void printBoard()
	{
		int asciiValue = 65;
        char asciiChar = (char) asciiValue;
        //this.board[5][5] = 3;
        for(int i = 0; i < 11; i++)
        {
            int temp = 0;
            for(int j = 0; j < 11; j++) 
            {

                if( j != 0 && i == 0)
                {
                    System.out.print(temp + " ");
                    temp++;
                }
                else if(i > 0 && j == 0)
                {
                    System.out.print(asciiChar + "|");
                    asciiChar++;
                }
                else if( j == 0 && i == 0)
                {
                    System.out.print("  ");
                }
                else if(i >= 1 && j >=1)	//I need this statement because otherwise it would create an Underflow exception
                { 
                    if(this.board[i-1][j-1] >=1 && this.board[i-1][j-1] <=5) 
                    {
                        System.out.print("B|");
                    }
                    else if(this.board[i-1][j-1] == 6)
                    {
                        System.out.print("H|");
                    }
                    else if(this.board[i-1][j-1] == 7)
                    {
                        System.out.print("X|");
                    }
                    else
                    {
                        System.out.print(" |");
                    }
                }
            }
            System.out.println();
        }
	}
	
	public int[][] getBoard()
	{
		return board;
	}
	//String array for identification of sea vehicles.
	private String[] shipNames = {"Aircraft Carrier", "Battleship", "Destroyer", "Submarine", "Patrol Boat"};
	//Integer array holding sizes of water-craft.
	private int[] shipSizes = {5,4,3,3,2};
	//Boolean array, will be used to indicate fleet status.
	 		
	public String getShip(int x)
	{
		return shipNames[x];
	}
	
	public boolean getFleet(int b)
	{
		return fleetIntact[b];
	}
	
	public void placeShip(String x, int y, String x1, int y1, int Index, int[][] board)
	{
		char z = x.charAt(0);
		int r = (int) z;
		char z1 = x1.charAt(0);
		int r1 = (int) z1;

		boolean Flag = false;

		if(Flag == false) {
			if ((r - 65) == (r1 - 65) || y == y1) //Verifies provided coordinates confine to defined parameters.
			{
				Flag = (board[r1 - 65][y] == 0); // check the front first //Will be used to 'flag' an overwriting placement.
				//Horizontal placement, that is x == x1.
				if ((r - 65) == (r1 - 65) && Math.abs(y1 - y) == (shipSizes[Index - 1] - 1) && board[r1 - 65][y] == 0) {
					for (int i = y1; i != y; i += (y - y1) / (shipSizes[Index - 1] - 1)) {
						Flag = Flag && (board[r1 - 65][i] == 0);
					}
					if (Flag) {
						for (int i = y1; i != y; i += (y - y1) / (shipSizes[Index - 1] - 1)) {
							board[r - 65][i] = Index;
						}
						board[r - 65][y] = Index; // assign the front
					} else {
						Flag = false;
					}
				} else {
					Flag = false;
				}
				//Vertical placement, that is y == y1.
				if ((y) == (y1) && Math.abs((r - 65) - (r1 - 65)) == (shipSizes[Index - 1] - 1) && board[r1 - 65][y] == 0) {
					for (int i = r1 - 65; i != r - 65; i += ((r - 65) - (r1 - 65)) / (shipSizes[Index - 1] - 1)) {

						Flag = Flag && (board[i][y] == 0);
					}
					if (Flag) {
						for (int i = r1 - 65; i != r - 65; i += ((r - 65) - (r1 - 65)) / (shipSizes[Index - 1] - 1)) {
							board[i][y] = Index;
						}
						board[r - 65][y] = Index; // assign the front
					} else {
						Flag = false;
					}
				} else {
					Flag = false;
				}
			}
		}
		else
		{
			System.out.println("The grid inputs entered are invalid." + "\n"
					+ "Note: Only vertical and horizontal placements are allowed");
		}
	}
	//Used to determine if a strike is successful.
	public boolean checkStrike(String x, int y, int[][] board)
	{
		char r = x.charAt(0);
		int charNum = (int) r;
		
		if(board[charNum - 65][y] >= 1 && board[charNum - 65][y] <= 5)
		{
			board[charNum - 65][y] = 6; //Sets the hit location to an 'H'.
			System.out.println("Hit");
			return true;
		}
		else
		{
			board[charNum - 65][y] = 7; //Sets location to an 'X', indicating a missed strike on the array.
			System.out.println("Miss.");
			return false;
		}
		
		
	}
	//A strike has occurred, method to investigate entire fleet status.
	public void trueStrike(int[][] board)
	{
		for(int k = 0; k < fleetIntact.length; k++)
		{
			fleetIntact[k] = false;
		}
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; i < board[i].length; j++)
			{
				switch(board[i][j])
				{
				case 5:
					fleetIntact[4] = true;
					break;
				case 4:
					fleetIntact[3] = true;
					break;
				case 3:
					fleetIntact[2] = true;
					break;
				case 2:
					fleetIntact[1] = true;
					break;
				case 1:
					fleetIntact[0] = true;
					break;
				}
				
			}
		}
	}
	
	private int[][] board = new int[10][10];
	private boolean[] fleetIntact = new boolean[5];
}

