package PartTwo.Submarine;

import PartTwo.Main.Position;
import com.jogamp.opengl.GL2;

/**
 * Created by Scott Richards on 22-May-17.
 */
public class SubmarineSpotlight extends SubmarinePart
{
	private float[] spotlightPosition  = { 0, -1.5f, 0, 1 };
	private float[] spotlightDiffuse   = { 1, 1, 1, 1 };
	private float[] spotlightDirection = { 0, -1, 0 };
	private float[] spotlightSpecular  = { 1, 1, 1, 1 };
	private int     spotlightAngle     = 60;

	private Position submarinePosition;
	private float lightIntensity = 1;

	public SubmarineSpotlight(Position submarinePosition)
	{
		this.submarinePosition = submarinePosition;

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

		setLightIntensity((float) ((-0.15 * submarinePosition.y) + 1));
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, spotlightDiffuse, 0);
		gl.glLightf(GL2.GL_LIGHT1, GL2.GL_SPOT_CUTOFF, spotlightAngle);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, spotlightPosition, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPOT_DIRECTION, spotlightDirection, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, spotlightSpecular, 0);
		gl.glPopMatrix();
	}

	private void setLightIntensity(float lightIntensity)
	{
		this.lightIntensity = lightIntensity;

		if (this.lightIntensity < 0)
		{
			this.lightIntensity = 0;
		}

		spotlightDiffuse = new float[] { lightIntensity, lightIntensity, lightIntensity, 1 };

	}
}
