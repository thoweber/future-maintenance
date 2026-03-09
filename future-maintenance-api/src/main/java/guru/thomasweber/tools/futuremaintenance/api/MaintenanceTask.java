// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.api;

import java.time.LocalDate;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Describes the metadata of a maintenance task.
 *
 * <p>This interface is typically implemented by enum constants that act as a centralized registry
 * of future maintenance work. Each task contains structured metadata that can later be resolved
 * from {@link FutureMaintenance} annotations.
 */
public interface MaintenanceTask {
  /**
   * Returns the external issue or ticket identifier associated with the task.
   *
   * <p>Examples include tracker IDs such as {@code "ABC-123"}.
   */
  String issueNumber();

  /** Returns the earliest date on which the task should be executed or reconsidered. */
  LocalDate executableAfter();

  /** Returns the reason why this maintenance task exists. */
  String reason();

  /**
   * Creates a simple builder for ad-hoc {@link MaintenanceTask} instances.
   *
   * <p>This can be useful for tests or programmatic construction outside an enum.
   */
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
