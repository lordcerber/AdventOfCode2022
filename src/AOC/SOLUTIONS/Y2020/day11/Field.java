package aoc.solutions.Y2020.day11;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Field extends HashMap<Cell, Cell> {

    private final long height;
    private final long width;

    public Field(List<String> input, String key) {
        int l = 0;
        height = input.size();
        long wid = 0;
        for (String line: input) {
            if (wid < line.length()) wid=line.length();
            for (int i = 0; i<line.length(); i++) {
                if (String.valueOf(line.charAt(i)).equals(key)) addCell(l,i);
            }
            l++;
        }
        width=wid;
    }

    private void addCell(long l, long i) {
        Cell c = new Cell(l, i, this);
        put(c,c);
    }

    public long advanceTimeRule1() {
        HashSet<Cell> flips = new HashSet<>();
        //find who changes
        for (Cell cell : keySet()) {
            if (cell.isEmpty() && cell.getNeigbours().stream().allMatch(c -> c.isEmpty())) flips.add(cell);
            if (!cell.isEmpty() && cell.getNeigbours().stream().filter(c -> !c.isEmpty()).count()>=4) flips.add(cell);
            //otherwise nothing changes
        }
        for (Cell cell : flips) {
            cell.changeState();
        }
        return flips.size();
    }

    public long advanceTimeRule2() {
        HashSet<Cell> flips = new HashSet<>();
        //find who changes
        for (Cell cell : keySet()) {
            if (cell.isEmpty() && cell.getFarNeigbours().stream().allMatch(c -> c.isEmpty())) flips.add(cell);
            if (!cell.isEmpty() && cell.getFarNeigbours().stream().filter(c -> !c.isEmpty()).count()>=5) flips.add(cell);
            //otherwise nothing changes
        }
        for (Cell cell : flips) {
            cell.changeState();
        }
        return flips.size();
    }

    public long countActiveCells() {
        return this.keySet().stream().filter(c -> !c.isEmpty()).count();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append(System.lineSeparator());
        for (long r = 0; r<height; r++) {
            for (long c = 0; c<width; c++) {
                if (containsKey(new Cell(r,c))) {
                    if (get(new Cell(r, c)).isEmpty()) {
                        out.append("L");
                    } else {
                        out.append("#");
                    }
                } else {
                    out.append(".");
                }
            }
            out.append(System.lineSeparator());
        }
        return out.toString();
    }

    public Cell vectorLookup(Cell startCell, int deltaRow, int deltaColumn) {
        Cell temp = startCell;
        long row = startCell.getRow();
        long col = startCell.getCol();
        //within borders, loop
        while (row>=0 && col>=0 && row<height && col <width) {
            row += deltaRow;
            col += deltaColumn;
            if (containsKey(new Cell(row, col))) {
                temp = get( new Cell(row, col));
                break; //the first seat to see
            }
        }
        return temp;
    }
}
