package suts.desigenpattens.learnnote.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by sutingshuai on 2019-09-10
 * Describe:
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
