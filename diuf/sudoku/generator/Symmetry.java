/*
 * Project: Sudoku Explainer
 * Copyright (C) 2006-2007 Nicolas Juillerat
 * Available under the terms of the Lesser General Public License (LGPL)
 */
package diuf.sudoku.generator;

/**
 * Enumeration of Sudoku grid's symmetries
 */
public enum Symmetry {

    Vertical {
        @Override
        public Point[] getPoints(int x, int y) {
            return new Point[] {
                    new Point(x, y),
                    new Point(15 - x, y)};
        }

        @Override
        public String getDescription() {
            return "Mirror symmetry around the vertical axis";
        }
    },
    Horizontal {
        @Override
        public Point[] getPoints(int x, int y) {
            return new Point[] {
                    new Point(x, y),
                    new Point(x, 15 - y)};
        }

        @Override
        public String getDescription() {
            return "Mirror symmetry around the horizontal axis";
        }
    },
    Diagonal {
        @Override
        public Point[] getPoints(int x, int y) {
            return new Point[] {
                    new Point(x, y),
                    new Point(15 - y, 15 - x)};
        }

        @Override
        public String getDescription() {
            return "Mirror symmetry around the raising diagonal";
        }
    },
    AntiDiagonal {
        @Override
        public Point[] getPoints(int x, int y) {
            return new Point[] {
                    new Point(x, y),
                    new Point(y, x)};
        }

        @Override
        public String toString() {
            return "Anti-diagonal";
        }

        @Override
        public String getDescription() {
            return "Mirror symmetry around the falling diagonal";
        }
    },
    BiDiagonal {
        @Override
        public Point[] getPoints(int x, int y) {
            return new Point[] {
                    new Point(x, y),
                    new Point(y, x),
                    new Point(15 - y, 15 - x),
                    new Point(15 - x, 15 - y)};
        }

        @Override
        public String toString() {
            return "Bi-diagonal";
        }

        @Override
        public String getDescription() {
            return "Mirror symmetries around both diagonals";
        }
    },
    Orthogonal {
        @Override
        public Point[] getPoints(int x, int y) {
            return new Point[] {
                    new Point(x, y),
                    new Point(15 - x, y),
                    new Point(x, 15 - y),
                    new Point(15 - x, 15 - y)};
        }

        @Override
        public String getDescription() {
            return "Mirror symmetries around the horizontal and vertical axes";
        }
    },
    Rotational180 {
        @Override
        public Point[] getPoints(int x, int y) {
            return new Point[] {
                    new Point(x, y),
                    new Point(15 - x, 15 - y)};
        }

        @Override
        public String toString() {
            return "180° rotational";
        }

        @Override
        public String getDescription() {
            return "Symmetric under a 180° rotation (central symmetry)";
        }
    },
    Rotational90 {
        @Override
        public Point[] getPoints(int x, int y) {
            return new Point[] {
                    new Point(x, y),
                    new Point(15 - x, 15 - y),
                    new Point(y, 15 - x),
                    new Point(15 - y, x)};
        }

        @Override
        public String toString() {
            return "90° rotational";
        }

        @Override
        public String getDescription() {
            return "Symmetric under a 90° rotation";
        }
    },
    None {
        @Override
        public Point[] getPoints(int x, int y) {
            return new Point[] {
                    new Point(x, y)};
        }

        @Override
        public String getDescription() {
            return "No symmetry";
        }
    },
    Full {
        @Override
        public Point[] getPoints(int x, int y) {
            return new Point[] {
                    new Point(x, y),
                    new Point(15 - x, y),
                    new Point(x, 15 - y),
                    new Point(15 - x, 15 - y),
                    new Point(y, x),
                    new Point(15 - y, x),
                    new Point(y, 15 - x),
                    new Point(15 - y, 15 - x)};
        }

        @Override
        public String getDescription() {
            return "All symmetries (around the 8 axes and under a 90° rotation)";
        }
    };

    public abstract Point[] getPoints(int x, int y);

    public abstract String getDescription();

}
