package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.smartdashboard.DashboardUpdatable;

public class OmniDriveTrain<T extends MotorController> implements DashboardUpdatable {
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

  public void drive(double forwardPower, double sidewaysPower, double rotatePower) {
    double leftPower = -forwardPower + rotatePower;
    double rightPower = forwardPower + rotatePower;
    double topPower = sidewaysPower + rotatePower;
    double bottomPower = -sidewaysPower + rotatePower;

    left.set(leftPower);
    right.set(rightPower);
    top.set(topPower);
    bottom.set(bottomPower);
  }

  @Override
  public void updateValues() {
    SmartDashboard.putNumber("left power", left.get());
    SmartDashboard.putNumber("top power", top.get());
    SmartDashboard.putNumber("right power", right.get());
    SmartDashboard.putNumber("bottom power", bottom.get());
  }
}