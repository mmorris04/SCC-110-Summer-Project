public class Driver {
    public static void main(String args[]) {
        GameArena gArena = new GameArena(1200, 720, true);

        Player Player1 = new Player(gArena, 350, 360, 50, "BLUE", 10);
        Player Player2 = new Player(gArena, 850, 360, 50, "BLUE", 10);
       
        Pitch Pitch = new Pitch(gArena);
        
        Ball Puck = new Ball(600, 360, 50, "RED", 20);
        gArena.addBall(Puck);
        while (true) {

            // Calculate Player 1's speed

            Double player1XSpeed = 0.0;
            Double player1YSpeed = 0.0;

            if (gArena.up1Pressed()) {
                player1YSpeed -= 3;
            };
            if (gArena.down1Pressed()) {
                player1YSpeed += 3;
            };
            if (gArena.left1Pressed()) {
                player1XSpeed -= 3;
            };
            if (gArena.right1Pressed()) {
                player1XSpeed += 3;
            };

            // Calculate Player 2's speed
            Double player2XSpeed = 0.0;
            Double player2YSpeed = 0.0;

            if (gArena.up2Pressed()) {
                player2YSpeed -= 3;
            };
            if (gArena.down2Pressed()) {
                player2YSpeed += 3;
            };
            if (gArena.left2Pressed()) {
                player2XSpeed -= 3;
            };
            if (gArena.right2Pressed()) {
                player2XSpeed += 3;
            };

            Double newPlayer1XPosition = Player1.getXPosition()+player1XSpeed;
            Double newPlayer2XPosition = Player2.getXPosition()+player2XSpeed;
            Double newPlayer1YPosition = Player1.getYPosition()+player1YSpeed;
            Double newPlayer2YPosition = Player2.getYPosition()+player2YSpeed;
            Double newPuckXPosition = Puck.getXPosition()+Puck.getXSpeed();
            Double newPuckYPosition = Puck.getYPosition()+Puck.getYSpeed();

            Boolean p1InXBoundary = Pitch.IsInXBoundary(Player1.getCharacter(), newPlayer1XPosition);
            Boolean p2InXBoundary = Pitch.IsInXBoundary(Player2.getCharacter(), newPlayer2XPosition);
            Boolean p1InYBoundary = Pitch.IsInYBoundary(Player1.getCharacter(), newPlayer1YPosition);
            Boolean p2InYBoundary = Pitch.IsInYBoundary(Player2.getCharacter(), newPlayer2YPosition);

            if (p1InXBoundary) {
                Player1.setXSpeed(player1XSpeed);
                Player1.setXPosition(newPlayer1XPosition);
            };

            if (p1InYBoundary) {
                Player1.setYSpeed(player1YSpeed);
                Player1.setYPosition(newPlayer1YPosition);
            };

            if (p2InXBoundary) {
                Player2.setXSpeed(player2XSpeed);
                Player2.setXPosition(newPlayer2XPosition);
            };

            if (p2InYBoundary) {
                Player2.setYSpeed(player2YSpeed);
                Player2.setYPosition(newPlayer2YPosition);
            };

            if (Player1.isTouchingPuck(Puck)) {
                Player1.deflectPuck(Puck);
            };
            if (Player2.isTouchingPuck(Puck)) {
                Player2.deflectPuck(Puck);
            };
            
            if (Pitch.IsInXBoundary(Puck, newPuckXPosition)) {
                Puck.setXPosition(newPuckXPosition);
            }
            else {
                Puck.setXSpeed(Puck.getXSpeed()*-1);
            };

            if (Pitch.IsInYBoundary(Puck, newPuckYPosition)) {
                Puck.setXPosition(newPuckYPosition);
            }
            else {
                Puck.setYSpeed(Puck.getYSpeed()*-1);
            };

            gArena.pause();
        }
        
    }
}
