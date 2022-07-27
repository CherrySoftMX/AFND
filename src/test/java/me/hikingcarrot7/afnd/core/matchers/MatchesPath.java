package me.hikingcarrot7.afnd.core.matchers;

import lombok.AllArgsConstructor;
import me.hikingcarrot7.afnd.core.afnd.MatchResult;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

@AllArgsConstructor
public class MatchesPath extends TypeSafeMatcher<MatchResult> {
  private String expected;

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

  public static Matcher<MatchResult> matchesPath(String expected) {
    return new MatchesPath(expected);
  }
}
