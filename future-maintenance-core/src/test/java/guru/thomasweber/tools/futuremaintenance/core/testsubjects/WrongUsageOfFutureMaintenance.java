package guru.thomasweber.tools.futuremaintenance.core.testsubjects;

import guru.thomasweber.tools.futuremaintenance.api.FutureMaintenance;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@FutureMaintenance(taskClass = TaskEnumSubject.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface WrongUsageOfFutureMaintenance {
  // IMPORTANT: never use a default for "value" in your custom annotation!
  String value() default "";
  String extraInformation() default "";
}
