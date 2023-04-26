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
    }
}
