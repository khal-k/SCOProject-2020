package com.atguigu.crowd.handler;

import com.atguigu.crowd.entity.vo.ProjectVO;
import com.atguigu.crowd.service.api.ProjectProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import util.ResultEntity;

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
