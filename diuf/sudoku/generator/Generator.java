/*
 * Project: Sudoku Explainer
 * Copyright (C) 2006-2007 Nicolas Juillerat
 * Available under the terms of the Lesser General Public License (LGPL)
 */
package diuf.sudoku.generator;

import java.util.*;

import diuf.sudoku.*;
import diuf.sudoku.solver.*;
import diuf.sudoku.solver.checks.*;


public class Generator {

    private final BruteForceAnalysis analyser = new BruteForceAnalysis(true);
    private boolean isInterrupted = false;


    /**
     * Generate a Sudoku grid matching the given parameters.
     * <p>
     * Depending on the given parameters, the generation can take very
     * long. The implementation actually repeatedly generates random
     * grids with the given symmetries until the difficulty is between
     * the given bounds.
     * @param symmetries the symmetries the resulting grid is allowed to have
     * @param minDifficulty the minimum difficulty of the grid
     * @param maxDifficulty the maximum difficulty of the grid
     * @return the generated grid
     */
    public Grid generate(List<Symmetry> symmetries, double minDifficulty, double maxDifficulty) {
//a     assert !symmetries.isEmpty() : "No symmetries specified";
        Random random = new Random();
        int symmetryIndex = random.nextInt(symmetries.size());
        while (true) {
            // Generate a random grid
            Symmetry symmetry = symmetries.get(symmetryIndex);
            symmetryIndex = (symmetryIndex + 1) % symmetries.size();
            Grid grid = generate(random, symmetry);
            if ( grid == null ) {
                return null;
            }

            if (isInterrupted) {
                System.err.println("Stopped.");
                System.err.flush();
                return null;
            }

            // Analyse difficulty
            Grid copy = new Grid();
            grid.copyTo(copy);
            Solver solver = new Solver(copy);
            solver.rebuildPotentialValues();
            double difficulty = solver.analyseDifficulty(minDifficulty, maxDifficulty);
            String s = "";
            for (int i = 0; i < 256; i++) {
                int n = grid.getCellValue(i % 16, i / 16);
                s += (n==0)?".":"@ABCDEFGHIJKLMNOP".substring(n,n+1);
            }
            System.err.println(s);
            int w = (int)((difficulty + 0.05) * 10);
            int p = w % 10; w /= 10;
            System.err.println("ED=" + w + "." + p);
            System.err.flush();
            if (difficulty >= minDifficulty && difficulty <= maxDifficulty) {
                grid.fixGivens();
                return grid;
            }
            if (isInterrupted) {
                System.err.println("Stopped.");
                System.err.flush();
                return null;
            }
        }
    }

    /**
     * Generate a random grid with the given symmetry
     * @param rnd the random gene
     * @param symmetry the symmetry type
     * @return the generated grid
     */
    public Grid generate(Random rnd, Symmetry symmetry) {

        // Build the solution
        Grid grid = new Grid();
        boolean result = analyser.solveRandom(grid, rnd);
        if ( !result ) {
            return null;
        }
        if (isInterrupted) {
            return null;
        }
        Grid solution = new Grid();
        grid.copyTo(solution);

        // Build running indexes
        int[] indexes = new int[256];

//      int attempts = 0;
//      int successes = 0;
        String s = ""; int cnt = 0;
        // Randomly remove clues
        boolean isSuccess = true;
        while (isSuccess) {
          for (int i = 0; i < indexes.length; i++)
              indexes[i] = i;
          // Shuffle
          for (int i = 0; i < 256; i++) {
              int p1 = rnd.nextInt(256);
              int p2 = rnd.nextInt(256);
              int temp = indexes[p1];
              indexes[p1] = indexes[p2];
              indexes[p2] = temp;
          }
            // Choose a random cell to clear
            int index = rnd.nextInt(256);
            int countDown = 256; // Number of cells
            int lastCount = 256;
            isSuccess = false;
            do {
              if ( indexes[ index] != -1 ) {
                // Build symmetric points list
                int y = indexes[index] / 16;
                int x = indexes[index] % 16;
                Point[] points = symmetry.getPoints(x, y);

                // Remove cells
                boolean cellRemoved = false;
                s = ""; cnt = 0;
                for (Point p : points) {
                    Cell cell = grid.getCell(p.x, p.y);
                    if (cell.getValue() != 0) {
                        cell.setValue(0);
                        cellRemoved = true;
                        if ( s.length() != 0 ) { s += ", "; }
                        s += "r" + (p.y+1) + "c" + (p.x+1) + "=0";
                    }
                }
                if ( s.length() != 0 && lastCount/10 != countDown/10 ) {
                    for (int i = 0; i < 256; i++) {
                        int n = grid.getCellValue(i % 16, i / 16);
                        if ( n != 0 ) { cnt++; }
                    }
                    s += " (" + cnt + "/" + countDown + ")";  // [cD=" + countDown + "]";
                  if ( cnt < 120 )
                  {
                    System.err.println(s);
                    System.err.flush();
                  }
                  lastCount = countDown;
                }
                if (cellRemoved) {
                    // Test if the Sudoku still has an unique solution
                    int state = analyser.getCountSolutions(solution, grid);
                    if (state == 1) {
                        // Cells successfully removed: still a unique solution
                        isSuccess = true;
//                      successes += 1;
//                  } else if (state == 0) {
//a                     assert false : "Invalid grid";
                    } else {
                        // Failed. Put the cells back and try with next cell
                        for (Point p : points) {
                            grid.setCellValue(p.x, p.y, solution.getCellValue(p.x, p.y));
                            indexes[ p.y*16 + p.x] = -1;
                        }
//                      attempts += 1;
                    }
                }
              }
                index = (index + 1) % 256; // Next index (indexing scrambled array of indexes)
                countDown--;
                if (isInterrupted) {
                    countDown = 0;
                }
            } while (!isSuccess && countDown > 0);
        }
               s = "";     cnt = 0;
        for (int i = 0; i < 256; i++) {
            int n = grid.getCellValue(i % 16, i / 16);
            if ( n != 0 ) { cnt++; }
        }
        if ( cnt < 10 ) { s += " "; }
        s += " " + cnt + " ";
      if (!isInterrupted) {
        s = s + "got new sudoku";
      }
        System.err.println(s);
        System.err.flush();
        return grid;
    }

    public void interrupt() {
        this.isInterrupted = true;
    }

}
