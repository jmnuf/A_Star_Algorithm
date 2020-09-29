
package org.nglr.astar.twodim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import org.nglr.astar.Node;

/**
 * A* node that keeps its position in a two-dimensional plane
 *
 * @author J
 * @see Node
 */
public class Node2D implements Node, Serializable {
    
    private double x, y;
    private boolean passable;
    private final boolean diagonals;
    private HashMap<Neighborings, Node> neighbors;
    private Node parent;
    
    /**
     * Instance a new Node2D that's passable at position
     * x, y and holds diagonal neighbors if <code>diagonals</code>
     * is true
     * 
     * @param x Node X-position
     * @param y Node Y-position
     * @param diagonals holds diagonal neighbors
     */
    public Node2D(double x, double y, boolean diagonals) {
        this(x, y, diagonals, true);
    }
    /**
     * Instance a new Node2D that's at position
     * x, y and holds diagonal neighbors if <code>diagonals</code>
     * is true and is passable if <code>passable</code> is true
     * 
     * @param x Node X-position
     * @param y Node Y-position
     * @param diagonals holds diagonal neighbors
     * @param passable Node is passable
     */
    public Node2D(double x, double y, boolean diagonals, boolean passable) {
        this.x = x;
        this.y = y;
        this.passable = passable;
        this.diagonals = diagonals;
        neighbors = new HashMap<>();
        setupNeighbors();
    }
    
    /**
     * Instance a new Node2D using another Node2D as reference,
     * basically creating a copy of the passed Node2D without the parent
     * 
     * @param node Node2D to copy data from
     */
    public Node2D(Node2D node) {
        this(node.getX(), node.getY(), node.usesDiagonals(), node.isPassable());
    }
    
    /**
     * Set the possible parents of this node
     * 
     */
    private void setupNeighbors() {
        for(Neighborings n : Neighborings.get2DNeighborings( diagonals )) {
            neighbors.put( n, null );
        }
    }
    
    @Override
    public Node[] getPath() {
        ArrayList<Node> path = new ArrayList<>();
        Node temp = this;
        while ( temp.getParent() != null ) {
            path.add( temp );
            temp = temp.getParent();
        }
        if (  ! path.contains( temp ) ) {
            path.add( temp );
        }
        Node[] totalPath = new Node[path.size()];
        path.toArray( totalPath );
        return totalPath;
    }
    
    @Override
    public void setParent(Node n) {
        parent = n;
    }
    
    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public double[] getPos() {
        return new double[] { x, y };
    }
    
    /**
     * @return Node's X-position
     */
    public double getX() {
        return x;
    }
    
    /**
     * @return Node's Y-position
     */
    public double getY() {
        return y;
    }

    @Override
    public void setPos( double... pos ) {
        if (pos.length >= 2) {
            x = pos[0];
            y = pos[1];
        } else if (pos.length == 1) {
            x = pos[0];
        }
    }

    @Override
    public void setNeighbor( Neighborings type, Node n ) {
        neighbors.put( type, n );
    }
    @Override
    public Node getNeighbor(Neighborings type) {
        return neighbors.get( type );
    }
    @Override
    public Node[] getNeighbors() {
        Node[] nbs = new Node[neighbors.size()];
        int i = 0;
        for(Neighborings type : neighbors.keySet()) {
            nbs[i] = neighbors.get( type );
            i ++;
        }
        return nbs;
    }
    @Override
    public void forAllNeighbors(Consumer<Node> action) {
        neighbors.values().forEach( action );
    }

    @Override
    public boolean isPassable() {
        return passable;
    }
    
    @Override
    public boolean usesDiagonals() {
        return diagonals;
    }

}
