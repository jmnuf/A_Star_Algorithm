
package org.nglr.astar.threedim;

import java.io.Serializable;
import org.nglr.astar.Node;

/**
 * A {@link org.nglr.astar.Grid} class that manages
 * a three dimensional world using {@link org.nglr.astar.threedim.Node3D}
 * objects
 *
 * @author J
 * @see Grid
 * @see Grid3DNoDiagonal
 * @see Node3D
 */
public class Grid3D extends Grid3DNoDiagonal implements Serializable {

    /**
     * Instance a new <code>Grid3D</code> that holds
     * a "world" with the given width, height and depth
     *
     * @param width the "world" width
     * @param height the "world" height
     * @param depth the "world" depth
     */
    public Grid3D( int width, int height, int depth ) {
        super( width, height, depth );
    }
    
    /**
     * Create a duplicate <code>Grid3D</code> of the
     * passed <code>Grid3D</code> instance
     *
     * @param grid3D Grid3D to duplicate
     */
    public Grid3D(Grid3D grid3D) {
        super(grid3D);
    }

    @Override
    protected Node3D createNode(double x, double y, double z) {
        return new Node3D(x, y, z, true);
    }
    
    @Override
    protected void generateNeighbors( Node n, int ...dimIndexes ) {
        super.generateNeighbors( n, dimIndexes );
        int x = dimIndexes[0];
        int y = dimIndexes[1];
        int z = dimIndexes[2];
        twoDimNeighbors(n, x, y);
        threeDimNeighbors(n, x, y, z);
    }
    
    private void twoDimNeighbors(Node n, int x, int y) {
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
    
    private void threeDimNeighbors(Node n, int x, int y, int z) {
        if (y > 0) {
            if (z > 0) {
                n.setNeighbor( Node.Neighborings.FrontUp, map[getIndex(x, y - 1, z - 1)]);
            } else {
                n.setNeighbor( Node.Neighborings.FrontUp, null);
            }
            if (z < getDepth() - 1) {
                n.setNeighbor( Node.Neighborings.BackUp, map[getIndex(x, y - 1, z + 1)]);
            } else {
                n.setNeighbor( Node.Neighborings.BackUp, null);
            }
        } else {
            n.setNeighbor( Node.Neighborings.FrontUp, null);
            n.setNeighbor( Node.Neighborings.BackUp, null);
        }
        if (x > 0) {
            if (y > 0) {
                if (z > 0) {
                    n.setNeighbor( Node.Neighborings.FrontUpLeft, map[getIndex(x - 1, y - 1, z - 1)]);
                } else {
                    n.setNeighbor( Node.Neighborings.FrontUpLeft, null);
                }
                if (z < getDepth() - 1) {
                    n.setNeighbor( Node.Neighborings.BackUpLeft, map[getIndex(x - 1, y - 1, z + 1)]);
                } else {
                    n.setNeighbor( Node.Neighborings.BackUpLeft, null);
                }
            } else {
                n.setNeighbor( Node.Neighborings.FrontUpLeft, null);
                n.setNeighbor( Node.Neighborings.BackUpLeft, null);
            }
            if (y < getHeight() - 1) {
                if (z > 0) {
                    n.setNeighbor( Node.Neighborings.FrontDownLeft, map[getIndex(x - 1, y - 1, z - 1)]);
                } else {
                    n.setNeighbor( Node.Neighborings.FrontDownLeft, null);
                }
                if (z < getDepth() - 1) {
                    n.setNeighbor( Node.Neighborings.BackDownLeft, map[getIndex(x - 1, y - 1, z + 1)]);
                } else {
                    n.setNeighbor( Node.Neighborings.BackDownLeft, null);
                }
            } else {
                n.setNeighbor( Node.Neighborings.FrontDownLeft, null);
                n.setNeighbor( Node.Neighborings.BackDownLeft, null);
            }
        }
        if (y < getHeight() - 1) {
            if (z > 0) {
                n.setNeighbor( Node.Neighborings.FrontDown, map[getIndex(x, y - 1, z - 1)]);
            } else {
                n.setNeighbor( Node.Neighborings.FrontDown, null);
            }
            if (z < getDepth() - 1) {
                n.setNeighbor( Node.Neighborings.BackDown, map[getIndex(x, y - 1, z + 1)]);
            } else {
                n.setNeighbor( Node.Neighborings.BackDown, null);
            }
        } else {
            n.setNeighbor( Node.Neighborings.FrontDown, null);
            n.setNeighbor( Node.Neighborings.BackDown, null);
        }
        if (x < getWidth() - 1) {
            if (y > 0) {
                if (z > 0) {
                    n.setNeighbor( Node.Neighborings.FrontUpRight, map[getIndex(x + 1, y - 1, z - 1)]);
                } else {
                    n.setNeighbor( Node.Neighborings.FrontUpRight, null);
                }
                if (z < getDepth() - 1) {
                    n.setNeighbor( Node.Neighborings.BackUpRight, map[getIndex(x + 1, y - 1, z + 1)]);
                } else {
                    n.setNeighbor( Node.Neighborings.BackUpRight, null);
                }
            } else {
                n.setNeighbor( Node.Neighborings.FrontUpRight, null);
                n.setNeighbor( Node.Neighborings.BackUpRight, null);
            }
            if (y < getHeight() - 1) {
                if (z > 0) {
                    n.setNeighbor( Node.Neighborings.FrontDownRight, map[getIndex(x + 1, y - 1, z - 1)]);
                } else {
                    n.setNeighbor( Node.Neighborings.FrontDownRight, null);
                }
                if (z < getDepth() - 1) {
                    n.setNeighbor( Node.Neighborings.BackDownRight, map[getIndex(x + 1, y - 1, z + 1)]);
                } else {
                    n.setNeighbor( Node.Neighborings.BackDownRight, null);
                }
            } else {
                n.setNeighbor( Node.Neighborings.FrontDownRight, null);
                n.setNeighbor( Node.Neighborings.BackDownRight, null);
            }
        }
    }
}
