package PartTwo.Submarine;

import PartOne.Main.Point;
import PartTwo.Main.Grid;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Scott on 4/05/2017.
 */
public class SubmarineMovement implements KeyListener, Runnable
{
	private final double submarineMovementSpeed = 0.2;
	private final double submarineUpDownSlower  = 2; // This variable slows down up/down: MovementSpeed / thisNumber
	private final double submarineRotateSpeed   = 1;

	private double maxHeight; // max height the submarine can go
	private double minHeight; // min height the submarine can go

	private Submarine submarine;
	private double propellerRotation = 0;
	public Point submarinePosition;

	private boolean up          = false;
	private boolean down        = false;
	private boolean forwards    = false;
	private boolean backwards   = false;
	private boolean rotateLeft  = false;
	private boolean rotateRight = false;

	public SubmarineMovement(Submarine submarine, Grid water, Grid floor)
	{
		this.submarine = submarine;
		this.submarinePosition = new Point();

		this.maxHeight = water.gridYLevel;
		this.minHeight = floor.gridYLevel + 1;

		Thread myThread = new Thread(this);
		myThread.start();
	}

	public void moveSubmarine()
	{
		// Up and Down
		if (up)
		{
			moveSubmarineUpDown(submarineMovementSpeed / submarineUpDownSlower);
		}
		else if (down)
		{
			moveSubmarineUpDown(-submarineMovementSpeed / submarineUpDownSlower);
		}

		// Forwards and Backwards
		if (forwards)
		{
			moveSubmarineForwardsBackwards(submarineMovementSpeed);
		}
		else if (backwards)
		{
			moveSubmarineForwardsBackwards(-submarineMovementSpeed);
		}

		// Left and Right (turning)
		if (rotateLeft)
		{
			rotateSubmarineLeftRight(submarineRotateSpeed);
		}
		else if (rotateRight)
		{
			rotateSubmarineLeftRight(-submarineRotateSpeed);
		}

		// to reset the rotations back to the default
		if ((!up && !down) || (submarinePosition.y < maxHeight && submarinePosition.y > minHeight))
		{
			resetSecondaryRotation(RotationAxis.X);
		}
		if (!rotateLeft && !rotateRight)
		{
			resetSecondaryRotation(RotationAxis.Z);
		}
	}

	private void resetSecondaryRotation(RotationAxis axis)
	{
		switch (axis)
		{
			case X:
				if (submarine.body.rotationSecondaryX > 0)
				{
					submarine.body.adjustRotationSecondary(-1, RotationAxis.X);
				}
				else if (submarine.body.rotationSecondaryX < 0)
				{
					submarine.body.adjustRotationSecondary(1, RotationAxis.X);
				}
				break;
			case Y:
				if (submarine.body.rotationSecondaryY > 0)
				{
					submarine.body.adjustRotationSecondary(-1, RotationAxis.Y);
				}
				else if (submarine.body.rotationSecondaryY < 0)
				{
					submarine.body.adjustRotationSecondary(1, RotationAxis.Y);
				}
				break;
			case Z:
				if (submarine.body.rotationSecondaryZ > 0)
				{
					submarine.body.adjustRotationSecondary(-1, RotationAxis.Z);
				}
				else if (submarine.body.rotationSecondaryZ < 0)
				{
					submarine.body.adjustRotationSecondary(1, RotationAxis.Z);
				}
				break;
		}

	}

