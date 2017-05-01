package Main;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * Created by Scott on 29/04/2017.
 */
public class Origin
{
	private static int axesSize = 20;

	public static void drawCoordinateAxes(GL2 gl, GLUT glut)
	{
		gl.glLineWidth(2.0f);

		gl.glBegin(GL2.GL_LINES);

		gl.glColor3d(1, 0, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(axesSize, 0, 0);
		gl.glColor3d(0, 1, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(0, axesSize, 0);
		gl.glColor3d(0, 0, 1);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(0, 0, axesSize);

		gl.glEnd();

		gl.glColor3d(1, 1, 1);
		glut.glutSolidSphere(0.05, 10, 10);
	}
}
