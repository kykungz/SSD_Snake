package main;

import java.util.ArrayList;

import lib.Block;

public class Snake {
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;

	private ArrayList<Block> body;
	private int direction;

	public Snake() {
		direction = UP;
		body = new ArrayList<>();
	}

	public Snake(int x, int y) {
		this();
		body.add(new Block(x, y));
		body.add(new Block(x, y - 1));
		body.add(new Block(x, y - 2));
		body.add(new Block(x, y - 3));
	}

	public void turn(int direction) {
		switch (direction) {
		case UP:
			if (this.direction != DOWN)
				this.direction = direction;
			break;
		case DOWN:
			if (this.direction != UP)
				this.direction = direction;
			break;
		case LEFT:
			if (this.direction != RIGHT)
				this.direction = direction;
			break;
		case RIGHT:
			if (this.direction != LEFT)
				this.direction = direction;
			break;
		default:
			break;
		}
	}

	public void grow() {
		body.add(getNextBlock());
	}

	public int getDirection() {
		return direction;
	}

	public void move() {
		Block tail = body.get(0);
		body.remove(tail);
		body.add(getNextBlock());
	}

	public boolean hitSelf() {
		return body.subList(0, body.size() - 1).contains(this.getHead());
	}

	public ArrayList<Block> getBody() {
		return body;
	}

	public Block getHead() {
		return body.get(body.size() - 1);
	}

	private Block getNextBlock() {
		Block head = getHead();
		switch (direction) {
		case UP:
			return new Block(head.getX(), head.getY() - 1);
		case DOWN:
			return new Block(head.getX(), head.getY() + 1);
		case LEFT:
			return new Block(head.getX() - 1, head.getY());
		case RIGHT:
			return new Block(head.getX() + 1, head.getY());
		default:
			return null;
		}
	}
}
