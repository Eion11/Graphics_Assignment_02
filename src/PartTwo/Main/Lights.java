package PartTwo.Main;

import com.jogamp.opengl.GL2;

/**
 * Created by Scott on 29/04/2017.
 */
class Lights
{
	static void renderLighting(GL2 gl, double cameraPosition)
	{
		float ambient[] = { 0, 0, 0.2f, 1 };
		float diffuse[] = { 0.1f, 0.1f, 0.2f, 1 };
		float specular[] = { 1, 1, 1, 1 };
		float position0[] = { 1, 1, 1, 0 };
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, position0, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specular, 0);

		// enable lights
		gl.glEnable(GL2.GL_LIGHT0);

		// draw using standard glColor
		gl.glEnable(GL2.GL_COLOR_MATERIAL);

		//fog
		if (cameraPosition > 15)
		{
			gl.glDisable(GL2.GL_LIGHT1);
			float fogColor[] = { 0.5f, 0.6f, 1f, 1 };
			gl.glFogfv(GL2.GL_FOG_COLOR, fogColor, 0);
			gl.glFogf(GL2.GL_FOG_START, 30);
			gl.glFogf(GL2.GL_FOG_END, 80);
		}
		else
		{
			gl.glEnable(GL2.GL_LIGHT1);
			float fogColor[] = { 0, 0.3f, 0.5f, 1 };
			gl.glFogfv(GL2.GL_FOG_COLOR, fogColor, 0);
			gl.glFogf(GL2.GL_FOG_START, 10);
			gl.glFogf(GL2.GL_FOG_END, 50);
		}
		gl.glFogf(GL2.GL_FOG_MODE, GL2.GL_LINEAR);

		gl.glEnable(GL2.GL_FOG);

		// top light
		float[] lightPosition  = { 100, 15, 0, 1 };
		float[] lightDirection = { 0, 1, 0 };
		float[] lightDiffuse   = { 0.5f, 0.5f, 0.5f, 1 };

		gl.glLightf(GL2.GL_LIGHT2, GL2.GL_SPOT_CUTOFF, 90);
		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_POSITION, lightPosition, 0);
		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_SPOT_DIRECTION, lightDirection, 0);
		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_DIFFUSE, lightDiffuse, 0);

		gl.glEnable(GL2.GL_LIGHT2);
	}
}
