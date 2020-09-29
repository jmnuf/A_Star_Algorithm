package org.nglr.astar;

import java.io.Serializable;
import java.util.function.Consumer;
import org.nglr.astar.twodim.Grid2D;
import org.nglr.astar.twodim.Grid2DNoDiagonal;

/**
 * The base for the "world" where <code>AStar</code> class
 * implements the A* algorithm
 *
 * @author J
 * @param <E extends Node> Node Type
 * @see AStar
 * @see Node
 * @see Grid2DNoDiagonal
 * @see Grid2D
 */
public abstract class Grid<E extends Node> implements Serializable {

    /**
     * <code>Node[]</code> that represents the "world"
     */
    protected Node[] map;
    /** Starting point to reach the goal/end point  */
    protected Node start;
    /** End point that want to reach  */
    protected Node goal;
    /** Way to convert Node indexes to greater positions  */
    protected NodeIndexPosConverter nipc;
    
    /**
     * @return nipc in use
     */
    public NodeIndexPosConverter getNIPC() {
        return nipc;
    }
    /**
     * Set the new NIPC values
     * 
     * @param seperation Node seperation
     * @param offset Node placement offset
     */
    public void setNIPC(int seperation, int offset) {
        this.nipc = new NodeIndexPosConverter(seperation, offset);
    }
    /**
     * Set the NIPC
     * 
     * @param nipc new <code>NodeIndexPosConverter</code> instance
     */
    public void setNIPC(NodeIndexPosConverter nipc) {
        this.nipc = nipc;
    }
    
    /**
     * Creates the "world" to hold the given dimension sizes
     * that are given
     * 
     * @param size dimension sizes
     */
    protected abstract void create(int[] size);
    /**
     * Set the start point and end point by giving the dimensional
     * indexes of the nodes
     * 
     * @param start dimensional indexes of starting node
     * @param end dimensional indexes of ending node
     */
    protected abstract void setup(int[] start, int[] end);
    /**
     * Set the start point and end point by giving their absolute
     * indexes in the map <code>Node[]</code>
     * 
     * @param i start node's index
     * @param j end node's index
     */
    public void setup(int i, int j) {
        start = map[i];
        goal = map[j];
    }
    
    /**
     * 
     * @return the "world" dimension sizes
     */
    public abstract int[] getSize();
    
    /**
     * Returns the "world" as a one dimensional node array
     * 
     * @return the "world"
     */
    public Node[] getMap() {
        return map;
    }
    
    /**
     * If a start is set then it returns it else it returns null
     * 
     * @return 
     */
    public E getStart() {
        return (E) start;
    }
    
    /**
     * If a goal is set then it returns it else it returns null
     * 
     * @return the goal
     */
    public E getGoal() {
        return (E) goal;
    }
    
    /**
     * Generates the map and the nodes' neighbors by calling 
     * the function <code>generateMap()</code> followed by
     * <code>generateNeighbors()</code>
     */
    protected final void generateGrid() {
        generateMap();
        generateNeighbors();
    }
    
    /**
     * Send an action to do in all nodes in the map one by one
     * 
     * @param action action to do in all nodes individually
     */
    public void forAllNodes(Consumer<Node> action) {
        for (Node n : map) {
            action.accept( n );
        }
    }
    
    /**
     * Generate the map of nodes that represents the "world",
     * instancing various <code>Node</code> objects
     */
    protected abstract void generateMap();
    
    /**
     * Generate the neighbors of all nodes in the map
     */
    protected abstract void generateNeighbors();
    /**
     * Generates the neighbors of the current node and receives
     * the dimensional indexes of where the node is situated
     * 
     * @param node Current <code>Node</code> to set the parents of
     * @param dimIndexes Dimensional indexes of the <code>Node</code>
     */
    protected abstract void generateNeighbors( Node node, int ...dimIndexes );

