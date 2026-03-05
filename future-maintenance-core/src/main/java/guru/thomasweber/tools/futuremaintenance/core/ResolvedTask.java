// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core;

import guru.thomasweber.tools.futuremaintenance.api.MaintenanceTask;
import java.time.LocalDate;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

public class ResolvedTask<T extends Enum<T> & MaintenanceTask> implements MaintenanceTask {
  private final T enumConstant;
  private final @Nullable String extraInformation;

  public ResolvedTask(T enumConstant, @Nullable String extraInformation) {
    this.enumConstant = enumConstant;
    this.extraInformation = extraInformation;
  }

  public T enumConstant() {
    return enumConstant;
  }

  @Override
  public String issueNumber() {
    return enumConstant.issueNumber();
  }

  @Override
  public LocalDate executableAfter() {
    return enumConstant.executableAfter();
  }

  @Override
  public String reason() {
    return enumConstant.reason();
  }

  public Optional<String> extraInformation() {
    return Optional.ofNullable(extraInformation);
  }
}
