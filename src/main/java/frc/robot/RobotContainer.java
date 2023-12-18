// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import com.datasiqn.robotutils.controlcurve.ControlCurve;
import com.datasiqn.robotutils.controlcurve.ControlCurves;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.smartdashboard.SmartDashboardManager;
import frc.robot.subsystems.DriveSubsytem;
import frc.robot.subsystems.MotorSubsystem;

public class RobotContainer {
  private final DriveSubsytem<MotorController> driveSubsystem = new DriveSubsytem<>(new OmniDriveTrain<>(
          new MotorPair<>(new PWMVictorSPX(Ports.LEFT_FRONT_MOTOR), new PWMVictorSPX(Ports.LEFT_BACK_MOTOR)),
          new MotorPair<>(new PWMVictorSPX(Ports.FRONT_LEFT_MOTOR), new PWMVictorSPX(Ports.FRONT_RIGHT_MOTOR)),
          new MotorPair<>(new PWMVictorSPX(Ports.RIGHT_FRONT_MOTOR), new PWMVictorSPX(Ports.RIGHT_BACK_MOTOR)),
          new MotorPair<>(new PWMVictorSPX(Ports.BACK_LEFT_MOTOR), new VictorSP(Ports.BACK_RIGHT_MOTOR)),
          new ADIS16470_IMU()
  ));
  private final MotorSubsystem<Spark> intakeSubsystem = new MotorSubsystem<>("intake", new Spark(Ports.INTAKE_MOTOR), 1);
  private final MotorSubsystem<Talon> outtakeSubsystem = new MotorSubsystem<>("outtake", new Talon(Ports.OUTTAKE_MOTOR), 0.75);
  private final CommandJoystick joystick = new CommandJoystick(0);
  private final ControlCurve driveCurve = ControlCurves.linear()
          .withDeadZone(0.1)
          .build();
  private final ControlCurve twistCurve = ControlCurves.linear()
          .withDeadZone(0.15)
          .build();
  private final Robot robot;

  private final Command autonomousCommand = Commands.sequence(
    intakeSubsystem.forwardCommand().withTimeout(1),
    Commands.waitSeconds(1),
    intakeSubsystem.forwardCommand().withTimeout(0.5)
  );

  public RobotContainer(Robot robot) {
    this.robot = robot;

    configureBindings();
    configureSmartDashboard();

    driveSubsystem.setDefaultCommand(Commands.run(() -> {
      double twist = joystick.getRawAxis(4);

      driveSubsystem.drive(driveCurve.get(joystick.getMagnitude()) * 0.5, joystick.getDirectionRadians(), twistCurve.get(twist) * 0.25);
    }, driveSubsystem));
  }

  private void configureBindings() {
    joystick.trigger().whileTrue(intakeSubsystem.forwardCommand());
    joystick.button(3).whileTrue(intakeSubsystem.backwardsCommand());

    joystick.button(2).whileTrue(outtakeSubsystem.forwardCommand());
    joystick.button(4).whileTrue(outtakeSubsystem.backwardsCommand());

    joystick.button(5).onTrue(Commands.runOnce(() -> {
      ADIS16470_IMU gyro = driveSubsystem.getDriveTrain().getGyro();
      gyro.reset();
      gyro.calibrate();
    }));
  }

  private void configureSmartDashboard() {
    SmartDashboardManager manager = robot.getSmartDashboardManager();
    manager.add(driveSubsystem.getDriveTrain());
    manager.add(intakeSubsystem);
    manager.add(outtakeSubsystem);
  }

  public Command getAutonomousCommand() {
    return autonomousCommand;
  }

  public DriveSubsytem<MotorController> getDriveSubsystem() {
    return driveSubsystem;
  }
}
