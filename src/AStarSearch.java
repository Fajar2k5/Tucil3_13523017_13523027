import java.util.*;

public class AStarSearch {
    // Comparator untuk A*: f(n) = g(n) + h(n)
    private static final Comparator<State> aStarComparator = (s1, s2) -> {
        int f1 = s1.getCost() + s1.getHeuristic();
        int f2 = s2.getCost() + s2.getHeuristic();
        return Integer.compare(f1, f2);
    };

    public static void solve(State startState) {
        PriorityQueue<State> openSet = new PriorityQueue<>(aStarComparator);
        Map<State, Integer> bestFScore = new HashMap<>();

        openSet.add(startState);
        bestFScore.put(startState, startState.getCost() + startState.getHeuristic());

        while (!openSet.isEmpty()) {
            State current = openSet.poll();

            if (current.isGoal()) {
                System.out.println("Solusi ditemukan dengan cost: " + current.getCost());
                current.printSolution();
                return;
            }

            for (State next : current.getNextStates()) {
                int g = next.getCost();
                int f = g + next.getHeuristic();

                if (!bestFScore.containsKey(next) || f < bestFScore.get(next)) {
                    bestFScore.put(next, f);
                    openSet.add(next);
                }
            }
        }

        System.out.println("Tidak ada solusi ditemukan.");
    }
}
