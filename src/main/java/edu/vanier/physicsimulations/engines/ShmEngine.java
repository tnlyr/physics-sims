package edu.vanier.physicsimulations.engines;

import javafx.animation.Animation;

public class ShmEngine {
    private double initialAngle, length, mass, gravity;
    private double period, kineticEnergy, potentialEnergy, totalEnergy, velocity, acceleration;

    private double height;



    public ShmEngine() {
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

    public void setPeriod(double length, double gravity) {
        this.period = 2 * Math.PI * Math.sqrt(length/gravity);
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

    public void setPotentialEnergy(double potentialEnergy) {
        this.potentialEnergy = potentialEnergy;
    }

    public double getTotalEnergy() {
        return totalEnergy;
    }

    public void setTotalEnergy(double totalEnergy) {
        this.totalEnergy = totalEnergy;
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

    public void setHeight(double height) {
        this.height = height;
    }
}
