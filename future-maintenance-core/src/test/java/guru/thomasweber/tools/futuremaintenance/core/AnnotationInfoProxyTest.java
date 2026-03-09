package guru.thomasweber.tools.futuremaintenance.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AnnotationInfoProxyTest {

  @Test
  void getAnnotationInfo_throwsIllegalStateException_whenNotInitialized() {
    AnnotationInfoProxy provider = AnnotationInfoProxy.ofClassMemberInfo(null);
    assertThrows(IllegalStateException.class, () -> provider.getAnnotationInfo("ignored"));
  }

  @Test
  void getAnnotationInfos_throwsIllegalStateException_whenNotInitialized() {
    AnnotationInfoProxy provider = AnnotationInfoProxy.ofClassMemberInfo(null);
    assertThrows(IllegalStateException.class, provider::getAnnotationInfos);
  }
}