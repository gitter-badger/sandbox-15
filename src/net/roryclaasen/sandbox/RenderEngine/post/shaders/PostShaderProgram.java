package net.roryclaasen.sandbox.RenderEngine.post.shaders;

import net.roryclaasen.sandbox.RenderEngine.shaders.ShaderProgram;

public abstract class PostShaderProgram extends ShaderProgram {

	public PostShaderProgram(String vert, String frag) {
		super("post/" + vert, "post/" + frag);
	}
}
