package net.roryclaasen.sandbox.RenderEngine.models;

import net.roryclaasen.sandbox.RenderEngine.texture.ModelTexture;

public class TexturedModel {
	private RawModel rawModel;
	private ModelTexture texture;

	public TexturedModel(RawModel model, ModelTexture texture) {
		this.rawModel = model;
		this.texture = texture;
	}

	public RawModel getRawModel() {
		return rawModel;
	}

	public ModelTexture getTexture() {
		return texture;
	}
	
	public int getVaoId(){
		return rawModel.getVaoId();
	}
	public int getVertexCount(){
		return rawModel.getVertexCount();
	}
}
