import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


import javax.swing.JPanel;

public class HUD extends JPanel implements ActionListener{

    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final int SIZE_OF_UNIT = 20;
    private static final int TOTAL_UNITS = (WIDTH * HEIGHT) / (SIZE_OF_UNIT * SIZE_OF_UNIT);
    private final int[] x = new int[TOTAL_UNITS];
    private final int[] y = new int[TOTAL_UNITS];
    private int length = 5;
    private int appleEaten;
    private int appleX;
    private int appleY;
    private char direction = 'D';
    private boolean running = false;
    private final Random random;
    private Timer timer;

    public HUD() {
        random = new Random();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        play();
    }   
    
    private void play() {
        addApple();
        running = true;
        timer = new Timer(80, this);
        timer.start();   
    }
    
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
    }
    
    private void move() {
        for (int i = length; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if (direction == 'L') {
            x[0] = x[0] - SIZE_OF_UNIT;
        } else if (direction == 'R') {
            x[0] = x[0] + SIZE_OF_UNIT;
        } else if (direction == 'U') {
            y[0] = y[0] - SIZE_OF_UNIT;
        } else {
            y[0] = y[0] + SIZE_OF_UNIT;
        }   
    }
    
    private void checkApple() {
        if(x[0] == appleX && y[0] == appleY) {
            length++;
            appleEaten++;
            addApple();
        }
    }
    
    private void draw(Graphics graphics) {
        if (running) {
            graphics.setColor(new Color(204, 0, 0));
            graphics.fillOval(appleX, appleY, SIZE_OF_UNIT, SIZE_OF_UNIT);
            graphics.setColor(Color.white);
            graphics.fillRect(x[0], y[0], SIZE_OF_UNIT, SIZE_OF_UNIT);
            for (int i = 1; i < length; i++) {
                graphics.setColor(new Color(255, 255, 0));
                graphics.fillRect(x[i], y[i], SIZE_OF_UNIT, SIZE_OF_UNIT);
            }
            graphics.setColor(Color.white);
            graphics.setFont(new Font("Monserrat", Font.ROMAN_BASELINE, 25));
            FontMetrics metrics = getFontMetrics(graphics.getFont());
            graphics.drawString("High Score: " + appleEaten, (WIDTH - metrics.stringWidth("High Score: " + appleEaten)) / 2, graphics.getFont().getSize());
        } else {
            gameOver(graphics);
        }
    }
    
    private void addApple() {
        appleX = random.nextInt((int)(WIDTH / SIZE_OF_UNIT))*SIZE_OF_UNIT;
        appleY = random.nextInt((int)(HEIGHT / SIZE_OF_UNIT))*SIZE_OF_UNIT;
    }
	
	public void checkHit() {
		for (int i = length; i > 0; i--) {
			if (x[0] == x[i] && y[0] == y[i]) {
				running = false;
			}
		}
		if (x[0] < 0 || x[0] > WIDTH || y[0] < 0 || y[0] > HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	
	public void gameOver(Graphics graphics) {
		graphics.setColor(Color.red);
		graphics.setFont(new Font("Monserrat", Font.ROMAN_BASELINE, 50));
		FontMetrics metrics = getFontMetrics(graphics.getFont());
		graphics.drawString("K.O.", (WIDTH - metrics.stringWidth("K.O.")) / 2, HEIGHT / 2);
		
		graphics.setColor(Color.white);
		graphics.setFont(new Font("Monserrat", Font.ROMAN_BASELINE, 25));
		metrics = getFontMetrics(graphics.getFont());
		graphics.drawString("High Score: " + appleEaten, (WIDTH - metrics.stringWidth("High Score: " + appleEaten)) / 2, graphics.getFont().getSize());

	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (running) {
			move();
			checkApple();
			checkHit();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if (direction != 'R') {
						direction = 'L';
					}
					break;
					
				case KeyEvent.VK_RIGHT:
					if (direction != 'L') {
						direction = 'R';
					}
					break;
					
				case KeyEvent.VK_UP:
					if (direction != 'D') {
						direction = 'U';
					}
					break;
					
				case KeyEvent.VK_DOWN:
					if (direction != 'U') {
						direction = 'D';
					}
					break;		
			}
		}
	}
}