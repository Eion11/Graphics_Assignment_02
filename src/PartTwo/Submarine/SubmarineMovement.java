package PartTwo.Submarine;

import PartOne.Main.Point;
import PartTwo.Main.Grid;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Scott on 4/05/2017.
 */
public class SubmarineMovement implements KeyListener
{
	private final double submarineMovementSpeed = 0.2;
	private final double submarineUpDownSlower  = 2; // This variable slows down up/down: MovementSpeed / thisNumber
	private final double submarineRotateSpeed   = 1;

	private double maxHeight; // max height the submarine can go
	private double minHeight; // min height the submarine can go

	private Submarine submarine;
	private double 	  propellerRotation = 0;
	public  Point     submarinePosition;

	private Grid water;
	private Grid floor;

	public SubmarineMovement(Submarine submarine, Grid water, Grid floor)
	{
		this.submarine = submarine;
		this.submarinePosition = new Point();

		this.water = water;
		this.floor = floor;
		this.maxHeight = water.gridYLevel;
		this.minHeight = floor.gridYLevel + 1;
	}

	public void moveSubmarine(MovementPositions position)
	{
		switch (position)
		{
			case UP:
				moveSubmarineUpDown(submarineMovementSpeed / submarineUpDownSlower);
				break;
			case DOWN:
				moveSubmarineUpDown(-submarineMovementSpeed / submarineUpDownSlower);
				break;
			case FORWARD:
				moveSubmarineForwardsBackwards(submarineMovementSpeed);
				break;
			case BACKWARD:
				moveSubmarineForwardsBackwards(-submarineMovementSpeed);
				break;
			case ROTATE_LEFT:
				rotateSubmarineLeftRight(submarineRotateSpeed);
				break;
			case ROTATE_RIGHT:
				rotateSubmarineLeftRight(-submarineRotateSpeed);
				break;
		}
	}

	public void moveSubmarineUpDown(double moveAmount)
	{
		if (submarinePosition.y + moveAmount < maxHeight && submarinePosition.y > minHeight)
		{
			submarinePosition.y += moveAmount;
			submarine.body.setTranslation(submarinePosition.x, submarinePosition.y, submarinePosition.z);
		}
		else
		{
			if (submarinePosition.y < 0)
			{
				submarinePosition.y = minHeight + 0.001;
			}
			else
			{
				submarinePosition.y = maxHeight - 0.001;
			}
		}
	}

	public void moveSubmarineForwardsBackwards(double moveAmount)
	{
		submarinePosition.x -= (moveAmount * Math.sin(Math.toRadians(submarine.body.getRotation())));
		submarinePosition.z -= (moveAmount * Math.cos(Math.toRadians(submarine.body.getRotation())));
		submarine.body.setTranslation(submarinePosition.x, submarinePosition.y, submarinePosition.z);

		spinPropellers(moveAmount * 10);
	}

	public void rotateSubmarineLeftRight(double moveAmount)
	{
		submarine.body.adjustRotation(moveAmount);

		spinPropellers(moveAmount);
	}

	private void spinPropellers(double spinAmount)
	{
		propellerRotation += spinAmount;

		if (propellerRotation > 360)
		{
			propellerRotation = 0;
		}

		submarine.propeller1.setRotation(propellerRotation);
		submarine.propeller2.setRotation(propellerRotation);
		submarine.propeller3.setRotation(propellerRotation);
		submarine.propeller4.setRotation(propellerRotation);
	}

	@Override public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		switch (key)
		{
			case KeyEvent.VK_UP: // Up
				moveSubmarine(MovementPositions.UP);
				break;
			case KeyEvent.VK_DOWN: // Down
				moveSubmarine(MovementPositions.DOWN);
				break;
			case KeyEvent.VK_W: // Forwards
				moveSubmarine(MovementPositions.FORWARD);
				break;
			case KeyEvent.VK_S: // Backwards
				moveSubmarine(MovementPositions.BACKWARD);
				break;
			case KeyEvent.VK_A: // Rotate Left
				moveSubmarine(MovementPositions.ROTATE_LEFT);
				break;
			case KeyEvent.VK_D: // Rotate Right
				moveSubmarine(MovementPositions.ROTATE_RIGHT);
				break;
			case KeyEvent.VK_L: // Toggle Wired
				submarine.toggleWired();
				water.toggleDrawingStyle();
				floor.toggleDrawingStyle();
				break;
		}
	}

	@Override public void keyReleased(KeyEvent e)
	{
	}

	@Override public void keyTyped(KeyEvent e)
	{
	}
}
