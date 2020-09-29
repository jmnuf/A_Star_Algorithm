
package org.nglr.astar.threedim;

import org.nglr.astar.AStar;
import org.nglr.astar.Grid;

/**
 * AStar class that works under a 3D "world" plane using Node3D as points
 * in the "world" plane and by default uses a {@link Grid3D}
 *
 * @author J
 * @see AStar
 * @see Node3D
 * @see Grid3DNoDiagonal
 * @see Grid3D
 */
public class AStar3D extends AStar {
    /**
     * Create a new AStar3D instance with a "world" with a size of
     * 20x20x20
     */
    public AStar3D() {
        super( new Grid3D(20, 20, 20) );
    }
    /**
     * Instance a new AStar2D object with a size of the
     * given width x the given height x the given depth
     *
     * @param width "world" width
     * @param height "world" height
     * @param depth "world" depth
     */
    public AStar3D(int width, int height, int depth) {
        super( new Grid3D(width, height, depth) );
    }
    
    /**
     * Create a duplicate instance of an AStar3D instance
     *
     * @param aStar AStar3D instance to duplicate
     */
    public AStar3D(AStar3D aStar) {
        super(aStar.instanceGridCopy());
    }
    
    public AStar3D(BaseGrid3D g3D) {
        super(g3D);
    }
    
    /**
     * @return A copy of the Grid instance the AStar class is using
     */
    public Grid instanceGridCopy() {
        if (getGrid().getMap()[0].usesDiagonals()) {
            return new Grid3DNoDiagonal( (Grid3DNoDiagonal) getGrid());
        } else {
            return new Grid3D( (Grid3D) getGrid());
        }
    }
    
    public static AStar3D createNoDiagonals(int width, int height, int depth) {
        return new AStar3D(new Grid3DNoDiagonal(width, height, depth));
    }
}
