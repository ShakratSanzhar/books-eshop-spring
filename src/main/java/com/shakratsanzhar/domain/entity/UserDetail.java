package com.shakratsanzhar.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "phone")
@ToString(exclude = "user")
@Entity
@Table(name = "user_details")
@Slf4j
public class UserDetail implements BaseEntity<Long> {

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private User user;

    private String name;

    private String surname;

    private LocalDate birthday;

    @Column(unique = true)
    private String phone;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    public void setUser(User user) {
        user.setUserDetail(this);
        this.user = user;
        this.id = user.getId();
    }
}
