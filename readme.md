# BlueRock TMS lights out challenge

Given a board, a list of pieces and the maximum depth for the pieces to be overlapped. 

- The goal is to place the pieces on the board so that all the cells on the board has become 0.

- The board has an initial state, and the piece also has some values in each cell.

- If the piece has been placed on the board, the cell at the same position are summed up and if the result in each cell is greater than the max depth, it resets to 0. (modulo).

- The objective is to find the top-left coordinate for the pieces to be placed on the board.

- Each piece can be used only once and both board and pieces are not rotatable. All the pieces must be placed on the board.

## Test File

The test file must be `.txt` file and should look like this:

```txt
2
100,101,011
..X,XXX,X.. X,X,X .X,XX XX.,.X.,.XX XX,X. XX .XX,XX.
```

- Line 1: “depth” of the puzzle. This will always be 2, 3 or 4.
- Line 2: the initial board state. Each	row	is separated by	a comma. Each digit represents the initial value for the cell.
- Line 3: individual pieces. Each piece is separated by a space. Each row within a piece is separated by a comma. `.` means no increment and `X` means increment by 1.

## Commandline Arguments

The program accepts a command line argument for the test file(s).

If there is no argument, the program will execute all the built-in test files located in `src/resource/levels/`.

- The argument can be a folder or a file name.

- If it is a folder name, all the test file inside it will be executed.

- If it is a file name, only that file will be executed.

## Compilation

To compile the program, navigate to the `src` directory containing the `Main.java` and run:

```sh
javac Main.java
```

Execute the Main class file inside that `src` directory:

```shell
java Main
```

Passing the specific test file name as an argument:
```shell
java Main <test-filename.txt>
```

All the test files inside that folder will be executed:
```shell
java Main <test-foldername.txt>
```

## Caution

The built-in test files inside `resource/levels` are loaded with the classpath meanwhile the argumented test file/folder name  are loaded with the file system.

If the program is compiled and class files are produced in different place, please relocate the built-in test files according to the `resource/levels` path. 

Otherwise, built-in test file will not be working. (this does not affect the argumented test file path which are loaded with the file system.)