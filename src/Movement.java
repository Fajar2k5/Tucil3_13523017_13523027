public class Movement {
    public enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }

    private char huruf;
    private Direction arah;
    private int jarak;

    public Movement(char huruf, Direction direction, int distance) {
        this.huruf = huruf;
        this.arah = direction;
        this.jarak = distance;
    }
    public char gethuruf() {
        return huruf;
    }

    public Direction getArah() {
        return arah;
    }

    public int getJarak() {
        return jarak;
    }
    public void sethuruf(char huruf) {
        this.huruf = huruf;
    }
    public void setArah(Direction arah) {
        this.arah = arah;
    }
    public void setJarak(int jarak) {
        this.jarak = jarak;
    }
    public String toString() {
        return String.format("Movement(%c, %s, %d)", huruf, getArahString(), jarak);
    }
    public String getArahString() {
        switch (arah) {
            case UP:
                return "UP";
            case DOWN:
                return "DOWN";
            case LEFT:
                return "LEFT";
            case RIGHT:
                return "RIGHT";
            default:
                return "";
        }
    }

}
