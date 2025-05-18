import java.util.*;

public class BeamSearch {
    private static int beamWidth=100; // Jumlah state terbaik yang dipertahankan
    
    public static void solve(State initialState) {
        long startTime = System.currentTimeMillis();
        Set<String> visited = new HashSet<>();
        int nodeCount = 0;
        List<State> currentBeam = new ArrayList<>();
        currentBeam.add(initialState);
        
        int iterations = 0;
        int maxIterations = 10000; // Batasan iterasi untuk menghindari loop tak terbatas
        
        while (!currentBeam.isEmpty() && iterations < maxIterations) {
            iterations++;
            List<State> nextBeam = new ArrayList<>();
            for (State current : currentBeam) {
                if (current.isGoal()) {
                    long endTime = System.currentTimeMillis();
                    System.out.println("Waktu eksekusi: " + (endTime - startTime) + " ms");
                    System.err.println("Node dikunjungi: " + nodeCount);
                    current.saveSolutionToFile(nodeCount, endTime - startTime);
                    return;
                }
                String boardKey = getBoardKey(current.getPapan().getPapan());
                visited.add(boardKey);
                List<State> successors = current.getNextStates();
                for (State successor : successors) {
                    String successorKey = getBoardKey(successor.getPapan().getPapan());
                    if (!visited.contains(successorKey)) {
                        nextBeam.add(successor);
                        nodeCount++;
                    }
                }
            }
            if (nextBeam.isEmpty()) {
                break;
            }
            
            // Sortir next beam berdasarkan kombinasi cost dan heuristic (f = g + h)
            Collections.sort(nextBeam, (s1, s2) -> {
                int f1 = s1.getCost() + s1.getHeuristic();
                int f2 = s2.getCost() + s2.getHeuristic();
                return Integer.compare(f1, f2);
            });
            
            currentBeam = nextBeam.size() <= beamWidth ? 
                          nextBeam : 
                          nextBeam.subList(0, beamWidth);
        }
        
        System.out.println("Tidak ada solusi ditemukan dalam " + iterations + " iterasi.");
    }
    
    // Fungsi untuk mendapatkan string representasi unik dari board
    private static String getBoardKey(char[][] board) {
        StringBuilder key = new StringBuilder();
        for (char[] row : board) {
            key.append(String.valueOf(row));
        }
        return key.toString();
    }
}