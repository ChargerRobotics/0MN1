package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.OmniDriveTrain;

public class DriveSubsytem<T extends MotorController> extends SubsystemBase {
  private final OmniDriveTrain<T> driveTrain;

  public DriveSubsytem(OmniDriveTrain<T> driveTrain) {
    this.driveTrain = driveTrain;
  }

  public void drive(double forwardPower, double sidewaysPower, double rotatePower) {
    driveTrain.drive(forwardPower, sidewaysPower, rotatePower);
  }

  public OmniDriveTrain<T> getDriveTrain() {
    return driveTrain;
  }
}
