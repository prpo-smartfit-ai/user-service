package com.smartfitai.services;

import com.smartfitai.models.User;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.security.MessageDigest;
import java.util.*;

@ApplicationScoped
public class UserService {
    
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public User createUser(String email, String password, String firstName, String lastName) {
        String hashedPassword = hashPassword(password);
        
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(hashedPassword);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        
        em.persist(user);
        em.flush();
        
        return user;
    }

    public User findByEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean verifyPassword(User user, String password) {
        String hashedPassword = hashPassword(password);
        return user.getPasswordHash().equals(hashedPassword);
    }

    public String generateToken(User user) {
        return "token_" + user.getId() + "_" + UUID.randomUUID().toString();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
