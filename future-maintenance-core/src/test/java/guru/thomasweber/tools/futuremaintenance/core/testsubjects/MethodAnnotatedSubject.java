// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core.testsubjects;

import guru.thomasweber.tools.futuremaintenance.api.FutureMaintenance;

public class MethodAnnotatedSubject {

  @FutureMaintenance(taskClass = TaskEnumSubject.class, value = "CLEANUP_API_METHOD", extraInformation = "extra information")
  private final String field = "default value";

  @FutureMaintenance(taskClass = TaskEnumSubject.class, value = "CLEANUP_API_METHOD")
  public void method() {}
}
