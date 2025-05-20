import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
!!!!TODO
Kasih validasi kalau format penulisan di file salah
 */

public class Papan {
    private char[][] papan;
    private int baris;
    private int kolom;
    private int jumlahAll;
    private char charUtama;
    private int keluarX;
    private int keluarY;
    private List<Piece> listAllPiece = new ArrayList<>();

    public Papan(int baris, int kolom) {
        this.baris = baris;
        this.kolom = kolom;
        this.charUtama = 'P';
        this.papan = new char[baris][kolom];
        this.jumlahAll = 0;

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
        this.jumlahAll = papan.jumlahAll;
        this.papan = new char[baris][kolom];
        for (int i = 0; i < baris; i++) {
            System.arraycopy(papan.papan[i], 0, this.papan[i], 0, kolom);
        }
        this.keluarX = papan.keluarX;
        this.keluarY = papan.keluarY;
        this.listAllPiece = new ArrayList<>();
        for (Piece piece : papan.listAllPiece) {
            this.listAllPiece.add(piece.copy());
        }
    }

    public Papan() {
        this.baris = 0;
        this.kolom = 0;
        this.charUtama = 'P';
        this.papan = new char[baris][kolom];
        this.jumlahAll = 0;
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
    public void setJumlahAll(int jumlahAll) {
        this.jumlahAll = jumlahAll;
    }
    public int getJumlahAll() {
        return this.jumlahAll;
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
        try {if (keluarY == -1){
            for (int i = 0; i < kolom; i++) {
                if (i != keluarX) {
                    System.out.print("  ");
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
        System.out.println();}
        catch (Exception e) {
            throw new RuntimeException("Papan tidak valid");
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
            int KCount = 0;
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
                    tempPapan[x][y] = '~';
                }
            }
            // Read the next one line to read into jumlahAll attribute
            line = br.readLine();
            if (line != null) {
                this.jumlahAll = Integer.parseInt(line);
            }
            // Read the rest of the lines to fill the board
            while ((line = br.readLine()) != null && i < baris + 1) {
                for (int j = 0; j < kolom + 1; j++) {
                    if (line.length() > this.kolom + 1) {
                        throw new RuntimeException("File tidak sesuai format");
                    }
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
                            if (j == this.kolom && i == this.baris) {
                                throw new RuntimeException("File tidak sesuai format");
                            }
                            if (j == 0 && i == 0) {
                                if (tempPapan[1][0] == ' ') throw new RuntimeException("File tidak sesuai format");
                            }
                            if (keluarX != -1 && keluarY != -1 && keluarX != this.kolom && keluarY != this.baris) throw new RuntimeException("File tidak sesuai format");
                        } else if (line.charAt(j) != ' ' && line.charAt(j) != '.' && (line.charAt(j) < 'A' || line.charAt(j) > 'Z')) throw new RuntimeException("File tidak sesuai format");
                    }
                }
                i++;
            }
            if (line == null && i < baris) {
                throw new RuntimeException("File tidak sesuai format");
            }
            List<Character> items = new ArrayList<>();
            char firstchara = tempPapan[0][0];
            if (keluarY != -1 && keluarX != -1 && firstchara==' ') throw new RuntimeException("File tidak sesuai format");
            for (int k = 0; k < baris+1; k++) {
                if (keluarX == -1 && tempPapan[k][0] != 'K' && tempPapan[k][0] != ' ' && tempPapan[k][0] != '~') throw new RuntimeException("File tidak sesuai format");
                else if (keluarX == this.kolom && tempPapan[k][0] == ' ') throw new RuntimeException("File tidak sesuai format");
                for (int j = 0; j < tempPapan[k].length; j++) {
                    char c = tempPapan[k][j];
                    
                    if (c != ' ' && c != 'K' && c != '~') {
                        items.add(c);
                    } else if (c == 'K') KCount++;
                }
            }
            
            if (items.size() != this.baris * this.kolom || KCount != 1) {
                throw new RuntimeException("Format file tidak sesuai");
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
            
            Map<Character, Boolean> charmap = new HashMap<>();
            boolean[][] isChecked = new boolean[baris][kolom];
            for (int k = 0; k < baris; k++) {
                for (int j = 0; j < kolom; j++) {
                    if (!isChecked[k][j]) {
                        isChecked[k][j] = true;
                        char c = papan[k][j];
                        // if it already exist in  the map, error
                        if (charmap.containsKey(c)) {
                            throw new RuntimeException("File tidak sesuai format");
                        }
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
                                // this.primaryPiece = piece;
                            }
                            listAllPiece.add(piece);
                            charmap.put(c, true);
                        } else if (k + 1 < baris && papan[k+1][j] == c) {
                            int count = 1;
                            while (k + count < baris && papan[k+count][j] == c) {
                                isChecked[k+count][j] = true;
                                count++;
                            }
                            Piece piece = new Piece(c, count, Piece.Orientasi.VERTIKAL, j, k);
                            if (c == charUtama) {
                                piece.setPrimary(true);
                                // this.primaryPiece = piece;
                            }
                            listAllPiece.add(piece);
                            charmap.put(c, true);
                        }
                        
                    }
                }
            }
        } catch (IOException e) {
            // e.printStackTrace();
            throw new RuntimeException("File tidak ditemukan");
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
        newPapan.setJumlahAll(this.jumlahAll);
        newPapan.setKeluarX(this.keluarX);
        newPapan.setKeluarY(this.keluarY);
        
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
        listAllPiece.add(Piece);
    }
    public List<Piece> getListAllPiece() {
        return listAllPiece;
    }
    public void setListAllPiece(List<Piece> listAllPiece) {
        this.listAllPiece = listAllPiece;
    }
    public void clearListAllPiece() {
        listAllPiece.clear();
    }
    
