package com.example.samuraitravel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.Favorite;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.repository.FavoriteRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.FavoriteService;

@Controller
public class FavoriteController {
	private final FavoriteRepository favoriteRepository;
	private final FavoriteService favoriteService;
	
	public FavoriteController(FavoriteRepository favoriteRepository, FavoriteService favoriteService) {
		this.favoriteRepository = favoriteRepository;
		this.favoriteService = favoriteService;
	}
	
	@GetMapping("/favorites")
	public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model,
			@PageableDefault(page = 0, size = 10, sort ="id", direction = Direction.ASC) Pageable pageable) {
		User user = userDetailsImpl.getUser();
		Page<Favorite> favoritePage = favoriteRepository.findByUser(user, pageable);
		model.addAttribute("favoritePage", favoritePage);
		return "favorites/index";
	}
	
	//お気に入り追加機能
	@PostMapping("/houses/{houseId}/favorites/add")
	public String add(@PathVariable("houseId") Integer houseId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model, RedirectAttributes redirectAttributes) {
		User user = userDetailsImpl.getUser();
		favoriteService.add(houseId, user.getId());
		
		redirectAttributes.addFlashAttribute("successMessage", "お気に入りを登録しました。");
		
		return "redirect:/houses/{houseId}";
	}
	
	//お気に入り解除機能
	@PostMapping("/houses/{houseId}/favorites/delete")
	public String delete(@PathVariable("houseId") Integer houseId, @RequestParam("favoriteId") Integer favoriteId, Model model, RedirectAttributes redirectAttributes) {
		favoriteRepository.deleteById(favoriteId);
		redirectAttributes.addFlashAttribute("successMessage", "お気に入りを削除しました。");
		return "redirect:houses/{houseId}";
	}

}
