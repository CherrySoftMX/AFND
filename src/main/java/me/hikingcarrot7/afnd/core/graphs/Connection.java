package me.hikingcarrot7.afnd.core.graphs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Connection<E> {
  private Node<?> origin;
  private Node<?> destination;
  private E condition;

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
    final Connection<?> other = (Connection<?>) obj;
    if (!Objects.equals(this.destination, other.destination)) {
      return false;
    }
    if (!Objects.equals(this.origin, other.origin)) {
      return false;
    }
    return Objects.equals(this.condition, other.condition);
  }

}
