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
public class AnnotationInfoProxy {

  @Nullable
  private ClassInfo classInfo;
  @Nullable
  private ClassMemberInfo classMemberInfo;

  public static AnnotationInfoProxy ofClassInfo(ClassInfo classInfo) {
    return new AnnotationInfoProxy(classInfo, null);
  }

  public static AnnotationInfoProxy ofClassMemberInfo(ClassMemberInfo classMemberInfo) {
    return new AnnotationInfoProxy(null, classMemberInfo);
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

}
