package PartOne.Main;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class Camera
{
	// GLU context
	private GLU glu = new GLU();

	// camera parameters
	double fov             = 45;
	private double viewingDistance = 30;
	private double windowWidth     = 1;
	private double windowHeight    = 1;

	// the point to look at
	private double lookAt[] = { 0, 0, 0 };
	private double eye[]    = { 0, 0, 0 };
	private double up[]     = { 0, 1, 0 };

	public void draw(GL2 gl)
	{
		// set up projection first
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(fov, (windowWidth / windowHeight), 0.1, viewingDistance);

		// then camera position and orientation
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluLookAt(eye[0], eye[1], eye[2], lookAt[0], lookAt[1], lookAt[2], up[0], up[1], up[2]);
	}

	public void setLookAt(double x, double y, double z)
	{
		eye = new double[] { x, y, z };

		// For when looking at the bottom or top, the eye cant be parallel
		if (eye[0] == 0 && eye[1] != 0 && eye[2] == 0)
		{
			up = new double[] { 1, 0, 0 };
		}
		else
		{
			up = new double[] { 0, 1, 0 };
		}
	}

	void newWindowSize(int width, int height)
	{
		windowWidth = Math.max(1.0, width);
		windowHeight = Math.max(1.0, height);
	}

	void limitFieldOfView()
	{
		if (fov < 1)
		{
			fov = 1;
		}
		if (fov > 160)
		{
			fov = 160;
		}
	}
}