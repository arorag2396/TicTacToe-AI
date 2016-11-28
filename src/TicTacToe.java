
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Platform.exit;
import javax.swing.*;

public class TicTacToe {

	public static int[][] a;

	static Player p1, p2;
	static boolean draw;
	static int turn;
	JButton[][] buttons;
	JPanel buttonPanel;

	public class ClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton temp = (JButton) e.getSource();
			int id = (int) (temp.getClientProperty("id"));
			if (!temp.getText().equals("")) {
				throw new IllegalArgumentException("Invalid Move");

			}
			String text = turn == 1 ? "O" : "X";
			temp.setText(text + "");
			a[(int) id / 3][id % 3] = turn;
			turn = turn == 1 ? 2 : 1;
			if (turn == 2) {

				try {
					move();
				} catch (IOException ex) {
					Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
				}
				turn = turn == 1 ? 2 : 1;

			}
			if (checkWin(p2)) {
				System.out.println(p2);
				JOptionPane.showMessageDialog(null, p2 + " won");
				System.exit(0);
			}
			if (checkWin(p1)) {
				System.out.println(p1);
				JOptionPane.showMessageDialog(null, p1 + " won");
				System.exit(0);
			}
			if (getPossibleMoves().size() == 0) {
				System.out.println("draw");
				JOptionPane.showMessageDialog(null, "Draw");
				System.exit(0);
			}

		}

	}

	public TicTacToe() {

		turn = 1;
		a = new int[3][3];
		buttonPanel = new JPanel(new GridLayout(3, 3));
		ActionListener listener = new ClickListener();

		buttons = new JButton[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				a[i][j] = 0;
				buttons[i][j] = new JButton();// 3*i+j + "");

				buttons[i][j].putClientProperty("id", Integer.valueOf(3 * i + j));
				buttons[i][j].addActionListener(listener);
				buttonPanel.add(buttons[i][j]);
			}

		}
		p1 = new Player("Gaurav", 1);
		p2 = new Player("computer", 2);
		draw = false;

	}

	public static void main(String[] args) throws IOException {

		TicTacToe t = new TicTacToe();
		JFrame frame = new JFrame();
		frame.setSize(300, 400);
		frame.setTitle("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(t.buttonPanel);
		frame.setVisible(true);

		if (t.checkWin(p2))
			System.out.println(p2);
		if (t.checkWin(p1))
			System.out.println(p1);
		if (draw)
			System.out.println("draw");
	}

	public boolean checkWin(Player p) {
		int pr = 0, pc = 0;
		int ox = p.ox;
		if (a[0][0] == ox && a[1][1] == ox && a[2][2] == ox) {
			p.win = true;
			return true;

		}

		if (a[0][2] == ox && a[1][1] == ox && a[2][0] == ox) {
			p.win = true;
			return true;
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (a[i][j] == ox)
					pr++;

				if (a[j][i] == ox)
					pc++;

				if (pr == 3) {
					p.win = true;
					return true;

				}

				if (pc == 3) {
					p.win = true;
					return true;

				}

			}
			pr = 0;
			pc = 0;
		}

		return false;
	}

	public void display() throws IOException {

		System.out.println("");

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				System.out.print(a[i][j] + " ");
			}
			System.out.println("");
		}

	}

	public LinkedList<Integer> getPossibleMoves() {
		LinkedList<Integer> moves = new LinkedList<>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (a[i][j] == 0)
					moves.add(i * 3 + j);

			}
		}
		return moves;
	}

	public int minmax(int player) throws IOException, java.lang.RuntimeException {

		if (checkWin(p2))
			return 100;
		else if (checkWin(p1))
			return -100;
		else if (this.getPossibleMoves().size() == 0)
			return 0;

		int score = 0;

		if (player == 2) {

			int bScore = Integer.MAX_VALUE;
			for (Integer possibleMove : this.getPossibleMoves()) {

				a[(int) (possibleMove / 3)][possibleMove % 3] = turn;
				turn = turn == 1 ? 2 : 1;

				score = minmax(1);

				if (score < bScore)
					bScore = score;

				turn = turn == 1 ? 2 : 1;
				a[(int) (possibleMove / 3)][possibleMove % 3] = 0;

			}

			return bScore;
		}

		if (player == 1) {

			int bScore = Integer.MIN_VALUE;
			for (Integer possibleMove : this.getPossibleMoves()) {

				a[(int) (possibleMove / 3)][possibleMove % 3] = turn;
				turn = turn == 1 ? 2 : 1;
				score = minmax(2);

				if (score > bScore)
					bScore = score;

				turn = turn == 1 ? 2 : 1;
				a[(int) (possibleMove / 3)][possibleMove % 3] = 0;

			}

			return bScore;
		}

		return score;
	}

	public void move() throws IOException {
		int k = this.getBestMove();
		if(k!=-1)
		{
		TicTacToe.a[(int) k / 3][k % 3] = 2;
		this.buttons[(int) k / 3][k % 3].setText("X");
		}

	}

	public int getBestMove() throws IOException {
		ArrayList<Integer> l = new ArrayList<>();
		ArrayList<Integer> lm = new ArrayList<>();
		int best = -1;
		Integer mm = null;
		for (Integer possibleMove : this.getPossibleMoves()) {

			a[(int) (possibleMove / 3)][possibleMove % 3] = turn;
			turn = turn == 1 ? 2 : 1;

			l.add(this.minmax(2));
			lm.add(possibleMove);

			a[(int) (possibleMove / 3)][possibleMove % 3] = 0;
			turn = turn == 1 ? 2 : 1;
		}
		int maxindex;
		if(l.size() !=0  )
		{
		int max = l.get(0);
		maxindex = lm.get(0);
		for (int i = 1; i < l.size(); i++) {
			if (max < l.get(i)) {
				max = l.get(i);
				maxindex = lm.get(i);

			}

		}
		}
		else
			{maxindex = -1;}
		return maxindex;
	}

}
