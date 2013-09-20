package au.edu.unimelb.csse.trollsvsgoats.core.model;

import playn.core.Image;

/**
 * Represents frames in an animation.
 */
public class Animation {
    // A parent image with subimages.
    private Image parentImage;
    private Image[] images;
    private float frameWidth, frameHeight;
    private int frameCount;
    private float frameTime;
    private int frameIndex;
    private float timer;

    public Animation(Image[] images, float frameTime) {
        this.images = images;
        this.frameTime = frameTime;
    }

    public Animation(Image parentImage, int frameCount, float frameTime) {
        this.parentImage = parentImage;
        this.frameCount = frameCount;
        this.frameWidth = parentImage.width() / frameCount;
        this.frameHeight = parentImage.height();
        this.frameTime = frameTime;
    }
    
    public Animation(Image parentImage, int frameCount, int frameWidth, int frameHeight, float frameTime) {
        this.parentImage = parentImage;
        this.frameCount = frameCount;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameTime = frameTime;
    }

    /**
     * Duration of time to show each frame.
     */
    public float frameTime() {
        return this.frameTime;
    }

    public void setFrameTime(float frameTime) {
        this.frameTime = frameTime;
    }

    public int frameCount() {
        if (images != null)
            return images.length;
        else
            return this.frameCount;
    }

    public Image frame(int index) {
        if (images != null)
            return images[index];
        else
            return parentImage.subImage(index * frameWidth, 0, frameWidth, frameHeight);
    }

    public Image nextFrame(float delta) {
        timer += delta;
        if (timer >= frameTime) {
            timer = 0;
            frameIndex = ++frameIndex % frameCount;
        }
        return frame(frameIndex);
    }
    
    public boolean isFinished(){
    	return frameIndex>=(frameCount-1);
    }
}
