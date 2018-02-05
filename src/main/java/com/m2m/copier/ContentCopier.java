package com.m2m.copier;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.m2m.domain.BillboardList;
import com.m2m.domain.Content;
import com.m2m.domain.EmotionPack;
import com.m2m.domain.EmotionPackDetail;
import com.m2m.domain.GiftInfo;
import com.m2m.domain.UserIndustry;
import com.m2m.entity.BillBoardListDTO;
import com.m2m.entity.ExtOutContent;
import com.m2m.mapstruct.TimeTransform;
import com.m2m.response.AttentionResponse;
import com.m2m.response.EmojiPackDetailResponse;
import com.m2m.response.EmojiPackageQueryResponse;
import com.m2m.response.GetGiftInfoListResponse;
import com.m2m.response.HotResponse;
import com.m2m.response.IndustryContentListResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = TimeTransform.class)
public interface ContentCopier {
	ContentCopier INSTANCE = Mappers.getMapper(ContentCopier.class);
	
	@Mapping(source = "readCountDummy", target = "readCount")
	@Mapping(source = "feeling", target = "tag")
	AttentionResponse.ContentElement asAttentionResponseContentElement(Content bean);
	
	@Mapping(source = "readCountDummy", target = "readCount")
	HotResponse.HotContentElement asHotResponseHotContentElement(Content extContent);
	
	
	BillBoardListDTO asBillBoardListDTO(BillboardList bbl);
	
	List<BillBoardListDTO> asBillBoardListDTOList(List<BillboardList> bblList);
	
	
	@Mapping(source = "id", target = "industryId")
	@Mapping(source = "industryName", target = "industry")
	IndustryContentListResponse.IndustryElement asIndustryElement(UserIndustry bean);
	
	List<IndustryContentListResponse.IndustryElement> asIndustryContentListIndustryElement(List<UserIndustry> list);
	
	@Mapping(source = "readCountDummy", target = "readCount")
	@Mapping(source = "feeling", target = "tag")
	ExtOutContent asExtOutContent4Content(Content extContent);
	
	@Mapping(source = "id", target = "packageId")
	@Mapping(source = "name", target = "packageName")
	@Mapping(source = "version", target = "packageVersion")
	@Mapping(source = "pVersion", target = "packagePversion")
	EmojiPackDetailResponse asEmojiPackDetailResponse4EmotionPack(EmotionPack bean);

	@Mapping(source = "thumbH", target = "thumb_h")
	@Mapping(source = "thumbW", target = "thumb_w")
	EmojiPackDetailResponse.PackageDetailData asPackageDetailData4EmotionPackDetail(EmotionPackDetail bean);
	
	EmojiPackageQueryResponse.PackageData asPackageData4EmotionPack(EmotionPack bean);

	@Mapping(source = "id", target = "giftId")
	GetGiftInfoListResponse.GiftInfoElement asGiftInfoElement4GiftInfo(GiftInfo bean);
}
