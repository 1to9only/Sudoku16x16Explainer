# Sudoku16x16Explainer

Sudoku16x16Explainer is my modifications to SudokuExplainer to solve 16x16 sudokus.

## Puzzle Format

- The GUI displays solved cells and candidates using numbers 1-16.
- Load/Save and Copy/Paste use A-P for the numbers 1-16 - Other formats: 0-9A-F, 1-9A-G, 1-16 are not supported.
- Candidate input via keyboard is through keys A-P/a-p for the numbers 1-16, and keys 1-9 for numbers 1-9 - it is a better experience to select candidates using the mouse (left-click to set a candidate, right-click to remove a candidate).

A puzzle converter to the A-P format used by Sudoku16Explainer is [here](https://github.com/1to9only/convert16x16).

## Changes 20210405
- a few cosmetic changes

## Changes 20210316
- a few fixes, and a few improvements

## Changes 20210118
- some small fixes, changes, improvements:
- Dark theme, 'Restart...' and 'Save as image...' options, 'Apply Singles' button

## Changes 20200915
- fixed one old bug, and other small changes

## Changes 20191118
- re-enabled commented out techniques, 8 solving techniques are disabled
- changed to PLAIN font for GUI grid, black given cells, blue solved cells

## Changes 20191104
- disabled changing thread priority in Solver.
- made Full Symmetry only initial option in GenerateDialog.
- changed ratings Naked and Hidden: Quintuplets, Sextuplets, Septuplets (5.4) - septuplets are disabled.
- changed ratings Fishes: Starfishes, Whales, Leviathans (5.5).
- added support for 0-9A-F, 1-9A-G, 1-16 puzzle formats, in GUI only.
- added Naked Octuplet, Hidden Octuplet, Loch Ness Monster (5.6) - all are elusive, and disabled.
- slightly improved SolvingTechnique, NakedSingle, Chaining, Grid.
- fixed parallel processing threads finishing order

## Changes 20191015
- modified to solve 16x16 sudokus: grid is A-P for given, . or 0 for empty cell
- disabled Unique Loops (UL), Aligned Pair Exclusion (APE) and AlignedTripletExclusion (ATE) - this is because of software limitations in Permutations.java.
- added Direct Hidden Quads, with rating of 4.3.
- added Naked Quintuplets, Sextuplets, Septuplets, all with same rating as for Naked Quad (5.0).
- added Fishes: Starfishes, Whales, Leviathans, all with same rating as for Jellyfish (5.2).
- added Hidden Quintuplets, Sextuplets, Septuplets, all with same rating as for Hidden Quad (5.4).
- added parallel processing, based on code from [SukakuExplainer](https://github.com/SudokuMonster/SukakuExplainer).
- added Difficulty total, based on code from [NewerSudokuExplainer](https://github.com/Sunnie-Shine/NewerSudokuExplainer).
- added hints.java

## Source
- 1.2.1 - Nicolas Juillerat's SudokuExplainer
- 1.2.1.3 - gsf's (Glenn Fowler) serate modifications from [here](http://gsf.cococlyde.org/download/sudoku/serate.tgz) used to rate sudoku puzzles in the [Patterns Game](http://forum.enjoysudoku.com/patterns-game-t6290.html).
- updated Reader code to handle most 9x9 grid formats: grid is 1-9 for given, . or 0 for empty cell
- added **Undo** based on code from [here](https://github.com/Itsukara/SudokuExplainerPlus)
- fixed mouse click issue in unsolved cell to select potential candidate
- added **Candidate Masks** based on code from [here](https://github.com/blindlf/SudokuExplainer)
- added Chaining.java changes from [here](http://forum.enjoysudoku.com/post280341.html)
- commented out all assert statements

## Usage - GUI

java.exe -jar Sudoku16Explainer.jar

![](/images/sample1.png)

## Usage - serate

java.exe -Xrs -Xmx500m -cp Sudoku16Explainer.jar diuf.sudoku.test.serate --format="%g ED=%r/%p/%d" --input=puzzle(s).txt --output=output.txt

## Usage - hints

java.exe -Xrs -Xmx500m -cp Sudoku16Explainer.jar diuf.sudoku.test.hints --input=puzzle(s).txt

## Usage - analyze

java.exe -Xrs -Xmx500m -cp Sudoku16Explainer.jar diuf.sudoku.test.Tester --input=puzzle(s).txt

## Usage - manual

java.exe -cp Sudoku16Explainer.jar diuf.sudoku.test.serate -m

## Testing

The program is functional, and it had limited testing. The first two puzzles it was tested with (and solved) are below:

This puzzle was posted by monstersudoku [here](http://forum.enjoysudoku.com/post6769.html#p6769):
```
.F..G..O.P.M.A..
L..ACE...B.DF.O.
.O...N.BF.......
..JP..M...C..EBI
.AC.PDO..JH...E.
GMEN......B.OC..
...F....LI..HNP.
P.....N.D.K....J
A....O.M.L.....P
.CNM..HD....I...
..PD.I......CHLK
.I...CK..DPN.BJ.
NKL..M...O..BJ..
.......CI.M...N.
.J.OI.F...LKP..G
..M.O.L.H..J..C. ED=2.3/1.2/1.2

Analyzing Sudoku #1
145 Hidden Single
3 Direct Pointing
4 Direct Hidden Pair
2 Naked Single
Hardest technique: Naked Single
Difficulty: 2.3
```

This is monstersudoku's puzzle modified by m_b_metcalf from [here](http://forum.enjoysudoku.com/post258131.html#p258131):
```
.E..F..N.O.L.P..
K..PBD...A.CE.N.
.....M.AE.......
..IO..L...B..D.H
.PB.OCN..IG...D.
FLDM........NB..
...E....KH..GMO.
......M.C.J....I
P....N.L.K.....O
.BML..GC....H...
..OC.H......BGKJ
.H...BJ...OM.AI.
MJK......N..AI..
.......BH.L...M.
.I..H......JO..F
..L...K.G.....B. ED=9.2/1.2/1.2

Analyzing Sudoku #1
152 Hidden Single
2 Direct Pointing
5 Direct Hidden Pair
1 Naked Single
1 Direct Hidden Triplet
22 Pointing
7 Claiming
2 Naked Pair
1 X-Wing
2 Hidden Pair
1 Naked Triplet
2 Hidden Triplet
1 Jellyfish
4 Turbot Fish
18 Forcing Chain
1 Bidirectional Cycle
3 Region Forcing Chains
6 Dynamic Region Forcing Chains
5 Dynamic Cell Forcing Chains
1 Dynamic Contradiction Forcing Chains
1 Dynamic Double Forcing Chains
Hardest technique: Dynamic Region Forcing Chains
Difficulty: 9.2
```

Sudoku16Explainer can serate:
- tarek's 206 tough puzzles<sup>1</sup> from [here](http://forum.enjoysudoku.com/post275765.html#p275765) (range: ED=3.0/1.2/1.2 - ED=9.7/1.2/1.2)
- tarek-first20 puzzles from [here](http://forum.enjoysudoku.com/post276187.html#p276187) (range: ED=2.6/1.2/1.2 - ED=9.4/1.2/1.2)

## (Non-) Issues

- Sudoku16Explainer can take a long time to solve the hardest 16x16 sudokus - be patient ...

- Sudoku16Explainer can generate a puzzle, the Difficulty level is set to Diabolical<sup>2</sup> at Maximum difficulty and cannot be changed ... - it takes some time to generate a puzzle ... - be patient ...

This is an example puzzle generated by Sudoku16Explainer:
```
....GF......M...
.D.F.L.A.O..KGI.
.CK.H.JE.M.P.F..
BN.PIDK.G...H.J.
..F.J.LKM..HDP.B
.......O....G.LN
.PI...HG.L.AFC..
...ND....CFO.HE.
.BG.EAP....IN...
..NDK.I.OB...AP.
PM.I....C.......
O.JEL..HFP.N.D..
.F.G...J.KHLE.CA
..H.A.E.PF.C.MN.
.OMB..D.E.G.P.F.
...K......OM.... ED=7.4/1.2/1.2

Analyzing Sudoku #1
131 Hidden Single
3 Direct Hidden Pair
6 Naked Single
1 Direct Hidden Triplet
12 Pointing
5 Claiming
2 Naked Pair
5 Hidden Pair
2 Naked Triplet
2 Swordfish
2 Hidden Triplet
1 XY-Wing
2 Direct Hidden Quad
1 Naked Quintuplet
1 Naked Sextuplet
1 Turbot Fish
1 Bidirectional Cycle
15 Forcing Chain
Hardest technique: Forcing Chain
Difficulty: 7.4
```


<sup>1</sup> tarek's 206 tough puzzles are in file test4.1g from [here](https://github.com/1to9only/convert16x16), use convert16x16.exe to convert to A-P format!

<sup>2</sup> Sudoku16Explainer has generated puzzles ranging from ED=2.0/1.2/1.2 to ED=10.3/1.2/1.2.

.

