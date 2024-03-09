package main_project_025.I6E1.domain.tag.dto;

import lombok.Builder;
import lombok.Getter;
import main_project_025.I6E1.domain.tag.entity.Tag;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TagRespondDto {

    private Long tagId;
    private String tagName;

    @Builder
    public TagRespondDto(Long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public static TagRespondDto fromEntity(Tag tag) {
        return TagRespondDto.builder()
                .tagId(tag.getTagId())
                .tagName(tag.getTagName())
                .build();
    }

    public static List<TagRespondDto> fromEntityList(List<Tag> tagList) {
        return tagList.stream()
                .map(TagRespondDto::fromEntity)
                .collect(Collectors.toList());
    }
}
