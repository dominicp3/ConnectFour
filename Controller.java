import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;

public class Controller
{
        private JFrame frame;
        private GUI gui;
        private KeyListener keyStroke;

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

        private boolean isGameOver()
        {
                if (game.status() != 0) {
                        gui.endScreen();
                        frame.repaint();
                        return true;
                }

                frame.repaint();
                return false;
        }

        public class ButtonListener implements ActionListener
        {
                @Override
                public void actionPerformed(ActionEvent e)
                {
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
                        // reset board
                        if (e.getKeyChar() == 'r') {
                                gui.resetBoard();
                                game.reset();
                                frame.repaint();
                        }

                        // reset board & score
                        if (e.getKeyChar() == 'c') {
                                gui.resetBoardScore();
                                game.reset();
                                frame.repaint();
                        }

                        // number input
                        if (e.getKeyChar() > 48 && e.getKeyChar() < 56) {
                                if (PvP || game.player())
                                        if (!game.playerMove(e.getKeyChar() - 49))
                                                return;

                                if (isGameOver())
                                        return;

                                if (!PvP)
                                        new AiThread().start();
                        }
                }

                @Override
                public void keyReleased(KeyEvent e) {}

                @Override
                public void keyTyped(KeyEvent e) {}
        }

        private class AiThread extends Thread
        {
                @Override
                public void run()
                {
                        game.cpuMove();
                        isGameOver();
                }
        }
}