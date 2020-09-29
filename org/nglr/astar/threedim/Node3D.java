
package org.nglr.astar.threedim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import org.nglr.astar.Node;

/**
 * A* node that keeps its position in a three-dimensional plane
 *
 * @author J
 * @see Node
 */
public class Node3D implements Node, Serializable {
    private double x, y, z;
    private boolean passable;
    private final boolean diagonals;
    private HashMap<Neighborings, Node> neighbors;
    private Node parent;
    
    /**
     * Instance a new Node2D that's at position
     * x, y, z and holds diagonal neighbors if <code>diagonals</code>
     * is true and is passable if <code>passable</code> is true
     * 
     * @param x Node X-position
     * @param y Node Y-position
     * @param z Node Z-position
     * @param diagonals holds diagonal neighbors
     * @param passable Node is passable
     */
    public Node3D(double x, double y, double z, boolean diagonals, boolean passable) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.passable = passable;
        this.diagonals = diagonals;
        neighbors = new HashMap<>();
        setupNeighbors();
    }
    /**
     * Instances a new passable Node3D object at the position z, y, z
     * and holds diagonal neighbors if <code>diagonals</code> is true
     *
     * @param x Node X-position
     * @param y Node Y-position
     * @param z Node Z-position
     * @param diagonals holds diagonal neighbors
     */
    public Node3D(double x, double y, double z, boolean diagonals) {
        this(x, y, z, diagonals, true);
    }
    /**
     * Instance a new Node3D using another Node3D as reference,
     * basically creating a copy of the passed Node3D without the parent
     *
     * @param node Node3D to duplicate
     */
    public Node3D(Node3D node) {
        this(node.getX(), node.getY(), node.getZ(), node.usesDiagonals(), node.isPassable());
    }
    
    /**
     * Set the possible parents of this node
     * 
     */
    private void setupNeighbors() {
        for(Neighborings n : Neighborings.get3DNeighborings( diagonals )) {
            neighbors.put( n, null );
        }
    }

    @Override
    public double[] getPos() {
        return new double[] { x, y, z };
    }

    @Override
    public void setPos( double... pos ) {
        if (pos.length > 2) {
            x = pos[0];
            y = pos[1];
            z = pos[2];
        } else if (pos.length > 1) {
            x = pos[0];
            y = pos[1];
        } else if (pos.length > 0) {
            x = pos[0];
        }
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
    
    /**
     * @return Node's Z-position
     */
    public double getZ() {
        return z;
    }

    @Override
    public boolean isPassable() {
        return passable;
    }

    @Override
    public boolean usesDiagonals() {
        return diagonals;
    }

    @Override
    public void setNeighbor( Neighborings type, Node node ) {
        neighbors.replace( type, node );
    }

    @Override
    public Node getNeighbor( Neighborings type ) {
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
    public void forAllNeighbors( Consumer<Node> action ) {
        neighbors.values().forEach( action );
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
    public Node getParent() {
        return parent;
    }

    @Override
    public void setParent( Node node ) {
        parent = node;
    }
}
