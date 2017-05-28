package PartTwo.Submarine;

import PartTwo.Main.Point;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import java.util.LinkedList;
import java.util.List;

public abstract class SubmarinePart
{
	protected int drawingStyle;
	protected Point position = new Point();
	protected RotationAxis axis;
	protected double       rotation;

	private List<SubmarinePart> children = new LinkedList<>();

	protected SubmarinePart()
	{
		drawingStyle = GLU.GLU_FILL;
	}

	public void addChild(SubmarinePart newChild)
	{
		children.add(newChild);
	}

	public void draw(GL2 gl)
	{
		gl.glPushMatrix();

		transformPart(gl);
		rotateSecondary(gl);
		drawPart(gl);

		for (SubmarinePart child : children)
		{
			child.draw(gl);
		}

		gl.glPopMatrix();
	}

	public void toggleDrawingStyle()
	{
		if (drawingStyle == GLU.GLU_LINE)
		{
			drawingStyle = GLU.GLU_FILL;
		}
		else
		{
			drawingStyle = GLU.GLU_LINE;
		}
	}

	public void setTranslation(double x, double y, double z)
	{
		position.x = x;
		position.y = y;
		position.z = z;
	}

	public double getRotation()
	{
		return rotation;
	}

	public abstract void transformPart(GL2 gl);

	public abstract void rotateSecondary(GL2 gl);

	public abstract void drawPart(GL2 gl);

}