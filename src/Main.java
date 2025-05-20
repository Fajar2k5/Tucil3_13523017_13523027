import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.err.println("Masukkan nama file (atau ketik 'exit' untuk keluar):");
            String fileName;
            try {
                fileName = reader.readLine();
                if (fileName == null || fileName.trim().equalsIgnoreCase("exit")) {
                    System.err.println("Program dihentikan.");
                    break;
                }
            } catch (IOException e) {
                System.err.println("Error reading input: " + e.getMessage());
                return;
            }

            Papan papan = new Papan();
            try {
                papan.readFromFile(fileName);
            } catch (RuntimeException e) {
                System.err.println("Gagal membaca file atau data tidak valid: " + e.getMessage());
                continue;
            }

            Map<Character, Piece> pieces = papan.getMapPiece();
            pieces.put('P', papan.getPrimaryPiece());
            papan.printPapan();

            System.err.println("Pilih algoritma pencarian:");
            System.err.println("1. UCS");
            System.err.println("2. GBFS");
            System.err.println("3. A*");
            System.err.println("4. Beam Search");
            System.err.println("5. Keluar");

            int choice;
            try {
                String input = reader.readLine();
                if (input == null || input.trim().equals("5")) {
                    System.err.println("Program dihentikan.");
                    break;
                }
                choice = Integer.parseInt(input);
            } catch (IOException | NumberFormatException e) {
                System.err.println("Input pilihan tidak valid: " + e.getMessage());
                continue;
            }

            try {
                State initialState = new State(pieces, papan, 0, null, "State Awal");
                switch (choice) {
                    case 1:
                        System.err.println("Menggunakan Algoritma UCS");
                        UCS.uniformCostSearch(initialState);
                        break;
                    case 2:
                        System.err.println("Menggunakan Algoritma GBFS");
                        // GBFS.solve(initialState)
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
                }
            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat menjalankan algoritma: " + e.getMessage());
            }
        }
    }
}
