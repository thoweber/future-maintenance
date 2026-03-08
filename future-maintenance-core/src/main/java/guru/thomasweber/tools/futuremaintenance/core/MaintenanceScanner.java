// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core;

import guru.thomasweber.tools.futuremaintenance.api.FutureMaintenance;
import guru.thomasweber.tools.futuremaintenance.api.MaintenanceTask;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Stream;

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
    return results.values().stream().flatMap(Collection::stream) /*.sorted()*/.toList();
  }

  private <T extends Enum<T> & MaintenanceTask> void processClass(
      ClassInfo classInfo, Map<T, List<MaintenanceOccurrence<T>>> results) {
    log.info("Processing class: {}", classInfo.getName());
    addTypeInfo(classInfo, results);
    addFieldInfo(classInfo, results);
    addConstructorInfo(classInfo, results);
    addMethodInfo(classInfo, results);
  }

  private <T extends Enum<T> & MaintenanceTask> MaintenanceOccurrence<T> getOrCreateOccurrence(
      ResolvedTask<T> resolvedTask, Map<T, List<MaintenanceOccurrence<T>>> results) {
    var enumConstant = resolvedTask.enumConstant();
    results.computeIfAbsent(enumConstant, key -> new ArrayList<>());
    var occurrence =
        MaintenanceOccurrence.of(enumConstant, resolvedTask.extraInformation().orElse(null));
    results.get(enumConstant).add(occurrence);
    return occurrence;
  }

  private <T extends Enum<T> & MaintenanceTask> void addFieldInfo(
      ClassInfo classInfo, Map<T, List<MaintenanceOccurrence<T>>> results) {
    classInfo.getFieldInfo().stream()
        .filter(fieldInfo -> fieldInfo.hasAnnotation(ANNOTATION))
        .forEach(
            fieldInfo -> {
              Optional<ResolvedTask<T>> optTask =
                  MaintenanceResolver.resolve(AnnotationInfoProvider.of(fieldInfo));
              optTask.ifPresent(
                  resolvedTask ->
                      getOrCreateOccurrence(resolvedTask, results)
                          .withFieldUsage(classInfo.getName(), LocationMapper.mapField(fieldInfo)));
            });
  }

  private <T extends Enum<T> & MaintenanceTask> void addMethodInfo(
      ClassInfo classInfo, Map<T, List<MaintenanceOccurrence<T>>> results) {
    classInfo.getMethodInfo().stream()
        .filter(methodInfo -> methodInfo.hasAnnotation(ANNOTATION))
        .forEach(
            methodInfo -> {
              Optional<ResolvedTask<T>> optTask =
                  MaintenanceResolver.resolve(AnnotationInfoProvider.of(methodInfo));
              optTask.ifPresent(
                  resolvedTask ->
                      getOrCreateOccurrence(resolvedTask, results)
                          .withMethodUsage(
                              classInfo.getName(), LocationMapper.mapMethod(methodInfo)));
            });
  }

  private <T extends Enum<T> & MaintenanceTask> void addConstructorInfo(
      ClassInfo classInfo, Map<T, List<MaintenanceOccurrence<T>>> results) {
    classInfo.getConstructorInfo().stream()
        .filter(methodInfo -> methodInfo.hasAnnotation(ANNOTATION))
        .forEach(
            methodInfo -> {
              Optional<ResolvedTask<T>> optTask =
                  MaintenanceResolver.resolve(AnnotationInfoProvider.of(methodInfo));
              optTask.ifPresent(
                  resolvedTask ->
                      getOrCreateOccurrence(resolvedTask, results)
                          .withConstructorUsage(
                              classInfo.getName(), LocationMapper.mapMethod(methodInfo)));
            });
  }

  private <T extends Enum<T> & MaintenanceTask> void addTypeInfo(
      ClassInfo classInfo, Map<T, List<MaintenanceOccurrence<T>>> results) {
    Optional<ResolvedTask<T>> optTask =
        MaintenanceResolver.resolve(AnnotationInfoProvider.of(classInfo));
    optTask.ifPresent(
        resolvedTask ->
            getOrCreateOccurrence(resolvedTask, results)
                .withClassUsage(classInfo.getName(), LocationMapper.mapClass(classInfo)));
  }
}
