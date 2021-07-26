import java.util.*;
import java.io.*;
public class MCModeRunner {
    public static void main(String[] args) throws IOException{
        Block playerPos = new Block(2, 7, 0,0,0);
        ArrayList<Block> blocks = new ArrayList();
        int chunkw, chunkl;
        int blockPreference;
        Scanner in = new Scanner(new File("input.txt"));
        boolean auto = in.nextBoolean();//false: manual, true: auto
        chunkw = in.nextInt();
        chunkl = in.nextInt();
        blockPreference = in.nextInt();
        blocks.addAll(searchOres(chunkw, chunkl, blockPreference));
        while(in.hasNextLine()){
            Block temp = new Block(in.nextInt(),in.nextInt(),in.nextInt(),in.nextInt(),in.nextInt());
            blocks.add(temp);
            System.out.println(temp.getPlayerDist());
        }
        System.out.println(blocks);
        blocks = quickSort(blocks);
        System.out.println(blocks);

    }

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
}

