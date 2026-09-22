package vn.iotstar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.dto.UserDTO;
import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // ========== LIST + SEARCH ==========
    @GetMapping
    public String list(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "8") int size,
                       Model model) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<UserDTO> userPage = userService.searchUsers(keyword, pageable);
        model.addAttribute("userPage", userPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        return "users/list";
    }

    // ========== FORM ADD ==========
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("isEdit", false);
        return "users/form";
    }

    @PostMapping("/add")
    public String addSubmit(@Valid @ModelAttribute("userForm") User user,
                            BindingResult result,
                            @RequestParam("roleId") Long roleId,
                            @RequestParam("rawPassword") String rawPassword,
                            Model model,
                            RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            model.addAttribute("isEdit", false);
            return "users/form";
        }
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            model.addAttribute("roles", roleRepository.findAll());
            model.addAttribute("isEdit", false);
            model.addAttribute("error", "Email đã tồn tại trong hệ thống!");
            return "users/form";
        }
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        ra.addFlashAttribute("success", "Thêm người dùng thành công!");
        return "redirect:/users";
    }

    // ========== FORM EDIT ==========
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("userForm", user);
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("isEdit", true);
        return "users/form";
    }

    @PostMapping("/edit/{id}")
    public String editSubmit(@PathVariable Long id,
                             @Valid @ModelAttribute("userForm") User user,
                             BindingResult result,
                             @RequestParam("roleId") Long roleId,
                             @RequestParam(value = "rawPassword", required = false) String rawPassword,
                             Model model,
                             RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            model.addAttribute("isEdit", true);
            return "users/form";
        }
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        existing.setFullName(user.getFullName());
        existing.setEnabled(user.isEnabled());
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        existing.setRole(role);
        if (rawPassword != null && !rawPassword.isBlank()) {
            existing.setPassword(passwordEncoder.encode(rawPassword));
        }
        userRepository.save(existing);
        ra.addFlashAttribute("success", "Cập nhật người dùng thành công!");
        return "redirect:/users";
    }

    // ========== DELETE ==========
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        userRepository.deleteById(id);
        ra.addFlashAttribute("success", "Xóa người dùng thành công!");
        return "redirect:/users";
    }

    // ========== TOGGLE ACTIVE ==========
    @PostMapping("/toggle/{id}")
    public String toggleEnabled(@PathVariable Long id, RedirectAttributes ra) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
        String msg = user.isEnabled() ? "Đã kích hoạt tài khoản." : "Đã khóa tài khoản.";
        ra.addFlashAttribute("success", msg);
        return "redirect:/users";
    }
}
