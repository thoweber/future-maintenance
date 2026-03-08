// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core;

import guru.thomasweber.tools.futuremaintenance.api.FutureMaintenance;
import guru.thomasweber.tools.futuremaintenance.api.MaintenanceTask;
import io.github.classgraph.AnnotationClassRef;
import io.github.classgraph.AnnotationInfo;

import java.util.Optional;

public class MaintenanceResolver {

  private static final String TASK_CLASS = "taskClass";
  private static final String VALUE = "value";
  private static final String EXTRA_INFORMATION = "extraInformation";

  public static <T extends Enum<T> & MaintenanceTask> Optional<ResolvedTask<T>> resolve(
      AnnotationInfoProvider annotationInfoProvider) {
    // 1. Check for direct annotation
    AnnotationInfo direct = annotationInfoProvider.getAnnotationInfo(FutureMaintenance.class.getName());
    if (direct != null) {
      Optional<ResolvedTask<T>> resolved = fromAnnotation(direct, direct);
      if (resolved.isPresent()) {
        return resolved;
      }
    }

    // 2. Check for meta-annotation (The "Template" pattern)
    for (AnnotationInfo usage : annotationInfoProvider.getAnnotationInfos()) {
      AnnotationInfo meta =
          usage.getClassInfo().getAnnotationInfo(FutureMaintenance.class.getName());
      if (meta != null) {
        // The template (meta) has the 'taskClass',
        // but the usage site might have the 'value' (constant name)
        Optional<ResolvedTask<T>> resolved = fromAnnotation(usage, meta);
        if (resolved.isPresent()) {
          return resolved;
        }
      }
    }
    return Optional.empty();
  }

  private static <T extends Enum<T> & MaintenanceTask> Optional<ResolvedTask<T>> fromAnnotation(
      AnnotationInfo usage, AnnotationInfo template) {
    // taskClass usually comes from the Template/Meta level
    AnnotationClassRef classRef =
        (AnnotationClassRef) template.getParameterValues().getValue(TASK_CLASS);

    // value (the constant name) and extra-info usually come from the Usage site
    String constantName = (String) usage.getParameterValues().getValue(VALUE);
    String extraInfo = (String) template.getParameterValues().getValue(EXTRA_INFORMATION);

    // Fallback: if usage value is empty, try template value
    if (constantName == null || constantName.isEmpty()) {
      constantName = (String) template.getParameterValues().getValue(VALUE);
    }
    if (extraInfo == null || extraInfo.isEmpty()) {
        extraInfo = (String) template.getParameterValues().getValue(EXTRA_INFORMATION);
    }

    if (constantName == null || constantName.isEmpty()) {
      return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    Class<T> enumClass = (Class<T>) classRef.loadClass();
    T enumConstant = Enum.valueOf(enumClass, constantName);

    return Optional.of(new ResolvedTask<>(enumConstant, extraInfo));
  }

}
