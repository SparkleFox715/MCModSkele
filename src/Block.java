import java.util.*;
public class Block {
    int XCord,YCord, ZCord;
    int Btype;//0->air, 1->ore, 2 ->other
    int Otype;//0->coal, 1->iron,2->gold,3->redstone, 4->lapiz, 5->diamond, 6->emerald, 7->neither
    public String lineColor;
    HashMap<Integer, String> BlockType = new HashMap<>();
    HashMap<Integer, String> OreType = new HashMap<>();
    public Block(int Btype, int OType, int X,int Y, int Z){
        this.Btype = Btype;
        this.Otype = OType;
        XCord = X;
        YCord = Y;
        ZCord = Z;
        BlockType.put(0, "air");
        BlockType.put(1, "ore");
        BlockType.put(2, "other");
        OreType.put(0, "coal");
        OreType.put(1, "iron");
        OreType.put(2, "gold");
        OreType.put(3, "redstone");
        OreType.put(4, "lapiz");
        OreType.put(5, "diamond");
        OreType.put(6, "emerald");
        OreType.put(7, "player");
        lineColor="";
    }
    public int[] getCords(){
        return new int[]{XCord, YCord, ZCord};
    }
    public double calcDist(Block b){
        //calculate horizontal distance
        int[] Bcords = b.getCords();
        double dist = Math.sqrt(Math.pow(XCord-Bcords[0], 2)+Math.pow(ZCord-Bcords[2], 2));
        //calculate vertical distance
        dist = Math.sqrt(Math.pow(dist, 2)+Math.pow(YCord-Bcords[1], 2));
        return dist;
    }
    public String toString(){
        return BlockType.get(Btype)+" "+OreType.get(Otype)+" "+XCord+" "+YCord+" "+ZCord+" "+getPlayerDist();
    }
    public double getPlayerDist(){
        return calcDist(new Block(0,0,0,0,0));
    }

}
