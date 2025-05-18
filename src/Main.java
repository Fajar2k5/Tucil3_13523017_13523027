
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.err.println("Masukkan nama file:");
        String fileName;
        try {
            fileName = reader.readLine();
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
            return;
        }
        Papan papan = new Papan();
        papan.readFromFile(fileName);
        Map<Character, Piece> pieces = papan.getMapPiece();
        pieces.put('P', papan.getPrimaryPiece());
        papan.printPapan();
        State initialState = new State(pieces, papan, 0, null, "State Awal");
        System.err.println("Pilih algoritma pencarian:");
        System.err.println("1. UCS");
        System.err.println("2. GBFS");
        System.err.println("3. A*");
        System.err.println("4. Beam Search");        
        int choice;
        try {
            choice = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
            return;
        }
        switch (choice) {
            case 1:
                System.err.println("Menggunakan Algoritma UCS");
                UCS.uniformCostSearch(initialState);
                break;
            case 2:
                System.err.println("Menggunakan Algoritma GBFS");
                break;
            case 3:
                System.err.println("Menggunakan Algoritma A*");
                AStarSearch.solve(initialState);
                break;
            case 4:
                System.err.println("Menggunakan Algoritma Beam Search");
                BeamSearch.solve(initialState);
                break;
            default:
                System.err.println("Pilihan tidak valid.");
                return;
        }
        
        
        
    }
}
