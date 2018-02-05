package com.m2m.copier;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.m2m.mapstruct.TimeTransform;
import com.m2m.entity.ExtOutData;
import com.m2m.entity.ExtOutImageData;
import com.m2m.entity.ExtTagOutInfo;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = TimeTransform.class)
public interface TagCopier {
	TagCopier INSTANCE = Mappers.getMapper(TagCopier.class);
	
	@Mapping(source = "image", target = "fragmentImage")
	ExtOutImageData asExtOutImageData(ExtTagOutInfo bean);
	
	List<ExtOutImageData> asExtOutImageDataList(List<ExtTagOutInfo> beans);
	
	@Mapping(source = "fid", target = "id")
	@Mapping(source = "image", target = "fragmentImage")
	ExtOutData asExtOutData(ExtTagOutInfo bean);
	
	List<ExtOutData> asExtOutDataList(List<ExtTagOutInfo> beans);
}
