package me.foodbag.hello.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.List;

/**
 * Uses the HttpSessionBindingListener interface. Listens to events of type HttpSessionBindingEvent,
 * which are triggered whenever a value is set or removed, or, in other words, bound or unbound, to
 * the HTTP session
 */
@Getter
@Setter
@Component
public class LoggedUser implements HttpSessionBindingListener {

  private String username;

  private ActiveUserStore activeUserStorage;

  public LoggedUser(String username, ActiveUserStore activeUserStorage) {
    this.username = username;
    this.activeUserStorage = activeUserStorage;
  }

  public LoggedUser() {}

  /**
   * the valueBound() method will be called when the user logs in
   *
   * @param event
   */
  @Override
  public void valueBound(HttpSessionBindingEvent event) {
    List<String> users = activeUserStorage.getUsers();
    LoggedUser loggedUser = (LoggedUser) event.getValue();
    if (!users.contains(loggedUser.getUsername())) {
      users.add(loggedUser.getUsername());
    }
  }

  /**
   * valueUnbound() method will be called when the user logs out or when the session expires
   *
   * @param event
   */
  @Override
  public void valueUnbound(HttpSessionBindingEvent event) {
    List<String> users = activeUserStorage.getUsers();
    LoggedUser user = (LoggedUser) event.getValue();
    users.remove(user.getUsername());
  }
}
