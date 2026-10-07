# 🐍 Snakes & Ladders – Java Data Structures Project

A simple **2-player Snakes & Ladders game** developed in **Java Swing** as a Data Structures project.

The game provides a graphical 10×10 board, dice-based movement, snakes and ladders, player turns using a queue, and snake/ladder connections implemented using a graph.

## 🎮 Features

* 👥 Two-player gameplay
* 🎲 Random dice rolling
* 🐍 Snakes and 🪜 Ladders
* 🔄 Turn management using a custom **Queue**
* 🕸️ Snakes and ladders represented using a **Graph**
* 🖥️ Interactive Java Swing GUI
* 🏆 Automatic winner detection
* 🎯 Exact roll required to reach position 100
* 📍 Player positions displayed directly on the board

## 🛠️ Technologies Used

* **Java**
* **Java Swing**
* **Object-Oriented Programming**
* **Data Structures**

  * Queue
  * Graph
  * Array

## 📚 Data Structures Used

### 1. Queue

A custom `PlayerQueue` is used to manage player turns.

```text
Player 1 → Player 2 → Player 1 → Player 2 → ...
```

The queue supports:

* `enqueue()`
* `dequeue()`
* `peek()`

### 2. Graph

A custom `Graph` structure stores the connections between snakes and ladders.

Examples:

```text
Ladders:
4  → 25
13 → 46
33 → 49
50 → 69
62 → 81
74 → 92

Snakes:
99 → 54
90 → 48
70 → 55
52 → 42
25 → 2
```

## 🎯 Game Rules

1. Enter the names of Player 1 and Player 2.
2. Player 1 starts the game.
3. Click **ROLL DICE** to roll the dice.
4. The player moves according to the dice value.
5. Landing on a ladder moves the player upward.
6. Landing on a snake moves the player downward.
7. Players take turns using the queue.
8. A player must roll the exact number required to reach position **100**.
9. The first player to reach position **100** wins.

## 🖥️ GUI

The application is built using Java Swing and contains:

* Game board
* Player turn indicator
* Dice result
* Game status message
* Roll Dice button
* Player tokens

The board is drawn dynamically using Java's `Graphics` and `Graphics2D` classes.

## 📂 Project Structure

```text
SnakesAndLaddersProject/
│
├── SnakesAndLaddersProject.java
└── README.md
```

## ▶️ How to Run

### Prerequisites

Make sure Java JDK is installed on your system.

Check your Java installation:

```bash
java -version
javac -version
```

### Compile

```bash
javac SnakesAndLaddersProject.java
```

### Run

```bash
java SnakesAndLaddersProject
```

A graphical window will open and ask for the names of the two players.

## 🏗️ Main Classes

### `SnakesAndLaddersProject`

Main class responsible for:

* Initializing the game
* Creating the GUI
* Managing player positions
* Rolling the dice
* Checking snakes and ladders
* Detecting the winner

### `Graph`

Stores the destination of each snake or ladder.

```java
void addEdge(int source, int destination)
```

### `PlayerQueue`

Manages the order of player turns.

```java
void enqueue(String player)
String dequeue()
String peek()
```

### `BoardPanel`

Custom Swing panel responsible for drawing:

* Board cells
* Snakes
* Ladders
* Player tokens

## 🎓 Project Purpose

This project demonstrates how fundamental **Data Structures and Java GUI programming** can be combined to create an interactive game.

It specifically demonstrates the practical use of:

* Arrays
* Circular Queue
* Graph representation
* Object-Oriented Programming
* Event-driven programming
* Java Swing graphics

## 👨‍💻 Author

**Your Name**

> A Java Data Structures project implementing a graphical Snakes & Ladders game.
