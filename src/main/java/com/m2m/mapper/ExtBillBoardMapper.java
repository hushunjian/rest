package com.m2m.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.m2m.entity.BillBoardListDTO;

public interface ExtBillBoardMapper {

	/**
	 * 最活跃的米汤新鲜人
	 */
	List<BillBoardListDTO> getActiveUserBillboard(@Param("sinceId")long sinceId, @Param("pageSize")int pageSize, @Param("blacklistUids")List<Long> blacklistUids);
	
	/**
	 * 这里的互动最热闹
	 */
	List<BillBoardListDTO> getInteractionHottestKingdomBillboard(@Param("sinceId")long sinceId, @Param("pageSize")int pageSize, @Param("blacklistUids")List<Long> blacklistUids);
	
	/**
	 * 最新更新的王国
	 */
	List<BillBoardListDTO> getLivesByUpdateTime(@Param("sinceId")long sinceId, @Param("pageSize")int pageSize, @Param("blacklistUids")List<Long> blacklistUids);
	
	/**
	 * 获取有王国的新注册的用户
	 * @param sex -1全部，0女，1男
	 */
	List<BillBoardListDTO> getNewPeople(@Param("sex")int sex, @Param("sinceId")long sinceId, @Param("pageSize")int pageSize, @Param("blacklistUids")List<Long> blacklistUids);
	
	/**
	 * 新注册的用户（无所谓有没有王国）
	 */
	List<BillBoardListDTO> getNewRegisterUsers(@Param("sinceId")long sinceId, @Param("pageSize")int pageSize, @Param("blacklistUids")List<Long> blacklistUids);
	
	/**
	 * 炙手可热的米汤红人
	 */
	List<BillBoardListDTO> fansBillboard(@Param("start")long start, @Param("pageSize")int pageSize, @Param("blacklistUids")List<Long> blacklistUids);
	
	/**
	 * 王国价值最高排行榜
	 */
	List<BillBoardListDTO> kingdomPriceList(@Param("start")long start, @Param("pageSize")int pageSize, @Param("blacklistUids")List<Long> blacklistUids);
	
	/**
	 * 王国价值上升最快排行榜
	 */
	List<BillBoardListDTO> kingdomIncrPriceList(@Param("start")long start, @Param("pageSize")int pageSize, @Param("blacklistUids")List<Long> blacklistUids);
	
	/**
	 * 标签王国价值最高排行榜
	 * @param tag 标签名
	 */
	List<BillBoardListDTO> tagKingdomPriceList(@Param("tag")String tag, @Param("start")long start, @Param("pageSize")int pageSize, @Param("blacklistUids")List<Long> blacklistUids);
	
	/**
	 * 标签王国价值增长最快排行榜
	 * @param tag 标签名
	 */
	List<BillBoardListDTO> tagKingdomIncrPriceList(@Param("tag")String tag, @Param("start")long start, @Param("pageSize")int pageSize, @Param("blacklistUids")List<Long> blacklistUids);
	
	/**
	 * 个人米汤币排行榜
	 */
	List<BillBoardListDTO> userCoinList(@Param("start")long start, @Param("pageSize")int pageSize, @Param("blacklistUids")List<Long> blacklistUids);
	
	/**
	 * 对外分享次数用户榜单(2017-08-07 00:00:00开始)
	 */
	List<BillBoardListDTO> shareUserList(@Param("start")long start, @Param("pageSize")int pageSize, @Param("blacklistUids")List<Long> blacklistUids);
	
	/**
	 * 外部阅读次数王国榜单(2017-08-07 00:00:00开始)
	 */
	List<BillBoardListDTO> outReadKingdomList(@Param("start")long start, @Param("pageSize")int pageSize, @Param("blacklistUids")List<Long> blacklistUids);
	
	/**
	 * 正在抽奖的王国
	 */
	List<BillBoardListDTO> kingdomLotteryList(@Param("start")long start, @Param("pageSize")int pageSize, @Param("blacklistUids")List<Long> blacklistUids);
	
	/**
	 * 优秀的新王国(王国榜单)
     * 1 王国成立时间5天(宽泛的5天,天数相减即可,不需要精确到小时)
	 * 2 这5天内更新天数超过4天
	 * 3 第5天(也就是今天)还在更新
	 * 4 除去语录更新
	 * 5 排序规则为: 王国价值从大到小
	 */
	List<BillBoardListDTO> goodNewKingdomList(@Param("today")String today, @Param("preFiveDay")String preFiveDay, @Param("start")long start, @Param("pageSize")int pageSize, @Param("blacklistUids")List<Long> blacklistUids);
}
