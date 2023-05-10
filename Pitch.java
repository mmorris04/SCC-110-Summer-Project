public class Pitch {

    private Rectangle pitch;
    private Rectangle border;
    private Rectangle centreLine;
    private Rectangle goal1;
    private Rectangle goal2;
    private Text status;
    private Text player1Score;
    private Text player2Score;

    public Pitch(GameArena gArena) {
        border = new Rectangle(100, 100, 1000, 520, "BLUE", 0);
        pitch = new Rectangle(125, 125, 950, 470, "WHITE", 1);
        centreLine = new Rectangle(599, 100, 2, 520, "BLUE", 2);
        goal1 = new Rectangle(125, 270, 15, 180, "GREY", 2);
        goal2 = new Rectangle(1060, 270, 15, 180, "GREY", 2);
        status = new Text("Null", 25, 50, 50, "WHITE");
        player1Score = new Text("0", 35, 20, 360, "WHITE");
        player2Score = new Text("0", 35, 1160, 360, "WHITE");

        Ball MiddleCircleWhite = new Ball(600, 360, 80, "WHITE", 3);
        Ball MiddleCircleBlue = new Ball(600, 360, 84, "BLUE", 2);

        gArena.addBall(MiddleCircleBlue);
        gArena.addBall(MiddleCircleWhite);

        gArena.addRectangle(border);
        gArena.addRectangle(pitch);
        gArena.addRectangle(centreLine);
        gArena.addRectangle(goal1);
        gArena.addRectangle(goal2);

        gArena.addText(status);
        gArena.addText(player1Score);
        gArena.addText(player2Score);
    };
    public Boolean IsInXBoundary(Ball ball, Double desiredPosition) {
        return (desiredPosition >= (pitch.getXPosition()+(ball.getSize()/2)) && desiredPosition <= ((pitch.getWidth()+pitch.getXPosition())-(ball.getSize()/2)));
    }
    public Boolean IsInYBoundary(Ball ball, Double desiredPosition) {
        return (desiredPosition >= (pitch.getYPosition()+ball.getSize()/2) && desiredPosition <= (pitch.getYPosition()+pitch.getHeight()-(ball.getSize()/2)));
    }

    public Boolean IsIn1XBoundary(Ball ball, Double desiredPosition) {
        return (desiredPosition <= ((centreLine.getXPosition()+1)-(ball.getSize()/2)) && desiredPosition >= (pitch.getXPosition()+(ball.getSize()/2)));
    }
    public Boolean IsIn2XBoundary(Ball ball, Double desiredPosition) {
        return (desiredPosition >= (centreLine.getXPosition()+(ball.getSize()/2)) && desiredPosition <= ((pitch.getWidth()+pitch.getXPosition())-(ball.getSize()/2)));
    }
}