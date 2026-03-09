// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core;

import static java.util.Objects.requireNonNull;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.AnnotationInfoList;
import io.github.classgraph.ClassMemberInfo;
import org.jspecify.annotations.Nullable;

final class ClassMemberInfoAnnotationInfoProxy implements AnnotationInfoProxy {

  private final ClassMemberInfo classMemberInfo;

  ClassMemberInfoAnnotationInfoProxy(ClassMemberInfo classMemberInfo) {
    this.classMemberInfo = requireNonNull(classMemberInfo, "classMemberInfo must not be null");
  }

  @Override
  public @Nullable AnnotationInfo getAnnotationInfo(String annotationName) {
    return classMemberInfo.getAnnotationInfo(
        requireNonNull(annotationName, "annotationName must not be null"));
  }

  @Override
  public AnnotationInfoList getAnnotationInfos() {
    return classMemberInfo.getAnnotationInfo();
  }
}
