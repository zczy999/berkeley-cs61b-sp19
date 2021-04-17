package bearmaps.proj2ab;

import java.util.ArrayList;
import java.util.List;

public class KDTree {
    private Node root;

    public KDTree(List<Point> points) {
        root = new Node(points.get(0), 0);
        for (int i = 1; i < points.size(); i++) {
            put(root, points.get(i),0);
        }
    }

    private Node put(Node x, Point p, int d) {
        if (x == null) {
            return new Node(p, d);
        }
        int comp;
        if (d == 0) comp = Double.compare(x.point.getX(),p.getX());
        else comp = Double.compare(x.point.getY(),p.getY());
        if (comp > 0) {
            x.left = put(x.left, p, ++d%2);
        } else if (comp == 0) {
            if (!p.equals(x.point)) x.right = put(x.right, p ,++d%2);
        } else {
            x.right = put(x.right, p ,++d%2);
        }
        return x;
    }

    public Point nearest(double x, double y) {
        Node node = nearest(root, new Point(x, y), root);
        return node.point;
    }

    private Node nearest(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }
        if (Point.distance(n.point, goal) < Point.distance(n.point, best.point)) {
            best = n;
        }
        int comp;
        if (n.direction == 0) comp = Double.compare(n.point.getX(),goal.getX());
        else comp = Double.compare(n.point.getY(),goal.getY());
        Node good,bad;
        if (comp > 0) {
            good = n.left;
            bad = n.right;
        } else {
            good = n.right;
            bad = n.left;
        }
        best = nearest(good, goal, best);
        double d;
        if (n.direction == 0) d = Math.pow((best.point.getX()-goal.getX()), 2);
        else d = Math.pow((best.point.getY()-goal.getY()), 2);
        if (Point.distance(n.point, best.point)<d) {
            best = nearest(bad, goal, best);
        }
        return best;
    }

    public static void main(String[] args) {
        Point p1 = new Point(2,3);
        Point p2 = new Point(4,2);
        Point p3 = new Point(4,5);
        Point p4 = new Point(3,3);
        Point p5 = new Point(1,3);
        List<Point> pointList = new ArrayList<>();
        pointList = List.of(p1,p2,p3,p4,p5);
        KDTree kdTree = new KDTree(pointList);
        kdTree.nearest(0,0);
    }

    private class Node {
        private Point point;
        private int direction;
        private Node left, right;

        public Node(Point p,int direction) {
            this.point = p;
            this.direction = direction;
        }
    }
}
