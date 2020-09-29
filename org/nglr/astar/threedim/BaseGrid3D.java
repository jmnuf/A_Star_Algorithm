/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nglr.astar.threedim;

import org.nglr.astar.Grid;

/**
 * Label class for 3D Grids
 *
 * @author J
 */
public abstract class BaseGrid3D extends Grid<Node3D> {
    
    /**
     * @return the "world" width
     */
    public abstract int getWidth();
    
    /**
     * @return the "world" height
     */
    public abstract int getHeight();
    
    /**
     * @return the "world" depth
     */
    public abstract int getDepth();
}
