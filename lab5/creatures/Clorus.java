package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;

public class Clorus extends Creature {
    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /**
     * creates plip with energy equal to E.
     */
    public Clorus(double e) {
        super("clorus");
        r = 34;
        b = 231;
        g = 0;
        energy = e;
    }

    @Override
    public Color color() {
        return color(r, g, b);
    }

    @Override
    public void move() {
        this.energy -= 0.03;
        if (energy<0){
            energy = 0;
        }
    }

    @Override
    public void attack(Creature c) {
        this.energy += c.energy();
    }


    @Override
    public Creature replicate() {
        this.energy = this.energy/2;
        return new Clorus(energy);
    }

    @Override
    public void stay() {
        this.energy -= 0.01;
        if (energy<0){
            energy = 0;
        }
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plips = new ArrayDeque<>();
//        boolean anyClorus = false;
        for (Direction d: Direction.values()) {
            Occupant o = neighbors.get(d);
            if (o!=null && "empty".equals(o.name())){
                emptyNeighbors.add(d);
            } else if ("plip".equals(o.name())) {
                plips.add(d);
            }
        }
        if (emptyNeighbors.isEmpty() && plips.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        }

        if (!plips.isEmpty()) {
            Direction direction1 = randomEntry(plips);
            return new Action(Action.ActionType.ATTACK,direction1);
        }

        if (energy>=1) {
            Direction direction2 = randomEntry(emptyNeighbors);
            return new Action(Action.ActionType.REPLICATE,direction2);
        } else {
            Direction direction2 = randomEntry(emptyNeighbors);
            return new Action(Action.ActionType.MOVE,direction2);
        }

    }
}
