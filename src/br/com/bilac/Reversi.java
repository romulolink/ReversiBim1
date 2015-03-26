package br.com.bilac;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;

class GPanel extends JPanel implements MouseListener {

    ReversiBoard board;
    int gameLevel;
    ImageIcon button_black, button_white;
    JLabel score_black, score_white;
    String gameTheme;
    Move hint=null;
    TKind vez = TKind.black;
    boolean inputEnabled, active;


    public GPanel (ReversiBoard board, JLabel score_black, JLabel score_white, String theme, int level) {
        super();
        this.board = board;
        this.score_black = score_black;
        this.score_white = score_white;
        gameLevel = level;
        setTheme(theme);
        addMouseListener(this);
        inputEnabled = true;
        active = true;

    }

    public void setTheme(String gameTheme)  {
        hint = null;
        this.gameTheme = gameTheme;

         if (gameTheme.equals("Electric")) {
            button_black = new ImageIcon(Reversi.class.getResource("button_black.png"));
            button_white = new ImageIcon(Reversi.class.getResource("button_white.png"));
            setBackground(Color.white);
        }

        repaint();
    }


    public void drawPanel(Graphics g) {

        for (int i = 1 ; i < 8 ; i++) {
            g.drawLine(i * Reversi.Square_L, 0, i * Reversi.Square_L, Reversi.Height);
        }
        g.drawLine(Reversi.Width, 0, Reversi.Width, Reversi.Height);
        for (int i = 1 ; i < 8 ; i++) {
            g.drawLine(0, i * Reversi.Square_L, Reversi.Width, i * Reversi.Square_L);
        }
        g.drawLine(0, Reversi.Height, Reversi.Width, Reversi.Height);
        for (int i = 0 ; i < 8 ; i++)
            for (int j = 0 ; j < 8 ; j++)
                switch (board.get(i,j)) {
                    case white:
                        if (gameTheme.equals("Flat"))
                        {	g.setColor(Color.white);
                            g.fillOval(1 + i * Reversi.Square_L, 1 + j * Reversi.Square_L, Reversi.Square_L-1, Reversi.Square_L-1);
                        }
                        else g.drawImage(button_white.getImage(), 1 + i * Reversi.Square_L, 1 + j * Reversi.Square_L, this);
                        break;
                    case black:
                        if (gameTheme.equals("Flat"))
                        {	g.setColor(Color.black);
                            g.fillOval(1 + i * Reversi.Square_L, 1 + j * Reversi.Square_L, Reversi.Square_L-1, Reversi.Square_L-1);
                        }
                        else g.drawImage(button_black.getImage(), 1 + i * Reversi.Square_L, 1 + j * Reversi.Square_L, this);
                        break;
                }
        if (hint != null) {
            g.setColor(Color.darkGray);
            g.drawOval(hint.i * Reversi.Square_L+3, hint.j * Reversi.Square_L+3, Reversi.Square_L-6, Reversi.Square_L-6);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPanel(g);
    }

    public Dimension getPreferredSize() {
        return new Dimension(Reversi.Width,Reversi.Height);
    }

    public void showWinner() {
        inputEnabled = false;
        active = false;
        if (board.counter[0] > board.counter[1])
            JOptionPane.showMessageDialog(this, "Você venceu!","Reversi",JOptionPane.INFORMATION_MESSAGE);
        else if (board.counter[0] < board.counter[1])
            JOptionPane.showMessageDialog(this, "Eu venci!","Reversi",JOptionPane.INFORMATION_MESSAGE);
        else JOptionPane.showMessageDialog(this, "Empate!","Reversi",JOptionPane.INFORMATION_MESSAGE);
    }

    public void setHint(Move hint) {
        this.hint = hint;
    }

    public void clear() {
        board.clear();
        score_black.setText(Integer.toString(board.getCounter(TKind.black)));
        score_white.setText(Integer.toString(board.getCounter(TKind.white)));
        inputEnabled = true;
        active = true;
    }

    /*
    Toda vez que o jogador insere uma peça este método é executado
     */
    public void computerMove() {
        if (board.gameEnd()) {
            showWinner();
            return;
        }
        Move move = new Move();

        // Modifica move com IA
        if (board.findMove(TKind.white,gameLevel,move)) {

            // Coloca a peça
            board.move(move,TKind.white);



            // Atualiza score
            score_black.setText(Integer.toString(board.getCounter(TKind.black)));
            score_white.setText(Integer.toString(board.getCounter(TKind.white)));
            repaint();
            if (board.gameEnd()) showWinner();
            else if (!board.userCanMove(TKind.black)) {
                JOptionPane.showMessageDialog(this, "Você passou","Reversi",JOptionPane.INFORMATION_MESSAGE);
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        computerMove();
                    }
                });
            }
        }
        else if (board.userCanMove(TKind.black))
            JOptionPane.showMessageDialog(this, "Passei","Reversi",JOptionPane.INFORMATION_MESSAGE);
        else showWinner();
    }

    // Tudo começa com o click do Mouse
    public void mouseClicked(MouseEvent e) {
    // generato quando il mouse viene premuto e subito rilasciato (click)

        if (inputEnabled) {
            hint = null;
            int i = e.getX() / Reversi.Square_L;
            int j = e.getY() / Reversi.Square_L;



            if (!Reversi.enable_multiplayer){

                if ((i < 8) && (j < 8) && (board.get(i,j) == TKind.nil) && (board.move(new Move(i,j),TKind.black) != 0)) {
                    score_black.setText(Integer.toString(board.getCounter(TKind.black)));
                    score_white.setText(Integer.toString(board.getCounter(TKind.white)));
                    repaint();
                    javax.swing.SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            Cursor savedCursor = getCursor();
                            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }

                            computerMove();

                            setCursor(savedCursor);
                        }
                    });
                } else

                    JOptionPane.showMessageDialog(this, "Movimento não permitido","Reversi",JOptionPane.INFORMATION_MESSAGE);

            }else {


                if ((i < 8) && (j < 8) && (board.get(i,j) == TKind.nil) && (board.move(new Move(i,j),vez ) != 0)) {
                    score_black.setText(Integer.toString(board.getCounter(TKind.black)));
                    score_white.setText(Integer.toString(board.getCounter(TKind.white)));
                    repaint();
                    javax.swing.SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            Cursor savedCursor = getCursor();
                            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }


                            if (vez == TKind.black)
                                vez = TKind.white;
                            else
                                vez = TKind.black;


                            setCursor(savedCursor);
                        }
                    });
                }else
                    JOptionPane.showMessageDialog(this, "Movimento não permitido","Reversi",JOptionPane.INFORMATION_MESSAGE);





            }








        }

    }


    public void mouseEntered(MouseEvent e) {
// generato quando il mouse entra nella finestra
    }


    public void mouseExited(MouseEvent e) {
// generato quando il mouse esce dalla finestra
    }


    public void mousePressed(MouseEvent e) {
// generato nell'istante in cui il mouse viene premuto
    }


    public void mouseReleased(MouseEvent e) {
// generato quando il mouse viene rilasciato, anche a seguito di click

    }


};

