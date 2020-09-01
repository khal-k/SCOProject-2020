package com.atguigu.crowd.handler;

import com.atguigu.crowd.entity.vo.DetailProjectVO;
import com.atguigu.crowd.entity.vo.PortalTypeVO;
import com.atguigu.crowd.entity.vo.ProjectVO;
import com.atguigu.crowd.service.api.ProjectProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.ResultEntity;

import java.util.List;

/**
 * @author 孔佳齐丶
 * @create 2020-08-30 10:03
 * @package com.atguigu.crowd.handler
 */
@RestController
public class ProjectProviderHandler {

    @Autowired
    private ProjectProviderService projectProviderService;

    /**
     * 根据portal页面的项目id获取得到该对象的所有详细信息
     * @param projectId
     * @return
     */
    @RequestMapping("/get/project/detail/remote/{projectId}")
    public ResultEntity<DetailProjectVO> getDate(@PathVariable("projectId") Integer projectId){
        DetailProjectVO detailProjectVO = null;
        try {
            detailProjectVO = projectProviderService.getDetailProjectVO(projectId);
            return  ResultEntity.successWithData(detailProjectVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 得到所有peoject对象
     * @return
     */
    @RequestMapping("/get/portal/type/project/data/remote")
    ResultEntity<List<PortalTypeVO>> getPortalTypeProjectData(){

        try {
            List<PortalTypeVO> portalTypeVOList = projectProviderService.getPortalTypeVO();
            return ResultEntity.successWithData(portalTypeVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 处理远程调用将Projectvo存入到mysql数据库
     * @param projectVO
     * @param memberId
     * @return
     */
    @RequestMapping("/save/project/vo/remote")
    ResultEntity<String> saveProjectVo(@RequestBody ProjectVO projectVO,
                                       @RequestParam("memberId") Integer memberId){

        try {
            //1.调用本地service执行保存
            projectProviderService.saveProject(projectVO,memberId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

        return ResultEntity.successWithoutData();
    }
}
