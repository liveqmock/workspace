package com.yazuo.erp.mkt.dao;

import com.yazuo.erp.base.BaseDAO;
import com.yazuo.erp.mkt.vo.MktContactVo;
import com.yazuo.erp.mkt.vo.UserInfoVo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 *
 */
@Repository
public class MktContactDao extends BaseDAO {
    /**
     * 查询所有联系人
     *
     * @return
     */
    public List<MktContactVo> getAllMktContact() {
        return this.jdbcTemplate.query("select * from mkt.mkt_contact", new BeanPropertyRowMapper<MktContactVo>(MktContactVo.class));
    }

    /**
     * 查询所有联系人的手机信息
     * @return
     */
    public List<String> getAllMobileForContact() {
        return this.jdbcTemplate.queryForList("select mobile_phone from mkt.mkt_contact", String.class);
    }

    /**
     * 保存联系人
     *
     * @param contactVos
     * @return
     */
    public long  saveContact(List<MktContactVo> contactVos) {
        StringBuffer stringBufferSQL = new StringBuffer();
        stringBufferSQL.append("insert into mkt.mkt_contact(");
        stringBufferSQL.append("merchant_id,name,gender_type,birthday,position,mobile_phone,telephone," +
                "role_type,mail,micro_message,is_enable,remark,insert_by,insert_time,update_by,update_time");
        stringBufferSQL.append(")");
        stringBufferSQL.append("values(");
        stringBufferSQL.append(":merchantId,:name,:genderType,:birthday,:position,:mobilePhone,:telephone," +
                ":roleType,:mail,:microMessage,:isEnable,:remark,:insertBy,:insertTime,:updateBy,:updateTime");
        stringBufferSQL.append(")");
        List<Map<String, ?>> objects = new ArrayList<Map<String, ?>>();
        for (MktContactVo vo : contactVos) {
            Map<String, Object> object = new HashMap<String, Object>();
            object.put("merchantId", vo.getMerchantId());
//            object.put("storeId", null);
            object.put("name", vo.getName());
            object.put("genderType", vo.getGenderType());
            object.put("birthday", vo.getBirthday());
            object.put("position", vo.getPosition());
            object.put("mobilePhone", vo.getMobilePhone());
            object.put("telephone", vo.getTelephone());
            object.put("roleType", vo.getRoleType());
            object.put("mail", vo.getMail());
            object.put("microMessage", vo.getMicroMessage());
            object.put("isEnable", vo.getIsEnable());
            object.put("remark", vo.getRemark());
            object.put("insertBy", vo.getInsertBy());
            object.put("insertTime", vo.getInsertTime());
            object.put("updateBy", vo.getUpdateBy());
            object.put("updateTime", vo.getUpdateTime());
            objects.add(object);
        }

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = this.getErpNamedJdbcTemplate();
        int[] ints = namedParameterJdbcTemplate.batchUpdate(stringBufferSQL.toString(), objects.toArray(new Map[objects.size()]));
        long sum = 0;
        for (int i : ints) {
            sum += i;
        }
        return sum;
    }

    /**
     * 查询所有的联系人信息
     *
     * @return
     */
    public List<String> getMobilesForCrm() {
        return this.jdbcTemplateCrm.queryForList("select mobile from account.user_info where available_flag=true", String.class);
    }

    /**
     * 用户信息表
     *
     * @param mobiles
     * @return
     */
    public List<UserInfoVo> getUserInfoVos(List<String> mobiles) {
        String sql = "select * from account.user_info where mobile in(:mobiles)";
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = this.getCrmNamedJdbcTemplate();
        return namedParameterJdbcTemplate.query(sql, Collections.singletonMap("mobiles", mobiles), new BeanPropertyRowMapper<UserInfoVo>(UserInfoVo.class));
    }

    public List<Integer> getMerchantIdsForErp() {
        String sql = "select merchant_id from syn.syn_merchant";
        return this.jdbcTemplate.queryForList(sql, Integer.class);
    }


    protected NamedParameterJdbcTemplate getErpNamedJdbcTemplate() {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate.getDataSource());
        return namedParameterJdbcTemplate;
    }
    protected NamedParameterJdbcTemplate getCrmNamedJdbcTemplate() {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplateCrm.getDataSource());
        return namedParameterJdbcTemplate;
    }
}
