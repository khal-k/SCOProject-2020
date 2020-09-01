package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.vo.DetailProjectVO;
import com.atguigu.crowd.entity.vo.PortalTypeVO;
import com.atguigu.crowd.entity.vo.ProjectVO;

import java.util.List;

/**
 * @author 孔佳齐丶
 * @create 2020-08-30 10:03
 * @package com.atguigu.crowd.service.api
 */
public interface ProjectProviderService {
    /** 将projectVo插入到数据库*/
    void saveProject(ProjectVO projectVO, Integer memberId);

    /** 遍历project*/
    List<PortalTypeVO> getPortalTypeVO();

    DetailProjectVO getDetailProjectVO(Integer projectId );
}
