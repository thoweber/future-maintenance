// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaintenanceScannerTest {

  @Test
  void scan() {
    var scanner = new MaintenanceScanner();
    var occurrences = scanner.scan("guru.thomasweber.tools.futuremaintenance.core");

    assertEquals(8, occurrences.size());
  }
}