public class Reversi extends JFrame implements ActionListener{

    JEditorPane editorPane;

    static final String WindowTitle = "Reversi";
    static boolean enable_multiplayer = false;
    static final String ABOUTMSG = WindowTitle+"\n\n26-12-2006\njavalc6";

    static GPanel gpanel;
    static JMenuItem hint;

    static final int Square_L = 65; // length in pixel of a square in the grid
    static final int  Width = 8 * Square_L; // Width of the game board
    static final int  Height = 8 * Square_L; // Width of the game board

    ReversiBoard board;
    static JLabel score_black, score_white;

    public Reversi() {
        super(WindowTitle);

        score_black=new JLabel("2"); // the game start with 2 black pieces
        score_black.setForeground(Color.blue);
        score_black.setFont(new Font("Dialog", Font.BOLD, 16));
        score_white=new JLabel("2"); // the game start with 2 white pieces
        score_white.setForeground(Color.red);
        score_white.setFont(new Font("Dialog", Font.BOLD, 16));
        board = new ReversiBoard();
        gpanel = new GPanel(board, score_black, score_white,"Electric", 3);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupMenuBar();
        gpanel.setMinimumSize(new Dimension(Reversi.Width,Reversi.Height));

        JPanel status = new JPanel();
        status.setLayout(new BorderLayout());
        status.add(score_black, BorderLayout.WEST);
        status.add(score_white, BorderLayout.EAST);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, gpanel, status);
        splitPane.setOneTouchExpandable(false);
        getContentPane().add(splitPane);

        pack();
        setVisible(true);
        setResizable(false);
    }


    void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(buildGameMenu());
        setJMenuBar(menuBar);
    }


    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String action = source.getText();
        if (action.equals("Classic")) gpanel.setTheme(action);
        else if (action.equals("Electric")) gpanel.setTheme(action);
        else if (action.equals("Flat")) gpanel.setTheme(action);
    }

    protected JMenu buildGameMenu() {
        JMenu game = new JMenu("Jogo");
        JMenuItem newWin = new JMenuItem("Single");
        JMenuItem quit = new JMenuItem("Sair");
        final JMenuItem multiplayer = new JMenuItem("Multiplayer");


        multiplayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enable_multiplayer = true;
            }
        });

        // Begin "New"
        newWin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gpanel.clear();
//                hint.setEnabled(true);
                repaint();
            }});

// Begin "Quit"
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }});
// End "Quit"



        game.add(newWin);
        game.add(multiplayer);
        game.addSeparator();
        game.add(quit);
        return game;
    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) { }
        Reversi app = new Reversi();
    }

}