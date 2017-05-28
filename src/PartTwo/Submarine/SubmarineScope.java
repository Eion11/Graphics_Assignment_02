package PartTwo.Submarine;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

/**
 * Created by Scott Richards on 21-May-17.
 */
public class SubmarineScope extends SubmarinePart
{
	private GLU        glu;
	private GLUquadric quadric;

	SubmarineScope(RotationAxis axis)
	{
		this.axis = axis;

		glu = new GLU();
	}

	@Override public void drawPart(GL2 gl)
	{
		quadric = glu.gluNewQuadric();

		gl.glPushMatrix();

		glu.gluQuadricDrawStyle(quadric, drawingStyle);

		gl.glRotated(90, 1, 0, 0);
		gl.glScaled(0.2, 0.2, 0.5);
		glu.gluCylinder(quadric, 1, 1, 1, 100, 100);

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

	@Override public void rotateSecondary(GL2 gl)
	{

	}

	public void setRotation(double rotation)
	{
		this.rotation = rotation;
	}
}
