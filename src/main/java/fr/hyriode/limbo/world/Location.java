package fr.hyriode.limbo.world;

/**
 * Created by AstFaster
 * on 08/04/2023 at 21:47
 */
public class Location {

    private double x;
    private double y;
    private double z;

    public Location(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj.getClass() != this.getClass()) {
            return false;
        } else {
            final Location other = (Location) obj;

            return other.getX() == this.x && other.getY() == this.y && other.getZ() == this.z;
        }
    }

}
