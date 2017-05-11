package PartOne.Main;

import PartOne.Submarine.Submarine;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;

import java.awt.*;
import java.awt.event.*;

/**
 * Created by Scott Richards on 03-Apr-17.
 */
public class Renderer_Part_1 implements GLEventListener, KeyListener, MouseWheelListener
{
	private static int WIN_WIDTH  = 1000;
	private static int WIN_HEIGHT = 1000;

	private GLU glu;

	private Camera camera;
	private int cameraDistance = 5;

	private Submarine submarine = new Submarine();

	public static void main(String[] args)
	{
		Frame frame = new Frame("Submarine");
		GLCanvas canvas = new GLCanvas();

		System.out.println("------------ Key mapping -----------");
		System.out.println("|  A/D: Left and Right side Views  |");
		System.out.println("|  T/U: Top and Bottom Views       |");
		System.out.println("|  W/S: Front and Back Views       |");
		System.out.println("|  Q  : Angled View                |");
		System.out.println("|  Scroll to zoom                  |");
		System.out.println("|                                  |");
		System.out.println("|  L  : Toggle Wire and Fill       |");
		System.out.println("\\----------------------------------/");

		Renderer_Part_1 app = new Renderer_Part_1();
		canvas.addGLEventListener(app);
		canvas.addKeyListener(app);
		canvas.addMouseWheelListener(app);

		frame.add(canvas);
		frame.setSize(WIN_WIDTH, WIN_HEIGHT);
		final Animator animator = new Animator(canvas);
		frame.addWindowListener(new WindowAdapter()
		{
			@Override public void windowClosing(WindowEvent e)
			{
				// Run this on another thread than the AWT event queue to
				// make sure the call to Animator.stop() completes before
				// exiting
				new Thread(new Runnable()
				{

					@Override public void run()
					{
						animator.stop();
						System.exit(0);
					}
				}).start();
			}
		});
		// Center frame
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		animator.start();
	}

	@Override public void init(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();

		// Enable VSync
		gl.setSwapInterval(1);

		// Setup the drawing area and shading mode
		gl.glClearColor(0, 0, 0, 0);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL2.GL_DEPTH_TEST);

		glu = new GLU();

		camera = new Camera();
		camera.setLookAt(cameraDistance, cameraDistance, cameraDistance);
	}

	@Override public void display(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();
		GLUT glut = new GLUT();

		// Clear the depth and color buffers
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		camera.draw(gl);
		Lights.renderLighting(gl);
		Origin.drawCoordinateAxes(gl, glut);

		// Draw PartOne.Submarine
		gl.glColor3d(0.27, 0.5, 0.7);
		submarine.draw(gl);

		gl.glFlush();
	}

	@Override public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
	{
		height = (height <= 0) ? 1 : height; // Avoid divide by zero
		WIN_WIDTH = width;
		WIN_HEIGHT = height;

		GL2 gl = drawable.getGL().getGL2();

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45, (double) width / height, 1, 30);
		camera.newWindowSize(WIN_WIDTH, WIN_HEIGHT);
	}

	@Override public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		switch (key)
		{
			case KeyEvent.VK_A: // Left View
				camera.setLookAt(cameraDistance, 0, 0);
				break;
			case KeyEvent.VK_D: // Right View
				camera.setLookAt(-cameraDistance, 0, 0);
				break;
			case KeyEvent.VK_T: // Top View
				camera.setLookAt(0, cameraDistance, 0);
				break;
			case KeyEvent.VK_U: // Bottom View
				camera.setLookAt(0, -cameraDistance, 0);
				break;
			case KeyEvent.VK_W: // Front View
				camera.setLookAt(0, 0, cameraDistance);
				break;
			case KeyEvent.VK_S: // Back View
				camera.setLookAt(0, 0, -cameraDistance);
				break;
			case KeyEvent.VK_Q: // Angled View
				camera.setLookAt(cameraDistance, cameraDistance, cameraDistance);
				break;
			case KeyEvent.VK_L:
				submarine.toggleWired();
				break;
		}
	}

	@Override public void keyReleased(KeyEvent e)
	{
	}

	@Override public void keyTyped(KeyEvent e)
	{
	}

	@Override public void dispose(GLAutoDrawable arg0)
	{
	}

	@Override public void mouseWheelMoved(MouseWheelEvent e)
	{
		int clicks = e.getWheelRotation();
		// zoom using the FoV
		while (clicks > 0)
		{
			camera.fov *= 1.1;
			clicks--;
		}
		while (clicks < 0)
		{
			camera.fov /= 1.1;
			clicks++;
		}

		camera.limitFieldOfView();
	}
}