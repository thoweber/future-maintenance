// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import guru.thomasweber.tools.futuremaintenance.core.testsubjects.TaskEnumSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class MaintenanceOccurrenceTest {

  @ParameterizedTest
  @NullSource
  @EmptySource
  @ValueSource(strings = {"  "})
  void builder_extraInformation_isSetToEmptyOptional_forNullEmptyAndBlank(String extraInformation) {
    var maintenanceOccurrence =
        MaintenanceOccurrence.builder(
                MaintenanceOccurrence.Key.of(TaskEnumSubject.CLEANUP_API_METHOD))
            .extraInformation(extraInformation)
            .forClassUsage("class", "location");
    assertTrue(maintenanceOccurrence.extraInformation().isEmpty());
  }

  @Test
  void checkAccessors() {
    var maintenanceOccurrence =
        MaintenanceOccurrence.builder(
                MaintenanceOccurrence.Key.of(TaskEnumSubject.CLEANUP_API_METHOD))
            .forClassUsage("class", "location");
    assertEquals(OccurenceType.CLASS, maintenanceOccurrence.occurenceType());
    assertEquals("class", maintenanceOccurrence.className());
    assertEquals("location", maintenanceOccurrence.location());
    assertTrue(maintenanceOccurrence.extraInformation().isEmpty());
  }
}
