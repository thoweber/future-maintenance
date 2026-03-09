// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core;

import static java.util.Objects.requireNonNull;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.AnnotationInfoList;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassMemberInfo;
import org.jspecify.annotations.Nullable;

public interface AnnotationInfoProxy {

  static AnnotationInfoProxy ofClassInfo(ClassInfo classInfo) {
    return new ClassInfoAnnotationInfoProxy(
        requireNonNull(classInfo, "classInfo must not be null"));
  }

  static AnnotationInfoProxy ofClassMemberInfo(ClassMemberInfo classMemberInfo) {
    return new ClassMemberInfoAnnotationInfoProxy(
        requireNonNull(classMemberInfo, "classMemberInfo must not be null"));
  }

  @Nullable AnnotationInfo getAnnotationInfo(String annotationName);

  AnnotationInfoList getAnnotationInfos();
}