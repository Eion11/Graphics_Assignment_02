package PartTwo.Main;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;
import java.io.IOException;

/**
 * Created by Scott on 3/05/2017.
 */
public class Grid
{
	private int displayList;

	private final String ground_texture = "textures/grass_texture.jpg";
	private Texture textureGround;

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
		displayList = -1;

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
		if (displayList == -1)
		{
			initDisplayList(gl);
		}

		gl.glCallList(displayList);
	}

	private void initDisplayList(GL2 gl)
	{
		displayList = gl.glGenLists(1);
		gl.glNewList(displayList, GL2.GL_COMPILE);

		drawTileGrid(gl);

		gl.glEndList();
	}

	private void drawTileGrid(GL2 gl)
	{
		try
		{
			textureGround = TextureIO.newTexture(new File(ground_texture), true);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		for (double i = -gridMax * tileSize; i < gridMax * tileSize; i += tileSize)
		{
			for (double j = -gridMax * tileSize; j < gridMax * tileSize; j += tileSize)
			{
				drawTile(gl, i, j);
			}
		}
	}

	private void drawTile(GL2 gl, double startX, double startY)
	{
		if (transparency > 0) // enable or disable features if the grid is transparent
		{
			gl.glDisable(GL2.GL_DEPTH_BUFFER_BIT);
			gl.glEnable(GL2.GL_BLEND);
		}

		textureGround.bind(gl);
		textureGround.setTexParameterf(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
		textureGround.setTexParameterf(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);

		double k0 = startX;
		double k1 = startX + 1;
		double l0 = startY;
		double l1 = startY + 1;

		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBegin(drawingStyle);

		gl.glColor4d(red, green, blue, transparency);

		gl.glNormal3d(0, 1, 0);
		gl.glTexCoord2d(k1, l0);
		gl.glVertex3d(startX, gridYLevel, startY);

		gl.glNormal3d(0, 1, 0);
		gl.glTexCoord2d(k1, l1);
		gl.glVertex3d(startX + tileSize, gridYLevel, startY);

		gl.glNormal3d(0, 1, 0);
		gl.glTexCoord2d(k0, l1);
		gl.glVertex3d(startX + tileSize, gridYLevel, startY + tileSize);

		gl.glNormal3d(0, 1, 0);
		gl.glTexCoord2d(k0, l0);
		gl.glVertex3d(startX, gridYLevel, startY + tileSize);

		gl.glEnd();
		gl.glDisable(GL2.GL_TEXTURE_2D);

		if (transparency > 0) // enable or disable features if the grid is transparent
		{
			gl.glDisable(GL2.GL_BLEND);
			gl.glEnable(GL2.GL_DEPTH_BUFFER_BIT);
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
