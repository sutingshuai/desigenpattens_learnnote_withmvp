package suts.desigenpattens.learnnote.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by sutingshuai on 2019-08-29
 * Describe:
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiInfo {
}
