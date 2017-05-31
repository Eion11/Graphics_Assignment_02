package PartTwo.Submarine;

import PartTwo.Main.Grid;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

/**
 * Created by Scott on 29/04/2017.
 */
public class Submarine
{
	public SubmarineMovement  submarineMovement;
	public SubmarineBody      body;
	public SubmarinePropeller propeller1;
	public SubmarinePropeller propeller2;
	public SubmarinePropeller propeller3;
	public SubmarinePropeller propeller4;
	public SubmarineScope     scope;
	public SubmarineSpotlight spotLight;

	public Submarine(Grid water, Grid floor)
	{
		submarineMovement = new SubmarineMovement(this, water, floor);
		createSubmarine();
	}

	private void createSubmarine()
	{
		// Creates each piece of the submarine and puts them in the right position
		propeller1 = new SubmarinePropeller(RotationAxis.Z, 0);
		propeller1.setTranslation(0, 0, 1.7);

		propeller2 = new SubmarinePropeller(RotationAxis.Z, 90);
		propeller2.setTranslation(0, 0, 1.7);

		propeller3 = new SubmarinePropeller(RotationAxis.Z, 180);
		propeller3.setTranslation(0, 0, 1.7);

		propeller4 = new SubmarinePropeller(RotationAxis.Z, 270);
		propeller4.setTranslation(0, 0, 1.7);

		scope = new SubmarineScope(RotationAxis.Z);
		scope.setTranslation(0, 1.5, 0);

		spotLight = new SubmarineSpotlight();

		body = new SubmarineBody(RotationAxis.Y);
		body.addChild(propeller1);
		body.addChild(propeller2);
		body.addChild(propeller3);
		body.addChild(propeller4);
		body.addChild(scope);
		body.addChild(spotLight);
	}

	public void draw(GL2 gl)
	{
		body.draw(gl);
	}
}
