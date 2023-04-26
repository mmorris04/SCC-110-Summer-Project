public class Driver {
    public static void main(String args[]) {
        GameArena gArena = new GameArena(1200, 720, true);

        Ball Player1 = new Ball(80, 360, 50, "BLUE", 0);
        Ball Player2 = new Ball(1120, 360, 50, "BLUE", 0);

        gArena.addBall(Player1);
        gArena.addBall(Player2);
    }
}
