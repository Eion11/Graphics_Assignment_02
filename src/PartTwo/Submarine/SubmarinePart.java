package PartTwo.Submarine;

import PartTwo.Main.Position;
import com.jogamp.opengl.GL2;

import java.util.LinkedList;
import java.util.List;

public abstract class SubmarinePart
{
	protected Position position = new Position();
	protected RotationAxis axis;
	protected double       rotation;

	private List<SubmarinePart> children = new LinkedList<>();

	public void addChild(SubmarinePart newChild)
	{
		children.add(newChild);
	}

	public void draw(GL2 gl)
	{
		gl.glColor3d(0.5, 0.5, 0.5);
		gl.glPushMatrix();

		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, new float[] { 0.2f, 0.2f, 0.2f, 1 }, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, new float[] { 0.3f, 0.35f, 0.4f, 1 }, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, new float[] { 0, 0, 0, 1 }, 0);
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 50);

		transformPart(gl);
		rotateSecondary(gl);
		drawPart(gl);

		for (SubmarinePart child : children)
		{
			child.draw(gl);
		}

		gl.glPopMatrix();
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