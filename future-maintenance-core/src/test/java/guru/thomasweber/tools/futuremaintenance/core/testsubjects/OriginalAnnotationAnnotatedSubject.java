// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core.testsubjects;

import guru.thomasweber.tools.futuremaintenance.api.FutureMaintenance;

@FutureMaintenance(taskClass = TaskEnumSubject.class, value = "CLEANUP_API_METHOD", extraInformation = "info on class")
public class OriginalAnnotationAnnotatedSubject {

  @FutureMaintenance(taskClass = TaskEnumSubject.class, value = "CLEANUP_API_METHOD", extraInformation = "info on field")
  private final String field;

  @FutureMaintenance(taskClass = TaskEnumSubject.class, value = "CLEANUP_API_METHOD", extraInformation = "info on constructor")
  public OriginalAnnotationAnnotatedSubject(String value) {
    this.field = value;
  }

  @FutureMaintenance(taskClass = TaskEnumSubject.class, value = "CLEANUP_API_METHOD", extraInformation = "info on method")
  public void method(String param) {}
}
