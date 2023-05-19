import java.lang.Math;

/**
* This class represents a player in the game.
*/
public class Player {
    private int Score;
    private Ball Mallet;
    private GameArena gArena;
    private String MalletName;

    /**
	* Creates a new player object.
	* @param garena The GameArena object.
    * @param x The starting X position of the player
    * @param y The starting Y position of the player
    * @param diameter The diameter of the players' mallet
    * @param colour The colour of the players' mallet
    * @param layer The layer on which the players' mallet is visible on
    * @param charName The name of the player; can be either Player1 or Player2
	*/
    public Player(GameArena garena, int x, int y, int diameter, String colour, int layer, String charName) {
        Mallet = new Ball(x, y, diameter, colour, layer);
        Score = 0;
        gArena = garena;
        gArena.addBall(Mallet);
        MalletName = charName;
    }

    /**
	 * Internal method to round to 1 decimal place.
	 * @param num The decimal to be rounded to 1 decimal place.
	 */

     private static double roundTo1DP(double num) {
        double multiplier = Math.pow(10, 1);
        double roundedNum = Math.round(num * multiplier) / multiplier;
        return roundedNum;
    };

    /**
	* Get the X Position of the players mallet.
    * @return The X position of the players mallet.
	*/
    public double getXPosition() {
        return Mallet.getXPosition();
    }
    /**
	* Get the Y position of the players mallet.
    * @return The Y position of the players mallet.
	*/
    public double getYPosition() {
        return Mallet.getYPosition();
    }

    /**
	* Set the X position of the players mallet to a given value.
	* @param pos The new X position value
	*/
    public void setXPosition(double pos) {
        Mallet.setXPosition(pos);
    }
    /**
	* Set the Y position of the players mallet to a given value .
	* @param num The decimal to be rounded to 1 decimal place.
	*/
    public void setYPosition(double pos) {
        Mallet.setYPosition(pos);
    }

    /**
	* Returns the players mallet.
    * @return The players mallet.
	*/
    public Ball getMallet() {
        return Mallet;
    }
    /**
	* Set the X speed of the players mallet to a given value.
	* @param pos The new X speed value
	*/
    public void setXSpeed(double speed) {
        Mallet.setXSpeed(speed);
    }

