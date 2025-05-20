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
            try {papan.printPapan();} catch (Exception e) {
                System.err.println("Gagal, konfigurasi papan tidak valid");
                continue;
            }

            while (true) {System.err.println("Pilih algoritma pencarian:");
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
                    return;
                }
                choice = Integer.parseInt(input);
            } catch (IOException | NumberFormatException e) {
                System.err.println("Input pilihan tidak valid: " + e.getMessage());
                continue;
            }

            try {
                State initialState = new State(pieces, papan, 0, null, "State Awal",0);
                switch (choice) {
                    case 1:
                        System.err.println("Menggunakan Algoritma UCS");
                        UCS.uniformCostSearch(initialState);
                        break;
                    case 2:
                        BestFirstSearch gbfs = new BestFirstSearch(new Papan(papan));
                        System.err.println("Menggunakan Algoritma GBFS");
                        while (true) {
                            System.err.println("Pilih metode heuristik:");
                            System.err.println("1. Heuristik jumlah piece yang menghalangi primary piece");
                            System.err.println("2. Heuristik kemampuan piece untuk bergerak");
                            System.err.println("3. Heuristik tingkat terhalanginya sebuah piece (dihitung levelnya secara rekursif)");
                            System.err.println("4. Kembali ke menu utama");

                            int heuristicChoice;
                            try {
                                String input = reader.readLine();
                                if (input == null || input.trim().equals("4")) {
                                    break;
                                }
                                heuristicChoice = Integer.parseInt(input);
                            } catch (IOException | NumberFormatException e) {
                                System.err.println("Input pilihan tidak valid: " + e.getMessage());
                                continue;
                            }

                            boolean chosen = false;

                            switch (heuristicChoice) {
                                case 1:
                                    gbfs.setHeuristic(0);
                                    chosen = true;
                                    break;
                                case 2:
                                    gbfs.setHeuristic(1);
                                    chosen = true;
                                    break;
                                case 3:
                                    gbfs.setHeuristic(2);
                                    chosen = true;
                                    break;
                                default:
                                    System.err.println("Pilihan tidak valid.");
                                    continue;
                            }
                            if (chosen) {
                                break;
                            }
                        }
                        gbfs.search();
                        if (gbfs.isFound()) {
                            System.err.println("Solusi ditemukan dengan GBFS.");
                            gbfs.saveSolutionToFile();
                        } else {
                            System.err.println("Tidak ada solusi ditemukan dengan GBFS.");
                            gbfs.saveNoSolutionToFile();
                        }
                        // GBFS.solve(initialState)
                        break;
                    case 3:
                        System.err.println("Menggunakan Algoritma A*");
                        while (true) {
                            System.err.println("Pilih metode heuristik:");
                            System.err.println("1. Heuristik jumlah piece yang menghalangi primary piece");
                            System.err.println("2. Heuristik kemampuan piece untuk bergerak");
                            System.err.println("3. Heuristik tingkat terhalanginya sebuah piece (dihitung levelnya secara rekursif)");
                            System.err.println("4. Kembali ke menu utama");

                            int heuristicChoice;
                            try {
                                String input = reader.readLine();
                                if (input == null || input.trim().equals("4")) {
                                    break;
                                }
                                heuristicChoice = Integer.parseInt(input);
                            } catch (IOException | NumberFormatException e) {
                                System.err.println("Input pilihan tidak valid: " + e.getMessage());
                                continue;
                            }

                            boolean chosen = false;

                            switch (heuristicChoice) {
                                case 1:
                                    initialState = new State(pieces, papan, 0, null, "State Awal",0);
                                    chosen = true;
                                    break;
                                case 2:
                                    initialState = new State(pieces, papan, 0, null, "State Awal",1);
                                    chosen = true;
                                    break;
                                case 3:
                                    initialState = new State(pieces, papan, 0, null, "State Awal",2);
                                    chosen = true;
                                    break;
                                default:
                                    System.err.println("Pilihan tidak valid.");
                                    continue;
                            }
                            if (chosen) {
                                break;
                            }
                        }
                        AStarSearch.solve(initialState);
                        break;
                    case 4:
                        System.err.println("Menggunakan Algoritma Beam Search");
                        while (true) {
                            System.err.println("Pilih metode heuristik:");
                            System.err.println("1. Heuristik jumlah piece yang menghalangi primary piece");
                            System.err.println("2. Heuristik kemampuan piece untuk bergerak");
                            System.err.println("3. Heuristik tingkat terhalanginya sebuah piece (dihitung levelnya secara rekursif)");
                            System.err.println("4. Kembali ke menu utama");

                            int heuristicChoice;
                            try {
                                String input = reader.readLine();
                                if (input == null || input.trim().equals("4")) {
                                    break;
                                }
                                heuristicChoice = Integer.parseInt(input);
                            } catch (IOException | NumberFormatException e) {
                                System.err.println("Input pilihan tidak valid: " + e.getMessage());
                                continue;
                            }

                            boolean chosen = false;

                            switch (heuristicChoice) {
                                case 1:
                                    initialState = new State(pieces, papan, 0, null, "State Awal",0);
                                    chosen = true;
                                    break;
                                case 2:
                                    initialState = new State(pieces, papan, 0, null, "State Awal",1);
                                    chosen = true;
                                    break;
                                case 3:
                                    initialState = new State(pieces, papan, 0, null, "State Awal",2);
                                    chosen = true;
                                    break;
                                default:
                                    System.err.println("Pilihan tidak valid.");
                                    continue;
                            }
                            if (chosen) {
                                break;
                            }
                        }
                        BeamSearch.solve(initialState);
                        break;
                    default:
                        System.err.println("Pilihan tidak valid.");
                }
            } catch (Exception e) {
                System.err.println("Terjadi kesalahan saat menjalankan algoritma: " + e.getMessage());
            }}
        }
    }
}
