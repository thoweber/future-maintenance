package guru.thomasweber.tools.futuremaintenance.core;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class MaintenanceOccurrenceTest {

  @ParameterizedTest
  @NullSource
  @EmptySource
  @ValueSource(strings = { "  " })
  void builder_extraInformation_isSetToEmptyOptional_forNullEmptyAndBlank(String extraInformation) {
    var maintenanceOccurrence = MaintenanceOccurrence.builder().extraInformation(extraInformation)
        .forClassUsage("class", "location");
    assertTrue(maintenanceOccurrence.extraInformation().isEmpty());
  }

}