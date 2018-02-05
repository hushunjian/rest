package com.m2m.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.m2m.request.BaseRequest;
import com.m2m.request.Home4AttentionRequest;
import com.m2m.request.Home4HotRequest;
import com.m2m.request.Home4RecTagGroupRequest;
import com.m2m.request.Home4TagLikeRequest;
import com.m2m.request.IndustryContentListRequest;
import com.m2m.request.IndustryOptRequest;
import com.m2m.request.NewestRequest;
import com.m2m.request.PricedKingdomListRequest;
import com.m2m.request.ShowListDetailRequest;
import com.m2m.request.ShowListRequest;
import com.m2m.response.AttentionResponse;
import com.m2m.response.BaseResponse;
import com.m2m.response.HotResponse;
import com.m2m.response.IndustryContentListResponse;
import com.m2m.response.NewestResponse;
import com.m2m.response.PricedKingdomListResponse;
import com.m2m.response.RecTagGroupResponse;
import com.m2m.response.ShowListDetailResponse;
import com.m2m.response.ShowListResponse;
import com.m2m.response.TagLikeResponse;
import com.m2m.response.UserGroupResponse;
import com.m2m.service.ContentService;
import com.m2m.web.Response;

@RestController
@Transactional
@RequestMapping("/home")
public class HomeController extends BaseController {
	
	@Autowired
	private ContentService contentService;
	
	/**
	 * 关注列表接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "attention")
    public Object attention(Home4AttentionRequest request) {
		AttentionResponse response = contentService.attention(request.getUid(), request.getUpdateTime());
        return Response.success(response);
    }
	/**
	 * 首页标签组获取接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "recTagGroup")
    public Object recTagGroup(Home4RecTagGroupRequest request) {
		RecTagGroupResponse response = contentService.recTagGroup(request.getUid());
        return Response.success(response);
    }
	/**
	 * 标签喜欢不喜欢
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "tagLike")
    public Object tagLike(Home4TagLikeRequest request) {
		TagLikeResponse response = contentService.tagLike(request.getUid(), request.getData(), request.getIsLike(), request.getNeedNew(), request.getTagIds());
        return Response.success(response);
    }
	/**
	 * 推荐列表接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "hot")
    public Object hot(Home4HotRequest request) {
		HotResponse response = contentService.hot(request.getPage(),request.getUid());
        return Response.success(response);
    }
	
	/**
	 * 榜单列表获取接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "showList")
	public Response<ShowListResponse> showList(ShowListRequest request){
		return contentService.showList(request);
	}
	
	/**
	 * 榜单详情列表获取接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "showListDetail")
	public Response<ShowListDetailResponse> showListDetail(ShowListDetailRequest request){
		return contentService.showListDetail(request);
	}
	
	/**
	 * 王国价值排行榜列表获取接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "getPricedKingdomList")
	public Response<PricedKingdomListResponse> getPricedKingdomList(PricedKingdomListRequest request){
		return contentService.getPricedKingdomList(request);
	}
	/**
	 * 行业操作接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "industryOpt")
	public Response<BaseResponse> industryOpt(IndustryOptRequest request){
		return contentService.industryOpt(request.getUid(), request.getIndustryId(), request.getAction());
	}
	/**
	 * 行业内容列表接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "industryContentList")
	public Response<IndustryContentListResponse> industryContentList(IndustryContentListRequest request){
		return contentService.industryContentList(request.getUid(), request.getPage(), request.getIndustryId());
	}
	/**
	 * 用户组接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "userGroup")
	public Response<UserGroupResponse> userGroup(BaseRequest request){
		return contentService.userGroup(request.getUid());
	}
    /**
     * 广场接口
     * @param request
     * @return
     */
    @RequestMapping(value = "/newest",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response<NewestResponse> newest(NewestRequest request){
        if(request.getSinceId() == -1){
            request.setSinceId(Long.MAX_VALUE);
        }
        return contentService.newest(request.getSinceId(),request.getUid());
    }
}
