package net.roryclaasen.sandbox.RenderEngine.shaders;


public abstract class PostShaderProgram extends ShaderProgram {

	public PostShaderProgram(String vert, String frag) {
		super("post/" + vert, "post/" + frag);
	}
}
