package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Qna;
import kr.codesqaud.cafe.repository.QnaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class QnaController {

    private Logger log = LoggerFactory.getLogger(getClass());
    private QnaRepository qnaRepository;

    @Autowired
    public QnaController(QnaRepository qnaRepository) {
        this.qnaRepository = qnaRepository;
    }

    @PostMapping("/qna")
    public String addUser(@ModelAttribute Qna qna) {
        qnaRepository.save(qna);
        log.debug("debug log={}", qna.getContents());
        return "redirect:/qna/list";
    }

    @GetMapping(value = {"/qna/list", "/"})
    public String findQnaList(Model model) {
        List<Qna> qnaList = qnaRepository.findAllQnas();
        model.addAttribute("qnaList", qnaList);
        log.info("debug log={}", qnaList.size());
        return "/qna/list";
    }
}
