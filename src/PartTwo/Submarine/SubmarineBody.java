package PartTwo.Submarine;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

/**
 * Created by Scott on 3/05/2017.
 */
public class SubmarineBody extends SubmarinePart
{
	private GLU        glu;
	private GLUquadric quadric;

	protected double rotationSecondaryX;
	protected double rotationSecondaryY;
	protected double rotationSecondaryZ;

	private int displayList = -1;

	SubmarineBody(RotationAxis axis)
	{
		this.axis = axis;
		glu = new GLU();
	}

	@Override public void drawPart(GL2 gl)
	{
		if (displayList == -1)
		{
			initDisplayList(gl);
		}

		gl.glPushMatrix();

		gl.glCallList(displayList);

		gl.glPopMatrix();
	}

	private void initDisplayList(GL2 gl)
	{
		displayList = gl.glGenLists(1);
		gl.glNewList(displayList, GL2.GL_COMPILE);

		quadric = glu.gluNewQuadric();
		glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
		gl.glScaled(1, 1, 1.5);
		glu.gluSphere(quadric, 1, 100, 100);

		gl.glEndList();
	}

	@Override public void transformPart(GL2 gl)
	{
		gl.glTranslated(position.x, position.y, position.z);

		switch (axis)
		{
			case X:  // X axis
				gl.glRotated(rotation, 1, 0, 0);
				break;
			case Y:  // Y axis
				gl.glRotated(rotation, 0, 1, 0);
				break;
			case Z:  // Z axis
				gl.glRotated(rotation, 0, 0, 1);
				break;
		}
	}

	@Override public void rotateSecondary(GL2 gl)
	{
		// X axis
		gl.glRotated(rotationSecondaryX, 1, 0, 0);

		// Y axis
		gl.glRotated(rotationSecondaryY, 0, 1, 0);

		// Z axis
		gl.glRotated(rotationSecondaryZ, 0, 0, 1);
	}

	public void adjustRotation(double adjustment)
	{
		rotation += adjustment;

		if (rotation > 360 || rotation < -360)
		{
			rotation = 0;
		}
	}

	public void adjustRotationSecondary(double adjustment, RotationAxis axisSecondary)
	{
		switch (axisSecondary)
		{
			case X:
				if (rotationSecondaryX + adjustment <= 20 && rotationSecondaryX + adjustment >= -20)
				{
					rotationSecondaryX += adjustment;
				}

				if (rotationSecondaryX < 1 && rotationSecondaryX > -1)
				{
					rotationSecondaryX = 0;
				}
				break;
			case Y:
				if (rotationSecondaryY + adjustment <= 15 && rotationSecondaryY + adjustment >= -15)
				{
					rotationSecondaryY += adjustment;
				}

				if (rotationSecondaryY < 1 && rotationSecondaryY > -1)
				{
					rotationSecondaryY = 0;
				}
				break;

			case Z:
				if (rotationSecondaryZ + adjustment <= 20 && rotationSecondaryZ + adjustment >= -20)
				{
					rotationSecondaryZ += adjustment;
				}

				if (rotationSecondaryZ < 1 && rotationSecondaryZ > -1)
				{
					rotationSecondaryZ = 0;
				}
				break;
		}

	}
}
