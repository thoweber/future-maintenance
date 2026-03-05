// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core;

import guru.thomasweber.tools.futuremaintenance.api.FutureMaintenance;
import guru.thomasweber.tools.futuremaintenance.api.MaintenanceOccurrence;
import guru.thomasweber.tools.futuremaintenance.api.MaintenanceTask;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class MaintenanceScanner {

  public <T extends Enum<T> & MaintenanceTask> List<MaintenanceOccurrence<T>> scan(
      String packageName) {
    Map<T, MaintenanceOccurrence<T>> results = new HashMap<>();

    try (ScanResult scanResult =
        new ClassGraph()
            .enableAllInfo()
            .enableClassInfo()
            .enableMethodInfo()
            .acceptPackages(packageName)
            .scan()) {
      var classesWithAnnotation =
          Stream.of(
                  scanResult.getClassesWithAnnotation(FutureMaintenance.class),
                  scanResult.getClassesWithMethodAnnotation(FutureMaintenance.class),
                  scanResult.getClassesWithFieldAnnotation(FutureMaintenance.class))
              .flatMap(ClassInfoList::stream)
              .distinct()
              .toList();
      System.out.println("Found " + classesWithAnnotation.size() + " classes with annotation");
    }
    return results.values().stream().sorted().toList();
  }

  private void addMethodOccurences(ClassInfoList classesWithMethodAnnotation) {}
}
