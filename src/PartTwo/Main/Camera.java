package PartTwo.Main;

import PartTwo.Submarine.Submarine;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Camera implements MouseWheelListener
{
	double cameraDistance = 20;
	private GLU glu = new GLU();

	private double windowWidth     = 1;
	private double windowHeight    = 1;
	private double fov             = 45;
	private double viewingDistance = 350;
	private double lookAt[]        = { 0, 0, 0 };
	public  double eye[]           = { 0, 0, 0 };
	private double up[]            = { 0, 1, 0 };

	public void draw(GL2 gl, Submarine submarine)
	{
		// Projection Setup
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(fov, (windowWidth / windowHeight), 0.1, viewingDistance);

		// Camera submarinePosition and Orientation
		setCameraPosition(submarine);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluLookAt(eye[0], eye[1], eye[2], lookAt[0], lookAt[1], lookAt[2], up[0], up[1], up[2]);
	}

	public void setCameraPosition(Submarine submarine)
	{
		// The Submarine Position
		double subX = submarine.submarineMovement.submarinePosition.x;
		double subY = submarine.submarineMovement.submarinePosition.y;
		double subZ = submarine.submarineMovement.submarinePosition.z;
		double rotation = submarine.body.getRotation();

		// Make the camera look at the Submarine
		setLookAt(subX, subY, subZ);

		// Make the camera behind the Submarine
		double r = cameraDistance * Math.cos(Math.toRadians(25));
		double eyeX = r * Math.sin(Math.toRadians(rotation));
		double eyeY = cameraDistance * Math.sin(Math.toRadians(25));
		double eyeZ = r * Math.cos(Math.toRadians(rotation));

		setEye(eyeX + subX, eyeY + subY, eyeZ + subZ);
	}

	public void setEye(double x, double y, double z)
	{
		eye = new double[] { x, y, z };
	}

	public void setLookAt(double x, double y, double z)
	{
		lookAt = new double[] { x, y, z };
	}

	void newWindowSize(int width, int height)
	{
		windowWidth = Math.max(1.0, width);
		windowHeight = Math.max(1.0, height);
	}

	@Override public void mouseWheelMoved(MouseWheelEvent e)
	{
		int clicks = e.getWheelRotation();

		// Zooms the camera in and out
		while (clicks > 0)
		{
			cameraDistance *= 1.1;
			limitCameraDistance();
			clicks--;
		}
		while (clicks < 0)
		{
			cameraDistance /= 1.1;
			limitCameraDistance();
			clicks++;
		}
	}

	private void limitCameraDistance()
	{
		// Restrict the camera so it dose'nt go to far forward/backward
		if (cameraDistance > 50)
		{
			cameraDistance = 50;
		}
		else if (cameraDistance < 8)
		{
			cameraDistance = 8;
		}
	}
}