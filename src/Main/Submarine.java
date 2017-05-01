package Main;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * Created by Scott on 29/04/2017.
 */
public class Submarine
{
	private Point transPoint;

	public Submarine()
	{
		transPoint = new Point();
	}

	public void moveForward()
	{
		transPoint.z += 0.1;
	}

	public void moveBackward()
	{
		transPoint.z -= 0.1;
	}

	public void drawSubmarine(GL2 gl, GLUT glut, boolean solid, double rotAdjust)
	{
		drawBody(gl, glut, solid);
		drawPropeller(gl, glut, solid, 0 + rotAdjust);
		drawPropeller(gl, glut, solid, 90 + rotAdjust);
		drawPropeller(gl, glut, solid, 180 + rotAdjust);
		drawPropeller(gl, glut, solid, 270 + rotAdjust);
	}

	private void drawBody(GL2 gl, GLUT glut, boolean solid)
	{
		gl.glPushMatrix();

		gl.glScaled(1, 1, 1.5);
		gl.glTranslated(transPoint.x, transPoint.y, transPoint.z);

		if (solid == true)
			glut.glutSolidSphere(1, 20, 20);
		else
			glut.glutWireSphere(1, 20, 20);

		gl.glPopMatrix();
	}

	private void drawPropeller(GL2 gl, GLUT glut, boolean solid, double rotation)
	{
		gl.glPushMatrix();

		gl.glRotated(90, 1, 0, 0);
		gl.glRotated(rotation, 0, 1, 0);
		gl.glScaled(0.5, 0.1, 1);
		gl.glTranslated(transPoint.x, transPoint.x + 15, transPoint.z - 1);

		if (solid == true)
		{
			glut.glutSolidCone(1, 1, 20, 20);
		}
		else
		{
			glut.glutWireCone(1, 1, 20, 20);
		}

		gl.glPopMatrix();
	}
}
