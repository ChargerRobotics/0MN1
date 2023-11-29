package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.smartdashboard.DashboardUpdatable;

public class OmniDriveTrain<T extends MotorController> implements DashboardUpdatable {
  private final MotorPair<T> left;
  private final MotorPair<T> top;
  private final MotorPair<T> right;
  private final MotorPair<T> bottom;

  private double leftPower = 0;
  private double topPower = 0;
  private double rightPower = 0;
  private double bottomPower = 0;

  public OmniDriveTrain(MotorPair<T> left, MotorPair<T> top, MotorPair<T> right, MotorPair<T> bottom) {
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
  }

  public void drive(double forwardPower, double sidewaysPower) {
    leftPower -= forwardPower;
    rightPower += forwardPower;

    topPower += sidewaysPower;
    bottomPower -= sidewaysPower;
  }

  public void rotate(double power) {
    leftPower += power;
    rightPower += power;
    topPower += power;
    bottomPower += power;
  }

  public void resetMotors() {
    leftPower = 0;
    rightPower = 0;
    topPower = 0;
    bottomPower = 0;
  }

  public void updateMotors() {
    right.drive(rightPower);
    left.drive(leftPower);
    top.drive(topPower);
    bottom.drive(bottomPower);
  }

  @Override
  public void updateValues() {
    SmartDashboard.putNumber("left power", leftPower);
    SmartDashboard.putNumber("top power", topPower);
    SmartDashboard.putNumber("right power", rightPower);
    SmartDashboard.putNumber("bottom power", bottomPower);
  }
}