    /**
     * Gets the distance between the <code>Node</code> instances
     * 
     * @param p <code>Node</code> to find distance from
     * @param q <code>Node</code> to find distance to
     * @return distance between <code>Node</code> p and <code>Node</code> q
     */
    public abstract double getDistance(Node p, Node q);
    /**
     * Calculates and returns the heuristics for the given
     * <code>Node</code><br>
     * Heuristics is gotten from getting the distance of the
     * given Node to the goal
     * 
     * @param node <code>Node</code> to calculate heuristics of
     * @return Heuristics of given <code>Node</code> instance
     */
    public abstract double getHeuristics(Node node);
    
    /**
     * Get the 1 dimensional index of the dimensional indexes
     * 
     * @param dimIndexes dimensional indexes
     * @return 1D array index
     */
    protected abstract int getIndex(int... dimIndexes);

    /**
     * Helper class to help transition between a node's cardinal position
     * and index position in the "world"
     */
    public class NodeIndexPosConverter implements Serializable {

        private final double seperation;
        private final double offset;

        /**
         * Instance NIPC with the given seperation and offset
         * values
         * 
         * @param seperation Node seperation
         * @param offset Node offset
         */
        public NodeIndexPosConverter( int seperation, int offset ) {
            this.seperation = seperation;
            this.offset = offset;
        }
        /**
         * Instance NIPC with seperation and offset equal to the
         * single value given
         * 
         * @param value Node seperation and offset
         */
        public NodeIndexPosConverter(int value) {
            this(value, value);
        }
        
        /** @return Node seperation */
        public double getSeperation() {
            return seperation;
        }
        /** @return Node offset */
        public double getOffset() {
            return offset;
        }

        /**
         * Transform dimensional indexes into coordinate positions
         * using the set seperation and offset
         * 
         * @param indexes dimensional indexes
         * @return coordinate positions for the given dimensional indexes
         */
        public double[] toMapPosition( int... indexes ) {
            double[] positions = new double[indexes.length];
            for(int i = 0; i < indexes.length; i++) {
                positions[i] = indexes[i] * seperation + offset;
            }
            return positions;
        }
        
        /**
         * Transform coordinate positions into dimensional indexes
         * using the set seperation and offset
         * 
         * @param positions coordinate position
         * @return supposed index in the map array
         */
        public int toMapIndex( double... positions ) {
            int[] indexes = new int[positions.length];
            for(int i = 0; i < positions.length; i++) {
                indexes[i] = (int) ((positions[i] - offset) / seperation);
            }
            return getIndex(indexes);
        }
    }
    
    /**
     * Add all the given values together
     * 
     * @param values values to add
     * @return sum of values
     */
    private static double add(double ...values) {
        if (values == null) {
            return 0;
        }
        double sum = 0;
        for(double val : values) {
            sum += val;
        }
        return sum;
    }
    /**
     * Formula for calculating the eucledian distance between two points.
     * Figuring the distance to the second point from the first point
     * 
     * @param p first point
     * @param q second point
     * @return eucledian distance between points p and q
     * @throws IllegalArgumentException if the points are of 0 dimensions or they aren't of the same dimensional plane
     */
    public static double EucledianDist(double[] p, double[] q) {
        if (p.length == 0 || q.length == 0) throw new IllegalArgumentException("Points can't be of 0 dimensions");
        if (p.length != q.length) throw new IllegalArgumentException("Points can't be of different dimensions");
        double[] difs = new double[p.length];
        for(int i = 0; i < p.length; i++) {
            difs[i] = Math.pow( q[i] - p[i], 2);
        }
        return Math.sqrt( add(difs) );
    }
    /**
     * Formula for calculating the manhattan distance between two points.
     * Figuring the distance to the second point from the first point,
     * ignoring the posibility of direct diagonals
     * 
     * @param p first point
     * @param q second point
     * @return manhattan distance between points p and q
     * @throws IllegalArgumentException if the points are of 0 dimensions or they aren't of the same dimensional plane
     */
    public static double ManhattanDist(double[] p, double[] q) {
        if (p.length != q.length) throw new IllegalArgumentException("Points can't be of different dimensions");
        double[] difs = new double[p.length];
        for(int i = 0; i < p.length; i++) {
            difs[i] = Math.abs( p[i] - q[i]);
        }
        return add(difs);
    }
}
