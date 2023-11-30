package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.smartdashboard.DashboardUpdatable;

public class IntakeSubystem<T extends MotorController> extends SubsystemBase implements DashboardUpdatable {
  private final T motor;

  public IntakeSubystem(T motor) {
    this.motor = motor;
  }

  public Command intakeCommand() {
    return runEnd(() -> motor.set(1), () -> motor.stopMotor());
  }

  @Override
  public void updateValues() {
    SmartDashboard.putNumber("intake power", motor.get());
  }
}
