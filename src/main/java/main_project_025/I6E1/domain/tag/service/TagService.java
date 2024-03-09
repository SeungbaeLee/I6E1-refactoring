package main_project_025.I6E1.domain.tag.service;

import lombok.RequiredArgsConstructor;
import main_project_025.I6E1.domain.commission.entity.Commission;
import main_project_025.I6E1.domain.commission.repository.CommissionRepository;
import main_project_025.I6E1.domain.tag.entity.CommissionTag;
import main_project_025.I6E1.domain.tag.entity.Tag;
import main_project_025.I6E1.domain.tag.repository.CommissionTagRepository;
import main_project_025.I6E1.domain.tag.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {

    private final TagRepository tagRepository;
    private final CommissionRepository commissionRepository;
    private final CommissionTagRepository commissionTagRepository;

    public Commission createTag(List<String> tagNames, Commission commission) {
        for (String tagName : tagNames) {
            Tag tag = tagRepository.findByTagName(tagName);
            if (tag != null) {
                CommissionTag commissionTag = CommissionTag.builder()
                        .commission(commission)
                        .tag(tag)
                        .tagName(tag.getTagName())
                        .build();
                commission.getTags().add(commissionTag);
                commissionTagRepository.save(commissionTag);
            } else {
                Tag newTag = Tag.builder()
                        .tagName(tagName)
                        .commissions(new ArrayList<>())
                        .build();
                CommissionTag commissionTag = CommissionTag.builder()
                        .commission(commission)
                        .tag(newTag)
                        .tagName(newTag.getTagName())
                        .build();
                newTag.getCommissions().add(commissionTag);
                tagRepository.save(newTag);
                commissionTagRepository.save(commissionTag);
                commission.getTags().add(commissionTag);
            }
            commissionRepository.save(commission);
        }
        return commission;
    }

    public Page<Tag> readTags(Pageable pageable) {
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        return tagRepository.findAll(pageRequest);
    }
}