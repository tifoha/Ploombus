package ua.tifoha.crawler;

import java.util.function.Predicate;

/**
 * Created by Vitaly on 04.09.2016.
 */
public interface UrlFilter extends Predicate<String>{
    @Override
    boolean test(String s);
}
