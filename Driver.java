
/**
 * This class runs the Air Hockey Game program.
 * @author Matthew Morris
 */

public class Driver {

    // Objects
    static GameArena gArena;
    static SoundPlayer soundPlayer = new SoundPlayer();
    static Player Player1;
    static Player Player2;
    static Pitch Pitch;
    static Ball Puck;

    // Game Values

    static int playerMovementSpeed = 6;
    static int winningScore = 5;
    static double friction = 0.01;
    static boolean gameActive = false;

    // Cheats

    static boolean smallMallets = false;
    static boolean bigPuck = false;
    static boolean fasterMallets = false;
    static boolean goal1Moving = false;
    static boolean goal2Moving = false;

    /**
	 * Round to 1 decimal place.
	 * @param num The decimal to be rounded to 1 decimal place.
	 */

    private static double roundTo1DP(double num) {
        double multiplier = Math.pow(10, 1);
        double roundedNum = Math.round(num * multiplier) / multiplier;
        return roundedNum;
    };

    /**
	 * Start a round of Air Hockey.
	 * @param PlayerTurn The integer representation of the player who will be serving this round.
	 */

    public static void startRound(int PlayerTurn) {
        gameActive = true;
        int PlayerScored = 0;
        gArena.addBall(Puck);
        // Reset puck and player positions 

        if (PlayerTurn == 1) {
            Pitch.updateStatus("Player 1 is serving!");
            Puck.setXPosition(550);
            Puck.setYPosition(360);
        }
        else {
            Pitch.updateStatus("Player 1 is serving!");
            Puck.setXPosition(650);
            Puck.setYPosition(360);
        };
        Puck.setXSpeed(0);
        Puck.setYSpeed(0);

        Player1.setXPosition(350);
        Player1.setYPosition(360);
        Player1.setXSpeed(0);
        Player1.setYSpeed(0);

        Player2.setXPosition(850);
        Player2.setYPosition(360);
        Player2.setXSpeed(0);
        Player2.setYSpeed(0);

        while (gameActive == true) {

            // Handle cheats
            
            
            handleKeyEvents(Pitch);
            handlePuckCollisions();
            handlePlayerMovement();
            handlePuckFriction();
            
            // Check if puck is touching goals
            int IsTouchingGoal = Pitch.isTouchingGoal(Puck);

            if (IsTouchingGoal == 1) {
                Player2.setScore(Player2.getScore() + 1);
                Pitch.updateStatus("Player 2 scored!");
                soundPlayer.playSound("applause.wav");
                Puck.setXPosition(-200);
                gameActive = false;
                PlayerScored = 2;
            }
            else if (IsTouchingGoal == 2) {
                Player1.setScore(Player1.getScore() + 1);
                Pitch.updateStatus("Player 1 scored!");
                soundPlayer.playSound("applause.wav");
                Puck.setXPosition(-200);
                gameActive = false;
                PlayerScored = 1;
            }
            gArena.pause();
        };

        // Update scoreboard
        Pitch.updateScores(Player1.getScore(), Player2.getScore());

        // Pause for a second
        try { Thread.sleep(1000); }
		catch (Exception e) {};

        // Check if either player has won the match
        if (Player1.getScore() >= winningScore && Player1.getScore() != Player2.getScore()) {
            Pitch.updateStatus("Player 1 wins! Press space to play another round!");
            soundPlayer.playSound("drumroll.wav");
        }
        else if (Player2.getScore() >= winningScore && Player1.getScore() != Player2.getScore()) {
            Pitch.updateStatus("Player 2 wins! Press space to play another round!");
            soundPlayer.playSound("drumroll.wav");
        }
        else {
            // Start round with player who lost the round serving
            if (PlayerScored == 1) {
                startRound(2);
            }
            else {
                startRound(1);
            }
        };


    };

