package me.foodbag.hello.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * VerificationToken Entity does 4 thing: 1. Links back to the User(via a unidirectional relation)
 * 2. Will be created right after registration 3. Will expire within 24 hours following its creation
 * 4. Has a unique, randomly generated value 2 and 3 are part of the registration logic. 3 and 4 are
 * implemented in the entity.
 *
 * Unidirectional relationship provides navigational access in both
 * directions, so that you can access the other side without explicit queries.
 */
@Data
@Entity
public class VerificationToken {

  private static final int EXPIRATION = 60 * 24;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String token;

  /**
   * Nullable = false to ensure data integrity and consistency in the VerificationToken<->User
   * association
   */
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
