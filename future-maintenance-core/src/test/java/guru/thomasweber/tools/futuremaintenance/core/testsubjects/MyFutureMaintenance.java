package guru.thomasweber.tools.futuremaintenance.core.testsubjects;

import guru.thomasweber.tools.futuremaintenance.api.FutureMaintenance;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@FutureMaintenance(taskClass = TaskEnumSubject.class) // Set once here
@Retention(RetentionPolicy.RUNTIME)
public @interface MyFutureMaintenance {
  String value();
  String extraInformation() default "";
}
