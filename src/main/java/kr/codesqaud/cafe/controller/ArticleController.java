package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.article.ArticleRepository;

import kr.codesqaud.cafe.util.SessionConst;
import kr.codesqaud.cafe.util.ValidateConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ArticleController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping("/qna")
    public String addArticle(@ModelAttribute Article article, Model model, HttpSession httpSession) {

        if (httpSession.getAttribute("loggedInId") == null) {
            model.addAttribute("errorMessage", ValidateConst.UNKNOWN_USER);
            return "util/error";
        }
        articleRepository.save(article);
        log.debug("debug log={}", article.getContents());
        return "redirect:/qna/list";
    }

    @GetMapping(value = {"/qna/list", "/"})
    public String findArticleList(Model model) {
        List<Article> articleList = articleRepository.findAllArticles();
        model.addAttribute("articleList", articleList);
        log.info("createdAt={}", articleList.get(0).getFormattedCreatedAt());
        return "qna/list";
    }

    @GetMapping("/qna/{id}")
    public String findArticle(@PathVariable int id, Model model, HttpSession httpSession) {

        if (httpSession.getAttribute("loggedInId") == null) {
            model.addAttribute("errorMessage", ValidateConst.UNKNOWN_USER);
            return "util/error";
        }

        Article article = articleRepository.findByArticleId(id);
        model.addAttribute("article", article);

        return "qna/show";
    }
}
