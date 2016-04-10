package net.roryclaasen.sandbox.RenderEngine;

import java.util.List;
import java.util.Map;

import net.roryclaasen.sandbox.RenderEngine.models.TexturedModel;
import net.roryclaasen.sandbox.RenderEngine.shaders.StaticShader;
import net.roryclaasen.sandbox.RenderEngine.texture.ModelTexture;
import net.roryclaasen.sandbox.entities.Entity;
import net.roryclaasen.sandbox.util.Maths;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

public class EntityRenderer {

	private StaticShader shader;

	public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void render(Map<TexturedModel, List<Entity>> entities) {
		for (TexturedModel model : entities.keySet()) {
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for (Entity entity : batch) {
				loadModelMatrix(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}
	}

	private void prepareTexturedModel(TexturedModel model) {
		GL30.glBindVertexArray(model.getVaoId());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);

		ModelTexture texture = model.getTexture();
		if (texture.hasTransparency()) MasterRenderer.disableCulling();
		shader.loadShineVariables(texture.getShineDamper(), texture.getRefelectivity());
		shader.loadUseFakeLightingVariable(texture.useFakeLighting());
		shader.loadNumberOfRows(texture.getNumberOfRows());
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
	}

	private void unbindTexturedModel() {
		MasterRenderer.enableCulling();
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	private void loadModelMatrix(Entity entity) {
		Matrix4f transformationMatix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatix);
		shader.loadTextureOffset(entity.getTextureXOffset(),entity.getTextureYOffset());
	}
}
