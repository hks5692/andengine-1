package org.anddev.andengine.opengl.texture.region.buffer;

import static org.anddev.andengine.opengl.vertex.RectangleVertexBuffer.VERTICES_PER_RECTANGLE;

import java.nio.FloatBuffer;

import org.anddev.andengine.opengl.buffer.BufferObject;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.region.BaseTextureRegion;

import com.badlogic.gdx.utils.BufferUtils;

/**
 * @author Nicolas Gramlich
 * @since 19:05:50 - 09.03.2010
 */
public abstract class BaseTextureRegionBuffer extends BufferObject {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	protected final BaseTextureRegion mTextureRegion;
	private boolean mFlippedVertical;
	private boolean mFlippedHorizontal;

	// ===========================================================
	// Constructors
	// ===========================================================

	public BaseTextureRegionBuffer(final BaseTextureRegion pBaseTextureRegion, final int pDrawType) {
		super(2 * VERTICES_PER_RECTANGLE, pDrawType);
		this.mTextureRegion = pBaseTextureRegion;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public BaseTextureRegion getTextureRegion() {
		return this.mTextureRegion;
	}

	public boolean isFlippedHorizontal() {
		return this.mFlippedHorizontal;
	}

	public void setFlippedHorizontal(final boolean pFlippedHorizontal) {
		if(this.mFlippedHorizontal != pFlippedHorizontal) {
			this.mFlippedHorizontal = pFlippedHorizontal;
			this.update();
		}
	}

	public boolean isFlippedVertical() {
		return this.mFlippedVertical;
	}

	public void setFlippedVertical(final boolean pFlippedVertical) {
		if(this.mFlippedVertical != pFlippedVertical) {
			this.mFlippedVertical = pFlippedVertical;
			this.update();
		}
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	protected abstract float getX1();

	protected abstract float getY1();

	protected abstract float getX2();

	protected abstract float getY2();

	// ===========================================================
	// Methods
	// ===========================================================

	public synchronized void update() {
		final BaseTextureRegion textureRegion = this.mTextureRegion;
		final Texture texture = textureRegion.getTexture();

		if(texture == null) {
			return;
		}
		
		final float x1 = this.getX1();
		final float y1 = this.getY1();
		final float x2 = this.getX2();
		final float y2 = this.getY2();

		final float[] bufferData = this.mBufferData;

		if(this.mFlippedVertical) {
			if(this.mFlippedHorizontal) {
				bufferData[0] = x2;
				bufferData[1] = y2;

				bufferData[2] = x2;
				bufferData[3] = y1;

				bufferData[4] = x1;
				bufferData[5] = y2;

				bufferData[6] = x1;
				bufferData[7] = y1;
			} else {
				bufferData[0] = x1;
				bufferData[1] = y2;

				bufferData[2] = x1;
				bufferData[3] = y1;

				bufferData[4] = x2;
				bufferData[5] = y2;

				bufferData[6] = x2;
				bufferData[7] = y1;
			}
		} else {
			if(this.mFlippedHorizontal) {
				bufferData[0] = x2;
				bufferData[1] = y1;

				bufferData[2] = x2;
				bufferData[3] = y2;

				bufferData[4] = x1;
				bufferData[5] = y1;

				bufferData[6] = x1;
				bufferData[7] = y2;
			} else {
				bufferData[0] = x1;
				bufferData[1] = y1;

				bufferData[2] = x1;
				bufferData[3] = y2;

				bufferData[4] = x2;
				bufferData[5] = y1;

				bufferData[6] = x2;
				bufferData[7] = y2;
			}
		}

		final FloatBuffer buffer = this.getFloatBuffer();
		BufferUtils.copy(bufferData, buffer, buffer.capacity(), 0);

		super.setHardwareBufferNeedsUpdate();
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}