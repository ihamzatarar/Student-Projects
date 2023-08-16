public class ScoringSystem {
    
    protected int moves;
    protected int scorce;

    public ScoringSystem() {
        this.moves = 0;
        this.scorce = 0;
    }

    public int getMoves() {
        return moves;
    }

    public void incrMoves() {
        this.moves += 1;
    }
    public int getScorce() {
        return scorce;
    }

    public void incrScorce() {
        this.scorce += 1;
    }

    protected boolean isStart(){
        return this.moves==0;
    }

    protected void resetScore(){
        this.moves = 0;
        this.scorce = 0;
    }
    
}
