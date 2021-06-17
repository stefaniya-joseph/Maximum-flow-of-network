//Name: Stefaniya Joseph
//UoW ID: w1790199
//Reference: https://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Main{

    public static FileReader file =new FileReader();
    public static String[] list1=file.file(file());
    public static Graph graph1= new Graph(file.list(list1));
    static final int V = Integer.parseInt(list1[0]); // Number of vertices in graph

    public static void main (String[] args) throws FileNotFoundException {
        Graph.printGraph(graph1,V);
        long start = System.currentTimeMillis();
        int graph[][] = graph(file.list(list1));
        System.out.println("~~~~The maximum possible flow is obtained by using BFS and ford fulkerson algorithm~~~~");
        System.out.println("Start\n" +
                "Initialize maxFlow = 0\n" +
                "repeat until there is no path from s to t\n" +
                "\trun Breadth First Search from source node to find a flow path to end node\n" +
                "\tf = minimum capacity value on the path\n" +
                "\tmaxFlow = maxFlow + f\n" +
                "for each edge (u,v) on the path:\n" +
                "\tdecrease capacity of the edge(u,v) by f\n" +
                "\tincrease capacity of the edge (v,u) by f\n" +
                "return max flow\n");

        System.out.println("The maximum possible flow is " + fordFulkerson(graph, 0,(V-1)));

        long now = System.currentTimeMillis();
        double elapsed = (now - start) / 1000.0;

        System.out.println("Elapsed time = " + elapsed + " seconds");

    }

    public static String file(){
        Scanner obj=new Scanner(System.in);
        System.out.print("Enter the file name: ");
        String fileName =obj.nextLine();
        return fileName;
    }

    public static int[][] graph(List<Edge> list){
        int rGraph[][] = new int[V][V];
        boolean visited[][] = new boolean[V][V];
        for(int u=0;u<V;u++){
            for(int v=0;v<V;v++){
                visited[u][v]=false;
            }
        }
        for(Edge edge: list){
            for(int u=0;u<V;u++){
                if(edge.src==u){
                    for(int v=0;v<V;v++){
                        if(edge.dest==v){
                            rGraph[u][v]=edge.weight;
                            visited[u][v]=true;
                        }
                    }
                }

            }
        }
        for(int u=0;u<V;u++){
            for(int v=0;v<V;v++){
                if(!visited[u][v]){
                    rGraph[u][v]=0;
                }
            }
        }
        return rGraph;
    }

    static boolean bfs(int rGraph[][], int s, int t, int parent[])
    {
        // Create a visited array and mark all vertices as
        // not visited
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; ++i)
            visited[i] = false;

        // Create a queue, enqueue source vertex and mark
        // source vertex as visited
        LinkedList<Integer> queue
                = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        // Standard BFS Loop
        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < V; v++) {
                if (visited[v] == false && rGraph[u][v] > 0) {
                    // If we find a connection to the sink
                    // node, then there is no point in BFS
                    // anymore We just have to set its parent
                    // and can return true
                    if (v == t) {
                        parent[v] = u;
                        return true;
                    }
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        // We didn't reach sink in BFS starting from source, so return false
        return false;
    }

    // Returns tne maximum flow from s to t in the given graph
    static int fordFulkerson(int graph[][], int s, int t)
    {
        int u, v;

        // Create a residual graph and fill the residual
        // graph with given capacities in the original graph
        // as residual capacities in residual graph

        // Residual graph where rGraph[i][j] indicates
        // residual capacity of edge from i to j (if there
        // is an edge. If rGraph[i][j] is 0, then there is
        // not)
        int rGraph[][] = new int[V][V];

        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                rGraph[u][v] = graph[u][v];

        // This array is filled by BFS and to store path
        int parent[] = new int[V];

        int max_flow = 0; // There is no flow initially

        // Augment the flow while tere is path from source to sink
        while (bfs(graph, s, t, parent)) {
            // Find minimum residual capacity of the edhes
            // along the path filled by BFS. Or we can say
            // find the maximum flow through the path found.
            int path_flow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                path_flow
                        = Math.min(path_flow, graph[u][v]);
            }

            // update residual capacities of the edges and
            // reverse edges along the path
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                graph[u][v] -= path_flow;
                graph[v][u] += path_flow;
            }

            // Add path flow to overall flow
            max_flow += path_flow;
        }

        // Return the overall flow
        return max_flow;
    }

}
