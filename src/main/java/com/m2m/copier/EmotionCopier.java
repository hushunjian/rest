package com.m2m.copier;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.m2m.mapstruct.TimeTransform;
import com.m2m.entity.ExtUserEmotion;
import com.m2m.response.EmotionHisListResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = TimeTransform.class)
public interface EmotionCopier {
	EmotionCopier INSTANCE = Mappers.getMapper(EmotionCopier.class);
	
	@Mapping(source = "eid", target = "id")
	@Mapping(source = "timeInterval", target = "timeInterval")
	@Mapping(source = "extra", target = "emotionPack.extra")
	@Mapping(source = "content", target = "emotionPack.content")
	@Mapping(source = "h", target = "emotionPack.h")
	@Mapping(source = "w", target = "emotionPack.w")
	@Mapping(source = "pid", target = "emotionPack.id")
	@Mapping(source = "image", target = "emotionPack.image")
	@Mapping(source = "thumb", target = "emotionPack.thumb")
	@Mapping(source = "thumb_h", target = "emotionPack.thumb_h")
	@Mapping(source = "thumb_w", target = "emotionPack.thumb_w")
	@Mapping(source = "title", target = "emotionPack.title")
	EmotionHisListResponse.EmotionElement ext2EmotionElement(ExtUserEmotion bean);
	
	List<EmotionHisListResponse.EmotionElement> ext2EmotionElementList(List<ExtUserEmotion> bean);
}
