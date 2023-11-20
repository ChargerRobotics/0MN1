// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

  private final Command autonomousCommand = Commands.runOnce(() -> {});

  public RobotContainer() {
    configureBindings();

    driveSubsystem.setDefaultCommand(Commands.run(() -> {
//      driveSubsystem.drive(-joystick.getRawAxis(2), joystick.getRawAxis(1));
//      driveSubsystem.rotate(joystick.getTwist());
    }, driveSubsystem));
  }

  private void configureBindings() {
  }

  public Command getAutonomousCommand() {
    return autonomousCommand;
  }

  public void updateSmartDashboard() {
    SmartDashboard.putNumber("x", joystick.getX());
    SmartDashboard.putNumber("y", joystick.getY());
    SmartDashboard.putNumber("twist", joystick.getTwist());
  }
}
