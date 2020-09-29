package org.nglr.astar.twodim;

import java.io.Serializable;
import org.nglr.astar.Grid;
import org.nglr.astar.Node;

/**
 * A {@link org.nglr.astar.Grid} class that manages
 * a two dimensional world were paths can't be diagonal using 
 * {@link org.nglr.astar.twodim.Node2D} objects
 *
 * @author J
 * @see Grid
 * @see Grid2D
 * @see Node2D
 */
public class Grid2DNoDiagonal extends BaseGrid2D implements Serializable {

    /** the "world" width */
    private int width;
    /** the "world" height */
    private int height;

    /**
     * Instance a new <code>Grid2DNoDiagonal</code> that holds a
     * "world" with the given width and height
     * 
     * @param width the "world" width
     * @param height the "world" height
     */
    public Grid2DNoDiagonal( int width, int height ) {
        init(width, height);
    }
    
    /**
     * Create a duplicate <code>Grid2DNoDiagonal</code> of the passed
     * <code>Grid2DNoDiagonal</code> instance
     * 
     * @param grid2D <code>Grid2DNoDiagonal</code> to duplicate
     */
    public Grid2DNoDiagonal(Grid2DNoDiagonal grid2D) {
        setNIPC(grid2D.getNIPC());
        create(grid2D.getSize());
        for(int i = 0; i < map.length; i++) {
            map[i] = new Node2D( (Node2D) grid2D.getMap()[i]);
        }
        if (grid2D.getStart() != null && grid2D.getGoal() != null) {
            int i = nipc.toMapIndex( grid2D.getStart().getX(), grid2D.getStart().getY());
            int j = nipc.toMapIndex( grid2D.getGoal().getX(), grid2D.getGoal().getY());
            setup(i, j);
        }
    }
    
    
    private void init(int w, int h) {
        nipc = new BasicNIPC();
        create(new int[] {w, h});
    }
    
    
    @Override
    public int getWidth() {
        return width;
    }
    
    @Override
    public int getHeight() {
        return height;
    }
    
    /**
     * Creates the "world" with the given width and height
     * 
     * @param width the "world" width
     * @param height the "world" height
     */
    public void create(int width, int height) {
        create(new int[] { width, height });
    }

    @Override
    protected void create( int[] size ) {
        width = size[0];
        height = size[1];
        start = null;
        goal = null;

        map = new Node2D[this.width * this.height];
        generateGrid();
    }

    @Override
    protected void setup( int[] p, int[] q ) {
        start = map[getIndex( p[0], p[1] )];
        goal = map[getIndex( q[0], q[1] )];
    }

    @Override
    protected void generateMap() {
        for (int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x ++) {
                double[] coords = nipc.toMapPosition( x, y );
                map[getIndex( x, y )] = createNode( coords[0], coords[1] );
            }
        }
    }
    
    /**
     * Quickly instance a new {@link Node2D}
     * 
     * @param x X position of Node
     * @param y Y position of Node
     * @return new Node2D
     */
    protected Node2D createNode(double x, double y) {
        return new Node2D(x, y, false);
    }

    @Override
    protected void generateNeighbors() {
        for (int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x ++) {
                generateNeighbors( map[getIndex( x, y )], x, y );
            }
        }
    }

    @Override
    protected void generateNeighbors( Node node, int ...dimIndexes ) {
        int x = dimIndexes[0];
        int y = dimIndexes[1];
        if ( x == 0 ) {
            node.setNeighbor( Node.Neighborings.Left, null );
        } else {
            node.setNeighbor( Node.Neighborings.Left, map[getIndex( x - 1, y )] );
        }
        if ( x == width - 1 ) {
            node.setNeighbor( Node.Neighborings.Right, null );
        } else {
            node.setNeighbor( Node.Neighborings.Right, map[getIndex( x + 1, y )] );
        }
        if ( y == 0 ) {
            node.setNeighbor( Node.Neighborings.Up, null );
        } else {
            node.setNeighbor( Node.Neighborings.Up, map[getIndex( x, y - 1 )] );
        }
        if ( y == height - 1 ) {
            node.setNeighbor( Node.Neighborings.Down, null );
        } else {
            node.setNeighbor( Node.Neighborings.Down, map[getIndex( x, y + 1 )] );
        }
    }

    @Override
    public double getDistance(Node p, Node q) {
        return Grid.ManhattanDist( p.getPos(), q.getPos() );
    }
    @Override
    public double getHeuristics( Node n ) {
        return Grid.ManhattanDist( n.getPos(), goal.getPos() );
    }

    @Override
    protected int getIndex( int... params ) {
        return params[0] + params[1] * width;
    }

    /**
     * Get the 1 dimensional index of the dimensional indexes
     * 
     * @param x X-dimension of the array position
     * @param y Y-dimension of the array position
     * @return 1D array index
     */
    public int getIndex( int x, int y ) {
        return getIndex( new int[] {x, y} );
    }

    @Override
    public int[] getSize() {
        return new int[] {width, height};
    }

    /**
     * A <code>NodeIndexPosConverter</code>  based class that simply
     * has a default value of 20
     */
    public class BasicNIPC extends NodeIndexPosConverter {

        /**
         * Instance NIPC with the given seperation and offset values
         * 
         * @param seperation Node seperation
         * @param offset Node offset
         */
        public BasicNIPC( int seperation, int offset ) {
            super( seperation, offset );
        }
        /**
         * Instance NIPC with seperation and offset equal to the
         * single value given
         * 
         * @param val Node seperation and offset
         */
        public BasicNIPC(int val) {
            super(val);
        }
        /**
         * Instance NIPC with seperation and offset equal to 20
         * 
         */
        public BasicNIPC() {
            super(20);
        }
    }
}
