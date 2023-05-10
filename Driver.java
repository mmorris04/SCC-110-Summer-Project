public class Driver {
    public static void main(String args[]) {
        GameArena gArena = new GameArena(1200, 720, true);

        Player Player1 = new Player(gArena, 350, 360, 50, "BLUE", 10, "Player1");
        Player Player2 = new Player(gArena, 850, 360, 50, "BLUE", 10, "Player2");
       
        Pitch pitch = new Pitch(gArena);
        
        Ball Puck = new Ball(600, 360, 50, "RED", 20);
        Puck.setXSpeed(6);
        Puck.setYSpeed(6);
        gArena.addBall(Puck);
        while (true) {

            if (Player1.isTouchingPuck(Puck)) {
                System.out.println("Player 1 touching puck");
                Player1.deflectPuck(Puck);
            };
            if (Player2.isTouchingPuck(Puck)) {
                System.out.println("Player 2 touching puck");
                Player2.deflectPuck(Puck);
            };

            // Calculate Player 1's speed

            Double player1XSpeed = 0.0;
            Double player1YSpeed = 0.0;

            if (gArena.up1Pressed()) {
                player1YSpeed -= 4;
            };
            if (gArena.down1Pressed()) {
                player1YSpeed += 4;
            };
            if (gArena.left1Pressed()) {
                player1XSpeed -= 4;
            };
            if (gArena.right1Pressed()) {
                player1XSpeed += 4;
            };

            // Calculate Player 2's speed
            Double player2XSpeed = 0.0;
            Double player2YSpeed = 0.0;

            if (gArena.up2Pressed()) {
                player2YSpeed -= 4;
            };
            if (gArena.down2Pressed()) {
                player2YSpeed += 4;
            };
            if (gArena.left2Pressed()) {
                player2XSpeed -= 4;
            };
            if (gArena.right2Pressed()) {
                player2XSpeed += 4;
            };

            
            Player1.movePlayer(player1XSpeed, player1YSpeed, pitch, Puck);
            Player2.movePlayer(player2XSpeed, player2YSpeed, pitch, Puck);

            Double newPuckXPosition = Puck.getXPosition()+Puck.getXSpeed();
            Double newPuckYPosition = Puck.getYPosition()+Puck.getYSpeed();
            if (pitch.IsInXBoundary(Puck, newPuckXPosition)) {
                if ((Player1.isTouchingPuck(Puck) && Player2.isTouchingPuck(Puck)) == false) {
                    System.out.println("Puck moved to new X position");
                    Puck.setXPosition(newPuckXPosition);
                }
            }
            else {
                System.out.println("Puck X speed inversed");
                Puck.setXSpeed(Puck.getXSpeed()*-1);
            };

            if (pitch.IsInYBoundary(Puck, newPuckYPosition)) {
                if ((Player1.isTouchingPuck(Puck) && Player2.isTouchingPuck(Puck)) == false) {
                    System.out.println("Puck moved to new Y position");
                    Puck.setYPosition(newPuckYPosition);
                }
                
            }
            else {
                System.out.println("Puck Y speed inversed");
                Puck.setYSpeed(Puck.getYSpeed()*-1);
            };

            

            gArena.pause();
        }
        
    }
}
