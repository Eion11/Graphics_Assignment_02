package Test;

/**
 * A demonstration class for transformations
 *
 * @author Jacqueline Whalley
 */

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Transformations implements GLEventListener, KeyListener
{

	private static int WIN_WIDTH  = 500;
	private static int WIN_HEIGHT = 500;

	private Axis      axis      = Axis.X;
	private Operation operation = Operation.NONE;

	private boolean showSecondCube = false;
	private boolean enablePushPop  = true;

	private GLU glu;

	public static void main(String[] args)
	{
		Frame frame = new Frame("Transformations");
		GLCanvas canvas = new GLCanvas();

		System.out.println("Key mapping:");
		System.out.println("T: Translate");
		System.out.println("S: Scale");
		System.out.println("R: Rotate");
		System.out.println("0: Reset");
		System.out.println("X: X-Axis");
		System.out.println("Y: Y-Axis");
		System.out.println("Z: Z-Axis");
		System.out.println("2: Show/Hide second independent cube");
		System.out.println("P: Enable/Disable Push/Pop matrix");

		Transformations app = new Transformations();
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
	}

	@Override public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
	{
		WIN_WIDTH = width;
		height = (height <= 0) ? 1 : height; //avoid divide by zero
		WIN_HEIGHT = height;

		GL2 gl = drawable.getGL().getGL2();

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45, (double) width / height, 1, 10);
	}

	@Override public void display(GLAutoDrawable drawable)
	{

		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		// Initialize the model/view matrix
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

		// position the camera FIRST
		glu.gluLookAt(3, 3, 3, 0, 0, 0, 0, 1, 0);

		// draw the coordinate axes
		drawCoordinateAxes(gl);

		if (enablePushPop)
		{
			// save model/view matrix
			gl.glPushMatrix();
		}
		// do the transformations and the drawing of the animated cube
		drawTransformedCube(gl);

		if (enablePushPop)
		{
			// restore the model/view matrix
			gl.glPopMatrix();
		}

		if (showSecondCube)
		{
			double time = System.currentTimeMillis() / 1000.0;
			double angle = (time * 10) % 360;
			gl.glTranslated(1, 0, 1);
			gl.glRotated(angle, 0, 1, 0);
			gl.glScaled(0.5, 0.5, 0.5);
			drawColourCube(gl);
		}

		// Flush all drawing operations to the graphics card
		gl.glFlush();
	}

	private void drawTransformedCube(GL2 gl)
	{
		// transformation factor, alternating between -1 and 1
		double pos = Math.sin(System.currentTimeMillis() / 1000.0);

		double aX = 0; // axes components
		double aY = 0;
		double aZ = 0;

		// select the axis
		switch (axis)
		{
			case X:
				aX = 1.0;
				break;
			case Y:
				aY = 1.0;
				break;
			case Z:
				aZ = 1.0;
				break;
		}

		// apply the selected transformation to the matrix stack
		switch (operation)
		{
			case TRANSLATE:
				gl.glTranslated(aX * pos, aY * pos, aZ * pos);
				break;
			case SCALE:
				gl.glScaled(1 + (aX * pos), 1 + (aY * pos), 1 + (aZ * pos));
				break;
			case ROTATE:
				gl.glRotated(45 * pos, aX, aY, aZ);
				break;
			case NONE:
				break;
		}

		// draw the colour cube with the transformation applied to it
		drawColourCube(gl);
	}

	private void drawColourCube(GL2 gl)
	{

		final double[][] vertices = { { -1, -1, -1 }, { +1, -1, -1 }, { +1, +1, -1 }, { -1, +1, -1 }, { -1, -1, +1 },
				{ +1, -1, +1 }, { +1, +1, +1 }, { -1, +1, +1 } };
		final double[][] colours = { { 0, 0, 0 }, { 1, 0, 0 }, { 1, 1, 0 }, { 0, 1, 0 }, { 0, 0, 1 }, { 1, 0, 1 },
				{ 1, 1, 1 }, { 0, 1, 1 } };
		final int[][] faces = { { 0, 1, 2, 3 }, { 4, 5, 6, 7 }, { 0, 1, 5, 4 }, { 3, 2, 6, 7 }, { 1, 5, 6, 2 },
				{ 0, 4, 7, 3 } };

		gl.glBegin(GL2.GL_QUADS);

		for (int[] face : faces)
		{
			for (int vtx : face)
			{
				gl.glColor3dv(colours[vtx], 0);
				gl.glVertex3dv(vertices[vtx], 0);
			}
		}

		gl.glEnd();
	}

	private void drawCoordinateAxes(GL2 gl)
	{
		gl.glLineWidth(2.0f);
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3d(1, 0, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(2, 0, 0);
		gl.glColor3d(0, 1, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(0, 2, 0);
		gl.glColor3d(0, 0, 1);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(0, 0, 2);
		gl.glEnd();
	}

	@Override public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		switch (key)
		{
			case KeyEvent.VK_T:
				operation = Operation.TRANSLATE;
				break;
			case KeyEvent.VK_S:
				operation = Operation.SCALE;
				break;
			case KeyEvent.VK_R:
				operation = Operation.ROTATE;
				break;
			case KeyEvent.VK_0:
				operation = Operation.NONE;
				break;
			case KeyEvent.VK_X:
				axis = Axis.X;
				break;
			case KeyEvent.VK_Y:
				axis = Axis.Y;
				break;
			case KeyEvent.VK_Z:
				axis = Axis.Z;
				break;
			case KeyEvent.VK_2:
				showSecondCube = !showSecondCube;
				break;
			case KeyEvent.VK_P:
				enablePushPop = !enablePushPop;
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
		// TODO Auto-generated method stub

	}

	public enum Axis
	{
		X, Y, Z;
	}

	public enum Operation
	{
		NONE, TRANSLATE, ROTATE, SCALE;
	}

}