// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core;

import static java.util.Objects.isNull;

import guru.thomasweber.tools.futuremaintenance.api.MaintenanceTask;
import java.time.LocalDate;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

public record MaintenanceOccurrence(
    Key key,
    OccurenceType occurenceType,
    String className,
    String location,
    @Nullable String extraInformationValue) {

  public Optional<String> extraInformation() {
    return Optional.ofNullable(extraInformationValue);
  }

  @Override
  public OccurenceType occurenceType() {
    return occurenceType;
  }

  @Override
  public String className() {
    return className;
  }

  @Override
  public String location() {
    return location;
  }

  public static MaintenanceOccurrenceBuilder builder(Key key) {
    return new MaintenanceOccurrenceBuilder(key);
  }

  public static final class MaintenanceOccurrenceBuilder {
    private final Key key;
    private @Nullable String extraInformationValue;
    private OccurenceType occurenceType;
    private String className;
    private String locationName;

    private MaintenanceOccurrenceBuilder(Key key) {
      this.key = key;
    }

    public MaintenanceOccurrenceBuilder extraInformation(@Nullable String extraInformation) {
      if (isNull(extraInformation) || extraInformation.isBlank()) {
        this.extraInformationValue = null;
      } else {
        this.extraInformationValue = extraInformation;
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
      return new MaintenanceOccurrence(
          key, occurenceType, className, locationName, extraInformationValue);
    }
  }

  public record Key(
      Class<? extends MaintenanceTask> clazz,
      String issueNumber,
      LocalDate executableAfter,
      String reason)
      implements MaintenanceTask {

    public static <T extends Enum<T> & MaintenanceTask> Key of(Enum<T> task) {
      var maintenanceTask = (MaintenanceTask) task;
      return new Key(
          maintenanceTask.getClass(),
          maintenanceTask.issueNumber(),
          maintenanceTask.executableAfter(),
          maintenanceTask.reason());
    }
  }
}
