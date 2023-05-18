/**
* This class represents the Air Hockey Pitch.
*/

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

    /**
	* Creates a new pitch object.
	* @param gArena The GameArena object.
	*/
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

    /**
	* Moves a given goal up and down
	* @param goalName The name of the goal to be moved
	*/
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

    /**
	* Resets a given goals position back to normal.
	* @param goalName The name of the goal to be reset
	*/
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
    
    /**
	* Updates the winning score label with a given score.
	* @param pos The new winning score value
	*/
    public void updateScoreToWin(int newscore) {
        scoreToWin.setText("Score needed to win: "+Integer.toString(newscore));
    }
    
    /**
	* Updates the scores for both players.
	* @param p1score Player 1's score
    * @param p2score Player 2's score
	*/
    public void updateScores(int p1score, int p2score) {
        player1Score.setText(Integer.toString(p1score));
        player2Score.setText(Integer.toString(p2score));
    }

    /**
	* Updates the status label on the top left of the pitch.
	* @param newStatus The new text to be displayed.
	*/
    public void updateStatus(String newStatus) {
        status.setText(newStatus);
    }

    /**
	* Updates the mute status label on the bottom right of the pitch.
	* @param muted The new muted value.
	*/
    public void updateMuteStatus(boolean muted) {
        if (muted) {
            muteStatus.setText("Press M to unmute SFX");
        }
        else {
            muteStatus.setText("Press M to mute SFX");
        }
    }

    /**
	* Check if a given puck is touching either goal.
	* @param puck The puck.
	*/
    public int isTouchingGoal(Ball puck) {
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

    /**
	* Checks whether a given ball is within the X boundaries of the pitch at a hypothetical X position.
	* @param ball The ball.
    * @param desiredPosition The hypothetical X position of the ball.
	*/
    public Boolean isInXBoundary(Ball ball, Double desiredPosition) {
        return (desiredPosition >= (pitch.getXPosition()+(ball.getSize()/2)) && desiredPosition <= ((pitch.getWidth()+pitch.getXPosition())-(ball.getSize()/2)));
    }
        
    /**
	* Checks whether a given ball is within the Y boundaries of the pitch at a hypothetical Y position.
	* @param ball The ball.
    * @param desiredPosition The hypothetical Y position of the ball.
	*/
    public Boolean isInYBoundary(Ball ball, Double desiredPosition) {
        return (desiredPosition >= (pitch.getYPosition()+ball.getSize()/2) && desiredPosition <= (pitch.getYPosition()+pitch.getHeight()-(ball.getSize()/2)));
    }
    
    /**
	* Checks whether a given ball is within the X boundaries of the left side of the pitch at a hypothetical X position.
	* @param ball The ball.
    * @param desiredPosition The hypothetical X position of the ball.
	*/
    public Boolean isIn1XBoundary(Ball ball, Double desiredPosition) {
        return (desiredPosition <= ((centreLine.getXPosition()+1)-(ball.getSize()/2)) && desiredPosition >= (pitch.getXPosition()+(ball.getSize()/2)));
    }

    /**
	* Checks whether a given ball is within the X boundaries of the right side of the pitch at a hypothetical X position.
	* @param ball The ball.
    * @param desiredPosition The hypothetical X position of the ball.
	*/
    public Boolean isIn2XBoundary(Ball ball, Double desiredPosition) {
        return (desiredPosition >= (centreLine.getXPosition()+(ball.getSize()/2)) && desiredPosition <= ((pitch.getWidth()+pitch.getXPosition())-(ball.getSize()/2)));
    }
}