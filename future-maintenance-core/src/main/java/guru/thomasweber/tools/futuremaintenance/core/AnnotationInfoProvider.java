package guru.thomasweber.tools.futuremaintenance.core;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.AnnotationInfoList;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassMemberInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;

import static java.util.Objects.nonNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AnnotationInfoProvider {

  @Nullable
  private ClassInfo classInfo;
  @Nullable
  private ClassMemberInfo classMemberInfo;

  public static AnnotationInfoProvider of(ClassInfo classInfo) {
    return new AnnotationInfoProvider(classInfo, null);
  }

  public static AnnotationInfoProvider of(ClassMemberInfo classMemberInfo) {
    return new AnnotationInfoProvider(null, classMemberInfo);
  }

  public @Nullable AnnotationInfo getAnnotationInfo(String annotationName) {
    if (nonNull(classInfo)) {
      return classInfo.getAnnotationInfo(annotationName);
    }
    if (nonNull(classMemberInfo)) {
      return classMemberInfo.getAnnotationInfo(annotationName);
    }
    throw new IllegalStateException("AnnotationInfoProvider is not initialized");
  }

  public AnnotationInfoList getAnnotationInfos() {
    if (nonNull(classInfo)) {
      return classInfo.getAnnotationInfo();
    }
    if (nonNull(classMemberInfo)) {
      return classMemberInfo.getAnnotationInfo();
    }
    throw new IllegalStateException("AnnotationInfoProvider is not initialized");
  }

  public String getClassName() {
    if (nonNull(classInfo)) {
      return classInfo.getName();
    }
    if (nonNull(classMemberInfo)) {
      return classMemberInfo.getClassInfo().getName();
    }
    throw new IllegalStateException("AnnotationInfoProvider is not initialized");
  }
}
