
package org.nglr.astar;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * Basic A* algorithm master class.<br>
 * Holds the calculation methods that use a
 * {@link org.nglr.astar.Grid <code>Grid</code>} instance
 * for the configurations of start point and end point and
 * possible points to use (the world where the calculations
 * are to happen in)
 *
 * @author J
 * @version 1.0
 * @see AStar2D
 * @see Node
 * @see Grid
 */
public class AStar {
    private Grid grid;
    private Node[] path, bestTry;
    private Node cur;
    private boolean tested;
    private boolean solvable;
    private boolean calculating;
    
    private Map<Node, Double> fScore = null;
    private Map<Node, Double> gScore = null;
    private Queue<Node> openSet = null;
    
    /**
     * Instance <code>AStar</code> passing in a <code>Grid</code>
     * as the "world" that will be used
     * 
     * @param grid the "world" to operate in
     * @throws IllegalArgumentException if <code>grid</code> is null
     */
    public AStar( Grid grid ) {
        if (grid == null) {
            throw new IllegalArgumentException("The \"grid\" can't be null");
        }
        this.grid = grid;
        path = null;
        bestTry = null;
        tested = false;
        solvable = true;
        calculating = false;
    }
    
    /**
     * Setup the start and end points to try to find the optimal
     * path between. The arrays must have a length equal to the
     * amount of dimensions of the "world" and the contents are
     * actually the index of each dimension.<br><br>
     * <b>Values use:</b><br><br>
     * <code>Node startNode = map[ start[0] ][ start[1] ];</code><br>
     * <code>Node endNode = map[ end[0] ][ end[1] ];</code>
     * 
     * @param start dimensional indexes for start position
     * @param end dimensional indexes for end position
     */
    public void prepare(int[] start, int[] end ) {
        grid.setup( start, end );
        setupForCalculation();
    }
    
    private void setupForCalculation() {
        tested = false;
        path = null;
        bestTry = null;
        path = null;
        solvable = false;
        calculating = false;
        if (openSet == null) {
            openSet = new LinkedList<>();
        } else {
            openSet.clear();
        }
        gScore = new HashMap<>();
        fScore = new HashMap<>();
        grid.forAllNodes(new Consumer<Node>() {
            @Override
            public void accept( Node n ) {
                gScore.put( n, Double.POSITIVE_INFINITY );
                fScore.put( n, Double.POSITIVE_INFINITY );
            }
        });
        Node start = grid.getStart();
        openSet.add( start );
        gScore.replace( start, 0.0 );
        fScore.replace( start, grid.getHeuristics(start ) );
        cur = null;
    }
    
    /**
     * With the given the data it calculates one step of the process
     * of finding the optimal path. <br>
     * Useful if you want to slowly do the process for visualization
     * purposes if you want to do it all instantly use
     * the <code>calculateAll</code> function
     * 
     * @throws NullPointerException if start and end points not defined
     */
    public void calculateStep() {
        if (!openSet.isEmpty() && !tested) {
            calculating = true;
            cur = lowestFScore();
            if ( cur == grid.getGoal() ) {
                path = cur.getPath();
                bestTry = cur.getPath();
                tested = true;
                solvable = true;
                calculating = false;
                return;
            }

            openSet.remove( cur );
            for (Node neighbor : cur.getNeighbors()) {
                if ( neighbor == null || !neighbor.isPassable() ) {
                    continue;
                }
                double tentativeScore = gScore.get( cur ) + grid.getDistance(cur, neighbor );
                if ( tentativeScore < gScore.get( neighbor ) ) {
                    neighbor.setParent( cur );
                    gScore.replace( neighbor, tentativeScore );
                    fScore.replace( neighbor, gScore.get( neighbor ) + grid.getHeuristics(neighbor ) );
                    if (  ! openSet.contains( neighbor ) ) {
                        openSet.add( neighbor );
                    }
                }
            }
        } else if (!tested) {
            bestTry = lowestFScore().getPath();
            tested = true;
            solvable = false;
            calculating = false;
        }
    }
    
    /**
     * Calculate in one go the best path to the desired point from the set start.
     * Will return the optimal path to the desired point or the best try for reaching
     * it.
     * 
     * @return best path
     * @throws NullPointerException if there's no start and/or goal set
     */
    public Node[] calculateAll() {
        calculating = true;
        tested = false;
        while (calculating && !tested) {
            calculateStep();
        }
        if (solvable) {
            return path;
        } else {
            return bestTry;
        }
    }
    
    private Node lowestFScore() {
        Node min = null;
        for (Node n : openSet) {
            if ( min == null ) {
                min = n;
            } else if ( fScore.get( n ) < fScore.get( min ) ) {
                min = n;
            }
        }
        return min;
    }

    /**
     * Get the "world" where the operations are taking placing
     * 
     * @return the "world" as a <code>Node[]</code>
     */
    public Node[] getMap() {
        return grid.getMap();
    }
    
    /**
     * Get the <code>Grid</code> instance
     * that is being used to operate as the "world"
     * 
     * @return the "world"
     */
    public Grid getGrid() {
        return grid;
    }
    
    /**
     * The best possible path to get to end point from the start point
     * 
     * @return <code>null</code> if the path has not been calculated or is unsolvable
     */
    public Node[] getPath() {
        return path;
    }
    
    /**
     * The best calculated path to try to get the end point from the
     * start point even if the end point is unreachable
     * 
     * @return <code>null</code> if the path has not been calculated
     */
    public Node[] getBestTry() {
        return bestTry;
    }
    
    /**
     * Check if the current settings have been tested.
     * Helps to know if it has fully calculated the best possible
     * path
     * 
     * @return 
     */
    public boolean isTested() {
        return tested;
    }
    
    /**
     * Use if <code>isTested</code> returns true to make
     * sure the result is reliable.<br>
     * Checks if it's possible to reach the end point from
     * the start point in the current "world"
     * 
     * @return 
     */
    public boolean isSolvable() {
        return solvable;
    }
}
