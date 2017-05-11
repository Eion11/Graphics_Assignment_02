package PartOne.Submarine;

import com.jogamp.opengl.GL2;

/**
 * Created by Scott on 29/04/2017.
 */
public class Submarine
{
	private SubmarineBody      body;
	private SubmarinePropeller propeller1;
	private SubmarinePropeller propeller2;
	private SubmarinePropeller propeller3;
	private SubmarinePropeller propeller4;

	private double rotation = 0;

	public Submarine()
	{
		createSubmarine();
	}

	private void createSubmarine()
	{
		propeller1 = new SubmarinePropeller(RotationAxis.Z, 0);
		propeller1.setTranslation(0, 0, 1.5);

		propeller2 = new SubmarinePropeller(RotationAxis.Z, 90);
		propeller2.setTranslation(0, 0, 1.5);

		propeller3 = new SubmarinePropeller(RotationAxis.Z, 180);
		propeller3.setTranslation(0, 0, 1.5);

		propeller4 = new SubmarinePropeller(RotationAxis.Z, 270);
		propeller4.setTranslation(0, 0, 1.5);

		body = new SubmarineBody(RotationAxis.Y);
		body.addChild(propeller1);
		body.addChild(propeller2);
		body.addChild(propeller3);
		body.addChild(propeller4);
	}

	public void draw(GL2 gl)
	{
		body.draw(gl);
		spinPropellers();
	}

	private void spinPropellers()
	{
		propeller1.setRotation(rotation);
		propeller2.setRotation(rotation);
		propeller3.setRotation(rotation);
		propeller4.setRotation(rotation);

		rotation += 0.1;
		if (rotation > 360)
		{
			rotation = 0;
		}
	}

	public void toggleWired()
	{
		body.toggleDrawingStyle();
		propeller1.toggleDrawingStyle();
		propeller2.toggleDrawingStyle();
		propeller3.toggleDrawingStyle();
		propeller4.toggleDrawingStyle();
	}
}
