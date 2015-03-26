package com.yazuo.erp.train.dao;

import com.yazuo.erp.train.JUnitBase;
import com.yazuo.erp.train.vo.TraStudentRecordVO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author congshuanglong
 */
public class StudentRecordDaoTest extends JUnitBase {

    @Resource
    private TraStudentRecordDao traStudentRecordDao;

    @Before
    public void init(){

    }

    @Test
    public void testGetStudiedCoursewareIds(){

    }

    @Test
    public void testSave() {
        TraStudentRecordVO studentRecordVO = new TraStudentRecordVO();
        studentRecordVO.setStudentId(3);
        studentRecordVO.setTeacherId(4);
        studentRecordVO.setCourseId(1);
        studentRecordVO.setCoursewareId(1);
        studentRecordVO.setOperatingType("0");
        studentRecordVO.setIsTimeout("1");
        studentRecordVO.setBeginTime(new Date());
        studentRecordVO.setInsertBy(3);
        studentRecordVO.setInsertTime(new Date());
        studentRecordVO.setUpdateBy(3);
        studentRecordVO.setUpdateTime(new Date());
        this.traStudentRecordDao.saveTraStudentRecord(studentRecordVO);
    }
    @After
    public void destroy(){}

}
