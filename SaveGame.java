//Class for save management.
import java.io.*;
public class SaveGame {
	//Alessandro
    public void SaveGame(Board board1, Board board2, Player player1, Player player2) 
{
        //Save game option
        int[][] board1S = board1.getBoard();
        int[][] board2S = board2.getBoard();
        try {
            DataOutputStream binOut = new DataOutputStream(new FileOutputStream("saveData.dat"));

            binOut.writeUTF(player1.getName());
            binOut.writeUTF(player2.getName());

            for(int c = 0; c < 10; c++)
            {
                for(int i = 0; i < 10; i++)
                {
                    binOut.writeInt(board1S[c][i]);
                }
            }

            for(int c = 0; c < 10; c++)
            {
                for(int i = 0; i < 10; i++)
                {
                    binOut.writeInt(board2S[c][i]);
                }
            }
            binOut.close();

        } catch (Exception e) {
            System.out.println("Unable to save game.");
        }
    }
    //William
    public boolean resumeGame()
    {
        boolean resume = true;
        //Resume Game option
        try
        {
        DataInputStream binIn = new DataInputStream(new FileInputStream("saveData.dat"));

        if(binIn.available() == 0)
        {
            System.out.println("There is no save data.");
            resume = false;
        }

        while(binIn.available() > 0)
        {
            player1Name = binIn.readUTF();
            player2Name = binIn.readUTF();

            for (int c = 0; c < 10; c++) {
                for (int i = 0; i < 10; i++) {
                    board1[c][i] = binIn.readInt();
                }
            }

            for (int c = 0; c < 10; c++)
            {
                for (int i = 0; i < 10; i++)
                {
                    board2[c][i] = binIn.readInt();
                }
            }
        }
        binIn.close();
        }
        catch(Exception e)
        {
            System.out.println("Oops something went wrong");
        }
        return resume;
    }
    public int[][] getBoard1(){ return board1; }

    public int[][] getBoard2(){ return board2; }

    public String getPlayer1(){ return player1Name; }

    public String getPlayer2(){ return player2Name; }

    private int[][] board1 = new int[10][10];
    private int[][] board2 = new int[10][10];
    private String player1Name, player2Name;


}
