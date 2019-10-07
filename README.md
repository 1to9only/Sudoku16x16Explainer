# SudokuExplainer (SE)

## 1.2.1

Nicolas Juillerat's SudokuExplainer

## 1.2.1.3

gsf's (Glenn Fowler) serate modifications from: http://gsf.cococlyde.org/download/sudoku/serate.tgz used to rate sudoku puzzles for the Patterns Game: http://forum.enjoysudoku.com/patterns-game-t6290.html

The Java binary can be downloaded from: http://gsf.cococlyde.org/download/sudoku/SudokuExplainer.jar

## Changes - 20191005

- updated Reader code to handle most grid formats: grid is 1-9 for given, . or 0 for empty cell
- added **Undo** based on code from https://github.com/Itsukara/SudokuExplainerPlus
- fixed mouse click issue in unsolved cell to select potential candidate

## Changes - 20191007

- added **Candidate Masks** based on code from https://github.com/blindlf/SudokuExplainer
- Chaining.java changes from http://forum.enjoysudoku.com/post280341.html
- commented out all assert statements

## Usage - GUI

  java.exe -jar SudokuExplainer.jar

## Usage - serate

  java.exe -Xrs -Xmx500m -cp SudokuExplainer.jar diuf.sudoku.test.serate --format="%g ED=%r/%p/%d" --input=puzzles.txt --output=output.txt

## Usage - manual

  java.exe -cp SudokuExplainer.jar diuf.sudoku.test.serate -m


