package PartOne.Submarine;

import PartOne.Main.Point;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

/**
 * Created by Scott on 3/05/2017.
 */
public class SubmarineBody extends TreeNode
{
	// pointer to quadric object
	private GLU        glu;
	private GLUquadric quadric;
	private int        drawingStyle;

	// rotation parameters
	private RotationAxis axis;
	private double       theta;

	// translation parameters
	private Point trans = new Point();

	// initialize the robot part
	SubmarineBody(RotationAxis axis)
	{
		this.axis = axis;

		glu = new GLU();
		drawingStyle = GLU.GLU_LINE;
	}

	@Override public void drawNode(GL2 gl)
	{
		quadric = glu.gluNewQuadric();

		gl.glPushMatrix();

		glu.gluQuadricDrawStyle(quadric, drawingStyle);

		gl.glScaled(1, 1, 1.5);
		glu.gluSphere(quadric, 1, 20, 20);

		gl.glPopMatrix();
	}

	@Override public void transformNode(GL2 gl)
	{
		gl.glTranslated(trans.x, trans.y, trans.z);

		switch (axis)
		{
			case X:  // X axis
				gl.glRotated(theta, 1, 0, 0);
				break;
			case Y:  // Y axis
				gl.glRotated(theta, 0, 1, 0);
				break;
			case Z:  // Z axis
				gl.glRotated(theta, 0, 0, 1);
				break;
		}
	}

	public void setTranslation(double x, double y, double z)
	{
		trans.x = x;
		trans.y = y;
		trans.z = z;
	}

	public double getRotation()
	{
		return theta;
	}

	public void setRotation(double theta)
	{
		this.theta = theta;
	}

	public void toggleDrawingStyle()
	{
		if (drawingStyle == GLU.GLU_LINE)
		{
			drawingStyle = GLU.GLU_FILL;
		}
		else
		{
			drawingStyle = GLU.GLU_LINE;
		}
	}
}
