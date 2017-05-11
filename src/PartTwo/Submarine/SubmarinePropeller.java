package PartTwo.Submarine;

import PartTwo.Main.Point;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

/**
 * Created by Scott on 3/05/2017.
 */
public class SubmarinePropeller extends SubmarinePart
{
	private GLU        glu;
	private GLUquadric quadric;

	private int initialRotation;

	SubmarinePropeller(RotationAxis axis, int initialRotation)
	{
		this.axis = axis;
		this.initialRotation = initialRotation;

		glu = new GLU();
	}

	@Override public void drawPart(GL2 gl)
	{
		quadric = glu.gluNewQuadric();

		gl.glPushMatrix();

		glu.gluQuadricDrawStyle(quadric, drawingStyle);

		gl.glRotated(90, 1, 0, 0);
		gl.glRotated(initialRotation, 0, 1, 0);
		gl.glScaled(0.5, 0.1, 1);
		glu.gluCylinder(quadric, 0, 1, 1, 20, 20);

		gl.glPopMatrix();
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

	public void setRotation(double rotation)
	{
		this.rotation = rotation;
	}
}

