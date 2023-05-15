import java.awt.event.KeyEvent;
import java.lang.management.GarbageCollectorMXBean;

public class Driver {

    static int winningScore = 5;
    static double friction = 0.01;
    static GameArena gArena;
    static SoundPlayer soundPlayer = new SoundPlayer();
    static Player Player1;
    static Player Player2;
    static Pitch Pitch;
    static Ball Puck;
    static boolean GameActive = false;

    public static double roundto1DP(double num) {
        double multiplier = Math.pow(10, 1);
        double roundedNum = Math.round(num * multiplier) / multiplier;
        return roundedNum;
    };

    public static void StartRound(int PlayerTurn) {
        GameActive = true;
        
        gArena.addBall(Puck);
        // Reset puck and player positions 

        if (PlayerTurn == 1) {
            Pitch.UpdateStatus("Player 1 is serving!");
            Puck.setXPosition(550);
            Puck.setYPosition(360);
        }
        else {
            Pitch.UpdateStatus("Player 1 is serving!");
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

        while (GameActive == true) {

            Double player1XSpeed = 0.0;
            Double player1YSpeed = 0.0;
            Double player2XSpeed = 0.0;
            Double player2YSpeed = 0.0;
            Double newPuckXPosition = roundto1DP(Puck.getXPosition()+Puck.getXSpeed());
            Double newPuckYPosition = roundto1DP(Puck.getYPosition()+Puck.getYSpeed());

            if (Pitch.IsInXBoundary(Puck, newPuckXPosition)) {
                if ((Player1.isTouchingPuck(Puck) && Player2.isTouchingPuck(Puck)) == false) {
                    Puck.setXPosition(newPuckXPosition);
                }
            }
            else {
                Puck.setXSpeed(Puck.getXSpeed()*-1);
                soundPlayer.PlaySound("bounce.wav");
            };

            if (Pitch.IsInYBoundary(Puck, newPuckYPosition)) {
                if ((Player1.isTouchingPuck(Puck) && Player2.isTouchingPuck(Puck)) == false) {
                    Puck.setYPosition(newPuckYPosition);
                }
            }
            else {
                Puck.setYSpeed(Puck.getYSpeed()*-1);
                soundPlayer.PlaySound("bounce.wav");
            };

            if (Player1.isTouchingPuck(Puck)) {
                System.out.println("Player 1 touching puck");
                Player1.deflectPuck(Puck);
            };
            if (Player2.isTouchingPuck(Puck)) {
                System.out.println("Player 2 touching puck");
                Player2.deflectPuck(Puck);
            };

            if (gArena.letterPressed('W')) {
                player1YSpeed -= 4;
            };
            if (gArena.letterPressed('S')) {
                player1YSpeed += 4;
            };
            if (gArena.letterPressed('A')) {
                player1XSpeed -= 4;
            };
            if (gArena.letterPressed('D')) {
                player1XSpeed += 4;
            };
            
            if (gArena.upPressed()) {
                player2YSpeed -= 4;
            };
            if (gArena.downPressed()) {
                player2YSpeed += 4;
            };
            if (gArena.leftPressed()) {
                player2XSpeed -= 4;
            };
            if (gArena.rightPressed()) {
                player2XSpeed += 4;
            };

            

            
            Player1.movePlayer(player1XSpeed, player1YSpeed, Pitch, Puck);
            Player2.movePlayer(player2XSpeed, player2YSpeed, Pitch, Puck);

            

            
            if (Puck.getXSpeed() > 0 && ((Puck.getXSpeed()-friction) >= 0)) {
                Puck.setXSpeed(Puck.getXSpeed()-friction);
            }
            else if (Puck.getXSpeed() < 0 && ((Puck.getXSpeed()+friction) <= 0)) {
                Puck.setXSpeed(Puck.getXSpeed()+friction);
            }
            else if ( (Puck.getXSpeed() > 0 && ((Puck.getXSpeed()-friction) < friction)) || (Puck.getXSpeed() < 0 && ((Puck.getXSpeed()+friction) > -friction))) {
                Puck.setXSpeed(0);
            
            };
            
            if (Puck.getYSpeed() > 0 && ((Puck.getYSpeed()-friction) >= 0)) {
                Puck.setYSpeed(Puck.getYSpeed()-friction);
            }
            else if (Puck.getYSpeed() < 0 && ((Puck.getYSpeed()+friction) <= 0)) {
                Puck.setYSpeed(Puck.getYSpeed()+friction);
            }
            else if ( (Puck.getYSpeed() > 0 && ((Puck.getYSpeed()-friction) < friction)) || (Puck.getYSpeed() < 0 && ((Puck.getYSpeed()+friction) > -friction)) ) {
                Puck.setYSpeed(0);
            };

            int IsTouchingGoal = Pitch.IsTouchingGoal(Puck);

            if (IsTouchingGoal == 1) {
                Player2.setScore(Player2.getScore() + 1);
                Pitch.UpdateStatus("Player 2 scored!");
                soundPlayer.PlaySound("applause.wav");
                Puck.setXPosition(-200);
                GameActive = false;
            }
            else if (IsTouchingGoal == 2) {
                Player1.setScore(Player1.getScore() + 1);
                Pitch.UpdateStatus("Player 1 scored!");
                soundPlayer.PlaySound("applause.wav");
                Puck.setXPosition(-200);
                GameActive = false;
            }
            gArena.pause();
        };
        Pitch.UpdateScores(Player1.getScore(), Player2.getScore());

        try { Thread.sleep(1000); }
		catch (Exception e) {};

        if (Player1.getScore() >= winningScore && Player1.getScore() != Player2.getScore()) {
            Pitch.UpdateStatus("Player 1 wins! Press space to play another round!");
            soundPlayer.PlaySound("drumroll.wav");
        }
        else if (Player2.getScore() >= winningScore && Player1.getScore() != Player2.getScore()) {
            Pitch.UpdateStatus("Player 2 wins! Press space to play another round!");
            soundPlayer.PlaySound("drumroll.wav");
        }
        else {
            if (PlayerTurn == 1) {
                StartRound(2);
            }
            else {
                StartRound(1);
            }
        };


    };
    public static void main(String args[]) {
        gArena = new GameArena(1200, 720, true);
        soundPlayer = new SoundPlayer();
        Player1 = new Player(gArena, 350, 360, 50, "BLUE", 10, "Player1");
        Player2 = new Player(gArena, 850, 360, 50, "BLUE", 10, "Player2");
        Pitch = new Pitch(gArena);
        Puck = new Ball(650, 360, 30, "BLACK", 20);

        while (true) {
            soundPlayer.PlaySound("fanfare.wav");
            StartRound(1);
            while (gArena.spacePressed() == false) {
                gArena.pause();
            }
            Player1.setScore(0);
            Player2.setScore(0);
            Pitch.UpdateScores(0, 0);
        }
    }
}