    /**
	* Set the Y speed of the players mallet to a given value.
	* @param pos The new Y speed value
	*/
    public void setYSpeed(double speed) {
        Mallet.setYSpeed(speed);
    }
    /**
	* Returns the X speed of the players mallet.
	* @return The current X speed of the players mallet.
	*/
    public double getXSpeed() {
        return Mallet.getXSpeed();
    }
    /**
	* Returns the Y speed of the players mallet.
	* @return The current Y speed of the players mallet.
	*/
    public double getYSpeed() {
        return Mallet.getYSpeed();
    }
    /**
	* Returns the players score.
	* @return The players score.
	*/
    public int getScore() {
        return Score;
    }
    /**
	* Set the players score to a given value.
	* @param pos The new score
	*/
    public void setScore(int score) {
        Score = score;
    }
    /**
	* Deflects the puck away from the players mallet.
	* @param puckBall The puck being deflected
    * @param soundPlayer The soundplayer object being used to play sounds
	*/
    public void deflectPuck(Puck puckBall, SoundPlayer soundPlayer, Pitch pitch) {

        // The position and speed of each of the two balls in the x and y axis before collision.

        double xSpeed1 = Mallet.getXSpeed();
        double ySpeed1 = Mallet.getYSpeed();
        double xSpeed2 = puckBall.getXSpeed();
        double ySpeed2 = puckBall.getYSpeed();

        double initialXSpeed1 = Mallet.getXSpeed();
        double initialYSpeed1 = Mallet.getYSpeed();
        double initialXSpeed2 = puckBall.getXSpeed();
        double initialYSpeed2 = puckBall.getYSpeed();

        if (initialXSpeed1 == 0 && initialYSpeed1 == 0) { // If mallet speed is 0 just act as a boundary and inverse their speed + halve it
            xSpeed2 = (xSpeed2*-1)/2;
            ySpeed2 = (ySpeed2*-1)/2;
        }
        else {

            double xPosition1 = Mallet.getXPosition();
            double yPosition1 = Mallet.getYPosition();
            double xPosition2 = puckBall.getXPosition();
            double yPosition2 = puckBall.getYPosition();
    
            // Calculate initial momentum of the balls... We assume unit mass here.
            double p1InitialMomentum = Math.sqrt(xSpeed1 * xSpeed1 + ySpeed1 * ySpeed1);
            double p2InitialMomentum = Math.sqrt(xSpeed2 * xSpeed2 + ySpeed2 * ySpeed2);
    
            // calculate motion vectors
            double[] p1Trajectory = {xSpeed1, ySpeed1};
            double[] p2Trajectory = {xSpeed2, ySpeed2};
    
            // Calculate Impact Vector
            double[] impactVector = {xPosition2 - xPosition1, yPosition2 - yPosition1};
            double[] impactVectorNorm = normalizeVector(impactVector);
    
            // Calculate scalar product of each trajectory and impact vector
            double p1dotImpact = Math.abs(p1Trajectory[0] * impactVectorNorm[0] + p1Trajectory[1] * impactVectorNorm[1]);
            double p2dotImpact = Math.abs(p2Trajectory[0] * impactVectorNorm[0] + p2Trajectory[1] * impactVectorNorm[1]);
    
            // Calculate the deflection vectors - the amount of energy transferred from one ball to the other in each axis
            double[] p1Deflect = { -impactVectorNorm[0] * p2dotImpact, -impactVectorNorm[1] * p2dotImpact };
            double[] p2Deflect = { impactVectorNorm[0] * p1dotImpact, impactVectorNorm[1] * p1dotImpact };
    
            // Calculate the final trajectories
            double[] p1FinalTrajectory = {p1Trajectory[0] + p1Deflect[0] - p2Deflect[0], p1Trajectory[1] + p1Deflect[1] - p2Deflect[1]};
            double[] p2FinalTrajectory = {p2Trajectory[0] + p2Deflect[0] - p1Deflect[0], p2Trajectory[1] + p2Deflect[1] - p1Deflect[1]};
    
            // Calculate the final energy in the system.
            double p1FinalMomentum = Math.sqrt(p1FinalTrajectory[0] * p1FinalTrajectory[0] + p1FinalTrajectory[1] * p1FinalTrajectory[1]);
            double p2FinalMomentum = Math.sqrt(p2FinalTrajectory[0] * p2FinalTrajectory[0] + p2FinalTrajectory[1] * p2FinalTrajectory[1]);
    
            // Scale the resultant trajectories if we've accidentally broken the laws of physics.
            double mag = (p1InitialMomentum + p2InitialMomentum) / (p1FinalMomentum + p2FinalMomentum);
            
            // Calculate the final x and y speed settings for the two balls after collision.
            xSpeed1 = p1FinalTrajectory[0] * mag;
            ySpeed1 = p1FinalTrajectory[1] * mag;
            xSpeed2 = p2FinalTrajectory[0] * mag;
            ySpeed2 = p2FinalTrajectory[1] * mag;

            if (Double.isNaN(xSpeed2)) {
                xSpeed2 = (initialXSpeed2*-1); 
            }
            if (Double.isNaN(ySpeed2)) {
                ySpeed2 = (initialYSpeed2*-1); 
            }
        }
    
        if ( (xSpeed2 > 0 || xSpeed2 < 0) || (ySpeed2 > 0 || ySpeed2 < 0) ) {
            soundPlayer.playSound("hit.wav");
        }
        
        // System.out.print(xSpeed2+", "+ySpeed2+"\n");

        // Set puck to new speed
		puckBall.setXSpeed(xSpeed2);
		puckBall.setYSpeed(ySpeed2);

        // Move puck away from mallet
        puckBall.setXPosition(puckBall.getXPosition()+(xSpeed2), pitch, false);
        puckBall.setYPosition(puckBall.getYPosition()+(ySpeed2), pitch, false); 
	};

