package aoc.solutions.Y2020.day11;

import java.util.HashSet;
import java.util.Objects;

public class Cell {

    private final long row;
    private final long col;
    private Boolean state; //true if empty
    private final Field field;
    private HashSet<Cell> neigbours = new HashSet<>();
    private HashSet<Cell> farNeigbours = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row &&
                col == cell.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    public Cell(long r, long c, Field f) {
        row = r;
        col = c;
        state = true;
        field = f; //even the pawn knows what desk it is on;
    }

    public Cell(long l, long l1) {
        this(l,l1,null);
    }


    public HashSet<Cell> getNeigbours() {
        if (neigbours.isEmpty()) {
            //initialisation, ugly;
            HashSet<Cell> lookup = new HashSet<>();
            lookup.add(new Cell (row-1,col-1));
            lookup.add(new Cell (row-1,col+1));
            lookup.add(new Cell (row-1,col));
            lookup.add(new Cell (row+1,col-1));
            lookup.add(new Cell (row+1,col+1));
            lookup.add(new Cell (row+1,col));
            lookup.add(new Cell (row,col+1));
            lookup.add(new Cell (row,col-1));

            for (Cell target : lookup) {
                if (field.containsKey(target)) neigbours.add(field.get(target));
            }

        }
        return neigbours;
    }

    public HashSet<Cell> getFarNeigbours() {
        if (farNeigbours.isEmpty()) {
            //initialisation, ugly;
            HashSet<Cell> lookup = new HashSet<>();
            lookup.add(field.vectorLookup(this, -1,-1));
            lookup.add(field.vectorLookup(this, -1,1));
            lookup.add(field.vectorLookup(this, -1,0));
            lookup.add(field.vectorLookup(this, 1,-1));
            lookup.add(field.vectorLookup(this, 1,1));
            lookup.add(field.vectorLookup(this, 1,0));
            lookup.add(field.vectorLookup(this, 0,-1));
            lookup.add(field.vectorLookup(this, 0,1));

            lookup.remove(this);

            for (Cell target : lookup) {
                if (field.containsKey(target)) farNeigbours.add(field.get(target));
            }

        }
        return farNeigbours;
    }

    public boolean isEmpty() {
        return state;
    }

    public void changeState() {
        state = !state;
    }

    public long getRow() {
        return row;
    }

    public long getCol() {
        return col;
    }
}
