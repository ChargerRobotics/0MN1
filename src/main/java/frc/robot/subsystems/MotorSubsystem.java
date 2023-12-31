package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.smartdashboard.DashboardUpdatable;

public class MotorSubsystem<T extends MotorController> extends SubsystemBase implements DashboardUpdatable {
  private final String name;
  private final T motor;
  private final double speed;
  private final double backwardsSpeed;

  public MotorSubsystem(String name, T motor, double speed) {
    this(name, motor, speed, -speed);
  }
  public MotorSubsystem(String name, T motor, double speed, double backwardsSpeed) {
    this.name = name;
    this.motor = motor;
    this.speed = speed;
    this.backwardsSpeed = backwardsSpeed;
  }

  public Command forwardCommand() {
    return startEnd(() -> motor.set(speed), () -> motor.stopMotor());
  }

  public Command backwardsCommand() {
    return startEnd(() -> motor.set(backwardsSpeed), () -> motor.stopMotor());
  }

  public T getMotor() {
    return motor;
  }

  @Override
  public void updateValues() {
    SmartDashboard.putNumber(name + " power", motor.get());
  }
}
