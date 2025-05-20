import java.util.PriorityQueue;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class BestFirstSearch {
    private Papan papan;
    private PriorityQueue<Node> nodeQueue;
    private boolean isFinished;
    private boolean isFound;
    private List<Movement> resultMovement;
    private Papan papanAkhir;
    private long executionTime = -1;
    private int nodeCount = -1;

    public BestFirstSearch(Papan papan) {
        this.papan = papan;
        this.nodeQueue = new PriorityQueue<>();
        this.isFinished = false;
        this.isFound = false;
        this.resultMovement = new ArrayList<>();
    }

    public boolean isFinished() {
        return isFinished;
    }
    public boolean isFound() {
        return isFound;
    }
    public List<Movement> getResultMovement() {
        return resultMovement;
    }

    public Papan getPapanAkhir() {
        return papanAkhir;
    }

    public void search() {
        nodeCount = 1;
        nodeQueue.clear();
        resultMovement.clear();
        // get current milliseconds
        long startTime = System.currentTimeMillis();
        if (papan.canPrimaryExit()) {
            isFound = true;
            isFinished = true;
            return;
        }
        Map<String, Boolean> visitedMap = new HashMap<>();
        visitedMap.put(papan.serializePapan(), true);

        for (Piece piece : papan.getListAllPiece()) {
            char huruf = piece.getHurufPiece();
            // System.out.println("Piece " + huruf + " is being moved");
            if (piece.getOrientasi()==Piece.Orientasi.HORIZONTAL) {
                int i = 1;
                while (true) {
                    
                    Papan papan2 = new Papan(papan);
                    int res = papan2.movePieceLeft(huruf, i);
                    if (res != 0) {
                        break;
                    }
                    Movement movement = new Movement(huruf, Movement.Direction.LEFT, i++);
                    Node node = new Node(papan2.countObstacleInFront(), new Papan(papan2), movement);
                    if (visitedMap.containsKey(papan2.serializePapan()) == false) {
                        nodeCount++;
                        nodeQueue.add(node);
                        node.getPapan().printPapan();
                        if (papan2.canPrimaryExit()) {
                            isFound = true;
                            isFinished = true;
                            resultMovement = node.getListMovement();
                            papanAkhir = papan2;
                            long endTime = System.currentTimeMillis();
                            executionTime = endTime - startTime;
                            return;
                        }
                    }
                } 
                i = 1;
                while (true) {

                    Papan papan2 = new Papan(papan);
                    int res  = papan2.movePieceRight(huruf, i);
                    if (res != 0) {
                        break;
                    }
                    Movement movement = new Movement(huruf, Movement.Direction.RIGHT, i++);
                    Node node = new Node(papan2.countObstacleInFront(), new Papan(papan2), movement);
                    if (visitedMap.containsKey(papan2.serializePapan()) == false) {
                        nodeCount++;
                        nodeQueue.add(node);
                        node.getPapan().printPapan();
                        if (papan2.canPrimaryExit()) {
                            isFound = true;
                            isFinished = true;
                            resultMovement = node.getListMovement();
                            papanAkhir = papan2;
                            long endTime = System.currentTimeMillis();
                            executionTime = endTime - startTime;
                            return;
                        }
                    }

                } 
            } else {
                int i = 1;
                while (true) {
            
                    Papan papan2 = new Papan(papan);
                    int res = papan2.movePieceUp(huruf, i);
                    if (res != 0) {
                        break;
                    }
                    Movement movement = new Movement(huruf, Movement.Direction.UP, i++);
                    Node node = new Node(papan2.countObstacleInFront(), new Papan(papan2), movement);
                    if (visitedMap.containsKey(papan2.serializePapan()) == false) {
                        nodeCount++;
                        nodeQueue.add(node);
                        node.getPapan().printPapan();
                        if (papan2.canPrimaryExit()) {
                            isFound = true;
                            isFinished = true;
                            resultMovement = node.getListMovement();
                            papanAkhir = papan2;
                            long endTime = System.currentTimeMillis();
                            executionTime = endTime - startTime;
                            return;
                        }
                    }
                }
                i = 1;
                while (true) {
                    Papan papan2 = new Papan(papan);
                    int res = papan2.movePieceDown(huruf, i);
                    if (res != 0) {
                        break;
                    }
                    Movement movement = new Movement(huruf, Movement.Direction.DOWN, i++);
                    Node node = new Node(papan2.countObstacleInFront(), new Papan(papan2), movement);
                    if (visitedMap.containsKey(papan2.serializePapan()) == false) {
                        nodeCount++;
                        nodeQueue.add(node);
                        node.getPapan().printPapan();
                        if (papan2.canPrimaryExit()) {
                            isFound = true;
                            isFinished = true;
                            resultMovement = node.getListMovement();
                            papanAkhir = papan2;
                            long endTime = System.currentTimeMillis();
                            executionTime = endTime - startTime;
                            return;
                        }
                    }

                } 
            }
        }
        while (true) {
            
            if (nodeQueue.isEmpty()) {
                isFinished = true;
                break;
            }
            // System.out.println("Queue size: " + nodeQueue.size());
            Node currNode = nodeQueue.poll();
            currNode.getPapan().printPapan();
            String papanKey = currNode.getPapan().serializePapan();
            if (visitedMap.containsKey(papanKey)) {
                continue;
            }
            visitedMap.put(currNode.getPapan().serializePapan(), true);
            if (currNode.getPapan().canPrimaryExit()) {
                isFound = true;
                isFinished = true;
                resultMovement = currNode.getListMovement();
                break;
            }
            for (Piece piece : currNode.getPapan().getListAllPiece()) {
                char huruf = piece.getHurufPiece();
            // System.out.println("Piece " + huruf + " is being moved");
            if (piece.getOrientasi()==Piece.Orientasi.HORIZONTAL) {
                int i = 1;
                while (true) {
                    
                    Papan papan2 = new Papan(currNode.getPapan());
                    int res = papan2.movePieceLeft(huruf, i);
                    if (res != 0) {
                        break;
                    }
                    Movement movement = new Movement(huruf, Movement.Direction.LEFT, i++);
                    Node node = new Node(papan2.countObstacleInFront(), new Papan(papan2), currNode.getListMovement(), movement);
                    if ((visitedMap.containsKey(papan2.serializePapan()) == false) && (currNode.getListMovement().getLast().isSameLetter(movement) == false)) {
                        nodeCount++;
                        nodeQueue.add(node);
                        node.getPapan().printPapan();
                        if (papan2.canPrimaryExit()) {
                            isFound = true;
                            isFinished = true;
                            resultMovement = node.getListMovement();
                            papanAkhir = papan2;
                            long endTime = System.currentTimeMillis();
                            executionTime = endTime - startTime;
                            return;
                        }
                    }
                } 
                i = 1;
                while (true) {

                    Papan papan2 = new Papan(currNode.getPapan());
                    int res  = papan2.movePieceRight(huruf, i);
                    if (res != 0) {
                        break;
                    }
                    Movement movement = new Movement(huruf, Movement.Direction.RIGHT, i++);
                    Node node = new Node(papan2.countObstacleInFront(), new Papan(papan2), currNode.getListMovement(), movement);
                    if ((visitedMap.containsKey(papan2.serializePapan()) == false) && (currNode.getListMovement().getLast().isSameLetter(movement) == false)) {
                        nodeCount++;
                        nodeQueue.add(node);
                        node.getPapan().printPapan();
                        if (papan2.canPrimaryExit()) {
                            isFound = true;
                            isFinished = true;
                            resultMovement = node.getListMovement();
                            papanAkhir = papan2;
                            long endTime = System.currentTimeMillis();
                            executionTime = endTime - startTime;
                            return;
                        }
                    }

                } 
            } else {
                int i = 1;
                while (true) {
            
                    Papan papan2 = new Papan(currNode.getPapan());
                    int res = papan2.movePieceUp(huruf, i);
                    if (res != 0) {
                        break;
                    }
                    Movement movement = new Movement(huruf, Movement.Direction.UP, i++);
                    Node node = new Node(papan2.countObstacleInFront(), new Papan(papan2), currNode.getListMovement(), movement);
                    if ((visitedMap.containsKey(papan2.serializePapan()) == false) && (currNode.getListMovement().getLast().isSameLetter(movement) == false)) {
                        nodeCount++;
                        nodeQueue.add(node);
                        node.getPapan().printPapan();
                        if (papan2.canPrimaryExit()) {
                            isFound = true;
                            isFinished = true;
                            resultMovement = node.getListMovement();
                            papanAkhir = papan2;
                            long endTime = System.currentTimeMillis();
                            executionTime = endTime - startTime;
                            return;
                        }
                    }
                }
                i = 1;
                while (true) {
                    Papan papan2 = new Papan(currNode.getPapan());
                    int res = papan2.movePieceDown(huruf, i);
                    if (res != 0) {
                        break;
                    }
                    Movement movement = new Movement(huruf, Movement.Direction.DOWN, i++);
                    Node node = new Node(papan2.countObstacleInFront(), new Papan(papan2), currNode.getListMovement(), movement);
                    if ((visitedMap.containsKey(papan2.serializePapan()) == false) && (currNode.getListMovement().getLast().isSameLetter(movement) == false)) {
                        nodeCount++;
                        nodeQueue.add(node);
                        node.getPapan().printPapan();
                        if (papan2.canPrimaryExit()) {
                            isFound = true;
                            isFinished = true;
                            resultMovement = node.getListMovement();
                            papanAkhir = papan2;
                            long endTime = System.currentTimeMillis();
                            executionTime = endTime - startTime;
                            return;
                        }
                    }

                } 
            }
            }
            isFinished = true;
            // get time elapsed
            long endTime = System.currentTimeMillis();
            executionTime = endTime - startTime;
        }
    }

    public void saveSolutionToFile() {
        Papan state = new Papan(papan);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Masukkan nama file untuk menyimpan solusi (tambahkan ekstensi .txt):");
        String filename;
        try {
            filename = reader.readLine();
            if (filename == null || filename.trim().isEmpty()) {
                throw new IllegalArgumentException("Nama file tidak boleh kosong.");
            }
            if (!filename.endsWith(".txt")) {
                throw new IllegalArgumentException("Nama file harus diakhiri dengan .txt");
            }
        } catch (IOException e) {
            throw new RuntimeException("Gagal membaca input nama file: " + e.getMessage(), e);
        }

        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println("Solusi ditemukan:");
            writer.println("Papan awal:");
            writer.println(state);
            for (Movement move : resultMovement) {
                writer.println("Move: " + move);
                state.applyMovement(move);
                writer.println(state);
            }
            writer.println("Visited nodes: " + nodeCount);
            writer.println("Execution time: " + executionTime + " ms");
        } catch (IOException e) {
            throw new RuntimeException("Gagal menyimpan file: " + e.getMessage(), e);
        }
    }
    public void saveNoSolutionToFile() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Masukkan nama file untuk menyimpan hasil (tambahkan ekstensi .txt):");
        String filename;
        try {
            filename = reader.readLine();
            if (filename == null || filename.trim().isEmpty()) {
                throw new IllegalArgumentException("Nama file tidak boleh kosong.");
            }
            if (!filename.endsWith(".txt")) {
                throw new IllegalArgumentException("Nama file harus diakhiri dengan .txt");
            }
        } catch (IOException e) {
            throw new RuntimeException("Gagal membaca input nama file: " + e.getMessage(), e);
        }

        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println("Tidak ada solusi ditemukan.");
            writer.println("Visited nodes: " + nodeCount);
            writer.println("Execution time: " + executionTime + " ms");
        } catch (IOException e) {
            throw new RuntimeException("Gagal menyimpan file: " + e.getMessage(), e);
        }
    }
}