    /**
	 * Handles Key Inputs by the player (excluding movement controls).
	 * @param Pitch The pitch the game is being played on.
	 */
    private static void handleKeyEvents(Pitch pitch) {
        // Mute sound effects
        if (gArena.letterPressed('M')) {
            soundPlayer.toggleMute(pitch);
            try { Thread.sleep(500); }
            catch (Exception e) {};
        };
        
        

        // Change winning score cheat
        if (!gameActive) {
            if (gArena.upPressed() && winningScore < 9) {
                winningScore += 1;
                try { Thread.sleep(500); }
                catch (Exception e) {};
            };
            if (gArena.downPressed() && winningScore > 1) {
                winningScore -= 1;
                try { Thread.sleep(500); }
                catch (Exception e) {};
            }
        }
        pitch.updateScoreToWin(winningScore);
        
        // Big puck cheat
        if (gArena.letterPressed('G')) {
            if (bigPuck == true) {
                Puck.setSize(30);
                bigPuck = false;
            }   
            else {
                Puck.setSize(60);
                bigPuck = true;
            }
            try { Thread.sleep(500); }
            catch (Exception e) {};
        };
            
        // Small mallets cheat
        if (gArena.letterPressed('H')) {
            if (smallMallets == true) {
                Player1.getMallet().setSize(50);
                Player2.getMallet().setSize(50);

                smallMallets = false;
            }
            else {
                Player1.getMallet().setSize(25);
                Player2.getMallet().setSize(25);

                smallMallets = true;
            }
            try { Thread.sleep(500); }
            catch (Exception e) {};
        };
        
        // Faster mallets cheat
        if (gArena.letterPressed('F')) {
            if (fasterMallets == true) {
                playerMovementSpeed /= 2; 

                fasterMallets = false;
            }
            else {
                playerMovementSpeed *= 2;

                fasterMallets = true;
            }
            try { Thread.sleep(500); }
            catch (Exception e) {};
        };

        // Player 1 goal moving cheat
        if (gArena.letterPressed('J')) {
            if (goal1Moving == true) {
                goal1Moving = false;
            }
            else {
                goal1Moving = true;
            }
            try { Thread.sleep(500); }
            catch (Exception e) {};
        };
        if (goal1Moving) {
            Pitch.moveGoal("Goal1");
        }
        else {
            Pitch.resetGoalPos("Goal1");
        }
        
        // Player 2 goal moving cheat
        if (gArena.letterPressed('K')) {
            if (goal2Moving == true) {
                goal2Moving = false;
            }
            else {
                goal2Moving = true;
            }
            try { Thread.sleep(500); }
            catch (Exception e) {};
        };
        if (goal2Moving) {
            Pitch.moveGoal("Goal2");
        }
        else {
            Pitch.resetGoalPos("Goal2");
        }

        
    }

    /**
	 * Reduces puck speed using the friction variable.
	 */

    private static void handlePuckFriction() {
        // Reduce puck X speed by friction value until almost 0
        if (Puck.getXSpeed() > 0 && ((Puck.getXSpeed()-friction) >= 0)) {
            Puck.setXSpeed(Puck.getXSpeed()-friction);
        }
        // If speed is negative, increase speed by friction value until almost 0
        else if (Puck.getXSpeed() < 0 && ((Puck.getXSpeed()+friction) <= 0)) {
            Puck.setXSpeed(Puck.getXSpeed()+friction);
        }
        // If speed is less than friction value but greater than 0, set speed to 0
        else if ( (Puck.getXSpeed() > 0 && ((Puck.getXSpeed()-friction) < friction)) || (Puck.getXSpeed() < 0 && ((Puck.getXSpeed()+friction) > -friction))) {
            Puck.setXSpeed(0);
        
        };
        
        // Reduce puck Y speed by friction value until almost 0
        if (Puck.getYSpeed() > 0 && ((Puck.getYSpeed()-friction) >= 0)) {
            Puck.setYSpeed(Puck.getYSpeed()-friction);
        }
        // If speed is negative, increase speed by friction until almost 0
        else if (Puck.getYSpeed() < 0 && ((Puck.getYSpeed()+friction) <= 0)) {
            Puck.setYSpeed(Puck.getYSpeed()+friction);
        }
        // If speed is greater than friction value but less than 0, set speed to 0
        else if ( (Puck.getYSpeed() > 0 && ((Puck.getYSpeed()-friction) < friction)) || (Puck.getYSpeed() < 0 && ((Puck.getYSpeed()+friction) > -friction)) ) {
            Puck.setYSpeed(0);
        };
    }


