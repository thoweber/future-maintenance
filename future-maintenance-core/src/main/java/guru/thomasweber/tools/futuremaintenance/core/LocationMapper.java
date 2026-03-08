// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core;

import io.github.classgraph.ClassInfo;
import io.github.classgraph.FieldInfo;
import io.github.classgraph.MethodInfo;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class LocationMapper {

  public static String mapField(FieldInfo fieldInfo) {
    return String.join(" ", fieldInfo.getTypeSignatureOrTypeDescriptor().toStringWithSimpleNames(),
        fieldInfo.getName());
  }

  public static String mapMethod(MethodInfo methodInfo) {
    var parameters = Arrays.stream(methodInfo.getParameterInfo()).map(mpi -> mpi.getTypeSignatureOrTypeDescriptor().toStringWithSimpleNames())
        .collect(Collectors.joining(", "));
    return String.join("", methodInfo.getName(), "(", parameters, ")");
  }

  public static String mapClass(ClassInfo classInfo) {
    return classInfo.getSimpleName();
  }
}
