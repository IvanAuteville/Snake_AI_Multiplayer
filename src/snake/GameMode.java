package snake;

public class GameMode
{
    public String gameMode = null;
    public int score = 0;
    public int time = 0;

    public GameMode(String gameMode, int score, int time)
    {
	this.gameMode = gameMode;
	this.score = score;
	this.time = time;
    }

    public String getGameMode()
    {
	return gameMode;
    }

    public int getGameScore()
    {
	return score;
    }

    public int getGameTime()
    {
	return time;
    }
}