
package org.nglr.astar.twodim;

import java.io.Serializable;
import org.nglr.astar.Grid;
import org.nglr.astar.Node;

/**
 * A {@link org.nglr.astar.Grid} class that manages
 * a two dimensional world using {@link org.nglr.astar.twodim.Node2D}
 * objects
 *
 * @author J
 * @see Grid
 * @see Grid2DNoDiagonal
 * @see Node2D
 * @see AStar2D
 */
public class Grid2D extends Grid2DNoDiagonal implements Serializable {

    /**
     * Instance a new <code>Grid2D</code> that holds a
     * "world" with the given width and height
     * 
     * @param width the "world" width
     * @param height the "world" height
     */
    public Grid2D( int width, int height ) {
        super( width, height );
    }
    
    /**
     * Create a duplicate instance of the passed <code>Grid2D</code> instance
     * 
     * @param grid2D <code>Grid2D</code> instance to duplicate
     */
    public Grid2D(Grid2D grid2D) {
        super(grid2D);
    }
     
    @Override
    protected Node2D createNode(double x, double y) {
        return new Node2D(x, y, true);
    }

    @Override
    protected void generateNeighbors( Node n, int ...dimIndexes ) {
        super.generateNeighbors( n, dimIndexes );
        int x = dimIndexes[0];
        int y = dimIndexes[1];
        if (x > 0 && y > 0) {
            n.setNeighbor( Node.Neighborings.UpLeft, map[getIndex(x - 1, y - 1)]);
        }
        if (x < getWidth() - 1 && y > 0) {
            n.setNeighbor( Node.Neighborings.UpRight, map[getIndex(x + 1, y - 1)]);
        }
        if (x > 0 && y < getHeight() - 1) {
            n.setNeighbor( Node.Neighborings.DownLeft, map[getIndex(x - 1, y + 1)]);
        }
        if (x < getWidth() - 1 && y < getHeight() - 1) {
            n.setNeighbor( Node.Neighborings.DownRight, map[getIndex(x + 1, y + 1)]);
        }
    }

    @Override
    public double getHeuristics( Node n ) {
        return Grid.EucledianDist(n.getPos(), goal.getPos() );
    }

}
