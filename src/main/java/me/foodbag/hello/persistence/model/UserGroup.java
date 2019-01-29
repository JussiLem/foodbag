package me.foodbag.hello.persistence.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "usergroup")
public class UserGroup {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long groupId;
  @Column(name="groupName", columnDefinition="TEXT")
  private String groupName;

}
