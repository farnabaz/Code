import java.util.ArrayList;

public class DFS {

    // adjacency list
    ArrayList<Integer> graph[];

    // nodes color
    byte colors[];

    /**
     * initialize dfs
     * @param n number of graph nodes
     */
    public void init(int n){
        graph = new ArrayList[n];
        colors = new byte[n];
        for (int i = 0; i < n; i++){
            graph[i] = new ArrayList<Integer>();
        }
    }

    /**
     * Start DFS search
     */
    public void dfs(){
        for (int i = 0; i < graph.length; i++) {
            if (colors[i] == 0) {
                dfsVisit(i);
            }
        }
    }

    /**
     * Run DFS for a node
     * @param index start index of DFS
     */
    private void dfsVisit(int index) {
        colors[index] = 1;
        for (int i = 0; i < graph[index].size(); i++) {
            if (colors[graph[index].get(i)] == 0) {
                dfsVisit(i);
            }
        }
    }
}
