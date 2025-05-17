import java.util.List;

public class IDAStar {
    private static final int INF = Integer.MAX_VALUE;
    private static List<State> solutionPath;

    public static void solve(State start) {
        int threshold = start.getCost() + start.getHeuristic();
        while (true) {
            System.out.println("Threshold: " + threshold);
            int temp = search(start, 0, threshold);
            if (temp == -1) {
                // Solusi ditemukan
                System.out.println("Solusi ditemukan dengan cost: " + solutionPath.get(solutionPath.size() - 1).getCost());
                for (State s : solutionPath) {
                    System.out.println(s.getMove());
                    s.getPapan().printPapan();
                    System.out.println("Total Cost: " + s.getCost()+s.getHeuristic());
                }
                return;
            }
            if (temp == INF) {
                System.out.println("Tidak ada solusi ditemukan.");
                return;
            }
            threshold = temp; // Naikkan batas cost
        }
    }

    private static int search(State node, int g, int threshold) {
        int f = g + node.getHeuristic();
        if (f > threshold) return f; // Lewati jika cost melebihi threshold
        if (node.isGoal()) {
            solutionPath = node.getPath();
            return -1; // Tandai solusi ditemukan
        }

        int min = INF;
        for (State succ : node.getNextStates()) {
            int temp = search(succ, g + succ.getCost() - node.getCost(), threshold);
            if (temp == -1) return -1;
            if (temp < min) min = temp;
        }
        return min;
    }
}
