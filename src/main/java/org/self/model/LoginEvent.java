package org.self.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter @Getter @EqualsAndHashCode @ToString
@Table(name = "login_events")
public class LoginEvent {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "event_id")
	private Long id;
	@Column(name = "user")
	private String userName;
	@Column(name = "event-name")
	private String eventName;
	@Column(name = "fail-reason")
	private String reason;
	@Column(name = "is-login-success")
	private Short loginSuccess;
}
