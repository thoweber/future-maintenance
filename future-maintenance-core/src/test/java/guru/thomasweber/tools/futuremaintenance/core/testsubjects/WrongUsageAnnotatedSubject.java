// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core.testsubjects;

@SuppressWarnings("unused")
public class WrongUsageAnnotatedSubject {

  @WrongUsageOfFutureMaintenance
  public WrongUsageAnnotatedSubject() {
    // only here to demonstrate the annotation usage
  }

}
