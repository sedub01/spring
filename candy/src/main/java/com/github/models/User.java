package com.github.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements UserDetails {
    // реализация UserDetails нужна для последующей реализации UserDetailsService, где
    // интерфейс будет реализовывать объект класса User (как будто это его родная сущность)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Ник не может быть пустым")
    private String username;
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
    @Transient // без этой аннотации hibernate попытается запихать это поле в БД или получить его
               // (перевод. как "временный")
    private String validationPassword;
    // Если название в таблице то же, что и в описании класса, то название колонки можно не помечать
    // В таблице оно "is_active" из-за camelCase'а
    private boolean isActive;

    @Email(message = "Некорректный формат почты")
    @NotBlank(message = "Адрес почты не может быть пустым")
    private String email;
    private String activationCode;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    //fetch - параметр, определяющий, как данные будут подгружаться относительно сущности
    //Когда мы загружаем пользователя, его роли хранятся в отдельной таблице, и их
    //можно загружать либо жадным способом (EAGER), либо ленивым (LAZY)
    //Жадный - это когда hibernate сразу всё загружает, ленивый - загружает при обращении
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    //Аннотация говорит, что сет будет храниться в отдельной таблице, для которой нет mapping'а
    //joinColumn говорит, что создается таблица user_role, которая будет соединяться с текущей таблицей
    //по user_id
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getValidationPassword() {
        return validationPassword;
    }

    public void setValidationPassword(String validationPassword) {
        this.validationPassword = validationPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
