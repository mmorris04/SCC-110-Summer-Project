public class Puck {
    private Ball PuckObj;
    private GameArena gArena;
    private SoundPlayer soundPlayer;

    public Puck(GameArena garena, int x, int y, int diameter, String colour, int layer, SoundPlayer soundPlayerInit) {
        gArena = garena;
        PuckObj = new Ball(x, y, diameter, colour, layer);
        gArena.addBall(PuckObj);
        soundPlayer = soundPlayerInit;
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
	 * Handles the pucks collisions with the pitch boundary.
	*/
    public int handlePuckCollisions(Pitch pitch, Player Player1, Player Player2) {
        Double newPuckXPosition = roundTo1DP(getXPosition()+getXSpeed());
        Double newPuckYPosition = roundTo1DP(getYPosition()+getYSpeed());

        boolean Player1TouchingPuck = Player1.isTouchingPuck(this, newPuckXPosition, newPuckYPosition, Player1.getXPosition(), Player1.getYPosition());
        boolean Player2TouchingPuck = Player2.isTouchingPuck(this, newPuckXPosition, newPuckYPosition, Player2.getXPosition(), Player2.getYPosition());

        // Check if puck fits inside the X boundary of the pitch

        if (pitch.isInXBoundary(getPuckObj(), newPuckXPosition)) {
            
            if (Player1TouchingPuck == false && Player2TouchingPuck == false) {
                //If it isn't touching Player 1 or Player 2 set it's position (to prevent the puck going inside the mallet)
                setXPosition(newPuckXPosition, pitch, false);
            }
            else if (Player1TouchingPuck) {
                Player1.deflectPuck(this, soundPlayer, pitch);
                return 0;
            }
            else if (Player2TouchingPuck) {
                Player2.deflectPuck(this, soundPlayer, pitch);
                return 0;
            }
        }
        else {
            //If puck is touching boundary reverse its speed and play bound sound effect
            setXPosition(getXPosition()+(getXSpeed()*-1), pitch, false);
            setXSpeed(getXSpeed()*-1);
            soundPlayer.playSound("bounce.wav");
        };

        if (pitch.isInYBoundary(getPuckObj(), newPuckYPosition)) {
            if (Player1TouchingPuck == false && Player2TouchingPuck == false) {
                //If it isn't touching Player 1 or Player 2 set it's position (to prevent the puck going inside the mallet)
                setYPosition(newPuckYPosition, pitch, false);
            }
            else if (Player1TouchingPuck) {
                Player1.deflectPuck(this, soundPlayer, pitch);
                return 0;
            }
            else if (Player2TouchingPuck) {
                Player2.deflectPuck(this, soundPlayer, pitch);
                return 0;
            }
        }
        else {
            //If puck is touching boundary reverse its speed and play bound sound effect
            setYPosition(getYPosition()+(getYSpeed()*-1), pitch, false);
            setYSpeed(getYSpeed()*-1);
            soundPlayer.playSound("bounce.wav");
        };
        return 1;
        
    }

    /**
	* Applies friction to the puck and reduces it's speed accordingly.
	* @param friction The friction value
	*/
    public void handlePuckFriction(double friction) {
        // Reduce puck X speed by friction value until almost 0
        if (getXSpeed() > 0 && ((getXSpeed()-friction) >= 0)) {
            setXSpeed(getXSpeed()-friction);
        }
        // If speed is negative, increase speed by friction value until almost 0
        else if (getXSpeed() < 0 && ((getXSpeed()+friction) <= 0)) {
            setXSpeed(getXSpeed()+friction);
        }
        // If speed is less than friction value but greater than 0, set speed to 0
        else if ( (getXSpeed() > 0 && ((getXSpeed()-friction) < friction)) || (getXSpeed() < 0 && ((getXSpeed()+friction) > -friction))) {
            setXSpeed(0);
        
        };
        
        // Reduce puck Y speed by friction value until almost 0
        if (getYSpeed() > 0 && ((getYSpeed()-friction) >= 0)) {
            setYSpeed(getYSpeed()-friction);
        }
        // If speed is negative, increase speed by friction until almost 0
        else if (getYSpeed() < 0 && ((getYSpeed()+friction) <= 0)) {
            setYSpeed(getYSpeed()+friction);
        }
        // If speed is greater than friction value but less than 0, set speed to 0
        else if ( (getYSpeed() > 0 && ((getYSpeed()-friction) < friction)) || (getYSpeed() < 0 && ((getYSpeed()+friction) > -friction)) ) {
            setYSpeed(0);
        };
    }

    /**
	* Set the X speed of the puck to a given value.
	* @param pos The new X speed value
	*/
    public void setXSpeed(double speed) {
        PuckObj.setXSpeed(speed);
    }

    /**
	* Set the Y speed of the puck to a given value.
	* @param pos The new Y speed value
	*/
    public void setYSpeed(double speed) {
        PuckObj.setYSpeed(speed);
    }
    /**
	* Returns the X speed of the puck.
	* @return The current X speed of the puck.
	*/
    public double getXSpeed() {
        return PuckObj.getXSpeed();
    }
    /**
	* Returns the Y speed of the puck.
	* @return The current Y speed of the puck.
	*/
    public double getYSpeed() {
        return PuckObj.getYSpeed();
    }
    /**
	* Get the X Position of the puck.
    * @return The X position of the puck.
	*/
    public double getXPosition() {
        return PuckObj.getXPosition();
    }
    /**
	* Get the Y position of the puck.
    * @return The Y position of the puck.
	*/
    public double getYPosition() {
        return PuckObj.getYPosition();
    }

    /**
	* Set the X position of the puck to a given value.
	* @param pos The new X position value
    * @param pitch The pitch the game is being played on
    * @param forced Whether or not the position change ignores the pitch boundary
	*/
    public void setXPosition(double pos, Pitch pitch, boolean forced) {
        if (pitch.isInXBoundary(PuckObj, pos) && !forced) {
            PuckObj.setXPosition(pos);
        }
        else if (forced) {
            PuckObj.setXPosition(pos);
        }
    }
    /**
	* Set the Y position of the puck to a given value .
	* @param num The decimal to be rounded to 1 decimal place.
    * @param pitch The pitch the game is being played on
    * @param forced Whether or not the position change ignores the pitch boundary
	*/
    public void setYPosition(double pos, Pitch pitch, boolean forced) {
        if (pitch.isInYBoundary(PuckObj, pos) && !forced) {
            PuckObj.setYPosition(pos);
        }
        else if (forced) {
            PuckObj.setYPosition(pos);
        }
    }
    /** 
    * Returns the PuckObj of the puck
    */
    public Ball getPuckObj() {
        return PuckObj;
    }
}