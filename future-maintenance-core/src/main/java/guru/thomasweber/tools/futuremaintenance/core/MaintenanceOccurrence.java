// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import guru.thomasweber.tools.futuremaintenance.api.MaintenanceTask;
import java.time.LocalDate;
import java.util.Optional;

import lombok.*;
import lombok.experimental.Accessors;
import org.jspecify.annotations.Nullable;

@Accessors(fluent = true)
@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class MaintenanceOccurrence {

  private final Key key;
  @Getter(AccessLevel.NONE)
  private final @Nullable String extraInformation;
  private final OccurenceType occurenceType;
  private final String className;
  private final String locationName;

  public Optional<String> extraInformation() {
    return Optional.ofNullable(extraInformation);
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

  public static class MaintenanceOccurrenceBuilder {

    public MaintenanceOccurrenceBuilder extraInformation(@Nullable String extraInformation) {
      if (isNull(extraInformation) || extraInformation.isBlank()) {
        this.extraInformation = null;
      } else {
        this.extraInformation = extraInformation;
      }
      return this;
    }

    public MaintenanceOccurrence forClassUsage(String className, String classLocation) {
      this.className = className;
      this.locationName = classLocation;
      this.occurenceType = OccurenceType.CLASS;
      return build();
    }

    public MaintenanceOccurrence forFieldUsage(String className, String fieldLocation) {
      this.className = className;
      this.locationName = fieldLocation;
      this.occurenceType = OccurenceType.FIELD;
      return build();
    }

    public MaintenanceOccurrence forConstructorUsage(String className, String constructorLocation) {
      this.className = className;
      this.locationName = constructorLocation;
      this.occurenceType = OccurenceType.CONSTRUCTOR;
      return build();
    }

    public MaintenanceOccurrence forMethodUsage(String className, String methodLocation) {
      this.className = className;
      this.locationName = methodLocation;
      this.occurenceType = OccurenceType.METHOD;
      return build();
    }

    private MaintenanceOccurrence build() {
      return new MaintenanceOccurrence(this.key, this.extraInformation, this.occurenceType, this.className, this.locationName);
    }
  }

  @Accessors(fluent = true)
  @Getter
  @EqualsAndHashCode
  @ToString
  public static final class Key implements MaintenanceTask {

    private final Class<? extends MaintenanceTask> clazz;
    private final String issueNumber;
    private final LocalDate executableAfter;
    private final String reason;

    private Key(MaintenanceTask task) {
      this.clazz = task.getClass();
      this.issueNumber = task.issueNumber();
      this.executableAfter = task.executableAfter();
      this.reason = task.reason();
    }

    public static <T extends Enum<T> & MaintenanceTask> Key of(Enum<T> task) {
      return new Key((MaintenanceTask) task);
    }
  }
}
