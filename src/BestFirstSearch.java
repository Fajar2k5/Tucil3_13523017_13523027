import java.util.PriorityQueue;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class BestFirstSearch {
    private Papan papan;
    private PriorityQueue<Node> nodeQueue;
    private boolean isFinished;
    private boolean isFound;
    private List<Movement> resultMovement;

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

    public void search() {
        if (papan.canPrimaryExit()) {
            isFound = true;
            isFinished = true;
            return;
        }
        Map<String, Boolean> visitedMap = new HashMap<>();
        visitedMap.put(papan.serializePapan(), true);
        for (Piece piece : papan.getListNonPiece()) {
                char huruf = piece.getHurufPiece();
                // System.out.println("Piece " + huruf + " is being moved");
                if (piece.getOrientasi()==Piece.Orientasi.HORIZONTAL) {
                    int disLeft = papan.movePieceToLeftFarthest(huruf);
                    // System.out.println("Trying to move to left");
                    if (disLeft > 0) {
                        if (visitedMap.containsKey(papan.serializePapan()) == false) {
                            Movement movement = new Movement(huruf, Movement.Direction.LEFT, disLeft);
                            Node node = new Node(papan.countObstacleInFront(), new Papan(papan), movement);
                            nodeQueue.add(node);
                            if (papan.canPrimaryExit()) {
                                isFound = true;
                                isFinished = true;
                                resultMovement = node.getListMovement();
                                return;
                            }
                            node.getPapan().printPapan();
                        }
                        papan.movePieceRight(huruf, disLeft);
                        // System.out.println("Moved to left " + disLeft);
                    }
                    int disRight = papan.movePieceToRightFarthest(huruf);
                    // System.out.println("Trying to move to right");
                    if (disRight > 0) {
                        if (visitedMap.containsKey(papan.serializePapan()) == false) {
                            Movement movement = new Movement(huruf, Movement.Direction.RIGHT, disRight);
                            Node node = new Node(papan.countObstacleInFront(), new Papan(papan), movement);
                            nodeQueue.add(node);
                            if (papan.canPrimaryExit()) {
                                isFound = true;
                                isFinished = true;
                                resultMovement = node.getListMovement();
                                return;
                            }
                            node.getPapan().printPapan();
                        }
                        papan.movePieceLeft(huruf, disRight);
                        // System.out.println("Moved to right " + disRight);
                    }
                } else {
                    int disUp = papan.movePieceToUpFarthest(huruf);
                    // System.out.println("Trying to move to up");
                    if (disUp > 0) {
                        if (visitedMap.containsKey(papan.serializePapan()) == false) {
                            Movement movement = new Movement(huruf, Movement.Direction.UP, disUp);
                            Node node = new Node(papan.countObstacleInFront(), new Papan(papan), movement);
                            nodeQueue.add(node);
                            if (papan.canPrimaryExit()) {
                                isFound = true;
                                isFinished = true;
                                resultMovement = node.getListMovement();
                                return;
                            }
                            node.getPapan().printPapan();
                        }
                        papan.movePieceDown(huruf, disUp);
                        // System.out.println("Moved to up " + disUp);
                    }
                    int disDown = papan.movePieceToDownFarthest(huruf);
                    // System.out.println("Trying to move to down");
                    if (disDown > 0) {
                        if (visitedMap.containsKey(papan.serializePapan()) == false) {
                            Movement movement = new Movement(huruf, Movement.Direction.DOWN, disDown);
                            Node node = new Node(papan.countObstacleInFront(), new Papan(papan), movement);
                            nodeQueue.add(node);
                            if (papan.canPrimaryExit()) {
                                isFound = true;
                                isFinished = true;
                                resultMovement = node.getListMovement();
                                return;
                            }
                            node.getPapan().printPapan();
                        }
                        papan.movePieceUp(huruf, disDown);
                        // System.out.println("Moved to down " + disDown);
                    }
                }
            }
        while (true) {
            
            if (nodeQueue.isEmpty()) {
                isFinished = true;
                break;
            }
            System.out.println("Queue size: " + nodeQueue.size());
            while (!nodeQueue.isEmpty()) {
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
                for (Piece piece : currNode.getPapan().getListNonPiece()) {
                    char huruf = piece.getHurufPiece();
                    if (piece.getOrientasi()==Piece.Orientasi.HORIZONTAL) {
                        int disLeft = currNode.getPapan().movePieceToLeftFarthest(huruf);
                        // System.out.println("Trying to move to left");
                        if (disLeft > 0) {
                            if (visitedMap.containsKey(currNode.getPapan().serializePapan()) == false) {
                                Movement movement = new Movement(huruf, Movement.Direction.LEFT, disLeft);
                                Node node = new Node(currNode.getPapan().countObstacleInFront(), new Papan(currNode.getPapan()), currNode.getListMovement(), movement);
                                nodeQueue.add(node);
                                if (currNode.getPapan().canPrimaryExit()) {
                                    isFound = true;
                                    isFinished = true;
                                    resultMovement = node.getListMovement();
                                    return;
                                }
                                currNode.getPapan().printPapan();
                            }
                            currNode.getPapan().movePieceRight(huruf, disLeft);
                            // System.out.println("Moved to left " + disLeft);
                        }
                        int disRight = currNode.getPapan().movePieceToRightFarthest(huruf);
                        // System.out.println("Trying to move to right");
                        if (disRight > 0) {
                            if (visitedMap.containsKey(currNode.getPapan().serializePapan()) == false) {
                                Movement movement = new Movement(huruf, Movement.Direction.RIGHT, disRight);
                                Node node = new Node(currNode.getPapan().countObstacleInFront(), new Papan(currNode.getPapan()), currNode.getListMovement(), movement);
                                nodeQueue.add(node);   
                                if (currNode.getPapan().canPrimaryExit()) {
                                    isFound = true;
                                    isFinished = true;
                                    resultMovement = node.getListMovement();
                                    return;
                                } 
                                currNode.getPapan().printPapan();
                            }
                            currNode.getPapan().movePieceLeft(huruf, disRight);
                            // System.out.println("Moved to right " + disRight);
                        }
                    } else {
                        int disUp = currNode.getPapan().movePieceToUpFarthest(huruf);
                        // System.out.println("Trying to move to up");
                        if (disUp > 0) {
                            if (visitedMap.containsKey(currNode.getPapan().serializePapan()) == false) {
                                Movement movement = new Movement(huruf, Movement.Direction.UP, disUp);
                                Node node = new Node(currNode.getPapan().countObstacleInFront(), new Papan(currNode.getPapan()), currNode.getListMovement(), movement);
                                nodeQueue.add(node);
                                if (currNode.getPapan().canPrimaryExit()) {
                                    isFound = true;
                                    isFinished = true;
                                    resultMovement = node.getListMovement();
                                    return;
                                }
                                currNode.getPapan().printPapan();
                            }
                            currNode.getPapan().movePieceDown(huruf, disUp);
                            // System.out.println("Moved to up " + disUp);
                        }
                        int disDown = currNode.getPapan().movePieceToDownFarthest(huruf);
                        // System.out.println("Trying to move to down");
                        if (disDown > 0) {
                            if (visitedMap.containsKey(currNode.getPapan().serializePapan()) == false) {
                                Movement movement = new Movement(huruf, Movement.Direction.DOWN, disDown);
                                Node node = new Node(currNode.getPapan().countObstacleInFront(), new Papan(currNode.getPapan()), currNode.getListMovement(), movement);
                                nodeQueue.add(node);
                                if (currNode.getPapan().canPrimaryExit()) {
                                    isFound = true;
                                    isFinished = true;
                                    resultMovement = node.getListMovement();
                                    return;
                                }
                                currNode.getPapan().printPapan();
                            }
                            currNode.getPapan().movePieceUp(huruf, disDown);
                            // System.out.println("Moved to down " + disDown);
                        }
                    }
                }
            }
            isFinished = true;
            isFound = false;

        }
    }
}