    /**
	* Internal method to normalize a given vector
	* @param vec The given vector
	*/
    private double[] normalizeVector(double[] vec) {
        double mag = 0.0;
        int dimensions = vec.length;
        double[] result = new double[dimensions];
        for (int i = 0; i < dimensions; i++)
            mag += vec[i] * vec[i];
        mag = Math.sqrt(mag);
        if (mag == 0.0) {
            result[0] = 1.0;
            for (int i = 1; i < dimensions; i++)
                result[i] = 0.0;
        } else {
            for (int i = 0; i < dimensions; i++)
                result[i] = vec[i] / mag;
        }
        return result;
    };

    /**
	* Check if the players mallet is colliding with a given puck.
	* @param puck The puck being checked for collisions.
	*/
    public boolean isTouchingPuck(Puck puck) {
        // Use pythagoras theorem to calculate distance between puck and player
        Ball puckObj = puck.getPuckObj();

        double a = puckObj.getXPosition()-Mallet.getXPosition();
        double b = puckObj.getYPosition()-Mallet.getYPosition();
        double c2 = (a * a) + (b * b);
        double c = Math.sqrt(c2);
        //double d = (Mallet.getSize()/2)+(puckObj.getSize()/2)-4;

        // If distance between puck and player is less than or equal to their radius' combined then they are touching
        //System.out.println(a+", "+b+", "+c+", "+d+".\n");
        return (roundTo1DP(c) < (roundTo1DP((Mallet.getSize()/2)+(puckObj.getSize()/2))-4));
    }

    /**
	* Check if the players mallet is colliding with a given puck at a given position.
	* @param puck The puck being checked for collisions.
    * @param xPuckPos The given puck X position.
    * @param yPuckPos The given puck Y position.
    * @param xMalletPos The given mallet X position.
    * @param yMalletkPos The given mallet Y position.
	*/
    public boolean isTouchingPuck(Puck puck, double xPuckPos, double yPuckPos, double xMalletPos, double yMalletPos) {
        // Use pythagoras theorem to calculate distance between puck and player
        Ball puckObj = puck.getPuckObj();

        double a = xPuckPos-xMalletPos;
        double b = yPuckPos-yMalletPos;
        double c2 = (a * a) + (b * b);
        double c = Math.sqrt(c2);
        //double d = (Mallet.getSize()/2)+(puckObj.getSize()/2)-4;
        // If distance between puck and player is less than or equal to their radius' combined then they are touching
        //System.out.println(a+", "+b+", "+c+", "+d+".\n");
        return (roundTo1DP(c) < (roundTo1DP((Mallet.getSize()/2)+(puckObj.getSize()/2))-4));
    }


    /**
	* Moves the players mallet by a certain distance depending on its speed.
	* @param newXSpeed The new X speed of the mallet.
    * @param newYSpeed The new Y speed of the mallet.
    * @param pitch The pitch object the game is being played on.
    * @param soundPlayer The soundplayer object being used to play soumds.
	*/
    public void movePlayer(double newXSpeed, double newYSpeed, Pitch pitch, SoundPlayer soundPlayer, Puck puck) {
        // Calculate new position using their speed
        Double newXPosition = Mallet.getXPosition()+newXSpeed;
        Double newYPosition = Mallet.getYPosition()+newYSpeed;

        // Check if player is in the boundary on the left side or right side of the pitch
        Boolean pIn1XBoundary = pitch.isIn1XBoundary(Mallet, newXPosition);
        Boolean pIn2XBoundary = pitch.isIn2XBoundary(Mallet, newXPosition);

        // Check if player is in the Y boundary of the pitch
        Boolean pInYBoundary = pitch.isInYBoundary(Mallet, newYPosition);

        Boolean isTouchingPuck = isTouchingPuck(puck, puck.getXPosition(), puck.getYPosition(), newXPosition, newYPosition);

        if (isTouchingPuck) {
            deflectPuck(puck, soundPlayer, pitch);
        }
        else {
            // Check if they are in the correct sides of the arena depending on which player number they are

            if ((pIn1XBoundary && MalletName == "Player1") || (pIn2XBoundary && MalletName == "Player2")) {
                Mallet.setXSpeed(newXSpeed);
                Mallet.setXPosition(newXPosition);
            };

            if (pInYBoundary) {
                Mallet.setYSpeed(newYSpeed);
                Mallet.setYPosition(newYPosition);
            }
        }
        
    }

}