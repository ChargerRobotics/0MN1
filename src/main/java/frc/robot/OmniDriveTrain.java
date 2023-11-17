package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class OmniDriveTrain<T extends MotorController> {
  private final MotorPair<T> left;
  private final MotorPair<T> top;
  private final MotorPair<T> right;
  private final MotorPair<T> bottom;

  public OmniDriveTrain(MotorPair<T> left, MotorPair<T> top, MotorPair<T> right, MotorPair<T> bottom) {
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
  }

  public void drive(double forwardPower, double sidewaysPower) {
    left.drive(forwardPower);
    right.drive(forwardPower);

    top.drive(sidewaysPower);
    bottom.drive(sidewaysPower);
  }

  public void rotate(double power) {
    left.drive(power);
    right.drive(-power);
    top.drive(power);
    bottom.drive(-power);
  }
}