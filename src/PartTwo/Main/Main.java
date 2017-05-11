package PartTwo.Main;

import PartTwo.Submarine.Submarine;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Scott on 4/05/2017.
 */
public class Main
{
	private static int WIN_WIDTH  = 1000;
	private static int WIN_HEIGHT = 1000;

	public static void main(String[] args)
	{
		printControls();

		Frame frame = new Frame("Submarine");
		GLCanvas canvas = new GLCanvas();

		Camera camera = new Camera();
		Grid water = new Grid(5, 8, 0.5, 0.8, 1, 0.5);
		Grid floor = new Grid(5, -2, 0.8, 0.5, 0.25, 0);
		Submarine submarine = new Submarine(water, floor);

		Renderer renderer = new Renderer(WIN_WIDTH, WIN_HEIGHT, camera, submarine, water, floor);

		setupFrame(frame, canvas);

		canvas.addGLEventListener(renderer);
		canvas.addKeyListener(submarine.submarineMovement);
		canvas.addMouseWheelListener(camera);
	}

	private static void printControls()
	{
		System.out.println("------------ Key mapping -----------");
		System.out.println("|  W/S: Forwards and Backwards     |");
		System.out.println("|  A/D: Rotating Left and Right    |");
		System.out.println("|  UP/DOWN: Up and Down            |");
		System.out.println("|                                  |");
		System.out.println("|  L  : Toggle Wire and Fill       |");
		System.out.println("|  Scroll to zoom                  |");
		System.out.println("\\----------------------------------/");
	}

	private static void setupFrame(Frame frame, Canvas canvas)
	{
		frame.add(canvas);
		frame.setSize(WIN_WIDTH, WIN_HEIGHT);
		final Animator animator = new Animator((GLAutoDrawable) canvas);
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

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		canvas.requestFocus();
		animator.start();
	}
}
