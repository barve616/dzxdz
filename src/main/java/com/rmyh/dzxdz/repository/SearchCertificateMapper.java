package com.rmyh.dzxdz.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rmyh.dzxdz.entity.EleCarryCerInfoDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClssName: SearchCertificateMapper
 * @description: 查询电子携带证信息（以携带证编号形式）
 * @author: wyf
 * @create: 2022-01-24 14:23
 **/
@Mapper
public interface SearchCertificateMapper extends BaseMapper<EleCarryCerInfoDO> {
}