    /**
	 * Handles the pucks collisions with the pitch boundary.
	*/
    private static void handlePuckCollisions() {
        Double newPuckXPosition = roundTo1DP(Puck.getXPosition()+Puck.getXSpeed());
        Double newPuckYPosition = roundTo1DP(Puck.getYPosition()+Puck.getYSpeed());

        // Check if puck fits inside the X boundary of the pitch

        if (Pitch.isInXBoundary(Puck, newPuckXPosition)) {
            if ((Player1.isTouchingPuck(Puck) && Player2.isTouchingPuck(Puck)) == false) {
                //If it isn't touching Player 1 or Player 2 set it's position (to prevent the puck going inside the mallet)
                Puck.setXPosition(newPuckXPosition);
            }
        }
        else {
            //If puck is touching boundary reverse its speed and play bound sound effect
            Puck.setXSpeed(Puck.getXSpeed()*-1);
            soundPlayer.playSound("bounce.wav");
        };

        if (Pitch.isInYBoundary(Puck, newPuckYPosition)) {
            if ((Player1.isTouchingPuck(Puck) && Player2.isTouchingPuck(Puck)) == false) {
                //If it isn't touching Player 1 or Player 2 set it's position (to prevent the puck going inside the mallet)
                Puck.setYPosition(newPuckYPosition);
            }
        }
        else {
            //If puck is touching boundary reverse its speed and play bound sound effect
            Puck.setYSpeed(Puck.getYSpeed()*-1);
            soundPlayer.playSound("bounce.wav");
        };

        // Check if either player is touching puck and deflect if they are
        if (Player1.isTouchingPuck(Puck)) {
            System.out.println("Player 1 touching puck");
            Player1.deflectPuck(Puck, soundPlayer);
        };
        if (Player2.isTouchingPuck(Puck)) {
            System.out.println("Player 2 touching puck");
            Player2.deflectPuck(Puck, soundPlayer);
        };
    }

    /**
	 * Handles Key Inputs by the player exclusively for movement controls.
	 */
    private static void handlePlayerMovement() {

        // Player controls: WASD for player 1, Arrow keys for player 2

        Double player1XSpeed = 0.0;
        Double player1YSpeed = 0.0;
        Double player2XSpeed = 0.0;
        Double player2YSpeed = 0.0;
            
        // Check inputs for Player 1 and Player 2

        if (gArena.letterPressed('W')) {
            player1YSpeed -= playerMovementSpeed;
        };
        if (gArena.letterPressed('S')) {
            player1YSpeed += playerMovementSpeed;
        };
        if (gArena.letterPressed('A')) {
            player1XSpeed -= playerMovementSpeed;
        };
        if (gArena.letterPressed('D')) {
            player1XSpeed += playerMovementSpeed;
        };

        if (gArena.upPressed()) {
            player2YSpeed -= playerMovementSpeed;
        };
        if (gArena.downPressed()) {
            player2YSpeed += playerMovementSpeed;
        };
        if (gArena.leftPressed()) {
            player2XSpeed -= playerMovementSpeed;
        };
        if (gArena.rightPressed()) {
            player2XSpeed += playerMovementSpeed;
        };

        // Move both players
        Player1.movePlayer(player1XSpeed, player1YSpeed, Pitch, soundPlayer);
        Player2.movePlayer(player2XSpeed, player2YSpeed, Pitch, soundPlayer); 
    }

    public static void main(String args[]) {

        // Instantiate objects

        gArena = new GameArena(1200, 720, true);
        soundPlayer = new SoundPlayer();
        Player1 = new Player(gArena, 350, 360, 50, "BLUE", 10, "Player1");
        Player2 = new Player(gArena, 850, 360, 50, "BLUE", 10, "Player2");
        Pitch = new Pitch(gArena);
        Puck = new Ball(650, 360, 30, "BLACK", 20);
        Pitch.updateStatus("Press space to start a round!");
        
        while (true) {
            // Wait until they press the space bar to restart the match
            while (gArena.spacePressed() == false) {
                handleKeyEvents(Pitch);
                gArena.pause();
            }

            // Reset scores
            Player1.setScore(0);
            Player2.setScore(0);
            Pitch.updateScores(0, 0);

            // Play sound at beginning of match

            soundPlayer.playSound("fanfare.wav");
            startRound(1);
        }
    }
}
