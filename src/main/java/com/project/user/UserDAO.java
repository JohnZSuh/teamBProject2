package com.project.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.common.datasource.ConnectionFactory;
import com.project.common.exceptions.DataSourceException;
import com.project.common.exceptions.ResourceNotFoundException;


//* DAO = Data Access Object
@Repository
public class UserDAO {
    
    private static Logger logger = LogManager.getLogger(UserDAO.class);
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private final ConnectionFactory connectionFactory;

    @Autowired
    public UserDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
    
    private final String baseSelect = "SELECT eu.user_id, eu.username, eu.email, eu.password, " +
                                        "eu.given_name, eu.surname, " +
                                        "eu.is_active, eur.role " +
                                        "FROM ers_users eu " +
                                        "JOIN ers_user_roles eur ON eu.role_id = eur.role_id ";

    public List<User> getAllUsers() {

        String sql = baseSelect;

        try (Connection conn = connectionFactory.getConnection()) {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            return mapResultSet(rs);

        } catch (SQLException e) {
            logger.error("Error connecting to database at {}, error message {}", LocalDateTime.now().format(format), e.getMessage());
            throw new DataSourceException(e);
        }

    }
    
    public String save(User user) {
        String sql = "INSERT INTO ers_users (user_id, username, email, password, given_name, surname, is_active, role_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, '4')";

        try (Connection conn = connectionFactory.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUser_id().trim());
            pstmt.setString(2, user.getUsername().trim());
            pstmt.setString(3, user.getEmail().trim());
            pstmt.setString(4, user.getPassword().trim());
            pstmt.setString(5, user.getGiven_name().trim());
            pstmt.setString(6, user.getSurname().trim());
            pstmt.setBoolean(7, user.getIs_active());
            
            pstmt.executeUpdate();

            return user.getUsername() + " has been added. ID: " + user.getUser_id();

        } catch (SQLException e) {
            logger.warn("Error connecting to database at {}, error message {}", LocalDateTime.now().format(format), e.getMessage());
            throw new DataSourceException(e);
        }
    }

    public Optional<User> findUserByUsernameAndPassword(String username, String password) {

        String sql = baseSelect + "WHERE eu.username = ? and eu.password = ?";

        try (Connection conn = connectionFactory.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            
            return mapResultSet(rs).stream().findFirst();

        } catch (SQLException e) {
            logger.warn("Error connecting to database at {}, error message {}", LocalDateTime.now().format(format), e.getMessage());
            throw new DataSourceException(e);
        }
    }
    // for uuid change string to uuid
    public Optional<User> findUserById(String id) {

        String sql = baseSelect + "WHERE eu.user_id = ?";

        try (Connection conn = connectionFactory.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            //for uuid change setString to setObject
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();

            return mapResultSet(rs).stream().findFirst();

        } catch (SQLException e) {
            logger.warn("Error connecting to database at {}, error message {}", LocalDateTime.now().format(format), e.getMessage());
            throw new DataSourceException(e);
        }
    }

    public Optional<User> findUserByUsername(String username) {

        String sql = baseSelect + "WHERE eu.username = ?";

        try (Connection conn = connectionFactory.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            return mapResultSet(rs).stream().findFirst();

        } catch (SQLException e) {
            logger.warn("Error connecting to database at {}, error message {}", LocalDateTime.now().format(format), e.getMessage());
            throw new DataSourceException(e);
        }
    }

    public Optional<User> findUserByRole(String role) {

        String sql = baseSelect + "WHERE eur.role = ?";

        try (Connection conn = connectionFactory.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, role);

            ResultSet rs = pstmt.executeQuery();

            return mapResultSet(rs).stream().findFirst();

        } catch (SQLException e) {
            logger.warn("Error connecting to database at {}, error message {}", LocalDateTime.now().format(format), e.getMessage());
            throw new DataSourceException(e);
        }
    }

    // Check if user is active
    public boolean isActive (String username, String password) {
        try{
            Optional<User> user = findUserByUsernameAndPassword(username, password);

            if(user.get().getIs_active()) {
                return true;
            } else {
                return false;
            }
        } catch (NoSuchElementException e) {
            logger.warn("Error connecting to database at {}, error message {}", LocalDateTime.now().format(format), e.getMessage());
            throw new ResourceNotFoundException();
        }
    }

    public boolean isUsernameTaken (String username) {
        return findUserByUsername(username).isPresent();
    }

    public Optional<User> findUserByEmail(String email) {

        String sql = baseSelect + "WHERE eu.email = ?";

        try (Connection conn = connectionFactory.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();

            return mapResultSet(rs).stream().findFirst();

        } catch (SQLException e) {
            logger.warn("Error connecting to database at {}, error message {}", LocalDateTime.now().format(format), e.getMessage());
            throw new DataSourceException(e);
        }
    }

    public boolean isEmailTaken (String email) {
        return findUserByEmail(email).isPresent();
    }

    public String updateUser (UpdateUserRequest updateUserRequest) {
        return null;
    }

    public String updateUserEmail (String email, String username) {

        String sql = "UPDATE ers_users SET email = ? WHERE username = ?";

        try (Connection conn = connectionFactory.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, username);

            pstmt.executeUpdate();

            return "Email Updated";

        } catch (SQLException e) {
            logger.warn("Error connecting to database at {}, error message {}", LocalDateTime.now().format(format), e.getMessage());
            throw new DataSourceException(e);
        }
    }

    public String updateUserActive (boolean active, String username) {

        String sql = "UPDATE ers_users SET is_active = ? WHERE username = ?";

        try (Connection conn = connectionFactory.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1, active);
            pstmt.setString(2, username);

            pstmt.executeUpdate();

            return "Active Status Updated";

        } catch (SQLException e) {
            logger.warn("Error connecting to database at {}, error message {}", LocalDateTime.now().format(format), e.getMessage());
            throw new DataSourceException(e);
        }
    }

    public String updateUserRole (String role, String username) {

        String sql = "UPDATE ers_users SET role_id = ? WHERE username = ?";

        try (Connection conn = connectionFactory.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, role);
            pstmt.setString(2, username);

            pstmt.executeUpdate();

            return "Role Status Updated";

        } catch (SQLException e) {
            logger.warn("Error connecting to database at {}, error message {}", LocalDateTime.now().format(format), e.getMessage());
            throw new DataSourceException(e);
        }
    }

    public String deactivateUser(boolean active, String username) {

        String sql = "UPDATE ers_users set is_active = ? WHERE username = ?";

        try (Connection conn = connectionFactory.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1, active);
            pstmt.setString(2, username);

            pstmt.executeUpdate();

            return "Deactivated: ";

        } catch (SQLException e) {
            logger.warn("Error connecting to database at {}, error message {}", LocalDateTime.now().format(format), e.getMessage());
            throw new DataSourceException(e);
        }
    }

    private List<User> mapResultSet(ResultSet rs) throws SQLException {

        List<User> users = new ArrayList<>();

        while (rs.next()) {
            User user = new User();
            user.setUser_id(rs.getString("user_id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            //! user.setPassword(rs.getString("password"));
            user.setGiven_name(rs.getString("given_name"));
            user.setSurname(rs.getString("surname"));
            user.setIs_active(rs.getBoolean("is_active"));
            user.setRole(rs.getString("role"));

            users.add(user);
        }
        return users;
    }
}

/** DAO that gets all users from the database, delete a user
 * from the database, or saves a user to the database.
 */