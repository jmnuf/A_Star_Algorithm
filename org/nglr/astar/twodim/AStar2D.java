
package org.nglr.astar.twodim;

import org.nglr.astar.AStar;
import org.nglr.astar.Grid;

/**
 * AStar class that works under a 2D "world" plane using Node2D as points
 * in the "world" plane and by default uses a {@link Grid2D}
 *
 * @author J
 * @see AStar
 * @see Node2D
 * @see Grid2DNoDiagonal
 * @see Grid2D
 */
public class AStar2D extends AStar {

    /**
     * Create a new AStar2D instance with a "world" with a size of 20x20
     */
    public AStar2D() {
        super( new Grid2D(20, 20) );
    }

    /**
     * Instance a new AStar2D object with a size of the given width x the given
     * height
     *
     * @param width "world" width
     * @param height "world" height
     */
    public AStar2D(int width, int height) {
        super( new Grid2D(width, height) );
    }

    /**
     * Create a duplicate instance of an AStar2D instance
     *
     * @param aStar AStar2D instance to duplicate
     */
    public AStar2D(AStar2D aStar) {
        super(aStar.instanceGridCopy());
    }
    
    /**
     * Instance an AStar2D using the given two-dimension 
     * representational grid
     * 
     * @param grid2D 2D Grid to use in the A*
     */
    public AStar2D(BaseGrid2D grid2D) {
        super(grid2D);
    }
    
    /**
     * @return A copy of the Grid instance the AStar class is using
     */
    public Grid instanceGridCopy() {
        if (getGrid().getMap()[0].usesDiagonals()) {
            return new Grid2DNoDiagonal( (Grid2DNoDiagonal) getGrid());
        } else {
            return new Grid2D( (Grid2D) getGrid());
        }
    }

    public static AStar2D createNoDiagonals(int width, int height) {
        return new AStar2D(new Grid2DNoDiagonal(width, height));
    }
}
