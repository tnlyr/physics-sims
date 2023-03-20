package edu.vanier.physicsimulations.engines;

import javafx.animation.Animation;

public class ShmEngine {
    private Animation SHMAnimation;

    public ShmEngine() {
    }

    public Animation shm() {
        return SHMAnimation;
    }

    public Animation getSHMAnimation() {
        return SHMAnimation;
    }

    public void setSHMAnimation(Animation SHMAnimation) {
        this.SHMAnimation = SHMAnimation;
    }
}
