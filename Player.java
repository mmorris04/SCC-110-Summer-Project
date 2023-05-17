import java.lang.Math;


public class Player {
    private int Score;
    private Ball Character;
    private GameArena gArena;
    private String CharacterName;

    public Player(GameArena garena, int x, int y, int diameter, String colour, int layer, String charName) {
        Character = new Ball(x, y, diameter, colour, layer);
        Score = 0;
        gArena = garena;
        gArena.addBall(Character);
        CharacterName = charName;
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
        Score = score;
    }
    public void deflectPuck(Ball puckBall, SoundPlayer soundPlayer) {

        // The position and speed of each of the two balls in the x and y axis before collision.

        double xSpeed1 = Character.getXSpeed();
        double ySpeed1 = Character.getYSpeed();
        double xSpeed2 = puckBall.getXSpeed();
        double ySpeed2 = puckBall.getYSpeed();

        double xPosition1 = Character.getXPosition();
        double yPosition1 = Character.getYPosition();
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
        xSpeed1 = 0 + p1FinalTrajectory[0] * mag;
        ySpeed1 = 0 + p1FinalTrajectory[1] * mag;
        xSpeed2 = 0 + p2FinalTrajectory[0] * mag;
        ySpeed2 = 0 + p2FinalTrajectory[1] * mag;

        if (Double.isNaN(xSpeed2)) {
            xSpeed2 = 0;
        }
        if (Double.isNaN(ySpeed2)) {
            ySpeed2 = 0;
        }
        
        System.out.println(xSpeed2);
        System.out.println(ySpeed2);
        if ( (xSpeed2 > 0 || xSpeed2 < 0) || (ySpeed2 > 0 || ySpeed2 < 0) ) {
            soundPlayer.PlaySound("hit.wav");
        }
        
		puckBall.setXSpeed(xSpeed2);
		puckBall.setYSpeed(ySpeed2);
	};

    
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

    public void olddeflectPuck(Ball puckBall, SoundPlayer soundPlayer) {

        double newXSpeed = 0;
        double newYSpeed = 0;


        if (Character.getXSpeed() == 0) {
            newXSpeed = puckBall.getXSpeed() *-1;
        }
        if (Character.getYSpeed() == 0) {
            newYSpeed = puckBall.getYSpeed() *-1;
        }

        
        if (Character.getXSpeed() > 0 && puckBall.getXSpeed() > 0 || (Character.getXSpeed() < 0 && puckBall.getXSpeed() < 0) ) {
            newXSpeed = puckBall.getXSpeed() *-1;
        }
        if ((Character.getYSpeed() > 0 && puckBall.getYSpeed() > 0) || (Character.getYSpeed() < 0 && puckBall.getYSpeed() < 0) ) {
            newYSpeed = puckBall.getYSpeed() *-1;
        }

        if (puckBall.getXSpeed() == 0) {
            newXSpeed = Character.getXSpeed()*2;
        };
        if (puckBall.getYSpeed() == 0) {
            newYSpeed = Character.getYSpeed()*2;
        };

        if ((Character.getXSpeed() > 0 && puckBall.getXSpeed() < 0) || (Character.getXSpeed() < 0 && puckBall.getXSpeed() > 0)) {
            newXSpeed = Character.getXSpeed()*2;
        }
        if ((Character.getYSpeed() > 0 && puckBall.getYSpeed() < 0) || (Character.getYSpeed() < 0 && puckBall.getYSpeed() > 0)) {
            newYSpeed = Character.getYSpeed()*2;
        }
        
        soundPlayer.PlaySound("hit.wav");

		puckBall.setXSpeed(newXSpeed);
		puckBall.setYSpeed(newYSpeed);
	};

    
    public boolean isTouchingPuck(Ball puck) {
        // Use pythagoras theorem to calculate distance between puck and player

        double a = puck.getXPosition()-Character.getXPosition();
        double b = puck.getYPosition()-Character.getYPosition();
        double c2 = (a * a) + (b * b);
        double c = Math.sqrt(c2);

        // If distance between puck and player is less than or equal to their radius' combined then they are touching
        return (c <= ((Character.getSize()/2)+(puck.getSize()/2)));
    }

    // Same as the other isTouchingPuck method but takes player position as parameters
    public boolean isTouchingPuck(Ball puck, double newXPosition, double newYPosition) {
        double a = puck.getXPosition()-newXPosition;
        double b = puck.getYPosition()-newYPosition;
        double c2 = (a * a) + (b * b);
        double c = Math.sqrt(c2);

        return (c <= ((Character.getSize()/2)+(puck.getSize()/2)));
    }

    public void movePlayer(double newXSpeed, double newYSpeed, Pitch pitch, Ball puck, SoundPlayer soundPlayer) {
        // Calculate new position using their speed
        Double newXPosition = Character.getXPosition()+newXSpeed;
        Double newYPosition = Character.getYPosition()+newYSpeed;

        // Check if player is in the boundary on the left side or right side of the pitch
        Boolean pIn1XBoundary = pitch.IsIn1XBoundary(Character, newXPosition);
        Boolean pIn2XBoundary = pitch.IsIn2XBoundary(Character, newXPosition);

        // Check if player is in the Y boundary of the pitch
        Boolean pInYBoundary = pitch.IsInYBoundary(Character, newYPosition);

        // Check if player will go inside the puck if moved to this new position 
        //Boolean isGoingToTouchPuck = isTouchingPuck(puck, newXPosition, newYPosition);

        // if (isGoingToTouchPuck == false) {
            // Check if they are in the correct sides of the arena depending on which player number they are
            if ((pIn1XBoundary && CharacterName == "Player1") || (pIn2XBoundary && CharacterName == "Player2")) {
                Character.setXSpeed(newXSpeed);
                Character.setXPosition(newXPosition);
            };
            
            if (pInYBoundary) {
                Character.setYSpeed(newYSpeed);
                Character.setYPosition(newYPosition);
            }
    //    }
        // else {
        //     // Character.setYPosition(Character.getYPosition()-(Math.sqrt(newYSpeed*newYSpeed)/4));
        //     // Character.setXPosition(Character.getXPosition()-(Math.sqrt(newXSpeed*newXSpeed)/4));
        //     deflectPuck(puck, soundPlayer);
        //      // Move player back by a quarter of it's speed to prevent puck from going inside the mallet
             
        // };
        
    }

}