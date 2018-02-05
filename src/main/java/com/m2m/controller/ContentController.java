package com.m2m.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.m2m.request.AcKingdomListRequest;
import com.m2m.request.EmojiPackageDetailRequest;
import com.m2m.request.MyPublishByTypeRequest;
import com.m2m.request.TagDetailRequest;
import com.m2m.response.EmojiPackDetailResponse;
import com.m2m.response.EmojiPackageQueryResponse;
import com.m2m.response.TagDetailResponse;
import com.m2m.service.ContentService;
import com.m2m.web.Response;

@RestController
@Transactional
@RequestMapping("/content")
public class ContentController extends BaseController {
	
	@Autowired
	private ContentService contentService;
	
	/**
	 * 聚合王国子王国列表接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "acKingdomList")
    public Object acKingdomList(AcKingdomListRequest request) {
        return contentService.acKingdomList(request.getUid(), request.getCeTopicId(), request.getResultType(), request.getPage());
    }
    /**
     * 用户王国列表接口
     * @param request
     * @return
     */
    @RequestMapping(value = "/myPublishByType",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object myPublishByType(MyPublishByTypeRequest request){
        if(request.getUpdateTime() <= 0){
        	request.setUpdateTime(Long.MAX_VALUE);
        }
        return contentService.myPublishByType(request.getCustomerId(),request.getType(),request.getUpdateTime(),request.getUid());
    }
    /**
     * 标签详情页接口
     * @param request
     * @return
     */
    @RequestMapping(value = "/tagDetail",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response<TagDetailResponse> tagDetail(TagDetailRequest request){
    	return contentService.tagDetail(request.getUid(), request.getTagId(), request.getTagName(), request.getPage());
    }
    /**
     * 表情包详情获取接口，获取表情包里的具体表情
     * @param request
     * @return
     */
    @RequestMapping(value = "/emojiPackageDetail",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response<EmojiPackDetailResponse> emojiPackageDetail(EmojiPackageDetailRequest request){
    	return contentService.emojiPackageDetail(request.getPackageId());
    }
    
    /**
     * 表情包全量查询接口
     * @return
     */
    @RequestMapping(value = "/emojiPackageQuery",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response<EmojiPackageQueryResponse> emojiPackageQuery(){
        return contentService.emojiPackageQuery();
    }
}
