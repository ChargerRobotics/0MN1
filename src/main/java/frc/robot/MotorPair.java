package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class MotorPair<T extends MotorController> {
  private final T left;
  private final T right;

  public MotorPair(T left, T right) {
    this.left = left;
    this.right = right;
  }

  public void set(double power) {
    left.set(power);
    right.set(power);
  }

  public double get() {
    return left.get();
  }
}
