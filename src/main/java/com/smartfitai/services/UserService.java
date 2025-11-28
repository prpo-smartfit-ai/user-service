package com.smartfitai.services;

import com.smartfitai.models.User;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.mindrot.jbcrypt.BCrypt;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserService {
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private JwtService jwtService;

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
    
    public User findById(Long id) {
        try {
            return em.find(User.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean verifyPassword(User user, String password) {
        return BCrypt.checkpw(password, user.getPasswordHash());
    }

    public String generateToken(User user) {
        return jwtService.generateToken(user.getId(), user.getEmail());
    }

    @Transactional
    public User updateUser(User user) {
        return em.merge(user);
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
