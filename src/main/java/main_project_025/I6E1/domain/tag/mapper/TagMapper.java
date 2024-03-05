package main_project_025.I6E1.domain.tag.mapper;

import main_project_025.I6E1.domain.tag.dto.TagRespondDto;
import main_project_025.I6E1.domain.tag.entity.Tag;
import main_project_025.I6E1.domain.tag.dto.TagPostDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag tagPostDtoToTag(TagPostDto tagPostDto);

    TagRespondDto tagToTagRespondDto(Tag tag);

    List<TagRespondDto> tagToRespondDto(List<Tag> tags);
}
