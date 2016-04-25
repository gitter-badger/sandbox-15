package net.roryclaasen.sandbox.RenderEngine;

import net.roryclaasen.sandbox.RenderEngine.gui.GuiShader;
import net.roryclaasen.sandbox.models.RawModel;
import net.roryclaasen.sandbox.util.Loader;
import net.roryclaasen.sandbox.util.Maths;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

public class Splash {

	private final RawModel quad;
	private GuiShader shader;

	private int image;

	private Vector2f pos, scale;

	public Splash(Loader loader, String file) {
		float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
		quad = loader.loadToVAO(positions);
		shader = new GuiShader();

		image = loader.loadTexture(true, "intro", file, 0);

		pos = new Vector2f(0, 0);
		scale = new Vector2f(1, 1);
	}

	public void show() {
		shader.start();
		GL30.glBindVertexArray(quad.getVaoId());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, image);
		Matrix4f matrix = Maths.createTransformationMatrix(pos, scale);
		shader.loadTransformation(matrix);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}

	public void hide() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
}
