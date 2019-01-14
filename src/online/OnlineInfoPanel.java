package online;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import snake.GameMode;
import snake.Snake;
import utils.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;

public class OnlineInfoPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private Image table;

    private JLabel player1lbl;
    private JLabel player2lbl;
    private JLabel player3lbl;
    private JLabel player4lbl;

    private JLabel score1lbl;
    private JLabel score2lbl;
    private JLabel score3lbl;
    private JLabel score4lbl;

    private JLabel infolbl;
    private JLabel modelbl;

    public OnlineInfoPanel(int height)
    {	
	try
	{
	    table = ImageIO.read(getClass().getClassLoader().getResource("table" + height + ".png"));
	} catch (IOException e)
	{
	    e.printStackTrace();
	}

	player1lbl = new JLabel("");
	player1lbl.setFont(new Font("Open Sans", Font.BOLD, 20));
	player1lbl.setForeground(new Color(255, 255, 255));

	player2lbl = new JLabel("");
	player2lbl.setForeground(Color.WHITE);
	player2lbl.setFont(new Font("Open Sans", Font.BOLD, 20));

	player3lbl = new JLabel("");
	player3lbl.setForeground(Color.WHITE);
	player3lbl.setFont(new Font("Open Sans", Font.BOLD, 20));

	player4lbl = new JLabel("");
	player4lbl.setForeground(Color.WHITE);
	player4lbl.setFont(new Font("Open Sans", Font.BOLD, 20));

	JLabel lblJugadorPuntaje = new JLabel("Jugador - Puntaje");
	lblJugadorPuntaje.setHorizontalAlignment(SwingConstants.LEFT);
	lblJugadorPuntaje.setForeground(Color.WHITE);
	lblJugadorPuntaje.setFont(new Font("Old English Text MT", Font.BOLD, 24));

	score1lbl = new JLabel("");
	score1lbl.setForeground(Color.WHITE);
	score1lbl.setFont(new Font("Open Sans", Font.BOLD, 20));

	score2lbl = new JLabel("");
	score2lbl.setForeground(Color.WHITE);
	score2lbl.setFont(new Font("Open Sans", Font.BOLD, 20));

	score3lbl = new JLabel("");
	score3lbl.setForeground(Color.WHITE);
	score3lbl.setFont(new Font("Open Sans", Font.BOLD, 20));

	score4lbl = new JLabel("");
	score4lbl.setForeground(Color.WHITE);
	score4lbl.setFont(new Font("Open Sans", Font.BOLD, 20));

	infolbl = new JLabel("INFORMACION");
	infolbl.setHorizontalAlignment(SwingConstants.LEFT);
	infolbl.setForeground(Color.WHITE);
	infolbl.setFont(new Font("Old English Text MT", Font.BOLD, 24));

	modelbl = new JLabel("MODO");
	modelbl.setVerticalAlignment(SwingConstants.TOP);
	modelbl.setHorizontalAlignment(SwingConstants.CENTER);
	modelbl.setForeground(Color.WHITE);
	modelbl.setFont(new Font("Old English Text MT", Font.BOLD, 24));

	GroupLayout groupLayout = new GroupLayout(this);
	groupLayout.setHorizontalGroup(
		groupLayout.createParallelGroup(Alignment.TRAILING)
			.addGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(30)
						.addComponent(modelbl, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblJugadorPuntaje, GroupLayout.PREFERRED_SIZE, 361, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGap(131)
							.addComponent(infolbl, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(92)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(player1lbl)
							.addComponent(player2lbl, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
							.addComponent(player3lbl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(player4lbl))
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(score4lbl, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addComponent(score3lbl, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addComponent(score2lbl, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addComponent(score1lbl, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))))
				.addContainerGap(32, Short.MAX_VALUE))
	);
	groupLayout.setVerticalGroup(
		groupLayout.createParallelGroup(Alignment.TRAILING)
			.addGroup(groupLayout.createSequentialGroup()
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(modelbl, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(infolbl, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
				.addGap(11)
				.addComponent(lblJugadorPuntaje, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
				.addGap(18)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addComponent(player1lbl)
					.addComponent(score1lbl, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(player2lbl, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(player3lbl, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(score2lbl, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(score3lbl, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
				.addGap(18)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addComponent(score4lbl, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addComponent(player4lbl, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addGap(34))
	);
	setLayout(groupLayout);
    }

    public void setInfoAndPlayers(Snake[] players, ArrayList<Color> colors, GameMode gameMode)
    {
	switch (gameMode.getGameMode())
	{
	case "SURVIVAL":
	    modelbl.setText("<html>El modo de juego es<br><center>Supervivencia</center></html>");
	    infolbl.setText("");
	    break;

	case "SCORE":
	    modelbl.setText("<html>El modo de juego es<br><center>Puntaje</center></html>");
	    //infolbl.setText("<html><center>Alcanza " + String.valueOf(gameMode.getGameScore()) + " pts.</center></html>");
	    infolbl.setText(String.valueOf(gameMode.getGameScore()) + " pts.");
	    break;

	case "TIME":
	    modelbl.setText("<html>El modo de juego es<br><center>Tiempo</center></html>");
	    infolbl.setText(String.valueOf(gameMode.getGameTime()) + ":00");
	    break;

	default:
	    break;
	}

	player1lbl.setForeground(colors.get(0));
	score1lbl.setForeground(colors.get(0));
	player1lbl.setText(players[0].getPlayerName());

	if (players.length > 1)
	{
	    player2lbl.setForeground(colors.get(1));
	    score2lbl.setForeground(colors.get(1));
	    player2lbl.setText(players[1].getPlayerName());
	}

	if (players.length > 2)
	{
	    player3lbl.setForeground(colors.get(2));
	    score3lbl.setForeground(colors.get(2));
	    player3lbl.setText(players[2].getPlayerName());
	}

	if (players.length > 3)
	{
	    player4lbl.setForeground(colors.get(3));
	    score4lbl.setForeground(colors.get(3));
	    player4lbl.setText(players[3].getPlayerName());
	}
    }
    
    public void setOnlineScores(Snake[] players, GameMode gameMode, Timer timer)
    {
	if(gameMode.getGameMode().equals("TIME"))
	{
	    infolbl.setText(timer.getMinutes() + ":" + (timer.getSeconds() < 10 ? "0" + timer.getSeconds() : timer.getSeconds()));	    
	}

	score1lbl.setText(String.valueOf(players[0].getScore()));

	if (players.length > 1)
	{
	    score2lbl.setText(String.valueOf(players[1].getScore()));
	}

	if (players.length > 2)
	{
	    score3lbl.setText(String.valueOf(players[2].getScore()));
	}

	if (players.length > 3)
	{
	    score4lbl.setText(String.valueOf(players[3].getScore()));
	}
    }

    @Override
    protected void paintComponent(Graphics g)
    {
	super.paintComponent(g);
	g.drawImage(table, 0, 0, null);
    }
}