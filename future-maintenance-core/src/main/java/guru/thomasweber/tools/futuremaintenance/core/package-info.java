/**
 * Runtime support for scanning and resolving future maintenance occurrences.
 *
 * <p>This package contains the implementation that discovers {@code @FutureMaintenance}
 * annotations, resolves them against task registries, and exposes the resulting occurrences for
 * reporting or automation.
 *
 * <p>The most relevant entry point for consumers is {@link
 * guru.thomasweber.tools.futuremaintenance.core.MaintenanceScanner}.
 */
@NullMarked
package guru.thomasweber.tools.futuremaintenance.core;

import org.jspecify.annotations.NullMarked;
