// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core;

import guru.thomasweber.tools.futuremaintenance.api.FutureMaintenance;
import guru.thomasweber.tools.futuremaintenance.api.MaintenanceTask;
import io.github.classgraph.*;

import java.util.*;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MaintenanceScanner {

  public static final Class<FutureMaintenance> ANNOTATION = FutureMaintenance.class;

  public <T extends Enum<T> & MaintenanceTask> List<MaintenanceOccurrence<T>> scan(
      String packageName) {
    Map<T, List<MaintenanceOccurrence<T>>> results = new HashMap<>();

    try (ScanResult scanResult =
        new ClassGraph().enableAllInfo().acceptPackages(packageName).scan()) {
      Stream.of(
              scanResult.getClassesWithAnnotation(ANNOTATION),
              scanResult.getClassesWithMethodAnnotation(ANNOTATION),
              scanResult.getClassesWithFieldAnnotation(ANNOTATION))
          .flatMap(ClassInfoList::stream)
          .distinct()
          .forEach(classInfo -> processClass(classInfo, results));
    }
    return results.values().stream().flatMap(Collection::stream).sorted().toList();
  }

  private <T extends Enum<T> & MaintenanceTask> void processClass(
      ClassInfo classInfo, Map<T, List<MaintenanceOccurrence<T>>> results) {
    log.info("Processing class: {}", classInfo.getName());
    addTypeInfo(classInfo, results);
    addMethodInfo(classInfo, results);
    addFieldInfo(classInfo, results);
  }

  private <T extends Enum<T> & MaintenanceTask> MaintenanceOccurrence<T> getOrCreateOccurrence(
          ClassMemberInfo classMemberInfo, Map<T, List<MaintenanceOccurrence<T>>> results) {
    var resolved = MaintenanceResolver.resolve(classMemberInfo);
    @SuppressWarnings("unchecked")
    var enumConstant = (T) resolved.enumConstant();
    results.computeIfAbsent(enumConstant, key -> new ArrayList<>());
    var occurrence = MaintenanceOccurrence.of(enumConstant, resolved.extraInformation().orElse(null));
    results.get(enumConstant).add(occurrence);
    return occurrence;
  }

  private <T extends Enum<T> & MaintenanceTask> void addFieldInfo(
      ClassInfo classInfo, Map<T, List<MaintenanceOccurrence<T>>> results) {
    classInfo.getFieldInfo().stream()
        .filter(fieldInfo -> fieldInfo.hasAnnotation(ANNOTATION))
        .forEach(fieldInfo -> getOrCreateOccurrence(fieldInfo, results)
                .withFieldUsage(classInfo.getName(), fieldInfo.getName()));
  }

  private <T extends Enum<T> & MaintenanceTask> void addMethodInfo(
      ClassInfo classInfo, Map<T, List<MaintenanceOccurrence<T>>> results) {}

  private <T extends Enum<T> & MaintenanceTask> void addTypeInfo(
      ClassInfo classInfo, Map<T, List<MaintenanceOccurrence<T>>> results) {}

}
