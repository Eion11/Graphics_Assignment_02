package Main;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Scott Richards on 03-Apr-17.
 */
public class Renderer implements GLEventListener, KeyListener
{
	private static int WIN_WIDTH  = 1000;
	private static int WIN_HEIGHT = 1000;

	private GLU        glu;
	private GLUquadric quadrant;

	private boolean wired          = true;
	private int     cameraDistance = 5;
	private int     cameraX        = cameraDistance;
	private int     cameraY        = cameraDistance;
	private int     cameraZ        = cameraDistance;

	private Submarine submarine = new Submarine();
	private Point transPoint = new Point();
	private double rotAdjust = 0;

	public static void main(String[] args)
	{
		Frame frame = new Frame("Transformations");
		GLCanvas canvas = new GLCanvas();

		System.out.println("Key mapping:");
		System.out.println("1-7: Camera Position");
		System.out.println("T  : Toggle Wired");

		Renderer app = new Renderer();
		canvas.addGLEventListener(app);
		canvas.addKeyListener(app);

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

	@Override public void display(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();
		GLUT glut = new GLUT();

		// Clear the depth and color buffers
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		// Initialize the model/view matrix
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

		// Position the camera
		glu.gluLookAt(cameraX, cameraY, cameraZ, 0, 0, 0, 0, 1, 0);

		// Set up lights
		Lights.renderLighting(gl);

		// Draw the coordinate axes
		Origin.drawCoordinateAxes(gl, glut);

		// Draw Submarine
		gl.glColor3d(0, 1, 0);

		if (wired != true)
		{
			submarine.drawSubmarine(gl, glut, true, rotAdjust);
		}
		else
		{
			submarine.drawSubmarine(gl, glut, false, rotAdjust);
		}

		updateAnimation();

		// Flush all drawing operations to the graphics card
		gl.glFlush();
	}

	@Override public void init(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();

		// Enable VSync
		gl.setSwapInterval(1);

		// Setup the drawing area and shading mode
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL2.GL_DEPTH_TEST);

		glu = new GLU();
		quadrant = glu.gluNewQuadric();
	}

	@Override public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
	{
		WIN_WIDTH = width;
		height = (height <= 0) ? 1 : height; // Avoid divide by zero
		WIN_HEIGHT = height;

		GL2 gl = drawable.getGL().getGL2();

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45, (double) width / height, 1, 10);
	}

	private void updateAnimation()
	{
		rotAdjust += 0.2;

		if (rotAdjust > 360)
		{
			rotAdjust = 0;
		}
	}

	@Override public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		switch (key)
		{
			case KeyEvent.VK_W:
				submarine.moveForward();
				break;
			case KeyEvent.VK_S:
				submarine.moveBackward();
				break;
			case KeyEvent.VK_T:
				if (wired == true)
				{
					wired = false;
				}
				else
				{
					wired = true;
				}
				break;
			case KeyEvent.VK_1:
				if (cameraX < 0 || cameraY < 0 || cameraZ < 0)
				{
					cameraX = cameraDistance;
					cameraY = cameraDistance;
					cameraZ = cameraDistance;
				}
				else
				{
					cameraX = -cameraDistance;
					cameraY = -cameraDistance;
					cameraZ = -cameraDistance;
				}
				break;
			case KeyEvent.VK_2:
				if (cameraX < 0 || cameraY < 0 || cameraZ < 0)
				{
					cameraX = cameraDistance;
					cameraY = cameraDistance;
					cameraZ = 0;
				}
				else
				{
					cameraX = -cameraDistance;
					cameraY = -cameraDistance;
					cameraZ = 0;
				}
				break;
			case KeyEvent.VK_3:
				if (cameraX < 0 || cameraY < 0 || cameraZ < 0)
				{
					cameraX = cameraDistance;
					cameraY = 0;
					cameraZ = cameraDistance;
				}
				else
				{
					cameraX = -cameraDistance;
					cameraY = 0;
					cameraZ = -cameraDistance;
				}
				break;
			case KeyEvent.VK_4:
				if (cameraX < 0 || cameraY < 0 || cameraZ < 0)
				{
					cameraX = 0;
					cameraY = cameraDistance;
					cameraZ = cameraDistance;
				}
				else
				{
					cameraX = 0;
					cameraY = -cameraDistance;
					cameraZ = -cameraDistance;
				}
				break;
			case KeyEvent.VK_5:
				if (cameraX < 0 || cameraY < 0 || cameraZ < 0)
				{
					cameraX = cameraDistance * 2;
					cameraY = 0;
					cameraZ = 0;
				}
				else
				{
					cameraX = -(cameraDistance * 2);
					cameraY = 0;
					cameraZ = 0;
				}
				break;
			case KeyEvent.VK_6:
				if (cameraX < 0 || cameraY < 0 || cameraZ < 0)
				{
					cameraX = 0;
					cameraY = 0;
					cameraZ = cameraDistance * 2;
				}
				else
				{
					cameraX = 0;
					cameraY = 0;
					cameraZ = -(cameraDistance * 2);
				}
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
}