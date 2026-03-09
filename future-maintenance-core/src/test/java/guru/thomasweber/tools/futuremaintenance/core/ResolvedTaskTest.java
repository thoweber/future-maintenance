package guru.thomasweber.tools.futuremaintenance.core;

import guru.thomasweber.tools.futuremaintenance.core.testsubjects.TaskEnumSubject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResolvedTaskTest {

  @Test
  void checkAccessors() {
    var resolvedTask = new ResolvedTask<>(TaskEnumSubject.CLEANUP_API_METHOD, "extra");

    assertEquals(TaskEnumSubject.CLEANUP_API_METHOD, resolvedTask.enumConstant());
    assertEquals(TaskEnumSubject.CLEANUP_API_METHOD.issueNumber(), resolvedTask.issueNumber());
    assertEquals(TaskEnumSubject.CLEANUP_API_METHOD.executableAfter(), resolvedTask.executableAfter());
    assertEquals(TaskEnumSubject.CLEANUP_API_METHOD.reason(), resolvedTask.reason());
    assertEquals("extra", resolvedTask.extraInformation().orElseThrow());
  }
}