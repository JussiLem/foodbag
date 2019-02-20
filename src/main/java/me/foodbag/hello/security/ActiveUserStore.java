package me.foodbag.hello.security;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/** Palauttaa aktiivisena olevan käyttäjän */
@Getter
@Setter
public class ActiveUserStore {

  private List<String> users;

  public ActiveUserStore() {
    users = new ArrayList<>();
  }

}
