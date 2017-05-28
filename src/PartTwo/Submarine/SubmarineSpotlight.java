package PartTwo.Submarine;

import com.jogamp.opengl.GL2;

/**
 * Created by Scott Richards on 22-May-17.
 */
public class SubmarineSpotlight extends SubmarinePart
{
	private float[] lightPosition  = { 0, -1, 1, 1 };
	private float[] lightDirection = { 0, -1, 0 };

	private float[] lightDiffuse = { 1, 1, 1, 1 };

	public SubmarineSpotlight()
	{
	}

	@Override public void transformPart(GL2 gl)
	{
		gl.glTranslated(position.x, position.y, position.z);
	}

	@Override public void rotateSecondary(GL2 gl)
	{

	}

	@Override public void drawPart(GL2 gl)
	{
		gl.glPushMatrix();

		gl.glLightf(GL2.GL_LIGHT1, GL2.GL_SPOT_CUTOFF, 30);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPosition, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPOT_DIRECTION, lightDirection, 0);

		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, lightDiffuse, 0);

		gl.glEnable(GL2.GL_LIGHT1);

		gl.glPopMatrix();
	}
}
