import java.util.List;
import java.util.ArrayList;

public class Node implements Comparable<Node> {
    private int jarak;
    private Papan papan;
    private List<Movement> listMovement = new ArrayList<>();
    
    public Node(int distance, Papan papan, List<Movement> listMovement) {
        this.jarak = distance;
        this.papan = papan;
        this.listMovement = new ArrayList<>(listMovement);
    }

    public Node(int distance, Papan papan, Movement newMovement) {
        this.jarak = distance;
        this.papan = papan;
        this.listMovement = new ArrayList<>();
        this.listMovement.add(newMovement);
    }

    public Node(int distance, Papan papan, List<Movement> listMovement, Movement newMovement) {
        this.jarak = distance;
        this.papan = papan;
        // deep copy listMovement
        this.listMovement = new ArrayList<>(listMovement);
        this.listMovement.add(newMovement);
    }
    
    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.jarak, other.jarak);
    }

    // @Override
    // public String toString() {
    //     return String.format("Node(%c, %s, %d)", huruf, arah, jarak);
    // }
    public Papan getPapan() {
        return papan;
    }
    public void setPapan(Papan papan) {
        this.papan = papan;
    }
    public int getJarak() {
        return jarak;
    }
    public void setJarak(int jarak) {
        this.jarak = jarak;
    }
    public List<Movement> getListMovement() {
        return listMovement;
    }
    public void setListMovement(List<Movement> listMovement) {
        this.listMovement = listMovement;
    }
    public void addMovement(Movement movement) {
        this.listMovement.add(movement);
    }
    public void removeMovement(Movement movement) {
        this.listMovement.remove(movement);
    }
    public void clearMovement() {
        this.listMovement.clear();
    }
    public boolean canExit() {
        return papan.canPrimaryExit();
    }
    
    public void setNode(Node node) {
        this.jarak = node.getJarak();
        this.papan = node.getPapan();
        this.listMovement = node.getListMovement();
    
    }
    
}
