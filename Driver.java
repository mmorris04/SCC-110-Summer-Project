public class Driver {
    public static void main(String args[]) {
        GameArena gArena = new GameArena(1200, 720, true);

        Ball Player1 = new Ball(350, 360, 50, "BLUE", 10);
        Ball Player2 = new Ball(850, 360, 50, "BLUE", 10);
        Rectangle Border = new Rectangle(100, 100, 1000, 520, "BLUE", 0);
        Rectangle Pitch = new Rectangle(125, 125, 950, 470, "WHITE", 1);
        Rectangle CentreLine = new Rectangle(598.5, 100, 3, 520, "BLUE", 2);
        Rectangle Goal1 = new Rectangle(125, 270, 15, 180, "GREY", 2);
        Rectangle Goal2 = new Rectangle(1060, 270, 15, 180, "GREY", 2);

        Ball MiddleCircleWhite = new Ball(600, 360, 80, "WHITE", 3);
        Ball MiddleCircleBlue = new Ball(600, 360, 84, "BLUE", 2);

        Text Status = new Text("Null", 25, 50, 50, "WHITE");
        Text Player1Score = new Text("0", 35, 20, 360, "WHITE");
        Text Player2Score = new Text("0", 35, 1160, 360, "WHITE");

        gArena.addText(Status);
        gArena.addText(Player1Score);
        gArena.addText(Player2Score);

        gArena.addBall(Player1);
        gArena.addBall(Player2);
        gArena.addBall(MiddleCircleBlue);
        gArena.addBall(MiddleCircleWhite);

        gArena.addRectangle(Border);
        gArena.addRectangle(Pitch);
        gArena.addRectangle(CentreLine);
        gArena.addRectangle(Goal1);
        gArena.addRectangle(Goal2);

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

            Boolean p1InXBoundary = (newPlayer1XPosition <= (CentreLine.getXPosition()-Player1.getSize()) && newPlayer1XPosition >= (Pitch.getXPosition()+Player1.getSize()));
            Boolean p2InXBoundary = (newPlayer2XPosition >= (CentreLine.getXPosition()+CentreLine.getWidth()+Player1.getSize()) && newPlayer2XPosition <= (Pitch.getXPosition()+Pitch.getWidth()-Player1.getSize()));

            Boolean p1InYBoundary = (newPlayer1YPosition >= (Pitch.getYPosition()+Player2.getSize()) && newPlayer1YPosition <= (Pitch.getYPosition()+Pitch.getHeight()-Player2.getSize()));
            Boolean p2InYBoundary = (newPlayer2YPosition >= (Pitch.getYPosition()+Player2.getSize()) && newPlayer2YPosition <= (Pitch.getYPosition()+Pitch.getHeight()-Player2.getSize()));

            if (p1InXBoundary) {
                Player1.setXSpeed(player1XSpeed);
                Player1.setXPosition(Player1.getXPosition()+player1XSpeed);
            };

            if (p1InYBoundary) {
                Player1.setYSpeed(player1YSpeed);
                Player1.setYPosition(Player1.getYPosition()+player1YSpeed);
            };

            if (p2InXBoundary) {
                Player2.setXSpeed(player2XSpeed);
                Player2.setXPosition(Player2.getXPosition()+player2XSpeed);
            };

            if (p2InYBoundary) {
                Player2.setYSpeed(player2YSpeed);
                Player2.setYPosition(Player2.getYPosition()+player2YSpeed);
            };

            gArena.pause();
        }
        
    }
}
