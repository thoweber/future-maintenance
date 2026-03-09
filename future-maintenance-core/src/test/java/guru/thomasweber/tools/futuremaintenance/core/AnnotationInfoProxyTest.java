// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.core;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AnnotationInfoProxyTest {

  @Test
  void ofClassMemberInfo_throwsNullPointerException() {
    assertThrows(NullPointerException.class, () -> AnnotationInfoProxy.ofClassMemberInfo(null));
  }

  @Test
  void ofClassInfo_throwsNullPointerException() {
    assertThrows(NullPointerException.class, () -> AnnotationInfoProxy.ofClassInfo(null));
  }
}
