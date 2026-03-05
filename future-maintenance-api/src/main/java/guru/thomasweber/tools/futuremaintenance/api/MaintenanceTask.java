// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.api;

import java.time.LocalDate;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface MaintenanceTask {
  String issueNumber();

  LocalDate executableAfter();

  String reason();

  static Builder builder() {
    return new Builder();
  }

  @Accessors(fluent = true)
  @Setter
  class Builder {
    private String issueNumber;
    private LocalDate executableAfter;
    private String reason;

    public MaintenanceTask build() {
      return new MaintenanceTaskImpl(issueNumber, executableAfter, reason);
    }
  }

  record MaintenanceTaskImpl(String issueNumber, LocalDate executableAfter, String reason)
      implements MaintenanceTask {}
}
