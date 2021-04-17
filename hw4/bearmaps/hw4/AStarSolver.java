package bearmaps.hw4;


import bearmaps.proj2ab.ArrayHeapMinPQ;
import bearmaps.proj2ab.DoubleMapPQ;
import bearmaps.proj2ab.ExtrinsicMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex>{
    private SolverOutcome solverOutcome;
    private double timespend;
    private double solutionWeight;
    private List<Vertex> solution;
    private int numStatesExplored;
    private Map<Vertex,Double> distanceToStart;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        distanceToStart = new HashMap<>();
        solution = new ArrayList<>();
        numStatesExplored = 0;
        Stopwatch sw = new Stopwatch();
        ExtrinsicMinPQ<Vertex> pq = new DoubleMapPQ<>();
        pq.add(start,input.estimatedDistanceToGoal(start,end));
        distanceToStart.put(start,0.0);
        while (pq.size() != 0) {
//            double time = sw.elapsedTime();
//            if (time > timeout) {
//                solverOutcome = SolverOutcome.TIMEOUT;
//                return;
//            }
            Vertex newStart = pq.removeSmallest();
            numStatesExplored++;
            solution.add(newStart);
            if (newStart.equals(end)) {
                timespend = sw.elapsedTime();
                solverOutcome = SolverOutcome.SOLVED;
                return;
            }
            List<WeightedEdge<Vertex>> n = input.neighbors(newStart);
            for (WeightedEdge<Vertex> w: n) {
                Vertex v = w.to();
                if (distanceToStart.get(v) == null) {
                    distanceToStart.put(v,Double.MAX_VALUE);
                }
                if ((distanceToStart.get(w.from())+w.weight()) < distanceToStart.get(v)) {
                    distanceToStart.put(v,(distanceToStart.get(w.from())+w.weight()));
                    double vweight = distanceToStart.get(v) + input.estimatedDistanceToGoal(v, end);
                    if (pq.contains(v)) {
                        pq.changePriority(v,vweight);
                    } else {
                        pq.add(v, vweight);
                    }
                }
            }
        }

        timespend = sw.elapsedTime();
        if (timespend > timeout) {
            solverOutcome = SolverOutcome.TIMEOUT;
        } else {
            solverOutcome = SolverOutcome.UNSOLVABLE;
        }

    }

    @Override
    public SolverOutcome outcome() {
        return solverOutcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return 0;
    }

    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return timespend;
    }
}
