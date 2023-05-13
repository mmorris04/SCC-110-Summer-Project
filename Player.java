import java.net.CacheRequest;
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
        Score += score;
    }
    public void deflectPuck(Ball puckBall) {

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
            newXSpeed = Character.getXSpeed()*1.5;
        };
        if (puckBall.getYSpeed() == 0) {
            newYSpeed = Character.getYSpeed()*1.5;
        };

        if ((Character.getXSpeed() > 0 && puckBall.getXSpeed() < 0) || (Character.getXSpeed() < 0 && puckBall.getXSpeed() > 0)) {
            newXSpeed = Character.getXSpeed()*1.5;
        }
        if ((Character.getYSpeed() > 0 && puckBall.getYSpeed() < 0) || (Character.getYSpeed() < 0 && puckBall.getYSpeed() > 0)) {
            newYSpeed = Character.getYSpeed()*1.5;
        }
        
		puckBall.setXSpeed(newXSpeed);
		puckBall.setYSpeed(newYSpeed);
	};
    public boolean isTouchingPuck(Ball puck) {
        double a = puck.getXPosition()-Character.getXPosition();
        double b = puck.getYPosition()-Character.getYPosition();
        double c2 = (a * a) + (b * b);
        double c = Math.sqrt(c2);

        // System.out.println("c: "+ c);
        // System.out.println("required dist: "+ ((Character.getSize()/2)+(puck.getSize()/2)+0.5));
        return (c <= ((Character.getSize()/2)+(puck.getSize()/2))+2);
    }

    public boolean isTouchingPuck(Ball puck, double newXPosition, double newYPosition) {
        double a = puck.getXPosition()-newXPosition;
        double b = puck.getYPosition()-newYPosition;
        double c2 = (a * a) + (b * b);
        double c = Math.sqrt(c2);

        return (c < ((Character.getSize()/2)+(puck.getSize()/2)));
    }

    public void movePlayer(double newXSpeed, double newYSpeed, Pitch pitch, Ball puck) {
        Double newXPosition = Character.getXPosition()+newXSpeed;
        Double newYPosition = Character.getYPosition()+newYSpeed;

        Boolean pIn1XBoundary = pitch.IsIn1XBoundary(Character, newXPosition);
        Boolean pIn2XBoundary = pitch.IsIn2XBoundary(Character, newXPosition);

        Boolean pInYBoundary = pitch.IsInYBoundary(Character, newYPosition);
        Boolean isGoingToTouchPuck = isTouchingPuck(puck, newXPosition, newYPosition);

        if (isGoingToTouchPuck == false) {
            if ((pIn1XBoundary && CharacterName == "Player1") || (pIn2XBoundary && CharacterName == "Player2")) {
                Character.setXSpeed(newXSpeed);
                Character.setXPosition(newXPosition);
            };
            
            if (pInYBoundary) {
                Character.setYSpeed(newYSpeed);
                Character.setYPosition(newYPosition);
            };
        };
        
    }

}