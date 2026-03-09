// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a code element as a known future maintenance location.
 *
 * <p>The annotation references an enum-based task registry via {@link #taskClass()} and the enum
 * constant name via {@link #value()}. This keeps maintenance metadata centralized while allowing
 * concise references directly in the code.
 *
 * <p>Supported targets are:
 *
 * <ul>
 *   <li>types
 *   <li>methods
 *   <li>constructors
 *   <li>fields
 * </ul>
 *
 * <p>Typical usage:
 *
 * <pre>{@code
 * @FutureMaintenance(taskClass = ProjectTasks.class, value = "CLEAN_UP_API")
 * class LegacyApiAdapter {
 * }
 * }</pre>
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FutureMaintenance {
  /**
   * Returns the enum class that defines the maintenance task registry.
   *
   * <p>The enum is expected to contain the constant referenced by {@link #value()}.
   */
  Class<? extends Enum<?>> taskClass();

  /**
   * Returns the enum constant name that identifies the referenced maintenance task.
   *
   * <p>Example: {@code "CLEAN_UP_API"}.
   */
  String value() default "";

  /**
   * Returns optional usage-specific information for this occurrence.
   *
   * <p>This is useful for adding local context that should not be stored globally in the task
   * registry.
   */
  String extraInformation() default "";
}
