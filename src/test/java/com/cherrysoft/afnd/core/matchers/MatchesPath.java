package com.cherrysoft.afnd.core.matchers;

import com.cherrysoft.afnd.core.automata.MatchResult;
import lombok.AllArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

@AllArgsConstructor
public class MatchesPath extends TypeSafeMatcher<MatchResult> {
  private String expected;

  public static Matcher<MatchResult> matchesPath(String expected) {
    return new MatchesPath(expected);
  }

  @Override
  protected boolean matchesSafely(MatchResult result) {
    var path = result.getPath();
    if (expected.length() != path.size()) {
      return false;
    }
    for (int i = 0; i < expected.length(); i++) {
      String ch = String.valueOf(expected.toCharArray()[i]);
      Object condition = path.get(i).getConnection().getCondition();
      if (!ch.equals(condition)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("matches path");
  }
}
