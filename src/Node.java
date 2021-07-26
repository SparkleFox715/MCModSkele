import java.util.ArrayList;
import java.util.HashMap;

public class Node {
    Block block;
    ArrayList<Node>connected;
    HashMap<Node, Double> weights;
    public Node(Block block){
        this.block = block;
        connected = new ArrayList<>();
        weights = new HashMap<>();
    }
    public void add(Node n){
        if(!connected.contains(n)){
            connected.add(n);
            weights.put(n, block.calcDist(n.block));
        }
    }
    public String toString(){
        return block.toString();
    }
}
