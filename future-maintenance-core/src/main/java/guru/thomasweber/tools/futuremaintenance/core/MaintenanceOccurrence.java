// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core;

import guru.thomasweber.tools.futuremaintenance.api.MaintenanceTask;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jspecify.annotations.Nullable;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Accessors(fluent = true)
@Getter
public class MaintenanceOccurrence<T extends Enum<T> & MaintenanceTask> {

  private final Enum<T> key;
  private final @Nullable String extraInformation;
  private @Nullable OccurenceType occurenceType;
  private @Nullable String className;
  private @Nullable String locationName;

  public MaintenanceOccurrence(Enum<T> t, @Nullable String extraInformation) {
    this.key = t;
    this.extraInformation = extraInformation;
  }

  public static <T extends Enum<T> & MaintenanceTask> MaintenanceOccurrence<T> of(Enum<T> t, @Nullable String extraInformation) {
    return new MaintenanceOccurrence<>(t, extraInformation);
  }

  public void withFieldUsage(String className, String fieldLocation) {
    if (nonNull(occurenceType)) {
      throw new IllegalStateException("MaintenanceOccurrence is already associated with a type");
    }
    this.className = className;
    this.locationName = fieldLocation;
    this.occurenceType = OccurenceType.FIELD;
  }

  public void withMethodUsage(String className, String methodLocation) {
    if (nonNull(occurenceType)) {
      throw new IllegalStateException("MaintenanceOccurrence is already associated with a type");
    }
    this.className = className;
    this.locationName = methodLocation;
    this.occurenceType = OccurenceType.METHOD;
  }

  public void withClassUsage(String className, String classLocation) {
    if (nonNull(occurenceType)) {
      throw new IllegalStateException("MaintenanceOccurrence is already associated with a type");
    }
    this.className = className;
    this.locationName = classLocation;
    this.occurenceType = OccurenceType.CLASS;
  }

  public void withConstructorUsage(String className, String constructorLocation) {
    if (nonNull(occurenceType)) {
      throw new IllegalStateException("MaintenanceOccurrence is already associated with a type");
    }
    this.className = className;
    this.locationName = constructorLocation;
    this.occurenceType = OccurenceType.CONSTRUCTOR;
  }

  public OccurenceType occurenceType() {
    requireNonNull(occurenceType, "occurenceType must be set before calling this method");
    return occurenceType;
  }

  public String className() {
    requireNonNull(className, "className must be set before calling this method");
    return className;
  }

  public String locationName() {
    requireNonNull(locationName, "locationName must be set before calling this method");
    return locationName;
  }
}
