import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SnakesAndLaddersProject extends JFrame {

    // Array - Board
    int[] board = new int[101];

    // Graph - Snakes and Ladders
    Graph graph = new Graph(101);

    // Queue - Player Turns
    PlayerQueue playerQueue = new PlayerQueue(2);

    String player1, player2;
    int player1Position = 0;
    int player2Position = 0;

    Random random = new Random();

    BoardPanel boardPanel;
    JLabel turnLabel, diceLabel, statusLabel;
    JButton rollButton;

    public SnakesAndLaddersProject() {

        setTitle("Snakes & Ladders - Data Structures Project");
        setSize(850, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize board array
        for (int i = 1; i <= 100; i++)
            board[i] = i;

        // Graph: Snakes
        graph.addEdge(99, 54);
        graph.addEdge(90, 48);
        graph.addEdge(70, 55);
        graph.addEdge(52, 42);
        graph.addEdge(25, 2);

        // Graph: Ladders
        graph.addEdge(4, 25);
        graph.addEdge(13, 46);
        graph.addEdge(33, 49);
        graph.addEdge(50, 69);
        graph.addEdge(62, 81);
        graph.addEdge(74, 92);

        player1 = JOptionPane.showInputDialog(
                this, "Enter Player 1 name:");

        if (player1 == null || player1.trim().isEmpty())
            player1 = "Player 1";

        player2 = JOptionPane.showInputDialog(
                this, "Enter Player 2 name:");

        if (player2 == null || player2.trim().isEmpty())
            player2 = "Player 2";

        playerQueue.enqueue(player1);
        playerQueue.enqueue(player2);

        createGUI();
    }

    void createGUI() {

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(3, 1));

        turnLabel = new JLabel(
                "Turn: " + playerQueue.peek(),
                SwingConstants.CENTER);

        diceLabel = new JLabel(
                "Dice: -",
                SwingConstants.CENTER);

        statusLabel = new JLabel(
                "Roll the dice to start!",
                SwingConstants.CENTER);

        turnLabel.setFont(new Font("Arial", Font.BOLD, 24));
        diceLabel.setFont(new Font("Arial", Font.BOLD, 22));
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        topPanel.add(turnLabel);
        topPanel.add(diceLabel);
        topPanel.add(statusLabel);

        add(topPanel, BorderLayout.NORTH);

        boardPanel = new BoardPanel();
        add(boardPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();

        rollButton = new JButton("ROLL DICE");
        rollButton.setFont(new Font("Arial", Font.BOLD, 20));
        rollButton.setPreferredSize(new Dimension(220, 50));

        rollButton.addActionListener(e -> playTurn());

        bottomPanel.add(rollButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    void playTurn() {

        // Queue: get current player
        String currentPlayer = playerQueue.dequeue();

        int dice = random.nextInt(6) + 1;
        diceLabel.setText("Dice: " + dice);

        int oldPosition;

        if (currentPlayer.equals(player1))
            oldPosition = player1Position;
        else
            oldPosition = player2Position;

        int newPosition = oldPosition + dice;

        if (newPosition > 100) {

            statusLabel.setText(
                    currentPlayer +
                    " needs an exact number to reach 100!");

            playerQueue.enqueue(currentPlayer);
            updateTurnLabel();
            return;
        }

        // Array: get board position
        int boardCell = board[newPosition];

        if (currentPlayer.equals(player1))
            player1Position = boardCell;
        else
            player2Position = boardCell;

        // Graph: check snake or ladder
        int destination = graph.getDestination(boardCell);

        if (destination != -1) {

            if (destination > boardCell) {

                statusLabel.setText(
                        "LADDER! " +
                        currentPlayer +
                        ": " +
                        boardCell +
                        " -> " +
                        destination);

            } else {

                statusLabel.setText(
                        "SNAKE! " +
                        currentPlayer +
                        ": " +
                        boardCell +
                        " -> " +
                        destination);
            }

            if (currentPlayer.equals(player1))
                player1Position = destination;
            else
                player2Position = destination;
        } else {

            statusLabel.setText(
                    currentPlayer +
                    " moved to " +
                    boardCell);
        }

        boardPanel.repaint();

        int finalPosition;

        if (currentPlayer.equals(player1))
            finalPosition = player1Position;
        else
            finalPosition = player2Position;

        if (finalPosition == 100) {

            rollButton.setEnabled(false);

            JOptionPane.showMessageDialog(
                    this,
                    currentPlayer + " WINS THE GAME!",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);

            statusLabel.setText(
                    currentPlayer + " is the winner!");

            return;
        }

        // Queue: add player back
        playerQueue.enqueue(currentPlayer);
        updateTurnLabel();
    }

    void updateTurnLabel() {

        turnLabel.setText(
                "Turn: " + playerQueue.peek());
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            SnakesAndLaddersProject game =
                    new SnakesAndLaddersProject();

            game.setVisible(true);
        });
    }


    // ================= GRAPH =================

    static class Graph {

        int[] destination;

        Graph(int size) {

            destination = new int[size];

            for (int i = 0; i < size; i++)
                destination[i] = -1;
        }

        void addEdge(int from, int to) {

            destination[from] = to;
        }

        int getDestination(int from) {

            return destination[from];
        }
    }


    // ================= QUEUE =================

    static class PlayerQueue {

        String[] queue;
        int front = 0;
        int rear = -1;
        int size = 0;

        PlayerQueue(int capacity) {

            queue = new String[capacity];
        }

        void enqueue(String player) {

            rear = (rear + 1) % queue.length;
            queue[rear] = player;
            size++;
        }

        String dequeue() {

            if (size == 0)
                return null;

            String player = queue[front];

            front = (front + 1) % queue.length;
            size--;

            return player;
        }

        String peek() {

            if (size == 0)
                return null;

            return queue[front];
        }
    }


    // ================= BOARD =================

    class BoardPanel extends JPanel {

        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            int width = getWidth();
            int height = getHeight();

            int cellSize = Math.min(
                    width / 10,
                    height / 10);

            for (int number = 1; number <= 100; number++) {

                int row = (number - 1) / 10;
                int column = (number - 1) % 10;

                if (row % 2 == 1)
                    column = 9 - column;

                int x = column * cellSize;
                int y = (9 - row) * cellSize;

                g.setColor(Color.WHITE);
                g.fillRect(
                        x, y,
                        cellSize,
                        cellSize);

                g.setColor(Color.BLACK);
                g.drawRect(
                        x, y,
                        cellSize,
                        cellSize);

                g.setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13));

                g.drawString(
                        String.valueOf(number),
                        x + 5,
                        y + 16);
            }

            drawSnake(g, 99, 54, cellSize);
            drawSnake(g, 90, 48, cellSize);
            drawSnake(g, 70, 55, cellSize);
            drawSnake(g, 52, 42, cellSize);
            drawSnake(g, 25, 2, cellSize);

            drawLadder(g, 4, 25, cellSize);
            drawLadder(g, 13, 46, cellSize);
            drawLadder(g, 33, 49, cellSize);
            drawLadder(g, 50, 69, cellSize);
            drawLadder(g, 62, 81, cellSize);
            drawLadder(g, 74, 92, cellSize);

            drawPlayer(
                    g,
                    player1Position,
                    Color.BLUE,
                    cellSize,
                    -12);

            drawPlayer(
                    g,
                    player2Position,
                    Color.RED,
                    cellSize,
                    12);
        }

        Point getCellCenter(
                int number,
                int cellSize) {

            if (number == 0)
                number = 1;

            int row = (number - 1) / 10;
            int column = (number - 1) % 10;

            if (row % 2 == 1)
                column = 9 - column;

            int x = column * cellSize
                    + cellSize / 2;

            int y = (9 - row) * cellSize
                    + cellSize / 2;

            return new Point(x, y);
        }

        void drawSnake(
                Graphics g,
                int from,
                int to,
                int cellSize) {

            Point start =
                    getCellCenter(from, cellSize);

            Point end =
                    getCellCenter(to, cellSize);

            Graphics2D g2 =
                    (Graphics2D) g;

            g2.setStroke(
                    new BasicStroke(7));

            g2.setColor(
                    new Color(40, 150, 70));

            g2.drawLine(
                    start.x,
                    start.y,
                    end.x,
                    end.y);

            g2.fillOval(
                    start.x - 10,
                    start.y - 10,
                    20,
                    20);
        }

        void drawLadder(
                Graphics g,
                int from,
                int to,
                int cellSize) {

            Point start =
                    getCellCenter(from, cellSize);

            Point end =
                    getCellCenter(to, cellSize);

            Graphics2D g2 =
                    (Graphics2D) g;

            g2.setStroke(
                    new BasicStroke(5));

            g2.setColor(
                    new Color(180, 120, 30));

            g2.drawLine(
                    start.x - 8,
                    start.y,
                    end.x - 8,
                    end.y);

            g2.drawLine(
                    start.x + 8,
                    start.y,
                    end.x + 8,
                    end.y);

            for (int i = 1; i < 6; i++) {

                double ratio =
                        (double) i / 6;

                int x = (int)
                        (start.x +
                        (end.x - start.x) * ratio);

                int y = (int)
                        (start.y +
                        (end.y - start.y) * ratio);

                g2.drawLine(
                        x - 8,
                        y,
                        x + 8,
                        y);
            }
        }

        void drawPlayer(
                Graphics g,
                int position,
                Color color,
                int cellSize,
                int offset) {

            if (position == 0)
                return;

            Point point =
                    getCellCenter(
                            position,
                            cellSize);

            g.setColor(color);

            g.fillOval(
                    point.x - 12 + offset,
                    point.y - 12,
                    24,
                    24);

            g.setColor(Color.BLACK);

            g.drawOval(
                    point.x - 12 + offset,
                    point.y - 12,
                    24,
                    24);
        }
    }
}