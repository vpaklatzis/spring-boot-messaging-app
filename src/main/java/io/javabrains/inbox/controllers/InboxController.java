package io.javabrains.inbox.controllers;

import io.javabrains.inbox.folders.Folder;
import io.javabrains.inbox.folders.FolderRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InboxController {
    private final FolderRepository folderRepository;

    public InboxController(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    @GetMapping("/")
    public String getHomepage(@AuthenticationPrincipal OAuth2User user, Model model) {
        String userId = user.getAttribute("login");

        if (user == null || !StringUtils.hasText(userId))
            return "index";

        List<Folder> folders = folderRepository.findAllById(userId);

        model.addAttribute("userFolders", folders);

        return "inbox-page";
    }
}
