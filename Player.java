//This class contains functions relating to the players of the game.
public class Player 
{
	//Variables will be kept private and accessed via getters & setters methods.
	private int Score;
	private String Name;
	
	//Will remove.
	public int getScore()
	{
		return Score;
	}
	//Will remove.
	public void setScore(int s)
	{
		this.Score = s;
	}
	
	public String getName()
	{
		return Name;
	}
	
	public void setName(String n)
	{
		this.Name = n;
	}
	//Will remove.
	public boolean checkScore(int s)
	{
		if(s == 5)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
}
