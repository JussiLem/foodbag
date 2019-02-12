package me.foodbag.hello.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
public class VerificationToken {

  private static final int EXPIRATION = 60 * 24;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String token;

  @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "user_id", foreignKey = @ForeignKey(name = "FK_VERIFY_USER"))
  private User user;

  private Date expiryDate;

  public void updateToken(final String token) {
    this.token = token;
    this.expiryDate = calculateExpiryDate();
  }

  private Date calculateExpiryDate() {
    final Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(new Date().getTime());
    cal.add(Calendar.MINUTE, VerificationToken.EXPIRATION);
    return new Date(cal.getTime().getTime());
  }

  public VerificationToken() {
    super();
  }


  public VerificationToken(final String token, final User user) {
    super();

    this.token = token;
    this.user = user;
    this.expiryDate = calculateExpiryDate();
  }
}
