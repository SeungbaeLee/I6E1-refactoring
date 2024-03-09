package main_project_025.I6E1.domain.tag.controller;

import lombok.RequiredArgsConstructor;
import main_project_025.I6E1.domain.tag.dto.TagRespondDto;
import main_project_025.I6E1.domain.tag.entity.Tag;
import main_project_025.I6E1.domain.tag.service.TagService;
import main_project_025.I6E1.global.Page.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
@Validated
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity readAllTag(Pageable pageable) {
        Page<Tag> tagPage = tagService.readTags(pageable);
        List<Tag> tagList = tagPage.getContent();
        return new ResponseEntity<>(new PageDto<>(TagRespondDto.fromEntityList(tagList), tagPage), HttpStatus.OK);
    }
}