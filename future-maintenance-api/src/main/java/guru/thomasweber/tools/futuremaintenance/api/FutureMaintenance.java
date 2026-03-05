// SPDX-FileCopyrightText: 2026 Thomas Weber
// SPDX-License-Identifier: MIT
// For full license text see: https://github.com/thoweber/future-maintenance/blob/main/LICENSE
package guru.thomasweber.tools.futuremaintenance.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FutureMaintenance {
  /** The User's Enum class (e.g., MyProjectTasks.class) */
  Class<? extends Enum<?>> taskClass();

  /** The name of the enum constant (e.g., "CLEANUP_API") */
  String value();

  String extraInformation() default "";
}
