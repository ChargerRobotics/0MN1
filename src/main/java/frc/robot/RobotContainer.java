// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.subsystems.DriveSubsytem;

public class RobotContainer {
  private final DriveSubsytem<MotorController> driveSubsystem = new DriveSubsytem<>(new OmniDriveTrain<>(
          new MotorPair<>(new PWMVictorSPX(Ports.LEFT_FRONT_MOTOR), new PWMVictorSPX(Ports.LEFT_BACK_MOTOR)),
          new MotorPair<>(new PWMVictorSPX(Ports.FRONT_LEFT_MOTOR), new PWMVictorSPX(Ports.FRONT_RIGHT_MOTOR)),
          new MotorPair<>(new PWMVictorSPX(Ports.RIGHT_FRONT_MOTOR), new PWMVictorSPX(Ports.RIGHT_BACK_MOTOR)),
          new MotorPair<>(new PWMVictorSPX(Ports.BACK_LEFT_MOTOR), new VictorSP(Ports.BACK_RIGHT_MOTOR))
  ));
  private final CommandJoystick joystick = new CommandJoystick(0);
  private final Robot robot;

  private final Command autonomousCommand = Commands.runOnce(() -> {});

  public RobotContainer(Robot robot) {
    this.robot = robot;

    configureBindings();
    configureSmartDashboard();

    driveSubsystem.setDefaultCommand(Commands.run(() -> {
      driveSubsystem.resetMotors();

      double y = joystick.getY() + 0.05;
      double x = joystick.getX();
      if (Math.abs(y) <= 0.1) y = 0;
      if (Math.abs(x) <= 0.1) x = 0;
      driveSubsystem.drive(y * 0.5, x * 0.5);

      double twist = joystick.getRawAxis(4);
      if (Math.abs(twist) <= 0.15) twist = 0;
      driveSubsystem.rotate(twist * 0.25);

      driveSubsystem.updateMotors();
    }, driveSubsystem));
  }

  private void configureBindings() {
  }

  private void configureSmartDashboard() {
    robot.getSmartDashboardManager().add(driveSubsystem.getDriveTrain());
  }

  public Command getAutonomousCommand() {
    return autonomousCommand;
  }
}
