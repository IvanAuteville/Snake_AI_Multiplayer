package menus;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import snake.GameMode;
import snake.Renderer;
import snake.Snake;
import sound_engine.SoundManager;
import java.awt.Component;
import java.awt.Color;

public class GameOverDialog extends JDialog
{
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JPanel buttonPane;
    private JButton exitButton;
    private JFrame game;

    public GameOverDialog(Snake[] players, GameMode gameMode, Renderer renderer, JFrame mainWindow)
    {
	setAlwaysOnTop(true);
	setUndecorated(true);
	setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	getContentPane().setBackground(Color.BLACK);
	setResizable(false);
	SoundManager.play("gameover");

	game = renderer.getFrame();

	setTitle("                                                             GAME OVER");
	setBounds(100, 100, 500, 400); // 450 327
	contentPanel.setBackground(Color.BLACK);
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

	String out = "";

	out = players[0].getPlayerName() + " consiguio " + players[0].getScore() + " pts.\n";
	JLabel ScoreLabel = new JLabel(out);
	ScoreLabel.setForeground(Color.GREEN);
	ScoreLabel.setBackground(Color.BLACK);

	ScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
	ScoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

	out = "";
	if (players.length > 1)
	{
	    out = players[1].getPlayerName() + " consiguio " + players[1].getScore() + "pts.\n";

	}

	JLabel ScoreLabel2 = new JLabel(out);
	ScoreLabel2.setForeground(Color.GREEN);
	ScoreLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
	ScoreLabel2.setFont(new Font("Tahoma", Font.PLAIN, 18));
	ScoreLabel2.setHorizontalAlignment(SwingConstants.CENTER);

	out = "";
	if (players.length > 2)
	{
	    out = players[2].getPlayerName() + " consiguio " + players[2].getScore() + "pts.\n";

	}

	JLabel ScoreLabel3 = new JLabel(out);
	ScoreLabel3.setForeground(Color.GREEN);
	ScoreLabel3.setBackground(Color.BLACK);
	ScoreLabel3.setHorizontalAlignment(SwingConstants.CENTER);
	ScoreLabel3.setFont(new Font("Tahoma", Font.PLAIN, 18));

	out = "";
	if (players.length > 3)
	{
	    out = players[3].getPlayerName() + " consiguio " + players[3].getScore() + "pts.\n";

	}

	JLabel ScoreLabel4 = new JLabel(out);
	ScoreLabel4.setBackground(Color.BLACK);
	ScoreLabel4.setForeground(Color.GREEN);
	ScoreLabel4.setHorizontalAlignment(SwingConstants.CENTER);
	ScoreLabel4.setFont(new Font("Tahoma", Font.PLAIN, 18));
	GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
	gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(
		Alignment.TRAILING,
		gl_contentPanel.createSequentialGroup().addGap(22).addGroup(gl_contentPanel
			.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(ScoreLabel3, GroupLayout.PREFERRED_SIZE, 404, GroupLayout.PREFERRED_SIZE)
				.addComponent(ScoreLabel, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
				.addComponent(ScoreLabel2, GroupLayout.PREFERRED_SIZE, 404, GroupLayout.PREFERRED_SIZE))
			.addComponent(ScoreLabel4, GroupLayout.PREFERRED_SIZE, 404, GroupLayout.PREFERRED_SIZE))
			.addGap(12)));
	gl_contentPanel
		.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
			gl_contentPanel.createSequentialGroup().addContainerGap()
				.addComponent(ScoreLabel, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(ScoreLabel2, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(ScoreLabel3, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(ScoreLabel4, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
				.addContainerGap()));
	contentPanel.setLayout(gl_contentPanel);
	{
	    buttonPane = new JPanel();
	    buttonPane.setBackground(Color.BLACK);
	    {
		exitButton = new JButton("Salir");
		exitButton.setFocusPainted(false);
		exitButton.setFocusTraversalKeysEnabled(false);
		exitButton.setBackground(Color.GREEN);
		exitButton.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
			SoundManager.stopAll();
			game.dispose();
			mainWindow.dispose();
			dispose();
		    }
		});
		exitButton.setActionCommand("Cancel");
	    }
	}

	JLabel winnerLbl = new JLabel("");
	winnerLbl.setHorizontalAlignment(SwingConstants.CENTER);
	winnerLbl.setForeground(Color.GREEN);
	winnerLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
	winnerLbl.setBackground(Color.BLACK);

	if (gameMode.getGameMode().equals("TIME") || gameMode.getGameMode().equals("SCORE"))
	{
	    int maxScore = -1;
	    boolean draw = false;
	    String player = "";

	    for (Snake snake : players)
	    {
		int auxScore = snake.getScore();

		if (auxScore > maxScore)
		{
		    draw = false;
		    maxScore = auxScore;
		    player = snake.getPlayerName();
		} else if (auxScore == maxScore)
		{
		    draw = true;
		}
	    }

	    if (!draw && players.length != 1)
	    {
		winnerLbl.setText(player + " GANA LA PARTIDA!!");
	    } else if (players.length != 1)
	    {
		winnerLbl.setText("EMPATE!");
	    }
	} else
	{
	    ArrayList<String> namesAlive = new ArrayList<String>();
	    ArrayList<Integer> scoresAlive = new ArrayList<Integer>();

	    for (Snake snake : players)
	    {
		if (snake.isAlive())
		{
		    namesAlive.add(snake.getPlayerName());
		    scoresAlive.add(snake.getScore());
		}
	    }

	    if (namesAlive.size() == 1 && players.length != 1)
	    {
		winnerLbl.setText(namesAlive.get(0) + " GANA LA PARTIDA!!");
	    }
	}

	GroupLayout groupLayout = new GroupLayout(getContentPane());
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		.addGroup(groupLayout.createSequentialGroup()
			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(48).addComponent(winnerLbl,
					GroupLayout.PREFERRED_SIZE, 404, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup().addGap(26).addComponent(contentPanel,
					GroupLayout.PREFERRED_SIZE, 448, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup().addGap(33).addComponent(buttonPane,
					GroupLayout.PREFERRED_SIZE, 434, GroupLayout.PREFERRED_SIZE)))
			.addContainerGap(26, Short.MAX_VALUE)));
	groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		.addGroup(groupLayout.createSequentialGroup().addContainerGap()
			.addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
				GroupLayout.PREFERRED_SIZE)
			.addGap(18).addComponent(winnerLbl, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
			.addGap(11).addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
				GroupLayout.PREFERRED_SIZE)
			.addGap(7)));

	JButton menuButton = new JButton("Volver al Menu");
	menuButton.setFocusPainted(false);
	menuButton.setFocusTraversalKeysEnabled(false);
	menuButton.setBackground(Color.GREEN);
	menuButton.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		SoundManager.stopAll();
		SoundManager.play("click");
		SoundManager.play("menu", true);
		game.dispose();
		mainWindow.setVisible(true);
		dispose();
	    }
	});
	menuButton.setActionCommand("Cancel");
	GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
	gl_buttonPane
		.setHorizontalGroup(gl_buttonPane.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
			gl_buttonPane.createSequentialGroup().addContainerGap()
				.addComponent(menuButton, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
				.addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
				.addContainerGap()));
	gl_buttonPane.setVerticalGroup(gl_buttonPane.createParallelGroup(Alignment.LEADING)
		.addGroup(gl_buttonPane.createSequentialGroup()
			.addGroup(gl_buttonPane.createParallelGroup(Alignment.LEADING)
				.addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
				.addComponent(menuButton, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
	buttonPane.setLayout(gl_buttonPane);
	getContentPane().setLayout(groupLayout);

	addKeyListener(new KeyAdapter()
	{
	    @Override
	    public void keyPressed(KeyEvent e)
	    {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
		    game.dispose();
		    dispose();
		}
	    }
	});
    }
}