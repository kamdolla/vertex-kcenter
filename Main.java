import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException{

        HashMap<Integer, HashMap<Integer, Integer>> g = readInputGraph();

        int minDistRadius = 32;

        System.out.println(VertexKCenter.solve(g, minDistRadius));
    }

    public static HashMap<Integer, HashMap<Integer, Integer>> readInputGraph() throws IOException{
        FileReader fileReader = new FileReader("graph.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String prb = bufferedReader.readLine();
        bufferedReader.close();
        fileReader.close();

        HashMap<Integer,HashMap<Integer,Integer>> inputGraph =  new HashMap<>();

        //get each vert
        prb = prb.substring(1, prb.length()-1);
        String[] vertsEdges = prb.split("\\}, ");
        
        for(String edgeSet : vertsEdges){
            String[] keyThenEdges = edgeSet.split("=\\{");
            int key = Integer.parseInt(keyThenEdges[0]);
            String edges = keyThenEdges[1];
            HashMap<Integer, Integer> hm = new HashMap<>();
            String[] ps = edges.split(", ");
            for(String psprime : ps){
                String[] kv = psprime.split("=");
                if(kv[1].contains("}"))kv[1] = kv[1].substring(0, kv[1].length()-1);
                hm.put(Integer.parseInt(kv[0]), Integer.parseInt(kv[1]));
            }
            inputGraph.put(key, hm);
        }

        return inputGraph;
    }
}
