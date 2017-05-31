package PartTwo.Main;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Scott on 3/05/2017.
 */
public class Grid
{
	private final String TEXTURE_GROUND_FILE_LOCATION = "resources/texture_ground.jpg";
	private final String TEXTURE_WATER_FILE_LOCATION  = "resources/texture_water.jpg";
	public  int     gridYLevel; // what y level the grid will be drawn
	private int     displayList;
	private Texture textureGround;

	private double tileSize = 8;
	private int    gridMax  = 40; // How many tiles you want to emerge in each direction from the origin

	private double transparency;

	public Grid(int height, double tra)
	{
		displayList = -1;

		gridYLevel = height;
		transparency = tra;
		if (transparency != 1)
		{
			tileSize = 30;
			gridMax = 2;
		}
	}

	private float moveWater = 0;

	public void draw(GL2 gl)
	{
		if (displayList == -1)
		{
			initDisplayList(gl);
		}

		if (transparency == 1)
		{
			gl.glCallList(displayList);
		}
		else // makes the water move
		{
			gl.glPushMatrix();

			moveWater += 0.03f;
			gl.glTranslated(moveWater, 0, 0);
			gl.glCallList(displayList);

			gl.glPopMatrix();

			if (moveWater >= tileSize)
			{
				moveWater = 0;
			}
		}
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
			if (transparency != 1)
			{
				textureGround = TextureIO.newTexture(new File(TEXTURE_WATER_FILE_LOCATION), true);
			}
			else
			{
				textureGround = TextureIO.newTexture(new File(TEXTURE_GROUND_FILE_LOCATION), true);
			}
		}
		catch (IOException e)
		{
			System.err.println("...Error Reading Texture File...");
			e.printStackTrace();
		}

		for (double row = -gridMax * tileSize; row < gridMax * tileSize; row += tileSize)
		{
			for (double column = -gridMax * tileSize; column < gridMax * tileSize; column += tileSize)
			{
				drawTile(gl, row, column);
			}
		}
	}

	private void drawTile(GL2 gl, double x, double z)
	{
		if (transparency != 1) // enable or disable features if the grid is transparent
		{
			gl.glDisable(GL2.GL_DEPTH_BUFFER_BIT);
			gl.glEnable(GL2.GL_BLEND);
		}

		textureGround.bind(gl);
		textureGround.setTexParameterf(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
		textureGround.setTexParameterf(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);

		double xTextureStart = x;
		double xTextureEnd = x + 1;
		double yTextureStart = z;
		double yTextureEnd = z + 1;

		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBegin(GL2.GL_QUADS);

		gl.glColor4d(1, 1, 1, transparency);

		gl.glNormal3d(0, 1, 0);
		gl.glTexCoord2d(xTextureEnd, yTextureStart);
		gl.glVertex3d(x, gridYLevel, z);

		gl.glNormal3d(0, 1, 0);
		gl.glTexCoord2d(xTextureEnd, yTextureEnd);
		gl.glVertex3d(x + tileSize, gridYLevel, z);

		gl.glNormal3d(0, 1, 0);
		gl.glTexCoord2d(xTextureStart, yTextureEnd);
		gl.glVertex3d(x + tileSize, gridYLevel, z + tileSize);

		gl.glNormal3d(0, 1, 0);
		gl.glTexCoord2d(xTextureStart, yTextureStart);
		gl.glVertex3d(x, gridYLevel, z + tileSize);

		gl.glEnd();
		gl.glDisable(GL2.GL_TEXTURE_2D);

		if (transparency > 0) // enable or disable features if the grid is transparent
		{
			gl.glDisable(GL2.GL_BLEND);
			gl.glEnable(GL2.GL_DEPTH_BUFFER_BIT);
		}
	}
}
