package PartTwo.Main;

import com.jogamp.opengl.GL2;

/**
 * Created by Scott on 3/05/2017.
 */
public class Grid
{
	public int gridYLevel; // what y level the grid will be drawn

	private double tileSize = 0.5;
	private int    gridMax; // How many tiles you want to emerge in each direction from the origin
	private double red;
	private double green;
	private double blue;
	private double transparency;

	private int drawingStyle; // filled or grid

	public Grid(int gridSize, int height, double r, double g, double b, double tra)
	{
		gridMax = gridSize;
		gridYLevel = height;
		red = r;
		green = g;
		blue = b;
		transparency = tra;

		drawingStyle = GL2.GL_QUADS;
	}

	public void draw(GL2 gl)
	{

		drawTileGrid(gl);
	}

	private void drawTile(GL2 gl, double startX, double startY)
	{
		if (transparency > 0) // enable or disable features if the grid is transparent
		{
			gl.glDisable(GL2.GL_DEPTH_BUFFER_BIT);
			gl.glEnable(GL2.GL_BLEND);
		}

		gl.glBegin(drawingStyle);

		gl.glColor4d(red, green, blue, transparency);

		gl.glNormal3d(0, 1, 0);
		gl.glVertex3d(startX, gridYLevel, startY);
		gl.glNormal3d(0, 1, 0);
		gl.glVertex3d(startX + tileSize, gridYLevel, startY);
		gl.glNormal3d(0, 1, 0);
		gl.glVertex3d(startX + tileSize, gridYLevel, startY + tileSize);
		gl.glNormal3d(0, 1, 0);
		gl.glVertex3d(startX, gridYLevel, startY + tileSize);

		gl.glEnd();

		if (transparency > 0) // enable or disable features if the grid is transparent
		{
			gl.glDisable(GL2.GL_BLEND);
			gl.glEnable(GL2.GL_DEPTH_BUFFER_BIT);
		}
	}

	private void drawTileGrid(GL2 gl)
	{
		for (double i = -gridMax * tileSize; i < gridMax * tileSize; i += tileSize)
		{
			for (double j = -gridMax * tileSize; j < gridMax * tileSize; j += tileSize)
			{
				drawTile(gl, i, j);
			}
		}
	}

	public void toggleDrawingStyle()
	{
		if (drawingStyle == GL2.GL_QUADS)
		{
			drawingStyle = GL2.GL_LINE_STRIP;
		}
		else
		{
			drawingStyle = GL2.GL_QUADS;
		}
	}
}
