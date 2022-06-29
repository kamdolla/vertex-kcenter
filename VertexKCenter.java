import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Solves the vertex k-center problem (with a slight variation).
 * <p>
 * Vertex k-center Problem with Radius :
 *      select a subset of vertices
 *      such that each other vertex
 *      is within at least one radius 
 *      of any one of the subset vertices
 * 
 * @author      Kameron Melvin <kmelvin22@amherst.edu>
 * @version     1.0
 * @since       1.0
 * 
 * @see         VertexSet
 */
class VertexKCenter{

    /**
     * Solves the vertex k-center problem (with a slight variation).
     * <p>
     * Finds vertex set (describing neighbors of vertex within minDistRadius radius) for each vertex, 
     * sorts vertex set list by vertex set size from max to min,
     * removes largest vertex set and all its elements from vertex set list,
     * and repeats removal and resorting process until vertex set list is empty.
     * <p>
     * When a vertex set is said to be removed, we also add that desired vertex set root
     * onto the list of the desired answer set.
     * 
     * @param   g               graph mapping of vertices to edges and their weights
     * @param   minDistRadius   vertex set radius
     * 
     * @return  List            vertex set
     */
    public static HashSet<Integer> solve(HashMap<Integer, HashMap<Integer,Integer>> g, int minDistRadius){
        
        HashSet<Integer>    answerSet       = new HashSet<Integer>();

        List<VertexSet>     vertexSetList   = findVertexSetList(g, new ArrayList<Integer>(g.keySet()), minDistRadius);

        while(!vertexSetList.isEmpty()){
            
            sortVertexSetList(vertexSetList);

            VertexSet largestVertexSet = vertexSetList.get(0);

            answerSet.add(largestVertexSet.root);

            removeVertexSet(vertexSetList, largestVertexSet);
        }

        return answerSet;
    }

    /**
     * For each vertex within given graph, finds vertex set within minDistRadius of given vertex
     * 
     * @param   g               – graph mapping of vertices to edges and their weights
     * @param   vertexList      – list of graph vertex
     * @param   minDistRadius   – vertex set radius
     * 
     * @return  List            – list of vertex sets
     * 
     * @see     VertexSet
     */
    public static List<VertexSet> findVertexSetList(HashMap<Integer, HashMap<Integer, Integer>> g, List<Integer> vertexList, int minDistRadius){

        List<VertexSet> vertexSetList = new ArrayList<VertexSet>();

        for (Integer vertex : vertexList){
            vertexSetList.add(new VertexSet(vertex, findVertexInRadius(g, vertex, minDistRadius)));
        }

        return vertexSetList;
    }

    /**
     * For given graph, finds vertices within minDistRadius of given vertex
     * <p>
     * Solves using a Dijkstras implementation, bound by a radius.
     * 
     * @param   g               – graph mapping of vertices to edges and their weights
     * @param   inputVertex     – graph vertex
     * @param   minDistRadius   – vertex set radius
     * 
     * @return  List            – vertex neighbor set
     */
    public static List<Integer> findVertexInRadius(HashMap<Integer, HashMap<Integer, Integer>> g, Integer inputVertex, int minDistRadius){

        HashMap<Integer, Integer> vertexInRadius    = new HashMap<Integer, Integer>();

        vertexInRadius.put(inputVertex, 0);

        Queue<Integer> vertexVisitedQueue           = new LinkedList<Integer>(vertexInRadius.keySet());

        while (!vertexVisitedQueue.isEmpty()){
            Integer vertex = vertexVisitedQueue.poll();

            for (Integer neighbor : g.get(vertex).keySet()){

                int distToVertex    = g.get(vertex).get(neighbor) + vertexInRadius.get(vertex);

                if (distToVertex <= minDistRadius){

                    if (vertexInRadius.containsKey(neighbor)){
                        if (distToVertex < vertexInRadius.get(neighbor)){
                            vertexInRadius.put(neighbor, distToVertex);
                            vertexVisitedQueue.add(neighbor);
                        }
                    }
                    else{
                        vertexInRadius.put(neighbor, distToVertex);
                        vertexVisitedQueue.add(neighbor);
                    }
                }
            }
        }

        return new ArrayList<Integer>(vertexInRadius.keySet());
    }

    /**
     * Removes vertex set and all its elements from given list and each of its vertex set elements
     * <p>
     * If remove vertex contains root of desired vertex set, remove desired vertex set
     * 
     * @param   vertexSetList   – vertex set list
     * @param   remVertexSet    – vertex set (to remove)
     * 
     * @see     VertexSet
     */
    public static void removeVertexSet(List<VertexSet> vertexSetList, VertexSet remVertexSet){

        vertexSetList.remove(remVertexSet);

        Queue<VertexSet> vertexSetQueue = new LinkedList<VertexSet>(vertexSetList);

        while (!vertexSetQueue.isEmpty()){
            VertexSet vertexSet = vertexSetQueue.poll();

            if (vertexSet.remove(remVertexSet))
                vertexSetList.remove(vertexSet);
        }
    }

    /**
     * Sorts given list of vertex sets by largest set size to smallest
     * 
     * @param   vertexSetList   – vertex set list
     * 
     * @see     VertexSet
     */
    public static void sortVertexSetList(List<VertexSet> vertexSetList){

        Collections.sort(vertexSetList, (a, b) -> b.set.size() - a.set.size());   
    }

}

/**
 * Vertex set object.
 * <p>
 * Class contains the following constructors:
 *      {@code root}, the vertex owner of this vertex set;
 *      {@code set}, the set of vertices within {@code minDistRadius} of the root.
 * <p>
 * Class contains the following methods:
 *      {@link VertexSet#remove(VertexSet)}, removes all elements of input vertex set from this vertex set.
 * 
 * @author      Kameron Melvin <kmelvin22@amherst.edu>
 * @version     1.0
 * @since       1.0
 */
class VertexSet {
    public Integer         root;
    public List<Integer>   set;

    /**
     * Vertex set object.
     * 
     * @param   vertex      root of vertex set
     * @param   vertexSet   vertex set
     */
    public VertexSet(Integer vertex, List<Integer> vertexSet){
        root    = vertex;
        set     = vertexSet;
    }

    public String toString(){

        return root + "=" + set;
    }

    /**
     * Removes all elements from vertex set from this set
     * <p>
     * Return true if remove vertex set contains this vertex set root
     * 
     * @param   remVertexSet    vertex set (to remove)
     */
    public boolean remove(VertexSet remVertexSet){
        if (remVertexSet.set.contains(this.root))
            return true;

        this.set.remove(remVertexSet.root);
        this.set.removeAll(remVertexSet.set);
        return false;
    }
}