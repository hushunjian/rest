package com.m2m.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.m2m.request.AddAppDownloadLogRequest;
import com.m2m.request.BaseRequest;
import com.m2m.request.CreateKingdomRequest;
import com.m2m.request.DetailFidPageRequest;
import com.m2m.request.DetailPageRequest;
import com.m2m.request.DetailPageStatusRequest;
import com.m2m.request.DetailRequest;
import com.m2m.request.GetJoinLotteryUsersRequest;
import com.m2m.request.GetLiveByCidRequest;
import com.m2m.request.GetLotteryRequest;
import com.m2m.request.GetMyLivesRequest;
import com.m2m.request.GetTopicVoteInfoRequest;
import com.m2m.request.ImageInfoRequest;
import com.m2m.request.Live4LiveCoverRequest;
import com.m2m.request.MyTopicRequest;
import com.m2m.request.SettingModifyRequest;
import com.m2m.request.SettingsRequest;
import com.m2m.request.SpeakRequest;
import com.m2m.request.TopicUpdateRequest;
import com.m2m.response.BaseResponse;
import com.m2m.response.CreateKingdomResponse;
import com.m2m.response.DetailFidPageResponse;
import com.m2m.response.DetailPageResponse;
import com.m2m.response.DetailPageStatusResponse;
import com.m2m.response.DetailResponse;
import com.m2m.response.GetGiftInfoListResponse;
import com.m2m.response.GetJoinLotteryUsersResponse;
import com.m2m.response.GetLiveByCidResponse;
import com.m2m.response.GetLotteryResponse;
import com.m2m.response.GetTopicVoteInfoResponse;
import com.m2m.response.ImageInfoResponse;
import com.m2m.response.LiveCoverResponse;
import com.m2m.response.MyTopicResponse;
import com.m2m.response.SettingModifyResponse;
import com.m2m.response.SettingsResponse;
import com.m2m.response.SpeakResponse;
import com.m2m.response.TeaseListQueryResponse;
import com.m2m.response.TopicUpdateResponse;
import com.m2m.service.LiveService;
import com.m2m.web.Response;

@RestController
@Transactional
@RequestMapping("/live")
public class LiveController extends BaseController {

	@Autowired
	private LiveService liveService;
	
	/**
	 * 王国发言接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "speak")
	public Response<SpeakResponse> speak(SpeakRequest request){
		return liveService.speak(request);
	}

	/**
	 * 王国封面信息获取接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "liveCover")
	public Response<LiveCoverResponse> liveCover(Live4LiveCoverRequest request){
		return liveService.liveCover(request.getTopicId(), request.getUid(), request.getSource(), request.getFromUid());
	}
	
	/**
	 * 王国详情接口（IOS调用）
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "detail")
	public Response<DetailResponse> detail(DetailRequest request){
		if(request.getOffset() <= 0){
			request.setOffset(50);
		}
		if(request.getPageNo() <= 0){
			request.setPageNo(1);
		}
		return liveService.detail(request);
	}
	
	/**
	 * 王国详情接口（安卓调用）
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "detailPage")
	public Response<DetailPageResponse> detailPage(DetailPageRequest request){
		if(request.getOffset() <= 0){
			request.setOffset(50);
		}
		if(request.getPageNo() <= 0){
			request.setPageNo(1);
		}
		return liveService.detailPage(request);
	}
	
	/**
	 * 王国互动列表获取接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "getMyTopic")
	public Response<MyTopicResponse> getMyTopic(MyTopicRequest request){
		return liveService.getMyTopic(request);
	}
	
	/**
	 * 王国内容更新数量接口（配合王国详情接口）
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "getUpdate")
	public Response<TopicUpdateResponse> getUpdate(TopicUpdateRequest request){
		if(request.getOffset() <= 0){
			request.setOffset(50);
		}
		return liveService.getUpdate(request);
	}
	
	/**
	 * 获取fid所在分页页码(目前仅安卓使用)
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "detailFidPage")
	public Response<DetailFidPageResponse> detailFidPage(DetailFidPageRequest request){
		if(request.getOffset() <= 0){
			request.setOffset(50);
		}
		return liveService.detailFidPage(request);
	}
	
	/**
	 * 获取详情分页信息（目前仅安卓使用）
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "detailPageStatus")
	public Response<DetailPageStatusResponse> detailPageStatus(DetailPageStatusRequest request){
		if(request.getOffset() <= 0){
			request.setOffset(50);
		}
		return liveService.detailPageStatus(request);
	}
	
	/**
	 * 创建王国接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "createKingdom")
	public Response<CreateKingdomResponse> createKingdom(CreateKingdomRequest request){
		return liveService.createKingdom(request);
	}
	
	/**
	 * 王国设置信息查询接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "settings")
	public Response<SettingsResponse> settings(SettingsRequest request){
		return liveService.settings(request);
	}
	
	/**
	 * 王国设置信息修改接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "settingModify")
	public Response<SettingModifyResponse> settingModify(SettingModifyRequest request){
		return liveService.settingModify(request);
	}
	
	/**
	 * 获取王国信息接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "getLiveByCid")
	public Response<GetLiveByCidResponse> getLiveByCid(GetLiveByCidRequest request){
		return liveService.getLiveByCid(request);
	}
	
	/**
	 * H5记录下载历史接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "addAppDownloadLog")
	public Response<BaseResponse> addAppDownloadLog(AddAppDownloadLogRequest request){
		return liveService.addAppDownloadLog(request);
	}
	
	/**
	 * 王国详情列表投票信息查询接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "getTopicVoteInfo")
	public Response<GetTopicVoteInfoResponse> getTopicVoteInfo(GetTopicVoteInfoRequest request){
		return liveService.getTopicVoteInfo(request);
	}
	
	/**
	 * 逗一逗全量查询接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "teaseListQuery")
	public Response<TeaseListQueryResponse> teaseListQuery(BaseRequest request){
		return liveService.teaseListQuery(request);
	}
	
    /**
     * 图片信息查询接口
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/imageInfo",method = RequestMethod.POST)
    public Response<ImageInfoResponse> imageInfo(ImageInfoRequest request){
    	return liveService.imageInfo(request.getUid(), request.getTopicId(), request.getFid(), request.getImageName());
    }
    /**
     * 获取礼物列表接口
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getGiftInfoList",method = RequestMethod.POST)
    public Response<GetGiftInfoListResponse> getGiftInfoList(){
    	return liveService.getAllGiftInfoList();
    }
    
    /**
     * 抽奖参与用户留言查询接口
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getJoinLotteryUsers")
    public Response<GetJoinLotteryUsersResponse> getJoinLotteryUsers(GetJoinLotteryUsersRequest request){
    	return liveService.getJoinLotteryUsers(request);
    }
    
    /**
     * 打开app调用此接口获取王国更新红点(未启用)
     */
    @ResponseBody
    @RequestMapping(value = "/getRedDot",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<BaseResponse> getRedDot(GetMyLivesRequest request){
        return liveService.getRedDot(request.getUid(),request.getUpdateTime());
    }
    
    /**
     * 获取抽奖详情接口
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getLottery")
    public Response<GetLotteryResponse> getLottery(GetLotteryRequest request){
        return liveService.getLottery(request);
    }
}
