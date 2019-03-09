package me.foodbag.hello.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * When a password reset is triggered – a token will be created and a special link containing this
 * token will be emailed to the user. //not yet implemented. Email not working yet.
 */
@Entity
@Data
public class PasswordResetToken {

  private static final int EXPIRATION = 60 * 24;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String token;

  @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "user_id")
  private User user;

  private Date expiryDate;

  public PasswordResetToken() {
    super();
  }

  public PasswordResetToken(final String token, final User user) {
    super();

    this.token = token;
    this.user = user;
    this.expiryDate = calculateExpiryDate();
  }

  private Date calculateExpiryDate() {
    final Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(new Date().getTime());
    cal.add(Calendar.MINUTE, PasswordResetToken.EXPIRATION);
    return new Date(cal.getTime().getTime());
  }

  public void updateToken(final String token) {
    this.token = token;
    this.expiryDate = calculateExpiryDate();
  }
}
