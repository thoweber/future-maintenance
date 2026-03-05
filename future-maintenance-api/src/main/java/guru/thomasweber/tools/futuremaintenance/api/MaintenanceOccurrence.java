// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.api;

public class MaintenanceOccurrence<T extends Enum<T> & MaintenanceTask> {

  public MaintenanceOccurrence(String name, T taskConstant, String extraInfo) {}
}
