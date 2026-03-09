// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core.testsubjects;

@SuppressWarnings("unused")
@MyFutureMaintenance(value = "CLEANUP_API_METHOD", extraInformation = "info on class")
public class CustomAnnotationAnnotatedSubject {

  @MyFutureMaintenance(value = "CLEANUP_API_METHOD", extraInformation = "info on field")
  private final String field;

  @MyFutureMaintenance(value = "CLEANUP_API_METHOD", extraInformation = "info on constructor")
  public CustomAnnotationAnnotatedSubject(String value) {
    this.field = value;
  }

  @MyFutureMaintenance(value = "CLEANUP_API_METHOD", extraInformation = "info on method")
  public void method(String param) {}
}
