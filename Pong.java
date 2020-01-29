import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class Pong
{
	/**
	 * Runs the program.
	 * This main method should not change.  All of your code should be below
	 * it.  The guiApp() method is the entry point for your program.
	 */
	public static void main (String[] args)
	{
		javax.swing.SwingUtilities.invokeLater (new Runnable () {
			public void run ()
			{
				guiApp ();
			}
		});
	}


	/**
	 * STATIC VARIABLES A ND CONSTANTS
	 * Declare the objects and variables that I want to access across multiple methods
	 */
	// Holds the panel and its size
	static JPanel panel; 
	static int panelWidth = 800;
	static int panelHeight = 600;
	static int scoreLeft = 0;
	static int scoreRight = 0;
	// Will hold pictures of a rightPaddle and a leftPaddle
	static JLabel rightPaddle = new JLabel(new ImageIcon(new ImageIcon("newpaddle.png").getImage().getScaledInstance(30, 100, Image.SCALE_SMOOTH)));
	static JLabel leftPaddle = new JLabel(new ImageIcon(new ImageIcon("newpaddle.png").getImage().getScaledInstance(30, 100, Image.SCALE_SMOOTH)));
	static JLabel ball = new JLabel(new ImageIcon(new ImageIcon("ball.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
	// Stores the position of the rightPaddle and leftPaddle at their center
	static int xRightPaddle = 1260;
	static int yRightPaddle = 300;
	static int xLeftPaddle = 0;
	static int yLeftPaddle = 300;
	static int xBall = 640;
	static int yBall = 360;

	// How far to move the vehicles for each key press
	final static int MOVE_DISTANCE = 2;
	static int moveBallX = 2;
	static int moveBallY = 2;

	static boolean upClicked = false;
	static boolean downClicked = false;
	static boolean wClicked = false;
	static boolean sClicked = false;

	static Random randomGenerator = new Random();

	static int shipX;
	static int shipY;
	static JLabel pirateShip;
	static int score = 0;
	static JLabel rightLabel;
	static JLabel leftLabel;


	static Timer keyTimer;
	/**
	 * Runs the main window.
	 */
	private static void guiApp ()
	{
		// Create and set up the window.
		JFrame frame = new JFrame ("PONG!");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.setResizable (false);

		// The panel that will hold the components in the frame.
		JPanel panel = new JPanel ();
		panel.setPreferredSize(new Dimension(175, 175));

		// Making the panel use BorderLayout
		panel.setLayout(new BorderLayout());

		// Make the side panel
		JPanel sideBar = new JPanel();
		sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.PAGE_AXIS));
		sideBar.setPreferredSize(new Dimension(175, 300));
		sideBar.setBackground(new Color(235, 52, 208));
		sideBar.setOpaque(true);
		sideBar.setBorder(new EmptyBorder(20, 20, 20, 20));
		panel.add(sideBar, BorderLayout.EAST);

		// Make the score title
		JLabel scoreTitle = new JLabel("VAPORWAVE PONG");
		scoreTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		sideBar.add(scoreTitle);
		sideBar.add(Box.createRigidArea(new Dimension(135, 10)));



		// Add the filler "glue"
		sideBar.add(Box.createVerticalGlue());

		// Make sidebar title
		JLabel actionsLabel = new JLabel("Actions");
		actionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		actionsLabel.setHorizontalAlignment(JLabel.CENTER);
		actionsLabel.setVerticalAlignment(JLabel.CENTER);
		actionsLabel.setBackground(new Color(52, 235, 183));
		actionsLabel.setOpaque(true);

		sideBar.add(actionsLabel);
		sideBar.add(Box.createRigidArea(new Dimension(135, 10)));

		// Make sidebar buttons
		JButton newGameButton = new JButton("New Game");
		newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		newGameButton.addActionListener(new NewGameListener());
		sideBar.add(newGameButton);
		sideBar.add(Box.createRigidArea(new Dimension(135, 10)));



		JButton quitButton = new JButton("Quit");
		quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		quitButton.addActionListener(new QuitButtonListener());
		sideBar.add(quitButton);

		// Make the center "map" panel
		JLayeredPane mapPanel = new JLayeredPane();
		panel.add(mapPanel, BorderLayout.CENTER);



		// Add the panel to the frame
		frame.setContentPane(panel);

		//size the window.
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible (true);
	}




	private static class QuitButtonListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			int answer = JOptionPane.showConfirmDialog(null, "Leaving so soon?", "Quit?", JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION)
			{
				System.exit(0);
			}
		}
	}

	private static class NewGameListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{




			// Create and set up the window.
			JFrame frame = new JFrame("Play!");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


			// Create a panel to hold the components
			panel = new JPanel();
			panel.setPreferredSize(new Dimension(1280, 720));
			panel.setLayout(new BorderLayout());

			// Add the rightPaddle and leftPaddle
			JLayeredPane gamePanel = new JLayeredPane();
			panel.add(gamePanel, BorderLayout.CENTER);




			JLabel background = new JLabel(new ImageIcon("newbackground.jpg"));
			background.setBounds(0, 0, 1280, 720);
			gamePanel.add(background, Integer.valueOf(-100));

			rightLabel = new JLabel("0");
			rightLabel.setSize(100, 40);
			rightLabel.setLocation(1000, 25);
			rightLabel.setBackground(new Color(235, 52, 208));
			rightLabel.setOpaque(true);
			rightLabel.setHorizontalAlignment(JLabel.CENTER);
			gamePanel.add(rightLabel, Integer.valueOf(100));

			leftLabel = new JLabel("0");
			leftLabel.setSize(100, 40);
			leftLabel.setLocation(200, 25);
			leftLabel.setBackground(new Color(52, 235, 183));
			leftLabel.setOpaque(true);
			leftLabel.setHorizontalAlignment(JLabel.CENTER);
			gamePanel.add(leftLabel, Integer.valueOf(100));





			gamePanel.add(rightPaddle);
			rightPaddle.setBounds(xRightPaddle, yRightPaddle, 20, 100);
			gamePanel.add(leftPaddle);
			leftPaddle.setBounds(xLeftPaddle, yLeftPaddle, 20, 100);

			gamePanel.add(ball);
			ball.setBounds(xBall, yBall, 100,100);

			// Add listener for mouse movement
			KeyTypedHandler handler = new KeyTypedHandler();
			frame.addKeyListener(handler);

			keyTimer = new Timer(5, new KeyTimerListener());
			keyTimer.start();

			// Add the panel to the frame
			frame.setContentPane(panel);

			// Show the frame
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			frame.requestFocus();

		}

		/**
		 * Handles mouse movement.
		 */
		public static class KeyTypedHandler implements KeyListener
		{
			/** This method does nothing. */
			public void keyTyped (KeyEvent e) {}

			/**
			 * When a key is pressed, move the rightPaddle or leftPaddle.
			 */
			public void keyPressed (KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_UP)
				{
					upClicked = true;
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN)
				{
					downClicked = true;
				}
				else if (e.getKeyCode() == KeyEvent.VK_W)
				{
					wClicked = true;
				}
				else if (e.getKeyCode() == KeyEvent.VK_S)
				{
					sClicked = true;
				}
			}

			/** This method does nothing. */
			public void keyReleased (KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP)
				{
					upClicked = false;
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN)
				{
					downClicked = false;
				}
				else if (e.getKeyCode() == KeyEvent.VK_W)
				{
					wClicked = false;
				}
				else if (e.getKeyCode() == KeyEvent.VK_S)
				{
					sClicked = false;
				}
			}
		}

		private static class KeyTimerListener implements ActionListener
		{
			public void actionPerformed (ActionEvent event)
			{

				if (upClicked)
				{
					// Move right paddle up
					rightPaddle.setLocation(rightPaddle.getX(), rightPaddle.getY() - MOVE_DISTANCE);
				}
				if (downClicked)
				{
					// Move right paddle up
					rightPaddle.setLocation(rightPaddle.getX(), rightPaddle.getY() + MOVE_DISTANCE);
				}
				if (wClicked)
				{
					// Move right paddle up
					leftPaddle.setLocation(leftPaddle.getX(), leftPaddle.getY() - MOVE_DISTANCE);
				}
				if (sClicked)
				{
					// Move right paddle up
					leftPaddle.setLocation(leftPaddle.getX(), leftPaddle.getY() + MOVE_DISTANCE);
				}

				// Move the ball
				if (ball.getX() + 100 == xRightPaddle && 
						ball.getY() + 50 >= yRightPaddle && 
						ball.getY() + 50 < yRightPaddle + 100)
				{
					moveBallX = -moveBallX;
				}
				else if (ball.getX() > 1180)
				{
					moveBallX = -moveBallX;
					scoreLeft++;
					leftLabel.setText(String.valueOf(scoreLeft));

				}

				else if( ball.getX() < 0)
				{
					moveBallX = -moveBallX;
					scoreRight++;
					rightLabel.setText(String.valueOf(scoreRight));


				}
				else if (ball.getY() > 620 || ball.getY() < 0 )
				{
					moveBallY = -moveBallY;
				}

				if(ball.getX() >leftPaddle.getX() && ball.getX() < leftPaddle.getX() + 30)
				{
					if(ball.getY() + 70 >leftPaddle.getY() && ball.getY() + 30 < leftPaddle.getY() + 100)
					{
						moveBallX = -moveBallX;				      }
				}
				
				
				if (ball.getX() + 100 > rightPaddle.getX()  && ball.getX() + 100 < rightPaddle.getX() + 30 )
				{
					if (ball.getY() + 70 > rightPaddle.getY() && ball.getY() + 30 < rightPaddle.getY() + 100)
					{
						moveBallX = -moveBallX;				      }
				}


				ball.setLocation(ball.getX()+ moveBallX ,ball.getY() + moveBallY);

				if (scoreRight > 7)
				{
					JOptionPane.showMessageDialog(panel, "Pink Wins!", 
							"WINNER", JOptionPane.INFORMATION_MESSAGE);
					System.exit(1);
				}

				if (scoreLeft > 7)
				{
					JOptionPane.showMessageDialog(panel, "Cyan Wins!", 
							"WINNER", JOptionPane.INFORMATION_MESSAGE);
					System.exit(1);
				}
			}


		}



		/** This method does nothing. */
		public void componentMoved (ComponentEvent e) {}

		/** This method does nothing. */
		public void componentShown (ComponentEvent e) {}

		/** This method does nothing. */
		public void componentHidden (ComponentEvent e) {}



	}		


}		

