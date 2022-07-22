package me.hikingcarrot7.afnd.model.utils;

import java.util.Objects;

public class Pair<L, R> {
  private L left;
  private R right;

  public Pair(L left, R right) {
    this.left = left;
    this.right = right;
  }

  public L getLeft() {
    return left;
  }

  public void setLeft(L left) {
    this.left = left;
  }

  public R getRight() {
    return right;
  }

  public void setRight(R right) {
    this.right = right;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 23 * hash + Objects.hashCode(this.left);
    hash = 23 * hash + Objects.hashCode(this.right);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Pair<?, ?> other = (Pair<?, ?>) obj;
    if (!Objects.equals(this.left, other.left)) {
      return false;
    }
    return Objects.equals(this.right, other.right);
  }

  @Override
  public String toString() {
    return "Pair{" + "left=" + left + ", right=" + right + '}';
  }

}
