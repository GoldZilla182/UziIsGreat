import java.util.Random;
//This class is responsible for the management of 2D arrays.
public class Board 
{
	//For the printing of a player's game board. 
	//Alessandro.
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
	
	public int[][] getBoard() { return board; }
	public void setBoard(int[][] b){ board = b; }
	//String array for identification of sea vehicles.
	private String[] shipNames = {"Aircraft Carrier", "Battleship", "Destroyer", "Submarine", "Patrol Boat"};
	//Integer array holding sizes of water-craft.
	private int[] shipSizes = {5,4,3,3,2};
	 		
	public String getShip(int x) { return shipNames[x]; }
	
	public boolean[] getAllFleet(){ return fleetIntact; };
	
	public boolean getFleet(int b)
	{
		return fleetIntact[b];
	}
	//Function for the manual placement of ships - William & Alessandro.
	public boolean placeShip(String x, int y, String x1, int y1, int Index, int[][] board)
	{
        //Alessandro
		char z;
        int r = 0;
        char z1;
        int r1 = 0;

        boolean Flag = false;
        boolean FlagInitialOverwritingY;
        boolean FlagInitialOverwritingX;

        try {
            z = x.charAt(0);
            r = (int) z;
            z1 = x1.charAt(0);
            r1 = (int) z1;

            FlagInitialOverwritingY = (board[r - 65][y] == 0 || board[r - 65][y1] == 0); //If they are in the same y axis but it's already occupied.
            FlagInitialOverwritingX = (board[r - 65][y] == 0 || board[r1 - 65][y] == 0); //If they are in the same x axis but it's already occupied;
        }
        catch (Exception e){
            System.out.println("Oops");
            FlagInitialOverwritingX = false;
            FlagInitialOverwritingY= false;
        }


        boolean inputsRs = ((r-65 >= 0 && r-65 <= 9) && (r-65 >= 0 && r-65 <= 9)); //Check if inputs are ok.
        boolean inputsYs = ((y >= 0 && y <= 9) && (y1 >= 0 && y1 <= 9));


        //William   
        if (((r - 65) == (r1 - 65) || y == y1) && (FlagInitialOverwritingX && FlagInitialOverwritingY) && (inputsRs && inputsYs)) //Verifies provided coordinates confine to defined parameters.
            {
                Flag = (board[r1 - 65][y] == 0); // check the front first / Will be used to 'flag' an overwriting placement.
                //Horizontal placement, that is x == x1.
                if ((r - 65) == (r1 - 65) && Math.abs(y1 - y) == (shipSizes[Index - 1] - 1) && board[r1 - 65][y] == 0)
                {
                    for (int i = y1; i != y; i += (y - y1) / (shipSizes[Index - 1] - 1))
                    {
                        Flag = Flag && (board[r1 - 65][i] == 0);
                    }
                    if (Flag) {
                        for (int i = y1; i != y; i += (y - y1) / (shipSizes[Index - 1] - 1))
                        {
                            board[r - 65][i] = Index;
                        }
                        board[r - 65][y] = Index; // assign the front
                    } else
                    {
                        Flag = false;
                    }
                }
                //Vertical placement, that is y == y1.
                else if ((y) == (y1) && Math.abs((r - 65) - (r1 - 65)) == (shipSizes[Index - 1] - 1) && board[r1 - 65][y] == 0)
                {
                    for (int i = r1 - 65; i != r - 65; i += ((r - 65) - (r1 - 65)) / (shipSizes[Index - 1] - 1))
                    {

                        Flag = Flag && (board[i][y] == 0);
                    }
                    if (Flag)
                    {
                        for (int i = r1 - 65; i != r - 65; i += ((r - 65) - (r1 - 65)) / (shipSizes[Index - 1] - 1))
                        {
                            board[i][y] = Index;
                        }
                        board[r - 65][y] = Index; // assign the front
                    }
                    else
                    {
                        Flag = false;
                    }
                }
                else
                {
                    Flag = false;
                }

                if (!Flag)
                {
                    System.out.println("You are overwriting your boats, change coordinates.");
                }
            }
            else
            {
                System.out.println("The grid inputs entered are invalid." + "\n"
                        + "You are probably overwriting your initial point or end point of a boat with another boat." + "\n"
                        + "Note: Only vertical and horizontal placements are allowed." + "\n"
                        + "Remember to use A 〜 J for the rows and 0 〜 9 for the columns." + "\n");
            }
        return Flag;
	}
	//Function providing the option to automatically place sea vehicles - Alessandro.
	public void automaticPlaceShip(int Index, int[][] board)
    {
	    int size = shipSizes[Index-1];

        Random rdn = new Random();
        int horizontalVertical = rdn.nextInt(2); //if 0 we will set the ship horizontally, if 1 vertically.

        if(horizontalVertical == 0)
        {
            int x = 0; // 0 to 9
            int y = 0; // 0 to 5, +4 so 4 to 9;
            int temp = 0;
            for(int c = 0; c < size; c++)
            {
                if(c == 0)
                {
                    x = rdn.nextInt(10); // 0 to 9
                    y = rdn.nextInt(5) + size - 1; // 0 to 5, +4 so 4 to 9;
                    temp = y;
                }

                if(board[x][temp] != 0)
                {
                    x = rdn.nextInt(10); // 0 to 9
                    y = rdn.nextInt(5)+size-1; // 0 to 5, +4 so 4 to 9;
                    c = -1;
                    temp = y;
                }
                temp--;
            }
                for (int c = 0; c < size; c++)
                {
                    board[x][y] = Index;
                    y--;
                }
        }
        else
        {
            int x = 0;
            int y = 0;
            int temp = 0;
            for(int c = 0; c < size; c++)
            {
                if(c == 0)
                {
                    x = rdn.nextInt(5) + size - 1; // 0 to 5, +4 so 4 to 9
                    y = rdn.nextInt(10); // 0 to 9
                    temp = x;
                }

                if(board[temp][y] != 0)
                {
                    x = rdn.nextInt(5)+size-1; // 0 to 5, +4 so 4 to 9
                    y = rdn.nextInt(10); // 0 to 9
                    c = -1;
                    temp = x;
                }
                temp--;
            }
            for (int c = 0; c < size; c++)
            {
                board[x][y] = Index;
                x--;
            }
        }
    }
	//Used to determine if a strike is successful - William.
	public boolean checkStrike(String x, int y, int[][] board, int[][] board2)
	{
		char r = x.charAt(0);
		int charNum = (int) r;
		
		if(board[charNum - 65][y] >= 1 && board[charNum - 65][y] <= 5)
		{
			board[charNum - 65][y] = 6; //Sets the hit location to an 'H'.
            if(board2[charNum - 65][y] == 0)
            {
                board2[charNum - 65][y] = 6; //Sets location to an 'X', indicating a missed strike on the array.
            }
			System.out.println("Hit");
			return true;
		}
		else
		{
			board[charNum - 65][y] = 7; //Sets location to an 'X', indicating a missed strike on the array.
            if(board2[charNum - 65][y] == 0)
            {
                board2[charNum - 65][y] = 7; //Sets location to an 'X', indicating a missed strike on the array.
            }
			System.out.println("Miss.");
			return false;
		}
		
		
	}
	//A strike has occurred, method to investigate entire fleet status - William
	public void trueStrike(int[][] board, boolean[] fleetIntact)
	{
		for(int k = 0; k < fleetIntact.length; k++)
		{
			fleetIntact[k] = false;
		}
		for(int[] i: board)
		{
			for(int j: i)
			{
				if(j == 5)
				{
					fleetIntact[4] = true;
					break;
				}
				
			}
			//Procedure to break outer loop.
			if(fleetIntact[4])
			{
				break;
			}
		}
		
		for(int[] i: board)
		{
			for(int j: i)
			{
				if(j == 4)
				{
					fleetIntact[3] = true;
					break;
				}
				
			}
			if(fleetIntact[3])
			{
				break;
			}
		}
		
		for(int[] i: board)
		{
			for(int j: i)
			{
				if(j == 3)
				{
					fleetIntact[2] = true;
					break;
				}
				
			}
			
			if(fleetIntact[2])
			{
				break;
			}
			
		}
		
		for(int[] i: board)
		{
			for(int j: i)
			{
				if(j == 2)
				{
					fleetIntact[1] = true;
					break;
				}
				
			}
			
			if(fleetIntact[1])
			{
				break;
			}
		}
		
		for(int[] i: board)
		{
			for(int j: i)
			{
				if(j == 1)
				{
					fleetIntact[0] = true;
					break;
				}
				
			}
			
			if(fleetIntact[0])
			{
				break;
			}
		}
	}
	
	private int[][] board = new int[10][10];
	//Boolean array, will be used to indicate fleet status.
	private boolean[] fleetIntact = new boolean[5];
}

