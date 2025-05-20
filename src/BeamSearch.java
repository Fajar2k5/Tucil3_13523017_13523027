import java.util.*;

public class BeamSearch {
    private static int beamWidth = 100;

    public static void solve(State initialState) {
        long startTime = System.currentTimeMillis();
        Map<String, Integer> visitedFScore = new HashMap<>(); // key: board, value: f = cost + heuristic
        int nodeCount = 0;
        List<State> currentBeam = new ArrayList<>();
        currentBeam.add(initialState);

        int iterations = 0;
        int maxIterations = 10000;

        while (!currentBeam.isEmpty() && iterations < maxIterations) {
            iterations++;
            List<State> nextBeam = new ArrayList<>();

            for (State current : currentBeam) {
                if (current.isGoal()) {
                    long endTime = System.currentTimeMillis();
                    System.out.println("Waktu eksekusi: " + (endTime - startTime) + " ms");
                    System.err.println("Node dikunjungi: " + nodeCount);
                    current.printSolution();
                    current.saveSolutionToFile(nodeCount, endTime - startTime);
                    return;
                }

                String currentKey = getBoardKey(current.getPapan().getPapan());
                int currentF = current.getCost() + current.getHeuristic();
                visitedFScore.put(currentKey, currentF);

                List<State> successors = current.getNextStates();
                for (State successor : successors) {
                    String succKey = getBoardKey(successor.getPapan().getPapan());
                    int succF = successor.getCost() + successor.getHeuristic();

                    if (!visitedFScore.containsKey(succKey) || succF < visitedFScore.get(succKey)) {
                        visitedFScore.put(succKey, succF);
                        nextBeam.add(successor);
                        nodeCount++;
                    }
                }
            }

            if (nextBeam.isEmpty()) break;

            // Sort by f = cost + heuristic
            nextBeam.sort(Comparator.comparingInt(s -> s.getCost() + s.getHeuristic()));

            currentBeam = nextBeam.size() <= beamWidth ?
                          nextBeam :
                          nextBeam.subList(0, beamWidth);
        }

        System.out.println("Tidak ada solusi ditemukan dalam " + iterations + " iterasi.");
        long endTime = System.currentTimeMillis();
        initialState.saveNoSolutionToFile(nodeCount, endTime - startTime);
    }

    private static String getBoardKey(char[][] board) {
        StringBuilder key = new StringBuilder();
        for (char[] row : board) {
            key.append(row);
        }
        return key.toString();
    }
}
