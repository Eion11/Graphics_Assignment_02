package PartTwo.Main;

import com.jogamp.opengl.GL2;

/**
 * Created by Scott on 29/04/2017.
 */
class Lights
{
	public static void renderLighting(GL2 gl, double cameraPosition, int waterHeightLevel)
	{
		globalLighting(gl);
		fogLighting(gl, cameraPosition, waterHeightLevel);
		sunLighting(gl);

		// enable the things
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_LIGHT1);
		gl.glEnable(GL2.GL_LIGHT2);
		gl.glEnable(GL2.GL_FOG);
	}

	private static void globalLighting(GL2 gl)
	{
		float globalAmbiance[] = { 0, 0, 0.2f, 1 };
		float globalDiffuse[] = { 0.1f, 0.1f, 0.2f, 1 };
		float globalSpecular[] = { 1, 1, 1, 1 };
		float globalPosition[] = { 1, 1, 1, 0 };
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, globalPosition, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, globalAmbiance, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, globalDiffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, globalSpecular, 0);
	}

	private static void fogLighting(GL2 gl, double cameraPosition, int waterHeightLevel)
	{
		gl.glFogf(GL2.GL_FOG_MODE, GL2.GL_LINEAR);

		if (cameraPosition > waterHeightLevel) // above the water
		{
			float fogColor[] = { 0.5f, 0.6f, 1f, 1 };
			gl.glFogfv(GL2.GL_FOG_COLOR, fogColor, 0);
			gl.glFogf(GL2.GL_FOG_START, 30);
			gl.glFogf(GL2.GL_FOG_END, 80);
		}
		else // below the water
		{
			float fogColor[] = { 0, 0.3f, 0.5f, 1 };
			gl.glFogfv(GL2.GL_FOG_COLOR, fogColor, 0);
			gl.glFogf(GL2.GL_FOG_START, 10);
			gl.glFogf(GL2.GL_FOG_END, 50);
		}
	}

	private static void sunLighting(GL2 gl)
	{
		float[] sunPosition = { 100, 15, 0, 1 };
		float[] sunDirection = { 0, 1, 0 };
		float[] sunDiffuse = { 0.5f, 0.5f, 0.5f, 1 };
		gl.glLightf(GL2.GL_LIGHT2, GL2.GL_SPOT_CUTOFF, 90);
		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_POSITION, sunPosition, 0);
		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_SPOT_DIRECTION, sunDirection, 0);
		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_DIFFUSE, sunDiffuse, 0);
	}
}
