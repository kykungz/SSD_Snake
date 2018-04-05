package main;

import java.util.List;
import java.util.Random;

import lib.AbstractGame;
import lib.Block;
import lib.Map;

public class SnakeGame extends AbstractGame {

	private Random random = new Random();
	private Map map;
	private Snake snake;
	private Block apple;

	public SnakeGame() {
		map = new Map();
		apple = new Block(random.nextInt(map.getSize()), random.nextInt(map.getSize()));
		snake = new Snake(1, 20);
		updateMap();
	}

	public int getMapSize() {
		return map.getSize();
	}

	public List<Block> getBlocks() {
		return map.getBlocks();
	}

	@Override
	protected void gameLogic() {
		if (snake.getHead().equals(apple)) {
			resetApple();
			snake.grow();
		} else {
			snake.move();
		}

		if (snake.hitSelf() || outOfBound()) {
			this.end();
		}

		updateMap();
	}

	private boolean outOfBound() {
		return !(snake.getHead().getX() <= map.getSize() - 1 && snake.getHead().getX() >= 0
				&& snake.getHead().getY() <= map.getSize() - 1 && snake.getHead().getY() >= 0);
	}

	private void resetApple() {
		apple = new Block(random.nextInt(map.getSize()), random.nextInt(map.getSize()));
		if (snake.getBody().contains(apple)) {
			resetApple();
		}
	}

	private void updateMap() {
		map.getBlocks().clear();
		map.getBlocks().addAll(snake.getBody());
		map.addBlock(apple);
	}

	@Override
	protected void handleLeftKey() {
		snake.turn(Snake.LEFT);
	}

	@Override
	protected void handleRightKey() {
		snake.turn(Snake.RIGHT);
	}

	@Override
	protected void handleUpKey() {
		snake.turn(Snake.UP);
	}

	@Override
	protected void handleDownKey() {
		snake.turn(Snake.DOWN);
	}

	public Memento saveToMemento() {
		return new Memento(this.map, this.snake, this.apple);
	}

	public void load(Memento savedState) {
		this.map = savedState.getMap();
		this.snake = savedState.getSnake();
		this.apple = savedState.getApple();
	}

	public static class Memento {
		private Map savedMap;
		private Snake savedSnake;
		private Block savedApple;

		public Memento(Map savedMap, Snake savedSnake, Block savedApple) {
			this.savedMap = new Map();
			this.savedMap.getBlocks().addAll(savedMap.getBlocks());

			this.savedSnake = new Snake();
			this.savedSnake.turn(savedSnake.getDirection());
			this.savedSnake.getBody().clear();
			this.savedSnake.getBody().addAll(savedSnake.getBody());

			this.savedApple = new Block(savedApple.getX(), savedApple.getY());
		}

		public Snake getSnake() {
			Snake snake = new Snake();
			snake.getBody().clear();
			snake.getBody().addAll(this.savedSnake.getBody());
			snake.turn(this.savedSnake.getDirection());
			return snake;
		}

		public Block getApple() {
			Block apple = new Block(this.savedApple.getX(), this.savedApple.getY());
			return apple;
		}

		public Map getMap() {
			Map map = new Map();
			map.getBlocks().addAll(savedMap.getBlocks());
			return savedMap;
		}
	}

}
