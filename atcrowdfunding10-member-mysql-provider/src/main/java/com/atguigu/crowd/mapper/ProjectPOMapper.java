package com.atguigu.crowd.mapper;

import com.atguigu.crowd.entity.po.ProjectPO;
import com.atguigu.crowd.entity.po.ProjectPOExample;
import com.atguigu.crowd.entity.vo.DetailProjectVO;
import com.atguigu.crowd.entity.vo.DetailReturnVO;
import com.atguigu.crowd.entity.vo.PortalProjectVO;
import com.atguigu.crowd.entity.vo.PortalTypeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectPOMapper {
    int countByExample(ProjectPOExample example);

    int deleteByExample(ProjectPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectPO record);

    int insertSelective(ProjectPO record);

    List<ProjectPO> selectByExample(ProjectPOExample example);

    ProjectPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByExample(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByPrimaryKeySelective(ProjectPO record);

    int updateByPrimaryKey(ProjectPO record);

    void insertRelationShipOfTypeIdList(@Param("projectId") Integer projectId, @Param("typeIdList")List<Integer> typeIdList);

    void insertRelationShipOfTagList(@Param("projectId") Integer projectId,@Param("tagIdList") List<Integer> tagIdList);

    /** 获取PortalTypeVO中的所有数据,并使用collection属性获取其PortalProjectVO的集合*/
    List<PortalTypeVO> selectPortalTypeVOList();

    /** collection中的select方法,用来根据id查询PortalProjectVO的*/
    PortalProjectVO selectPortalProjectVOList();

    DetailProjectVO selectDetailProjectVO(@Param("projectId") Integer projectId);

    List<DetailReturnVO> selectDeatailReturnVO();

    String selectDetailPicturePath();
}