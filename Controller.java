import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Controller
{
		private JFrame frame;
	    private GUI gui;
	    private KeyListener keyStroke;

	    AiThread ai;
		private Game game = new Game();
        private boolean PvP = false; // PvP = player v player

        private static int FRAME_WIDTH = 650;
        private static int FRAME_HEIGHT = 600;

        private JCheckBox box;

		public Controller()
		{
		        frame = new JFrame();
	            frame.setTitle("Connect Four");
	            frame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT + 30));
	            frame.setLocationRelativeTo(null);
	            frame.setResizable(false);
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	            gui = new GUI(FRAME_WIDTH, FRAME_HEIGHT, game);

	            keyStroke = new NumKeys();

	            gui.setFocusable(true);
	            gui.addKeyListener(keyStroke);
	            gui.requestFocusInWindow();

	            box = new JCheckBox("Player v Player");
	            box.setBounds(35, 35, 120, 20);
	            box.setActionCommand("PvP");
	            box.addActionListener(new ButtonListener());
	            box.addKeyListener(keyStroke);
	            gui.add(box);

	            frame.getContentPane().add(gui);
	            frame.setVisible(true);
		}

		public class ButtonListener implements ActionListener
		{
		        @Override
                public void actionPerformed(ActionEvent e) {
                        if (e.getActionCommand().equals("PvP")) {
                        		PvP = box.isSelected() ? true : false;
                        		gui.resetBoard();
                        		game.reset();
                        		frame.repaint();
                        }
                }
		}

		private class NumKeys implements KeyListener
		{
                @Override
                public void keyPressed(KeyEvent e)
                {
//              		reset board
	                    if (e.getKeyChar() == 'r') {
	                    		gui.resetBoard();
	                    		game.reset();
	                    		frame.repaint();
	                    }

//              		reset board & score
	                    if (e.getKeyChar() == 'c') {
	                    		gui.resetBoardScore();
	                    		game.reset();
	                    		frame.repaint();
	                    }

//              		if game is over return
	                    if (game.status() != 0)
	    						return;

//              		number input
	                    if (e.getKeyChar() > 48 && e.getKeyChar() < 56) {
	                    		boolean valid = false;
	                    		if (PvP || (!PvP && !game.player()))
	                    				valid = game.playerMove(e.getKeyChar() - 49);

	                    		frame.repaint();
	                    		if (!valid) return;

	                    		if (!PvP) {
	                        			ai = new AiThread();
		                        		ai.start();
	                    		}
	                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {}

                @Override
                public void keyTyped(KeyEvent e) {}
		}

		private class AiThread extends Thread
		{
				public void run()
				{
						game.cpuMove();
						frame.repaint();

						if (game.status() != 0) {
								gui.endScreen();
								frame.repaint();
						}
				}
		}
}