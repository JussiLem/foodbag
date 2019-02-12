package me.foodbag.hello.security;

import java.util.ArrayList;
import java.util.List;

/** Palauttaa aktiivisena olevan käyttäjän */
public class ActiveUserStorage {

  public List<String> users;

  public ActiveUserStorage() {
    users = new ArrayList<>();
  }

  public List<String> getUsers() {
    return users;
  }

  public void setUsers(List<String> users) {
    this.users = users;
  }
}
