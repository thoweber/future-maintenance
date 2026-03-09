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

  public List<MaintenanceOccurrence> scan(
      String packageName) {
    List<MaintenanceOccurrence> results = new ArrayList<>();

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
    return Collections.unmodifiableList(results);
  }

  private void processClass(
      ClassInfo classInfo, List<MaintenanceOccurrence> results) {
    log.info("Processing class: {}", classInfo.getName());
    addTypeInfo(classInfo, results);
    addFieldInfo(classInfo, results);
    addConstructorInfo(classInfo, results);
    addMethodInfo(classInfo, results);
  }

  private <T extends Enum<T> & MaintenanceTask> MaintenanceOccurrence.MaintenanceOccurrenceBuilder occurenceBuilder(
      ResolvedTask<T> resolvedTask) {
    var enumConstant = resolvedTask.enumConstant();
    return MaintenanceOccurrence.builder()
            .key(MaintenanceOccurrence.Key.of(enumConstant))
            .extraInformation(resolvedTask.extraInformation().orElse(null));
  }

  private <T extends Enum<T> & MaintenanceTask> void addFieldInfo(
      ClassInfo classInfo, List<MaintenanceOccurrence> results) {
    classInfo.getFieldInfo().stream()
        .filter(fieldInfo -> fieldInfo.hasAnnotation(ANNOTATION))
        .forEach(
            fieldInfo -> {
              Optional<ResolvedTask<T>> optTask =
                  MaintenanceResolver.resolve(AnnotationInfoProvider.of(fieldInfo));
              optTask.ifPresent(
                  resolvedTask ->
                      results.add(occurenceBuilder(resolvedTask)
                          .forFieldUsage(classInfo.getName(), LocationMapper.mapField(fieldInfo))));
            });
  }

  private <T extends Enum<T> & MaintenanceTask> void addMethodInfo(
      ClassInfo classInfo, List<MaintenanceOccurrence> results) {
    classInfo.getMethodInfo().stream()
        .filter(methodInfo -> methodInfo.hasAnnotation(ANNOTATION))
        .forEach(
            methodInfo -> {
              Optional<ResolvedTask<T>> optTask =
                  MaintenanceResolver.resolve(AnnotationInfoProvider.of(methodInfo));
              optTask.ifPresent(
                  resolvedTask ->
                      results.add(occurenceBuilder(resolvedTask)
                          .forMethodUsage(
                              classInfo.getName(), LocationMapper.mapMethod(methodInfo))));
            });
  }

  private <T extends Enum<T> & MaintenanceTask> void addConstructorInfo(
      ClassInfo classInfo, List<MaintenanceOccurrence> results) {
    classInfo.getConstructorInfo().stream()
        .filter(methodInfo -> methodInfo.hasAnnotation(ANNOTATION))
        .forEach(
            methodInfo -> {
              Optional<ResolvedTask<T>> optTask =
                  MaintenanceResolver.resolve(AnnotationInfoProvider.of(methodInfo));
              optTask.ifPresent(
                  resolvedTask ->
                      results.add(occurenceBuilder(resolvedTask)
                          .forConstructorUsage(
                              classInfo.getName(), LocationMapper.mapMethod(methodInfo))));
            });
  }

  private <T extends Enum<T> & MaintenanceTask> void addTypeInfo(
      ClassInfo classInfo, List<MaintenanceOccurrence> results) {
    Optional<ResolvedTask<T>> optTask =
        MaintenanceResolver.resolve(AnnotationInfoProvider.of(classInfo));
    optTask.ifPresent(
        resolvedTask ->
            results.add(occurenceBuilder(resolvedTask)
                .forClassUsage(classInfo.getName(), LocationMapper.mapClass(classInfo))));
  }
}
