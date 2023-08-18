import javax.swing.ImageIcon;

public class Snake {   

    protected String snakeHeadDir = "right";


    protected int[] SnakeXlength = new int[750];
    protected int[] SnakeYlength = new int[750];

    protected int lengthOfSnake = 3;


    protected ImageIcon getHeadIcon(){

        if (this.isRight()){
            return new ImageIcon(getClass().getResource("assets/images/rightmouth.png"));
        }
        else if (this.isLeft()){
            return new ImageIcon(getClass().getResource("assets/images/leftmouth.png"));
        }
        else if (this.isUp()){
            return new ImageIcon(getClass().getResource("assets/images/upmouth.png"));
        }
        else if (this.isDown()){
            return new ImageIcon(getClass().getResource("assets/images/downmouth.png"));
        }
        else{
            return null;
        }
    }

    protected void updateSnakeHeadDir(String direction){
        this.snakeHeadDir = direction;
    }

    protected boolean isRight(){
        return this.snakeHeadDir == "right";
    }
    protected boolean isLeft(){
        return this.snakeHeadDir == "left";
    }
    protected boolean isUp(){
        return this.snakeHeadDir == "up";
    }
    protected boolean isDown(){
        return this.snakeHeadDir == "down";
    }
    
}
