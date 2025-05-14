import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Papan {
    private char[][] papan;
    private int baris;
    private int kolom;
    private int jumlahNon;
    private char charUtama;
    private int keluarX;
    private int keluarY;

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
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                System.out.print(papan[i][j] + " ");
            }
            System.out.println();
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
                this.kolom = Integer.parseInt(dimensions[1]);
                this.papan = new char[baris][kolom];
            }
            // Read the next one line to read into jumlahNon attribute
            line = br.readLine();
            if (line != null) {
                this.jumlahNon = Integer.parseInt(line);
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
}
