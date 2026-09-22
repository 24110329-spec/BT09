package vn.iotstar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.dto.ProductDTO;
import vn.iotstar.entity.User;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.service.ProductService;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UserRepository userRepository;

    // ========== LIST ==========
    @GetMapping
    public String list(@RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "8") int size,
                       Model model) {
        Page<ProductDTO> productPage = productService.findAll(
                PageRequest.of(page, size, Sort.by("id").ascending()));
        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "products/list";
    }

    // ========== FORM ADD ==========
    @GetMapping("/add")
    public String addForm(Model model, Authentication auth) {
        ProductDTO dto = new ProductDTO();
        // Nếu là USER thường, gán sẵn userId = chính họ
        if (auth != null && auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            userRepository.findByEmailWithRole(auth.getName()).ifPresent(u -> dto.setUserId(u.getId()));
        }
        model.addAttribute("productDTO", dto);
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("isEdit", false);
        return "products/form";
    }

    @PostMapping("/add")
    public String addSubmit(@Valid @ModelAttribute("productDTO") ProductDTO dto,
                            BindingResult result,
                            Model model,
                            RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("users", userRepository.findAll());
            model.addAttribute("isEdit", false);
            return "products/form";
        }
        productService.save(dto);
        ra.addFlashAttribute("success", "Thêm sản phẩm thành công!");
        return "redirect:/products";
    }

    // ========== FORM EDIT ==========
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("productDTO", productService.findById(id));
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("isEdit", true);
        return "products/form";
    }

    @PostMapping("/edit/{id}")
    public String editSubmit(@PathVariable Long id,
                             @Valid @ModelAttribute("productDTO") ProductDTO dto,
                             BindingResult result,
                             Model model,
                             RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("users", userRepository.findAll());
            model.addAttribute("isEdit", true);
            return "products/form";
        }
        dto.setId(id);
        productService.save(dto);
        ra.addFlashAttribute("success", "Cập nhật sản phẩm thành công!");
        return "redirect:/products";
    }

    // ========== DELETE ==========
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        productService.deleteById(id);
        ra.addFlashAttribute("success", "Xóa sản phẩm thành công!");
        return "redirect:/products";
    }
}
