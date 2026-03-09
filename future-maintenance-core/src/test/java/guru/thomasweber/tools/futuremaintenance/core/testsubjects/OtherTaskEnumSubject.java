// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core.testsubjects;

import guru.thomasweber.tools.futuremaintenance.api.MaintenanceTask;

import java.time.LocalDate;

public enum OtherTaskEnumSubject implements MaintenanceTask {
  CLEANUP_API_METHOD(
      MaintenanceTask.builder()
          .issueNumber("123")
          .executableAfter(LocalDate.parse("2025-12-05"))
          .reason("Cleanup API method")
          .build());

  private final MaintenanceTask task;

  OtherTaskEnumSubject(MaintenanceTask task) {
    this.task = task;
  }

  @Override
  public String issueNumber() {
    return task.issueNumber();
  }

  @Override
  public LocalDate executableAfter() {
    return task.executableAfter();
  }

  @Override
  public String reason() {
    return task.reason();
  }
}
