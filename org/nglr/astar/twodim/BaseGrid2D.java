
package org.nglr.astar.twodim;

import org.nglr.astar.Grid;

/**
 * Label class for 2D Grids
 *
 * @author J
 * @see Grid
 * @see Gri2DNoDiagonal
 * @see Grid2D
 */
public abstract class BaseGrid2D extends Grid<Node2D> {

    /**
     * @return the "world" width
     */
    public abstract int getWidth();
    
    /**
     * @return the "world" height
     */
    public abstract int getHeight();
    
}
