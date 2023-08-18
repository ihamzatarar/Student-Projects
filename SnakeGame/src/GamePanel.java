
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    
    //loading Images

    private ImageIcon snaketitle = new ImageIcon(getClass().getResource("assets/images/snaketitle.jpg"));
    private ImageIcon snakeimage = new ImageIcon(getClass().getResource("assets/images/snakeimage.png"));
    private ImageIcon appleimage = new ImageIcon(getClass().getResource("assets/images/enemy.png"));
    private ScoringSystem scoreSys = new ScoringSystem();
    private Snake snake = new Snake();
    private Apple apple = new Apple(snake);
    private Sounds sounds = new Sounds();


    private Timer timer;
    private int delay = 100;
    private boolean gameOver = false;

    GamePanel(){

        //Enabling Key listener
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        timer = new Timer(delay, this);
        timer.start();
        sounds.playMusic();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.WHITE);
        //Title border
        g.drawRect(24, 10, 851, 55);
        //Setting title Image 
        snaketitle.paintIcon(this, g, 25, 11);
        

        //Main Game border
         g.drawRect(24, 74, 851, 576 );
        //Creating Center Black Rectangle
        g.setColor(Color.black);
        g.fillRect(25, 75,850,575);



        //Initizling Game
        if (scoreSys.isStart()){

            //Head Position in frame
            snake.SnakeXlength[0] = 100;
            snake.SnakeYlength[0] = 100;   //Head of snake is made of 100 pixels
            
            //Body Position in Frame
            snake.SnakeXlength[1] = 75;
            snake.SnakeYlength[1] = 100;
            snake.SnakeXlength[2] = 50;
            snake.SnakeYlength[2] = 100;

        }

        //Drawing Head icon
        ImageIcon headIcon = snake.getHeadIcon();
        headIcon.paintIcon(this, g, snake.SnakeXlength[0],snake.SnakeYlength[0]);
        
        //Drawing Body
        for(int i=1;i<snake.lengthOfSnake;i++){
            snakeimage.paintIcon(this, g, snake.SnakeXlength[i], snake.SnakeYlength[i]);
        }

        //Drawing Apple
        appleimage.paintIcon(this, g, apple.Xcord,apple.Ycord );

        if(gameOver){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial",Font.BOLD, 50));
            g.drawString("Game Over", 300, 300);

            g.setFont(new Font("Arial",Font.PLAIN, 20));
            g.drawString("Press SPACE to restart", 320, 350);
        }

        //Drawings Stats
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.PLAIN, 14));
        g.drawString("Score: "+ scoreSys.scorce, 750, 30);
        g.drawString("Length: "+ snake.lengthOfSnake, 750, 50);


        //Delete all the painted things
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      
        //Changing position of body by assigning position to next one
        for(int i=snake.lengthOfSnake-1;i>0;i--){
            snake.SnakeXlength[i] = snake.SnakeXlength[i-1];
            snake.SnakeYlength[i] = snake.SnakeYlength[i-1];
        }


        //Changing position of head
        if(snake.isLeft()){
            snake.SnakeXlength[0] -= 25;
        }
        if(snake.isRight()){
            snake.SnakeXlength[0] += 25;
        }
        if(snake.isUp()){
            snake.SnakeYlength[0] -= 25;
        }
        if(snake.isDown()){
            snake.SnakeYlength[0] += 25;
        }

        //Conditions to make snake from other side when it reaches the wall
        //Right and left
        if(snake.SnakeXlength[0]>850)snake.SnakeXlength[0]=25;
        if(snake.SnakeXlength[0]<25)snake.SnakeXlength[0]=850;
        //Up and down
        if(snake.SnakeYlength[0]>625)snake.SnakeYlength[0]=75;
        if(snake.SnakeYlength[0]<75)snake.SnakeYlength[0]=625;


        CollisionWithApple();
        CollisionWithBody();
        //Paint updated frame
        repaint(); 
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_LEFT && !snake.isRight()){
            snake.updateSnakeHeadDir("left");
            scoreSys.incrMoves();
        }
        if (e.getKeyCode()==KeyEvent.VK_RIGHT && !snake.isLeft()){
            snake.updateSnakeHeadDir("right");
            scoreSys.incrMoves();
        }
        if (e.getKeyCode()==KeyEvent.VK_UP && !snake.isDown()){
            snake.updateSnakeHeadDir("up");
            scoreSys.incrMoves();
        }
        if (e.getKeyCode()==KeyEvent.VK_DOWN && !snake.isUp()){
            snake.updateSnakeHeadDir("down");
            scoreSys.incrMoves();
        }

        if (e.getKeyCode()==KeyEvent.VK_SPACE && gameOver){
            restartGame();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    public void CollisionWithApple(){
        if(snake.SnakeXlength[0]==apple.Xcord && snake.SnakeYlength[0] == apple.Ycord){
            sounds.playEatSound();
            apple.newApple(snake);
            snake.lengthOfSnake++;
            scoreSys.incrScorce();
        }
    }

    public void CollisionWithBody(){
        for(int i=snake.lengthOfSnake-1;i>0;i--){
            if(snake.SnakeXlength[i]== snake.SnakeXlength[0] && snake.SnakeYlength[i] == snake.SnakeYlength[0]){
                timer.stop();
                gameOver =true;
            }
        }
    }


    public void restartGame(){
        gameOver=false;
        scoreSys.resetScore();
        snake.lengthOfSnake =3;
        snake.updateSnakeHeadDir("right");
        timer.start();
        repaint();
    }

}
