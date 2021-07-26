import java.util.*;
import java.io.*;
public class MCModeRunner {
    static HashMap<Node, Double> distTo = new HashMap<>();
    static HashMap<Node, Node> edgeTo = new HashMap<>();
    public static void main(String[] args) throws IOException{
        //Representing player pos
        Block playerPos = new Block(2, 7, 0,0,0);
        Node origin = new Node(playerPos); //Representing starting node/player node
        ArrayList<Block> blocks = new ArrayList();//contains all ores of interest
        ArrayList<Node> nodes = new ArrayList(); //contains nodes of ores of interest
        PriorityQueue pq = new PriorityQueue(new NodeComparator());
        ArrayList<Node> fin = new ArrayList();//Contains the final order of nodes/ores
        int chunkw, chunkl;
        int blockPreference;
        Scanner in = new Scanner(new File("input.txt"));
        boolean auto = in.nextBoolean();//false: manual, true: auto
        chunkw = in.nextInt();
        chunkl = in.nextInt();
        blockPreference = in.nextInt();
        distTo.put(origin, -1.0);
        edgeTo.put(origin, null);
        pq.add(origin);
        blocks.addAll(searchOres(chunkw, chunkl, blockPreference));
        //the purpose of this loop is to intake ore info from txt file
        while(in.hasNextLine()){
            Block temp = new Block(in.nextInt(),in.nextInt(),in.nextInt(),in.nextInt(),in.nextInt());
            blocks.add(temp);
            Node def = new Node(temp);
            distTo.put(def, Integer.MAX_VALUE+0.0);
            edgeTo.put(def, null);
            pq.add(def);
            nodes.add(def);
            origin.add(def);
        }
        if(!auto){//manual
            int playerRange = 30;//30 blocks
            blocks = quickSort(blocks);
            System.out.println(blocks);
            Queue<Block>[]buckets = new Queue[7];
            for(int i=0;i<7;i++){
                buckets[i] = new LinkedList<>();
            }
            HashMap<Integer, String> LineColor = new HashMap<>();
            LineColor.put(0, "black");
            LineColor.put(1, "grey");
            LineColor.put(2, "yellow");
            LineColor.put(3, "red");
            LineColor.put(4, "blue");
            LineColor.put(5, "teal");
            LineColor.put(6, "green");
            for(int i = 0;i<blocks.size();i++) {
                if (blocks.get(i).Otype < 7) {
                    buckets[blocks.get(i).Otype].add(blocks.get(i));
                }

            }
            for(int i = 0;i<7;i++){
                while(buckets[i].size()>0) {
                    Block temp = buckets[i].poll();
                    if (temp.getPlayerDist() <= playerRange) {
                        System.out.println(temp + " " + LineColor.get(temp.Otype));
                    }
                }
            }
            //send these strings to the MC client to generate said-colored string pointing at ores.
            //An array of stacks is used incase the use wants to specify which ores they wish to look for.
        }else {//auto
            addNodes(nodes);
            System.out.println(pq);
            Prim(pq, origin, nodes, fin);
            System.out.println(fin);
        }
    }
    //Prim's algorithm
    public static void Prim(PriorityQueue<Node> pq, Node origin, ArrayList<Node>nodes, ArrayList<Node> fin){
        Node current = origin;
        edgeTo.remove(current);
        distTo.remove(current);
        while(!pq.isEmpty()){
            for(int i=0;i<current.connected.size();i++){
                if(edgeTo.containsKey(current.connected.get(i))) {
                    edgeTo.replace(current.connected.get(i), current);
                    distTo.replace(current.connected.get(i), current.weights.get(current.connected.get(i)));
                }
            }
            fin.add(current);
            if(current.equals(origin))
                pq.remove(current);
            current = pq.remove();
            if(pq.isEmpty())
                fin.add(current);
            edgeTo.remove(current);
            distTo.remove(current);
            pq = update(pq);


        }

    }
    public static PriorityQueue<Node> update(PriorityQueue<Node> pq){
        PriorityQueue<Node> temp = new PriorityQueue<>(new NodeComparator());
        temp.addAll(pq);
        return pq;
    }
    //connecting all the nodes together to make a weighted graph
    public static void addNodes(ArrayList<Node> nodes){
        for(int x = 0;x<nodes.size();x++){
            for(int i=0;i<nodes.size();i++){
                if(x!=i){
                    nodes.get(x).add(nodes.get(i));
                }
            }
        }
    }
    //Used in the MC client to find ores within the range of chunks
    public static ArrayList<Block> searchOres(int chunkw, int chunkl, int blockPreference){
        ArrayList<Block> ret = new ArrayList();
        for(int i =0;i<257;i++){
            for(int x = 0;x<chunkw*16;x++){
                for(int j=0;j<chunkl;j++){
                    /*
                    Use the Minecraft library to get the type of block at the xyz coordinate and
                    add it to the arraylist if the block is the preferred type of ore.
                    */
                }
            }
        }
        return ret;
    }
    //used to set priority for ores in the future
    public static ArrayList<Block> quickSort(ArrayList<Block> blocks){
        if(blocks.size()<=1){
            return blocks;
        }
        ArrayList<Block>smaller = new ArrayList();
        ArrayList<Block>same = new ArrayList();
        ArrayList<Block>larger = new ArrayList();
        double pivot = blocks.get(0).getPlayerDist();
        for(int i=0;i<blocks.size();i++){
            if(blocks.get(i).getPlayerDist()<pivot){
                smaller.add(blocks.get(i));
            }else if(blocks.get(i).getPlayerDist()>pivot){
                larger.add(blocks.get(i));
            }else{
                same.add(blocks.get(i));
            }
        }
        smaller = quickSort(smaller);
        larger  = quickSort(larger);
        smaller.addAll(same);
        smaller.addAll(larger);
        return smaller;
    }

    private static class NodeComparator implements Comparator<Node>{
        public int compare(Node n1, Node n2){
            if(distTo.get(n1)<distTo.get(n2))
                return -1;
            else if(distTo.get(n1)>distTo.get(n2))
                return 1;
            return 0;
        }
    }
}

