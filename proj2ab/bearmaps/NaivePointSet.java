package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet{

    List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {
        double mindistance = Double.MAX_VALUE;
        Point tem = new Point(x, y);
        Point point = null;
        for (Point p: points) {
            double d = Point.distance(p,tem);
            if (d<mindistance) {
                point = p;
                mindistance = d;
            }
        }
        return point;
    }
}
