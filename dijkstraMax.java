import java.util.*;

/**
*
* @author Kola Ladipo
* Minimum Spanning Tree Application
* 
*/

public class dijkstraMax {
	public static int V;		// number of vertices in graph
	public static int E;		// number of edges or connections in graph
	
    public static int dijkstra(Graph graph, int source, int destination) {
        int[] stat = new int[V];
        int[] parent = new int[V];
        int[] distance = new int[V];

        for (int i = 0; i < V; i++) {
            stat[i] = 0;
            distance[i] = Integer.MAX_VALUE;
        }

        //Update info of source
        stat[source] = 2;
        parent[source] = -1;

        //Update vertices adjacent to source
        ArrayList<Edge> verticesToSource = graph.getAdj()[source];
        for (Edge edge : verticesToSource) {
            int w = edge.getOtherVertex(source);
            stat[w] = 1;
            parent[w] = source;
            distance[w] = edge.getWeight();
        }

        //Dijsktra algorithm to find destination
        int count = 0;
        while (stat[destination] != 2) {
            count++;
            int maxBandwidth = Integer.MIN_VALUE;
            int u = -1;
            for (int i = 0; i < V; i++) {
                if (stat[i] == 1) {
                    if (distance[i] > maxBandwidth) {
                        maxBandwidth = distance[i];
                        u = i;
                    }
                }
            }
            
            stat[u] = 2;
            ArrayList<Edge> verticesToV = graph.getAdj()[u];
            for (Edge edge : verticesToV) {
                int v = edge.getOtherVertex(u);
                if (stat[v] == 0) {
                    parent[v] = u;
                    stat[v] = 1;
                    distance[v] = Math.min(distance[u], edge.getWeight());
                } else if(stat[v] == 1 && distance[v] < Math.min(distance[u], edge.getWeight())) {
                    parent[v] = u;
                    distance[v] = Math.min(distance[u], edge.getWeight());
                }
            }
        }

        return distance[destination];
    }
    
    
    static void space(int n) {
    	for(int i = 0; i < n; ++i) {
    		System.out.println();
    	}
    }
    
    
    static void print(String str) {
    	System.out.println(str);
    }

    
    public static void main(String[] args) {

    	int start = -1, end = -1, weight = -1, SOURCE = -1, DESTINATION = -1;

    	HashSet<Integer> VertexNames = new HashSet<Integer>();
    	
    	Scanner scnr = new Scanner(System.in);
    	
//    	System.out.println("Enter number of vertices: ");
//        
//    	while(V < 1) {
//    		V = scnr.nextInt();
//    		if (V < 1) print("Number must be a positive integer\n");
//    		}
    	
    	
    	boolean valid;

        do {
            valid = true;
            print("Enter number of vertices: ");
            try {
                V = Integer.parseInt(scnr.nextLine());
                if (V < 1) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                System.out.print("Invalid Entry! ");
                valid = false;
            }
        } while (!valid);
        
        
//        Scanner scnr2 = new Scanner(System.in);
        do {
            valid = true;
            print("Enter number of Edges: ");
            try {
                E = Integer.parseInt(scnr.nextLine());
                if (E < 1) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                System.out.print("Invalid Entry! ");
                valid = false;
            }
        } while (!valid);
    	

    	
        Graph graph = new Graph(V);

        for(int i = 0; i < E; ++i) {
        	print("Enter Edge "+ (i+1) + " of " + E + ": ");
        	
        	// Input Source Value
            do {
                valid = true;
                try {
                	print("Source" + "["+(i+1)+"] - ");
                    start = Integer.parseInt(scnr.nextLine());
                    if (start < 0 || start > V) {
                        throw new IllegalArgumentException();
                    }
                } catch (IllegalArgumentException e) {
                    System.out.print("Invalid! Enter Range [0-" + V + "]. ");
                    valid = false;
                }
            } while (!valid);
            
            // Input Destination Value
            do {
                valid = true;
                try {
                	print("Destination" + "["+(i+1)+"] - ");
                    end = Integer.parseInt(scnr.nextLine());
                    if (end < 0 || end > V) {
                        throw new IllegalArgumentException();
                    }
                } catch (IllegalArgumentException e) {
                    System.out.print("Invalid! Enter Range [0-" + V + "]. ");
                    valid = false;
                }
            } while (!valid);
            
        	// Input Weight of Edge
            do {
                valid = true;
                try {
                	print("Weight" + "["+(i+1)+"] - ");
                    weight = Integer.parseInt(scnr.nextLine());
                    if (weight < 1) {
                        throw new IllegalArgumentException();
                    }
                } catch (IllegalArgumentException e) {
                    System.out.print("Enter Integer > 0. ");
                    valid = false;
                }
            } while (!valid);
            
        	graph.addEdge(start, end, weight);
        	int num = start+end;
        	VertexNames.add(start);
        	VertexNames.add(end);
            space(1);
        }
        
        
        boolean exitLoop = false;
        char c = ' ';
        
        while(!exitLoop) {
        	do {
                valid = true;
                try {
                	print("Enter SOURCE: ");
                    SOURCE = Integer.parseInt(scnr.nextLine());
                    if (!VertexNames.contains(SOURCE)) {
                        throw new IllegalArgumentException();
                    }
                } catch (IllegalArgumentException e) {
                    System.out.print("Source not found! Please re-enter: ");
                    valid = false;
                }
            } while (!valid);
            
            
            do {
                valid = true;
                try {
                	print("Enter DESTINATION: ");
                    DESTINATION = Integer.parseInt(scnr.nextLine());
                    if (!VertexNames.contains(DESTINATION)) {
                        throw new IllegalArgumentException();
                    }
                } catch (IllegalArgumentException e) {
                    System.out.print("Destination not found! Please re-enter: ");
                    valid = false;
                }
            } while (!valid);
            

            	print("Smallest edge on the path with the maximum total weight: " +
            			dijkstra(graph, SOURCE, DESTINATION));
//            }
            
        	
        	
        	print("Type any key to continue OR X to exit!");
        	c = scnr.next().charAt(0);
        	if(c == 'x' || c == 'X') {
        		System.exit(0);
        	}
        	scnr.nextLine();
        }
        
        
        
    } // end main
}


class Edge {
    private int start;
    private int end;
    private int weight;
    
    public Edge(int start, int end, int weight) {
        if (start < 0 || end < 0 || weight < 0) {
        	System.out.println("Vertices and weight must be a nonnegative integer");
        	System.exit(0);
        }
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
    
    public int getWeight() {
        return weight;
    }

    public int getOtherVertex(int x) {
        if (x == start) {
            return end;
        } else if (x == end) {
            return start;
        } else throw new IllegalArgumentException("Illegal endpoint");
    }
    
    @Override
    public String toString() {
        return start + "-" + end + " " + weight;
    }
}


class Graph {

    private int vertices;
    private int edge;
    ArrayList<Edge>[] adjacent;
    
    public Graph(int vertices) {
        //Initializes an empty graph
        this.vertices = vertices;
        this.edge = 0;
        adjacent = (ArrayList<Edge>[])new ArrayList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacent[i] = new ArrayList<Edge>();
        }
    }
    
    public ArrayList<Edge>[] getAdj() {
        return adjacent;
    }

    public void addEdge(int start, int end, int weight) {
        Edge edge1 = new Edge(start, end, weight);
        adjacent[start].add(edge1);
        Edge edge2 = new Edge(start, end, weight);
        adjacent[end].add(edge2);
        edge += 2;
    }
}


