// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.smartdashboard.SmartDashboardManager;

public class Robot extends TimedRobot {
  private final SmartDashboardManager smartDashboardManager = new SmartDashboardManager();
  private RobotContainer robotContainer;

  public SmartDashboardManager getSmartDashboardManager() {
    return smartDashboardManager;
  }

  @Override
  public void robotInit() {
    robotContainer = new RobotContainer(this);

    CameraServer.startAutomaticCapture();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    
    smartDashboardManager.updateAll();
  }

  @Override
  public void disabledExit() {
    robotContainer.getDriveSubsystem().getDriveTrain().getGyro().reset();
  }

  @Override
  public void autonomousInit() {
    robotContainer.getAutonomousCommand().schedule();
  }

  @Override
  public void autonomousExit() {
    robotContainer.getAutonomousCommand().cancel();
  }
}