    public void removePiece(int index) {
        if (index >= 0 && index < listAllPiece.size()) {
            listAllPiece.remove(index);
        }
    }
    public void removePiece(char hurufPiece) {
        listAllPiece.removeIf(Piece -> Piece.getHurufPiece() == hurufPiece);
    }
    public void printAllPiece() {
        for (Piece piece : listAllPiece) {
            piece.printPiece();
        }
    }
    public void movePiece(char hurufPiece, int jarak) {
        boolean found = false;
        for (Piece piece : getListAllPiece()) {
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
        for (Piece piece : getListAllPiece()) {        
            if (piece.isPrimary()) {
                if (piece.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
                    int x = piece.getX();
                    if (keluarX  == -1) {
                        x--;
                        while (x > -1) {
                            if (papan[piece.getY()][x] == '.') {
                                
                                x--;
                            } else {
                                canExit = false;
                                break;
                            }
                        }
                    } else {
                        x = x + piece.getUkuran();
                        while (x < this.kolom) {
                            if (papan[piece.getY()][x] == '.') {
                                x++;
                                
                            } else {
                                canExit = false;
                                break;
                            }
                        }
                    }
                } else {
                    int y = piece.getY();
                    if (keluarY == -1) {
                        y--;
                        while (y > -1) {
                            if (papan[y][piece.getX()] == '.') {
                                y--;
                            } else {
                                canExit = false;
                                break;
                            }
                        }
                    } else {
                        y = y + piece.getUkuran();
                        while (y < this.baris) {
                            if (papan[y][piece.getX()] == '.') {
                                y++;
                            } else {
                                canExit = false;
                                break;
                            }
                        }
                    }
                }
            break;

            }
        }
        return canExit;
    }
    public Piece getPrimaryPiece() {
        for (Piece piece : getListAllPiece()) {
            if (piece.isPrimary()) {
                return piece;
            }
        }
        return null;
    }
    public int movePieceToRightFarthest(char hurufPiece) {
        int moveDistance = 0;
        boolean found = false;
        for (Piece piece : getListAllPiece()) {
            if (piece.getHurufPiece() == hurufPiece) {
                int oldX = piece.getX();
                found = true;
                int x = piece.getX() + piece.getUkuran();
                if (piece.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
                    
                    if (x < this.kolom && papan[piece.getY()][x] == '.') {
                        while (x < this.kolom && papan[piece.getY()][x] == '.') {
                            x++;
                        }
                        piece.setX(x - piece.getUkuran());
                        moveDistance = piece.getX() - oldX;
                    }
                    
                    // Clear old position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY()][oldX + i] = '.';
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
        for (Piece piece : getListAllPiece()) {
            if (piece.getHurufPiece() == hurufPiece) {
                int oldX = piece.getX();
                found = true;
                if (piece.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
                    int x = piece.getX() - 1;
                    if (x >= 0 && papan[piece.getY()][x] == '.') {
                        while (x >= 0 && papan[piece.getY()][x] == '.') {
                            x--;
                        }
                        piece.setX(x + 1);
                        moveDistance = oldX - piece.getX();
                    }
                    
                    // Clear old position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY()][oldX + i] = '.';
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
        for (Piece piece : getListAllPiece()) {
            if (piece.getHurufPiece() == hurufPiece) {
                int oldY = piece.getY();
                found = true;
                if (piece.getOrientasi() == Piece.Orientasi.VERTIKAL) {
                    int y = piece.getY() - 1;
                    if (y >= 0 && papan[y][piece.getX()] == '.') {
                        while (y >= 0 && papan[y][piece.getX()] == '.') {
                            y--;
                        }
                        piece.setY(y + 1);
                        moveDistance = oldY - piece.getY();
                    }
                    
                    // Clear old position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[oldY + i][piece.getX()] = '.';
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
        for (Piece piece : getListAllPiece()) {
            if (piece.getHurufPiece() == hurufPiece) {
                int oldY = piece.getY();
                found = true;
                if (piece.getOrientasi() == Piece.Orientasi.VERTIKAL) {
                    int y = piece.getY() + piece.getUkuran();
                    if (y < this.baris && papan[y][piece.getX()] == '.') {
                        while (y < this.baris && papan[y][piece.getX()] == '.') {
                            y++;
                        }
                        piece.setY(y - piece.getUkuran());
                        moveDistance = piece.getY() - oldY;
                    }
                    
                    // Clear old position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[oldY + i][piece.getX()] = '.';
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
    public int movePieceRight(char hurufPiece, int jarak) {
        boolean found = false;
        for (Piece piece : getListAllPiece()) {
            if (piece.getHurufPiece() == hurufPiece) {
                found = true;
                if (piece.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
                    for (int i = 0; i < jarak; i++) {
                        if (piece.getX() + piece.getUkuran() + i >= this.kolom || papan[piece.getY()][piece.getX() + piece.getUkuran() + i] != '.') {
                            return 4;
                        }
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
                    return 0;
                } else {
                    return 1;
                }
            }
        }
        if (!found) {
            return 2;
        }
        return -1;
    }
    public int movePieceLeft(char hurufPiece, int jarak) {
        boolean found = false;
        for (Piece piece : getListAllPiece()) {
            if (piece.getHurufPiece() == hurufPiece) {
                found = true;
                if (piece.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
                    for (int i = 1; i < jarak + 1; i++) {
                        if (piece.getX() - i < 0 || papan[piece.getY()][piece.getX() - i] != '.') {
                            return 4;
                        }
                    }
                    piece.moveX(-jarak);
                    // Clear old position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY()][piece.getX() + jarak + i] = '.';
                    }
                    // Set new position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY()][piece.getX() + i] = piece.getHurufPiece();
                    }
                    return 0;
                } else {
                    return 1;
                }
            }
        }
        if (!found) {
            return 2;
        }
        return -1;
    }
    public int movePieceUp(char hurufPiece, int jarak) {
        boolean found = false;
        for (Piece piece : getListAllPiece()) {
            if (piece.getHurufPiece() == hurufPiece) {
                found = true;
                if (piece.getOrientasi() == Piece.Orientasi.VERTIKAL) {
                    for (int i = 1; i < jarak + 1; i++) {
                        if (piece.getY() - i < 0 || papan[piece.getY() - i][piece.getX()] != '.') {
                            return 4;
                        }
                    }
                    piece.moveY(-jarak);
                    // Clear old position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY() + jarak + i][piece.getX()] = '.';
                    }
                    // Set new position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY() + i][piece.getX()] = piece.getHurufPiece();
                    }
                    return 0;
                } else {
                    return 1;
                }
            }
        }
        if (!found) {
            return 2;
        }
        return -1;
    }
    public int movePieceDown(char hurufPiece, int jarak) {
        boolean found = false;
        for (Piece piece : getListAllPiece()) {
            if (piece.getHurufPiece() == hurufPiece) {
                int oldY = piece.getY();
                found = true;
                if (piece.getOrientasi() == Piece.Orientasi.VERTIKAL) {
                    for (int i = 0; i < jarak; i++) {
                        if (piece.getY() + i + piece.getUkuran() >= this.baris || papan[piece.getY() + piece.getUkuran() + i][piece.getX()] != '.') {
                            return 4;
                        }
                    }
                    piece.moveY(jarak);
                    // Clear old position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[oldY + i][piece.getX()] = '.';
                    }
                    // Set new position
                    for (int i = 0; i < piece.getUkuran(); i++) {
                        papan[piece.getY() + i][piece.getX()] = piece.getHurufPiece();
                    }
                    return 0;
                } else {
                    return 1;
                }
            }
        }
        if (!found) {
            return 2;
        }
        return -1;
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
    public int countObstacleInFront() {
        int count = 0;
        char obs = '.';

        for (Piece piece : listAllPiece) {
            if (piece.getHurufPiece() == charUtama) {
                if (piece.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
                    if (keluarX == -1) {
                        for (int i = piece.getX() - 1; i >= 0; i--) {
                            if (papan[piece.getY()][i] != obs) {
                                count++;
                            }
                        }
                    } else {
                        for (int i = piece.getX() + piece.getUkuran(); i < this.kolom; i++) {
                            if (papan[piece.getY()][i] != obs) {
                                count++;
                            }
                        } 
                    }
                } else {
                    if (keluarY == -1) {
                        for (int i = piece.getY() - 1; i >= 0; i--) {
                            if (papan[i][piece.getX()] != obs) {
                                count++;
                            }
                        }
                    } else {
                        for (int i = piece.getY() + piece.getUkuran(); i < this.baris; i++) {
                            if (papan[i][piece.getX()] != obs) {
                                count++;
                            }
                        }
                    }
                }
                break;
            }
        }
        return count;
    }
    public Map<Character,Piece> getMapPiece() {
        Map<Character, Piece> pieces = new HashMap<>();
        for (Piece piece : listAllPiece) {
            pieces.put(piece.getHurufPiece(), piece);
        }
        // pieces.put('P', primaryPiece);
        return pieces;
    }

    public void applyMovement(Movement movement) {
        char hurufPiece = movement.gethuruf();
        int jarak = movement.getJarak();
        Movement.Direction arah = movement.getArah();
        if (arah.equals(Movement.Direction.RIGHT)) {
            movePieceRight(hurufPiece, jarak);
        } else if (arah.equals(Movement.Direction.LEFT)) {
            movePieceLeft(hurufPiece, jarak);
        } else if (arah.equals(Movement.Direction.UP)) {
            movePieceUp(hurufPiece, jarak);
        } else if (arah.equals(Movement.Direction.DOWN)) {
            movePieceDown(hurufPiece, jarak);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (keluarY == -1) {
            for (int i = 0; i < kolom; i++) {
                if (i != keluarX) {
                    sb.append("  ");
                } else {
                    sb.append("K");
                }
            }
            sb.append("\n");
            for (int i = 0; i < baris; i++) {
                for (int j = 0; j < kolom; j++) {
                    sb.append(papan[i][j]).append(" ");
                }
                sb.append("\n");
            }
        } else if (keluarX == -1) {
            for (int i = 0; i < baris; i++) {
                if (i != keluarY) {
                    sb.append("  ");
                    for (int j = 0; j < kolom; j++) {
                        sb.append(papan[i][j]).append(" ");
                    }
                } else {
                    sb.append("K ");
                    for (int j = 0; j < kolom; j++) {
                        sb.append(papan[i][j]).append(" ");
                    }
                }
                sb.append("\n");
            }
        } else if (keluarX == this.kolom) {
            for (int i = 0; i < baris; i++) {
                for (int j = 0; j < kolom; j++) {
                    sb.append(papan[i][j]).append(" ");
                }
                if (i == keluarY) {
                    sb.append("K");
                }
                sb.append("\n");
            }
        } else {
            for (int i = 0; i < baris + 1; i++) {
                if (i == keluarY) {
                    for (int j = 0; j < kolom; j++) {
                        if (j == keluarX) {
                            sb.append("K ");
                        } else {
                            sb.append("  ");
                        }
                    }
                    sb.append("\n");
                } else {
                    for (int j = 0; j < kolom; j++) {
                        sb.append(papan[i][j]).append(" ");
                    }
                    sb.append("\n");
                }
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    public int getPieceBlockerDepth(char hurufPiece) {
        Map<Character, Boolean> visited = new HashMap<>();
        return countPieceBlockersDepth(hurufPiece, visited);
    }

    private int countPieceBlockersDepth(char hurufPiece, Map<Character, Boolean> visited) {
        int count = 0;
        for (Piece piece : listAllPiece) {
            if (piece.getHurufPiece() == hurufPiece) {
                if (piece.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
                    if (!visited.getOrDefault(hurufPiece, false)) {
                        visited.put(hurufPiece, true);
                        if (piece.getX() - 1 >= 0 && papan[piece.getY()][piece.getX() - 1] != '.') {
                            count++;
                            if (!visited.getOrDefault(papan[piece.getY()][piece.getX() - 1], false)) count += countPieceBlockersDepth(papan[piece.getY()][piece.getX() - 1], visited);
                        } else if (piece.getX() - 1 < 0) {
                            count++;
                        }
                        if (piece.getX() + piece.getUkuran() < this.kolom && papan[piece.getY()][piece.getX() + piece.getUkuran()] != '.') {
                            count++;
                            if (!visited.getOrDefault(papan[piece.getY()][piece.getX() + piece.getUkuran()], false)) count += countPieceBlockersDepth(papan[piece.getY()][piece.getX() + piece.getUkuran()], visited);
                        } else if (piece.getX() + piece.getUkuran() >= this.kolom) {
                            count++;
                        }
                    }
                } else {
                    if (!visited.getOrDefault(hurufPiece, false)) {
                        visited.put(hurufPiece, true);
                        if (piece.getY() - 1 >= 0 && papan[piece.getY() - 1][piece.getX()] != '.') {
                            count++;
                            if (!visited.getOrDefault(papan[piece.getY() - 1][piece.getX()], false)) count += countPieceBlockersDepth(papan[piece.getY() - 1][piece.getX()], visited);
                        } else if (piece.getY() - 1 < 0) {
                            count++;
                        }
                        if (piece.getY() + piece.getUkuran() < this.baris && papan[piece.getY() + piece.getUkuran()][piece.getX()] != '.') {
                            count++;
                            if (!visited.getOrDefault(papan[piece.getY() + piece.getUkuran()][piece.getX()], false)) count += countPieceBlockersDepth(papan[piece.getY() + piece.getUkuran()][piece.getX()], visited);
                        } else if (piece.getY() + piece.getUkuran() >= this.baris) {
                            count++;
                        }
                    }
                }
                break;
            }
        }
        return count;
    }
}