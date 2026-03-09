// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import guru.thomasweber.tools.futuremaintenance.core.testsubjects.OtherTaskEnumSubject;
import guru.thomasweber.tools.futuremaintenance.core.testsubjects.TaskEnumSubject;
import java.util.List;
import org.junit.jupiter.api.Test;

class MaintenanceScannerTest {

  @Test
  void scan() {
    var scanner = new MaintenanceScanner();
    var expected =
        List.of(
            MaintenanceOccurrence.builder(
                    MaintenanceOccurrence.Key.of(TaskEnumSubject.CLEANUP_API_METHOD))
                .extraInformation("info on class")
                .forClassUsage(
                    "guru.thomasweber.tools.futuremaintenance.core.testsubjects.CustomAnnotationAnnotatedSubject",
                    "CustomAnnotationAnnotatedSubject"),
            MaintenanceOccurrence.builder(
                    MaintenanceOccurrence.Key.of(TaskEnumSubject.CLEANUP_API_METHOD))
                .extraInformation("info on field")
                .forFieldUsage(
                    "guru.thomasweber.tools.futuremaintenance.core.testsubjects.CustomAnnotationAnnotatedSubject",
                    "String field"),
            MaintenanceOccurrence.builder(
                    MaintenanceOccurrence.Key.of(TaskEnumSubject.CLEANUP_API_METHOD))
                .extraInformation("info on constructor")
                .forConstructorUsage(
                    "guru.thomasweber.tools.futuremaintenance.core.testsubjects.CustomAnnotationAnnotatedSubject",
                    "<init>(String)"),
            MaintenanceOccurrence.builder(
                    MaintenanceOccurrence.Key.of(TaskEnumSubject.CLEANUP_API_METHOD))
                .extraInformation("info on method")
                .forMethodUsage(
                    "guru.thomasweber.tools.futuremaintenance.core.testsubjects.CustomAnnotationAnnotatedSubject",
                    "method(String)"),
            MaintenanceOccurrence.builder(
                    MaintenanceOccurrence.Key.of(TaskEnumSubject.CLEANUP_API_METHOD))
                .extraInformation("info on class")
                .forClassUsage(
                    "guru.thomasweber.tools.futuremaintenance.core.testsubjects.OriginalAnnotationAnnotatedSubject",
                    "OriginalAnnotationAnnotatedSubject"),
            MaintenanceOccurrence.builder(
                    MaintenanceOccurrence.Key.of(TaskEnumSubject.CLEANUP_API_METHOD))
                .extraInformation("info on field")
                .forFieldUsage(
                    "guru.thomasweber.tools.futuremaintenance.core.testsubjects.OriginalAnnotationAnnotatedSubject",
                    "String field"),
            MaintenanceOccurrence.builder(
                    MaintenanceOccurrence.Key.of(OtherTaskEnumSubject.CLEANUP_API_METHOD))
                .extraInformation("info on constructor")
                .forConstructorUsage(
                    "guru.thomasweber.tools.futuremaintenance.core.testsubjects.OriginalAnnotationAnnotatedSubject",
                    "<init>(String)"),
            MaintenanceOccurrence.builder(
                    MaintenanceOccurrence.Key.of(TaskEnumSubject.CLEANUP_API_METHOD))
                .extraInformation("info on method")
                .forMethodUsage(
                    "guru.thomasweber.tools.futuremaintenance.core.testsubjects.OriginalAnnotationAnnotatedSubject",
                    "method(String)"));
    var result = scanner.scan("guru.thomasweber.tools.futuremaintenance.core");

    assertEquals(8, result.size());
    assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
  }
}
