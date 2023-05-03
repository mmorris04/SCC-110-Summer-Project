public class Player {
    private int Score;
    private Ball Character;
    private GameArena gArena;
    public Player(GameArena garena, int x, int y, int diameter, String colour, int layer) {
        Character = new Ball(x, y, diameter, colour, layer);
        Score = 0;
        gArena = garena;
        gArena.addBall(Character);
    }
    public double getXPosition() {
        return Character.getXPosition();
    }
    public double getYPosition() {
        return Character.getYPosition();
    }

    public void setXPosition(double pos) {
        Character.setXPosition(pos);
    }
    public void setYPosition(double pos) {
        Character.setYPosition(pos);
    }

    public Ball getCharacter() {
        return Character;
    }

    public void setXSpeed(double speed) {
        Character.setXSpeed(speed);
    }

    public void setYSpeed(double speed) {
        Character.setYSpeed(speed);
    }

    public double getXSpeed() {
        return Character.getXSpeed();
    }
    public double getYSpeed() {
        return Character.getYSpeed();
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score += score;
    }

    public void deflectPuck(Ball puck) {
        gArena.deflectPuck(Character, puck);
    }
    public boolean isTouchingPuck(Ball puck) {

        return Character.collides(puck);

        //boolean isTouchingOnXAxis = ((Character.getXPosition()+Character.getSize()) >= puck.getXPosition() || Character.getXPosition() <= (puck.getXPosition()+puck.getSize()));
        //boolean isTouchingOnYAxis = ((Character.getYPosition()+Character.getSize()) >= puck.getYPosition() || Character.getYPosition() <= (puck.getYPosition()+puck.getSize()));

        //return (isTouchingOnXAxis == true || isTouchingOnYAxis == true);
    }

}