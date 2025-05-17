import java.util.*;

public class UCS {

    public static void uniformCostSearch(State initialState) {
        PriorityQueue<State> frontier = new PriorityQueue<>();
        Set<State> explored = new HashSet<>();

        frontier.add(initialState);

        while (!frontier.isEmpty()) {
            State current = frontier.poll();

            if (current.isGoal()) {
                System.out.println("Solusi ditemukan dengan cost: " + current.cost);
                current.printSolution();
                return;
            }

            explored.add(current);

            for (State next : current.getNextStates()) {
                if (!explored.contains(next) && !frontier.contains(next)) {
                    frontier.add(next);
                } else if (frontier.contains(next)) {
                    // Jika next sudah ada di frontier dengan cost lebih tinggi, update
                    for (State stateInFrontier : frontier) {
                        if (stateInFrontier.equals(next) && stateInFrontier.cost > next.cost) {
                            frontier.remove(stateInFrontier);
                            frontier.add(next);
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("Tidak ditemukan solusi");
    }
}
