package com.imo.backend.contexts.journey_tracking.progress_milestone.http.controllers;

import com.imo.backend.contexts.common.ApplicationUrlHelper;
import com.imo.backend.contexts.journey_tracking.progress_milestone.lib.ProgressMilestoneUrlBuilder;
import com.imo.backend.contexts.journey_tracking.progress_milestone.usecases.GetPublicProgressMilestoneUseCase;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Hidden
@Controller
public class GetPublicProgressMilestonePageController {
  private final GetPublicProgressMilestoneUseCase getPublicProgressMilestoneUseCase;
  private final ApplicationUrlHelper applicationUrlHelper;

  public GetPublicProgressMilestonePageController(
      GetPublicProgressMilestoneUseCase getPublicProgressMilestoneUseCase,
      ApplicationUrlHelper applicationUrlHelper) {
    this.getPublicProgressMilestoneUseCase = getPublicProgressMilestoneUseCase;
    this.applicationUrlHelper = applicationUrlHelper;
  }

  @GetMapping("/m/{publicCode}")
  public String handle(@PathVariable String publicCode, Model model) {
    var milestone = this.getPublicProgressMilestoneUseCase.execute(publicCode);
    String baseUrl = this.applicationUrlHelper.getBackendBaseUrl();
    String shareUrl = ProgressMilestoneUrlBuilder.buildShareUrl(baseUrl, publicCode);
    String imageUrl = ProgressMilestoneUrlBuilder.buildImageUrl(baseUrl, publicCode);
    String profileUrl =
        this.applicationUrlHelper.buildFrontendUrl("/social/" + milestone.getUserId());

    model.addAttribute(
        "pageTitle", milestone.getAuthorNameSnapshot() + " concluiu um curso na IMO");
    model.addAttribute(
        "pageDescription",
        String.format(
            "%s concluiu %d de %d aulas do curso %s e compartilhou este marco de progresso.",
            milestone.getAuthorNameSnapshot(),
            milestone.getWatchedLessonsCountSnapshot(),
            milestone.getTotalLessonsCountSnapshot(),
            milestone.getCourseNameSnapshot()));
    model.addAttribute("imageUrl", imageUrl);
    model.addAttribute("shareUrl", shareUrl);
    model.addAttribute("profileUrl", profileUrl);
    model.addAttribute("milestone", milestone);
    model.addAttribute("generatedAt", milestone.getGeneratedAt());

    return "progress-milestone-public";
  }
}
