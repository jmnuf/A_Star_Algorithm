/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nglr.astar;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Represents a point in space for A* usage
 *
 * @author J
 */
public interface Node extends Serializable {
    /**
     * @return Node's position
     */
    public abstract double[] getPos();
    /**
     * Set the Node's position
     * 
     * @param pos new position
     */
    public abstract void setPos(double ...pos);
    
    /**
     * @return if Node is passable
     */
    public boolean isPassable();
    public boolean usesDiagonals();
    
    /**
     * Set the neighbor of this node to the neighboring position
     * 
     * @param type neighboring position
     * @param node neighbor
     */
    public void setNeighbor(Neighborings type, Node node);
    /**
     * Get the neighbor of the node that's of the given neighboring
     * type
     * 
     * @param type Neighboring position
     * @return Nieghbor to the neighboring position
     */
    public Node getNeighbor(Neighborings type);
    /**
     * @return All of this node's neighbors
     */
    public Node[] getNeighbors();
    /**
     * Perform an action to all neighbors one by one
     * 
     * @param action action to perform
     */
    public void forAllNeighbors(Consumer<Node> action);
    
    /**
     * @return the path take to reach this node
     */
    public Node[] getPath();
    
    /**
     * @return Node from where this one was reached from
     */
    public Node getParent();
    /**
     * Set the parent node which is the node use to acces this one
     * 
     * @param node node used to access current one
     */
    public void setParent(Node node);
    
    /**
     * Possible neighboring positions for a node
     */
    public enum Neighborings {
        /*/--- Values ---/*/
            // 2D No Diagonals
            Up, Down,
            Right, Left,
            // 2D Diagonals
            UpRight, UpLeft,
            DownRight, DownLeft,
            // 3D No Diagonals
            Front, Back,
            FrontUp, FrontDown,
            // 3D Diagonals
            FrontLeft, FrontRight,
            FrontUpRight, FrontDownRight,
            FrontUpLeft, FrontDownLeft,
            BackUp, BackDown,
            BackLeft, BackRight,
            BackUpRight, BackDownRight,
            BackUpLeft, BackDownLeft,
        ;
        /**
         * Get all Neighborings for 2D
         * 
         * @param withDiagonals include diagonals like up-right
         * @return 2D Neighborings
         */
        public static Neighborings[] get2DNeighborings(boolean withDiagonals) {
            Neighborings[] set;
            if (withDiagonals) {
                set = new Neighborings[] {
                    Up, Down,
                    Right, Left,
                    UpRight, UpLeft,
                    DownRight, DownLeft
                };
            } else {
                set = new Neighborings[] {
                    Up, Down,
                    Right, Left
                };
            }
            return set;
        }
        
        /**
         * Get all Neighborings for 3D
         * 
         * @param withDiagonals include diagonals forward-right and backward-down-right
         * @return 3D Neighborings
         */
        public static Neighborings[] get3DNeighborings(boolean withDiagonals) {
            Neighborings[] set;
            if (!withDiagonals) {
                set = new Neighborings[] {
                    Up, Down,
                    Right, Left,
                    Front, Back
                };
            } else {
                set = values();
            }
            return set;
        }
    }
}
