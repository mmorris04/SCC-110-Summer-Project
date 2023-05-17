public class Pitch {

    private Rectangle pitch;
    private Rectangle border;
    private Rectangle centreLine;
    private Rectangle goal1;
    private Rectangle goal2;
    private Text muteStatus;
    private Text status;
    private Text player1Score;
    private Text player2Score;
    private Text scoreToWin;
    private String goal1MovementDirection = "UP";
    private String goal2MovementDirection = "UP";

    public Pitch(GameArena gArena) {
        border = new Rectangle(100, 100, 1000, 520, "BLUE", 0);
        pitch = new Rectangle(125, 125, 950, 470, "WHITE", 1);
        centreLine = new Rectangle(599, 100, 2, 520, "BLUE", 2);
        goal1 = new Rectangle(125, 270, 15, 180, "GREY", 2);
        goal2 = new Rectangle(1060, 270, 15, 180, "GREY", 2);
        status = new Text("Null", 35, 50, 50, "GREEN");
        muteStatus = new Text("Press M to mute SFX", 25, 800, 680, "WHITE");
        player1Score = new Text("0", 60, 20, 360, "WHITE");
        player2Score = new Text("0", 60, 1140, 360, "WHITE");
        scoreToWin = new Text("Score needed to win: 5", 35, 35, 680, "WHITE");

        Ball MiddleCircleWhite = new Ball(600, 360, 80, "WHITE", 3);
        Ball MiddleCircleBlue = new Ball(600, 360, 84, "BLUE", 2);

        gArena.addBall(MiddleCircleBlue);
        gArena.addBall(MiddleCircleWhite);

        gArena.addRectangle(border);
        gArena.addRectangle(pitch);
        gArena.addRectangle(centreLine);
        gArena.addRectangle(goal1);
        gArena.addRectangle(goal2);

        gArena.addText(scoreToWin);
        gArena.addText(muteStatus);
        gArena.addText(status);
        gArena.addText(player1Score);
        gArena.addText(player2Score);
    };

    // Move the goal up and down (for cheats)
    public void moveGoal(String goalName) {
        if (goalName == "Goal1") {
            if (goal1MovementDirection == "UP" && goal1.getYPosition()<=pitch.getYPosition()) {
                goal1MovementDirection = "DOWN";
                goal1.setYPosition(goal1.getYPosition()+5);
            } 
            else if (goal1MovementDirection == "DOWN" && (goal1.getYPosition()+goal1.getHeight()) >= (pitch.getYPosition()+pitch.getHeight())) {
                goal1MovementDirection = "UP";
                goal1.setYPosition(goal1.getYPosition()-5);
            }   
            else if (goal1MovementDirection == "DOWN") {
                goal1.setYPosition(goal1.getYPosition()+5);
            }
            else if (goal1MovementDirection == "UP") {
                goal1.setYPosition(goal1.getYPosition()-5);
            }
        }
        else {
            if (goal2MovementDirection == "UP" && goal2.getYPosition()<=pitch.getYPosition()) {
                goal2MovementDirection = "DOWN";
                goal2.setYPosition(goal2.getYPosition()+5);
            } 
            else if (goal2MovementDirection == "DOWN" && (goal2.getYPosition()+goal2.getHeight()) >= (pitch.getYPosition()+pitch.getHeight())) {
                goal2MovementDirection = "UP";
                goal2.setYPosition(goal2.getYPosition()-5);
            }
            else if (goal2MovementDirection == "DOWN") {
                goal2.setYPosition(goal2.getYPosition()+5);
            }
            else if (goal2MovementDirection == "UP") {
                goal2.setYPosition(goal2.getYPosition()-5);
            }
        }
    }

    // Reset a goals position back to normal
    public void resetGoalPos(String goalName) {
        if (goalName == "Goal1") {
            goal1.setXPosition(125);
            goal1.setYPosition(270);
        }
        else {
            goal2.setXPosition(1060);
            goal2.setYPosition(270);
        }
    }
    
    public void UpdateScoreToWin(int newscore) {
        scoreToWin.setText("Score needed to win: "+Integer.toString(newscore));
    }
    // Update the scoreboard
    public void UpdateScores(int p1score, int p2score) {
        player1Score.setText(Integer.toString(p1score));
        player2Score.setText(Integer.toString(p2score));
    }

    // Update the message which appears on the top left of the screen
    public void UpdateStatus(String newStatus) {
        status.setText(newStatus);
    }

    // Update the mute tip on the bottom right of the screen
    public void UpdateMuteStatus(boolean muted) {
        if (muted) {
            muteStatus.setText("Press M to unmute SFX");
        }
        else {
            muteStatus.setText("Press M to mute SFX");
        }
    }

    // Check if a given puck is touching either of the goals
    public int IsTouchingGoal(Ball puck) {
        boolean touchingLeftGoal = (
            (puck.getXPosition()-(puck.getSize()/2) <= goal1.getXPosition()+goal1.getWidth()) 
            &&
            (
                (puck.getYPosition()+puck.getSize()/2 >= goal1.getYPosition()) 
                &&
                (puck.getYPosition()-puck.getSize()/2 <= goal1.getYPosition()+goal1.getHeight())
            )
        );
        
        boolean touchingRightGoal = (
            (puck.getXPosition()+(puck.getSize()/2) >= goal2.getXPosition()) 
            &&
            (
                (puck.getYPosition()+puck.getSize()/2 >= goal2.getYPosition()) 
                &&
                (puck.getYPosition()-puck.getSize()/2 <= goal2.getYPosition()+goal2.getHeight())
            )
        );

        if (touchingLeftGoal) {
            return 1;
        }
        else if (touchingRightGoal) {
            return 2;
        }
        else {
            return 0;
        }
    }

    // Check if a given ball is within the X boundary (for the puck)
    public Boolean IsInXBoundary(Ball ball, Double desiredPosition) {
        return (desiredPosition >= (pitch.getXPosition()+(ball.getSize()/2)) && desiredPosition <= ((pitch.getWidth()+pitch.getXPosition())-(ball.getSize()/2)));
    }
        
    // Check if a given ball is within the Y boundary of the pitch
    public Boolean IsInYBoundary(Ball ball, Double desiredPosition) {
        return (desiredPosition >= (pitch.getYPosition()+ball.getSize()/2) && desiredPosition <= (pitch.getYPosition()+pitch.getHeight()-(ball.getSize()/2)));
    }
    
    // Check if a given ball is within the X boundary of the left side of the pitch
    public Boolean IsIn1XBoundary(Ball ball, Double desiredPosition) {
        return (desiredPosition <= ((centreLine.getXPosition()+1)-(ball.getSize()/2)) && desiredPosition >= (pitch.getXPosition()+(ball.getSize()/2)));
    }

    // Check if a given ball is within the X boundary of the right side of the pitch
    public Boolean IsIn2XBoundary(Ball ball, Double desiredPosition) {
        return (desiredPosition >= (centreLine.getXPosition()+(ball.getSize()/2)) && desiredPosition <= ((pitch.getWidth()+pitch.getXPosition())-(ball.getSize()/2)));
    }
}