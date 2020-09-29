package org.nglr.astar.threedim;

import java.io.Serializable;
import org.nglr.astar.Grid;
import org.nglr.astar.Node;

/**
 * A {@link org.nglr.astar.Grid} class that manages
 * a three dimensional world were paths can't be diagonal using 
 * {@link org.nglr.astar.twodim.Node3D} objects
 *
 * @author J
 * @see Grid
 * @see Grid3D
 * @see Node3D
 */
public class Grid3DNoDiagonal extends BaseGrid3D implements Serializable {

    /**
     * the "world" width
     */
    private int width;
    /**
     * the "world" height
     */
    private int height;
    /**
     * the "world" depth
     */
    private int depth;

    /**
     * Instance a new <code>Grid3DNoDiagonal</code> that holds a
     * "world" with the given width, height and depth
     *
     * @param width the "world" width
     * @param height the "world" height
     * @param depth the "world" depth
     */
    public Grid3DNoDiagonal( int width, int height, int depth ) {
        init( width, height, depth );
    }
    
    /**
     * Create a duplicate <code>Grid3DNoDiagonal</code> of the passed
     * <code>Grid3DNoDiagonal</code> instance
     *
     * @param grid3D <code>Grid3DNoDiagonal</code> to duplicate
     */
    public Grid3DNoDiagonal(Grid3DNoDiagonal grid3D) {
        setNIPC(grid3D.getNIPC());
        create(grid3D.getSize());
        for(int i = 0; i < map.length; i++) {
            map[i] = new Node3D( (Node3D) grid3D.getMap()[i]);
        }
        if (grid3D.getStart() != null && grid3D.getGoal() != null) {
            int i = nipc.toMapIndex( grid3D.getStart().getX(), grid3D.getStart().getY());
            int j = nipc.toMapIndex( grid3D.getGoal().getX(), grid3D.getGoal().getY());
            setup(i, j);
        }
    }

    private void init( int w, int h, int d ) {
        setNIPC( new NodeIndexPosConverter( 20 ) );
        create( new int[] {w, h, d} );
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getDepth() {
        return depth;
    }

    /**
     * Creates the "world" with the given width, height and depth
     *
     * @param width  the "world" width
     * @param height the "world" height
     * @param depth  the "world" depth
     */
    public void create( int width, int height, int depth ) {
        create( new int[] {width, height, depth} );
    }

    /**
     * Quickly instance a new {@link Node3D}
     *
     * @param x X position of Node
     * @param y Y position of Node
     * @param z Z position of Node
     *
     * @return new Node3D
     */
    protected Node3D createNode( double x, double y, double z ) {
        return new Node3D( x, y, z, false );
    }

    @Override
    protected void create( int[] size ) {
        width = size[0];
        height = size[1];
        depth = size[2];
        start = null;
        goal = null;

        map = new Node3D[width * height * depth];
        generateGrid();
    }

    @Override
    protected void setup( int[] p, int[] q ) {
        start = map[getIndex(p[0], p[1], p[2])];
        goal = map[getIndex(q[0], q[1], q[2])];
    }

    @Override
    public int[] getSize() {
        return new int[] {width, height, depth};
    }

    @Override
    protected void generateMap() {
        for (int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x ++) {
                for (int z = 0; z < depth; z ++) {
                    double[] coords = nipc.toMapPosition( x, y, z );
                    map[getIndex( x, y )] = createNode( coords[0], coords[1], coords[2] );
                }
            }
        }
    }

    @Override
    protected void generateNeighbors() {
        for (int z = 0; z < depth; z ++) {
            for (int y = 0; y < height; y ++) {
                for (int x = 0; x < width; x ++) {
                    generateNeighbors( map[getIndex( x, y, z )], x, y, z );
                }
            }
        }
    }

    @Override
    protected void generateNeighbors( Node node, int... dimIndexes ) {
        int x = dimIndexes[0];
        int y = dimIndexes[1];
        int z = dimIndexes[2];
        if ( x == 0 ) {
            node.setNeighbor( Node.Neighborings.Left, null );
        } else {
            node.setNeighbor( Node.Neighborings.Left, map[getIndex( x - 1, y, z )] );
        }
        if ( x == width - 1 ) {
            node.setNeighbor( Node.Neighborings.Right, null );
        } else {
            node.setNeighbor( Node.Neighborings.Right, map[getIndex( x + 1, y, z )] );
        }
        if ( y == 0 ) {
            node.setNeighbor( Node.Neighborings.Up, null );
        } else {
            node.setNeighbor( Node.Neighborings.Up, map[getIndex( x, y - 1, z )] );
        }
        if ( y == height - 1 ) {
            node.setNeighbor( Node.Neighborings.Down, null );
        } else {
            node.setNeighbor( Node.Neighborings.Down, map[getIndex( x, y + 1, z )] );
        }
        if ( z == 0 ) {
            node.setNeighbor( Node.Neighborings.Front, null );
        } else {
            node.setNeighbor( Node.Neighborings.Front, map[getIndex( x, y, z - 1 )] );
        }
        if ( z == depth - 1 ) {
            node.setNeighbor(Node.Neighborings.Back, null );
        } else {
            node.setNeighbor(Node.Neighborings.Back, map[getIndex( x, y, z + 1 )] );
        }
    }

    @Override
    public double getDistance( Node p, Node q ) {
        return Grid.EucledianDist( p.getPos(), q.getPos() );
    }

    @Override
    public double getHeuristics( Node node ) {
        return getDistance( node, goal );
    }

    @Override
    protected int getIndex( int... dimIndexes ) {
        return dimIndexes[0] + width * (dimIndexes[1] + depth * dimIndexes[2]);
    }

    /**
     * Get the 1 dimensional index of the dimensional indexes
     * 
     * @param x X-dimension of the array position
     * @param y Y-dimension of the array position
     * @param z Z-dimension of the array position
     * @return 1D array index
     */
    public int getIndex( int x, int y, int z ) {
        return getIndex( new int[] {x, y, z} );
    }
}
