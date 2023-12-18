package frc.robot;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.ADIS16470_IMU.IMUAxis;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.smartdashboard.DashboardUpdatable;

public class OmniDriveTrain<T extends MotorController> implements DashboardUpdatable {
  private final MotorPair<T> left;
  private final MotorPair<T> top;
  private final MotorPair<T> right;
  private final MotorPair<T> bottom;
  private final ADIS16470_IMU gyro;

  public OmniDriveTrain(MotorPair<T> left, MotorPair<T> top, MotorPair<T> right, MotorPair<T> bottom, ADIS16470_IMU gyro) {
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
    this.gyro = gyro;

    this.gyro.setYawAxis(IMUAxis.kX);
    this.gyro.reset();
  }

  public ADIS16470_IMU getGyro() {
    return gyro;
  }

  public void drive(double power, double angleRadians, double rotatePower) {
    angleRadians -= Math.toRadians(gyro.getAngle());
    double forwardPower = Math.cos(angleRadians) * power;
    double sidewaysPower = Math.sin(angleRadians) * power;
    double leftPower = forwardPower + rotatePower;
    double rightPower = -forwardPower + rotatePower;
    double topPower = sidewaysPower + rotatePower;
    double bottomPower = -sidewaysPower + rotatePower;

    SmartDashboard.putNumber("rotate power", rotatePower);

    left.set(leftPower);
    right.set(rightPower);
    top.set(topPower);
    bottom.set(bottomPower);
  }

  @Override
  public void updateValues() {
    SmartDashboard.putNumber("gryo angle", gyro.getAngle());
    double gyroRadians = Math.toRadians(gyro.getAngle());
    SmartDashboard.putNumber("cosine gyro angle", Math.cos(gyroRadians));
    SmartDashboard.putNumber("sine gyro angle", Math.sin(gyroRadians));
    SmartDashboard.putNumber("left power", left.get());
    SmartDashboard.putNumber("top power", top.get());
    SmartDashboard.putNumber("right power", right.get());
    SmartDashboard.putNumber("bottom power", bottom.get());
  }
}