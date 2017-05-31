package PartTwo.Main;

import PartTwo.Submarine.Submarine;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

/**
 * Created by Scott Richards on 03-Apr-17.
 */
public class Renderer implements GLEventListener
{
	private int WIN_WIDTH;
	private int WIN_HEIGHT;
	private GLU glu;

	private Camera    camera;
	private Submarine submarine;

	private Grid water;
	private Grid floor;

	public Renderer(int width, int height, Camera camera, Submarine submarine, Grid water, Grid floor)
	{
		WIN_WIDTH = width;
		WIN_HEIGHT = height;

		this.camera = camera;
		this.submarine = submarine;
		this.water = water;
		this.floor = floor;
	}

	@Override public void init(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();

		gl.setSwapInterval(1);
		gl.glClearColor(0, 0, 0, 0);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_NORMALIZE);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT1);

		glu = new GLU();
	}

	@Override public void display(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		camera.draw(gl, submarine);
		Lights.renderLighting(gl);
		Origin.drawCoordinateAxes(gl);

		// Draw Submarine
		submarine.draw(gl);
		floor.draw(gl);
		water.draw(gl);

		GLUquadric quadric = glu.gluNewQuadric();
		glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
		gl.glScaled(200, 200, 200);
		glu.gluSphere(quadric, 1, 20, 20);

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

	@Override public void dispose(GLAutoDrawable arg0)
	{
	}
}