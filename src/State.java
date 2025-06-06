import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class State implements Comparable<State> {
    Map<Character, Piece> pieces; // Map id kendaraan ke objek Vehicle
    Papan papan;
    int cost;
    State parent; // untuk melacak jalur solusi
    String move;
    int heuristic; // untuk A* search
    int heuristicType;

    public State(Map<Character, Piece> pieces, Papan papan, int cost, State parent,  String move, int heuristicType) {
        this.pieces = pieces;
        this.papan = papan;
        this.cost = cost;
        this.parent = parent;
        this.move = move;
        this.heuristicType = heuristicType;
        switch (heuristicType) {
            case 0:
                this.heuristic = computeHeuristic();
                break;
            case 1:
                this.heuristic = computeExitDistanceHeuristic();
                break;
            case 2:
                int totalDepth = 0;
                for (Piece piece : papan.getListAllPiece()) {
                    char huruf = piece.getHurufPiece();
                    totalDepth += papan.getPieceBlockerDepth(huruf);
                }
                this.heuristic = totalDepth;
                break;
            default:
                throw new IllegalArgumentException("Invalid heuristic type: " + heuristicType);
        }
    }
    public int getCost() {
        return cost;
    }
    public Papan getPapan() {
        return papan;
    }
    public int getHeuristic() {
        return heuristic;
    }
    public String getMove() {
        return move;
    }
    public State getParent() {
        return parent;
    }
    public void setParent(State parent) {
        this.parent = parent;
    }
    private int computeHeuristic() {
        Piece xCar = pieces.get('P');
        if (xCar == null) return 0;

        int count = 0;

        if (xCar.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
            int row = xCar.getY();

            if (papan.getKeluarX() == -1) {
                // Exit di kiri
                int leftCol = xCar.getX();
                for (int c = leftCol - 1; c >= 0; c--) {
                    if (papan.getPapan()[row][c] != '.') count++;
                }
            } else {
                // Exit di kanan
                int rightEnd = xCar.getX() + xCar.getUkuran() - 1;
                for (int c = rightEnd + 1; c < papan.getPapan()[0].length; c++) {
                    if (papan.getPapan()[row][c] != '.') count++;
                }
            }

        } else {
            int col = xCar.getX();

            if (papan.getKeluarY() == -1) {
                // Exit di atas
                int topRow = xCar.getY();
                for (int r = topRow - 1; r >= 0; r--) {
                    if (papan.getPapan()[r][col] != '.') count++;
                }
            } else {
                // Exit di bawah
                int bottomEnd = xCar.getY() + xCar.getUkuran() - 1;
                for (int r = bottomEnd + 1; r < papan.getPapan().length; r++) {
                    if (papan.getPapan()[r][col] != '.') count++;
                }
            }
        }

        return count;
    }
    private int computeExitDistanceHeuristic() {
        Piece xCar = pieces.get('P');
        if (xCar == null) return 0;

        if (xCar.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
            int x = xCar.getX();
            int size = xCar.getUkuran();
            int exitX = papan.getKeluarX();

            // Exit di kiri
            if (exitX == -1) {
                return x;
            } else {
                return (exitX - (x + size - 1));
            }
        } else {
            int y = xCar.getY();
            int size = xCar.getUkuran();
            int exitY = papan.getKeluarY();

            // Exit di atas
            if (exitY == -1) {
                return y;
            } else {
                return (exitY - (y + size - 1));
            }
        }
    }





    // Membandingkan state berdasarkan cost (UCS)
    @Override
    public int compareTo(State other) {
        return Integer.compare(this.cost, other.cost);
    }

    // Cek apakah mobil merah sudah di exit
    public boolean isGoal() {
        Piece redCar = pieces.get('P');
        if (redCar == null) return false;

        if (redCar.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
            int leftCol = redCar.getX();
            int rightEndCol = redCar.getX() + redCar.getUkuran() - 1;
            int row = redCar.getY();

            if (papan.getKeluarX() == -1) {
                // Exit di kiri, cek ujung kiri mobil sudah di -1
                return papan.getKeluarY() == row && leftCol == papan.getKeluarX();
            } else {
                // Exit di kanan atau kolom lain
                return papan.getKeluarY() == row && rightEndCol == papan.getKeluarX();
            }
        } else {
            int topRow = redCar.getY();
            int bottomEndRow = redCar.getY() + redCar.getUkuran() - 1;
            int col = redCar.getX();

            if (papan.getKeluarY() == -1) {
                // Exit di atas, cek ujung atas mobil sudah di -1
                return papan.getKeluarX() == col && topRow == papan.getKeluarY();
            } else {
                // Exit di bawah atau baris lain
                return papan.getKeluarX() == col && bottomEndRow == papan.getKeluarY();
            }
        }
    }
    public List<State> getNextStates() {
    List<State> nextStates = new ArrayList<>();

    for (Piece p : pieces.values()) {
        // Untuk dua arah: maju (+1) dan mundur (-1)
        for (int direction : new int[]{1, -1}) {
            int steps = 1;
            while (true) {
                if (!canMove(p, direction, steps)) break;

                State newState = moveVehicle(p, direction * steps);
                if (newState != null) nextStates.add(newState);
                steps++;
            }
        }
    }

    return nextStates;
}


    private boolean canMove(Piece p, int direction, int steps) {
    if (p.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
        int row = p.getY();
        if (direction == -1) {
            for (int i = 1; i <= steps; i++) {
                int col = p.getX() - i;
                if (col < 0) {
                    // cek exit kiri
                    if (col == -1) {
                        if (i == steps) {
                            return papan.getKeluarY() == row && papan.getKeluarX() == -1;
                        } else return false;
                    }
                    return false;
                }
                if (papan.getPapan()[row][col] != '.') return false;
            }
        } else {
            int rightEnd = p.getX() + p.getUkuran() - 1;
            for (int i = 1; i <= steps; i++) {
                int col = rightEnd + i;
                if (col >= papan.getPapan()[0].length) {
                    if (col == papan.getPapan()[0].length && i == steps) {
                        return papan.getKeluarY() == row && papan.getKeluarX() == col;
                    } else return false;
                }
                if (col < papan.getPapan()[0].length && papan.getPapan()[row][col] != '.') return false;
            }
        }
    } else {
        int col = p.getX();
        if (direction == -1) {
            for (int i = 1; i <= steps; i++) {
                int row = p.getY() - i;
                if (row < 0) {
                    if (row == -1 && i == steps) {
                        return papan.getKeluarX() == col && papan.getKeluarY() == -1;
                    } else return false;
                }
                if (papan.getPapan()[row][col] != '.') return false;
            }
        } else {
            int bottomEnd = p.getY() + p.getUkuran() - 1;
            for (int i = 1; i <= steps; i++) {
                int row = bottomEnd + i;
                if (row >= papan.getPapan().length) {
                    if (row == papan.getPapan().length && i == steps) {
                        return papan.getKeluarX() == col && papan.getKeluarY() == row;
                    } else return false;
                }
                if (row < papan.getPapan().length && papan.getPapan()[row][col] != '.') return false;
            }
        }
    }
    return true;
}


        private State moveVehicle(Piece p, int move) {
            // Copy kendaraan dan map kendaraan
            Map<Character, Piece> newPiece = new HashMap<>();
            for (Map.Entry<Character, Piece> entry : pieces.entrySet()) {
                newPiece.put(entry.getKey(), entry.getValue().copy());
            }

            Piece movedPiece = newPiece.get(p.getHurufPiece());

            // Update posisi kendaraan yang digerakkan
            if (movedPiece.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
                int x = movedPiece.getX();
                movedPiece.setX(x + move);
            } else {
                int y = movedPiece.getY();
                movedPiece.setY(y + move);
            }

            // Buat board baru kosong
            char[][] newBoard = new char[papan.getPapan().length][papan.getPapan()[0].length];
            for (int i = 0; i < newBoard.length; i++) {
                Arrays.fill(newBoard[i], '.');
            }

            // Update posisi kendaraan ke board baru
            for (Piece pie : newPiece.values()) {
                int r = pie.getY();
                int c = pie.getX();
                for (int i = 0; i < pie.getUkuran(); i++) {
                    if (pie.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
                        int cc = c + i;
                        if (r >= 0 && r < newBoard.length && cc >= 0 && cc < newBoard[0].length) {
                            newBoard[r][cc] = pie.getHurufPiece();
                        }
                    } else {
                        int rr = r + i;
                        if (rr >= 0 && rr < newBoard.length && c >= 0 && c < newBoard[0].length) {
                            newBoard[rr][c] = pie.getHurufPiece();
                        }
                    }
                }
            }


            // Hitung cost baru (misal cost 1 per gerakan)
            int newCost = this.cost + 1;
            Papan newPapan = papan.copy();
            newPapan.setPapan(newBoard);
            if (movedPiece.getOrientasi() == Piece.Orientasi.HORIZONTAL) {
                return new State(newPiece, newPapan, newCost, this,"Gerak " + p.getHurufPiece() + " ke " + (move > 0 ? "kanan" : "kiri"),heuristicType);
            }
            return new State(newPiece, newPapan, newCost, this,"Gerak " + p.getHurufPiece() + " ke " + (move > 0 ? "bawah" : "atas"), heuristicType);
        }

    // Untuk menyimpan state di Set, kita butuh equals dan hashCode (berdasarkan posisi kendaraan)
    // @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State other = (State) o;

        if (pieces.size() != other.pieces.size()) return false;

        for (char id : pieces.keySet()) {
            Piece v1 = pieces.get(id);
            Piece v2 = other.pieces.get(id);
            if (v2 == null) return false;
            if (v1.getX() != v2.getX() || v1.getY() != v2.getY()) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        for (Piece v : pieces.values()) {
            result = 31 * result + v.getHurufPiece();
            result = 31 * result + v.getY();
            result = 31 * result + v.getX();
        }
        return result;
    }

    @Override
    public String toString() {
        int keluarX = this.getPapan().getKeluarX();
        int keluarY = this.getPapan().getKeluarY();

        char[][] papan = this.getPapan().getPapan();
        int rows = papan.length;
        int cols = papan[0].length;

        int minRow = Math.min(0, keluarY);
        int maxRow = Math.max(rows - 1, keluarY);
        int minCol = Math.min(0, keluarX);
        int maxCol = Math.max(cols - 1, keluarX);

        StringBuilder sb = new StringBuilder();

        for (int y = minRow; y <= maxRow; y++) {
            for (int x = minCol; x <= maxCol; x++) {
                if (x == keluarX && y == keluarY) {
                    sb.append("K");
                } else {
                    if (y >= 0 && y < rows && x >= 0 && x < cols) {
                        sb.append(papan[y][x]);
                    } else {
                        if ((x== keluarX && y != keluarY)||(y== keluarY && x != keluarX)) {
                            sb.append(" ");
                        } else {
                            sb.append(".");
                        }
                    }
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }


   public void saveSolutionToFile(int nodeCount, long executionTime) {
        List<State> path2 = new ArrayList<>();
        State current2 = this;
        while (current2 != null) {
            path2.add(current2);
            current2 = current2.getParent();
        }
        Collections.reverse(path2);

        System.err.println("Solusi ditemukan:");
        System.err.println("Initial state:");
        this.getPapan().printColoredPapan();
        System.err.println("\nLangkah-langkah:\n");


        for (State state : path2) {
            System.err.println("Move: " + state.getMove());
            char huruf = state.getMove().charAt(6);
            state.getPapan().printColoredPapan(huruf);
        }

        System.err.println("Visited nodes: " + nodeCount);
        System.err.println("Execution time: " + executionTime + " ms");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Masukkan nama file untuk menyimpan solusi (tambahkan ekstensi .txt):");
        String filename;
        while (true) {try {
            filename = reader.readLine();
            if (filename == null || filename.trim().isEmpty()) {
                System.err.println("Nama file tidak boleh kosong.");
            }
            else if (!filename.endsWith(".txt")) {
                System.err.println("Nama file harus diakhiri dengan .txt");
            } else break;
        } catch (IOException e) {
            throw new RuntimeException("Gagal membaca input nama file: " + e.getMessage(), e);
        }}

        List<State> path = new ArrayList<>();
        State current = this;
        while (current != null) {
            path.add(current);
            current = current.getParent();
        }
        Collections.reverse(path);

        try (PrintWriter writer = new PrintWriter(filename)) {
            for (State state : path) {
                writer.println("Move: " + state.getMove());
                writer.println(state.getPapan());
            }
            writer.println("Visited nodes: " + nodeCount);
            writer.println("Execution time: " + executionTime + " ms");
        } catch (IOException e) {
            throw new RuntimeException("Gagal menyimpan file: " + e.getMessage(), e);
        }
    }
    public void saveNoSolutionToFile(int nodeCount, long executionTime) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Masukkan nama file untuk menyimpan hasil (tambahkan ekstensi .txt):");
        String filename;
        while (true) {try {
            filename = reader.readLine();
            if (filename == null || filename.trim().isEmpty()) {
                System.err.println("Nama file tidak boleh kosong.");
            }
            else if (!filename.endsWith(".txt")) {
                System.err.println("Nama file harus diakhiri dengan .txt");
            } else break;
        } catch (IOException e) {
            throw new RuntimeException("Gagal membaca input nama file: " + e.getMessage(), e);
        }}

        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println("Tidak ada solusi ditemukan.");
            writer.println("Visited nodes: " + nodeCount);
            writer.println("Execution time: " + executionTime + " ms");
        } catch (IOException e) {
            throw new RuntimeException("Gagal menyimpan file: " + e.getMessage(), e);
        }
    }



    public void printSolution() {
        if (parent != null) {
            parent.printSolution();
        }
        System.out.println(move);
        papan.printPapan();
    }
    public List<State> getPath() {
        List<State> path = new ArrayList<>();
        State current = this;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }
}
