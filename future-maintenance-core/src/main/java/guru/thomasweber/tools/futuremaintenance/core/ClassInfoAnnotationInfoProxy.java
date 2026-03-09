// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core;

import static java.util.Objects.requireNonNull;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.AnnotationInfoList;
import io.github.classgraph.ClassInfo;
import org.jspecify.annotations.Nullable;

final class ClassInfoAnnotationInfoProxy implements AnnotationInfoProxy {

  private final ClassInfo classInfo;

  ClassInfoAnnotationInfoProxy(ClassInfo classInfo) {
    this.classInfo = requireNonNull(classInfo, "classInfo must not be null");
  }

  @Override
  public @Nullable AnnotationInfo getAnnotationInfo(String annotationName) {
    return classInfo.getAnnotationInfo(requireNonNull(annotationName, "annotationName must not be null"));
  }

  @Override
  public AnnotationInfoList getAnnotationInfos() {
    return classInfo.getAnnotationInfo();
  }
}