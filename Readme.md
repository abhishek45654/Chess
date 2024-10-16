# Chess Game

This project is a simple implementation of a chess game, structured to facilitate 
conversion to a Spring Boot application using Maven. 
The project follows a standard Maven directory structure.
It is purely created in java.
### tech stack: *java*

## Project Structure
```plaintext
chess/
├── out/                            # Compiled output

├── src/
│   └── main/
│       └── java/
│           └── app/
│               └── chess/
│                   ├── application/
│                   │   └── ChessApplication.java    # Main application entry point
│
│                   ├── board/
│                   │   └── Board.java               # Chess board implementation
│
│                   ├── config/
│                   │   ├── AppConfig.java           # Configuration for the application
│                   │   └── Mappings.java            # Piece mappings for chess board setup
│
│                   ├── constants/
│                   │   └── PieceNotation.java       # Constants for chess piece notation
│
│                   ├── piece/
│                   │   ├── Piece.java               # Abstract piece class
│                   │   ├── PieceFactory.java        # Factory for creating pieces
│                   │   └── pieces/
│                   │       ├── Bishop.java          # Bishop class implementation
│                   │       ├── King.java            # King class implementation
│                   │       ├── Knight.java          # Knight class implementation
│                   │       ├── NoPiece.java         # Placeholder for empty square
│                   │       ├── Pawn.java            # Pawn class implementation
│                   │       ├── Queen.java           # Queen class implementation
│                   │       └── Rook.java            # Rook class implementation
│
│                   ├── utils/
│                   │   ├── CheckmateUtil.java       # Utility for checkmate logic
│                   │   ├── CheckmateUtilImpl.java   # Implementation of checkmate utility
│                   │   ├── ValidationsUtil.java     # Utility for validating moves
│                   │   └── ValidationsUtilImpl.java # Implementation of validation utility
│
│                   └── validation/
│                       ├── Validation.java          # Validation interface
│                       └── ValidationImpl.java      # Validation logic implementation

├── target/                        # Compiled project output

├── .gitignore                     # Git ignored files

├── pom.xml                        # Maven build configuration

└── README.md                      # Project documentation (this file)
```

## How to Start the Program
After cloning the repository, follow these steps:

### Skipping Compilation

If you want to skip the compilation part, you can directly run the application from the project root using the following command:
```bash
java -cp ./out app/chess/ChessApp
```


### Steps to Compile and Run (For Champs)

### Step 1: Navigate to the Source Directory
Go to the source directory where the Java files are located:
```bash
cd src/main/java
```
### Step 2: Create an Output Directory
Create an out directory to keep the compiled files. There is already a directory created in the chess folder:
```bash
mkdir -p ../../../out
```

### Step 3: Compile the Java Files
Run the following command to compile the ChessApp.java file:
```bash
javac -d ../../../out app/chess/ChessApp.java
```
You can also specify your own output path by modifying the command above.

### Step 4: Run the Application
Execute the following command to run the application:
```bash
java -cp ../../../out app/chess/ChessApp
```
Once you complete these steps, the application will start.

## How to Play
### Notations

In this chess application, the pieces are represented using the following notations:

- `W` → Represents a white piece. For example:
    - `WK` → White King
    - `WB` → White Bishop
    - `WN` → White Knight

- `B` → Represents a black piece. For example:
    - `BK` → Black King
    - `BB` → Black Bishop
    - `BN` → Black Knight

Each piece is abbreviated as follows:
- `K` → King
- `Q` → Queen
- `R` → Rook
- `B` → Bishop
- `N` → Knight
- `P` → Pawn

### Example Setup:

At the start of the game, the chessboard will be arranged as follows:


### Chessboard Layout

```
8 BR BN BB BQ BK BB BN BR
7 BP BP BP BP BP BP BP BP
6 -- -- -- -- -- -- -- --
5 -- -- -- -- -- -- -- --
4 -- -- -- -- -- -- -- --
3 -- -- -- -- -- -- -- --
2 WP WP WP WP WP WP WP WP
1 WR WN WB WQ WK WB WN WR
+  a  b  c  d  e  f  g  h
```
+ The first column represents **ranks** (the horizontal rows).
+ The last row represents **files** (the vertical columns).
+ To make a move, provide the start and end positions in a single line of input. For example, if you want to move pawn from `e2` to `e4`, you should input:

> e2 e4

after first move
```
1 WR WN WB WK WQ WB WN WR 
2 WP WP WP -- WP WP WP WP 
3 -- -- -- -- -- -- -- -- 
4 -- -- -- WP -- -- -- -- 
5 -- -- -- -- -- -- -- -- 
6 -- -- -- -- -- -- -- -- 
7 BP BP BP BP BP BP BP BP 
8 BR BN BB BK BQ BB BN BR 
+ h  g  f  e  d  c  b  a  

```
White always starts first, followed by Black.
Enter the moves alternately until the game ends.


## *Enjoy your game of chess!*

