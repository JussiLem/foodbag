package me.foodbag.hello.security;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/** ActiveUserStorage acts as an in-memory store for the logged in users */
@Getter
@Setter
public class ActiveUserStore {

  private List<String> users;

  public ActiveUserStore() {
    users = new ArrayList<>();
  }

}
