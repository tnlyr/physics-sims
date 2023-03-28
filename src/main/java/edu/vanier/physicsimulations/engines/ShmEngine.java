package edu.vanier.physicsimulations.engines;

import javafx.animation.Animation;

public class ShmEngine {
    private double initialAngle, length, mass, gravity;
    private double period, kineticEnergy, potentialEnergy, totalEnergy, velocity, acceleration;

    private double height;


    public ShmEngine(double initialAngle, double length, double mass, double gravity) {
        this.initialAngle = initialAngle;
        this.length = length;
        this.mass = mass;
        this.gravity = gravity;
    }

    public double getInitialAngle() {
        return initialAngle;
    }

    public void setInitialAngle(double initialAngle) {
        this.initialAngle = initialAngle;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public double getPeriod() {
        return period;
    }

    public void setPeriod() {
        this.period = 2 * Math.PI * Math.sqrt(this.length/this.gravity);
    }

    public double getKineticEnergy() {
        return kineticEnergy;
    }

    public void setKineticEnergy(double kineticEnergy) {
        this.kineticEnergy = kineticEnergy;
    }

    public double getPotentialEnergy() {
        return potentialEnergy;
    }

    public void setPotentialEnergy() {
        this.potentialEnergy = this.height * this.gravity * this.mass;
    }

    public double getTotalEnergy() {
        return totalEnergy;
    }

    public void setTotalEnergy() {
        this.totalEnergy = this.mass * this.gravity * this.height;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight() {
        this.height = this.length - (this.length * Math.cos(this.initialAngle));
    }
}
