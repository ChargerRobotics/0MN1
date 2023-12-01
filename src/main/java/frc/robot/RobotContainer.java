// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.subsystems.DriveSubsytem;
import frc.robot.subsystems.MotorSubsystem;

public class RobotContainer {
  private final DriveSubsytem<MotorController> driveSubsystem = new DriveSubsytem<>(new OmniDriveTrain<>(
          new MotorPair<>(new PWMVictorSPX(Ports.LEFT_FRONT_MOTOR), new PWMVictorSPX(Ports.LEFT_BACK_MOTOR)),
          new MotorPair<>(new PWMVictorSPX(Ports.FRONT_LEFT_MOTOR), new PWMVictorSPX(Ports.FRONT_RIGHT_MOTOR)),
          new MotorPair<>(new PWMVictorSPX(Ports.RIGHT_FRONT_MOTOR), new PWMVictorSPX(Ports.RIGHT_BACK_MOTOR)),
          new MotorPair<>(new PWMVictorSPX(Ports.BACK_LEFT_MOTOR), new VictorSP(Ports.BACK_RIGHT_MOTOR))
  ));
  private final MotorSubsystem<Spark> intakeSubystem = new MotorSubsystem<>("intake", new Spark(Ports.INTAKE_MOTOR), 1);
  private final MotorSubsystem<Talon> outtakeSubsystem = new MotorSubsystem<>("outtake", new Talon(Ports.OUTTAKE_MOTOR), 0.5);
  private final CommandJoystick joystick = new CommandJoystick(0);
  private final Robot robot;

  private final Command autonomousCommand = Commands.sequence(
    intakeSubystem.runCommand().withTimeout(1),
    Commands.waitSeconds(1),
    intakeSubystem.runCommand().withTimeout(0.5)
  );

  public RobotContainer(Robot robot) {
    this.robot = robot;

    configureBindings();
    configureSmartDashboard();

    driveSubsystem.setDefaultCommand(Commands.run(() -> {
      double y = joystick.getY() + 0.05;
      double x = joystick.getX();
      if (Math.abs(y) <= 0.1) y = 0;
      if (Math.abs(x) <= 0.1) x = 0;
      double twist = joystick.getRawAxis(4);
      if (Math.abs(twist) <= 0.15) twist = 0;

      driveSubsystem.drive(y * 0.5, x * 0.5, twist * 0.25);
    }, driveSubsystem));
  }

  private void configureBindings() {
    joystick.trigger().whileTrue(intakeSubystem.runCommand());

    joystick.button(2).whileTrue(outtakeSubsystem.runCommand());
  }

  private void configureSmartDashboard() {
    robot.getSmartDashboardManager().add(driveSubsystem.getDriveTrain());
    robot.getSmartDashboardManager().add(intakeSubystem);
    robot.getSmartDashboardManager().add(outtakeSubsystem);
  }

  public Command getAutonomousCommand() {
    return autonomousCommand;
  }
}
