package csu.songtie.itie.service;

import csu.songtie.itie.domain.entity.Category;
import csu.songtie.itie.domain.entity.Tag;

import java.util.List;

public interface TagService {

    List<Tag> getTagListByCourseId(int category_Id);
}
