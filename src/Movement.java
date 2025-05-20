import java.util.List;

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
        return String.format("Move %c %s, %d blocks", huruf, getArahString(), jarak);
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
    public boolean equals(Movement other) {
        return this.huruf == other.huruf && this.arah == other.arah && this.jarak == other.jarak;
    }
    public boolean isReverse(Movement other) {
        if (this.huruf == other.huruf && this.jarak == other.jarak)
        {
            if (this.getArah() == Direction.UP && other.getArah() == Direction.DOWN) {
                return true;
            } else if (this.getArah() == Direction.DOWN && other.getArah() == Direction.UP) {
                return true;
            } else if (this.getArah() == Direction.LEFT && other.getArah() == Direction.RIGHT) {
                return true;
            } else if (this.getArah() == Direction.RIGHT && other.getArah() == Direction.LEFT) {
                return true;
            }
        }
        return false;
    }

    public boolean isUselessReverseInList(List<Movement> list) {
        for (int i = list.size() - 1; i >= 0; i--) {
            Movement movement = list.get(i);
            if (movement.isReverse(this)) {
                return true;
            }
            if (movement.isSameLetter(this)) {
                break;
            }
        }
        return false;
    }

    public boolean isSameLetter(Movement other) {
        return this.huruf == other.huruf;
    }
}
