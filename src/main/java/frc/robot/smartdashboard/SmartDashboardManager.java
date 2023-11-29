package frc.robot.smartdashboard;

import java.util.ArrayList;
import java.util.List;

public class SmartDashboardManager {
  private final List<DashboardUpdatable> updatables = new ArrayList<>();

  public void add(DashboardUpdatable updatable) {
    updatables.add(updatable);
  }

  public void updateAll() {
    updatables.forEach(DashboardUpdatable::updateValues);
  }
}
