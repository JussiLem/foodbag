package me.foodbag.hello.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.List;

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

  @Override
  public void valueBound(HttpSessionBindingEvent event) {
    List<String> users = activeUserStorage.getUsers();
    LoggedUser loggedUser = (LoggedUser) event.getValue();
    if (!users.contains(loggedUser.getUsername())) {
      users.add(loggedUser.getUsername());
    }
  }

  @Override
  public void valueUnbound(HttpSessionBindingEvent event) {
    List<String> users = activeUserStorage.getUsers();
    LoggedUser user = (LoggedUser) event.getValue();
    users.remove(user.getUsername());
  }
}
