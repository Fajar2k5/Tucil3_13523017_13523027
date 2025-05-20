import java.util.*;

public class UCS {

    public static void uniformCostSearch(State initialState) {
        long startTime = System.currentTimeMillis();
        int visitCount = 0;

        // Priority queue berdasarkan cost ascending
        PriorityQueue<State> frontier = new PriorityQueue<>(Comparator.comparingInt(s -> s.cost));
        Map<State, Integer> stateCostMap = new HashMap<>(); // untuk menyimpan cost terkecil ke suatu state
        Set<State> explored = new HashSet<>();

        frontier.add(initialState);
        stateCostMap.put(initialState, initialState.cost);

        while (!frontier.isEmpty()) {

            State current = frontier.poll();
            visitCount++;

            if (explored.contains(current)) continue;
            explored.add(current);

            if (current.isGoal()) {
                long endTime = System.currentTimeMillis();
                System.out.println("Waktu eksekusi: " + (endTime - startTime) + " ms");
                System.out.println("Node dikunjungi: " + visitCount);
                current.printSolution();
                current.saveSolutionToFile(visitCount, endTime - startTime);
                return;
            }

            for (State next : current.getNextStates()) {
                int newCost = next.cost;

                if (!explored.contains(next) && 
                    (!stateCostMap.containsKey(next) || newCost < stateCostMap.get(next))) {

                    frontier.add(next);
                    stateCostMap.put(next, newCost);
                }
            }
        }

        System.out.println("Tidak ditemukan solusi");
        long endTime = System.currentTimeMillis();
        initialState.saveNoSolutionToFile(visitCount, endTime - startTime);
    }
}