	public void moveSubmarineUpDown(double moveAmount)
	{
		if (submarinePosition.y + moveAmount < maxHeight && submarinePosition.y + moveAmount > minHeight)
		{
			submarinePosition.y += moveAmount;
			submarine.body.setTranslation(submarinePosition.x, submarinePosition.y, submarinePosition.z);

			// Adjust Rotation
			if (forwards || backwards)
			{
				if (moveAmount > 0)
				{
					double rot = submarine.body.rotationSecondaryX;
					if (rot < 10)
					{
						submarine.body.adjustRotationSecondary(2, RotationAxis.X);
					}
					else if (rot < 15)
					{
						submarine.body.adjustRotationSecondary(0.8, RotationAxis.X);
					}
					else if (rot < 17)
					{
						submarine.body.adjustRotationSecondary(0.3, RotationAxis.X);
					}
					else if (rot < 19)
					{
						submarine.body.adjustRotationSecondary(0.1, RotationAxis.X);
					}
				}
				else if (moveAmount < 0)
				{
					double rot = submarine.body.rotationSecondaryX;
					if (rot > -10)
					{
						submarine.body.adjustRotationSecondary(-2, RotationAxis.X);
					}
					else if (rot > -15)
					{
						submarine.body.adjustRotationSecondary(-0.8, RotationAxis.X);
					}
					else if (rot > -17)
					{
						submarine.body.adjustRotationSecondary(-0.3, RotationAxis.X);
					}
					else if (rot > -19)
					{
						submarine.body.adjustRotationSecondary(-0.1, RotationAxis.X);
					}
				}
			}
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

		spinPropellers(moveAmount * 20);
	}

	public void rotateSubmarineLeftRight(double rotateAmount)
	{
		submarine.body.adjustRotation(rotateAmount);

		// Adjust Rotation
		if (rotateAmount > 0)
		{
			double rot = submarine.body.rotationSecondaryZ;
			if (rot < 10)
			{
				submarine.body.adjustRotationSecondary(1, RotationAxis.Z);
			}
			else if (rot < 15)
			{
				submarine.body.adjustRotationSecondary(0.5, RotationAxis.Z);
			}
			else if (rot < 17)
			{
				submarine.body.adjustRotationSecondary(0.3, RotationAxis.Z);
			}
			else if (rot < 19)
			{
				submarine.body.adjustRotationSecondary(0.1, RotationAxis.Z);
			}
		}
		else if (rotateAmount < 0)
		{
			double rot = submarine.body.rotationSecondaryZ;
			if (rot > -10)
			{
				submarine.body.adjustRotationSecondary(-1, RotationAxis.Z);
			}
			else if (rot > -15)
			{
				submarine.body.adjustRotationSecondary(-0.5, RotationAxis.Z);
			}
			else if (rot > -17)
			{
				submarine.body.adjustRotationSecondary(-0.3, RotationAxis.Z);
			}
			else if (rot > -19)
			{
				submarine.body.adjustRotationSecondary(-0.1, RotationAxis.Z);
			}
		}
	}

	private void spinPropellers(double rotateAmount)
	{
		propellerRotation += rotateAmount;

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
				up = true;
				break;
			case KeyEvent.VK_DOWN: // Down
				down = true;
				break;
			case KeyEvent.VK_W: // Forwards
				forwards = true;
				break;
			case KeyEvent.VK_S: // Backwards
				backwards = true;
				break;
			case KeyEvent.VK_A: // Rotate Left
				rotateLeft = true;
				break;
			case KeyEvent.VK_D: // Rotate Right
				rotateRight = true;
				break;
		}

	}

	@Override public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();

		switch (key)
		{
			case KeyEvent.VK_UP: // Up
				up = false;
				break;
			case KeyEvent.VK_DOWN: // Down
				down = false;
				break;
			case KeyEvent.VK_W: // Forwards
				forwards = false;
				break;
			case KeyEvent.VK_S: // Backwards
				backwards = false;
				break;
			case KeyEvent.VK_A: // Rotate Left
				rotateLeft = false;
				break;
			case KeyEvent.VK_D: // Rotate Right
				rotateRight = false;
				break;
		}
	}

	@Override public void keyTyped(KeyEvent e)
	{
	}

	@Override public void run()
	{
		while (true)
		{
			try
			{
				Thread.sleep(30);
				moveSubmarine();
			}
			catch (InterruptedException e)
			{
				System.out.println("--- Submarine Movement Thread Sleep Error ---");
			}
		}
	}
}
