package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubystem<T extends MotorController> extends SubsystemBase {
  private final T motor;

  public IntakeSubystem(T motor) {
    this.motor = motor;
  }

  public Command intakeCommand() {
    return runEnd(() -> motor.set(1), () -> motor.stopMotor());
  }
}
