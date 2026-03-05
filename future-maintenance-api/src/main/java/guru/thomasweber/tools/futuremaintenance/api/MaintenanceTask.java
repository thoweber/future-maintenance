// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.api;

import java.time.LocalDate;

public interface MaintenanceTask {
  String issueNumber();

  LocalDate executableAfter();

  String reason();
}
