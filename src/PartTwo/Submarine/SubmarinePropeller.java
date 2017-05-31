package PartTwo.Submarine;

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

	private int displayList = -1;

	SubmarinePropeller(RotationAxis axis, int initialRotation)
	{
		this.axis = axis;
		this.initialRotation = initialRotation;

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
		glu.gluQuadricDrawStyle(quadric, drawingStyle);
		gl.glRotated(90, 1, 0, 0);
		gl.glRotated(initialRotation, 0, 1, 0);
		gl.glScaled(0.5, 0.1, 1);
		glu.gluCylinder(quadric, 0, 1, 1, 100, 100);

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

	}

	public void setRotation(double rotation)
	{
		this.rotation = rotation;
	}
}

