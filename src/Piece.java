public class Piece {
    private char hurufPiece;
    private int ukuran;
    enum Orientasi {
        HORIZONTAL,
        VERTIKAL
    }
    private Orientasi orientasi;
    private int x; // x dan y dimulai dari ujung kiri atas
    private int y;
    private boolean isPrimary;

    public Piece(char hurufPiece, int ukuran, Orientasi orientasi, int x, int y) {
        this.hurufPiece = hurufPiece;
        this.ukuran = ukuran;
        this.orientasi = orientasi;
        this.x = x;
        this.y = y;
        this.isPrimary = false;
    }

    public char getHurufPiece() {
        return hurufPiece;
    }
    public int getUkuran() {
        return ukuran;
    }
    public Orientasi getOrientasi() {
        return orientasi;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean isPrimary() {
        return isPrimary;
    }
    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }
    public void setHurufPiece(char hurufPiece) {
        this.hurufPiece = hurufPiece;
    }
    public void setUkuran(int ukuran) {
        this.ukuran = ukuran;
    }
    public void setOrientasi(Orientasi orientasi) {
        this.orientasi = orientasi;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void moveX(int deltaX) {
        if (orientasi == Orientasi.HORIZONTAL) {
            this.x += deltaX;
        } else {
            // Throw an exception
            throw new UnsupportedOperationException("Cannot move X for vertical piece");
        }
    }
    public void moveY(int deltaY) {
        if (orientasi == Orientasi.VERTIKAL) {
            this.y += deltaY;
        } else {
            // Throw an exception
            throw new UnsupportedOperationException("Cannot move Y for horizontal piece");
        }
    }
    public void printPiece() {
        if (orientasi == Orientasi.HORIZONTAL) {
            for (int i = 0; i < ukuran; i++) {
                System.out.print(hurufPiece);
            }
            System.out.println();
        } else {
            for (int i = 0; i < ukuran; i++) {
                System.out.println(hurufPiece);
            }
        }
    }
    public Piece copy() {
        Piece newPiece = new Piece(this.hurufPiece, this.ukuran, this.orientasi, this.x, this.y);
        newPiece.setPrimary(this.isPrimary);
        return newPiece;
    }
}
