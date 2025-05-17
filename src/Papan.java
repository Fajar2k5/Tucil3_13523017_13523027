import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
!!!!TODO
Kasih validasi kalau format penulisan di file salah
 */

public class Papan {
    private char[][] papan;
    private int baris;
    private int kolom;
    private int jumlahNon;
    private char charUtama;
    private int keluarX;
    private int keluarY;
    private List<Piece> listNonPiece = new ArrayList<>();
    private Piece primaryPiece;

    public Papan(int baris, int kolom) {
        this.baris = baris;
        this.kolom = kolom;
        this.charUtama = 'P';
        this.papan = new char[baris][kolom];
        this.jumlahNon = 0;

        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                papan[i][j] = ' ';
            }
        }
    }

    // Deep copy constructor
    public Papan(Papan papan) {
        this.baris = papan.baris;
        this.kolom = papan.kolom;
        this.charUtama = papan.charUtama;
        this.jumlahNon = papan.jumlahNon;
        this.papan = new char[baris][kolom];
        for (int i = 0; i < baris; i++) {
            System.arraycopy(papan.papan[i], 0, this.papan[i], 0, kolom);
        }
        this.keluarX = papan.keluarX;
        this.keluarY = papan.keluarY;
        this.listNonPiece = new ArrayList<>(papan.listNonPiece);
        this.primaryPiece = papan.primaryPiece;
    }

    public Papan() {
        this.baris = 0;
        this.kolom = 0;
        this.charUtama = 'P';
        this.papan = new char[baris][kolom];
        this.jumlahNon = 0;
    }

    public void setChar(int baris, int kolom, char c) {
        if (baris >= 0 && baris < this.baris && kolom >= 0 && kolom < this.kolom) {
            papan[baris][kolom] = c;
        }
    }
    public char getChar(int baris, int kolom) {
        if (baris >= 0 && baris < this.baris && kolom >= 0 && kolom < this.kolom) {
            return papan[baris][kolom];
        }
        return ' ';
    }
    public void setCharUtama(char c) {
        this.charUtama = c;
    }
    public char getCharUtama() {
        return this.charUtama;
    }
    public void setJumlahNon(int jumlahNon) {
        this.jumlahNon = jumlahNon;
    }
    public int getJumlahNon() {
        return this.jumlahNon;
    }
    public void setPapan(char[][] papan) {
        this.papan = papan;
    }
    public char[][] getPapan() {
        return this.papan;
    }
    public int getBaris() {
        return this.baris;
    }
    public int getKolom() {
        return this.kolom;
    }
    public void printPapan() {
        if (keluarY == -1){
            for (int i = 0; i < kolom; i++) {
                if (i != keluarX) {
                    System.out.print(" ");
                } else {
                    System.out.print("K");
                }
            }
            System.out.println();
            for (int i = 0; i < baris; i++) {
                for (int j = 0; j < kolom; j++) {
                    System.out.print(papan[i][j] + " ");
                }
                System.out.println();
            }
        } else if (keluarX == -1) {
            for (int i = 0; i < baris; i++) {
                if (i != keluarY) {
                    System.out.print("  ");
                    for (int j = 0; j < kolom; j++) {
                        System.out.print(papan[i][j] + " ");
                    }
                } else {
                    System.out.print("K ");
                    for (int j = 0; j < kolom; j++) {
                        System.out.print(papan[i][j] + " ");
                    }
                }
                System.out.println();
            }
        } else if (keluarX == this.kolom) {
            for (int i = 0; i < baris; i++) {
                for (int j = 0; j < kolom; j++) {
                    System.out.print(papan[i][j] + " ");
                }
                if (i == keluarY) {
                    System.out.print("K");
                }
                System.out.println();
            }
        } else {
            for (int i = 0; i < baris+1; i++) {
                if (i == keluarY) {
                    for (int j = 0; j < kolom; j++) {
                        if (j == keluarX) {
                            System.out.print("K ");
                        } else {
                            System.out.print("  ");
                        }
                    }
                    System.out.println();
                } else {
                    for (int j = 0; j < kolom; j++) {
                        System.out.print(papan[i][j] + " ");
                    }
                    System.out.println();
                }
            }
        }
        
    }
    public void clearPapan() {
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                papan[i][j] = ' ';
            }
        }
    }
    public void readFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int i = 0;
            // Read the first line to get the dimensions
            line = br.readLine();
            if (line != null) {
                String[] dimensions = line.split(" ");
                this.baris = Integer.parseInt(dimensions[0]);
                System.out.println("baris: " + baris);
                this.kolom = Integer.parseInt(dimensions[1]);
                System.out.println("kolom: " + kolom);
                this.papan = new char[baris][kolom];
            }
            char[][] tempPapan = new char[baris+1][kolom+1];
            //fill the tempPapan with ' '
            for (int x = 0; x < baris + 1; x++) {
                for (int y = 0; y < kolom + 1; y++) {
                    tempPapan[x][y] = ' ';
                }
            }
            // Read the next one line to read into jumlahNon attribute
            line = br.readLine();
            if (line != null) {
                this.jumlahNon = Integer.parseInt(line);
            }
            // Read the rest of the lines to fill the board
            while ((line = br.readLine()) != null && i < baris + 1) {
                for (int j = 0; j < kolom + 1; j++) {
                    if (j < line.length()) {
                        tempPapan[i][j] = line.charAt(j);
                        if (line.charAt(j) == 'K') {
                            this.keluarX = j;
                            this.keluarY = i;
                            if (keluarY == 0 && keluarX < this.kolom) {
                                this.keluarY = -1;
                            } else if (keluarX == 0 && keluarY < this.baris) {
                                this.keluarX = -1;
                            }
                        }
                    }
                    
                }
                i++;
            }
            List<Character> items = new ArrayList<>();
            for (int k = 0; k < baris+1; k++) {
                for (int j = 0; j < tempPapan[k].length; j++) {
                    char c = tempPapan[k][j];
                    if (c != ' ' && c != 'K') {
                        items.add(c);
                    }
                }
            }
            // Print all the items
            // for (int k = 0; k < items.size(); k++) {
            //     System.out.print(items.get(k) + " ");
            // }
            for (int k = 0; k < baris; k++) {
                for (int j = 0; j < kolom; j++) {
                    this.papan[k][j] = items.get(k * kolom + j);
                }
            }
            boolean[][] isChecked = new boolean[baris][kolom];
            for (int k = 0; k < baris; k++) {
                for (int j = 0; j < kolom; j++) {
                    if (!isChecked[k][j]) {
                        isChecked[k][j] = true;
                        char c = papan[k][j];
                        if (c == '.') continue;
                        if (j+1 < this.kolom && papan[k][j+1] == c) {
                            int count = 1;
                            while (j+count < this.kolom && papan[k][j+count] == c) {
                                isChecked[k][j+count] = true;
                                count++;
                            }
                            Piece piece = new Piece(c, count, Piece.Orientasi.HORIZONTAL, j, k);
                            if (c == charUtama) {
                                piece.setPrimary(true);
                                this.primaryPiece = piece;
                                continue;
                            }
                            listNonPiece.add(piece);
                        } else if (k + 1 < baris && papan[k+1][j] == c) {
                            int count = 1;
                            while (k + count < baris && papan[k+count][j] == c) {
                                isChecked[k+count][j] = true;
                                count++;
                            }
                            Piece piece = new Piece(c, count, Piece.Orientasi.VERTIKAL, j, k);
                            if (c == charUtama) {
                                piece.setPrimary(true);
                                this.primaryPiece = piece;
                                continue;
                            }
                            listNonPiece.add(piece);
                        }
                        
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isFull() {
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                if (papan[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
    
    public Papan copy() {
        Papan newPapan = new Papan(this.baris, this.kolom);
        newPapan.setCharUtama(this.charUtama);
        newPapan.setJumlahNon(this.jumlahNon);
        
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                newPapan.setChar(i, j, this.papan[i][j]);
            }
        }
        
        return newPapan;
    }
    
    public boolean isEmpty(int baris, int kolom) {
        if (baris >= 0 && baris < this.baris && kolom >= 0 && kolom < this.kolom) {
            return papan[baris][kolom] == '.';
        }
        return false;
    }
    
    public void saveToFile(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < baris; i++) {
                for (int j = 0; j < kolom; j++) {
                    bw.write(papan[i][j]);
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getKeluarX() {
        return keluarX;
    }
    public int getKeluarY() {
        return keluarY;
    }
    public void setKeluarX(int keluarX) {
        this.keluarX = keluarX;
    }
    public void setKeluarY(int keluarY) {
        this.keluarY = keluarY;
    }
    public void addPiece(Piece Piece) {
        listNonPiece.add(Piece);
    }
    public List<Piece> getListNonPiece() {
        return listNonPiece;
    }
    public void setListNonPiece(List<Piece> listNonPiece) {
        this.listNonPiece = listNonPiece;
    }
    public void clearListNonPiece() {
        listNonPiece.clear();
    }
    
    public void removePiece(int index) {
        if (index >= 0 && index < listNonPiece.size()) {
            listNonPiece.remove(index);
        }
    }
    public void removePiece(char hurufPiece) {
        listNonPiece.removeIf(Piece -> Piece.getHurufPiece() == hurufPiece);
    }
    public void printAllPiece() {
        for (Piece piece : listNonPiece) {
            piece.printPiece();
        }
    }
    public void movePiece(char hurufPiece, int jarak) {
        boolean found = false;
        for (Piece piece : listNonPiece) {
            if (piece.getHurufPiece() == hurufPiece) {
                found = true;
                if (piece.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
                    if (piece.getX() + jarak >= 0 && piece.getX() + jarak < this.kolom) {
                        piece.moveX(jarak);
                        // Clear old position
                        for (int i = 0; i < piece.getUkuran(); i++) {
                            papan[piece.getY()][piece.getX() - jarak + i] = '.';
                        }
                        // Set new position
                        for (int i = 0; i < piece.getUkuran(); i++) {
                            papan[piece.getY()][piece.getX() + i] = piece.getHurufPiece();
                        }
                    }
                } else {
                    if (piece.getY() + jarak >= 0 && piece.getY() + jarak < this.baris) {
                        piece.moveY(jarak);
                        // Clear old position
                        for (int i = 0; i < piece.getUkuran(); i++) {
                            papan[piece.getY() - jarak + i][piece.getX()] = '.';
                        }
                        // Set new position
                        for (int i = 0; i < piece.getUkuran(); i++) {
                            papan[piece.getY() + i][piece.getX()] = piece.getHurufPiece();
                        }
                    }
                }
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Piece with character " + hurufPiece + " not found.");
        }
    }
    public boolean canPrimaryExit() {
        boolean canExit = true;
        if (primaryPiece.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
            int x = primaryPiece.getX();
            if (keluarX  == -1) {
                x--;
                while (x > -1) {
                    if (papan[primaryPiece.getY()][x] == '.') {
                        
                        x--;
                    } else {
                        canExit = false;
                        break;
                    }
                }
            } else {
                x = x + primaryPiece.getUkuran();
                while (x < this.kolom) {
                    if (papan[primaryPiece.getY()][x] == '.') {
                        x++;
                        
                    } else {
                        canExit = false;
                        break;
                    }
                }
            }
        } else {
            int y = primaryPiece.getY();
            if (keluarY == -1) {
                y--;
                while (y > -1) {
                    if (papan[y][primaryPiece.getX()] == '.') {
                        y--;
                    } else {
                        canExit = false;
                        break;
                    }
                }
            } else {
                y = y + primaryPiece.getUkuran();
                while (y < this.baris) {
                    if (papan[y][primaryPiece.getX()] == '.') {
                        y++;
                    } else {
                        canExit = false;
                        break;
                    }
                }
            }
        }
        return canExit;
    }
    public Piece getPrimaryPiece() {
        return primaryPiece;
    }
    public int movePieceToRightFarthest(char hurufPiece) {
        int moveDistance = 0;
        boolean found = false;
        for (Piece piece : listNonPiece) {
            if (piece.getHurufPiece() == hurufPiece) {
                int oldX = piece.getX();
                found = true;
                if (piece.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
                    int x = piece.getX() + piece.getUkuran();
                    while (x < this.kolom && papan[piece.getY()][x] == '.') {
                        x++;
                    }
                    piece.setX(x - piece.getUkuran());
                    moveDistance = piece.getX() - oldX;
                    // Clear old position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY()][piece.getX() + i] = '.';
                    }
                    // Set new position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY()][piece.getX() + i] = piece.getHurufPiece();
                    }
                } else {
                    throw new UnsupportedOperationException("Piece is not horizontal");
                }
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Piece with character " + hurufPiece + " not found.");
        }
        return moveDistance;
    }
    public int movePieceToLeftFarthest(char hurufPiece) {
        int moveDistance = 0;
        boolean found = false;
        for (Piece piece : listNonPiece) {
            if (piece.getHurufPiece() == hurufPiece) {
                int oldX = piece.getX();
                found = true;
                if (piece.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
                    int x = piece.getX() - 1;
                    while (x >= 0 && papan[piece.getY()][x] == '.') {
                        x--;
                    }
                    piece.setX(x + 1);
                    moveDistance = piece.getX() - oldX;
                    // Clear old position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY()][piece.getX() + i] = '.';
                    }
                    // Set new position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY()][piece.getX() + i] = piece.getHurufPiece();
                    }
                } else {
                    throw new UnsupportedOperationException("Piece is not horizontal");
                }
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Piece with character " + hurufPiece + " not found.");
        }
        return moveDistance;
    }
    public int movePieceToUpFarthest(char hurufPiece) {
        int moveDistance = 0;
        boolean found = false;
        for (Piece piece : listNonPiece) {
            if (piece.getHurufPiece() == hurufPiece) {
                int oldY = piece.getY();
                found = true;
                if (piece.getOrientasi() == Piece.Orientasi.VERTIKAL) {
                    int y = piece.getY() - 1;
                    while (y >= 0 && papan[y][piece.getX()] == '.') {
                        y--;
                    }
                    piece.setY(y + 1);
                    moveDistance = piece.getY() - oldY;
                    // Clear old position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY() + i][piece.getX()] = '.';
                    }
                    // Set new position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY() + i][piece.getX()] = piece.getHurufPiece();
                    }
                } else {
                    throw new UnsupportedOperationException("Piece is not vertical");
                }
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Piece with character " + hurufPiece + " not found.");
        }
        return moveDistance;
    }
    public int movePieceToDownFarthest(char hurufPiece) {
        int moveDistance = 0;
        boolean found = false;
        for (Piece piece : listNonPiece) {
            if (piece.getHurufPiece() == hurufPiece) {
                int oldY = piece.getY();
                found = true;
                if (piece.getOrientasi() == Piece.Orientasi.VERTIKAL) {
                    int y = piece.getY() + piece.getUkuran();
                    while (y < this.baris && papan[y][piece.getX()] == '.') {
                        y++;
                    }
                    piece.setY(y - piece.getUkuran());
                    moveDistance = piece.getY() - oldY;
                    // Clear old position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY() + i][piece.getX()] = '.';
                    }
                    // Set new position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY() + i][piece.getX()] = piece.getHurufPiece();
                    }
                } else {
                    throw new UnsupportedOperationException("Piece is not vertical");
                }
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Piece with character " + hurufPiece + " not found.");
        }
        return moveDistance;
    }
    public void movePieceRight(char hurufPiece, int jarak) {
        boolean found = false;
        for (Piece piece : listNonPiece) {
            if (piece.getHurufPiece() == hurufPiece) {
                found = true;
                if (piece.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
                    if (piece.getX() + jarak < 0 || piece.getX() + jarak >= this.kolom) {
                        throw new IllegalArgumentException("Piece cannot move outside the board");
                    }
                    piece.moveX(jarak);
                    // Clear old position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY()][piece.getX() - jarak + i] = '.';
                    }
                    // Set new position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY()][piece.getX() + i] = piece.getHurufPiece();
                    }
                } else {
                    throw new UnsupportedOperationException("Piece is not horizontal");
                }
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Piece with character " + hurufPiece + " not found.");
        }
    }
    public void movePieceLeft(char hurufPiece, int jarak) {
        boolean found = false;
        for (Piece piece : listNonPiece) {
            if (piece.getHurufPiece() == hurufPiece) {
                found = true;
                if (piece.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
                    if (piece.getX() + jarak < 0 || piece.getX() + jarak >= this.kolom) {
                        throw new IllegalArgumentException("Piece cannot move outside the board");
                    }
                    piece.moveX(-jarak);
                    // Clear old position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY()][piece.getX() + jarak + i] = '.';
                    }
                    // Set new position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY()][piece.getX() - i] = piece.getHurufPiece();
                    }
                } else {
                    throw new UnsupportedOperationException("Piece is not horizontal");
                }
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Piece with character " + hurufPiece + " not found.");
        }
    }
    public void movePieceUp(char hurufPiece, int jarak) {
        boolean found = false;
        for (Piece piece : listNonPiece) {
            if (piece.getHurufPiece() == hurufPiece) {
                found = true;
                if (piece.getOrientasi() == Piece.Orientasi.VERTIKAL) {
                    if (piece.getY() + jarak < 0 || piece.getY() + jarak >= this.baris) {
                        throw new IllegalArgumentException("Piece cannot move outside the board");
                    }
                    piece.moveY(-jarak);
                    // Clear old position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY() + jarak + i][piece.getX()] = '.';
                    }
                    // Set new position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY() - i][piece.getX()] = piece.getHurufPiece();
                    }
                } else {
                    throw new UnsupportedOperationException("Piece is not vertical");
                }
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Piece with character " + hurufPiece + " not found.");
        }
    }
    public void movePieceDown(char hurufPiece, int jarak) {
        boolean found = false;
        for (Piece piece : listNonPiece) {
            if (piece.getHurufPiece() == hurufPiece) {
                found = true;
                if (piece.getOrientasi() == Piece.Orientasi.VERTIKAL) {
                    if (piece.getY() + jarak < 0 || piece.getY() + jarak >= this.baris) {
                        throw new IllegalArgumentException("Piece cannot move outside the board");
                    }
                    piece.moveY(jarak);
                    // Clear old position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY() - jarak + i][piece.getX()] = '.';
                    }
                    // Set new position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY() + i][piece.getX()] = piece.getHurufPiece();
                    }
                } else {
                    throw new UnsupportedOperationException("Piece is not vertical");
                }
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Piece with character " + hurufPiece + " not found.");
        }
    }
    public String serializePapan() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                sb.append(papan[i][j]);
            }
        }
        return sb.toString();
    }
    public int countObstacleInFront(char hurufPiece) {
        int count = 0;
        char obs = '.';
        if (primaryPiece.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
            if (keluarX == -1) {
                for (int i = primaryPiece.getX() - 1; i >= 0; i--) {
                    if (papan[primaryPiece.getY()][i] != obs) {
                        count++;
                    }
                }
            } else {
                for (int i = primaryPiece.getX() + primaryPiece.getUkuran(); i < this.kolom; i++) {
                    if (papan[primaryPiece.getY()][i] != obs) {
                        count++;
                    }
                } 
            }
        } else {
            if (keluarY == -1) {
                for (int i = primaryPiece.getY() - 1; i >= 0; i--) {
                    if (papan[i][primaryPiece.getX()] != obs) {
                        count++;
                    }
                }
            } else {
                for (int i = primaryPiece.getY() + primaryPiece.getUkuran(); i < this.baris; i++) {
                    if (papan[i][primaryPiece.getX()] != obs) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
    
}