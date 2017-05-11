package PartTwo.Submarine;

import PartTwo.Main.Point;
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

	SubmarineBody(RotationAxis axis)
	{
		this.axis = axis;
		glu = new GLU();
	}

	@Override public void drawPart(GL2 gl)
	{
		quadric = glu.gluNewQuadric();

		gl.glPushMatrix();

		glu.gluQuadricDrawStyle(quadric, drawingStyle);
		gl.glScaled(1, 1, 1.5);
		glu.gluSphere(quadric, 1, 20, 20);

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

	public void adjustRotation(double adjustment)
	{
		rotation += adjustment;

		if (rotation > 360 || rotation < -360)
		{
			rotation = 0;
		}
	}
}
