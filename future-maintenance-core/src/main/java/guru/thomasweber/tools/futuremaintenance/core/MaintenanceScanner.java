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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MaintenanceScanner {

  public static final Class<FutureMaintenance> ANNOTATION = FutureMaintenance.class;

  public List<MaintenanceOccurrence> scan(String packageName) {
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

  private void processClass(ClassInfo classInfo, List<MaintenanceOccurrence> results) {
    log.info("Processing class: {}", classInfo.getName());
    addTypeInfo(classInfo, results);
    addFieldInfo(classInfo, results);
    addConstructorInfo(classInfo, results);
    addMethodInfo(classInfo, results);
  }

  private <T extends Enum<T> & MaintenanceTask>
      MaintenanceOccurrence.MaintenanceOccurrenceBuilder occurenceBuilder(
          ResolvedTask<T> resolvedTask) {
    var enumConstant = resolvedTask.enumConstant();
    return MaintenanceOccurrence.builder()
        .key(MaintenanceOccurrence.Key.of(enumConstant))
        .extraInformation(resolvedTask.extraInformation().orElse(null));
  }

  private void addFieldInfo(ClassInfo classInfo, List<MaintenanceOccurrence> results) {
    classInfo.getFieldInfo().stream()
        .filter(fieldInfo -> fieldInfo.hasAnnotation(ANNOTATION))
        .map(
            fieldInfo ->
                MaintenanceResolver.resolve(AnnotationInfoProxy.ofClassMemberInfo(fieldInfo))
                    .map(
                        resolvedTask ->
                            occurenceBuilder(resolvedTask)
                                .forFieldUsage(
                                    classInfo.getName(), LocationMapper.mapField(fieldInfo))))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .forEach(results::add);
  }

  private void addMethodInfo(ClassInfo classInfo, List<MaintenanceOccurrence> results) {
    classInfo.getMethodInfo().stream()
        .filter(methodInfo -> methodInfo.hasAnnotation(ANNOTATION))
        .map(
            methodInfo ->
                MaintenanceResolver.resolve(AnnotationInfoProxy.ofClassMemberInfo(methodInfo))
                    .map(
                        resolvedTask ->
                            occurenceBuilder(resolvedTask)
                                .forMethodUsage(
                                    classInfo.getName(), LocationMapper.mapMethod(methodInfo))))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .forEach(results::add);
  }

  private void addConstructorInfo(ClassInfo classInfo, List<MaintenanceOccurrence> results) {
    classInfo.getConstructorInfo().stream()
        .filter(methodInfo -> methodInfo.hasAnnotation(ANNOTATION))
        .map(
            methodInfo ->
                MaintenanceResolver.resolve(AnnotationInfoProxy.ofClassMemberInfo(methodInfo))
                    .map(
                        resolvedTask ->
                            occurenceBuilder(resolvedTask)
                                .forConstructorUsage(
                                    classInfo.getName(), LocationMapper.mapMethod(methodInfo))))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .forEach(results::add);
  }

  private void addTypeInfo(ClassInfo classInfo, List<MaintenanceOccurrence> results) {
    MaintenanceResolver.resolve(AnnotationInfoProxy.ofClassInfo(classInfo))
        .map(
            resolvedTask ->
                occurenceBuilder(resolvedTask)
                    .forClassUsage(classInfo.getName(), LocationMapper.mapClass(classInfo)))
        .ifPresent(results::add);
  }
}
