import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GUI extends JPanel
{
		private Game game;

		private Color yellow = new Color(237, 223, 43);

        private JLabel redScoreText;
        private JLabel redNumText;
        private int redWinCount = 0;

        private JLabel yellowScoreText;
        private JLabel yellowNumText;
        private int yellowWinCount = 0;

        private JLabel[] buttons;
        private JLabel clear;
        private JLabel resetScore;
        private JLabel playerWonText;

//      lineX = 132
        private int lineX;

//      lineY = 135
        private int lineY;        

        public GUI(int frameWidth, int frameHeight, Game game)
        {
        		this.game = game;

                lineX = (frameWidth - 7*55) / 2;
                lineY = (frameHeight - 6*55) / 2;   
                this.setLayout(null);

                redScoreText = new JLabel("Red Score");
	            redScoreText.setBounds((lineX-60)/2, 80, 60, 20);
	            add(redScoreText);

	            redNumText = new JLabel("0");
	            redNumText.setBounds((lineX-16)/2, 110, 16, 20);
	            redNumText.setFont(new Font("", Font.BOLD, 15));
	            add(redNumText);

	            yellowScoreText = new JLabel("Yellow Score");
	            yellowScoreText.setBounds(536, 80, 74, 20);
	            add(yellowScoreText);

	            yellowNumText = new JLabel("0");
	            yellowNumText.setBounds(569, 110, 16, 20);
	            yellowNumText.setFont(new Font("", Font.BOLD, 15));
	            add(yellowNumText);

	            addButtons();
        }

        private void addButtons()
		{
		        buttons = new JLabel[7];
	                
                for (int i = 0; i < 7; i++) {
                        buttons[i] = new JLabel(Integer.toString(i + 1));
                        buttons[i].setBounds(lineX+(15/2)+16 + (i * 55), lineY + 6*55 + 10, 40, 20);
                        buttons[i].setFont(new Font("", Font.BOLD, 12));
                        add(buttons[i]);
                }

                clear = new JLabel("Clear (r)");
                clear.setBounds(lineX, buttons[0].getY() + 40, 123, 30);
                add(clear);

                resetScore = new JLabel("Clear score (c)");
                resetScore.setBounds(lineX, clear.getY() + 30, 123, 30);
                add(resetScore);
		}

        private void invisibleButtons()
		{
		        for (int i = 0; i < 7; i++)
                        buttons[i].setVisible(false);
		        
                resetScore.setVisible(false);
		}

		private void visibleButtons()
		{
		        for (int i = 0; i < 7; i++)
                        buttons[i].setVisible(true);
		        
                resetScore.setVisible(true);
		}

		public void endScreen()
		{
		        playerWonText = new JLabel();

	            if (game.status() == 1) {
	                    playerWonText.setText("Draw!");
	                    playerWonText.setBounds(260, 450, 130, 40);
	            } else if (game.player()) {
		            	playerWonText.setText("Yellow has won!");
	                    playerWonText.setBounds(247, 500, 157, 40);
	                    yellowNumText.setText(Integer.toString(++yellowWinCount));
	            } else {
		            	playerWonText.setText("Red has won!");
	                    playerWonText.setBounds(260, 500, 130, 40);
	                    redNumText.setText(Integer.toString(++redWinCount));
	            }

	            playerWonText.setFont(new Font("", Font.BOLD, 20));
	            add(playerWonText);

	            clear.setText("Play again? (r)");

	            invisibleButtons();
		}

		public void resetBoard()
		{
		        if (playerWonText != null)
		        		remove(playerWonText);
	            clear.setText("Clear (r)");
	            visibleButtons();
		}

		public void resetBoardScore()
		{
				redWinCount = 0;
	            yellowWinCount = 0;
	            redNumText.setText("0");
	            yellowNumText.setText("0");
	            resetBoard();
		}

        @Override
        public void paintComponent(Graphics g)
        {
        		char[][] grid = game.getState().getBoard();

                // Vertical lines
                for (int i = 0; i < 8; i++)
                        g.drawLine(lineX + (i * 55), lineY, lineX + (i * 55), lineY + 6*55);

                // Horizontal lines
                for (int i = 0; i < 7; i++)
                        g.drawLine(lineX, lineY + (i*55), lineX + 7*55, lineY + (i*55));

                if (game.player())
                        g.setColor(Color.RED);
                else
                        g.setColor(yellow);

                g.fillRect(275, 35, 100, 10);

                for (int j = 0; j < 7; j++) {
                        for (int i = 0; i < 6; i++) {
                                if (grid[i][j] != 0) {
                                        if (grid[i][j] == 'Y')
                                                g.setColor(yellow);
                                        else
                                                g.setColor(Color.RED);
                                        g.fillOval(lineX + 8 + (j * 55), lineY + 8 + 5*55 - (i * 55), 40, 40);
                                }
                        }
                }
        }
}