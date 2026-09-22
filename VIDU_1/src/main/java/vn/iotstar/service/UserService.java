package vn.iotstar.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.iotstar.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO findById(Long id);
    UserDTO findByEmail(String email);
    List<UserDTO> findAll();
    Page<UserDTO> searchUsers(String keyword, Pageable pageable);
    UserDTO save(UserDTO userDTO);
    void deleteById(Long id);
    long count();
}
